package com.pet.moneyconvertor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pet.moneyconvertor.room.CurrencyEntity

class SharedRightViewModel : ViewModel() {
    private val _selected = MutableLiveData<CurrencyEntity>()

    val selected: LiveData<CurrencyEntity> get() = _selected

    fun select(currencyEntity: CurrencyEntity) {
        _selected.value = currencyEntity
    }
}