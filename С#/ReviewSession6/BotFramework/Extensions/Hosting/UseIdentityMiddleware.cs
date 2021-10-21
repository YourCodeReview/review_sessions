using BotFramework.Abstractions;
using BotFramework.Middleware;
using Microsoft.Extensions.DependencyInjection;

namespace BotFramework.Extensions.Hosting
{
    public static class UseIdentityMiddleware
    {
        public static void UseIdentity<TUser>(this IAppBuilder builder) where TUser : class
        {
            builder.Services.AddScoped<UserContext<TUser>>();
            builder.Services.AddScoped(provider => provider.GetService<UserContext<TUser>>()?.User!);
            builder.UseMiddleware<IdentityMiddleware<TUser>>();
        }
    }
}