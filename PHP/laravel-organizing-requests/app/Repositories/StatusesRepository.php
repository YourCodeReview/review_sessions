<?php

namespace App\Repositories;

use App\Models\Status;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Model;

class StatusesRepository
{
    /**
     * @return Model|Status
     */
    public function getRandom(): Model|Status
    {
        return Status::query()->inRandomOrder()->first();
    }

    /**
     * @return Collection
     */
    public function getAll(): Collection
    {
        return Status::all();
    }
}
