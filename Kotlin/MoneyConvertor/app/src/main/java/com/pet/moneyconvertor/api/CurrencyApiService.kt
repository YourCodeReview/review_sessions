package com.pet.moneyconvertor.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.cbr.ru/scripts/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(SimpleXmlConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(OkHttpClient())
    .build()
interface CurrencyApiService {
    @GET("XML_daily.asp?")
    suspend fun getValCurs(): ValCurs
}

object CurrencyApi {
    val retrofitService: CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }
}