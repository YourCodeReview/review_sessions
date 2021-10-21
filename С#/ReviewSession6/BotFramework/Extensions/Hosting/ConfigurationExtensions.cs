using System;
using System.IO;
using System.Reflection;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Hosting;

namespace BotFramework.Extensions.Hosting
{
    public static class ConfigurationExtensions
    {
        public static IHostBuilder UseConfigurationWithEnvironment(this IHostBuilder hostBuilder)
        {
            return hostBuilder
                   .ConfigureHostConfiguration(builder => builder.AddEnvironmentVariables())
                   .ConfigureAppConfiguration((hostingContext, config) =>
                   {
                       var env            = hostingContext.HostingEnvironment;
                       var assemblyFolder = Path.GetDirectoryName(Assembly.GetExecutingAssembly().Location);
                       if (assemblyFolder is null)
                       {
                           throw new ArgumentNullException(nameof(assemblyFolder));
                       }

                       config.AddJsonFile($"{assemblyFolder}/appsettings.json", false, true)
                             .AddJsonFile($"{assemblyFolder}/appsettings.{env.EnvironmentName}.json", true, true)
                             .Build();
                   });
        }
    }
}