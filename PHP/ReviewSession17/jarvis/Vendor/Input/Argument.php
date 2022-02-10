<?php

namespace Jarvis\Vendor\Input;

class Argument
{
    private $name;
    private $description;
    private $required;

    public function __construct(string $name, string $description = '', bool $required = true)
    {
        $this->name = $name;
        $this->description = $description;
        $this->required = $required;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getDescription(): string
    {
        return $this->description;
    }

    public function isRequired(): bool
    {
        return $this->required;
    }
}