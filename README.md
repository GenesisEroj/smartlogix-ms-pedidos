# SmartLogix - Microservicio de Pedidos (MS Pedidos)

## Descripción
Microservicio encargado de la gestión de pedidos en la plataforma SmartLogix.
Implementa los patrones **Factory Method**, **CQRS** y **Repository** para
garantizar una arquitectura escalable y mantenible.

## Tecnologías
- Java 17
- Spring Boot 3.5.15
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Patrones de Diseño Implementados
- **Repository Pattern**: Abstracción de la capa de acceso a datos
- **CQRS**: Separación de operaciones de lectura y escritura
- **Factory Method**: Creación estandarizada de pedidos
- **DTO**: Transferencia de datos entre capas

## Estructura del Proyecto
src/
├── main/
│   ├── java/com/smartlogix/pedidos/
│   │   ├── controller/    # PedidoController
│   │   ├── service/       # PedidoService
│   │   ├── repository/    # PedidoRepository
│   │   ├── model/         # Pedido
│   │   └── dto/           # PedidoDTO
│   └── resources/
│       └── application.properties
└── test/
└── java/com/smartlogix/pedidos/
└── MsPedidosApplicationTests

## Requisitos
- Java 17+
- Maven 3.9+
- MySQL 8.0+

## Configuración
Edita `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartlogix_pedidos
spring.datasource.username=root
spring.datasource.password=root
server.port=8081
```

## Instalación y Ejecución
```bash
# Clonar el repositorio
git clone https://github.com/GenesisEroj/smartlogix-ms-pedidos

# Entrar al directorio
cd smartlogix-ms-pedidos

# Ejecutar con Maven
mvn spring-boot:run

# O con Maven Wrapper
./mvnw spring-boot:run
```

## Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/pedidos | Crear nuevo pedido |
| GET | /api/pedidos | Obtener todos los pedidos |
| GET | /api/pedidos/{id} | Obtener pedido por ID |
| GET | /api/pedidos/cliente/{clienteId} | Obtener pedidos por cliente |
| PUT | /api/pedidos/{id}/estado | Actualizar estado del pedido |
| DELETE | /api/pedidos/{id} | Eliminar pedido |

## Pruebas Unitarias
```bash
mvn test
```
Las pruebas cubren el servicio con Mockito, verificando:
- Creación de pedidos
- Consulta de pedidos
- Actualización de estado
- Manejo de excepciones

## Equipo
- Genesis Eroj
- Francisco Monsalve

**DSY1106 - Desarrollo Fullstack III**