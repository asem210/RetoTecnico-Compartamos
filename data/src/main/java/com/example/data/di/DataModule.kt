package com.example.data.di

import org.koin.dsl.module

/**
 * Módulo de inyección de dependencias para la capa de datos
 * Aquí se registran repositories, data sources y API services
 */
val dataModule = module {
    // Retrofit - Singleton
    // single { provideRetrofit() }
    
    // API Services
    // factory<ApiService> { get<Retrofit>().create(ApiService::class.java) }
    
    // Data Sources
    // factory { DataSource(get()) }
    
    // Repositories
    // factory<Repository> { RepositoryImpl(get()) }
}
