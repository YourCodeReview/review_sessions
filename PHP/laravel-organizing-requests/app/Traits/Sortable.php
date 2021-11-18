<?php

namespace App\Traits;

use App\Http\Sort\QuerySort;
use Illuminate\Database\Eloquent\Builder;

trait Sortable
{
    /**
     * @param Builder $builder
     * @param QuerySort $sort
     */
    public function scopeSort(Builder $builder, QuerySort $sort)
    {
        $sort->apply($builder);
    }
}
