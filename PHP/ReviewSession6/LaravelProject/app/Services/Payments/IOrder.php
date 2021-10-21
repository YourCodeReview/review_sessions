<?php

namespace App\Services\Payments;

use DateTimeInterface;

interface IOrder
{
    public const STATUS_PENDING = 'pending';
    public const STATUS_SUCCESS = 'success';
    public const STATUS_FAIL    = 'fail';
    public const STATUS_EXPIRED = 'expired';

    public function getID(): string;
    public function getPrice(): float;
    public function getCurrency(): string;
    public function getStatus(): string;
    public function getUserID(): string;
    public function getComment(): string;
    public function getExpires(): DateTimeInterface;
}
