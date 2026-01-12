package com.example.exampleproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domain.model.account.Account
import com.example.domain.model.transaction.TransactionResult
import com.example.exampleproject.features.transaction.form.FormScreen
import com.example.exampleproject.features.transaction.start.StartScreen
import com.example.exampleproject.features.transaction.voucher.VoucherScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

sealed class TransactionRoute(val route: String) {
    data object Start : TransactionRoute("transaction_start")
    data object Form : TransactionRoute("transaction_form")
    data object Voucher : TransactionRoute("transaction_voucher")
}

@Composable
fun TransactionNavHost(
    navController: NavHostController = rememberNavController(),
    onBackToHome: () -> Unit = {},
    onTransactionCompleted: (String, Double) -> Unit = { _, _ -> }  // accountCode, amount
) {
    NavHost(
        navController = navController,
        startDestination = TransactionRoute.Start.route
    ) {
        // START Screen
        composable(TransactionRoute.Start.route) {
            // Obtener datos de transacción completada si existen
            val transactionCompletedAccountCode = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.get<String>("completedTransactionAccountCode")
            val transactionCompletedAmount = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("completedTransactionAmount")

            StartScreen(
                onBack = { onBackToHome() },
                onNext = { account ->
                    // Serializar a JSON String
                    val accountJson = Json.encodeToString(account)
                    navController.currentBackStackEntry?.savedStateHandle?.set("accountJson", accountJson)
                    navController.navigate(TransactionRoute.Form.route)
                },
                onTransactionCompleted = if (transactionCompletedAccountCode != null && transactionCompletedAmount != null) {
                    { accountCode, amount ->
                        // Ya se ejecutará automáticamente por el ViewModel
                    }
                } else null
            )

            // Procesar actualización de saldo si existe
            LaunchedEffect(transactionCompletedAccountCode, transactionCompletedAmount) {
                if (transactionCompletedAccountCode != null && transactionCompletedAmount != null) {
                    onTransactionCompleted(transactionCompletedAccountCode, transactionCompletedAmount)
                    // Limpiar los datos después de procesarlos
                    navController.currentBackStackEntry?.savedStateHandle?.remove<String>("completedTransactionAccountCode")
                    navController.currentBackStackEntry?.savedStateHandle?.remove<Double>("completedTransactionAmount")
                }
            }
        }

        // FORM Screen
        composable(TransactionRoute.Form.route) {
            // Deserializar desde JSON String
            val accountJson = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("accountJson")
            
            val account = accountJson?.let { Json.decodeFromString<Account>(it) }

            if (account != null) {
                FormScreen(
                    account = account,
                    onBack = { navController.popBackStack() },
                    onNavigateToVoucher = { resultJson ->
                        navController.currentBackStackEntry?.savedStateHandle?.set("transactionResultJson", resultJson)
                        navController.navigate(TransactionRoute.Voucher.route)
                    }
                )
            }
        }

        // VOUCHER Screen
        composable(TransactionRoute.Voucher.route) {
            val transactionResultJson = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("transactionResultJson")

            val transactionResult = transactionResultJson?.let { Json.decodeFromString<TransactionResult>(it) }

            if (transactionResult != null) {
                VoucherScreen(
                    transactionResult = transactionResult,
                    onBackToHome = {
                        // Pasar los datos de la transacción completada al volver al Start
                        navController.getBackStackEntry(TransactionRoute.Start.route).savedStateHandle.apply {
                            set("completedTransactionAccountCode", transactionResult.originAccountCode)
                            set("completedTransactionAmount", transactionResult.amount)
                        }
                        navController.popBackStack(TransactionRoute.Start.route, inclusive = false)
                    }
                )
            }
        }
    }
}
