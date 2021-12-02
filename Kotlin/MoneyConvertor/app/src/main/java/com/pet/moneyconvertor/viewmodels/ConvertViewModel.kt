package com.pet.moneyconvertor.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pet.moneyconvertor.repository.CurrencyRepository
import com.pet.moneyconvertor.room.CurrencyEntity
import com.pet.moneyconvertor.room.getDatabase
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ConvertViewModel(applicationContext: Application) : ViewModel() {
    val startInputValue: LiveData<Boolean>
        get() = _startInputValue
    val leftCurrency: LiveData<CurrencyEntity>
        get() = _leftCurrency
    val rightCurrency: LiveData<CurrencyEntity>
        get() = _rightCurrency
    val convertResult: LiveData<Double>
        get() = _convertResult

    private val _leftCurrency = MutableLiveData<CurrencyEntity>()
    private val _rightCurrency = MutableLiveData<CurrencyEntity>()
    private val _convertResult = MutableLiveData<Double>()
    private val dataBase = getDatabase(applicationContext)
    private val repository = CurrencyRepository(dataBase)
    private val _startInputValue = MutableLiveData<Boolean>()


    init {
        refreshDataFromRepository()
        _startInputValue.value = false
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            repository.refreshCurrency()
        }
    }

    fun setLeftCurrency(currencyEntity: CurrencyEntity) {
        _leftCurrency.value = currencyEntity
        onEnabledInputValue()
    }

    fun setRightCurrency(currencyEntity: CurrencyEntity) {
        _rightCurrency.value = currencyEntity
        onEnabledInputValue()
    }

    fun convert(value: String) {
        if (value.isNotEmpty()) {
            val round = 100.0
            val leftValue = leftCurrency.value?.value
            val rightValue = rightCurrency.value?.value
            val valueConvert = value.toDouble()
            _convertResult.value =
                ((valueConvert * leftValue!! / rightValue!!) * round).roundToInt() / round
        }
    }

    private fun onEnabledInputValue() {
        _startInputValue.value = (_leftCurrency.value != null && _rightCurrency.value != null)
    }

    fun swapCurrency(value: String) {
        val temp = _leftCurrency.value
        _leftCurrency.value = _rightCurrency.value
        _rightCurrency.value = temp
        this.convert(value)
    }

}