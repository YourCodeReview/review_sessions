<?php

namespace Jarvis\Vendor\Input;

class ArgvInput extends CommandData
{
    protected function parse(array $params)
    {
        foreach ($params as $key => $param) {
            if ($key === 0) {
                continue;
            } elseif ($key === 1) {
                $this->command = $param;
            } elseif (substr($param, 0, 2) !== '--') {
                $this->arguments[] = $param;
            } else {
                $option = substr($param, 2);
                $optionParts = explode('=', $option);
                $optionKey = $optionParts[0];
                $optionValue = count($optionParts) > 1 ? substr($option, strlen($optionKey) + 1) : null;
                $this->options[$optionKey][] = $optionValue;
            }
        }
    }
}