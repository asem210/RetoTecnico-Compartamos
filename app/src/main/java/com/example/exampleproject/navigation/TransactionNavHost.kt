package com.example.exampleproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domain.model.account.Account
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
    onBackToHome: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = TransactionRoute.Start.route
    ) {
        // START Screen
        composable(TransactionRoute.Start.route) {
            StartScreen(
                onBack = { onBackToHome() },
                onNext = { account ->
                    // Serializar a JSON String
                    val accountJson = Json.encodeToString(account)
                    navController.currentBackStackEntry?.savedStateHandle?.set("accountJson", accountJson)
                    navController.navigate(TransactionRoute.Form.route)
                }
            )
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
                    onNext = { selectedAccount, amount, description ->
                        val accountJson = Json.encodeToString(selectedAccount)
                        navController.currentBackStackEntry?.savedStateHandle?.apply {
                            set("accountJson", accountJson)
                            set("amount", amount)
                            set("description", description)
                        }
                        navController.navigate(TransactionRoute.Voucher.route)
                    }
                )
            }
        }

        // VOUCHER Screen
        composable(TransactionRoute.Voucher.route) {
            val accountJson = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("accountJson")
            val amount = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("amount") ?: ""
            val description = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("description") ?: ""

            val account = accountJson?.let { Json.decodeFromString<Account>(it) }

            if (account != null) {
                VoucherScreen(
                    account = account,
                    amount = amount,
                    description = description,
                    onBackToHome = {
                        navController.popBackStack(TransactionRoute.Start.route, inclusive = true)
                        onBackToHome()
                    }
                )
            }
        }
    }
}
