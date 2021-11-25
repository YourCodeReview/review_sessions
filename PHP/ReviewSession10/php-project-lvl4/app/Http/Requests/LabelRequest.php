<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class LabelRequest extends FormRequest
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
            'name'  => ['required', 'unique:labels,name'],
        ];
    }

    /**
     * @inerhitDoc
     */
    public function messages(): array
    {
        return [
            'required' => 'Это обязательное поле',
            'unique'   => 'Метка с таким именем уже существует'
        ];
    }
}
