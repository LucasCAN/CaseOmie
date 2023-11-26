package com.nog.caseomie.utils

import java.text.NumberFormat
import java.util.Locale

object Money {
    fun formatToReal(value: Double): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return currencyFormat.format(value)
    }
}