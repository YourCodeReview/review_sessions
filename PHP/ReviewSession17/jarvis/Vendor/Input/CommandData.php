<?php

namespace Jarvis\Vendor\Input;

abstract class CommandData
{
    public $command = null;
    public $arguments = [];
    public $options = [];

    public function __construct(array $params)
    {
        $this->parse($params);
    }

    abstract protected function parse(array $params);
}