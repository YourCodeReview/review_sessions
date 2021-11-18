<?php

use App\Http\Controllers\HomeController;
use App\Http\Controllers\RequestController;
use Illuminate\Support\Facades\Route;

/** Главная страница */
Route::get('/', [HomeController::class, 'index']);

/** Список заявок */
Route::get('/requests', [RequestController::class, 'index']);

/** Страница создания заявки */
Route::get('/requests/new', [RequestController::class, 'create']);

/** Создание заявки */
Route::post('/requests', [RequestController::class, 'store']);

/** Страница редактирования заявки */
Route::get('/requests/{request}/edit', [RequestController::class, 'edit']);

/** Обновление заявки */
Route::patch('/requests/{request}', [RequestController::class, 'update']);

/** Детальная страница заявки */
Route::get('/requests/{request}', [RequestController::class, 'show']);
