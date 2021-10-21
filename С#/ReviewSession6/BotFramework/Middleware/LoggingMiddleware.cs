using System.Threading.Tasks;
using BotFramework.Abstractions;
using BotFramework.Extensions;
using Serilog;
using Telegram.Bot.Types;

namespace BotFramework.Middleware
{
    /// <summary>
    /// Middleware that logs all incoming messages from users.
    /// </summary>
    public class LoggingMiddleware
    {
        private readonly UpdateDelegate _next;
        private readonly ILogger        _logger;

        public LoggingMiddleware(UpdateDelegate next, ILogger logger)
        {
            _next   = next;
            _logger = logger;
        }

        public Task Invoke(Update update)
        {
            var info = update.GetInfoFromUpdate();

            _logger.Information("{UpdateType} {MessageType} | {From}: {Contents}",
                info.UpdateType,
                info.MessageType,
                info.From,
                info.Contents);

            return _next.Invoke(update);
        }
    }
}