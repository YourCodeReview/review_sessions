using System.Linq;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using BotFramework.Extensions;
using BotFramework.Services.Factories;
using Serilog;
using Serilog.Core;
using Telegram.Bot.Types;

namespace BotFramework.Middleware
{
    public class CommandEndpoint : IEndpoint
    {
        public EndpointPriority Priority { get; private set; }
        public Task             Action   => _command.Execute(_client);

        private readonly IClient  _client;
        private          ICommand _command;

        public CommandEndpoint(IClient client)
        {
            _client = client;
        }

        public void Initlialize(ICommand command, EndpointPriority priority)
        {
            Priority = priority;
            _command = command;
        }
    }


    public class SuitableMiddleware
    {
        private readonly UpdateDelegate _next;

        public SuitableMiddleware(UpdateDelegate next)
        {
            _next = next;
        }

        public Task Invoke(Update           update,
                           UpdateContext    updateContext,
                           PossibleCommands possibleCommands,
                           EndpointFactory  endpointFactory,
                           ILogger?         logger = null)
        {
            logger ??= Logger.None;
            var commands = possibleCommands.Commands.OfType<IStaticCommand>().ToList();

            if (commands.FirstOrDefault(t => t.SuitableFirst(update)) is { } firstCommand)
            {
                logger.Debug("{Command} is first suitable to handle {User}'s request", firstCommand.GetType().Name,
                    update.GetUser());

                updateContext.Endpoints.Add(endpointFactory.CreateEndpoint(firstCommand, EndpointPriority.First));
            }

            if (commands.FirstOrDefault(t => t.SuitableLast(update)) is { } lastCommand)
            {
                logger.Debug("{Command} is last suitable to handle {User}'s request", lastCommand.GetType().Name,
                    update.GetUser());

                updateContext.Endpoints.Add(endpointFactory.CreateEndpoint(lastCommand, EndpointPriority.Last));
            }

            // else
            // {
            //     logger.Debug("No suitable command found for {User}", update.GetUser());
            // }


            return _next(update);
        }
    }
}