from aiohttp import web
from aiohttp.web_exceptions import HTTPForbidden

from messenger.api.auth.schema import (
    GetTokenRequest, GetTokenResponse,
    RegisterRequest, RegisterResponse,
    )
from messenger.api.auth.token_utils import encode_token, is_password_right
from messenger.api.handlers import ROOT_URL
from messenger.api.services.users import create_user, get_user

routes = web.RouteTableDef()


@routes.post(ROOT_URL + r"/auth/get-token", name="get-token")
async def get_token_handler(request):
    payload = await request.json()
    validated_payload = GetTokenRequest(**payload)
    async with request.app["engine"].connect() as conn:
        user = await get_user(name=validated_payload.name, conn=conn)
    if not is_password_right(validated_payload.password, user.password_hash):
        raise HTTPForbidden
    token = encode_token(user.name, user.id)
    return web.json_response(GetTokenResponse(token=token).dict(), status=200)


@routes.post(ROOT_URL + r"/auth/register", name="register")
async def register_handler(request):
    payload = await request.json()
    validated_payload = RegisterRequest(**payload)
    async with request.app["engine"].connect() as conn:
        user_id = await create_user(**validated_payload.dict(), conn=conn)
    return web.json_response(RegisterResponse(id=str(user_id)).dict(),
                             status=201)
