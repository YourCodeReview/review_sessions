<?php

namespace Jarvis\Commands;

use Jarvis\Vendor\Command\AbstractCommand;
use Jarvis\Vendor\Output\Message;
use Jarvis\Vendor\Config;

class Help extends AbstractCommand
{
    public static $name = 'help';
    public static $description = 'Команда для отображения информации о командах Jarvis';

    public function configure()
    {
        $this->addArgument('name', 'имя команды', false);
    }

    public function execute()
    {
        if ($this->hasArgument('name')) {
            $this->help($this->getArgument('name'));
        } else {
            $this->list();
        }
    }

    private function list()
    {
        Message::success("Консольная утилита Jarvis");
        Message::success("-------------------------");
        if (empty(Config::get('commands'))) {
            Message::success('Ни одной команды не зарегистрировано. Для начала работы создайте хотя бы одну команду.');
        } else {
            Message::success("Доступные команды:");
        }
        foreach (Config::get('commands') as $commandClass) {
            Message::info("- {$commandClass::$name}: {$commandClass::$description}");
        }
        Message::success('Для вывода подробной информации о команде можно воспользоваться следующим вызовом: "php jarvis {название команды} help" ');
    }

    private function help($command)
    {
        // Определяем класс команды
        $commandClass = AbstractCommand::getCommandClass($command);
        // Создаем экземпляр команды
        $commandDataClass= get_class($this->getCommandData());
        $commandEntity = new $commandClass(new $commandDataClass([]), false);

        Message::success("Команда $command");
        Message::success("----------------");
        if ($commandClass::$description) {
            Message::success($commandClass::$description);
        }
        if (!empty($commandEntity->getConfig('arguments'))) {
            Message::success("Аргументы:");
            foreach ($commandEntity->getConfig('arguments') as $key => $argument) {
                $isRequired = $argument->isRequired() ? '(обязательный)' : '(необязательный)';
                $number = $key + 1;
                Message::info("{$number}. {$argument->getName()} {$isRequired}: {$argument->getDescription()}");
            }
        }
        if (!empty($commandEntity->getConfig('options'))) {
            Message::success("Опции:");
            foreach ($commandEntity->getConfig('options') as $option) {
                $isValueRequired = $option->isValueRequired() ? '(значение обязательно)' : '(значение необязательно)';
                Message::info("-- {$option->getName()} {$isValueRequired}: {$option->getDescription()}");
            }
        }
    }
}