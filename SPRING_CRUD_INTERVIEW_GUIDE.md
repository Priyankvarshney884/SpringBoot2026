# Spring CRUD Interview Guide

A focused revision guide for a face-to-face interview. Examples match the Book API in `springboot2026 2/`. Practice explaining the request flow and trade-offs in your own words rather than memorizing every sentence.

## 1. A clear 60-second explanation

> In Spring Boot, I implement CRUD as a layered REST API. The controller maps HTTP requests and handles the web contract, request DTOs validate client input, the service applies business rules inside transaction boundaries, and a Spring Data JPA repository performs persistence. JPA entities represent database state; response DTOs keep that persistence model separate from the API. I use meaningful HTTP statuses, centralized exception handling, pagination for collection endpoints, and tests at the service, MVC, and persistence boundaries. For writes, I also consider uniqueness constraints, concurrent requests, transaction rollback, and authorization rather than relying only on happy-path CRUD.

## 2. CRUD flow in this project

Base path: `/api/books`

| Operation | HTTP request | Typical success response | Implementation |
|---|---|---|---|
| Create | `POST /api/books` with JSON | `201 Created`, body, and `Location` header | `BookController.create` → `BookService.create` → `BookRepository.save` |
| Read collection | `GET /api/books?page=0&size=10&sort=title,asc` | `200 OK`, page of DTOs | `findAll(Pageable)` |
| Read one | `GET /api/books/{id}` | `200 OK`; `404` if absent | `findById` and `orElseThrow` |
| Replace | `PUT /api/books/{id}` with all editable fields | `200 OK`; `404` if absent | Load managed entity, replace fields, commit transaction |
| Partial update | `PATCH /api/books/{id}` with selected fields | `200 OK`; `404` if absent | Preserve fields omitted from patch DTO |
| Delete | `DELETE /api/books/{id}` | `204 No Content`; `404` if absent | Check existence, then delete |

For example, create with `POST /api/books` and body `{"title":"Effective Java","author":"Joshua Bloch","publicationYear":2018}`. A successful response includes the generated ID. Invalid fields return `400`; duplicate title and author return `409`.

### Trace a request aloud

`HTTP request → DispatcherServlet → handler mapping → controller → service → repository/JPA → database → DTO → JSON response`

Spring MVC's `DispatcherServlet` is the front controller. It routes to the matching handler. A message converter (commonly Jackson) converts JSON into the request DTO and serializes the response DTO. `@Valid` runs Bean Validation before the controller's method body. Exceptions flow to `@RestControllerAdvice`, which maps them to consistent HTTP responses.

## 3. What each layer owns

- **Controller:** URL, HTTP method, request/response shape, status and headers. Keep business rules and database calls out of it.
- **Request DTO:** client-controlled input and validation constraints. Do not bind a JPA entity directly to untrusted JSON (mass-assignment risk and persistence/API coupling).
- **Service:** business rules, use-case orchestration, transaction boundary, and mapping between entity and DTO.
- **Repository:** persistence operations and query declarations. `JpaRepository<Book, Long>` supplies CRUD, paging, sorting, and common methods; derived query names can generate simple queries.
- **Entity:** persistence mapping and state. It is not automatically the correct API response object.
- **Exception advice:** converts known failures into suitable status codes and a stable error body; unexpected errors should not expose stack traces or internal messages.

## 4. Core interview questions and answers

### 1. What does CRUD mean, and how does it map to REST?

CRUD is Create, Read, Update, Delete. Typical REST mappings are POST, GET, PUT/PATCH, and DELETE. The URL identifies a resource; the HTTP method expresses the operation. The response should use HTTP semantics: `201` for successful creation, `200` for a returned representation, `204` for a successful deletion with no body, and appropriate 4xx/5xx statuses for failures.

### 2. Walk me through creating a record in Spring Boot.

The client sends JSON to a POST endpoint. Spring's message converter binds it to a request DTO. `@Valid` checks constraints. The controller calls the service; the service normalizes input, applies business rules, and saves an entity through the repository within a transaction. The service maps the saved entity to a response DTO. The controller returns `201 Created`, ideally with a `Location` header for the new resource. Database constraints remain the final protection against races and invalid persisted state.

