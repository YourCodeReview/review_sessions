<?php
use App\Models\Task;

/**
 * @var Task[] $tasks
 * @var array $statusesList
 * @var array $usersList
 * @var integer $statusId
 * @var integer $createdById
 * @var integer $assignedToId
 */
?>

@extends('layouts.app')

@section('content')
        <h1 class="mb-5">@lang('Задачи')</h1>
        <div class="d-flex">
            <div>
                {{Form::open(['class' => 'form-inline','method' => 'get'])}}
                {{Form::select('filter[status_id]', $statusesList , $statusId,  selectAttributes : ['class' => 'form-control mr-2','placeholder' => __('Статус')])}}
                {{Form::select('filter[created_by_id]', ['' => __('Создатель')] + $usersList, $createdById, selectAttributes : ['class' => 'form-control mr-2'])}}
                {{Form::select('filter[assigned_to_id]',['' => __('Исполнитель')] + $usersList, $assignedToId, selectAttributes : ['class' => 'form-control mr-2'])}}
                <input class="btn btn-outline-primary mr-2" type="submit" value="Применить">
                {{Form::close()}}
            </div>
        </div>


        @auth()
            <a href="{{route('tasks.create')}}" class="btn btn-primary mt-5"> @lang('Создать задачу') </a>
        @endauth
        <table class="table mt-2">
            <thead>
            <tr>
                <th>@lang('ID')</th>
                <th>@lang('Статус')</th>
                <th>@lang('Имя')</th>
                <th>@lang('Автор')</th>
                <th>@lang('Исполнитель')</th>
                <th>@lang('Дата создания')</th>
                @auth()
                <th>@lang('Действия')</th>
                @endauth
            </tr>
            </thead>
            <tbody>
            @foreach($tasks as $task)
                <tr>
                    <td>{{$task->id}}</td>
                    <td>{{$task->status->name}}</td>
                    <td>
                        <a href="{{route('tasks.show', [$task->id])}}">{{$task->name}}</a>
                    </td>
                    <td>{{$task->creator->name}}</td>
                    <td>{{optional($task->executor)->name ?? ''}}</td>
                    <td>{{$task->created_at}}</td>
                    @auth()
                        <td>
                            @can('destroy-task',[$task])
                            <a class="text-danger"
                               href="{{route('tasks.destroy', [$task->id])}}"
                               data-confirm="Вы уверены?" data-method="delete"> @lang('Удалить')
                            </a>
                            @endcan

                            <a href="{{route('tasks.edit',[$task->id])}}" > @lang('Изменить') </a>

                        </td>
                    @endauth
                </tr>
            @endforeach
            </tbody>
        </table>
@endsection