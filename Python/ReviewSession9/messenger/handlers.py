from aiohttp import web

from messenger.api.auth.permissions import (
    is_authorized, is_chat_member, is_task_owner,
    protected,
    )
from messenger.api.schemas.requests import (
    ChatCreateRequest, ChatGetMessagesRequest,
    ChatJoinRequest, ChatSendMessageRequest, SearchQuery, TaskResultRequest,
    )
from messenger.api.schemas.responses import (
    ChatCreateResponse, ChatGetMessagesResponse,
    ChatJoinResponse, ChatSendMessageResponse, GetHistoryResponse,
    GetTaskResponse,
    )
from messenger.api.services.chats import create_chat
from messenger.api.services.messages.general import (
    get_messages,
    save_message, serialize_messages,
    )
from messenger.api.services.messages.search_messages import (
    get_search_result,
    save_search_result,
    )
from messenger.api.services.users_chats_links import add_to_chat
from messenger.api.tasks import Task, get_task_status
from messenger.settings import ROOT_URL

routes = web.RouteTableDef()


@routes.get(r"/ping_db", name="ping_db")
async def ping_db_handler(request):
    return web.json_response({"message": "ok"}, status=200)


@routes.post(ROOT_URL, name="root")
@protected(is_authorized)
async def create_chat_handler(request):
    payload = await request.json()
    validated_request = ChatCreateRequest(**payload)
    chat_id = await create_chat(**validated_request.dict(),
                                engine=request.app["engine"],
                                user_id=request.user_id)
    return web.json_response(ChatCreateResponse(chat_id=str(chat_id)).dict(),
                             status=201)


@routes.post(ROOT_URL + r"/{chat_id}/users", name="chat-join")
@protected(is_chat_member)
async def chat_join_handler(request):
    payload = await request.json()
    validated_payload = ChatJoinRequest(chat_id=request.match_info["chat_id"],
                                        **payload)
    user_id = await add_to_chat(**validated_payload.dict(),
                                engine=request.app["engine"])
    return web.json_response(ChatJoinResponse(user_id=user_id).dict(),
                             status=201)


@routes.get(ROOT_URL + r"/{chat_id}/messages", name="messages")
@protected(is_chat_member)
async def chat_get_messages(request):
    chat_id = request.match_info["chat_id"]
    validated_payload = ChatGetMessagesRequest(chat_id=chat_id,
                                               **request.query)
    messages, cursor = await get_messages(**validated_payload.dict(),
                                          engine=request.app["engine"])
    return web.json_response(
        ChatGetMessagesResponse(messages=messages, next=cursor).dict())


@routes.post(ROOT_URL + r"/{chat_id}/messages", name="messages")
@protected(is_chat_member)
async def chat_send_messages(request):
    chat_id = request.match_info["chat_id"]
    payload = await request.json()
    validated_payload = ChatSendMessageRequest(chat_id=chat_id,
                                               **payload)
    message_id = await save_message(**validated_payload.dict(),
                                    user_id=request.user_id,
                                    engine=request.app["engine"],
                                    )
    return web.json_response(
        ChatSendMessageResponse(message_id=message_id).dict(),
        status=201
        )


@routes.post(ROOT_URL + r"/search", name="search")
@protected(is_authorized)
async def search_handler(request):
    payload = await request.json()
    validated_payload = SearchQuery(**payload)
    task = await Task.create(save_search_result, engine=request.app["engine"],
                             user_id=request.user_id)
    await task.defer(validated_payload.message, task.id, request.user_id,
                     request.app["engine"])
    return web.json_response(
        GetHistoryResponse(task_id=str(task.id)).dict(),
        status=201
        )


@routes.get(ROOT_URL + r"/search/status/{task_id}", name="task_status")
@protected(is_task_owner)
async def task_status_handler(request):
    task_id = request.match_info["task_id"]
    status = await get_task_status(task_id, request.app["engine"])
    return web.json_response(GetTaskResponse(status=status.value).dict(),
                             status=200)


@routes.get(ROOT_URL + r"/search/{task_id}/messages", name="task_result")
@protected(is_task_owner)
async def task_result_handler(request):
    task_id = request.match_info["task_id"]
    validated_payload = TaskResultRequest(task_id=task_id, **request.query)
    messages_data = await get_search_result(**validated_payload.dict(),
                                            engine=request.app["engine"])
    messages, cursor = serialize_messages(messages_data)
    return web.json_response(
        ChatGetMessagesResponse(messages=messages, next=cursor).dict(),
        status=200)