### 3. What are the responsibilities of controller, service, and repository?

The controller handles HTTP, the service handles a use case and business rules, and the repository handles data access. This separation keeps HTTP concerns out of business logic, makes transaction ownership clear, and allows each part to be tested at the right level.

### 4. Why use DTOs instead of returning entities?

DTOs give the API a deliberate, stable contract independent of table structure. They prevent clients from setting server-managed fields, reduce accidental exposure of internal data, and avoid problems from lazy-loaded relations or recursive JSON serialization. Separate request and response DTOs also let input validation and output fields differ.

### 5. What is the difference between PUT and PATCH?

PUT represents replacement of the resource's editable state; clients should send the complete representation needed for that replacement. PATCH applies a partial change. In this API, omitted PATCH fields mean “keep the current value.” PATCH needs careful null/omitted-field semantics: a nullable Java field alone cannot always distinguish omitted from explicitly null when null itself is a meaningful update. A wrapper or explicit patch format may be needed.

### 6. Are POST, PUT, PATCH, and DELETE idempotent?

Idempotency means repeating the same request has the same intended effect on server state. GET, PUT, and DELETE are expected to be idempotent by HTTP semantics. POST is generally not: repeating a create can create duplicates. PATCH's idempotency depends on the patch operation; setting a field to a fixed value is usually idempotent, while incrementing it is not. Network retries make this distinction operationally important.

### 7. What is dependency injection, and why constructor injection?

Dependency injection means an object receives dependencies from a container instead of constructing them itself. Spring creates the controller and service beans and supplies their collaborators. Constructor injection makes required dependencies explicit, allows fields to be final, supports straightforward unit tests, and prevents an object from being created in an incomplete state. With one constructor, modern Spring does not require `@Autowired` on it.

### 8. What do `@RestController`, `@Service`, `@Repository`, and `@SpringBootApplication` do?

`@RestController` makes a class an MVC controller whose return values are response bodies. `@Service` marks application/business logic for component scanning. `@Repository` marks data access and participates in persistence exception translation; Spring Data also creates implementations for repository interfaces. `@SpringBootApplication` combines application configuration, auto-configuration, and component scanning from the package of the application class downward.

### 9. What does `JpaRepository` provide? How does a derived query work?

It provides common entity operations such as `save`, `findById`, `findAll`, `deleteById`, pagination, and sorting. Spring Data parses method names such as `findByTitleContainingIgnoreCase` and builds a query. For complex queries, use explicit JPQL/native SQL or specifications/querydsl as appropriate, and inspect generated SQL and query plans for performance.

### 10. Why put `@Transactional` on a service method?

The service method usually represents a business use case, so it is a natural unit of work. The transaction groups database changes and allows them to commit together or roll back on failure. In this project, class-level `@Transactional(readOnly = true)` is the default and write methods override it with `@Transactional`. A transaction is not a substitute for validation, database constraints, or a carefully designed boundary.

### 11. Explain JPA dirty checking.

Within a persistence context, JPA tracks managed entities. If a managed entity changes inside a transaction, Hibernate detects the changed state and writes an update at flush/commit. In `replace`, the service loads the entity and changes its fields; it does not need to call `save` again for that managed instance. Detached entities and operations outside the transaction have different behavior.

### 12. What is the difference between `save` and dirty checking?

`save` asks Spring Data to persist or merge an entity and is appropriate for a new entity. For an already managed entity in an active transaction, changing its fields is enough for dirty checking. Calling `save` again may be harmless but can obscure the persistence-context model; understand whether the entity is managed and whether the method is transactional.

### 13. How do you handle validation and errors?

Put input constraints on request DTOs and use `@Valid` at the web boundary. Map validation failures to `400 Bad Request`, missing resources to `404 Not Found`, and conflicts such as duplicate records to `409 Conflict`. Use `@RestControllerAdvice` for a consistent response schema. Keep internal exception details and stack traces in server-side logs, not client responses.

### 14. Why check whether a record exists before updating or deleting?

It gives this API an explicit `404` behavior when the requested resource is absent. For an update, loading the entity is also needed to change it and supports dirty checking. For deletion, a check followed by delete can have a race if another transaction changes the row between operations. Database behavior and transaction isolation matter; use a delete count, locking, or a single operation where the use case requires stronger guarantees.

