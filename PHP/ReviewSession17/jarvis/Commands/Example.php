<?php

namespace Jarvis\Commands;

use Jarvis\Vendor\Command\AbstractCommand;
use Jarvis\Vendor\Output\Message;

class Example extends AbstractCommand
{
    public static $name = 'example';
    public static $description = 'Команда для демонстрации работы консольного приложения Jarvis';

    public function configure()
    {
        $this->addArgument('name', 'имя')
             ->addArgument('surname', 'фамилия', false)
             ->addArgument('second-name', 'отчество', false)
             ->addOption('excellence', 'положительные качества', true)
             ->addOption('job', 'есть работа');
    }

    public function execute()
    {
        Message::success('Результат выполнения команды:');
        Message::info("Имя: ".$this->getArgument('name'));
        if ($this->hasArgument('surname')){
            Message::info("Фамилия: ".$this->getArgument('surname'));
        }
        if ($this->hasArgument('second-name')){
            Message::info("Отчество: ".$this->getArgument('second-name'));
        }
        if ($this->hasOption('excellence')){
            Message::info("Положительные качества: ". implode(', ', $this->getOption('excellence')));
        }
        if ($this->hasOption('job')){
            Message::info("Работа: есть");
        }
    }
}