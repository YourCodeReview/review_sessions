<?php

namespace App\Tasks\Requests;

use App\Models\Request;
use App\Tasks\AbstractTask;
use Illuminate\Database\Eloquent\Model;

/**
 * @task CreateRequestTask
 * @description Создание заявки
 */
class CreateRequestTask extends AbstractTask
{
    /**
     * @param array $data
     * @return Model|Request
     */
    public static function run(array $data): Model|Request
    {
        return Request::query()->create($data);
    }
}
