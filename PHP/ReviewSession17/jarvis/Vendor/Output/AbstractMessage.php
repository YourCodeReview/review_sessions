<?php

namespace Jarvis\Vendor\Output;

abstract class AbstractMessage
{
    const COLORS = [];

    abstract public static function write(string $str, string $color);
}