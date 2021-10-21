using System;
using System.Collections.Concurrent;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using Telegram.Bot.Types;

namespace BotFramework.Services.Clients
{
    public class CommandUpdateConsumer : ICommandUpdateConsumer
    {
        public void Initialize(Task command)
        {
            CurrentTask = command;
        }

        private readonly ConcurrentDictionary<UpdateTask, bool> _handlers = new();
        private readonly ConcurrentQueue<Update>                Updates   = new();
        public           bool                                   IsDone => CurrentTask.IsCompleted;
        private          Task                                   CurrentTask = null!;

        public ValueTask<Update> GetUpdate(Func<Update, bool>? filter = null, Action<Update>? onFilterFail = null)
        {
            var handler = new UpdateTask(filter, onFilterFail, Updates, handler => _handlers.TryRemove(handler, out _));
            _handlers.TryAdd(handler, default);
            return handler.GetUpdate();
        }

        public void Consume(Update update)
        {
            foreach (var (handler, _) in _handlers)
            {
                if (handler.HandleUpdate(update))
                {
                    return;
                }
            }

            Updates.Enqueue(update);
        }
    }
}