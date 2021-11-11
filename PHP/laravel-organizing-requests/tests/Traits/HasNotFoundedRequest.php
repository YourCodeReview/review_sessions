<?php

namespace Tests\Traits;

use App\Models\Request;

trait HasNotFoundedRequest
{
    /**
     * @return int
     */
    public function getNotFoundedID(): int
    {
        return Request::query()->max('id') + 1;
    }
}
