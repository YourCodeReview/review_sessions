<?php

namespace Jarvis\Vendor\Output;

class Console extends AbstractMessage
{
    const COLORS = [
        'error'   => '0;31',
        'warning' => '0;33',
        'info'    => '0;37',
        'success' => '0;32'
    ];

    public static function write(string $str, string $color)
    {
        echo "\033[" . self::COLORS[$color] . "m" . $str . "\033[0m" . PHP_EOL;
    }
}