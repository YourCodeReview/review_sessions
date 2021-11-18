<?php

namespace App\Traits;

use Carbon\Carbon;

trait AccessCreatedAt
{
    protected string $newDateFormat = 'd.m.Y';

    /**
     * @param mixed $value
     * @return string
     */
    public function getCreatedAtAttribute(mixed $value): string
    {
        return Carbon::parse($value)->format($this->newDateFormat);
    }
}
