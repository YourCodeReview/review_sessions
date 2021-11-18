<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class TaskRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize(): bool
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules(): array
    {
        return [
            'name'  => ['required', 'unique:tasks,name'],
            'status_id' => ['required'],
        ];
    }

    /**
     * @inerhitDoc
     */
    public function messages(): array
    {
        return [
            'required' => 'Это обязательное поле',
            'unique'   => 'Задача с таким именем уже существует'
        ];
    }
}
