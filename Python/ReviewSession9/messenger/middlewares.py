import logging
import time
from json import JSONDecodeError
from uuid import uuid4

from aiohttp import web
from aiohttp.web_exceptions import HTTPError
from aiohttp.web_middlewares import middleware
from pydantic import ValidationError

import messenger.db.utils
from messenger.api.exceptions import ChatException, DbError, NotFoundException

logger = logging.getLogger(__name__)


@middleware
async def add_request_id_middleware(request, handler):
    request.id = uuid4()
    text = await request.text()
    logger.debug("request (url %s) with body: %s", request.url,
                 text)
    result = await handler(request)
    logger.debug("response: %s", result.body)
    return result


@middleware
async def error_handling_middleware(request, handler):
    try:
        response = await handler(request)
    except (ValidationError, JSONDecodeError):
        response = web.json_response({"message": "bad-parameters"}, status=400)
    except NotFoundException as e:
        response = web.json_response(
            {"message": f"{e.item}-not-found"}, status=e.status_code)
    except (HTTPError, ChatException) as e:
        response = web.json_response({"message": str(e)},
                                     status=e.status_code)

        logger.warning("Got Chat or HTTP error %s. Returned %s response: %s",
                       str(e), response.status, response.text, exc_info=True)
    except Exception as e:
        response = web.json_response({"message": e.__class__.__name__},
                                     status=500)
        logger.error("Got unexpected error %s. Returned %s response: %s",
                     str(e), response.status, response.text, exc_info=True)
    return response


@middleware
async def db_check_middleware(request, handler):
    request.IS_DB_OK, request.DB_ERROR_MESSAGE = \
        await messenger.db.utils.check_db(request.app["engine"])
    if not request.IS_DB_OK:
        logger.warning("DB is not available. request id: %s", request.id)
    return await handler(request)


@middleware
async def db_reconnect_middleware(request, handler):
    if not request.IS_DB_OK:
        try:
            await messenger.db.utils.reconnect_to_db(request.app)
            is_ok, message = \
                await messenger.db.utils.check_db(request.app["engine"])
            if not is_ok:
                raise DbError(message)
            logger.info("successfully reconnected to db!")
        except Exception:
            logger.error("couldn't reconnect to db", exc_info=True)
    return await handler(request)


@middleware
async def db_fail_middleware(request, handler):
    if request.IS_DB_OK:
        return await handler(request)
    raise DbError(request.DB_ERROR_MESSAGE)


@middleware
async def timing_middleware(request, handler):
    start = time.time()
    response = await handler(request)
    finish = time.time()
    logger.info("Request [id %s] finished in %s seconds", request.id,
                finish - start)
    return response
