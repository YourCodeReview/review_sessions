<?php

namespace Tests\Traits;

use App\Enums\MediaCollections;
use App\Models\Request;
use Database\Seeders\Traits\HasRandomImage;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileCannotBeAdded;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileDoesNotExist;
use Spatie\MediaLibrary\MediaCollections\Exceptions\FileIsTooBig;

trait HasCreatedRequest
{
    use HasRandomImage;

    /**
     * @return Request
     * @throws FileCannotBeAdded
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    public function getCreatedRequest(): Request
    {
        /** @var Request $request */
        $request = Request::factory()->create();
        $this->createImage($request);
        return $request;
    }

    /**
     * @param Request $request
     * @throws FileCannotBeAdded
     * @throws FileDoesNotExist
     * @throws FileIsTooBig
     */
    private function createImage(Request &$request): void
    {
        $imageURL = $this->getRandomImageURL();

        $request->addMediaFromUrl($imageURL)
            ->toMediaCollection(MediaCollections::REQUESTS_IMAGES);
    }
}
