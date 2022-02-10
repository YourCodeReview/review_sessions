<?php

namespace Jarvis\Vendor\Input;

class ArrayInput extends CommandData
{
    protected function parse(array $params)
    {
        $this->command = $params['command'];
        if (is_array($params['arguments']) && !empty($params['arguments'])){
            $this->arguments = $params['arguments'];
        }
        if (is_array($params['options']) && !empty($params['options'])){
            $this->options = $params['options'];
            // Если значение опции не массив, создаём его
            foreach ($this->options as &$option){
                if (!is_array($option)) $option = [$option];
            }
        }
    }
}