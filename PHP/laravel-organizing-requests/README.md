# Laravel organizing requests

## Описание

<b>Laravel organizing requests</b> - Сервис для организации работы с заявками.

----------------

Функционал WEB:

- Список заявок \[GET /requests\]
- Подробная страница заявки \[GET /requests/{request_id}\]
- Создание новой заявки \[GET /requests/new\]
- Редактирование заявки \[GET /requests/{request_id}/edit\]

----------------

Функционал API:

- Список заявок \[GET /api/requests\]
- Подробная страница заявки \[GET /api/requests/{request_id}\]
- Создание новой заявки \[POST /api/requests\]
- Редактирование заявки \[PATCH /api/requests/{request_id}\]

## Архитектура

В приложении использовалась стандартная архитектура laravel. Так же была использована концепция Porto (Actions, Tasks)

Общение с Frontend происходит с помощью встроенного функционала и шаблонизатора - Blade.

## Тестирование

Для проверки корректной работы приложения вы можете запустить тесты:

1. Тестирование API

```shell
  $ php artisan test --testsuite=API
```

2. Тестирование WEB

```shell
  $ php artisan test --testsuite=WEB
```

3. Общее тестирование

```shell
  $ php artisan test
```

## Необходимые параметры

Все параметры среды указываются в .env (в корне проекта).

- <b>APP_URL</b> (Ссылки на изображения формируются с помощью этого параметра)
- <b>PAGINATION_LIMIT</b> (Лимит заявок для вывода на одной странице. По умолчанию - 12)
