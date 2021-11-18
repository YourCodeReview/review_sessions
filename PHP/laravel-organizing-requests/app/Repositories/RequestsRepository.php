<?php

namespace App\Repositories;

use App\Http\Sort\RequestSort;
use App\Models\Request;
use Illuminate\Pagination\LengthAwarePaginator;

class RequestsRepository
{
    /**
     * Получить список заявок
     *
     * @param RequestSort $sort
     * @return LengthAwarePaginator
     */
    public function getPaged(RequestSort $sort): LengthAwarePaginator
    {
        return Request::sort($sort)->paginate(config('pagination.limit'));
    }
}
