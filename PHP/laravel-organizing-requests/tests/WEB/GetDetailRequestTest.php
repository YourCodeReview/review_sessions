<?php

namespace Tests\WEB;

use App\Models\Request;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileCannotBeAdded;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileDoesNotExist;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileIsTooBig;
use Tests\TestCase;
use Tests\Traits\HasCreatedRequest;
use Tests\Traits\HasNotFoundedRequest;

class GetDetailRequestTest extends TestCase
{
    use HasCreatedRequest, HasNotFoundedRequest;

    /**
     * @var Request
     */
    private Request $request;

    /**
     * @throws FileCannotBeAdded
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    protected function setUp(): void
    {
        parent::setUp(); // TODO: Change the autogenerated stub
        $this->request = $this->getCreatedRequest();
    }

    /**
     * @test Проверка на успешное открытие подробной страницы заявки
     */
    public function test_successful_opening_page()
    {
        $uri = "/requests/{$this->request->id}";
        $response = $this->get($uri);
        $response->assertStatus(200);
    }

    /**
     * @test Проверка на открытие подробной страницы несуществующей заявки
     */
    public function test_wrong_getting_not_founded_request()
    {
        $id = $this->getNotFoundedID();
        $response = $this->get("/requests/$id");
        $response->assertStatus(404);
    }
}
