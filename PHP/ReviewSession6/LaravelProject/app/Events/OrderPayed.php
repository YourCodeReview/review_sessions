<?php

namespace App\Events;

use App\Models\Order;

class OrderPayed extends AbstractEvent
{
    protected Order $order;

    public function __construct(Order $order)
    {
        $this->order = $order;
    }

    public function getOrder(): Order
    {
        return $this->order;
    }
}
