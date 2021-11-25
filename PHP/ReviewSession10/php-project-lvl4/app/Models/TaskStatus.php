<?php

namespace App\Models;

use App\Traits\AccessCreatedAt;
use Database\Factories\TaskStatusFactory;
use Eloquent;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\HasMany;
use Illuminate\Support\Carbon;

/**
 * App\Models\TaskStatus
 *
 * @property int $id
 * @property string $name
 * @property Carbon|null $created_at
 * @property Carbon|null $updated_at
 * @method static Builder|TaskStatus newModelQuery()
 * @method static Builder|TaskStatus newQuery()
 * @method static Builder|TaskStatus query()
 * @method static Builder|TaskStatus whereCreatedAt($value)
 * @method static Builder|TaskStatus whereId($value)
 * @method static Builder|TaskStatus whereName($value)
 * @method static Builder|TaskStatus whereUpdatedAt($value)
 * @mixin Eloquent
 * @property-read Collection|Task[] $tasks
 * @property-read int|null $tasks_count
 * @method static \Database\Factories\TaskStatusFactory factory(...$parameters)
 */

class TaskStatus extends Model
{
    use HasFactory;
    use AccessCreatedAt;

    protected $fillable = ['name', 'created_at', 'updated_at'];

    /**
     * @return HasMany
     */
    public function tasks(): HasMany
    {
        return $this->hasMany(Task::class, 'status_id', 'id');
    }
}
