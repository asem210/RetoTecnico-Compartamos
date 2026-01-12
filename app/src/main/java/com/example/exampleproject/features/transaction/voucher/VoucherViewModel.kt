package com.example.exampleproject.features.transaction.voucher

import com.example.domain.model.account.Account
import com.example.exampleproject.core.presentation.viewmodel.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class VoucherViewModel(
    private val account: Account,
    private val amount: String,
    private val description: String
) : BaseViewModel<VoucherUiState, VoucherIntent, VoucherNavigation>() {

    override fun createInitialState(): VoucherUiState {
        val transactionId = generateTransactionId()
        val transactionDate = getCurrentDateTime()

        return VoucherUiState(
            account = account,
            amount = amount,
            description = description,
            transactionId = transactionId,
            transactionDate = transactionDate
        )
    }

    override suspend fun handleIntent(intent: VoucherIntent) {
        when (intent) {
            VoucherIntent.BackToHome -> goNavigation(VoucherNavigation.GoToHome)
        }
    }

    private fun generateTransactionId(): String {
        return "TXN${Random.nextInt(100000, 999999)}"
    }

    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}
