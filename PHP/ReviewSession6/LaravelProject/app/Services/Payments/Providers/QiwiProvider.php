<?php

namespace App\Services\Payments\Providers;

use App\Exceptions\Payment\PaymentException;
use App\Services\Payments\IOrder;
use App\Services\Payments\IPayment;
use Qiwi\Api\BillPayments;
use DateTimeInterface;

/**
 * see https://developer.qiwi.com/ru/p2p-payments/#create
 * see https://developer.qiwi.com/ru/p2p-payments/#invoice-status
 * see https://developer.qiwi.com/ru/p2p-payments/#cancel
 * see https://developer.qiwi.com/ru/p2p-payments/#notification
 */
final class QiwiProvider extends AbstractProvider
{
    private const STATUS_WAITING  = 'WAITING';
    private const STATUS_PAID     = 'PAID';
    private const STATUS_REJECTED = 'REJECTED';
    private const STATUS_EXPIRED  = 'EXPIRED';

    private BillPayments $sdk;

    protected function onInit(): void
    {
        $this->sdk = $this->getSDKInstance();
    }

    private function getSDKInstance(): BillPayments
    {
        $billPayments = new BillPayments($this->getSecretKey());

        return $billPayments;
    }

    private function getSDK(): BillPayments
    {
        return $this->sdk;
    }

    public function getIdent(): string
    {
        return 'qiwi';
    }

    public function create(IOrder $order): void
    {
        $this->createBill($order);
    }

    private function createBill(IOrder $order): array
    {
        $billId = $order->getID();
        $values = [
            'amount'             => $order->getPrice(),
            'currency'           => $this->getCurrencyByCode($order->getCurrency()),
            'comment'            => $order->getComment(),
            'expirationDateTime' => $order->getExpires()->format(DateTimeInterface::ATOM),
            'account'            => $order->getUserID(),
            'customFields'       => [],
        ];

        $themeIdent = $this->getConfigValue('theme_ident');
        if ($themeIdent) {
            $values['customFields']['themeCode'] = $themeIdent;
        }

        return $this->getSDK()->createBill($billId, $values);
    }

    public function getPayUrl(IOrder $order, string $redirectUrl): string
    {
        $bill = $this->createBill($order);

        return $this->getSDK()->getPayUrl($bill, $redirectUrl);
    }

    public function handle(IPayment $payment): string
    {
        $sdk = $this->getSDK();
        $bill = $this->getBillFromPayment($sdk, $payment);

        return $bill['billId'];
    }

    public function getStatus(IOrder $order): string
    {
        $sdk = $this->getSDK();
        $response = $sdk->getBillInfo($order->getID());

        return $this->getMappedStatus($response['status']['value']);
    }

    private function getSecretKey(): string
    {
        return $this->getConfigValue('SECRET_KEY');
    }

    private function getCurrencyMapper(): array
    {
        // TODO: remake to const
        return [
            'RUB' => 'RUB',
        ];
    }

    private function getCurrencyByCode(string $code): string
    {
        $mapper = $this->getCurrencyMapper();
        if (!array_key_exists($code, $mapper)) {
            throw new PaymentException(sprintf('Incorrect currency "%s"', $code));
        }

        return $mapper[$code];
    }

    private function getStatusMapper(): array
    {
        return [
            self::STATUS_WAITING  => IOrder::STATUS_PENDING,
            self::STATUS_PAID     => IOrder::STATUS_SUCCESS,
            self::STATUS_REJECTED => IOrder::STATUS_FAIL,
            self::STATUS_EXPIRED  => IOrder::STATUS_EXPIRED,
        ];
    }

    private function getMappedStatus(string $status): string
    {
        $mapper = $this->getStatusMapper();
        if (!array_key_exists($status, $mapper)) {
            throw new PaymentException(sprintf('Incorrect status "%s"', $status));
        }

        return $mapper[$status];
    }

    private function getBillFromPayment(BillPayments $sdk, IPayment $payment): array
    {
        $request = $payment->getRequest();
        $requestData = $request['body'];
        $headers = array_change_key_case($request['headers'], CASE_LOWER);

        $sign = $headers['x-api-signature-sha256'] ?? null;
        if (!$sign) {
            throw new PaymentException('Signature was not found!');
        }
        $this->doValidSignature($sdk, $sign, $requestData);

        return $requestData['bill'];
    }

    private function doValidSignature(BillPayments $sdk, string $sign, array $validatedData): void
    {
        $bill = $validatedData['bill'];

        $requestData = [
            'bill'    => [
                'siteId' => $bill['siteId'],
                'billId' => $bill['billId'],
                'amount' => [
                    'value'    => $bill['amount']['value'],
                    'currency' => $bill['amount']['currency']
                ],
                'status' => [
                    'value' => $bill['status']['value']
                ]
            ],
            'version' => $validatedData['version']
        ];

        $result = $sdk->checkNotificationSignature(
            $sign,
            $requestData,
            $this->getSecretKey()
        );

        if (!$result) {
            throw new PaymentException('Invalid Signature');
        }
    }
}
