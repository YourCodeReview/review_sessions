<?php

namespace App\Services\Payments;

use App\Services\Payments\IPayment;

interface IProvider
{
    public function getIdent(): string;
    public function create(IOrder $order): void;
    public function getPayUrl(IOrder $order, string $redirectUrl): string;
    public function handle(IPayment $payment): string;
    public function getStatus(IOrder $order): string;
}
