<?php

namespace App\Models;

use App\Traits\AccessCreatedAt;
use Database\Factories\TaskFactory;
use Eloquent;
use Illuminate\Database\Eloquent\Builder;
use Illuminate\Database\Eloquent\Collection;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;
use Illuminate\Database\Eloquent\Relations\HasOne;
use Illuminate\Support\Carbon;

/**
 * App\Models\Task
 *
 * @property int $id
 * @property string $name
 * @property string $description
 * @property int $status_id
 * @property int $created_by_id
 * @property int|null $assigned_to_id
 * @property Carbon $updated_at
 * @property Carbon $created_at
 * @method static Builder|Task newModelQuery()
 * @method static Builder|Task newQuery()
 * @method static Builder|Task query()
 * @method static Builder|Task whereAssignedToId($value)
 * @method static Builder|Task whereCreatedAt($value)
 * @method static Builder|Task whereCreatedById($value)
 * @method static Builder|Task whereDescription($value)
 * @method static Builder|Task whereId($value)
 * @method static Builder|Task whereName($value)
 * @method static Builder|Task whereStatusId($value)
 * @method static Builder|Task whereUpdatedAt($value)
 * @mixin Eloquent
 * @property-read User $creator
 * @property-read User|null $executor
 * @property-read TaskStatus|null $status
 * @property-read Collection|Label[] $labels
 * @property-read int|null $labels_count
 * @method static TaskFactory factory(...$parameters)
 */
class Task extends Model
{
    use HasFactory;
    use AccessCreatedAt;

    protected $table = 'tasks';

    protected $fillable = ['name', 'description', 'status_id', 'assigned_to_id', 'created_by_id', 'updated_at', 'created_at'];

    /**
     * @return HasOne
     */
    public function status(): HasOne
    {
        return $this->hasOne(TaskStatus::class, 'id', 'status_id');
    }

    /**
     * @return HasOne
     */
    public function creator(): HasOne
    {
        return $this->hasOne(User::class, 'id', 'created_by_id');
    }

    /**
     * @return HasOne
     */
    public function executor(): HasOne
    {
        return $this->hasOne(User::class, 'id', 'assigned_to_id');
    }

    /**
     * @return BelongsToMany
     */
    public function labels(): BelongsToMany
    {
        return $this->belongsToMany(Label::class, 'task_label');
    }
}
