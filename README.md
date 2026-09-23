# app-eve-common

Librería base de `Eve`: entidades y clases abstractas de las capas para reutilizar entre microservicios.

No es una aplicación ejecutable. Cada microservicio la consume como dependencia e implementa SUS repositorios, servicios y controladores concretos extendiendo las bases que provee esta librería.

## Requisitos del microservicio consumidor

- Java 21
- Spring Boot 4.1.x (misma versión que esta librería)
- Configuración propia de PostgreSQL en su `application.properties`
- Maven

## 1. Instalar la librería en tu máquina

```bash
git clone https://github.com/Eve-Eventos-Inolvidables/app-eve-common.git
cd app-eve-common
mvn install
```

Esto deja el jar en el repositorio Maven local (`~/.m2/repository`). No se publica en ningún servidor.

## 2. Consumirla desde tu proyecto

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>app-eve-common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## 3. Qué recibís automáticamente

Con solo agregar la dependencia, la auto-configuración de la librería registra:

- **Entidades compartidas** (`com.example.appevecommon.Models.*`): `SimpleEvent`, `User`, `Batch`, `Sector`, `EventCategory`, `Buy`, `Ticket`, etc.
- **Manejo de errores común**: `GlobalExceptionHandler` + formato `Response`.
- **DTOs** generados por OpenAPI (`org.openapitools.model.*`).
- Clases base para implementar tus capas: `IBaseRepository`, `IArchivableRepository`, `IBaseService`, `AbstractBaseService`, `AbstractArchivableBaseService`, `BaseController`, `PagedFilter`, `PageResult`, `ResponseFactory`.

## 4. Qué debés implementar en tu microservicio

Siempre en paquetes propios (no toques la librería). Ejemplo con `EventCategory`:

```java
// Repository
public interface IEventCategoryRepository extends IArchivableRepository<EventCategory> {}

// Filter
public class EventCategoryFilter extends PagedFilter {
    private String name;
}

// Service
@Service
public class EventCategoryService
        extends AbstractArchivableBaseService<EventCategory, EventCategoryDto, EventCategoryFilter> {

    protected EventCategoryService(IEventCategoryRepository repository) {
        super(repository);
    }

    @Override
    public EventCategoryDto toDto(EventCategory entity) { ... }

    @Override
    public EventCategory toEntity(EventCategoryDto dto) { ... }

    @Override
    public Specification<EventCategory> toSpecification(EventCategoryFilter filter) { ... }
}

// Controller
@RestController
@RequestMapping("/api/event-categories")
public class EventCategoryController
        extends BaseController<EventCategory, EventCategoryDto, EventCategoryFilter, EventCategoryService> {

    protected EventCategoryController(EventCategoryService service) {
        super(service);
    }
}
```

Regla rápida para elegir la base:

- Entidad con borrado lógico (extiende `Archivable`): repositorio `IArchivableRepository` + servicio `AbstractArchivableBaseService`
- Entidad sin borrado lógico (extiende `BaseEntity`): repositorio `IBaseRepository` + servicio `AbstractBaseService`

## 5. Actualizar la librería

```bash
git pull
mvn install
```

Reinstalala cada vez que haya cambios en `main` y rebuilda tu microservicio.

## Notas de seguridad

El archivo `src/main/resources/config/private.properties` (credenciales de Supabase) **no se incluye** en el jar: cada microservicio configura su propia conexión con sus credenciales.