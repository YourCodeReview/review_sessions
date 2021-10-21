using System;
using BotFramework.Extensions.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

namespace BotFramework.HostServices
{
    public class AppBuilderFactory : IServiceProviderFactory<AppBuilder>
    {
        private readonly HostBuilderContext _context;

        public AppBuilderFactory(HostBuilderContext context)
        {
            _context = context;
        }

        public AppBuilder CreateBuilder(IServiceCollection services)
        {
            return new(services);
        }

        public IServiceProvider CreateServiceProvider(AppBuilder containerBuilder)
        {
            var (services, app)                                   = containerBuilder.Build();
            _context.Properties[BotFrameworkExtensions.AppKostyl] = app;
            return services;
        }
    }
}