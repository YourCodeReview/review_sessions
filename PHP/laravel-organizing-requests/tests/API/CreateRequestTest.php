<?php

namespace Tests\API;

use Tests\TestCase;
use Tests\Traits\HasRequestData;

class CreateRequestTest extends TestCase
{
    use HasRequestData;

    /**
     * @test Проверка на успешное создание заявки
     */
    public function test_successful_creating_request()
    {
        $response = $this->postJson('/api/requests', $this->getRequestData());
        $response->assertStatus(201);
    }

    /**
     * @test Проверка на неверное создание заявки
     */
    public function test_wrong_creating_request()
    {
        $response = $this->postJson('/api/requests', []);
        $response->assertStatus(422);
    }
}
