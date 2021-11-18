<?php

namespace App\Http\Controllers;

use Illuminate\View\View;

/**
 * @controller HomeController
 * @description Отвечает за работу главной страницы
 */
class HomeController extends Controller
{
    /**
     * @return View
     */
    public function index(): View
    {
        return view('welcome');
    }
}
