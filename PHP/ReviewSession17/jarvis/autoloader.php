<?php

use Jarvis\Vendor\Config;

spl_autoload_register(function ($class) {
    if (substr($class, 0, 6) === "Jarvis"){
        $pathParts = explode('\\', $class);
        $pathParts[0] = strtolower($pathParts[0]);
        $classPath = dirname(__DIR__) . DIRECTORY_SEPARATOR . implode(DIRECTORY_SEPARATOR, $pathParts) . '.php';
        if (file_exists($classPath)) {
            require $classPath;
        } else {
            throw new \Exception("Ошибка загрузки '$classPath'");
        }
    }
});

Config::$data = include 'config.php';

// Регистрируем команды
foreach (Config::get('commands') as $command){
    spl_autoload_call($command);
}