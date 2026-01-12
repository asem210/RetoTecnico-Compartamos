# ExampleProject - Estructura Base

Proyecto Android con arquitectura limpia usando **Ktor** y **Koin**.

## 📁 Estructura

```
ExampleProject/
├── domain/                     # Capa de Dominio
│   └── src/main/java/com/example/domain/
│       ├── model/             # Modelos de dominio
│       ├── repository/        # Interfaces de repositorios
│       ├── usecase/          # Casos de uso
│       └── di/               # DomainModule.kt
│
├── data/                      # Capa de Datos
│   └── src/main/java/com/example/data/
│       ├── dto/              # DTOs de la API
│       ├── mapper/           # Transformadores DTO ↔ Domain
│       ├── ktor/             # Configuración HttpClient
│       ├── remote/           # API Services
│       ├── datasource/       # Data Sources
│       ├── repository/       # Implementaciones de repos
│       └── di/               # DataModule.kt
│
└── app/                       # Capa de Presentación
    └── (tu UI aquí)
```

## 🔧 Tecnologías

- **Kotlin** 2.0.21
- **Ktor** 2.3.12 - Cliente HTTP
- **Koin** 3.5.6 - Inyección de dependencias
- **Kotlinx Serialization** - JSON
- **Coroutines** - Async

## 🚀 Siguiente Paso

1. Crea tus modelos en `domain/model/`
2. Define interfaces de repositorio en `domain/repository/`
3. Crea casos de uso en `domain/usecase/`
4. Implementa DTOs en `data/dto/`
5. Crea API services en `data/remote/`
6. Implementa repositorios en `data/repository/`
7. Registra todo en los módulos de DI

## 📝 Ejemplo Básico

### 1. Modelo en Domain
```kotlin
// domain/model/User.kt
data class User(
    val id: String,
    val name: String
)
```

### 2. DTO en Data
```kotlin
// data/dto/UserDto.kt
@Serializable
data class UserDto(
    val id: String,
    val name: String
)
```

### 3. Mapper
```kotlin
// data/mapper/UserMapper.kt
fun UserDto.toDomain() = User(
    id = this.id,
    name = this.name
)
```

### 4. Registrar en Koin
```kotlin
// data/di/DataModule.kt
val dataModule = module {
    factoryOf(::UserApiService)
}
```

¡Listo para empezar! 🎉
