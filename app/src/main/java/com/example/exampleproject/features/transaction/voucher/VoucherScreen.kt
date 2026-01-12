package com.example.exampleproject.features.transaction.voucher

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.domain.model.transaction.TransactionResult
import com.example.exampleproject.core.presentation.ui.BaseScreen
import java.text.NumberFormat
import java.util.Locale

@Composable
fun VoucherScreen(
    transactionResult: TransactionResult,
    onBackToHome: () -> Unit = {}
) {
    BaseScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título de éxito
            Text(
                text = "¡Transacción Exitosa!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card con detalles de la transacción
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    VoucherDetailRow("Código de operación:", transactionResult.operationCode)
                    VoucherDetailRow("Fecha y hora:", "${transactionResult.operationDate} - ${transactionResult.operationTime}")
                    VoucherDetailRow("Cuenta origen:", transactionResult.originAccountCode)
                    VoucherDetailRow("Cuenta destino:", transactionResult.destinationAccountCode)
                    
                    // Monto formateado
                    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
                    formatter.currency = java.util.Currency.getInstance("PEN")
                    VoucherDetailRow("Monto:", formatter.format(transactionResult.amount))
                    
                    if (transactionResult.commission > 0) {
                        VoucherDetailRow("Comisión:", formatter.format(transactionResult.commission))
                        VoucherDetailRow("Total:", formatter.format(transactionResult.totalAmount))
                    }
                    
                    if (!transactionResult.description.isNullOrBlank()) {
                        VoucherDetailRow("Descripción:", transactionResult.description!!)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón para volver al inicio
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onBackToHome
            ) {
                Text("Volver al Inicio")
            }
        }
    }
}

@Composable
fun VoucherDetailRow(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
