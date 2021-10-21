<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class CreateOrderRequest extends FormRequest
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
            'amount'   => [
                'required',
                'integer',
                'min:100',
                'max:50000',
            ],
            'provider' => [
                'required',
                'string',
                'exists:payment_providers,ident',
            ],
            'redirect_url' => [
                'required',
                'string',
                'min:6',
                'max:256',
            ],
        ];
    }
}