### 15. Why is an application-level duplicate check not sufficient?

Two concurrent requests can both check “not present” before either inserts. Add a unique constraint/index in the database as the authoritative guarantee, then translate the resulting constraint violation into `409 Conflict`. The service-level check can still provide a friendly early error, but it is not concurrency control by itself.

### 16. What is the N+1 query problem?

It occurs when one query loads a collection and then each row triggers another query for a related entity, resulting in one plus N queries. Lazy relationships can help avoid loading unused data but do not automatically solve N+1. Use DTO projections, fetch joins/entity graphs, or batching based on the use case, and verify with SQL logging or metrics. Avoid switching every association to eager loading, which can over-fetch and create large joins.

### 17. Why use pagination and sorting?

Returning every row can consume too much memory, network bandwidth, and database work as data grows. `Pageable` limits and orders results at the database. A `Page` often includes a total-count query; if the total is unnecessary, `Slice` can avoid that cost. Validate or cap page size for a public API, and whitelist allowed sort fields where clients can control sorting.

### 18. How do you prevent lost updates?

Two users can read the same version, then one overwrite the other's changes. Optimistic locking with `@Version` detects stale writes and lets the API return a conflict (often `409`). Pessimistic locking can serialize access but increases contention. Choose based on contention and consistency requirements; expose an ETag/`If-Match` contract when useful to clients.

### 19. How do you test a CRUD API?

Unit-test service rules with mocked collaborators where that gives a focused test. Use MVC slice tests (`@WebMvcTest`) for routes, validation, status codes, JSON, and advice. Use repository tests (`@DataJpaTest`) for mappings and queries. Use a smaller number of full integration tests for end-to-end wiring and database behavior. Test success and failure cases, including validation, missing IDs, duplicate/concurrent constraints, pagination, and rollback. This project currently has only a context-load test, so those focused tests are a practical next improvement.

### 20. What should you consider before taking this CRUD API to production?

Authentication and authorization for every operation; database migrations and constraints; production database configuration and connection pool sizing; transaction and concurrency behavior; request size and pagination limits; input validation; consistent error responses; structured logs, metrics, tracing, health checks; API versioning and compatibility; idempotency/retry behavior; and automated tests. Avoid logging credentials or sensitive request data.

### 21. How would you secure an update endpoint?

Authenticate the caller, then authorize access to that particular resource (ownership/tenant checks), not just the URL pattern. Validate fields and avoid binding privileged fields from client JSON. For browser sessions consider CSRF protections; for token APIs configure the Spring Security filter chain, token validation, and appropriate CORS policy. Enforce authorization in a place that cannot be bypassed by another entry point, and test both allowed and denied cases.

### 22. What does `@Valid` do, and where does validation happen?

It asks Bean Validation to evaluate constraints on the bound request object. In a controller, binding and validation happen before the handler body runs. Field constraints such as `@NotBlank`, `@Size`, and `@Min` describe input rules; the exception advice converts violations into an HTTP response. Business rules involving current database state belong in the service/domain layer too.

### 23. What is the difference between 400, 401, 403, 404, 409, and 500?

`400` means the request is malformed or invalid; `401` means authentication is required or failed; `403` means the caller is authenticated but not allowed; `404` means the resource was not found; `409` means the request conflicts with current state (for example, a uniqueness conflict); `500` means an unexpected server failure. Return the most accurate status and a safe, useful error body.

### 24. How do Spring transactions behave with exceptions?

By default, Spring rolls back on unchecked exceptions and errors, but not on every checked exception. Rollback rules can be configured. Transactional behavior is commonly applied through proxies, so self-invoking another `@Transactional` method on the same object may not pass through the proxy. Keep boundaries clear and verify rollback behavior for important checked and unchecked failures.

### 25. What is the difference between `Page` and `List` for a collection endpoint?

A `List` returns whatever the query fetched and carries no paging metadata. `Page` represents a page and includes metadata such as total elements/pages, typically requiring a count query. Choose a bounded response for potentially large datasets. For a simple “is there another page?” experience, `Slice` can be more efficient.

## 5. Common follow-up design improvements

