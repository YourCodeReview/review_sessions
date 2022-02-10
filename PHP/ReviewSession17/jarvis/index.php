<?php

use Jarvis\Vendor\Output\Message;
use Jarvis\Vendor\Input\ArrayInput;
use Jarvis\Vendor\Application;

try {
    require 'autoloader.php';
    $input = new ArrayInput([
        'command'   => 'example',
        'arguments' => [
            'Василий',
            'Пупкин'
        ],
        'options'   => [
            'excellence' => [
                'любит покушать',
                'обожает сериалы',
                'добряк'
            ],
            'job'        => null
        ]
    ]);
    $app = new Application($input);
    $app->run();
} catch (\Exception $e) {
    Message::error($e->getMessage());
}