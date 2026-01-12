package com.example.exampleproject.features.transaction.voucher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.model.account.Account
import com.example.exampleproject.core.presentation.ui.BaseScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun VoucherScreen(
    account: Account,
    amount: String,
    description: String,
    viewModel: VoucherViewModel = koinViewModel { parametersOf(account, amount, description) },
    onBackToHome: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    // Navigation handler
    LaunchedEffect(viewModel.navigation) {
        viewModel.navigation.collect { nav ->
            when (nav) {
                VoucherNavigation.GoToHome -> onBackToHome()
            }
        }
    }

    BaseScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Success Icon
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "¡Transacción Exitosa!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            }

            // Voucher Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Comprobante de Operación",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    HorizontalDivider()

                    VoucherRow(
                        label = "Nº de Operación",
                        value = uiState.transactionId
                    )

                    VoucherRow(
                        label = "Fecha y Hora",
                        value = uiState.transactionDate
                    )

                    HorizontalDivider()

                    VoucherRow(
                        label = "Cuenta",
                        value = uiState.account?.productDescription ?: ""
                    )

                    VoucherRow(
                        label = "Número de Cuenta",
                        value = uiState.account?.maskedAccountCode ?: ""
                    )

                    HorizontalDivider()

                    VoucherRow(
                        label = "Monto",
                        value = "${uiState.account?.currencySymbol} ${uiState.amount}",
                        valueColor = MaterialTheme.colorScheme.primary,
                        valueFontWeight = FontWeight.Bold
                    )

                    if (uiState.description.isNotEmpty()) {
                        VoucherRow(
                            label = "Descripción",
                            value = uiState.description
                        )
                    }
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.setIntent(VoucherIntent.BackToHome) }
            ) {
                Text("Volver al Inicio")
            }
        }
    }
}

@Composable
private fun VoucherRow(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
    valueFontWeight: FontWeight = FontWeight.Normal
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = valueColor,
            fontWeight = valueFontWeight
        )
    }
}
