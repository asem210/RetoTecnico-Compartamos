# ExampleProject - Estructura Base

Proyecto Android con arquitectura limpia usando **Retrofit + OkHttp** y **Koin**.

## 📁 Estructura

```
ExampleProject/
├── domain/                     # Capa de Dominio
│   └── src/main/java/com/example/domain/
│       ├── model/             # Modelos de dominio
│       │   ├── account/       # Modelos de cuentas
│       │   └── transaction/   # Modelos de transacciones
│       ├── repository/        # Interfaces de repositorios
│       │   ├── account/       # Repositorios de cuentas
│       │   └── transaction/   # Repositorios de transacciones
│       ├── usecase/          # Casos de uso
│       │   ├── account/       # Casos de uso de cuentas
│       │   └── transaction/   # Casos de uso de transacciones
│       └── di/               # DomainModule.kt
│
├── data/                      # Capa de Datos
│   └── src/main/java/com/example/data/
│       ├── dto/              # DTOs de la API
│       │   ├── account/       # DTOs de cuentas
│       │   └── transaction/   # DTOs de transacciones
│       ├── mapper/           # Transformadores DTO ↔ Domain
│       │   ├── account/       # Mappers de cuentas
│       │   └── transaction/   # Mappers de transacciones
│       ├── remote/           # Configuración Retrofit & API Services
│       │   ├── account/       # API Services de cuentas
│       │   └── transaction/   # API Services de transacciones
│       ├── datasource/       # Data Sources
│       │   ├── account/       # Data Sources de cuentas
│       │   └── transaction/   # Data Sources de transacciones
│       ├── repository/       # Implementaciones de repos
│       │   ├── account/       # Implementaciones de repos de cuentas
│       │   └── transaction/   # Implementaciones de repos de transacciones
│       └── di/               # DataModule.kt
│
└── app/                       # Capa de Presentación
    └── (tu UI aquí)
```

## 🔧 Tecnologías

- **Kotlin** 1.9.24
- **Retrofit** 2.9.0 - Cliente HTTP REST
- **OkHttp** 4.12.0 - Cliente HTTP
- **Koin** 3.5.6 - Inyección de dependencias
- **Kotlinx Serialization** 1.6.0 - JSON
- **Kotlinx Coroutines** 1.7.3 - Async
- **Jetpack Compose** - UI (BOM 2024.02.00)
- **Compose Compiler** 1.5.14

## 🚀 Siguiente Paso

1. Crea tus modelos en `domain/model/{account|transaction}/`
2. Define interfaces de repositorio en `domain/repository/{account|transaction}/`
3. Crea casos de uso en `domain/usecase/{account|transaction}/`
4. Implementa DTOs en `data/dto/{account|transaction}/`
5. Crea API services (Retrofit) en `data/remote/{account|transaction}/`
6. Implementa data sources en `data/datasource/{account|transaction}/`
7. Implementa repositorios en `data/repository/{account|transaction}/`
8. Registra todo en los módulos de DI

## 📝 Ejemplo Básico

### 1. Modelo en Domain
```kotlin
// domain/model/account/Account.kt
data class Account(
    val id: String,
    val number: String,
    val balance: Double
)
```

### 2. DTO en Data
```kotlin
// data/dto/account/AccountDto.kt
@Serializable
data class AccountDto(
    @SerialName("id") val id: String,
    @SerialName("account_number") val accountNumber: String,
    @SerialName("balance") val balance: Double
)
```

### 3. Mapper
```kotlin
// data/mapper/account/AccountMapper.kt
fun AccountDto.toDomain() = Account(
    id = this.id,
    number = this.accountNumber,
    balance = this.balance
)
```

### 4. API Service (Retrofit)
```kotlin
// data/remote/account/AccountApiService.kt
interface AccountApiService {
    @GET("accounts/{id}")
    suspend fun getAccountById(@Path("id") id: String): AccountDto
}
```

### 5. Data Source
```kotlin
// data/datasource/account/AccountDataSource.kt
class AccountDataSource(private val apiService: AccountApiService) {
    suspend fun getAccountById(id: String): AccountDto {
        return apiService.getAccountById(id)
    }
}
```

### 6. Repository Implementation
```kotlin
// data/repository/account/AccountRepositoryImpl.kt
class AccountRepositoryImpl(
    private val dataSource: AccountDataSource
) : AccountRepository {
    override suspend fun getAccountById(id: String): Account {
        return dataSource.getAccountById(id).toDomain()
    }
}
```

### 7. Registrar en Koin
```kotlin
// data/di/DataModule.kt
val dataModule = module {
    // Retrofit
    single { provideRetrofit() }
    
    // API Services
    factory<AccountApiService> {
        get<Retrofit>().create(AccountApiService::class.java)
    }
    
    // Data Sources
    factory { AccountDataSource(get()) }
    
    // Repositories
    factory<AccountRepository> { AccountRepositoryImpl(get()) }
}

// domain/di/DomainModule.kt
val domainModule = module {
    factory { GetAccountByIdUseCase(get()) }
}
```

## 📐 SDK y Configuración

- **Compile SDK**: 36
- **Min SDK**: 26
- **Target SDK**: 34
- **JVM Target**: 11

¡Listo para empezar! 🎉
