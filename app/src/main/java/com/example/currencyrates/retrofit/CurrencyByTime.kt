package com.example.currencyrates.retrofit

data class CurrencyByTime (
    val rates: MutableMap<String, Rates>
    )