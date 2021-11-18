from functools import wraps

from aiohttp.web_exceptions import HTTPForbidden

import messenger
from messenger.api import tasks
from messenger.api.services.users_chats_links import is_in_chat


async def is_authorized(request):
    """
    confirms if user is not None
    """
    return getattr(request, "user_name") and getattr(request, "user_id")


async def is_chat_member(request):
    """
    confirms if user is in chat
    """
    if await is_authorized(request):
        chat_id = request.match_info.get("chat_id")
        if not chat_id:
            raise ValueError("Couldn't parse chat_id from url path params")
        return await is_in_chat(request.user_id, chat_id,
                                request.app["engine"])


async def is_task_owner(request):
    """
    confirms if request.user is owner of task
    """
    if await is_authorized(request):
        task_id = request.match_info["task_id"]
        return await tasks.is_task_owner(request.user_id, task_id,
                                         request.app["engine"])


class protected:  # noqa
    def __init__(self, *permissions):
        self.permissions = permissions

    def __call__(self, handler):
        @wraps(handler)
        async def wrapper(request):
            is_allowed = all([await p(request) for p in self.permissions])
            if is_allowed or messenger.settings.AUTH_DISABLED:
                return await handler(request)
            else:
                raise HTTPForbidden

        return wrapper
