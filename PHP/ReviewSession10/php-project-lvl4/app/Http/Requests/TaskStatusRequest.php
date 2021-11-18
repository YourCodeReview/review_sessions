<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class TaskStatusRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'name'  => ['unique:task_statuses,name','required'],
        ];
    }

    /**
     * @inerhitDoc
     */
    public function messages(): array
    {
        return [
            'required' => 'Это обязательное поле',
            'unique'   => 'Статус с таким именем уже существует'
        ];
    }
}
