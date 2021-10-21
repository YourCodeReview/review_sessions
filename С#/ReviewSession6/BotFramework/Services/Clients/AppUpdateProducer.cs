using System.Threading.Tasks;
using BotFramework.Extensions.Hosting;
using Telegram.Bot.Types;

namespace BotFramework.Services.Clients
{
    public class AppUpdateProducer
    {
        public Task FromUser(Update  update)  => _debugDelegateWrapper.App(update);
        public Task FromUser(Message message) => FromUser(new Update { Message = message });

        private readonly BotFrameworkExtensions.DebugDelegateWrapper _debugDelegateWrapper;

        public AppUpdateProducer(BotFrameworkExtensions.DebugDelegateWrapper debugDelegateWrapper)
        {
            _debugDelegateWrapper = debugDelegateWrapper;
        }
    }
}