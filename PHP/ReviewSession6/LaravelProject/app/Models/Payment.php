<?php

namespace App\Models;

use Carbon\Carbon;
use DateTimeInterface;
use Illuminate\Database\Eloquent\Model;
use App\Services\Payments\IPayment;
use Illuminate\Database\Eloquent\Relations\BelongsTo;
use App\Casts\Json;

/**
 * @property int $id
 * @property string $provider_ident
 * @property string $order_ident
 * @property string $status
 * @property array $data
 * @property array|null $error
 * @property DateTimeInterface $created_at
 * @property DateTimeInterface|null $updated_at
 */
class Payment extends Model implements IPayment
{
    protected $fillable = [
        'id',
        'provider_ident',
        'order_ident',
        'status',
        'data',
        'error',
        'created_at',
        'updated_at',
    ];

    protected $dates = [
        'created_at',
        'updated_at',
    ];

    protected $casts = [
        'data' => Json::class,
        'error' => Json::class,
    ];

    public $timestamps = false;

    public static function boot()
    {
        parent::boot();

        self::creating(function ($model) {
            $model->created_at = Carbon::now();
        });
       static::updating(function($model) {
           $model->updated_at = Carbon::now();
       });
    }

    public function user(): BelongsTo
    {
        return $this->belongsTo(User::class);
    }

    public function provider(): BelongsTo
    {
        return $this->belongsTo(PaymentProvider::class, 'provider_ident', 'ident');
    }

    public function getStatus(): string
    {
        return $this->status;
    }

    public function getCreated(): DateTimeInterface
    {
        return $this->created_at;
    }

    public function getProviderIdent(): string
    {
        return $this->provider_ident;
    }

    public function getRequest(): array
    {
        return $this->data;
    }

    public function setDataAttribute($value)
    {
        $this->attributes['data'] = json_encode($value);
    }

    public function getDataAttribute($value)
    {
        return json_decode($value, true);
    }
}
