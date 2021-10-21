<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Carbon\Carbon;

class UserTransaction extends Model
{
    protected $fillable = [
        'user_id',
        'total',
        'amount',
        'commission',
        'comment',
        'created_at',
    ];

    protected $dates = [
        'created_at',
    ];

    protected $casts = [
        'created_at' => 'datetime:Y-m-d H:i:s',

        'user_id'    => 'integer',
        'amount'     => 'float',
        'total'      => 'float',
        'commission' => 'float',
    ];

    public $timestamps = false;

    public function user()
    {
        return $this->belongsTo(User::class);
    }
}
