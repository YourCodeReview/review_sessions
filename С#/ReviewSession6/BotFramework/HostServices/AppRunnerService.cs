using System.Threading;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using Microsoft.Extensions.Hosting;
using Serilog;
using Serilog.Core;
using Telegram.Bot;

namespace BotFramework.HostServices
{
    // todo handle obsolete update handling
    public class AppRunnerService : IHostedService
    {
        private readonly ITelegramBotClient _client;
        private readonly ILogger            _logger;

        public AppRunnerService(UpdateDelegate app, ITelegramBotClient client, ILogger logger)
        {
            _client          =  client;
            _logger          =  logger;
            _client.OnUpdate += (sender, args) => app(args.Update);
        }

        public AppRunnerService(UpdateDelegate app, ITelegramBotClient client) : this(app, client, Logger.None) { }

        public async Task StartAsync(CancellationToken cancellationToken)
        {
            var me = await _client.GetMeAsync(cancellationToken);
            _logger.Information("Started bot @{UserName}", me.Username);
            _client.StartReceiving(cancellationToken: cancellationToken);
        }

        public Task StopAsync(CancellationToken cancellationToken)
        {
            _client.StopReceiving();
            return Task.CompletedTask;
        }
    }
}