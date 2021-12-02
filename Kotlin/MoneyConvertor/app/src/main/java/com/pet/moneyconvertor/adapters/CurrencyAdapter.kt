package com.pet.moneyconvertor.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pet.moneyconvertor.databinding.ItemCurrencyBinding
import com.pet.moneyconvertor.room.CurrencyEntity

class CurrencyAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<CurrencyEntity, CurrencyAdapter.CurrencyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemCurrencyBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currencyEntity = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(currencyEntity)
        }
        holder.bind(currencyEntity)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CurrencyEntity>() {
        override fun areItemsTheSame(oldItem: CurrencyEntity, newItem: CurrencyEntity): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: CurrencyEntity, newItem: CurrencyEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class CurrencyViewHolder(private var binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currencyEntity: CurrencyEntity) {
            binding.currency = currencyEntity
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (currencyEntity: CurrencyEntity) -> Unit) {
        fun onClick(currencyEntity: CurrencyEntity) = clickListener(currencyEntity)
    }
}