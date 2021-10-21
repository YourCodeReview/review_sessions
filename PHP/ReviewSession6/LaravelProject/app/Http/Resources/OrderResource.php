<?php

namespace App\Http\Resources;

class OrderResource extends AbstractResource
{
    /**
     * Transform the resource into an array.
     *
     * @param \Illuminate\Http\Request $request
     * @return array|\Illuminate\Contracts\Support\Arrayable|\JsonSerializable
     */
    public function toArray($request)
    {
        return [
            'ident'      => $this->ident,
            'user_id'    => $this->user_id,
            'price'      => $this->price,
            'currency'   => $this->currency,
            'value'      => $this->value,
            'comment'    => $this->comment,
            'pay_url'    => $this->pay_url,
            'status'     => $this->status,
            'created_at' => $this->created_at->format($this->getDateTimeFormat()),
            'expires_at' => $this->expires_at->format($this->getDateTimeFormat()),
        ];
    }
}
