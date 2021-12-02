package com.pet.moneyconvertor.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pet.moneyconvertor.room.CurrencyEntity

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CurrencyEntity>?) {
    val adapter = recyclerView.adapter as CurrencyAdapter
    adapter.submitList(data)
}