<?php

namespace App\Services\Users;

use App\Exceptions\AppException;
use App\Services\Users\Commission\ICommission;
use App\Services\Users\Commission\ReplenishCommission;

class CommissionService
{
    public function get(string $type): ICommission
    {
        switch ($type) {
            case 'replenish':
                return app()->make(ReplenishCommission::class);
        }

        throw new AppException(sprintf('Unknown commission class "%s"', $type));
    }
}
