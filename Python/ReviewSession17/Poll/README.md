# Backend for test task

## Configuration
Configuration is stored in `.env`, for examples see `.env.examples`

## Installing on a local machine
* This project requires python3.9 and sqlite or PostgreSQL(with Docker).
* Create and activate your virtual environment
* Docs(swagger) on http://127.0.0.1:8000/docs/ # local environment


Install requirements:

```sh
pip install -r requirements.txt
cp .env.example .env  # default environment variables
```

```sh
./manage.py migrate
./manage.py createsuperuser
```
Development servers:

```bash
# run django dev server
$ ./manage.py runserver


```
Or install Docker, Docker-Compose and run this command:
```bash
 docker-compose up --build -d
```
Makefile short commands for docker
```bash
up:
	docker-compose up --build -d
logs:
	docker logs --follow --timestamps poll_web
exec:
	docker exec -it poll_web bash
migrations:
	docker exec -it poll_web python manage.py makemigrations
migrate:
	docker exec -it poll_web python manage.py migrate
superuser:
	docker exec -it poll_web python manage.py createsuperuser
restart:
	docker restart poll_web
```

Example:
```bash
make up 
```
