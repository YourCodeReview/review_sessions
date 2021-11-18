<?php

namespace Database\Seeders;

use App\Enums\Statuses;
use App\Models\Status;
use Illuminate\Database\Seeder;

class StatusesSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run(): void
    {
        foreach (Statuses::values() as $status) {
            Status::factory()->create([
                'name' => $status,
            ]);
        }
    }
}
