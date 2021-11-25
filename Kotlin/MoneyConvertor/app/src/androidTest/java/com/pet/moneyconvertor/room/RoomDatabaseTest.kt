package com.pet.moneyconvertor.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var currencyDao: CurrencyDao
    private lateinit var dataBase: CurrencyDataBase
    private var currency: CurrencyEntity? = null
    private lateinit var currencyList: List<CurrencyEntity>
    private lateinit var currencyListWithRepeatCurrency: List<CurrencyEntity>

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataBase = Room.inMemoryDatabaseBuilder(
            context, CurrencyDataBase::class.java
        ).build()
        currencyDao = dataBase.currencyDao
        currency = CurrencyEntity(
            "1111",
            "637",
            "RUB",
            1,
            "Rubel",
            1.0
        )

        currencyList = listOf(
            CurrencyEntity(
                "1111",
                "637",
                "RUB",
                1,
                "Rubel",
                1.0
            ),
            CurrencyEntity(
                "2222",
                "453",
                "USD",
                1,
                "Dollar",
                30.0
            ),
            CurrencyEntity(
                "3333",
                "849",
                "EUR",
                1,
                "Euro",
                60.0
            ),
            CurrencyEntity(
                "4444",
                "568",
                "Indian rupee",
                1,
                "INR",
                2.0
            )
        )
        currencyListWithRepeatCurrency = listOf(
            CurrencyEntity(
                "1111",
                "637",
                "RUB",
                1,
                "Rubel",
                1.0
            ),
            CurrencyEntity(
                "2222",
                "453",
                "USD",
                1,
                "Dollar",
                30.0
            ),
            CurrencyEntity(
                "3333",
                "849",
                "EUR",
                1,
                "Euro",
                60.0
            ),
            CurrencyEntity(
                "4444",
                "568",
                "Indian rupee",
                1,
                "INR",
                2.0
            ),
            CurrencyEntity(
                "3333",
                "849",
                "EUR",
                1,
                "Euro",
                60.0
            ),
            CurrencyEntity(
                "1111",
                "637",
                "RUB",
                1,
                "Rubel",
                1.0
            )
        )

    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        dataBase.close()
        currency = null
        currencyList = emptyList()
        currencyListWithRepeatCurrency = emptyList()
    }

    @Test
     fun saveAndLoadOneCurrency() = runBlocking {
        currency?.let { currencyDao.save(it) }
        val actual = getAllCurrency()
        assertEquals(currency, actual[0])
    }

    @Test
    fun saveListCurrencyWithRepeatElements() = runBlocking {
        currencyDao.saveAll(currencyListWithRepeatCurrency)
        val actual = getAllCurrency()
        assertEquals( currencyList, actual.sortedBy { it.id })
    }


    @Test
    fun saveAndLoadAllCurrency() = runBlocking {
        currencyDao.saveAll(currencyList)
        val actual = getAllCurrency()
        assertEquals(currencyList, actual)
    }

    @Test
    fun saveListCurrencyAndFindByName() = runBlocking {
        currencyDao.saveAll(currencyList)
        val actual = findByValue("Rubel")
        assertEquals(currency, actual[0])
    }

    @Test
    fun saveListCurrencyAndFindByCharCode() = runBlocking {
        currencyDao.saveAll(currencyList)
        val actual = findByValue("RUB")
        assertEquals(currency, actual[0])
    }

    private suspend fun getAllCurrency(): List<CurrencyEntity> {
        return withContext(Dispatchers.Default) {
            return@withContext currencyDao.loadAll()
        }
    }
    private suspend fun findByValue(value: String): List<CurrencyEntity> {
        return withContext(Dispatchers.Default) {
            return@withContext currencyDao.findByNameOrCharCode(value)
        }
    }
}