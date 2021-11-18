
<?php
use App\Models\Label;

/**
 * @var $label \App\Models\Label
 */
?>

@extends('layouts.app')
@section('content')
    <main class="container py-4">
        <h1 class="mb-5">@lang('Редактировать метку') </h1>
        {{ Form::model($label, ['url' => route('labels.update', [$label->id]),'method' => 'patch', 'class'=>'w-50']) }}
        <div class="form-group">
            {{Form::label('name', __('Название'))}}
            {{ Form::text('name', null, ['class'=> 'form-control']) }}<br>
            @if($errors->has('name'))
                <div class="text-danger ">{{ $errors->first('name') }}</div>
            @endif
        </div>
        <div class="form-group">
            {{Form::label('description', __('Описание'))}}
            {{ Form::text('description', null, ['class'=> 'form-control']) }}<br>
            @if($errors->has('description'))
                <div class="text-danger ">{{ $errors->first('description') }}</div>
            @endif
        </div>
        <div class="form-group">
            {{ Form::submit( __('Обновить') )}}
        </div>
        {{ Form::close() }}

    </main>

@endsection
