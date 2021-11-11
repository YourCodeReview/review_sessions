<?php

namespace Database\Seeders\Traits;

trait HasRandomImage
{
    /**
     * @param int $width
     * @param int $height
     * @return string
     */
    public function getRandomImageURL(int $width = 200, int $height = 200): string
    {
        return "https://picsum.photos/$width/$height";
    }
}
