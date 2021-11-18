<?php

namespace Tests\API;

use Tests\TestCase;

class GetRequestsTest extends TestCase
{
    /**
     * @test Проверка на успешное получение списка заявок
     */
    public function test_successful_getting_requests_list()
    {
        $response = $this->getJson('/api/requests');
        $response->assertJsonCount(config('pagination.limit'), 'data');
        $response->assertStatus(200);
    }
}
