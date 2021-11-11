<?php

namespace App\Enums;

final class Statuses extends AbstractEnum
{
    public const OPEN = 'Open';
    public const NEEDS_OFFER = 'Needs offer';
    public const OFFERED = 'Offered';
    public const APPROVED = 'Approved';
    public const IN_PROGRESS = 'In progress';
    public const READY = 'Ready';
    public const VERIFIED = 'Verified';
    public const CLOSED = 'Closed';
}
