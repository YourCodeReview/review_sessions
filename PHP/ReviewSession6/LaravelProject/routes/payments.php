<?php

use App\Http\Controllers\PaymentController;
use Illuminate\Support\Facades\Route;

Route::get('payments/providers', [PaymentController::class, 'getProvidersList'])
    ->middleware('api_auth');

Route::post('payments', [PaymentController::class, 'create'])
    ->middleware('api_auth');

Route::get('payments/{payment}/check', [PaymentController::class, 'check'])
    ->middleware('api_auth');

Route::post('callback/payments/{providerIdent}', [PaymentController::class, 'callback'])
    ->name('payment.callback');
    