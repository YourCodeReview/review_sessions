<?php

namespace App\Actions\Statuses;

use App\Actions\AbstractAction;
use App\Repositories\StatusesRepository;
use Illuminate\Database\Eloquent\Collection;

/**
 * @action GetStatusesAction
 * @description Получение списка статусов
 */
class GetStatusesAction extends AbstractAction
{
    /**
     * @return Collection
     */
    public static function run(): Collection
    {
        return app(StatusesRepository::class)->getAll();
    }
}
