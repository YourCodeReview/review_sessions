<?php

namespace App\Http\Requests;

use App\Rules\FurtherToday;
use Illuminate\Foundation\Http\FormRequest;

class ApplicationCreateRequest extends FormRequest
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
            'title' => 'required|string|max:64',
            'image' => 'required|image|max:5000',
            'content' => 'required|string',
            'completion_at' => ['required', 'date', new FurtherToday],
            'status_id' => 'required|exists:statuses,id',
        ];
    }
}
