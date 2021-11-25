package com.pet.moneyconvertor.room

import android.content.Context
import androidx.room.Database
import androidx.room.*

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}

private lateinit var INSTANCE: CurrencyDataBase

fun getDatabase(context: Context): CurrencyDataBase {
    synchronized(CurrencyDataBase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CurrencyDataBase::class.java,
                "currencies"
            ).build()
        }
    }
    return INSTANCE
}
