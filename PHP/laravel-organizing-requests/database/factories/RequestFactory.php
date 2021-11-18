<?php

namespace Database\Factories;

use App\Models\Status;
use App\Repositories\StatusesRepository;
use Illuminate\Database\Eloquent\Factories\Factory;

class RequestFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array
     */
    public function definition(): array
    {
        return [
            'title' => $this->faker->sentence(5),
            'content' => $this->faker->text(300),
            'status_id' => $this->getStatus()->id,
            'completion_at' => now()->addDays(rand(1, 50))->format('Y-m-d'),
        ];
    }

    /**
     * @return Status
     */
    private function getStatus(): Status
    {
        return app(StatusesRepository::class)->getRandom();
    }
}
