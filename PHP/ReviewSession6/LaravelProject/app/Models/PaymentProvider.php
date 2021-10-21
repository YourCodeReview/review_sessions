<?php

namespace App\Models;

use App\Services\Payments\Models\IProviderModel;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Model;

/**
 * @property string $ident
 * @property string $name
 * @property string $status
 * @property string $classname
 */
class PaymentProvider extends Model implements IProviderModel
{
    public const STATUS_ACTIVE   = 'active';
    public const STATUS_DISABLED = 'disabled';

    protected $fillable = [
        'ident',
        'name',
        'status',
        'classname',
    ];

    protected $primaryKey = 'ident';

    protected $keyType = 'string';
    public $incrementing = false;
    public $timestamps = false;

    public function scopeActive($query): Builder
    {
        return $query->where('status', static::STATUS_ACTIVE);
    }

    public function getIdent(): string
    {
        return $this->ident;
    }

    public function getName(): string
    {
        return $this->name;
    }

    public function getClassname(): string
    {
        return $this->classname;
    }
}
