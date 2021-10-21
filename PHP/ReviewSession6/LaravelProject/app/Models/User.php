<?php

namespace App\Models;

use DateTimeInterface;
use Illuminate\Contracts\Auth\MustVerifyEmail;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use App\Casts\Json;
use Carbon\Carbon;
use App\Services\Auth\IAccountable;
use App\Models\UserAccount;
use App\Models\UserReview;
use App\Traits\HasRoleAndPermission;

/**
 * @property int $id
 * @property string $name
 * @property string $avatar
 * @property string $settings
 * @property DateTimeInterface $created_at
 * @property DateTimeInterface $updated_at
 * @property DateTimeInterface $visited_at
 * @property DateTimeInterface $deleted_at
 */
class User extends Authenticatable implements IAccountable
{
    use Notifiable;
    use SoftDeletes;
    use HasFactory;
    use HasRoleAndPermission;

    public const TABLE_NAME =  'users';

    private const ONLINE_TIME = 120;

    protected $fillable = [
        'name',
        'avatar',
        'created_at',
        'updated_at',
        'visited_at',
    ];

    protected $dates = [
        'created_at',
        'updated_at',
        'visited_at',
        'deleted_at',
    ];

    protected $casts = [
        'created_at' => 'datetime:Y-m-d H:i:s',
        'updated_at' => 'datetime:Y-m-d H:i:s',
        'deleted_at' => 'datetime:Y-m-d H:i:s',
        'visited_at' => 'datetime:Y-m-d H:i:s',

        'settings' => Json::class,
    ];

    public $timestamps = false;

    public function setSettings(array $values)
    {
        $this->attributes['settings'] = json_encode($values);
    }

    public function getSettings(): array
    {
        $settings = json_decode($this->attributes['settings'], true);
        if (!$settings) {
            $settings = [];
        }
        return $settings;
    }


    public function getCurrentBalance(): float
    {
        return $this->transactions()->sum('amount');
    }
}
