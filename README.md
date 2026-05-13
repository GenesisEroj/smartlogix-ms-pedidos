# SmartLogix — Microservicio de Pedidos (`ms-pedidos`)

Microservicio REST encargado de la gestión completa del ciclo de vida de pedidos dentro de la plataforma **SmartLogix**. Forma parte de una arquitectura de microservicios junto con `ms-inventario` y el BFF (`smartlogix-bff`).

---

## 🛠 Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 3.5.15-SNAPSHOT |
| Spring Data JPA | — |
| MySQL | 8.0+ |
| Lombok | — |
| Maven | 3.9+ |

---

## 🏗 Patrones de Diseño Implementados

| Patrón | Descripción |
|---|---|
| **Repository** | `PedidoRepository` abstrae el acceso a datos con Spring Data JPA |
| **CQRS** | El `PedidoService` separa explícitamente los métodos de consulta (*Query*) y de comando (*Command*) |
| **Factory Method** | `crearPedido()` encapsula la lógica de creación, asignando estado por defecto `PENDIENTE` y fecha de creación automáticamente via `@PrePersist` |
| **DTO / Builder** | La entidad `Pedido` usa el patrón Builder de Lombok para construcción inmutable |

---

## 📂 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/smartlogix/pedidos/
│   │   ├── MsPedidosApplication.java   # Punto de entrada Spring Boot
│   │   ├── controller/
│   │   │   └── PedidoController.java   # Exposición de la API REST
│   │   ├── service/
│   │   │   └── PedidoService.java      # Lógica de negocio (CQRS + Factory)
│   │   ├── repository/
│   │   │   └── PedidoRepository.java   # Acceso a datos (Spring Data JPA)
│   │   ├── model/
│   │   │   └── Pedido.java             # Entidad JPA con @PrePersist
│   │   └── dto/                        # Objetos de transferencia de datos
│   └── resources/
│       └── application.properties      # Configuración del servidor y BD
└── test/
    └── java/com/smartlogix/pedidos/
        └── MsPedidosApplicationTests.java
```

---

## ⚙️ Modelo de Datos

La entidad `Pedido` mapea a la tabla `pedidos`:

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | `Long` (PK, auto) | Identificador único |
| `clienteId` | `String` | Identificador del cliente |
| `productoId` | `String` | Identificador del producto |
| `cantidad` | `Integer` | Cantidad solicitada |
| `estado` | `String` | Estado del pedido (ej. `PENDIENTE`, `COMPLETADO`) |
| `fechaCreacion` | `LocalDateTime` | Asignado automáticamente al persistir |

> **Nota:** `estado` se inicializa en `"PENDIENTE"` y `fechaCreacion` se asigna con `LocalDateTime.now()` si no se proporcionan, gracias al método `@PrePersist`.

---

## 🔌 Endpoints REST

Base URL: `http://localhost:8081/api/pedidos`

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| `POST` | `/` | Crear un nuevo pedido | `201 Created` + `Pedido` |
| `GET` | `/` | Obtener todos los pedidos | `200 OK` + `List<Pedido>` |
| `GET` | `/{id}` | Obtener pedido por ID | `200 OK` + `Pedido` |
| `GET` | `/cliente/{clienteId}` | Obtener pedidos de un cliente | `200 OK` + `List<Pedido>` |
| `PUT` | `/{id}/estado?estado=X` | Actualizar estado del pedido | `200 OK` + `Pedido` |
| `DELETE` | `/{id}` | Eliminar un pedido | `204 No Content` |

### Ejemplo — Crear pedido

```json
POST /api/pedidos
Content-Type: application/json

{
  "clienteId": "cliente-001",
  "productoId": "prod-007",
  "cantidad": 3
}
```

---

## 🗄️ Configuración

Edita `src/main/resources/application.properties`:

```properties
# Servidor
server.port=8081

# Base de datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/smartlogix_pedidos?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Nombre de la aplicación
spring.application.name=ms-pedidos
```

> **Importante:** `createDatabaseIfNotExist=true` crea automáticamente la base de datos si no existe.

---

## 🚀 Instalación y Ejecución

### Requisitos previos
- Java 17+
- Maven 3.9+
- MySQL 8.0+ en ejecución

```bash
# 1. Clonar el repositorio
git clone https://github.com/GenesisEroj/smartlogix-ms-pedidos
cd smartlogix-ms-pedidos

# 2. Ejecutar con Maven
mvn spring-boot:run

# O con Maven Wrapper (sin instalar Maven)
./mvnw spring-boot:run     # Linux / macOS
mvnw.cmd spring-boot:run   # Windows
```

El servicio quedará disponible en `http://localhost:8081`.

---

## 🧪 Pruebas

```bash
mvn test
```

Las pruebas cubren el `PedidoService` con Mockito, verificando:
- Creación de pedidos con estado por defecto
- Consulta de pedidos por ID y por cliente
- Actualización de estado
- Manejo de excepciones (`RuntimeException` cuando no se encuentra el pedido)

---

## 🔗 Integración en la Arquitectura SmartLogix

```
[Frontend React]
      │
      ▼
[smartlogix-bff :8083]
      ├──────────────────► [ms-pedidos   :8081] ─► MySQL (smartlogix_pedidos)
      └──────────────────► [ms-inventario:8082] ─► MySQL (smartlogix_inventario)
```

El BFF (`smartlogix-bff`) actúa como punto de entrada único para el frontend, enrutando las peticiones de pedidos a este microservicio.

---

## 👥 Equipo

- Genesis Eroj
- Francisco Monsalve

**DSY1106 — Desarrollo Fullstack III**