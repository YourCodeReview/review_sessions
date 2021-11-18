<?php

namespace App\Http\Sort;

class RequestSort extends QuerySort
{
    /**
     * Сортировка по названию
     *
     * @param string $type
     */
    public function title(string $type): void
    {
        $this->builder->orderBy('title', $type);
    }

    /**
     * Сортировка по дате создания
     *
     * @param string $type
     */
    public function createdAt(string $type): void
    {
        $this->builder->orderBy('created_at', $type);
    }

    /**
     * Сортировка по дате завершения
     *
     * @param string $type
     */
    public function completionAt(string $type): void
    {
        $this->builder->orderBy('completion_at', $type);
    }

    /**
     * Сортировка по статусу
     *
     * @param string $type
     */
    public function status(string $type): void
    {
        $this->builder->orderBy('status_id', $type);
    }
}
