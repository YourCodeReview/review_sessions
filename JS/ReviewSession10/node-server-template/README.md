## 🪲 Node server template

![JavaScript](https://img.shields.io/badge/-JavaScript-24292F?style=for-the-badge&logo=JavaScript&logoColor=F7DF1E)
![Node.js](https://img.shields.io/badge/-Node.js-24292F?style=for-the-badge&logo=Node.js&logoColor=339933)
![Express](https://img.shields.io/badge/-Express-24292F?style=for-the-badge&logo=Express)
![Prisma](https://img.shields.io/badge/-Prisma-24292F?style=for-the-badge&logo=Prisma&logoColor=85EA2D)
![Jest](https://img.shields.io/badge/-Jest-24292F?style=for-the-badge&logo=Jest&logoColor=C21325)
![Swagger](https://img.shields.io/badge/-Swagger-24292F?style=for-the-badge&logo=Swagger&logoColor=85EA2D)

Простой сервер на ```Express``` с тестами на ```Jest``` 👋

<img src="docs/Preview.png" alt="Preview" />

Для примера реализован тестовый маршрут по адресу ```localhost/api/v1/test/item``` с уже готовой тестовой средой.
Документация находиться по маршруту ```localhost/docs```.

Файл ```/docs/test-api.paw``` необходим для удобства разработки через приложение ```🦊 Paw```

### 🛠 Конфигурация ```.env``` файла

```conf
MODE=dev
PORT=3000
DATABASE_URL="mysql://root:root@localhost:3306/prisma"
```
При ```dev``` моде - логирование ведётся в консоль, для ```prod``` в файл ```/logs/test.log``` по умолчанию.

Модуль логирования находится в ```/logs/logger.js```, в нем можно настроить вывод за основу взят  ```morgan```

### 🏁 Первый запуск

Создание базы данных
```console
$ npx prisma migrate dev --name init
```
Заполнение тестовыми данными (необязательно)
```console
$ npx prisma db seed
```
Создание документации
```console
$ npm run docs
```

### 🚀 Запуск сервера

```console
$ npm run dev
```

### 🧑‍💻 Запуск тестов

При запуске тестов автоматически задается ```MODE=test```

```console
$ npm run test-dev
$ npm run test
```

### 🗃 Работа с БД через ```Prisma```

Миграция из ```prisma/schema.prisma``` в базу данных
```console
$ npx prisma migrate dev --name init
```

Проанализировать свою базу данных и записать схему в ```prisma/schema.prisma```
```console
$ npx prisma db pull
// Обновить клиент для отправки запросов
$ prisma generate
```

Визуальный редактор данных в вашей базе данных
```console
$ npx prisma studio
```

Заполнить базу данных образцом данных ```prisma/seed.js```
```console
$ npx prisma db seed
```

### 📜 Запуск авто-генерации документации API

```console
$ npm run docs
```
<img src="docs/Preview-docs.png" alt="Preview docs" />

### 🏋️‍♂️ TODO

- [ ] 💅 Преобразить ```/public/index.html```
- [ ] 💪 Добавить ```express-cluster``` + pm2
- [x] 🗃 Добавить работу с бд через ```prisma```
- [ ] ✋ Добавить ```express-rate-limit``` и ```express-slow-down```
- [ ] 🕯 Добавить ```express-redis-cache```
- [ ] 📦 Модульные тесты
- [x] 🧑‍💻 Интеграционные тесты
- [ ] ✍️ Добавить eslint