Use a database unique constraint for `(title, author)` and map constraint violations to `409`. Add `@Version` for optimistic locking if concurrent edits matter. Cap page sizes and constrain sort fields. Add service, MVC, repository, and integration tests. Use database migrations for schema changes. Add authentication and resource-level authorization before exposing writes. For relationships, decide which data the API needs and map DTOs without triggering accidental lazy loads.

## 6. Explain this implementation file by file

- [`BookController.java`](springboot2026%202/src/main/java/com/revision/springboot2026/controller/BookController.java): routes, HTTP input/output, validation entry point, and statuses.
- [`BookService.java`](springboot2026%202/src/main/java/com/revision/springboot2026/service/BookService.java): use cases, uniqueness check, transactions, dirty checking, and DTO mapping.
- [`BookRepository.java`](springboot2026%202/src/main/java/com/revision/springboot2026/repository/BookRepository.java): CRUD and derived queries.
- [`Book.java`](springboot2026%202/src/main/java/com/revision/springboot2026/domain/Book.java): entity/table mapping and persistence state.
- [`BookRequest.java`](springboot2026%202/src/main/java/com/revision/springboot2026/dto/BookRequest.java), [`BookPatchRequest.java`](springboot2026%202/src/main/java/com/revision/springboot2026/dto/BookPatchRequest.java), [`BookResponse.java`](springboot2026%202/src/main/java/com/revision/springboot2026/dto/BookResponse.java): input, partial-update input, and output contracts.
- [`ApiExceptionHandler.java`](springboot2026%202/src/main/java/com/revision/springboot2026/exception/ApiExceptionHandler.java): consistent mapping from application/request errors to HTTP errors.

## 7. Final practice checklist

Before the interview, explain without reading: one request end to end; why each layer exists; POST versus PUT versus PATCH; how validation and exceptions become statuses; what `@Transactional` and dirty checking do; how you would prevent duplicate races and lost updates; and how you would test the feature. When answering from experience, give one concrete example, explain the trade-off, and state how you would verify the behavior.

## 8. Java garbage collection: definition and small POC

Garbage collection (GC) is JVM-managed memory reclamation. Objects are created in the heap; when an object is no longer reachable from GC roots (such as live thread stacks, static fields, and JNI references), it is eligible for collection. “Eligible” does not mean “collected immediately.” The JVM chooses when and how to reclaim memory. GC helps manage memory, but it does not prevent memory leaks: an application can accidentally keep references to objects it no longer needs.

### Common generation terms

- **Minor GC / young collection:** commonly means collecting the young generation, where most newly allocated objects start. Short-lived objects are often reclaimed here. The exact name and behavior depend on the collector.
- **Major GC / old-generation collection:** commonly means collecting the old/tenured generation. Some sources use “major” differently, so clarify what a specific JVM log or team means.
- **Full GC:** generally a broader collection of the heap (often including young and old generations), and may include additional work such as class metadata processing. It can cause a longer pause, but exact behavior depends on the collector and JVM version.

These labels are not a universal contract. Modern collectors such as G1, ZGC, and Shenandoah organize and report work differently. In an interview, say that the terms are collector-dependent and then describe what memory region/work is being collected. Prefer reading the actual GC log over inferring from a label.

### Run the POC

Open `springboot2026 2/src/main/java/com/revision/springboot2026/practice/GarbageCollectionDemo.java` and run its `main` method. It:

1. prints used, committed, and maximum heap snapshots;
2. allocates an 8 MiB array and observes it through a `WeakReference` while a normal strong reference keeps it alive;
3. removes the strong reference, asks for GC, and checks for up to two seconds whether the weak reference was cleared;
4. creates a modest amount of short-lived allocation churn.

The GC request is only a hint, and memory snapshots are approximate observations, not proof that a particular collection ran. The demo deliberately avoids forcing a heap-full condition, because that would be unreliable and could crash or stall a machine. To see JVM events, add this VM option in the run configuration:

```text
-Xlog:gc*
```

Then compare the demo output with the collector's log. You can also try `-Xms32m -Xmx64m -Xlog:gc*` to make the heap smaller and collection activity easier to observe; do not assume a specific number or type of collections. The exact output varies by JDK, collector, and machine.

