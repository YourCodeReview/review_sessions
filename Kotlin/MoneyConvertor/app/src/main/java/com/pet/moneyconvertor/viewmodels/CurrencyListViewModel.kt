package com.pet.moneyconvertor.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pet.moneyconvertor.repository.CurrencyRepository
import com.pet.moneyconvertor.room.getDatabase
import kotlinx.coroutines.launch

class CurrencyListViewModel(applicationContext: Application) : ViewModel() {
    private val database = getDatabase(applicationContext)
    private val repository = CurrencyRepository(database)

    var currencies = repository.currencies

    init {
        loadAllCurrency()
    }
    fun searchCurrency(value: String) {
        viewModelScope.launch {
            repository.searchCurrency(value)
        }
    }

    private fun loadAllCurrency() {
        viewModelScope.launch {
            repository.loadAllCurrency()
        }
    }
}