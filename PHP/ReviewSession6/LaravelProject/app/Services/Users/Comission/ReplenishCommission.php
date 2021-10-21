<?php

namespace App\Services\Users\Commission;

class ReplenishCommission extends AbstractCommission
{
    public function getPercent(): float
    {
        return (float) config('payments.commission_replenish', 0);
    }
}
