package com.example.exampleproject.features.transaction.voucher

sealed class VoucherIntent {
    data object BackToHome : VoucherIntent()
}
