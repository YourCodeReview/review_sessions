using System;
using BotFramework.Abstractions;
using BotFramework.Middleware;

namespace BotFramework.Services.Factories
{
    public class EndpointFactory
    {
        private readonly IServiceProvider _provider;

        public EndpointFactory(IServiceProvider provider)
        {
            _provider = provider;
        }

        public IEndpoint CreateEndpoint(ICommand command, EndpointPriority priority)
        {
            var newCommand = (IStaticCommand)_provider.GetService(command.GetType())!;
            var endpoint   = (CommandEndpoint)_provider.GetService(typeof(CommandEndpoint))!;
            endpoint.Initlialize(newCommand, priority);
            return endpoint;
        }
    }
}