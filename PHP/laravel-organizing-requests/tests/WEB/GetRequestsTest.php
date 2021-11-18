<?php

namespace Tests\WEB;

use Tests\TestCase;

class GetRequestsTest extends TestCase
{
    /**
     * @test Проверка на успешное открытие страницы списка заявок
     */
    public function test_successful_opening_page()
    {
        $response = $this->get('/requests');
        $response->assertStatus(200);
    }
}
