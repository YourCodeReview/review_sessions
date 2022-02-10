<?php

namespace Jarvis\Vendor;

class Config
{
    public static $data;

    private function __clone()
    {

    }

    private function __construct()
    {

    }

    public static function get(...$keys)
    {
        $config = self::$data;
        foreach ($keys as $key) {
            if (isset($config[$key])) {
                $config = $config[$key];
            }
        }

        return $config;
    }
}