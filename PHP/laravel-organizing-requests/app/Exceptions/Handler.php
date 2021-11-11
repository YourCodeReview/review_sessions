<?php

namespace App\Exceptions;

use Illuminate\Foundation\Exceptions\Handler as ExceptionHandler;
use Illuminate\Http\Exceptions\HttpResponseException;
use Illuminate\Validation\ValidationException;

class Handler extends ExceptionHandler
{
    /**
     * A list of the exception types that are not reported.
     *
     * @var string[]
     */
    protected $dontReport = [
        //
    ];

    /**
     * A list of the inputs that are never flashed for validation exceptions.
     *
     * @var string[]
     */
    protected $dontFlash = [
        'current_password',
        'password',
        'password_confirmation',
    ];

    /**
     * Register the exception handling callbacks for the application.
     *
     * @return void
     */
    public function register()
    {
        $this->renderable(function (ValidationException $e) {

            if (request()->wantsJson()) {
                $this->ApiValidationResponse($e);
            }
        });
    }

    /**
     * @param ValidationException $e
     */
    private function ApiValidationResponse(ValidationException $e)
    {
        $response = [
            'message' => 'Ошибка валидации',
            'errors' => [],
        ];

        foreach ($e->errors() as $field => $message) {
            $response['errors'][] = ['field' => $field, 'message' => $message[0]];
        }

        throw new HttpResponseException(response()->json($response, 422));
    }
}
