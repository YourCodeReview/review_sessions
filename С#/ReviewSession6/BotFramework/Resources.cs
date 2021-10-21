using System.Globalization;

namespace BotFramework
{
    internal static class Resources
    {
        internal static CultureInfo Culture { get; } = CultureInfo.CurrentCulture;

        /// <summary>'{0}' is not available.</summary>
        internal static string @Exception_UseMiddlewareIServiceProviderNotAvailable => "'{0}' is not available.";


        /// <summary>'{0}' is not available.</summary>
        internal static string FormatException_UseMiddlewareIServiceProviderNotAvailable(object p0) =>
        string.Format(Culture, @Exception_UseMiddlewareIServiceProviderNotAvailable, p0);

        /// <summary>No public '{0}' or '{1}' method found for middleware of type '{2}'.</summary>
        internal static string @Exception_UseMiddlewareNoInvokeMethod =>
        "No public '{0}' or '{1}' method found for middleware of type '{2}'.";

        /// <summary>No public '{0}' or '{1}' method found for middleware of type '{2}'.</summary>
        internal static string FormatException_UseMiddlewareNoInvokeMethod(object p0, object p1, object p2)
            => string.Format(Culture, Exception_UseMiddlewareNoInvokeMethod, p0, p1, p2);

        /// <summary>'{0}' or '{1}' does not return an object of type '{2}'.</summary>
        internal static string @Exception_UseMiddlewareNonTaskReturnType =>
        "'{0}' or '{1}' does not return an object of type '{2}'.";

        /// <summary>'{0}' or '{1}' does not return an object of type '{2}'.</summary>
        internal static string FormatException_UseMiddlewareNonTaskReturnType(object p0, object p1, object p2) =>
        string.Format(Culture, @Exception_UseMiddlewareNonTaskReturnType, p0, p1, p2);

        /// <summary>The '{0}' or '{1}' method's first argument must be of type '{2}'.</summary>
        internal static string @Exception_UseMiddlewareNoParameters =>
        "The '{0}' or '{1}' method's first argument must be of type '{2}'.";

        /// <summary>The '{0}' or '{1}' method's first argument must be of type '{2}'.</summary>
        internal static string FormatException_UseMiddlewareNoParameters(object p0, object p1, object p2)
            => string.Format(Culture, Exception_UseMiddlewareNoParameters, p0, p1, p2);

        /// <summary>Multiple public '{0}' or '{1}' methods are available.</summary>
        internal static string @Exception_UseMiddleMutlipleInvokes => "Multiple public '{0}' or '{1}' methods are available.";

        /// <summary>Multiple public '{0}' or '{1}' methods are available.</summary>
        internal static string FormatException_UseMiddleMutlipleInvokes(object p0, object p1)
            => string.Format(Culture, Exception_UseMiddleMutlipleInvokes, p0, p1);

        /// <summary>The path in '{0}' must start with '/'.</summary>
        internal static string @Exception_PathMustStartWithSlash => "The path in '{0}' must start with '/'.";

        /// <summary>The path in '{0}' must start with '/'.</summary>
        internal static string FormatException_PathMustStartWithSlash(object p0)
            => string.Format(Culture, Exception_PathMustStartWithSlash, p0);

        /// <summary>Unable to resolve service for type '{0}' while attempting to Invoke middleware '{1}'.</summary>
        internal static string @Exception_InvokeMiddlewareNoService =>
        "Unable to resolve service for type '{0}' while attempting to Invoke middleware '{1}'.";

        /// <summary>Unable to resolve service for type '{0}' while attempting to Invoke middleware '{1}'.</summary>
        internal static string FormatException_InvokeMiddlewareNoService(object p0, object p1)
            => string.Format(Culture, Exception_InvokeMiddlewareNoService, p0, p1);

        /// <summary>The '{0}' method must not have ref or out parameters.</summary>
        internal static string @Exception_InvokeDoesNotSupportRefOrOutParams =>
        "The '{0}' method must not have ref or out parameters.";

        /// <summary>The '{0}' method must not have ref or out parameters.</summary>
        internal static string FormatException_InvokeDoesNotSupportRefOrOutParams(object p0)
            => string.Format(Culture, Exception_InvokeDoesNotSupportRefOrOutParams, p0);

        /// <summary>The value must be greater than zero.</summary>
        internal static string @Exception_PortMustBeGreaterThanZero => "The value must be greater than zero.";

        /// <summary>No service for type '{0}' has been registered.</summary>
        internal static string @Exception_UseMiddlewareNoMiddlewareFactory =>
        "No service for type '{0}' has been registered.";

        /// <summary>No service for type '{0}' has been registered.</summary>
        internal static string FormatException_UseMiddlewareNoMiddlewareFactory(object p0)
            => string.Format(Culture, Exception_UseMiddlewareNoMiddlewareFactory, p0);

        /// <summary>'{0}' failed to create middleware of type '{1}'.</summary>
        internal static string @Exception_UseMiddlewareUnableToCreateMiddleware =>
        "'{0}' failed to create middleware of type '{1}'.";

        /// <summary>'{0}' failed to create middleware of type '{1}'.</summary>
        internal static string FormatException_UseMiddlewareUnableToCreateMiddleware(object p0, object p1)
            => string.Format(Culture, @Exception_UseMiddlewareUnableToCreateMiddleware, p0, p1);

        /// <summary>Types that implement '{0}' do not support explicit arguments.</summary>
        internal static string @Exception_UseMiddlewareExplicitArgumentsNotSupported =>
        "Types that implement '{0}' do not support explicit arguments.";

        /// <summary>Types that implement '{0}' do not support explicit arguments.</summary>
        internal static string FormatException_UseMiddlewareExplicitArgumentsNotSupported(object p0)
            => string.Format(Culture, Exception_UseMiddlewareExplicitArgumentsNotSupported, p0);

        /// <summary>Argument cannot be null or empty.</summary>
        internal static string @ArgumentCannotBeNullOrEmpty => "Argument cannot be null or empty.";

        /// <summary>An element with the key '{0}' already exists in the {1}.</summary>
        internal static string @RouteValueDictionary_DuplicateKey => "An element with the key '{0}' already exists in the {1}.";

        /// <summary>An element with the key '{0}' already exists in the {1}.</summary>
        internal static string FormatRouteValueDictionary_DuplicateKey(object p0, object p1)
            => string.Format(Culture, RouteValueDictionary_DuplicateKey, p0, p1);

        /// <summary>The type '{0}' defines properties '{1}' and '{2}' which differ only by casing. This is not supported by {3} which uses case-insensitive comparisons.</summary>
        internal static string @RouteValueDictionary_DuplicatePropertyName =>
        "The type '{0}' defines properties '{1}' and '{2}' which differ only by casing. This is not supported by {3} which uses case-insensitive comparisons.";

        /// <summary>The type '{0}' defines properties '{1}' and '{2}' which differ only by casing. This is not supported by {3} which uses case-insensitive comparisons.</summary>
        internal static string FormatRouteValueDictionary_DuplicatePropertyName(object p0, object p1, object p2, object p3)
            => string.Format(Culture, RouteValueDictionary_DuplicatePropertyName, p0, p1, p2, p3);
    }
}