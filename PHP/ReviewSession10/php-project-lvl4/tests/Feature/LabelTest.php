<?php

namespace Tests\Feature;

use App\Models\Task;
use App\Models\Label;
use App\Models\TaskStatus;
use App\Models\User;
use Tests\TestCase;

class LabelTest extends TestCase
{

    public User $user ;

    public function setUp(): void
    {
        parent::setUp(); // TODO: Change the autogenerated stub
        $this->user = User::factory()->create();
    }

    public function testIndex(): void
    {
        $this->assertDatabaseCount(Label::class, 0);

        $labels = Label::factory()
            ->count(5)
            ->create()
            ->pluck('name')
            ->all();

        $response = $this->get(route('labels.index'));
        $response->assertStatus(200);
        $response->assertSee($labels);
        $this->assertDatabaseCount(Label::class, 5);
    }

    public function testCreate(): void
    {

        $this->actingAs($this->user)
            ->get(route('labels.create'))
            ->assertStatus(200);
    }


    public function testStore(): void
    {
        $this->assertDatabaseCount(Label::class, 0);
        $data = ['name' => 'new'];
        $response = $this
            ->actingAs($this->user)
            ->post(route('labels.store'), $data);

        $response->assertRedirect(route('labels.index'));
        $this->assertDatabaseHas(Label::class, $data);

        $this->assertDatabaseCount(Label::class, 1);
    }

    public function testEdit(): void
    {
        /** @var Label */
        $status = Label::factory()->create();
        $this
            ->actingAs($this->user)
            ->get(route('labels.edit', [$status->id]))
            ->assertStatus(200);
    }

    public function testUpdate(): void
    {
        /** @var Label */
        $status = Label::factory()->create();
        $updatedData = ['name' => 'updated'];
        $response = $this
            ->actingAs($this->user)
            ->patch(route('labels.update', [$status->id]), $updatedData);
        $response->assertRedirect(route('labels.index'));

        $this->assertDatabaseCount(Label::class, 1);
        $this->assertDatabaseHas(Label::class, $updatedData);
    }

    public function testDestroy(): void
    {
        /** @var  Label */
        $label = Label::factory()->create();
        $this->assertDatabaseCount(Label::class, 1);

        $response = $this
            ->actingAs($this->user)
            ->delete(route('labels.destroy', [$label->id]));
        $response->assertStatus(302);
        $this->assertDatabaseCount(Label::class, 0);
    }

    public function testDestroyWithExistedTask(): void
    {
        /** @var Label */
        $label = Label::factory()->create();
        $status = TaskStatus::factory()->create();
        $taskData = [
            'name'           => 'test',
            'description'    => 'test description',
            'status_id'      => $status->id,
            'created_by_id'  => $this->user->id
        ];
        /** @var Task */
        $task = Task::create($taskData);
        $task->labels()->save($label);
        $task->refresh();

        $this->assertDatabaseCount(Label::class, 1);

        $response = $this
            ->actingAs($this->user)
            ->delete(route('labels.destroy', [$status->id]));

        $this->assertDatabaseCount(Label::class, 1);

        $response->assertStatus(302);
    }
}