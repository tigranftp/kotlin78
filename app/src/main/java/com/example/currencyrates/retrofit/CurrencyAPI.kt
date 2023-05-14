package com.example.currencyrates.retrofit

import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate

interface CurrencyAPI {
    @GET("/latest?from=USD")
    suspend fun getCurrencyRates(): Currency


    @GET("/{start}..{end}?from=USD")
    suspend fun getCurrencyRatesForInterval(
        @Path("start") start: LocalDate,
        @Path("end") end: LocalDate
    ): CurrencyByTime

    companion object InstanceCurrencyAPI {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.frankfurter.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val api: CurrencyAPI by lazy {
            retrofit.create(CurrencyAPI::class.java)
        }
    }
}