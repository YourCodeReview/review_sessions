using System;
using System.Diagnostics.CodeAnalysis;
using System.Linq;
using System.Linq.Expressions;
using System.Reflection;
using System.Threading.Tasks;
using BotFramework.Abstractions;
using Microsoft.Extensions.DependencyInjection;
using Telegram.Bot.Types;

namespace BotFramework.Extensions.Hosting
{
    public static class UseMiddlewareExtensions
    {
        internal const string InvokeMethodName      = "Invoke";
        internal const string InvokeAsyncMethodName = "InvokeAsync";

        private static readonly MethodInfo GetServiceInfo =
        typeof(UseMiddlewareExtensions).GetMethod(nameof(GetService), BindingFlags.NonPublic | BindingFlags.Static)!;

        // We're going to keep all public constructors and public methods on middleware
        private const DynamicallyAccessedMemberTypes MiddlewareAccessibility =
        DynamicallyAccessedMemberTypes.PublicConstructors | DynamicallyAccessedMemberTypes.PublicMethods;

        /// <summary>
        /// Adds a middleware type to the application's request pipeline.
        /// </summary>
        /// <typeparam name="TMiddleware">The middleware type.</typeparam>
        /// <param name="app">The <see cref="IAppBuilder"/> instance.</param>
        /// <param name="args">The arguments to pass to the middleware type instance's constructor.</param>
        /// <returns>The <see cref="IAppBuilder"/> instance.</returns>
        public static IAppBuilder UseMiddleware<[DynamicallyAccessedMembers(MiddlewareAccessibility)] TMiddleware>(
            this IAppBuilder app, params object[] args)
        {
            return app.UseMiddleware(typeof(TMiddleware), args);
        }

        /// <summary>
        /// Adds a middleware type to the application's request pipeline.
        /// </summary>
        /// <param name="app">The <see cref="IAppBuilder"/> instance.</param>
        /// <param name="middleware">The middleware type.</param>
        /// <param name="args">The arguments to pass to the middleware type instance's constructor.</param>
        /// <returns>The <see cref="IAppBuilder"/> instance.</returns>
        public static IAppBuilder UseMiddleware(this IAppBuilder app,
                                                [DynamicallyAccessedMembers(MiddlewareAccessibility)]
                                                Type middleware,
                                                params object[] args)
        {
            return app.Use(applicationServices => next =>
            {
                var methods = middleware.GetMethods(BindingFlags.Instance | BindingFlags.Public)
                                        .Concat(middleware.GetMethods(BindingFlags.Instance | BindingFlags.NonPublic));
                var invokeMethods = methods.Where(m =>
                                           string.Equals(m.Name,    InvokeMethodName,      StringComparison.Ordinal)
                                           || string.Equals(m.Name, InvokeAsyncMethodName, StringComparison.Ordinal)
                                           )
                                           .ToArray();

                if (invokeMethods.Length > 1)
                {
                    throw new InvalidOperationException(
                        Resources.FormatException_UseMiddleMutlipleInvokes(InvokeMethodName, InvokeAsyncMethodName));
                }

                if (invokeMethods.Length == 0)
                {
                    throw new InvalidOperationException(
                        Resources.FormatException_UseMiddlewareNoInvokeMethod(InvokeMethodName, InvokeAsyncMethodName,
                            middleware));
                }

                var methodInfo = invokeMethods[0];
                if (!typeof(Task).IsAssignableFrom(methodInfo.ReturnType))
                {
                    throw new InvalidOperationException(
                        Resources.FormatException_UseMiddlewareNonTaskReturnType(InvokeMethodName, InvokeAsyncMethodName,
                            nameof(Task)));
                }

                var parameters = methodInfo.GetParameters();
                if (parameters.Length == 0 || parameters[0].ParameterType != typeof(Update))
                {
                    throw new InvalidOperationException(
                        Resources.FormatException_UseMiddlewareNoParameters(InvokeMethodName, InvokeAsyncMethodName,
                            nameof(Update)));
                }

                var ctorArgs = new object[args.Length + 1];
                ctorArgs[0] = next;
                Array.Copy(args, 0, ctorArgs, 1, args.Length);
                var instance = ActivatorUtilities.CreateInstance(applicationServices, middleware, ctorArgs);
                if (parameters.Length == 1)
                {
                    return (UpdateDelegate)methodInfo.CreateDelegate(typeof(UpdateDelegate), instance);
                }

                var factory = Compile<object>(methodInfo, parameters);

                return context =>
                {
                    if (applicationServices == null)
                    {
                        throw new InvalidOperationException(
                            Resources.FormatException_UseMiddlewareIServiceProviderNotAvailable(nameof(IServiceProvider)));
                    }

                    return factory(instance, context, applicationServices);
                };
            });
        }

        private static Func<T, Update, IServiceProvider, Task> Compile<T>(MethodInfo methodInfo, ParameterInfo[] parameters)
        {
            // If we call something like
            //
            // public class Middleware
            // {
            //    public Task Invoke(HttpContext context, ILoggerFactory loggerFactory)
            //    {
            //
            //    }
            // }
            //

            // We'll end up with something like this:
            //   Generic version:
            //
            //   Task Invoke(Middleware instance, HttpContext httpContext, IServiceProvider provider)
            //   {
            //      return instance.Invoke(httpContext, (ILoggerFactory)UseMiddlewareExtensions.GetService(provider, typeof(ILoggerFactory));
            //   }

            //   Non generic version:
            //
            //   Task Invoke(object instance, HttpContext httpContext, IServiceProvider provider)
            //   {
            //      return ((Middleware)instance).Invoke(httpContext, (ILoggerFactory)UseMiddlewareExtensions.GetService(provider, typeof(ILoggerFactory));
            //   }

            var middleware = typeof(T);

            var httpContextArg = Expression.Parameter(typeof(Update),           "httpContext");
            var providerArg    = Expression.Parameter(typeof(IServiceProvider), "serviceProvider");
            var instanceArg    = Expression.Parameter(middleware,               "middleware");

            var methodArguments = new Expression[parameters.Length];
            methodArguments[0] = httpContextArg;
            for (int i = 1; i < parameters.Length; i++)
            {
                var parameterType = parameters[i].ParameterType;
                if (parameterType.IsByRef)
                {
                    throw new NotSupportedException(
                        Resources.FormatException_InvokeDoesNotSupportRefOrOutParams(InvokeMethodName));
                }

                var parameterTypeExpression = new Expression[]
                {
                    providerArg,
                    Expression.Constant(parameterType,            typeof(Type)),
                    Expression.Constant(methodInfo.DeclaringType, typeof(Type))
                };

                var getServiceCall = Expression.Call(GetServiceInfo, parameterTypeExpression);
                methodArguments[i] = Expression.Convert(getServiceCall, parameterType);
            }

            Expression middlewareInstanceArg = instanceArg;
            if (methodInfo.DeclaringType != null && methodInfo.DeclaringType != typeof(T))
            {
                middlewareInstanceArg = Expression.Convert(middlewareInstanceArg, methodInfo.DeclaringType);
            }

            var body = Expression.Call(middlewareInstanceArg, methodInfo, methodArguments);

            var lambda = Expression.Lambda<Func<T, Update, IServiceProvider, Task>>(body, instanceArg, httpContextArg,
                providerArg);

            return lambda.Compile();
        }

        private static object GetService(IServiceProvider sp, Type type, Type middleware)
        {
            var service = sp.GetService(type);
            if (service == null)
            {
                throw new InvalidOperationException(Resources.FormatException_InvokeMiddlewareNoService(type, middleware));
            }

            return service;
        }
    }
}