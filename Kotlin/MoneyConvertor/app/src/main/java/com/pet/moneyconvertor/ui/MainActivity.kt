package com.pet.moneyconvertor.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.pet.moneyconvertor.R
import com.pet.moneyconvertor.databinding.ActivityMainBinding
import com.pet.moneyconvertor.room.CurrencyEntity
import com.pet.moneyconvertor.room.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var rubleCurrency: CurrencyEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MoneyConvertor)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        val sp: SharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        val notAddRuble: Boolean = sp.getBoolean(getString(R.string.add_ruble_key), false)

        if (!notAddRuble) {
            val e: SharedPreferences.Editor = sp.edit()
            e.putBoolean(getString(R.string.add_ruble_key), true)
            runBlocking {
                saveCurrency()
            }
            e.apply()
        }

    }

    private suspend fun saveCurrency() {
        rubleCurrency = CurrencyEntity(
            "1111",
            "637",
            "RUB",
            1,
            "Рубль",
            1.0
        )
        val database = getDatabase(applicationContext)
        withContext(Dispatchers.IO) { database.currencyDao.save(rubleCurrency) }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}