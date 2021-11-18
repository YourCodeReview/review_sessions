<?php

namespace App\Actions\Requests;

use App\Actions\AbstractAction;
use App\Http\Sort\RequestSort;
use App\Repositories\RequestsRepository;
use Illuminate\Pagination\LengthAwarePaginator;

/**
 * @action GetPagedRequestsAction
 * @description Получение списка заявок
 */
class GetPagedRequestsAction extends AbstractAction
{
    /**
     * @param RequestSort $sort
     * @return LengthAwarePaginator
     */
    public static function run(RequestSort $sort): LengthAwarePaginator
    {
        return app(RequestsRepository::class)->getPaged($sort);
    }
}
