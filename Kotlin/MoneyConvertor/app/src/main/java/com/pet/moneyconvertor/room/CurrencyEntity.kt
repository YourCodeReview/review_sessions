package com.pet.moneyconvertor.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pet.moneyconvertor.api.Currency

@Entity
data class CurrencyEntity(
    @PrimaryKey
    var id: String,
    var numCode: String? = null,
    var charCode : String? = null,
    var nominal: Int? = null,
    var name: String? = null,
    var value: Double? = null
)

fun List<CurrencyEntity>.asDomainModel(): List<Currency> {
    return map {
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