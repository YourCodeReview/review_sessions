package com.pet.moneyconvertor.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pet.moneyconvertor.repository.CurrencyRepository
import com.pet.moneyconvertor.room.getDatabase
import retrofit2.HttpException

class RefreshCurrencyWorker(appContext: Context, workerParams: WorkerParameters) :
        CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "com.pet.moneyconvertor.workmanager.RefreshCurrencyWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = CurrencyRepository(database)

        try {
            repository.refreshCurrency()
        } catch (e: HttpException) {
            return Result.retry()
        }
        return Result.success()
    }
}