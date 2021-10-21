using BotFramework.Abstractions;
using Microsoft.Extensions.Hosting;

namespace BotFramework
{
    public interface IStartup
    {
        public void Configure(IAppBuilder app, HostBuilderContext context);
        public bool isTesting { get; }
    }
}