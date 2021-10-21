using System;
using System.Collections.Concurrent;
using System.Threading;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using Serilog;
using Telegram.Bot.Requests.Abstractions;

namespace BotFramework.Services.Clients
{
    public class MemorySink : IRequestSinc
    {
        private readonly ILogger _logger;

        public MemorySink(ILogger logger)
        {
            _logger = logger;
        }

        private readonly ConcurrentQueue<TaskCompletionSource<object>?> GetTasks = new();

        private readonly ConcurrentQueue<object> RequestToSend   = new();
        private readonly ConcurrentQueue<object> TelegramReplies = new();

        public async Task<TResponse> MakeRequest<TResponse>(IRequest<TResponse> request,
                                                            CancellationToken   cancellationToken = default)
        {
            TelegramReplies.TryDequeue(out var reply);

            lock (GetTasks)
            {
                GetTasks.TryPeek(out var task);
                if (task?.Task.IsCompleted == false)
                {
                    task.SetResult(request);
                    GetTasks.TryDequeue(out _);
                    return (TResponse)reply!;
                }
            }

            RequestToSend.Enqueue(request);
            _logger.Verbose("{Message}", await request.ToHttpContent().ReadAsStringAsync(cancellationToken));
            return (TResponse)reply!;
        }

        public ValueTask<TResponse> GetRequest<TResponse>(Func<TResponse, bool>? filter = null)
        {
            TResponse? updateToReturn = default;
            while (RequestToSend.TryDequeue(out var update))
            {
                if (update is not TResponse item || filter?.Invoke(item) == false)
                {
                    continue;
                }

                updateToReturn = item;
                break;
            }

            if (updateToReturn is not null)
            {
                return ValueTask.FromResult(updateToReturn);
            }

            var source = new TaskCompletionSource<object>();
            lock (GetTasks)
            {
                GetTasks.Enqueue(source);
            }

            var task = source?.Task.ContinueWith(a =>
                       (TResponse)a.GetAwaiter().GetResult())
                       ?? Task.FromResult(default(TResponse))!;
            return new ValueTask<TResponse>(task);
        }
    }
}