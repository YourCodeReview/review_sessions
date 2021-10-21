using System;
using BotFramework.Abstractions;
using BotFramework.HostServices;
using BotFramework.Middleware;
using BotFramework.Services.Factories;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Serilog;
using Telegram.Bot;
using Telegram.Bot.Types;

namespace BotFramework.Extensions.Hosting
{
    public static class BotFrameworkExtensions
    {
        internal const string AppKostyl = "UniqueAppKostyl";

        public static IHostBuilder UseSimpleBotFramework(this IHostBuilder builder, bool isTesting = false)
        {
            return UseSimpleBotFramework(builder, (_, _) => { }, isTesting);
        }

        public static IHostBuilder UseSimpleBotFramework(this IHostBuilder                       builder,
                                                         Action<IAppBuilder, HostBuilderContext> appConfigurator,
                                                         bool                                    isTesting = false)
        {
            return builder.UseBotFramework((app, context) =>
            {
                appConfigurator?.Invoke(app, context);

                if (!isTesting)
                {
                    app.Services.AddUpdateConsumer();
                    app.Services.AddTelegramClient(context.Configuration["BotToken"]);
                }
                else
                {
                    app.Services.AddDebugUpdateConsumer();
                }

                app.UseStaticCommands();

                app.Services.AddScoped<Update>(provider => provider.GetService<UpdateFactory>()!.CurrentUpdate);
                app.Services.AddScoped<UpdateFactory>();
                app.Services.AddScoped<EndpointFactory>();
                app.Services.AddScoped<UpdateContext>();
                app.Services.AddScoped<CommandEndpoint>();

                app.UseMiddleware<UpdateMiddleware>();
                app.UseMiddleware<LoggingMiddleware>();
                app.UseMiddleware<SuitableMiddleware>();
                app.UseMiddleware<EndpointMiddleware>();
            }, isTesting);
        }

        public static IHostBuilder UseBotFramework(this IHostBuilder                       builder,
                                                   Action<IAppBuilder, HostBuilderContext> appConfigurator,
                                                   bool                                    isTesting = false)
        {
            builder
            .UseServiceProviderFactory(context => new AppBuilderFactory(context))
            .ConfigureContainer<AppBuilder>((context, appBuilder) =>
            {
                appConfigurator(appBuilder, context);

                appBuilder.Services.AddHostedService(provider =>
                new AppRunnerService((UpdateDelegate)context.Properties[AppKostyl],
                    provider.GetService<ITelegramBotClient>()!, provider.GetService<ILogger>()!));

                if (isTesting)
                {
                    appBuilder.Services.AddSingleton(_ =>
                    new DebugDelegateWrapper((UpdateDelegate)context.Properties[AppKostyl]));
                }
            })
            .UseConsoleLifetime();

            return builder;
        }

        public static IHostBuilder UseBotFrameworkStartup<T>(this IHostBuilder builder, T startup) where T : IStartup
        {
            return builder.UseBotFramework(startup.Configure, startup.isTesting);
        }

        public static IHostBuilder UseBotFrameworkStartup<T>(this IHostBuilder builder) where T : IStartup, new()
        {
            var startup = new T();
            return builder.UseBotFramework(startup.Configure, startup.isTesting);
        }

        public record DebugDelegateWrapper(UpdateDelegate App);
    }
}