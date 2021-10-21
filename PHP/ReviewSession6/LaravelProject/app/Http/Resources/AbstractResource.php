<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class AbstractResource extends JsonResource
{
    protected function getDateTimeFormat(): string
    {
        return 'Y-m-d H:i:s';
    }

    protected function getDateFormat(): string
    {
        return 'Y-m-d';
    }
}
