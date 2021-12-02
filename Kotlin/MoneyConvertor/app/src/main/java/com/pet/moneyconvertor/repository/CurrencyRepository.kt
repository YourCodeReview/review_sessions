package com.pet.moneyconvertor.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pet.moneyconvertor.NetworkCurrencyContainer
import com.pet.moneyconvertor.api.CurrencyApi
import com.pet.moneyconvertor.asDatabaseModel
import com.pet.moneyconvertor.room.CurrencyDataBase
import com.pet.moneyconvertor.room.CurrencyEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CurrencyRepository(private val database: CurrencyDataBase) {
    val currencies:LiveData<List<CurrencyEntity>> get() = _currencies
    private val _currencies = MutableLiveData<List<CurrencyEntity>>()

    suspend fun refreshCurrency() {
        withContext(Dispatchers.IO) {
            try {
                val networkCurrencies = CurrencyApi.retrofitService.getValCurs().valList?.let {
                    NetworkCurrencyContainer(
                        it
                    )
                }
                networkCurrencies?.let { database.currencyDao.saveAll(it.asDatabaseModel()) }
            } catch (e: Exception) {
                Timber.v(e)
            }
        }
    }
    suspend fun loadAllCurrency() {
        _currencies.value = database.currencyDao.loadAll()
    }
    suspend fun searchCurrency(value: String) {
        _currencies.value =  database.currencyDao.findByNameOrCharCode(value)
    }
}