<?php

namespace Jarvis\Vendor;

use Jarvis\Vendor\Input\CommandData;
use Jarvis\Vendor\Command\AbstractCommand;

class Application
{
    protected $commandData;

    public function __construct(CommandData $params)
    {
        $this->commandData = $params;
    }

    public function run()
    {
        // Определяем класс команды
        $commandClass = AbstractCommand::getCommandClass($this->commandData->command);
        // Создаем экземпляр команды
        $commandEntity = new $commandClass($this->commandData);
        // Вызываем команду
        $commandEntity->execute();
    }
}