package com.pet.moneyconvertor.viewmodelfactories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pet.moneyconvertor.R
import com.pet.moneyconvertor.viewmodels.CurrencyListViewModel

class CurrencyListViewModelFactory(private val applicationContext: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyListViewModel::class.java)) {
            return CurrencyListViewModel(applicationContext) as T
        }
        throw IllegalArgumentException(applicationContext.getString(R.string.exception_message_view_model_factory))
    }

}