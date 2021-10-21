<?php

namespace App\Http\Controllers;

use App\Http\Requests\CreateOrderRequest;
use App\Http\Resources\OrderResource;
use App\Http\Resources\PaymentProviderResource;
use App\Http\Resources\PaymentResource;
use App\Jobs\ProcessPayment;
use App\Services\Payments\IPayment;
use Carbon\Carbon;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Auth;
use App\Services\Payments\PaymentsService;
use App\Models\Payment;
use App\Models\PaymentProvider;

class PaymentController extends Controller
{
    private PaymentsService $service;

    public function __construct(PaymentsService $service)
    {
        $this->service = $service;
    }

    /**
     * Get allowed payment providers
     *
     * @group Payments
     *
     * @apiResource App\Http\Resources\PaymentProviderResource
     * @apiResourceModel App\Models\PaymentProvider
     */
    public function getProvidersList(): JsonResource
    {
        return PaymentProviderResource::collection($this->service->getProvidersList());
    }

    /**
     * Create ticket
     *
     * @group Tickets
     *
     * @apiResource App\Http\Resources\OrderResource
     * @apiResourceModel App\Models\Order
     */
    public function create(CreateOrderRequest $request)
    {
        $provider = $this->service->getProvider($request->provider);

        $order = $this->service->create($this->getCurrentUser(), $request->all());

        $order->pay_url = $this->service->getPayUrl($order, $provider, $request->get('redirect_url'));

        return new OrderResource($order);
    }

    public function callback(Request $request, string $providerIdent)
    {
        $requestData = [
//            'headers' => $request->headers->all(),
            'headers' => getallheaders(),
            'body' => $request->all(),
        ];
        $requestDataEncoded = json_encode($requestData);
        $message = "Request: {$requestDataEncoded}";
        Log::channel('payments')->debug($message);

        $payment = Payment::create(
            [
                'provider_ident' => $providerIdent,
                'data'           => $requestData,
                'status'         => IPayment::STATUS_PENDING,
                'created_at'     => Carbon::now(),
            ]
        );

        ProcessPayment::dispatch($payment);
    }

    public function check(Payment $payment)
    {
        $this->service->check($payment);
    }
}
