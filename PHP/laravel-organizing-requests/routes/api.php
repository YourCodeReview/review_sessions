<?php

use App\Http\Controllers\API\RequestController;
use Illuminate\Support\Facades\Route;

/** Список заявок */
Route::get('/requests', [RequestController::class, 'index']);

/** Подробный просмотр заявки */
Route::get('/requests/{request}', [RequestController::class, 'show']);

/** Создание заявки */
Route::post('/requests', [RequestController::class, 'store']);

/** Обновление заявки */
Route::patch('/requests/{request}', [RequestController::class, 'update']);
