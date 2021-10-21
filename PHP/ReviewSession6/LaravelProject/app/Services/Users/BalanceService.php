<?php

namespace App\Services\Users;

use App\Events\TransactionCreated;
use App\Models\User;
use App\Models\UserTransaction;
use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use Illuminate\Contracts\Events\Dispatcher as DispatcherContract;

class BalanceService
{
    private DispatcherContract $eventDispatcher;
    private CommissionService $commissionService;

    public function __construct(
        DispatcherContract $eventDispatcher,
        CommissionService $commissionService
    )
    {
        $this->eventDispatcher = $eventDispatcher;
        $this->commissionService = $commissionService;
    }

    public function change(User $user, float $amount, string $comment): float
    {
        $this->create($user, $amount, 0, $comment);

        return $user->getCurrentBalance();
    }

    private function create(
        User $user,
        float $amount,
        float $commission,
        string $comment
    ): UserTransaction {
        return DB::transaction(function () use ($user, $amount, $commission, $comment): UserTransaction {

            $values = [
                'amount'     => $amount,
                'total'      => $amount + $commission,
                'commission' => $commission,
                'comment'    => $comment,
                'created_at' => Carbon::now(),
            ];
            /** @var UserTransaction $transaction */
            $transaction = $user->transactions()->create($values);

            $this->eventDispatcher->dispatch(new TransactionCreated($user, $transaction));

            return $transaction;
        });
    }

    public function replenish(
        User $user,
        float $amount,
        float $commission,
        string $comment
    ): void
    {
        $this->create($user, $amount, $commission, $comment);
    }

    public function updateUserBalance(User $user): void
    {
        $user->balance = $user->getCurrentBalance();
        $user->updated_at = Carbon::now();
        $user->save();
    }
}
