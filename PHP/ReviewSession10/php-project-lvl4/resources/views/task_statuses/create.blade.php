
<?php
    use App\Models\TaskStatus;

/**
 * @var $taskStatus \App\Models\TaskStatus
 */
?>

@extends('layouts.app')
@section('content')
    <main class="container py-4">
        <h1 class="mb-5">@lang('Создать статус') </h1>
            {{ Form::model($taskStatus, ['url' => route('task_statuses.store'),'class'=>'w-50']) }}
             <div class="form-group">
                 {{Form::label('name', __('Название'))}}
                 {{ Form::text('name', null, ['class'=> 'form-control']) }}<br>
                 @if($errors->has('name'))
                    <div class="text-danger ">{{ $errors->first('name') }}</div>
                 @endif
                {{ Form::submit( __('Создать') )}}
            </div>
            {{ Form::close() }}

    </main>

@endsection
