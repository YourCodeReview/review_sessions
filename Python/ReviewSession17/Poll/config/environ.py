"""Read .env file"""
import environ
import os.path

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

env = environ.Env(
    DEBUG=(bool, False),
)

environ.Env.read_env(os.path.join(BASE_DIR, '.env'))                  # reading .env file

__all__ = [
    env,
]
