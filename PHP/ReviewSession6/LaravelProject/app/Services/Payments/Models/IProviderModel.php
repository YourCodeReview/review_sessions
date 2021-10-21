<?php

namespace App\Services\Payments\Models;

interface IProviderModel
{
    public function getIdent(): string;
    public function getName(): string;
    public function getClassname(): string;
}
