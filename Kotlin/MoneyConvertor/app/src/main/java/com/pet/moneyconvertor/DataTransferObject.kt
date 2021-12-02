package com.pet.moneyconvertor

import com.pet.moneyconvertor.api.Currency
import com.pet.moneyconvertor.room.CurrencyEntity


data class NetworkCurrencyContainer(val currencies: List<Currency>)

fun NetworkCurrencyContainer.asDomainModel(): List<Currency> {
    return currencies.map {
        Currency(
            id = it.id,
            numCode = it.numCode,
            charCode = it.charCode,
            nominal = it.nominal.toString(),
            name = it.name,
            value = it.value.toString()
        )
    }
}

fun NetworkCurrencyContainer.asDatabaseModel(): List<CurrencyEntity> {
    return currencies.map {
        CurrencyEntity(
            id = it.id.toString(),
            numCode = it.numCode,
            charCode = it.charCode,
            nominal = it.nominal?.toInt(),
            name = it.name,
            value = it.value?.replace(",",".")?.toDouble()
        )
    }
}