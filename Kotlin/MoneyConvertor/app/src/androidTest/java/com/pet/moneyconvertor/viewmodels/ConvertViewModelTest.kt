package com.pet.moneyconvertor.viewmodels

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pet.moneyconvertor.room.CurrencyEntity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConvertViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private var viewModel: ConvertViewModel? = null
    private var rightCurrency: CurrencyEntity? = null
    private var leftCurrency: CurrencyEntity? = null

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        viewModel = ConvertViewModel(context as Application)
        leftCurrency = CurrencyEntity(
            "1111",
            "637",
            "RUB",
            1,
            "Rubel",
            1.0
        )
        rightCurrency = CurrencyEntity(
            "2222",
            "345",
            "USD",
            1,
            "Dollar",
            71.2866
        )
    }

    @After
    fun tearDown() {
        viewModel = null
        leftCurrency = null
        rightCurrency = null
    }

    @Test
    fun swapCurrencies() {
        leftCurrency?.let { viewModel?.setLeftCurrency(it) }
        rightCurrency?.let { viewModel?.setRightCurrency(it) }
        viewModel?.swapCurrency("")

        assertEquals(leftCurrency, viewModel?.rightCurrency?.value)
        assertEquals(rightCurrency, viewModel?.leftCurrency?.value)
    }
    @Test
    fun swapCurrenciesWhereLeftCurrencyIsNull() {
        rightCurrency?.let { viewModel?.setRightCurrency(it) }
        viewModel?.swapCurrency("")

        assertEquals(rightCurrency, viewModel?.leftCurrency?.value)
        assertEquals(null, viewModel?.rightCurrency?.value)
    }

    @Test
    fun swapCurrenciesWhereRightCurrencyIsNull() {
        leftCurrency?.let { viewModel?.setLeftCurrency(it) }
        viewModel?.swapCurrency("")

        assertEquals(leftCurrency, viewModel?.rightCurrency?.value)
        assertEquals(null, viewModel?.leftCurrency?.value)
    }

    @Test
    fun setLeftCurrency() {
        leftCurrency?.let { viewModel?.setLeftCurrency(it) }
        val actual = viewModel?.leftCurrency?.value
        assertEquals(leftCurrency, actual)
    }

    @Test
    fun setRightCurrency() {
        rightCurrency?.let { viewModel?.setLeftCurrency(it) }
        val actual = viewModel?.leftCurrency?.value
        assertEquals(rightCurrency, actual)
    }

    @Test
    fun convertLeftCurrencyToRightCurrency() {
        leftCurrency?.let { viewModel?.setLeftCurrency(it) }
        rightCurrency?.let { viewModel?.setRightCurrency(it) }
        viewModel?.convert("131.7")
        val excepted = 1.85
        val actual = viewModel?.convertResult?.value
        assertEquals(excepted, actual)
    }

    @Test
    fun convertRightCurrencyToLeftCurrency() {
        rightCurrency?.let { viewModel?.setLeftCurrency(it) }
        leftCurrency?.let { viewModel?.setRightCurrency(it) }
        viewModel?.convert("131.7")
        val excepted = 9388.45
        val actual = viewModel?.convertResult?.value
        assertEquals(excepted, actual)
    }
}