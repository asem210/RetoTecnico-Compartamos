package com.example.exampleproject.core.components.textfield

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.DecimalFormat

/**
 * Transformación visual para campos de monto.
 * Convierte el input numérico en formato monetario con símbolo de moneda.
 * 
 * Ejemplo: 
 * - Input: "1500" → Display: "S/ 15.00"
 * - Input: "100050" → Display: "S/ 1,000.50"
 */
class AmountVisualTransformation(
    private val currencySymbol: String = "S/"
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        if (original.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        // Convertimos el texto ingresado en número, lo dividimos entre 100 para tener los decimales
        val rawNumber = original.toDoubleOrNull()?.div(100) ?: 0.0

        // Formato con separadores de miles y dos decimales
        val formatter = DecimalFormat("#,##0.00")
        val formattedNumber = "$currencySymbol ${formatter.format(rawNumber)}"

        // OffsetMapping simple: el cursor se mantiene siempre al final
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return formattedNumber.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return original.length
            }
        }

        return TransformedText(AnnotatedString(formattedNumber), offsetMapping)
    }
}
