<?php

namespace App\Listeners;

use App\Events\OrderPayed;
use App\Models\User;
use App\Services\Users\BalanceService;

class ApplyPayment extends AbstractQueueListener
{
    private BalanceService $service;

    public function __construct(BalanceService $service)
    {
        $this->service = $service;
    }

    public function handle(OrderPayed $event): void
    {
        $order = $event->getOrder();
        $user = User::findOrFail($order->getUserID());
        $this->service->replenish(
            $user,
            $order->getValue(),
            $order->getPrice() - $order->getValue(),
            $order->getComment()
        );
    }
}
