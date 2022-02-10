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