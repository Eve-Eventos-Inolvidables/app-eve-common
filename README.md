# app-eve-common

Librería base de `Eve`: entidades y clases abstractas de las capas para reutilizar entre microservicios.

No es una aplicación ejecutable. Cada microservicio la consume como dependencia e implementa SUS repositorios, servicios y controladores concretos extendiendo las bases que provee esta librería.

## Requisitos del microservicio consumidor

- Java 21
- Spring Boot 4.1.x (misma versión que esta librería)
- Configuración propia de PostgreSQL en su `eve-common-defaults.properties`
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
- Clases base para implementar tus capas: `IBaseRepository`, `IBaseService`, `AbstractBaseService`, `BaseController`, `PagedFilter`, `PageResult`, `ResponseFactory`.

## 4. Qué debés implementar en tu microservicio

Siempre en paquetes propios (no toques la librería). Ejemplo con `EventCategory`:

```java
// Repository
public interface IEventCategoryRepository extends IBaseRepository<EventCategory> {}

// Filter
public class EventCategoryFilter extends PagedFilter {
    private String name;
}

// Service
@Service
public class EventCategoryService
        extends AbstractBaseService<EventCategory, EventCategoryDto, EventCategoryFilter> {

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

## 5. Borrado lógico en todas las entidades

`BaseEntity` ya trae el flag `archived` con `@SQLRestriction("is_archived = false")`, así que **toda entidad es soft delete por defecto** y no hay que elegir base de repositorio ni de servicio. `delete(id)` archiva la fila en vez de eliminarla.

Para incluir o excluir lo archivado:

| Método | Qué hace |
|---|---|
| `findAll()` / `findById()` / `findAll(spec)` | Solo lo activo (el filtro lo inyecta Hibernate) |
| `findAllIncludingInactive()` | Todo, archivado incluido |
| `findByIdIncludingInactive(id)` | Busca un recurso archivado puntual |
| `unarchive(id)` / `setArchived(false)` | Restaura un recurso |

Consecuencia a tener en cuenta: el `@SQLRestriction` **se propaga a las asociaciones**, así que un hijo archivado desaparece de la colección del padre (`Buy.ticketList`, `Event.sectorList`, etc.).

## 6. Cómo escribir un `update`

La base no impone `create`/`update`: cada service los declara con sus propios DTOs. La regla para no romper datos:

**`toEntity(dto)` es únicamente para `create`. En un `update`, nunca reconstruyas la entidad desde cero** — usá `patch()`, que carga la entidad, le aplica solo los cambios y la guarda:

```java
@Override
public EventCategoryDto update(Long id, UpdateEventCategoryDto dto) {
    return toDto(patch(id, e -> {
        e.setName(dto.getName());
    }));
}
```

`archived`, `id` y cualquier campo que el DTO de update no declare se conservan solos. Si en cambio hacés `toEntity(dto)` + `setId(id)` + `save(...)`, el merge reemplaza la fila completa y esos campos vuelven al default (por ejemplo `archived = false`, que des-archivaría el recurso).

## 7. Parámetro `sort` del `PagedFilter`

`sort` acepta `campo` o `campo desc`, separados por coma o espacio. `desc` es el único valor que invierte el orden; cualquier otro token se toma como ascendente.

| Valor enviado | Resultado |
|---|---|
| *(ausente o vacío)* | Sin `ORDER BY` — evitá paginar así: el orden no es estable entre páginas |
| `name` | `name` ascendente |
| `name asc` | `name` ascendente |
| `name desc` / `name,desc` | `name` descendente |
| `name, desc` | `name` descendente (espacios alrededor de la coma) |
| `name lo-que-sea` | `name` ascendente (token no reconocido) |
| `name desc otro` | `name` descendente; los tokens extra se ignoran |
| `name,desc,id` | Solo `name` descendente — **no hay multi-ordenamiento** |
| `user.name desc` | Ordena por la propiedad anidada `user.name` |
| `columna_sql desc` | Debe ser la propiedad JPA (`eventName`), no el nombre de la columna (`event_name`) |
| `id; DROP TABLE ...` | Campo inexistente → 500 (ver más abajo) |

```http
GET /api/event-categories?page=0&size=20&sort=name+asc
GET /api/event-categories?page=0&size=20&sort=name,desc
```

Dos cosas a tener en cuenta: `sort` **no se valida contra una whitelist**, así que un campo inexistente lanza `PropertyReferenceException`, que el `GlobalExceptionHandler` mapea a **500** y no a 400. Y como no hay segundo criterio de desempate, conviene paginar con `sort=id desc`.

## 8. Actualizar la librería

```bash
git pull
mvn install
```

Reinstalala cada vez que haya cambios en `main` y rebuilda tu microservicio.

## Notas de seguridad

El archivo `src/main/resources/config/private.properties` (credenciales de Supabase) **no se incluye** en el jar: cada microservicio configura su propia conexión con sus credenciales.