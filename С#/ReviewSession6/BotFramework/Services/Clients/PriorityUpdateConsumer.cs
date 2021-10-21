using System.Collections.Concurrent;
using System.Linq;
using BotFramework.Abstractions;
using BotFramework.Middleware;

namespace BotFramework.Services.Clients
{
    public class PriorityUpdateConsumer
    {
        private readonly ConcurrentBag<ICommandUpdateConsumer> consumers = new();

        public void Consume(UpdateContext          context,
                            ICommandUpdateConsumer client)
        {
            var commands = context.Endpoints;
            if (CheckPriority(EndpointPriority.First))
            {
                return;
            }

            if (consumers.FirstOrDefault(a => !a.IsDone) is { } consumer)
            {
                consumer.Consume(context.Update);
            }

            if (CheckPriority(EndpointPriority.Last))
            {
                return;
            }

            bool CheckPriority(EndpointPriority priority)
            {
                if (commands.FirstOrDefault(a => a?.Priority == priority) is not { } endpoint)
                {
                    return false;
                }

                client.Initialize(endpoint.Action);
                client.Consume(context.Update);

                consumers.Add(client);
                return true;
            }
        }
    }
}