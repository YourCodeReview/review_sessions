<?php

namespace App\Services\Payments;

use DateTimeInterface;

interface IPayment
{
    public const STATUS_PENDING   = 'pending';
    public const STATUS_PROCESSED = 'processed';
    public const STATUS_ERROR     = 'error';

    public function getStatus(): string;
    public function getCreated(): DateTimeInterface;
    public function getProviderIdent(): string;
    public function getRequest(): array;
}
