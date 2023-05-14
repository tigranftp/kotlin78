package com.example.currencyrates.retrofit

data class Currency (
        val rates: Rates
        )


data class Rates (
    val EUR: Float,
    val CAD: Float,
    val GBP: Float,
        )


