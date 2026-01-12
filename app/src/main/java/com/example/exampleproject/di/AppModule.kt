package com.example.exampleproject.di

import com.example.exampleproject.MainViewModel
import com.example.exampleproject.features.transaction.form.FormViewModel
import com.example.exampleproject.features.transaction.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulo de inyección de dependencias para la capa de presentación
 */
val appModule = module {
    // Main
    viewModel { MainViewModel(get()) }
    
    // Transaction features
    viewModel { StartViewModel(get()) }
    viewModel { FormViewModel(get()) }
    // VoucherScreen ya no necesita ViewModel
}
