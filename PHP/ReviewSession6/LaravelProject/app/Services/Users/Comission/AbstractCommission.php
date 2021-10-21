<?php

namespace App\Services\Users\Commission;

abstract class AbstractCommission implements ICommission
{
    public function getCalculatedPercentValue(float $value): float
    {
        $percent = $this->getPercent();
        if (!$percent) {
            return 0;
        }

        return $value * $percent / 100;
    }

    public function getCalculatedValue(float $value): float
    {
        $commission = $this->getCalculatedPercentValue($value);
        if (!$commission) {
            return $value;
        }

        return $value - $commission;
    }

    public function getOriginalValue(float $value): float
    {
        $percent = $this->getPercent();
        if (!$percent) {
            return $value;
        }

        return $value / (100 + $percent) * 100;
    }
}
