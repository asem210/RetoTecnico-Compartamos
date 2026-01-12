package com.example.exampleproject.features.transaction.voucher

sealed class VoucherNavigation {
    data object GoToHome : VoucherNavigation()
}
