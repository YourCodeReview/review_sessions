<?php

namespace App\Http\Controllers;

use App\Actions\Requests\CreateRequestAction;
use App\Actions\Requests\GetPagedRequestsAction;
use App\Actions\Requests\UpdateRequestAction;
use App\Actions\Statuses\GetStatusesAction;
use App\Enums\MediaCollections;
use App\Http\Requests\ApplicationCreateRequest;
use App\Http\Requests\ApplicationUpdateRequest;
use App\Http\Sort\RequestSort;
use App\Models\Request;
use Illuminate\Http\RedirectResponse;
use Illuminate\View\View;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileDoesNotExist;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileIsTooBig;

/**
 * @controller RequestController
 * @description Отвечает за работу с заявками
 */
class RequestController extends Controller
{
    /**
     * Страница со списком заявок
     *
     * @param RequestSort $sort
     * @return View
     */
    public function index(RequestSort $sort): View
    {
        // Заявки
        $requests = GetPagedRequestsAction::run($sort);

        // Шаблон
        return view('requests.index')->with([
            'requests' => $requests,
        ]);
    }

    /**
     * Детальная страница заявки
     *
     * @param Request $request
     * @return View
     */
    public function show(Request $request): View
    {
        // Шаблон
        return view('requests.show')->with([
            'request' => $request,
            'imageURL' => $request->getFirstMediaUrl(
                MediaCollections::REQUESTS_IMAGES
            ),
        ]);
    }

    /**
     * Страница создания новой заявки
     *
     * @return View
     */
    public function create(): View
    {
        // Статусы
        $statuses = GetStatusesAction::run();

        // Шаблон
        return view('requests.create', [
            'statuses' => $statuses,
        ]);
    }

    /**
     * Страница редактирования заявки
     *
     * @param Request $request
     * @return View
     */
    public function edit(Request $request): View
    {
        // Статусы
        $statuses = GetStatusesAction::run();

        // Шаблон
        return view('requests.edit', [
            'request' => $request,
            'statuses' => $statuses,
            'imageURL' => $request->getFirstMediaUrl(
                MediaCollections::REQUESTS_IMAGES
            ),
        ]);
    }

    /**
     * Создание заявки
     *
     * @param ApplicationCreateRequest $request
     * @return RedirectResponse
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    public function store(ApplicationCreateRequest $request): RedirectResponse
    {
        // Создание заявки
        $request = CreateRequestAction::run($request);

        // Сообщение
        session()->flash('success', 'Заявка успешно создана!');

        // Шаблон
        return redirect('/requests/' . $request->id);
    }

    /**
     * Обновление заявки
     *
     * @param ApplicationUpdateRequest $updateRequest
     * @param Request $request
     * @return RedirectResponse
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    public function update(ApplicationUpdateRequest $updateRequest, Request $request): RedirectResponse
    {
        // Обновленная заявка
        $request = UpdateRequestAction::run($updateRequest, $request);

        // Сообщение
        session()->flash('success', 'Заявка успешно обновлена!');

        // Шаблон
        return redirect('/requests/' . $request->id);
    }
}
