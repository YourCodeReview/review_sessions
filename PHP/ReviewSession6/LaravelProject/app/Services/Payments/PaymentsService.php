<?php

namespace App\Services\Payments;

use App\Events\OrderPayed;
use App\Models\Order;
use App\Models\Payment;
use App\Services\Payments\IPayment;
use App\Services\Payments\IProvider;
use App\Services\Payments\Models\IProviderModel;
use Illuminate\Contracts\Container\Container;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;
use App\Exceptions\Payment\PaymentException;
use App\Models\User;
use App\Models\PaymentProvider;
use Carbon\Carbon;
use Illuminate\Support\Str;
use Throwable;
use Illuminate\Contracts\Events\Dispatcher as DispatcherContract;

class PaymentsService
{
    private array $config;
    private Container $container;
    private DispatcherContract $eventDispatcher;

    // TODO: remake to dict
    public const DEFAULT_CURRENCY = 'RUB';

    public function __construct(
        array $config,
        Container $container,
        DispatcherContract $eventDispatcher
    )
    {
        $this->config = $config;
        $this->container = $container;
        $this->eventDispatcher = $eventDispatcher;
    }

    public function getProvidersList()
    {
        return PaymentProvider::active()->get();
    }

    public function getTypes(): array
    {
        return $this->config['types'];
    }

    public function create(User $user, array $orderValues): IOrder
    {
        $ident = Str::uuid();
        $comment = "Пополнение личного счета для {$user->name}";
        // TODO: calc commission
        $price = $orderValues['amount'];
        $values = [
            'ident'          => $ident,
            'user_id'        => $user->id,
            'currency'       => self::DEFAULT_CURRENCY,
            'price'          => $price,
            'value'          => $orderValues['amount'],
            'comment'        => $comment,
            'status'         => IOrder::STATUS_PENDING,
            'created_at'     => Carbon::now(),
            'expires_at'     => Carbon::now()->addMonth(1),
        ];
        /** @var IOrder $order */
        $order = $user->orders()->create($values);

        return $order;
    }

    public function getPayUrl(
        IOrder $order,
        IProviderModel $providerModel,
        ?string $redirectUrl
    ): string
    {
        $provider = $this->getProviderInstance($providerModel);

        return $provider->getPayUrl($order, $redirectUrl);
    }

    public function getProvider(string $ident): PaymentProvider
    {
        return PaymentProvider::where('ident', $ident)->firstOrFail();
    }

    public function getProviderInstance(IProviderModel $providerModel): IProvider
    {
        $className = $providerModel->getClassname();
        if (!class_exists($className)) {
            throw new PaymentException("Provider instance '{$className}' was not found!");
        }

        return $this->container->make($className, ['config' => $this->getTypes()[$providerModel->getIdent()]]);
    }

    public function getProviderInstanceByIdent(string $ident): IProvider
    {
        $providerModel = PaymentProvider::where('ident', $ident)->firstOrFail();
        return $this->getProviderInstance($providerModel);
    }

    public function handle(IPayment $payment): void
    {
        try {
            $provider = $this->getProviderInstanceByIdent($payment->getProviderIdent());
            $orderID = $provider->handle($payment);

            /** @var IOrder $order */
            $order = Order::where('ident', $orderID)->firstOrFail();

            $status = $provider->getStatus($order);
            if ($order->getStatus() === $status) {
                return;
            }

            $payment->status = $status;
            DB::transaction(function () use ($order, $status, $payment) {
                $payment->status = IPayment::STATUS_PROCESSED;
                $payment->order_ident = $order->getID();
                $payment->save();

                $order->status = $status;
                $order->save();
                $order->refresh();

                $this->eventDispatcher->dispatch(new OrderPayed($order));
            });
        } catch (Throwable $exception) {
            $payment->status = IPayment::STATUS_ERROR;
            $error = [
                'message' => $exception->getMessage(),
                'trace'   => $exception->getTrace(),
            ];
            Log::channel('payment_errors')->error(json_encode($error));

            $payment->error = $error;
            $payment->save();

            throw $exception;
        }

    }
}
