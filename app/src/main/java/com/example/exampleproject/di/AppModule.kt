package com.example.exampleproject.di

import com.example.exampleproject.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Módulo de inyección de dependencias para la capa de presentación
 */
val appModule = module {
    viewModel { MainViewModel(get()) }
}
