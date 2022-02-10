<?php

namespace Jarvis\Vendor\Output;

/**
 * Класс сообщения
 * - вызывает метод write сущности Console или Browser
 * - выбор сущности зависит от режима работы php: в консоли или в браузере
 * - сама сущность определяется в методе entity()
 * - методы info, error, success, warning выводит сообщение в соответствующем цвете
 */
class Message
{
    protected static function entity(): string
    {
        return PHP_SAPI === "cli" ? Console::class : Browser::class;
    }

    public static function info(string $str)
    {
        self::entity()::write($str, 'info');
    }

    public static function error(string $str)
    {
        self::entity()::write($str, 'error');
    }

    public static function success(string $str)
    {
        self::entity()::write($str, 'success');
    }

    public static function warning(string $str)
    {
        self::entity()::write($str, 'warning');
    }
}