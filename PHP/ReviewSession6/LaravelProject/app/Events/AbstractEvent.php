<?php

namespace App\Events;

use Illuminate\Queue\SerializesModels;
use Illuminate\Foundation\Events\Dispatchable;

class AbstractEvent implements EventInterface
{
    use SerializesModels;
    use Dispatchable;
}
