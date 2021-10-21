using BotFramework.Abstractions;
using BotFramework.Services.Clients;
using Microsoft.Extensions.DependencyInjection;
using Telegram.Bot;

namespace BotFramework.Extensions.Hosting
{
    public static class ConsumerExtensions
    {
        public static IServiceCollection AddUpdateConsumer(this IServiceCollection services)
        {
            services.AddScoped<ICommandUpdateConsumer, CommandUpdateConsumer>();
            services.AddSingleton<IRequestSinc, TelegramSink>();
            services.AddScoped<IClient, Client>();
            return services;
        }

        public static IServiceCollection AddDebugUpdateConsumer(this IServiceCollection services)
        {
            services.AddScoped<ICommandUpdateConsumer, CommandUpdateConsumer>();
            services.AddSingleton<AppUpdateProducer>();
            services.AddSingleton<IRequestSinc, MemorySink>();
            services.AddScoped<IClient, Client>();
            return services;
        }

        public static IServiceCollection AddTelegramClient(this IServiceCollection services, string token)
        {
            services.AddSingleton<ITelegramBotClient>(new TelegramBotClient(token));
            return services;
        }
    }
}