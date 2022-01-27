FROM python:3.9.4

LABEL maintainer="minovaziz@gmail.com"

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1
ENV STATIC_ROOT /static

WORKDIR /app
ADD requirements.txt /app/requirements.txt

RUN pip install -r requirements.txt

ADD ../../../../review_session_repos/17/анон/poll-master /app

CMD python manage.py collectstatic --no-input && gunicorn -b 0.0.0.0:8000 --log-level info --reload -w 9 project.wsgi:application
