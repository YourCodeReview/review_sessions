<?php

namespace Tests\Traits;

use App\Models\Request;
use Illuminate\Http\UploadedFile;

trait HasRequestData
{
    /**
     * @return array
     */
    public function getRequestData(): array
    {
        $request = Request::factory()->make()->toArray();
        $image = UploadedFile::fake()->image('image.png', 200, 200);
        $request['image'] = $image;

        return $request;
    }
}
