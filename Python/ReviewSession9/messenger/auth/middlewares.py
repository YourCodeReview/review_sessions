from aiohttp.web_middlewares import middleware

from messenger.api.auth.token_utils import decode_token


@middleware
async def auth_middleware(request, handler):
    """
    confirms that token is valid and adds fields
    request.username and request.user_id
    """
    auth_header = request.headers.get("Authorization", "")
    token = auth_header.split(" ")[-1]
    user_name, user_id = decode_token(token)
    request.user_name = user_name
    request.user_id = user_id
    return await handler(request)


@middleware
async def dumb_auth_middleware(request, handler):
    """
    used instead of (auth_middleware, permissions_middleware) when
    settings.AUTH_REQUIRED = False

    adds fields request.username and request.user_id from headers
    "X-User-Id", "X"-Username"
    """
    request.username = request.headers.get("X-Username")
    return await handler(request)
