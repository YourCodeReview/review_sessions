<?php

namespace App\Http\Controllers\API;

use App\Actions\Requests\CreateRequestAction;
use App\Actions\Requests\GetPagedRequestsAction;
use App\Actions\Requests\UpdateRequestAction;
use App\Http\Controllers\Controller;
use App\Http\Requests\ApplicationCreateRequest;
use App\Http\Requests\ApplicationUpdateRequest;
use App\Http\Resources\RequestResource;
use App\Http\Sort\RequestSort;
use App\Models\Request;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Resources\Json\AnonymousResourceCollection;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileDoesNotExist;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileIsTooBig;

/**
 * @controller RequestController
 * @description Отвечает за работу с заявками (API)
 */
class RequestController extends Controller
{
    /**
     * Получение списка заявок
     *
     * @param RequestSort $sort
     * @return AnonymousResourceCollection
     */
    public function index(RequestSort $sort): AnonymousResourceCollection
    {
        $requests = GetPagedRequestsAction::run($sort);
        return RequestResource::collection($requests);
    }

    /**
     * Получение заявки
     *
     * @param Request $request
     * @return RequestResource
     */
    public function show(Request $request): RequestResource
    {
        return new RequestResource($request);
    }

    /**
     * Создание новой заявки
     *
     * @param ApplicationCreateRequest $createRequest
     * @return JsonResponse
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    public function store(ApplicationCreateRequest $createRequest): JsonResponse
    {
        $request = CreateRequestAction::run($createRequest);

        return (new RequestResource($request))
            ->response()
            ->setStatusCode(201);
    }

    /**
     * Обновление заявки
     *
     * @param ApplicationUpdateRequest $updateRequest
     * @param Request $request
     * @return RequestResource
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    public function update(ApplicationUpdateRequest $updateRequest, Request $request): RequestResource
    {
        $request = UpdateRequestAction::run($updateRequest, $request);
        return new RequestResource($request);
    }
}
