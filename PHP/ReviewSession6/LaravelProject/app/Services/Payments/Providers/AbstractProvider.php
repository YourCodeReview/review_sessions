<?php

namespace App\Services\Payments\Providers;

use App\Services\Payments\IProvider;

abstract class AbstractProvider implements IProvider
{
    private $config;

    public function __construct($config)
    {
        $this->config = $config;

        $this->onInit();
    }

    protected function onInit(): void
    {

    }

    protected function getConfigValue(string $key): string
    {
        return $this->config[$key];
    }
}
