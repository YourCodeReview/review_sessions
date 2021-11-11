<?php

namespace Database\Seeders;

use App\Enums\MediaCollections;
use App\Models\Request;
use Database\Seeders\Traits\HasRandomImage;
use Illuminate\Database\Seeder;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileCannotBeAdded;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileDoesNotExist;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileIsTooBig;

class RequestsSeeder extends Seeder
{
    use HasRandomImage;

    public const COUNT = 20;

    /**
     * Run the database seeds.
     *
     * @return void
     * @throws FileCannotBeAdded
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    public function run(): void
    {
        for ($i = 0; $i < self::COUNT; $i++) {
            $this->create();
        }
    }

    /**
     * @throws FileCannotBeAdded
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    private function create()
    {
        /** @var Request $request */
        $request = Request::factory()->create();
        $this->createImage($request);
    }

    /**
     * @param Request $request
     * @throws FileCannotBeAdded
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    private function createImage(Request $request): void
    {
        $imageURL = $this->getRandomImageURL();

        $request->addMediaFromUrl($imageURL)
            ->toMediaCollection(MediaCollections::REQUESTS_IMAGES);
    }
}
