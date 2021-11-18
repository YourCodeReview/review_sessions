<?php
use App\Models\TaskStatus;

/**
 * @var TaskStatus[] $taskStatuses
 */
?>

@extends('layouts.app')

@section('content')
    <main class="container py-4">
        <h1 class="mb-5">@lang('Статусы')</h1>
        @auth()
        <a href="{{route('task_statuses.create')}}" class="btn btn-primary"> @lang('Создать статус') </a>
        @endauth
        <table class="table mt-2">
            <thead>
            <tr>
                <th>@lang('ID')</th>
                <th>@lang('Имя')</th>
                <th>@lang('Дата создания')</th>
                @auth
                <th>@lang('Действия')</th>
                @endauth
            </tr>
            </thead>
            <tbody>
            @foreach($taskStatuses as $taskStatus)
                <tr>
                    <td>{{$taskStatus->id}}</td>
                    <td>{{$taskStatus->name}}</td>
                    <td>{{$taskStatus->created_at}}</td>
                    @auth
                    <td>
                        <a class="text-danger"
                           href="{{route('task_statuses.destroy', [$taskStatus->id])}}"
                           data-confirm="Вы уверены?" data-method="delete"> @lang('Удалить')
                        </a>
                        <a href="{{route('task_statuses.edit',[$taskStatus->id])}}" > @lang('Изменить') </a>
                    </td>
                    @endauth
                </tr>
            @endforeach
            </tbody>
        </table>
    </main>
@endsection