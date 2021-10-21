using System.Collections.Concurrent;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using BotFramework.Extensions;
using BotFramework.Services.Clients;
using Telegram.Bot.Types;

namespace BotFramework.Middleware
{
    public class EndpointMiddleware
    {
        private static readonly ConcurrentDictionary<long, PriorityUpdateConsumer> _consumers = new();
        private readonly        UpdateDelegate                                     _next;


        public EndpointMiddleware(UpdateDelegate next)
        {
            _next = next;
        }

        public Task Invoke(Update                 update,
                           UpdateContext          updateContext,
                           ICommandUpdateConsumer client)
        {
            var id = update.GetId()!.Value;

            if (_consumers.TryGetValue(id, out var consumer))
            {
                consumer.Consume(updateContext, client);
            }
            else
            {
                var a = new PriorityUpdateConsumer();
                _consumers.TryAdd(id, a);
                a.Consume(updateContext, client);
            }

            return _next.Invoke(update);
        }
    }
}