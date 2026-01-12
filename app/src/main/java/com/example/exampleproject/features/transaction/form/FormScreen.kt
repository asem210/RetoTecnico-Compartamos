package com.example.exampleproject.features.transaction.form

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.domain.model.account.Account
import com.example.exampleproject.core.components.textfield.AmountVisualTransformation
import com.example.exampleproject.core.presentation.ui.BaseScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun FormScreen(
    account: Account,
    viewModel: FormViewModel = koinViewModel(),
    onBack: () -> Unit = {},
    onNext: (Account, String, String) -> Unit = { _, _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsState()

    // Log del objeto decodificado
    LaunchedEffect(account) {
        Log.d("FormScreen", "Account decodificado: $account")
        Log.d("FormScreen", "  - Código: ${account.accountCode}")
        Log.d("FormScreen", "  - Producto: ${account.productDescription}")
        Log.d("FormScreen", "  - Máscara: ${account.maskedAccountCode}")
        Log.d("FormScreen", "  - Saldo: ${account.currencySymbol} ${account.availableBalance}")
        Log.d("FormScreen", "  - Moneda: ${account.currencyCode}")
    }

    // Navigation handler
    LaunchedEffect(viewModel.navigation) {
        viewModel.navigation.collect { nav ->
            when (nav) {
                is FormNavigation.Back -> onBack()
                is FormNavigation.Continue -> {
                    if (uiState.amount.isNotEmpty()) {
                        onNext(account, uiState.amount, uiState.description)
                    }
                }
            }
        }
    }

    BaseScreen(showProgress = uiState.isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Datos de Transacción",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Cuenta seleccionada:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "${account.productDescription}\n${account.maskedAccountCode}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            OutlinedTextField(
                value = uiState.amount,
                onValueChange = { newValue ->
                    // Solo permitir dígitos
                    if (newValue.all { it.isDigit() }) {
                        viewModel.setIntent(FormIntent.AmountChanged(newValue))
                    }
                },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = AmountVisualTransformation(currencySymbol = "S/"),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = uiState.description,
                onValueChange = { viewModel.setIntent(FormIntent.DescriptionChanged(it)) },
                label = { Text("Descripción (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.amount.isNotEmpty(),
                onClick = { viewModel.setIntent(FormIntent.ContinueClicked) }
            ) {
                Text("Continuar")
            }
        }
    }
}
