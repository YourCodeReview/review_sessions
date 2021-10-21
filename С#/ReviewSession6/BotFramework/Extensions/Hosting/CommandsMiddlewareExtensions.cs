using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using BotFramework.Abstractions;
using BotFramework.Middleware;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using Serilog;

namespace BotFramework.Extensions.Hosting
{
    public static class CommandsMiddlewareExtensions
    {
        public static void UsePossibleCommands(this IAppBuilder builder)
        {
            builder.Services.AddScoped<PossibleCommands>();
        }

        public static void UseStaticCommands(this IAppBuilder builder, StaticCommandsList staticCommands)
        {
            builder.UsePossibleCommands();
            builder.Services.TryAddSingleton(provider =>
            {
                ServiceProviderServiceExtensions.GetService<ILogger>(provider)
                                                ?.Debug("Loaded {Count} static commands: {Commands}",
                                                    staticCommands.StaticCommandsTypes.Count,
                                                    string.Join(", ", staticCommands.StaticCommandsTypes.Select(a => a.Name)));

                return staticCommands;
            });
            builder.UseMiddleware<StaticCommandsMiddleware>();

            foreach (var command in staticCommands.StaticCommandsTypes)
            {
                builder.Services.AddScoped(command);
            }
        }

        public static void UseStaticCommandsAssembly(this IAppBuilder builder, Assembly assembly)
        {
            builder.UseStaticCommands(GetStaticCommands(new[] { assembly }));
        }

        public static void UseStaticCommands(this IAppBuilder builder)
        {
            if (builder.Services.Any(x => x.ServiceType == typeof(StaticCommandsList)))
            {
                return;
            }

            builder.UseStaticCommands(GetStaticCommands(GetAssemblies()));
        }

        // this might not be perfect solution because it loads many assemblies 
        // we need to investigate it later
        internal static Assembly[] GetAssemblies()
        {
            var assemblies = AppDomain.CurrentDomain.GetAssemblies()
                                      .Where(a => !a.FullName.Contains("Microsoft")
                                                  && !a.FullName.Contains("System"))
                                      .ToList();

            var referenced = assemblies.SelectMany(a => a.GetReferencedAssemblies())
                                       .Where(a => !a.FullName.Contains("Microsoft")
                                                   && !a.FullName.Contains("System"))
                                       .Select(Assembly.Load);


            return assemblies.Concat(referenced).ToArray();
        }

        public static StaticCommandsList GetStaticCommands(IEnumerable<Assembly> assemblies)
        {
            var allTypes = assemblies.SelectMany(a => a.GetTypes());
            var res = allTypes.Where(p => typeof(ICommand).IsAssignableFrom(p) && !p.IsAbstract)
                              .ToList();
            return new(res);
        }
    }
}