On the Java 21/G1 run used to check this POC, `System.gc()` appeared as `Pause Full (System.gc())`, and the short-lived allocation loop triggered `Pause Young (Normal)`. This demonstrates the log labels for that run only; the JVM can ignore explicit requests, and a different collector may report different phases.

### Interview questions and answers

**What is garbage collection?** The JVM reclaims heap memory occupied by objects that are no longer reachable from GC roots. Application code should release unnecessary references and external resources; GC is not a substitute for closing files, sockets, or database connections.

**When does an object become eligible?** When no strong reference path from a GC root reaches it. Eligibility is not a promise about when the object will be reclaimed. A `WeakReference` lets us observe an object without keeping it alive.

**What are Minor, Major, and Full GC?** Traditionally, minor GC collects young-generation objects, major GC collects old-generation objects, and full GC covers a broader heap collection. The exact meanings vary by collector and log terminology, so I verify the actual JVM's documentation and GC logs.

**Does `System.gc()` force collection?** No. It requests that the JVM consider collection; the JVM can ignore it, and it should not be used as routine application memory management.

**Does Java have memory leaks if it has GC?** Yes. GC cannot reclaim objects that remain reachable. Examples include unbounded caches, listeners never deregistered, static collections that keep request data, and `ThreadLocal` values not cleared in pooled threads.

**What is stop-the-world?** Some GC phases pause application threads so the collector can safely perform work. Collectors aim to control pause time and throughput impact, but the trade-off depends on workload and configuration. Concurrent collection does not mean every phase has zero pauses.

**How do you diagnose high GC activity in production?** Inspect GC logs and JVM metrics: allocation rate, heap occupancy before/after collections, pause duration, frequency, promotion/old-gen behavior, and out-of-memory events. Correlate with traffic and application metrics, then use heap dumps/profilers to find retained objects. Avoid changing heap size or collector settings without evidence and load testing.

**What is the difference between heap and stack?** The heap stores objects and is managed by GC. Each thread has a stack of method frames, local variables, and call state; stack frames are removed as methods return. A local variable holding an object reference may be on the stack while the object itself is on the heap.

## 9. Spring bean scopes: a real-world explanation

### Start with the problem scopes solve

Spring needs to know how long to keep an object around and who should share it. Think of a busy restaurant:

- A **singleton** is the shared kitchen station: all tables use the same service object. It is efficient, so the object should not keep a customer's changing order in ordinary mutable fields.
- A **prototype** is a fresh notepad handed out whenever one is requested. It is useful when each operation needs a separate short-lived helper with its own state.
- A **request-scoped** object is one order ticket for one HTTP request. Every component handling that same request can use the same ticket; the next HTTP request gets a different one.
- A **session-scoped** object is a customer's ongoing tab across multiple requests in the same HTTP session. It can hold small session-specific state, but it consumes memory until the session expires.
- An **application-scoped** object is shared for the lifetime of the web application's `ServletContext`. It is web-aware shared state, not per-user state.

The scope controls bean instance lifetime/sharing; it does not automatically make the object's fields safe, persistent, or appropriate for user data. In a normal Spring Boot web application, singleton is the default and is the right choice for most controllers, services, and repositories because they contain reusable behavior and are stateless (or use properly synchronized shared collaborators).

### The five scopes in practical terms

| Scope | When Spring gives you an instance | Real application example | Benefit and caution |
|---|---|---|---|
| **Singleton (default)** | One instance per Spring application context | `BookService`, `BookRepository`, configuration, reusable formatter | Low allocation and one shared collaborator. Many request threads can call it at once, so do not store request-specific mutable data in its fields. “Singleton” is per Spring context, not necessarily one per JVM/cluster. |
| **Prototype** | A new instance each time the container is asked for it | A stateful calculation helper or one-operation builder | Fresh mutable state for each use. Spring injects and initializes it, but generally does not manage its destruction after handing it out. Injecting it straight into a singleton usually captures one prototype at startup; use `ObjectProvider<T>` or a scoped proxy to ask for fresh instances. |
| **Request** | One instance for one HTTP request | Request correlation/context data shared through a call chain | Natural boundary for request-only state; another request gets another object. Only available in a web request; do not use it to keep durable work or state for later requests. |
| **Session** | One instance for one HTTP session, shared over that user's requests | Small multi-step web workflow state | Convenient user-session state. Sessions expire and may live on different server nodes, so keep data small and consider replication/stickiness; do not treat it as a database or authorization check. For stateless APIs, avoid depending on sessions unless the design requires them. |
| **Application** | One instance per web `ServletContext` | Shared web-app-level metadata or a carefully managed in-memory registry | Shared throughout that web application. It is not shared across separate application deployments/instances, and mutable state needs thread-safety. Prefer a singleton for ordinary Spring services; use application scope only when ServletContext lifetime is actually needed. |

