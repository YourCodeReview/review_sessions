package com.pet.moneyconvertor.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CurrencyDao {
    @Insert(onConflict = REPLACE)
    fun save(currency: CurrencyEntity)
    @Insert(onConflict = REPLACE)
    fun saveAll(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM currencyEntity")
    suspend fun loadAll(): List<CurrencyEntity>

    @Query("SELECT * FROM currencyEntity WHERE name LIKE :value || '%' OR charCode LIKE :value || '%'")
    suspend fun findByNameOrCharCode(value: String): List<CurrencyEntity>
}