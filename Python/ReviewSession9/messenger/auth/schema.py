from pydantic import BaseModel


class GetTokenRequest(BaseModel):
    name: str
    password: str


class GetTokenResponse(BaseModel):
    token: str


class RegisterRequest(BaseModel):
    name: str
    password: str


class RegisterResponse(BaseModel):
    id: str
