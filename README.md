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

## 7. Los DTOs (`openapi/api.yaml`)

Cada entidad tiene cuatro piezas, generadas desde `src/main/resources/openapi/api.yaml` al compilar:

| Schema | Para qué | Validación |
|---|---|---|
| `XBaseDto` | Properties de la entidad, **sin `id` ni `archived`** | ninguna |
| `CreateXDto` | `XBaseDto` + `required` | `@NotNull` + `@Size`/`@Email` |
| `UpdateXDto` | `XBaseDto` sola | ninguna (todo opcional) |
| `XDto` | Lectura: `ArchivableDto` + `XBaseDto` | ninguna (es respuesta, no entrada) |

Consecuencias de la convención:

- Los `Create*Dto` no llevan `id`: el backend lo asigna.
- Ningún `XDto` de lectura tiene `@NotNull`, así que un recurso con campos incompletos no rompe la deserialización de la respuesta.
- Los campos que calcula el backend (`Buy.total`, `Buy.date`, `Ticket.qrToken`) están **solo en el DTO de lectura**, no en el `Create`.
- `Update*Dto` existe solo para los recursos que se editan (`Role`, `User`, `EventCategory`, `SimpleEvent`, `Sector`, `Batch`). Lo que se origina al comprar o al relacionar (`Buy`, `Ticket`, `SectorByBatch`, `EventManager`, `UserInterest`) tiene `Create` y nada más: se crean, se archivan, no se parchean.
- Todos los `XDto` extienden `ArchivableDto` porque con la unificación **toda** entidad es soft delete.

⚠️ **Trampa de tipos**: `kickOffTime` (`SimpleEventBaseDto`) y `BuyDto.date` están declarados con `format: partial-time`, pero el openapi-generator 7.8.0 no los mapea a `LocalTime` y los genera como `String`. Las entidades usan `LocalTime`, así que hay que convertir a mano en `toDto`/`toEntity`:

```java
e.setKickOffTime(LocalTime.parse(dto.getKickOffTime()));   // String -> LocalTime
```

## 8. Parámetro `sort` del `PagedFilter`

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

## 9. El envelope de respuesta

Los controllers no devuelven el DTO pelado: devuelven un envelope. Hay **un tipo por forma de respuesta**, no un envelope mega con todos los campos.

| Endpoint | Status | Tipo | JSON |
|---|---|---|---|
| `getById`, `create`, `update` | 200 | `Response<T>` | `{success, message, data}` |
| `getByFilter` | 200 | `PagedResponse<T>` | `{success, message, data[], pagination}` |
| `delete` | 204 | — | sin body |
| errores | 4xx/5xx | `ErrorResponse` | `{success, message, error{statusCode, message}}` |

```jsonc
// GET /api/event-categories/1
{"success":true,"message":"Operación exitosa","data":{"id":1,"name":"Rock","archived":false}}

// GET /api/event-categories?page=0&size=20&sort=name+asc
{"success":true,"message":"Operación exitosa","data":[...],"pagination":{"page":0,"size":20,"totalItems":100,"totalPages":5}}

// 404
{"success":false,"message":"Recurso no encontrado","error":{"statusCode":404,"message":"No existe EventCategory con id=99"}}
```

**Por qué un tipo por forma y no uno solo:** springdoc deriva el schema del *tipo de retorno declarado*. Con un único `Response<T>` de 5 campos opcionales, Swagger muestra los 5 en cada endpoint —incluidos `error` y `pagination` en los que nunca se emiten. Al partirlo, cada endpoint declara su forma y su schema muestra solo lo que ese endpoint manda. `@JsonInclude(NON_NULL)` limpia el JSON en runtime pero **no** influye en el schema, así que no raggiunge.

Cuatro reglas para no romper el contrato:

- **`success` y `message` están siempre** (son `required`). Los campos opcionales se omiten del JSON cuando no aplican: no mandes `null` explícito, chequeá que la clave exista.
- **`pagination` es plano, no va anidado en `data`.** Los items van en `data` como array pelado y la paginación sube al nivel superior. El tipo parametrizado de `PagedResponse` es el item, no `List<Dto>`, para que el schema exponga el tipo real del recurso.
- **`error.statusCode` siempre coincide con el status HTTP.** No es una convención: los factories de error devuelven `ResponseEntity<ErrorResponse>` y arman el body en la misma llamada, así que no pueden desincronizarse. Por eso los de éxito devuelven el envelope pelado — el éxito no tiene status que elegir.
- **El 500 expone el mensaje de la excepción a propósito**, para debug. Si en producción querés ocultarlo, cambialo en `GlobalExceptionHandler.handleGeneric`.

`ResponseFactory.ok(PageResult<T>)` aplana el `PageResult` del service, así que `BaseController.getByFilter` devuelve `PagedResponse<D>` aunque `AbstractBaseService.getByFilter` siga devolviendo `PageResult<D>`: la paginación no se toca en la capa de service, solo en el borde HTTP.

Para un status de éxito que no tiene factory (201, 202), no agregues un método: `ResponseEntity.status(201).body(ResponseFactory.ok(dto))`.

## 10. Actualizar la librería

```bash
git pull
mvn install
```

Reinstalala cada vez que haya cambios en `main` y rebuilda tu microservicio.

## 11. Migración a la 0.2.2: el service ahora pide su mapper

`AbstractBaseService` pasó a requerir el mapper en el constructor:

```java
// antes
super(repository)

// ahora
super(repository, mapper)
```

**Los microservicios no compilan hasta que cada service concreto pase su mapper.** Es un error de compilación, no de arranque, así que aparece en el primer build:

```
constructor AbstractBaseService ... required: IBaseRepository<E>,M
                                 found:    IBaseRepository<EventCategory>
```

MapStruct ya está en el classpath, pero **`BaseMapper` todavía NO es un `@Mapper`**: sigue siendo una clase abstracta con `toDto` a mano, y el processor no genera nada. Por eso el `mapper` hay que pasarlo explícitamente y todavía se puede seguir usando una implementación manual.

Cuando se cablee MapStruct de verdad, faltará `componentModel = "spring"` (o el `-Amapstruct.defaultComponentModel=spring`): con el default, el `…Impl` que genera el processor no es bean de Spring y el `mapper` no se va a poder inyectar. Cada microservicio que declare un `@Mapper` necesita su propia copia de `annotationProcessorPaths` con `mapstruct-processor` **y** `lombok-mapstruct-binding`, en ese orden.

## Notas de seguridad

El archivo `src/main/resources/config/private.properties` (credenciales de Supabase) **no se incluye** en el jar: cada microservicio configura su propia conexión con sus credenciales.