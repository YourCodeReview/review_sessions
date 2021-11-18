
<?php
use Illuminate\Support\Arr;
/**
 * @var $task \App\Models\Task
 * @var $statusesList array
 * @var $usersList array
 * @var $labelsList array
 **/
?>

@extends('layouts.app')
@section('content')
    <main class="container py-4">
        <h1 class="mb-5">@lang('Создать задачу') </h1>
        {{ Form::model($task, ['url' => route('tasks.update', [$task->id]),'method' => 'patch', 'class'=>'w-50']) }}
             <div class="form-group">
                 {{Form::label('name', __('Имя'))}}
                 {{ Form::text('name', null, ['class'=> 'form-control']) }}<br>
                 @if($errors->has('name'))
                    <div class="text-danger ">{{ $errors->first('name') }}</div>
                 @endif
             </div>
            <div class="form-group">
                 {{Form::label('description', __('Описание'))}}
                 {{ Form::textarea('description', null, ['class'=> 'form-control']) }}<br>
                 @if($errors->has('description'))
                     <div class="text-danger ">{{ $errors->first('description') }}</div>
                 @endif
            </div>
            <div class="form-group">
                 {{Form::label('status_id', __('Статус'))}}
                 {{Form::select('status_id', $statusesList, selectAttributes : ['class' => 'form-control',] )}}
                 @if($errors->has('status_id'))
                     <div class="text-danger ">{{ $errors->first('status_id') }}</div>
                 @endif
            </div>
            <div class="form-group">
                 {{Form::label('assigned_to_id', __('Исполнитель'))}}
                 {{Form::select('assigned_to_id', $usersList, selectAttributes : ['class' => 'form-control',] )}}
                 @if($errors->has('assigned_to_id'))
                     <div class="text-danger ">{{ $errors->first('assigned_to_id') }}</div>
                 @endif
            </div>
            <div class="form-group">
                {{Form::label('labels', __('Метки'))}}
                {{Form::select('labels[]', $labelsList, selectAttributes : ['class' => 'form-control ', 'multiple'] )}}
                @if($errors->has('labels'))
                    <div class="text-danger ">{{ $errors->first('labels') }}</div>
                @endif
            </div>
            <div class="form-group">
                {{ Form::submit( __('Обновить'), ['class' => 'btn btn-primary'] )}}
            </div>
            {{ Form::close() }}

    </main>

@endsection
