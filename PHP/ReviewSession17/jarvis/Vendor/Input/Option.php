<?php

namespace Jarvis\Vendor\Input;

class Option
{
    private $name;
    private $description;
    private $valueRequired;

    public function __construct(string $name, string $description = '', bool $valueRequired = false)
    {
        $this->name = $name;
        $this->description = $description;
        $this->valueRequired = $valueRequired;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getDescription(): string
    {
        return $this->description;
    }

    public function isValueRequired(): bool
    {
        return $this->valueRequired;
    }
}