### Connect it to your project

Run the app and call `GET /api/core-demo` more than once. In `CoreDemoService.examples()`:

- `CoreDemoComponent` is singleton-scoped; two container lookups return the same object, so the response says `singletonLookupsReturnSameObject: true`.
- `PrototypeNote` is prototype-scoped; `ObjectProvider.getObject()` asks Spring for a fresh object each time, so the response includes two different IDs.
- `CoreDemoService` itself is a singleton service. That is safe because its fields are collaborators/configuration and its request handling does not store a particular caller's data in mutable fields.

That last point is a useful code-review test: if a singleton service has a field like `currentUser`, `currentBook`, or `requestData` that changes on every API call, simultaneous requests can overwrite each other's values. Make per-call data a local variable or use an appropriate web scope when there is a strong reason.

### How do I inject a short-lived bean into a singleton?

If a singleton constructor directly receives a prototype bean, the container resolves that dependency when it creates the singleton. The singleton then keeps using that same prototype reference; it does not automatically receive a new object on each method call. In this project, `ObjectProvider<PrototypeNote>` is injected instead. Calling `getObject()` inside the operation asks Spring for a fresh prototype on demand.

Web scopes have a related issue when a singleton service needs a request/session bean: the actual scoped object changes by request/session, while the singleton is created once. Use a scoped proxy or an appropriate `ObjectProvider`/provider lookup so resolution happens at use time. Keep this as a deliberate dependency because it couples that service to a web context.

### A natural interview answer

> “A Spring bean scope decides how long Spring keeps a bean instance and which uses share it. Singleton is the default and means one instance per application context; that suits stateless services and repositories, but they must be safe for concurrent requests. Prototype gives a fresh object each time the container is asked, useful for short-lived stateful helpers; if I need one inside a singleton, I use `ObjectProvider` because direct injection only resolves it when the singleton is created. Request scope is one bean per HTTP request, session scope is one per user session, and application scope is one per web `ServletContext`. I choose the narrowest lifetime that fits the state and avoid keeping request/user data in singleton fields.”

### Likely follow-up questions

**Does singleton mean only one instance in the entire deployment?** No. It means one per Spring application context. Multiple contexts, JVMs, or service replicas each have their own singleton.

**Is a singleton bean thread-safe automatically?** No. Scope controls sharing, not synchronization. A stateless service is usually safe because each invocation uses its own stack/local variables; mutable shared fields need a thread-safety design.

**Why not make every service prototype?** Most services have no per-call state. Prototype adds object creation and lifecycle complexity without benefit, and injecting it directly into a singleton does not refresh it per call.

**Can I inject a request bean into a singleton?** Yes, using a scoped proxy or provider so Spring resolves the active request's instance when needed. A direct raw object cannot represent a different request instance for every later call.

**When would you choose session scope?** For a server-rendered or stateful multi-request workflow where user-specific temporary state is genuinely useful. For REST APIs designed to be stateless, I usually keep workflow state in explicit tokens or a data store rather than rely on an HTTP session.

**Application scope versus singleton?** Singleton is a core Spring scope tied to an application context. Application scope is a web scope tied to a `ServletContext`. They often look similar in a simple Boot app but represent different lifecycle boundaries; application scope is not a distributed shared cache.

## 10. Global exception handling with `@ControllerAdvice`

### The everyday problem it solves

Imagine every controller had to write its own `try/catch` for “book missing,” invalid JSON, validation errors, duplicates, and unexpected failures. The responses would drift: one endpoint might return a string, another a stack trace, and another a different status for the same problem. Global advice is a shared receptionist for controller failures: it receives exceptions that escape controller handling and translates them into a consistent HTTP response.

