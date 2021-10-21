<?php

namespace App\Models;

use App\Services\Payments\IOrder;
use DateTimeInterface;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsTo;

/**
 * @property int $id
 * @property string $ident
 * @property int $user_id
 * @property float $price
 * @property string $currency
 * @property float $value
 * @property string $comment
 * @property string $status
 * @property DateTimeInterface $created_at
 * @property DateTimeInterface $updated_at
 * @property DateTimeInterface $expires_at
 * @property User $user
 */
class Order extends Model implements IOrder
{
    protected $fillable = [
        'id',
        'ident',
        'user_id',
        'price',
        'currency',
        'value',
        'comment',
        'status',
        'created_at',
        'updated_at',
        'expires_at',
    ];

    protected $dates = [
        'created_at',
        'updated_at',
        'expires_at',
    ];

    protected $casts = [
        'user_id' => 'integer',
        'price'   => 'float',
        'value'   => 'float',
    ];

    public $timestamps = false;

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

    public function getID(): string
    {
        return $this->ident;
    }

    public function getPrice(): float
    {
        return $this->price;
    }

    public function getValue(): float
    {
        return $this->value;
    }

    public function getUserID(): string
    {
        return $this->user->id;
    }

    public function getComment(): string
    {
        return $this->comment;
    }

    public function getCurrency(): string
    {
        return $this->currency;
    }

    public function getStatus(): string
    {
        return $this->status;
    }

    public function getExpires(): DateTimeInterface
    {
        return $this->expires_at;
    }
}
