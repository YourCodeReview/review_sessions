using System;
using System.Threading;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using BotFramework.Extensions;
using Telegram.Bot.Requests.Abstractions;
using Telegram.Bot.Types;

namespace BotFramework.Services.Clients
{
    public class Client : IClient
    {
        public long UserId { get; }

        private readonly IRequestSinc _requestSinc;
        private readonly IUpdateQueue _updateQueue;

        public Client(IRequestSinc requestSinc, ICommandUpdateConsumer updateQueue, Update update)
        {
            UserId       = update.GetId()!.Value;
            _requestSinc = requestSinc;
            _updateQueue = updateQueue;
        }

        public Task<TResponse> MakeRequest<TResponse>(
            IRequest<TResponse> request,
            CancellationToken   cancellationToken = default)
        {
            return _requestSinc.MakeRequest(request, cancellationToken);
        }

        public ValueTask<Update> GetUpdate(Func<Update, bool>? filter = null, Action<Update>? onFilterFail = null)
        {
            return _updateQueue.GetUpdate(filter, onFilterFail);
        }
    }
}