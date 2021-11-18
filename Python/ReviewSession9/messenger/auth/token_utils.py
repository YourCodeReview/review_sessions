import logging
from datetime import datetime

import bcrypt
import jwt
from pytz import utc

AUTH_JWT_SECRET = "yJzb21lIjoicGF5bG9hZCJ9.WTzLz"
AUTH_JWT_ALGORITHM = "HS512"
logger = logging.getLogger(__name__)


def encode_token(user_name, user_id):
    return jwt.encode(
        {
            "user_name": user_name,
            "user_id": str(user_id),
            "iat": datetime.now(tz=utc),
            },
        key=AUTH_JWT_SECRET,
        algorithm=AUTH_JWT_ALGORITHM,
        )


def decode_token(token):
    try:
        data = jwt.decode(token, key=AUTH_JWT_SECRET,
                          algorithms=[AUTH_JWT_ALGORITHM])
    except jwt.PyJWTError as e:
        logger.info("Invalid token '%s' (%s)", token, str(e))
        return None, None
    else:
        return data["user_name"], data["user_id"]


def generate_hash(password: str) -> str:
    if password:
        salt = bcrypt.gensalt()
        return bcrypt.hashpw(password.encode(), salt).decode()


def is_password_right(password: str, password_hash: str):
    return bcrypt.checkpw(password.encode(), password_hash.encode())
