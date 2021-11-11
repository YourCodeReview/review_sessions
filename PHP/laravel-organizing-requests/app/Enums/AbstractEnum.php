<?php

namespace App\Enums;

abstract class AbstractEnum
{
    /**
     * @return array
     */
    public static function values(): array
    {
        $oClass = new \ReflectionClass(static::class);
        return $oClass->getConstants();
    }
}
