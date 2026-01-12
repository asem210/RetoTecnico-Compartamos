package com.example.exampleproject.core.utils

/**
 * Convierte el valor string del input (sin decimales) a Double con decimales.
 * 
 * Ejemplo:
 * - "1500" → 15.00
 * - "100050" → 1000.50
 * - "" → 0.0
 */
fun String.toAmountDouble(): Double {
    return this.toDoubleOrNull()?.div(100) ?: 0.0
}

/**
 * Formatea un Double como string para el input de monto.
 * 
 * Ejemplo:
 * - 15.00 → "1500"
 * - 1000.50 → "100050"
 */
fun Double.toAmountString(): String {
    return (this * 100).toInt().toString()
}
