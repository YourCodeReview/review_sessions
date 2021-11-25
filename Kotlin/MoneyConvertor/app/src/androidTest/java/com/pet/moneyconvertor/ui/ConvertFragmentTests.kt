package com.pet.moneyconvertor.ui


import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.internal.ContextUtils.getActivity
import com.pet.moneyconvertor.R
import com.pet.moneyconvertor.adapters.CurrencyAdapter
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ConvertFragmentTests {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun leftCurrencyButtonIsDisplayed() {
        val button = withId(R.id.buttonLeft)
        onView(button).check(matches(isDisplayed()))
        onView(button).check(matches(isClickable()))
        onView(button).check(matches(isEnabled()))
    }

    @Test
    fun rightCurrencyButtonIsDisplayed() {
        val button = withId(R.id.buttonRight)
        onView(button).check(matches(isDisplayed()))
        onView(button).check(matches(isClickable()))
        onView(button).check(matches(isEnabled()))
    }

    @Test
    fun textEditValueIsNotFocusable() {
        onView(withId(R.id.editTextValue)).check(matches(isNotFocusable()))
    }

    @Test
    fun textEditValueClickWhileIsNotFocusable() {
        onView(withId(R.id.editTextValue)).perform(click())
        onView(withText(R.string.enabled_toast_message)).inRoot(
            withDecorView(
                not(
                    `is`(
                        getActivity(ApplicationProvider.getApplicationContext())?.window?.decorView
                    )
                )
            )
        ).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun selectLeftCurrency() {
        onView(withId(R.id.buttonLeft)).perform(click())
        onView(withId(R.id.currencyList))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<CurrencyAdapter.CurrencyViewHolder>(
                        0, click()
                    )
            )
        onView(withId(R.id.textViewLeft)).check(matches(withText("AUD")))
    }
    @Test
    fun selectRightCurrency() {
        onView(withId(R.id.buttonRight)).perform(click())
        onView(withId(R.id.currencyList))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<CurrencyAdapter.CurrencyViewHolder>(
                        1, click()
                    )
            )
        onView(withId(R.id.textViewRight)).check(matches(withText("AZN")))
    }

    @Test
    fun editTextIsFocusableAfterSelectCurrencies() {
        onView(withId(R.id.buttonLeft)).perform(click())
        onView(withId(R.id.currencyList))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<CurrencyAdapter.CurrencyViewHolder>(
                        0, click()
                    )
            )

        onView(withId(R.id.buttonRight)).perform(click())
        onView(withId(R.id.currencyList))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<CurrencyAdapter.CurrencyViewHolder>(
                        1, click()
                    )
            )

        onView(withId(R.id.editTextValue)).check(matches(isFocusable()))
    }
}