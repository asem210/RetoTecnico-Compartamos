@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.exampleproject.features.transaction.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.model.account.Account
import com.example.exampleproject.core.components.AppSelector
import com.example.exampleproject.core.components.bottomsheet.Item
import com.example.exampleproject.core.components.bottomsheet.ItemChooserBottomSheet
import com.example.exampleproject.core.presentation.ui.BaseScreen
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun StartScreen(
    viewModel: StartViewModel = koinViewModel(),
    onBack: () -> Unit = {},
    onNext: (Account) -> Unit = {},
    onTransactionCompleted: ((String, Double) -> Unit)? = null
) {
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val accountSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Load accounts when entering
    LaunchedEffect(Unit) {
        viewModel.setIntent(StartIntent.LoadAccounts)
    }

    // Navigation handler
    LaunchedEffect(viewModel.navigation) {
        viewModel.navigation.collect { nav ->
            when (nav) {
                is StartNavigation.Back -> onBack()
                is StartNavigation.GoToForm -> onNext(nav.account)
            }
        }
    }

    // BottomSheet logic
    if (uiState.accountFocused) {
        ItemChooserBottomSheet(
            sheetState = accountSheetState,
            onDismissRequest = {
                viewModel.toggleAccountFocus(false)
                scope.launch { accountSheetState.hide() }
            },
            items = uiState.accounts.map { account ->
                Item(
                    title = account.maskedAccountCode,
                    description = "${account.currencySymbol} ${String.format("%.2f", account.availableBalance)}"
                )
            },
            onItemSelected = { selected ->
                val account = uiState.accounts.find { it.maskedAccountCode == selected.title }
                account?.let { viewModel.setIntent(StartIntent.AccountSelected(it)) }
                viewModel.toggleAccountFocus(false)
                scope.launch { accountSheetState.hide() }
            }
        )
        LaunchedEffect(uiState.accountFocused) {
            if (uiState.accountFocused) accountSheetState.show()
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
                text = "Realizar Transacción",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Selecciona la cuenta de origen",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            AppSelector(
                modifier = Modifier.fillMaxWidth(),
                label = "Cuenta de origen",
                description = uiState.accountSelected?.productDescription ?: "Seleccionar cuenta",
                content = uiState.accountSelected?.let { account ->
                    "${account.maskedAccountCode}\n${account.currencySymbol} ${String.format("%.2f", account.availableBalance)}"
                } ?: "",
                focused = uiState.accountFocused,
                onClick = {
                    viewModel.toggleAccountFocus(true)
                }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState.accountSelected != null,
                onClick = { viewModel.setIntent(StartIntent.ContinueClicked) }
            ) {
                Text("Continuar")
            }
        }
    }
}
