<?php

namespace App\Actions\Requests;

use App\Actions\AbstractAction;
use App\Enums\MediaCollections;
use App\Http\Requests\ApplicationCreateRequest;
use App\Models\Request;
use App\Tasks\Requests\CreateRequestTask;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileDoesNotExist;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileIsTooBig;

/**
 * @action CreateRequestAction
 * @description Создание новой заявки
 */
class CreateRequestAction extends AbstractAction
{
    /**
     * @param ApplicationCreateRequest $createRequest
     * @return Request
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    public static function run(ApplicationCreateRequest $createRequest): Request
    {
        // Создание заявки
        $data = self::getRequestData($createRequest);
        $request = CreateRequestTask::run($data);

        // Добавление изображения
        $request->addMediaFromRequest('image')
            ->toMediaCollection(MediaCollections::REQUESTS_IMAGES);

        return $request;
    }

    /**
     * @param ApplicationCreateRequest $createRequest
     * @return array
     */
    private static function getRequestData(ApplicationCreateRequest $createRequest): array
    {
        return $createRequest->only([
            'title',
            'content',
            'completion_at',
            'status_id'
        ]);
    }
}
