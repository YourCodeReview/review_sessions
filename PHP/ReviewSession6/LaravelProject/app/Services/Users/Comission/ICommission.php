<?php

namespace App\Services\Users\Commission;

interface ICommission
{
    public function getPercent(): float;
    public function getCalculatedPercentValue(float $value): float;
    public function getCalculatedValue(float $value): float;
    public function getOriginalValue(float $value): float;
}
