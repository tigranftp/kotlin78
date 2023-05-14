package com.example.currencyrates.retrofit

import retrofit2.http.Path
import java.time.LocalDate

class Repository {
    suspend fun getCurrencyRates(): Currency {
        return CurrencyAPI.api.getCurrencyRates()
    }


    suspend fun getCurrencyRatesForInterval(start: LocalDate, end: LocalDate): CurrencyByTime {
        return CurrencyAPI.api.getCurrencyRatesForInterval(start, end)
    }
}