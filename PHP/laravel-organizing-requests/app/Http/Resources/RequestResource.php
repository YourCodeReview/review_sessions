<?php

namespace App\Http\Resources;

use App\Enums\MediaCollections;
use Illuminate\Http\Resources\Json\JsonResource;
use Illuminate\Http\Request;

class RequestResource extends JsonResource
{
    /**
     * @param Request $request
     * @return array
     */
    public function toArray($request): array
    {
        return [
            'id' => $this->id,
            'title' => $this->title,
            'image' => $this->getFirstMediaUrl(MediaCollections::REQUESTS_IMAGES),
            'content' => $this->content,
            'status' => $this->status,
            'created_at' => $this->created_at->format('Y-m-d'),
            'completion_at' => $this->completion_at,
        ];
    }
}
