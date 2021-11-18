<?php

namespace App\Tasks\Requests;

use App\Models\Request;
use App\Tasks\AbstractTask;

/**
 * @task UpdateRequestTask
 * @description Обновление заявки
 */
class UpdateRequestTask extends AbstractTask
{
    /**
     * @param Request $request
     * @param array $data
     * @return Request
     */
    public static function run(Request $request, array $data): Request
    {
        $request->update($data);
        return $request;
    }
}
