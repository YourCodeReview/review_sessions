<?php

namespace Tests\WEB;

use Tests\TestCase;
use Tests\Traits\HasRequestData;

class CreateRequestTest extends TestCase
{
    use HasRequestData;

    /**
     * @test Проверка на успешное открытие страницы создания заявки
     */
    public function test_successful_opening_page()
    {
        $response = $this->get('/requests/new');
        $response->assertStatus(200);
    }

    /**
     * @test Проверка на успешное создание заявки
     */
    public function test_successful_creating_request()
    {
        $response = $this->post('/requests', $this->getRequestData());
        $response->assertStatus(302);
        $response->assertValid();
    }

    /**
     * @test Проверка на неверное создание заявки
     */
    public function test_wrong_creating_request()
    {
        $response = $this->post('/requests', []);
        $response->assertStatus(302);
        $response->assertInvalid();
    }
}