In this project, [`ApiExceptionHandler.java`](springboot2026%202/src/main/java/com/revision/springboot2026/exception/ApiExceptionHandler.java) is annotated with `@RestControllerAdvice`. `@ControllerAdvice` lets Spring apply handlers across controllers; `@RestControllerAdvice` also makes returned values response bodies, which is suitable for this JSON API. Each `@ExceptionHandler(SomeException.class)` method says which exception it knows how to translate.

### Follow real requests through the code

**Missing book:** `GET /api/books/999` reaches the controller, then `BookService.findById`. The repository returns an empty `Optional`; the service throws `BookNotFoundException`. It bubbles out of the controller, Spring finds the matching advice method, and the handler returns `404 Not Found` with an `ApiError` body.

**Invalid request:** `POST /api/books` with a blank title reaches the MVC binding/validation stage. `@Valid` on the controller argument causes Bean Validation to check `BookRequest`; a constraint violation raises `MethodArgumentNotValidException` before the controller method executes. The advice gathers field messages and returns `400 Bad Request`.

**Duplicate book:** the service detects an existing title/author pair and throws `DuplicateBookException`; the advice translates it into `409 Conflict`. A database uniqueness constraint should also protect against concurrent requests in a production system.

**Unexpected server failure:** an exception with no more specific handler reaches the general `Exception` handler. The project logs the exception and stack trace server-side, then returns a safe generic `500 Internal Server Error` to the client. This keeps implementation details out of the API response while leaving operators enough information to investigate.

The response is built in one `response(...)` helper, so all errors use the same [`ApiError`](springboot2026%202/src/main/java/com/revision/springboot2026/exception/ApiError.java) shape: numeric status, reason, message, and field errors. For example:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request",
  "fieldErrors": { "title": "title must not be blank" }
}
```

### What belongs here, and what does not

Use advice to translate failures into HTTP: validation/binding errors to `400`, missing resources to `404`, business conflicts to `409`, and unexpected failures to a safe `500`. Keep the service focused on business rules; it can throw meaningful application exceptions without depending on `ResponseEntity` or HTTP status codes. Do not catch every exception in every controller, return `200 OK` with an error string, expose stack traces/SQL/exception messages to clients, or silently swallow failures. Log unexpected failures on the server and return a stable public error contract.

Avoid treating every exception as the same error. A broad fallback is useful so clients do not receive a raw stack trace, but it should be the last, least-specific handler. Specific handlers communicate expected outcomes accurately. In a larger API, use a correlation/trace ID in the response and logs, and avoid logging secrets or sensitive payloads.

### Natural interview answer

> “I use `@RestControllerAdvice` to centralize exception-to-HTTP mapping across controllers. The service throws meaningful exceptions such as `BookNotFoundException`; the advice maps that to 404. Bean Validation failures become 400 with field details, and conflicts such as duplicate records become 409. A shared error DTO keeps the response format consistent. Unexpected exceptions are logged with diagnostic details on the server, while the client gets a safe generic 500 response. That keeps HTTP concerns out of the service and avoids duplicating try/catch logic in every endpoint.”

### Likely follow-ups

**What is the difference between `@ControllerAdvice` and `@RestControllerAdvice`?** `@RestControllerAdvice` combines controller advice behavior with response-body semantics. It is convenient for REST APIs returning JSON. `@ControllerAdvice` can be used for broader MVC handling, including view-based applications.

**Does it catch exceptions thrown anywhere in the application?** It handles exceptions raised during MVC controller request processing that propagate to Spring MVC. It is not a universal JVM exception handler for background jobs, startup failures, or arbitrary threads; handle errors at those boundaries too.

**Why not return `ResponseEntity<String>` from every error handler?** A shared error DTO gives clients one predictable schema and supports structured details such as field errors. It is easier for clients, logging, and API documentation to handle consistently.

**Should validation be checked in the controller or service?** Request-shape constraints belong at the API boundary via DTO validation. Business invariants still belong in the service/domain and database because calls may come from other entry points and state may change concurrently.

**Why map a missing record to 404 rather than return null?** `404` expresses that the requested resource does not exist, and the consistent error body gives clients a clear, machine-readable result. Returning null can produce an ambiguous or accidental `200` response.
