# Spring Boot 2026 — Java & Interview Practice

A hands-on scratch project for revising Java, Spring Boot, REST APIs, persistence, testing, and common backend interview coding exercises. Build the project in IntelliJ IDEA and work through the topics in small, runnable steps.

> This repository contains runnable learning code for the completed steps and a README that tracks the next topics. The README itself is a guide, not a generated Spring project.

## Repository learning progress

This status is based on the source files and configuration in this checkout. “Implemented” means there is code to read or run; a topic mentioned in a README is not counted as implemented code.

| Area | Status | What is present / what is still missing |
|---|---|---|
| Step 1: Java fundamentals | ✅ Implemented | Plain-Java practice package demonstrates classes, records, interfaces, enums, collections, equality, ordering, generics, exceptions, `Optional`, lambdas, method references, `java.time`, and complexity. Dedicated tests for the exercises are absent. |
| Step 2: Spring Core + first endpoint | ✅ POC coverage added | Health controller/service plus `/api/core-demo` demonstrate stereotypes, configuration beans, constructor injection, `@Autowired`, `@Qualifier`, `@Primary`, `BeanFactory`/`ApplicationContext`, lifecycle callbacks, singleton/prototype scopes, and component scanning. Definitions and caveats are in the Spring Core guide below. |
| Spring Boot fundamentals from the interview prompt | ✅ POC + definitions added | Why Boot, architecture/startup flow, conditional auto-configuration, existing starters/dependency management, and embedded web server are covered. Profile-based/external configuration is Step 6; Actuator is Step 8. |
| Step 3: REST CRUD | ✅ Implemented | Book GET/POST/PUT/DELETE routes, DTOs, controller/service/repository layers, HTTP status codes, and in-memory storage. PATCH, query parameters, pagination, sorting, and versioning are not implemented. |
| Step 4: validation and errors | ✅ Implemented | DTO constraints, `@Valid`, centralized advice, 400/404/409/500 responses. Automated tests for these paths are absent. |
| Step 5: JPA/Hibernate | ⬜ Not implemented | JPA/H2 dependencies exist, but there is no `@Entity`, `JpaRepository`, database configuration, transaction, relationship, or JPA test. |
| Step 6: profiles/configuration | ⬜ Not implemented | Only `application.properties` with the application name exists; no profiles or `@ConfigurationProperties`. |
| Step 7: automated tests | 🟡 Minimal | One Spring context-load test exists. Service unit, MVC, repository, and full API integration tests are missing. |
| Step 8: operations/deployment | 🟡 Partial | Dockerfile and deployment workflows/documentation exist. Actuator, structured logging/metrics, migrations, API security, caching, and production database settings are absent. |
| Interview coding problems | 🟡 Listed, not solved | README lists DSA exercises, but the exercise implementations and focused tests are missing. |

The learning prompt also asks for broad interview coverage beyond the current Book API. Still to study and code in focused labs: deeper Spring Boot condition-report/startup diagnostics; the full MVC path (filters, `DispatcherServlet`, handler mapping/adapters, interceptors); JPA/Hibernate behavior (persistence context, dirty checking, lazy/eager loading, N+1, relationships, JPQL); transaction ACID/propagation/isolation/rollback; Spring Security authentication/authorization, filter chain, password encoding, JWT, CORS/CSRF; and microservices patterns (HTTP clients, gateways, discovery, resilience, Kafka, sagas, observability). Caching/Redis, connection pools, performance, and concurrency beyond the in-memory map also remain.

For the interview prompt's study priority, cover 🔴 Spring Core/DI, REST + MVC request flow, validation/errors, JPA basics, transactions, and security fundamentals first; 🟡 profiles/configuration, tests, observability, and performance next; 🟢 deeper distributed-system patterns after the foundations. Later code additions should keep using the same order: explain the problem, show the flow, implement one small example, add comments beside unfamiliar annotations, then practice interview questions.

## Goals

- Practice modern Java (Java 21) and core object-oriented design.
- Build a REST API with clear controller, service, and repository responsibilities.
- Persist and validate data, handle errors, and write useful tests.
- Practice coding exercises that commonly appear in Java/backend interviews.
- Understand the build and run workflow with Maven or Gradle.

## Prerequisites

Install IntelliJ IDEA, a JDK, and **one** build tool. Use the versions below as the target for this practice project:

```bash
java -version       # JDK 21
mvn -version        # Maven 3.9.x (if using Maven)
gradle --version    # Gradle 9.8 (if using Gradle)
```

The generated Maven Wrapper (`./mvnw`) or Gradle Wrapper (`./gradlew`) is the preferred way to build after project creation. It keeps the project on its configured build-tool version even if another version is installed globally. Choose a Spring Boot release offered by Spring Initializr that supports Java 21, and keep the version generated by Initializr unless you intentionally upgrade it.

**Current generated Gradle project:** the wrapper in `springboot2026 2/gradle/wrapper/gradle-wrapper.properties` is configured for Gradle **9.7.1**. The Gradle 9.8 command above is the requested target version; to use it, update that wrapper's `distributionUrl` to `https://services.gradle.org/distributions/gradle-9.8-bin.zip`. Running `./gradlew --version` shows the version actually used by the wrapper.

## 1. Create the project in IntelliJ IDEA

1. Open IntelliJ IDEA and choose **New Project** → **Spring Boot**. If that wizard is unavailable, generate the project at [Spring Initializr](https://start.spring.io), extract it, then choose **File → Open** in IntelliJ.
2. Set:
   - **Name / Artifact**: `springboot2026`
   - **Group**: `com.revision`
   - **Package name**: `com.revision.springboot2026`
   - **Language**: Java
   - **JDK / Java**: 21
   - **Packaging**: Jar
   - **Build system**: Maven or Gradle (choose one for this checkout)
3. Choose a Spring Boot version that supports Java 21.
4. Add these dependencies to start:
   - Spring Web
   - Spring Data JPA
   - H2 Database
   - Validation
   - Spring Boot DevTools (optional, for local development)
   - Spring Boot Starter Test (usually included by the generator)
5. Generate/open the project and wait for IntelliJ to finish importing dependencies.
6. In **File → Project Structure**, confirm the Project SDK and language level are 21. In Maven or Gradle settings, use the project wrapper where available.

Avoid adding Lombok at first: writing constructors, accessors, and `equals`/`hashCode` helps with Java practice. Add it later if you want to learn how annotation processing changes the workflow.

## 2. Check the generated project

The generated layout should look similar to this (Gradle uses `build.gradle` or `build.gradle.kts`; Maven uses `pom.xml`):

```text
springboot2026/
├── src/
│   ├── main/
│   │   ├── java/com/revision/springboot2026/
│   │   │   └── Springboot2026Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/com/revision/springboot2026/
├── pom.xml                 # Maven project only
├── mvnw, .mvn/             # Maven wrapper
├── build.gradle            # Gradle project only (or build.gradle.kts)
├── gradlew, gradle/        # Gradle wrapper
└── README.md
```

Keep the application class at the root package (`com.revision.springboot2026`) so Spring component scanning finds the subpackages below it. Do not maintain both build files unless you deliberately want to compare builds; pick Maven or Gradle for the project.

## 3. Run the empty application

From IntelliJ, run the class annotated with `@SpringBootApplication`. Or use the terminal from the project root:

### Maven

```bash
./mvnw spring-boot:run
./mvnw clean test
./mvnw clean package
java -jar target/springboot2026-*.jar
```

### Gradle

```bash
./gradlew bootRun
./gradlew clean test
./gradlew clean build
java -jar build/libs/springboot2026-*.jar
```

On Windows, use `mvnw.cmd` or `gradlew.bat`. Start with the wrapper checked into the generated project, rather than installing another wrapper version by hand.

### Read the startup output

These messages are normal when startup succeeds:

- `No active profile set, falling back to ... "default"` means no profile was selected, so Spring is using the default configuration.
- `Started Springboot2026Application` means Spring finished creating the application context.
- `BUILD SUCCESSFUL` means Gradle completed the requested task. The configuration-cache suggestion is an optional Gradle performance feature.

This checkout already includes Spring Web, Spring Data JPA, Validation, and H2 in `springboot2026 2/build.gradle`. The health and book endpoints can start an HTTP server on port 8080. JPA and H2 are dependencies only at this stage; the book API still uses an in-memory map until Step 5.

## 4. Build the scratch application, step by step

Use a small **Book Catalog API** as the running example. Implement one step at a time and run the app after each change.

### Step 1 — Java fundamentals before Spring

Start in `springboot2026 2/src/main/java/com/revision/springboot2026/practice/PracticeDemo.java`. Run its `main` method from IntelliJ. These examples use plain Java; you do not need to understand Spring annotations to read them.

This project is configured for Java 21, so the examples stick to Java 21 features (including records). Java 25 is the current LTS release as of this writing, but changing the project's Java version is not needed for this lesson. Always check the project's toolchain before copying a newer API into it.

#### How to read a Java line

For example, `Book firstBook = new Book(...)` says: make a variable named `firstBook`, whose type is `Book`, and assign it a newly created `Book` object. `catalog.add(firstBook)` calls the `add` method on the object stored in `catalog`, passing that book as input. The dot means “look at a member of this object or type.” Parentheses call a method; the values inside are its inputs.

When you see a method you do not know, find it instead of guessing:

1. In IntelliJ, put the caret on the method name and press **Ctrl+B** (macOS: **Cmd+B**) to jump to its declaration. **Ctrl+Q** (macOS: **F1**, depending on keymap) shows quick documentation. Autocomplete after typing `object.` also lists methods that are valid for that object's type.
2. For Java library methods such as `Map.putIfAbsent`, `List.copyOf`, or `Optional.map`, open the JDK API docs for the version in this project: [Java 21 API documentation](https://docs.oracle.com/en/java/javase/21/docs/api/). Search the type (`Map`, `List`, `Optional`), then find the method and read its return value and notes.
3. For an unfamiliar method, ask: What object owns it? What inputs does it take? What does it return? Can it change data or throw an exception? What is its cost?
4. Use IDE completion and docs as a lookup aid, not a reason to memorize every method. Practice a small example and inspect the result.

#### What the practice code teaches

- `Book` is a **class**. A constructor (`new Book(...)`) creates it and checks the input. `private` means code outside the class cannot access that field directly; public methods provide controlled access. `final` fields cannot be reassigned after construction. Together with no setters, this makes this example's `Book` immutable.
- `BookRequest` is a **record**, a concise data carrier. Java creates the constructor, accessors like `title()`, and value methods. Its compact constructor validates the values. Records are shallowly immutable: if a record field refers to a mutable list, the list can still change unless copied.
- `Identifiable` is an **interface**, a small promise that a type can provide an ID. `BookCategory` is an **enum**, a fixed set of choices. `@Override` on a method is a standard Java annotation asking the compiler to check that the method really overrides a parent/interface method.
- `List` stores an ordered sequence and may contain duplicates. `Set` stores distinct values, using `equals` and `hashCode` to decide whether values are duplicates. `Map` looks up values by key; here a book ID finds a book. `Queue` represents items waiting their turn; this example processes them first-in, first-out.
- `equals` defines logical equality. `hashCode` must return the same value for objects that are equal, so hash collections can find them. `Comparable` gives `Book` one natural order (year, then title). `Comparator` lets one operation choose a different order (title only).
- `<T extends Identifiable>` is a generic type parameter: this method works with any type that provides `id()`, and Java remembers the actual type so callers do not need a cast. `List.copyOf` and `Map.copyOf` make unmodifiable copies of containers; they do not make mutable objects inside the container immutable.
- `Optional<Book>` means a lookup may produce a book or no book. `isEmpty()` checks absence, `get()` retrieves a present value, `map(...)` transforms it, and `orElse(...)` supplies a fallback. `BookNotFoundException` is a specific failure for the “required book missing” case; `IllegalArgumentException` indicates invalid input.
- A **lambda**, `book -> book.category() == BookCategory.JAVA`, is a small rule supplied to a method. `Predicate<Book>` is the type of a rule that accepts a book and answers true/false. `Book::title` is a **method reference**, shorthand for calling `title()` on a book. A loop is used for filtering here so the execution is visible; the equivalent stream is `books.stream().filter(rule).toList()`.
- `LocalDate` is a calendar date; `Instant` is a point on the UTC timeline; `Duration` is an elapsed amount of time. For a real local appointment that needs a timezone, learn `ZonedDateTime` too.

#### Complexity practice

Let `n` mean the number of books and `k` the number that match a filter. `HashMap` lookup/insertion is expected O(1), with O(n) storage. Filtering checks each item: O(n) time and O(k) output space. Sorting is O(n log n) time. These are growth estimates, not exact runtime promises. For each method, trace a small input and say its time and extra space out loud.

#### The bridge to Spring

Keep `BookCatalog` plain for now. Later, Spring can create a service object and supply its dependencies; a repository can replace the in-memory map; a controller can translate HTTP into a call to the service. A request record is a useful DTO (data transfer object). A JPA entity is a persistence object and should normally be an ordinary class, because persistence providers may need a no-argument constructor, proxies, and mutable state. Check the JPA provider's requirements before choosing a different shape.

#### Spring annotations in beginner language

An annotation is metadata written with `@Name`. It is not a Java keyword and does not usually execute the method by itself. Spring reads annotations while starting the application or handling a request, then follows their instructions. Learn the Java class and method first; then ask what Spring is being told to do.

| Annotation | Plain-language meaning |
|---|---|
| `@SpringBootApplication` | Marks the starting application class. It enables Spring Boot setup and scans this package and its subpackages for Spring-managed classes. |
| `@RestController` | Marks a class whose methods handle web requests and whose return values are written as response data, often JSON. |
| `@RequestMapping("/api/books")` | Gives a controller a shared URL path prefix. |
| `@GetMapping` / `@PostMapping` | Connects a method to an HTTP GET / POST request. The mapping annotation routes a request to the method. |
| `@Component` | General marker for a class Spring should create and manage when it does not have a more specific role. |
| `@Service` | Marks a class that holds application/business work so Spring can create and share it. |
| `@Repository` | Marks a data-access class; Spring can also translate certain persistence errors. Spring Data repository interfaces often need no handwritten implementation. |
| `@Valid` | Asks validation to check the incoming request object against its constraints. |
| `@Entity`, `@Id`, `@GeneratedValue` | These are JPA annotations (persistence metadata), not Spring MVC annotations. They describe a database-mapped class and its identifier. |

Example: in `@GetMapping("/{id}")`, `@GetMapping` tells Spring which request reaches the method; `"/{id}"` is the variable part of the URL. A method parameter annotated `@PathVariable long id` receives that URL value. You can learn these one at a time when reaching Step 2; do not try to memorize every annotation now.

When Spring sees `@Service`, it creates an object (often called a **bean**) and keeps it in the **application context**, Spring's object registry. If a service constructor needs a repository, Spring looks for a matching bean and passes it in. This is **dependency injection**. Constructor injection means the dependency is visible in the constructor and the object cannot be created without it. In current Spring, a class with one constructor usually does not need `@Autowired` on that constructor.

### Step 2 — First endpoint and dependency injection

The working example is in `springboot2026 2/src/main/java/com/revision/springboot2026/`. Read `Springboot2026Application.java`, then `controller/HealthController.java`, and finally `service/HealthService.java`. The Java files have short comments beside their Spring annotations; this section explains the same ideas as a request flow.

#### Run it and see the response

Run `Springboot2026Application.main()` from IntelliJ. Wait until the startup log says the application started. In a browser, visit `http://localhost:8080/api/health`, or call it from a terminal:

```bash
curl -i http://localhost:8080/api/health
```

Expected response body: `{"status":"UP"}`. The `-i` option asks curl to show the HTTP response status and headers too, including `HTTP/1.1 200`. These prints come from the running web server; `PracticeDemo.main()` is a separate plain-Java program. Stop the server with IntelliJ's red stop button.

#### Follow one request

```text
Browser or curl
      ↓ GET /api/health
HealthController.health()
      ↓ calls currentStatus()
HealthService.currentStatus()
      ↓ returns "UP"
HealthController returns a Map
      ↓ Spring converts it to JSON
Browser or curl receives {"status":"UP"}
```

The controller handles the web/HTTP part. The service holds the application rule. The controller returns a Java `Map<String, String>`; Spring's web support converts that result into JSON for the HTTP response. The client never receives a Java object, only response bytes and headers.

#### What each annotation asks Spring to do

An annotation is metadata written with `@`. It is not a Java keyword and does not replace the method or class. Spring reads the metadata and performs framework work that would otherwise require lower-level setup and request-handling code.

| Annotation | What Spring does | What boilerplate it saves us from |
|---|---|---|
| `@SpringBootApplication` | Marks the starting configuration. It brings together Spring Boot configuration, auto-configuration, and component scanning. Scanning starts at this class's package (`com.revision.springboot2026`) and includes child packages such as `controller` and `service`. | Manually assembling much of the application configuration and listing each discovered class. It does not remove the `main` method; `SpringApplication.run(...)` still starts the application. |
| `@RestController` | Registers a web controller and treats its return values as response bodies. It combines the roles of `@Controller` and `@ResponseBody`. | Writing a servlet by hand, manually routing to this class, and manually writing the returned map as JSON. |
| `@RequestMapping("/api/health")` | Sets the shared URL prefix for this controller. | Repeating the complete `/api/health` path on every handler method. |
| `@GetMapping` | Connects the method to an HTTP GET request at the controller path. It is a shortcut for `@RequestMapping(method = RequestMethod.GET)`. | Checking the request method and URL manually in code. |
| `@Service` | Marks `HealthService` as an application service for Spring to discover and manage. It is a specialized kind of component marker. | Writing setup code that manually creates the service with `new` and stores it for the application. |
| `@Component` | General marker for a class Spring should discover and manage when it has no more specific role. | Manually registering a general-purpose object in Spring's configuration. Use `@Service` for business logic and `@Repository` for data access when those roles fit better. |
| `@Repository` | Marks a handwritten data-access class. Spring can apply persistence exception translation; Spring Data repository interfaces are usually discovered through Spring Data configuration. | Manually wiring the data-access object and, in some cases, translating persistence exceptions. This health example has no database, so it does not need a repository. |

The “boilerplate it saves us from” column describes the framework work Spring handles. An annotation does not magically replace all code: for example, `@GetMapping` still needs a Java method to run, and `@SpringBootApplication` still needs `SpringApplication.run(...)` in `main`.

#### What component scanning and the application context mean

**Component scanning** is Spring looking through the application package for classes marked with roles such as `@RestController` and `@Service`. The application class is in the parent package, so Spring can find both classes in the subpackages. If a class is outside the scan area, Spring may not know it exists.

A **bean** is an object Spring creates and manages. The **application context** is Spring's registry of beans and their relationships. At startup, Spring finds `HealthController` and `HealthService`, creates the service bean, and sees that the controller constructor needs a `HealthService`.

#### Constructor injection: how the service gets into the controller

```java
private final HealthService healthService;

public HealthController(HealthService healthService) {
    this.healthService = healthService;
}
```

The constructor says, “a `HealthController` needs a `HealthService` to be created.” Spring finds the matching service bean and passes it as the constructor argument. This is **dependency injection**: the controller receives an object it depends on, rather than making one itself with `new HealthService()`. `final` means this dependency is assigned once. With one constructor, Spring can use it automatically; no `@Autowired` annotation is needed here.

#### Try it yourself

1. Change `"UP"` in `HealthService.currentStatus()` to `"LEARNING"`, restart the app, and request the URL again. The response should change without editing the controller.
2. Change the path in `@RequestMapping`, restart, and request the old path and new path. Only the new path should work.
3. Explain this flow in your own words: request → controller → service → controller response.

This endpoint returns a fixed status, so it demonstrates routing and dependency injection but does not check whether a database or another dependency is actually healthy. A real health check can be added later.
#### Spring Core: definitions and runnable POC

This section covers every topic listed under **Spring Core** in `springlearningpromt.txt`. Read the definition, then open the linked class and call the demo endpoint. Spring Framework internals are simplified here; the POC makes the relationships visible, but it does not reproduce Spring's implementation.

##### The foundation: Spring, IoC, and dependency injection

**Spring Framework** is a Java framework that provides an object container and common application services such as web handling, transactions, and data access. **Spring Boot** builds on Spring Framework: it provides auto-configuration, starter dependencies, and embedded-server setup so an application needs less manual configuration. Boot uses Spring; it does not replace it.

**Inversion of Control (IoC)** means application code does not control every object creation and connection itself. The framework takes that responsibility. **Dependency Injection (DI)** is one way Spring applies IoC: a class declares what it needs, and Spring supplies those objects. For example, `BookController` needs `BookService`, and `BookService` needs `BookRepository`; their constructors declare those dependencies. This avoids classes constructing concrete collaborators with `new`, which would tightly couple their implementation choices.

```text
Springboot2026Application.main()
        ↓ starts
Spring container / ApplicationContext
        ↓ scans, creates, configures, and connects beans
BookController → BookService → BookRepository
```

##### Container, beans, and startup/lifecycle

A **bean** is an object that Spring creates and manages. The **container** is the Spring machinery responsible for creating beans, injecting dependencies, applying framework callbacks, and managing their configured scope. The application's `ApplicationContext` is the main container object used by a Spring Boot app.

`BeanFactory` is the basic bean lookup/container interface. `ApplicationContext` extends the BeanFactory family and adds application features such as events, message resolution, resource loading, and application lifecycle integration. A plain `BeanFactory` usually creates a bean when it is first requested; an `ApplicationContext` normally creates non-lazy singleton beans during startup/refresh. In normal Boot applications, inject `ApplicationContext` only when you truly need container operations; prefer ordinary constructor-injected collaborators for application logic. The POC assigns its `ApplicationContext` to a `BeanFactory` variable and calls `getBean` to demonstrate that relationship.

For a typical singleton bean, a simplified lifecycle is:

```text
Spring creates object
 → injects dependencies
 → runs initialization callbacks
 → bean is ready for use
 → application context closes
 → runs destruction callback (for managed singleton beans)
```

`CoreDemoConfiguration` declares `@Bean(initMethod = "initialize", destroyMethod = "cleanup")`; `CoreLifecycleProbe` logs those callbacks. Watch the application startup and shutdown logs. Behind the scenes, Spring also supports awareness callbacks and `BeanPostProcessor`s before and after initialization; common init callbacks include `@PostConstruct`, `InitializingBean`, and a configured init method. The POC uses configured init/destroy methods to keep the first example explicit. Prototype objects are a caveat: Spring creates and injects them, but generally does not manage their destruction callbacks after handing them to the caller.

##### Bean scopes

| Scope | Meaning | In this project |
|---|---|---|
| `singleton` | One shared instance per Spring application context. This is the default; it does not mean one instance for every JVM or every server. | `CoreDemoComponent` explicitly uses singleton scope; the demo looks it up twice and reports whether it is the same object. |
| `prototype` | Spring creates a new instance each time the container is asked for one. | `PrototypeNote` uses prototype scope. `ObjectProvider.getObject()` requests two objects so the endpoint can show two IDs. |
| `request`, `session`, `application`, `websocket` | Web-aware scopes tied to an HTTP request, session, application, or WebSocket lifecycle. | Not implemented in this core demo. Learn them when you need state tied to a web interaction; do not use them as a replacement for normal stateless services. |

Injecting a prototype directly into a singleton normally gives that singleton one prototype instance at singleton creation time. `ObjectProvider` is used here because it lets the singleton ask for a fresh prototype on demand. Singleton services should normally be stateless or use thread-safe state because many requests can use the same instance concurrently.

##### Component annotations and configuration

| Annotation | Role | POC in this repository |
|---|---|---|
| `@Component` | General-purpose class discovered and managed as a bean. | `FriendlyGreetingFormatter`, `CoreDemoComponent`, and `PrototypeNote`. |
| `@Service` | A component whose role is application/business logic. | `BookService` and `CoreDemoService`. |
| `@Repository` | A component whose role is data access; Spring can translate certain persistence exceptions. | `InMemoryBookRepository`. |
| `@Controller` | Spring MVC controller. A returned string normally names a view. | `TraditionalController`; `@ResponseBody` makes its returned string become response text. |
| `@RestController` | Controller whose handler return values are response bodies; effectively combines `@Controller` and `@ResponseBody`. | `HealthController`, `BookController`, and `CoreDemoController`. |
| `@Configuration` | Declares a Java configuration class whose methods can define beans. | `CoreDemoConfiguration`. |
| `@Bean` | Marks a method whose returned object Spring should manage. Useful for configuring third-party classes or objects created with custom setup. | `CoreDemoConfiguration.coreDemoSettings()` and `coreLifecycleProbe()`. |

`@Component` is usually chosen when no more specific role fits. `@Service` and `@Repository` communicate intent to people and framework extensions. Use `@Bean` when you need an explicit factory method; use component scanning for classes you own and can annotate. Don't create an empty configuration class just to add annotations.

##### Injection and choosing among beans

**Constructor injection** is the default recommendation: dependencies are listed in the constructor, can be stored in `final` fields, and are visible when reading the class. Spring sees one constructor and supplies matching beans. `@Autowired` can mark an injection point, but on a class with one constructor it is optional; the POC marks the `CoreDemoService` constructor so you can recognize it. Avoid field injection in application code because it hides required dependencies and makes plain unit construction harder.

Two classes implement `GreetingFormatter`:

- `FriendlyGreetingFormatter` has `@Primary`. When Spring sees a constructor needing a `GreetingFormatter` without further information, this is the preferred candidate.
- `UppercaseGreetingFormatter` is named `uppercaseGreetingFormatter`. `@Qualifier("uppercaseGreetingFormatter")` asks for this exact candidate where that parameter is needed.

Use `@Primary` for the normal default implementation and `@Qualifier` at an injection point when one particular implementation is required. A qualifier resolves which matching bean to inject; it does not create the bean.

##### Component scanning and configuration classes

`@SpringBootApplication` includes component scanning. Since `Springboot2026Application` is in `com.revision.springboot2026`, Spring scans this package and its subpackages, including `core`, `controller`, `service`, and `repository`. A component outside that package tree must be imported or included in an explicit scan/configuration.

`@Configuration` classes are Java-based configuration. Their `@Bean` methods are registered with the context. Spring processes full `@Configuration` classes so calls between bean methods preserve container behavior; application code should obtain collaborators through injection rather than manually calling configuration methods.

##### Try the POC

Start `Springboot2026Application.main()` and call:

```bash
curl -i http://localhost:8080/api/core-demo
curl -i http://localhost:8080/api/core-demo/traditional-controller
```

The first response shows both formatter choices, the `@Bean` settings object, BeanFactory/ApplicationContext relationship, singleton lookup result, and two prototype IDs. The second returns plain text and demonstrates `@Controller` plus `@ResponseBody`. When the app stops, look for the lifecycle cleanup log. Source is under `springboot2026 2/src/main/java/com/revision/springboot2026/core/` and the controller package.

##### Interview review

**30-second answer:** “Spring is a Java framework that manages application objects and common infrastructure. Its IoC container creates beans and connects their dependencies; constructor injection is the clearest way to declare those dependencies. Spring Boot builds on the framework with starters, auto-configuration, and embedded-server support. Stereotype annotations tell Spring and developers each class's role, while `@Configuration` and `@Bean` provide explicit object construction. Scopes control how bean instances are created and shared.”

Common follow-ups: How does `ApplicationContext` differ from `BeanFactory`? What happens during bean initialization? Is singleton scope thread-safe? When do you use `@Bean` instead of `@Component`? How do `@Qualifier` and `@Primary` interact? Why is constructor injection preferred? Avoid saying that `@Autowired` creates the dependency by itself: Spring's container creates the bean and resolves the dependency; the annotation can identify an injection point.

#### Spring Boot: requested topics and what we are implementing now

Your interview prompt lists the topics below. This repo already schedules configuration/profiles for Step 6 and Actuator for Step 8, so their implementation stays in those steps. The other missing Boot foundations have definitions here and a runnable conditional auto-configuration POC now.

| Topic | Status in this project |
|---|---|
| Why Spring Boot? | Explained below; the app demonstrates it by starting an HTTP server with minimal setup. |
| Spring Boot architecture and startup flow | Explained below and traced from the existing `main` method. |
| Auto-configuration | Runnable conditional POC at `/api/boot-demo`. |
| Starters | Demonstrated by the existing Gradle dependencies. |
| `application.properties` / `application.yml` | The project currently uses `application.properties`; format and external configuration are explained below. No YAML file is needed. |
| Profiles, configuration management, environment variables | Reserved for Step 6; no profile/configuration POC is added here. |
| Embedded server | Demonstrated by the running web application and Dockerfile. |
| Spring Boot Actuator | Reserved for Step 8; dependency and endpoints are not added here. |
| Dependency management | Demonstrated by the Gradle plugins and versionless dependencies. |

##### Why Boot and its architecture

Spring Framework provides the container and APIs. Before Boot, an application team had to assemble many library versions, register framework components, configure the web server, and write repetitive setup. Spring Boot provides useful defaults and conditional configuration so a typical application starts with less setup. You still own the application rules, API design, security, and operational choices. See the [Spring Boot reference guide](https://docs.spring.io/spring-boot/reference/).

```text
Gradle dependencies (starters + Boot dependency management)
                         ↓
main() → SpringApplication.run(...)
                         ↓
             Environment + ApplicationContext
               ↙                     ↘
 component scan              conditional auto-configuration
               ↘                     ↙
          bean definitions → beans are created and connected
                         ↓
           embedded web server listens on port 8080
                         ↓
           routes such as /api/books can receive requests
```

This is a learning diagram, not a strict internal call sequence. In this project, `Springboot2026Application.main()` calls `SpringApplication.run`. The web starter makes this a servlet web application, and Boot starts the embedded server so you run one Java process rather than separately installing/configuring a servlet container. The existing Dockerfile packages that executable application.

##### Auto-configuration: useful defaults with conditions

Auto-configuration means Boot considers configuration based on what is available. It commonly uses conditions such as “is this library on the classpath?”, “is this property enabled?”, and “has the application already supplied this bean?” It is designed to back off when an application provides its own bean for the same role. It is not magic and it does not prevent you from replacing defaults. The Boot docs explain [auto-configuration](https://docs.spring.io/spring-boot/reference/using/auto-configuration.html) and the [conditions report](https://docs.spring.io/spring-boot/reference/using/auto-configuration.html#using.auto-configuration.condition-annotations).

The project POC adds `com.revision.bootpoc.autoconfigure.BootDemoAutoConfiguration`, listed in `src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`. It shows three real conditions:

- `@ConditionalOnClass`: only consider the config if the servlet web class is available.
- `@ConditionalOnProperty`: only consider it when `boot-demo.enabled=true` (the default for this demo).
- `@ConditionalOnMissingBean`: supply a `BootDemoFeature` only if the application has not supplied one already.

The auto-configuration package is intentionally outside the application's component-scan package. Boot discovers it through the imports file, matching the approach used by reusable auto-configuration modules. The demo endpoint at `/api/boot-demo` shows whether the conditional bean exists. Try the default and disabled forms:

```bash
curl -i http://localhost:8080/api/boot-demo
cd "springboot2026 2"  # run Gradle commands from the directory that contains gradlew
# Stop any app already using port 8080, then run:
./gradlew bootRun --args='--boot-demo.enabled=false'
```

For diagnosis, start with `--debug` to see Boot's condition evaluation report. A custom auto-configuration is useful for a shared library; ordinary application configuration usually belongs in `@Configuration` classes and does not need an auto-configuration imports file.

##### Starters, dependency management, and the embedded server

A **starter** is a curated dependency bundle for a kind of application. This project's `spring-boot-starter-web` supplies the servlet web stack; `spring-boot-starter-data-jpa` supplies Spring Data JPA/Hibernate integration; and `spring-boot-starter-validation` supplies request validation. The web starter also brings a default embedded servlet server. Boot can start a self-contained web server; see the [web application reference](https://docs.spring.io/spring-boot/reference/web/).

The Gradle file applies the Spring Boot plugin and `io.spring.dependency-management`. The application dependencies do not specify versions individually because Boot's dependency management supplies a tested set. The Boot plugin version is still explicit (`4.1.1`) because it selects the Boot release. A starter does not itself make code secure or create database tables; it provides compatible libraries and defaults.

##### Properties, YAML, profiles, environment, and Actuator: scheduled topics

`application.properties` is a `key=value` configuration file; this repo's file currently sets only `spring.application.name`. `application.yml`/`application.yaml` can express the same settings as indented structured data. Pick one format for an application rather than maintaining both. The project uses `.properties`.

Profiles select environment-specific configuration (for example `dev` vs `prod`). Externalized configuration lets the same packaged code receive settings from files, environment variables, system properties, or command-line arguments, with defined precedence. Group related application settings with `@ConfigurationProperties` and keep credentials out of source control. These implementations are in Step 6, so this step does not add profile files or bind a settings object. See the official [externalized configuration guide](https://docs.spring.io/spring-boot/reference/features/external-config.html).

Actuator adds operational endpoints such as health and info. It is explicitly scheduled for Step 8, so this step does not add its dependency or expose endpoints. When implemented, expose only the endpoints needed and secure management information; see [Actuator endpoints](https://docs.spring.io/spring-boot/reference/actuator/endpoints.html).

##### Spring Boot startup flow

1. The JVM enters `Springboot2026Application.main()`.
2. `SpringApplication.run(...)` prepares the environment and chooses the application context type.
3. Boot reads configuration sources and registers application configuration, auto-configuration candidates, and scanned components.
4. The context evaluates conditions, creates eligible beans, injects constructor dependencies, and runs initialization callbacks.
5. For this web app, the embedded server starts and registers MVC routes.
6. Startup completes; the health, books, and demo routes can receive requests.
7. On shutdown, Spring closes the context and runs managed bean destruction callbacks.

This omits listeners and other extension hooks. The startup logs are the practical signal: a `Started Springboot2026Application` message means startup completed. The [SpringApplication reference](https://docs.spring.io/spring-boot/reference/features/spring-application.html) describes the lifecycle and startup events.

### Step 3 — Add a resource and CRUD REST API

The first CRUD version is implemented in the `domain`, `dto`, `repository`, `service`, and `controller` packages. Read the code in that order to follow one book operation from storage up to HTTP.

#### The layers and why they are separate

```text
HTTP request / JSON
        ↓
BookController       Knows URLs, HTTP methods, and status codes
        ↓
BookService          Knows the application operation
        ↓
BookRepository       Describes book storage operations
        ↓
InMemoryBookRepository stores books in a Map
```

- `domain/Book.java` is the application's book object: ID, title, author, and publication year. It is a plain Java class, not a JPA entity yet.
- `dto/BookRequest.java` is the data the client may send. It does not contain an ID because the server assigns one. `dto/BookResponse.java` is the data sent back. These records keep the API's JSON shape separate from the storage object.
- `repository/BookRepository.java` is an interface: it names operations (`findAll`, `findById`, `save`, `deleteById`) without choosing a storage implementation. `InMemoryBookRepository.java` implements those operations using a concurrent map and an ID counter. The data disappears when the application stops.
- `service/BookService.java` performs the operation and converts between domain books and response DTOs. It does not know about URL paths or HTTP status codes.
- `controller/BookController.java` connects HTTP requests to service calls. It does not know that this version stores data in a map.

This separation means we can replace the map implementation with database storage later without changing what a controller URL means. The classes are Spring beans: `@Repository` marks the storage implementation, `@Service` marks the service, and `@RestController` marks the HTTP controller. Spring finds these classes by scanning below `com.revision.springboot2026` and supplies dependencies through their constructors.

#### Read the controller annotations and request parameters

| Code | Meaning | Work Spring handles |
|---|---|---|
| `@RestController` | This class handles web requests; return values are response bodies. | Routes matching requests to its methods and converts Java response data to JSON. |
| `@RequestMapping("/api/books")` | Shared URL prefix for this controller. | Combines the prefix with each method's path. |
| `@GetMapping` | Handle GET at `/api/books`. | Matches HTTP method and URL instead of hand-written request checks. |
| `@GetMapping("/{id}")` | Handle GET at a URL such as `/api/books/12`. | Finds the path and provides the value `12` to the method. |
| `@PathVariable long id` | Take `id` from the variable URL segment. | Parses the text segment into a Java `long`. |
| `@PostMapping` / `@PutMapping` / `@DeleteMapping` | Handle POST / PUT / DELETE requests at the controller path (with `/{id}` for the latter two). | Selects the method using HTTP verb and URL. |
| `@RequestBody BookRequest request` | Read the JSON request body and make a `BookRequest` object from it. | Parses JSON and assigns its fields to the Java record. |

`ResponseEntity<BookResponse>` lets a controller choose both the body and HTTP status. `ResponseEntity.ok(...)` means 200, `created(...)` means 201 and includes a `Location` header, `notFound()` means 404, and `noContent()` means 204 with no body. Returning a plain `List` or record lets Spring choose the normal 200 response and convert the body to JSON.

#### The five routes and expected behavior

| Method | Path | Behavior |
|---|---|---|
| `GET` | `/api/books` | List books |
| `GET` | `/api/books/{id}` | Fetch one book or return 404 |
| `POST` | `/api/books` | Create a book and return 201 |
| `PUT` | `/api/books/{id}` | Replace all book fields or return 404 when the ID is missing |
| `DELETE` | `/api/books/{id}` | Delete the book and return 204, or 404 when the ID is missing |

#### Try the endpoints

Start `Springboot2026Application.main()` in IntelliJ. Run these commands from a terminal. The POST response includes the created ID; this in-memory example starts IDs at 1 after each app restart.

List all books (initially an empty JSON array):

```bash
curl -i http://localhost:8080/api/books
```

Create a book. JSON field names match the `BookRequest` record component names:

```bash
curl -i -X POST http://localhost:8080/api/books \
  -H 'Content-Type: application/json' \
  -d '{"title":"Effective Java","author":"Joshua Bloch","publicationYear":2018}'
```

Fetch the created book (assuming the returned ID was `1`):

```bash
curl -i http://localhost:8080/api/books/1
```

Replace all editable book fields. PUT is a full replacement in this exercise, so send every field:

```bash
curl -i -X PUT http://localhost:8080/api/books/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Effective Java, Third Edition","author":"Joshua Bloch","publicationYear":2018}'
```

Delete it. A successful delete has status 204 and an empty response body:

```bash
curl -i -X DELETE http://localhost:8080/api/books/1
```

Try `GET /api/books/999` and `DELETE /api/books/999` too; both should return 404. Stop the app with IntelliJ's stop button when finished.

#### Trace POST from JSON to the response

For POST, Spring reads the JSON body because of `@RequestBody` and creates `BookRequest`. `@Valid` checks its constraints before the controller runs. The controller passes the valid request to `BookService.create`. The service checks the duplicate rule, makes a `Book` with a temporary ID of 0, and asks the repository to save it. The map implementation assigns the next ID, stores it, and returns it. The service maps the saved book into `BookResponse`. Finally, the controller returns HTTP 201 with the response body and a `Location` header. Step 4 shows how validation and error responses work.

#### Why use an interface for the repository?

`BookService` depends on the `BookRepository` interface, not `InMemoryBookRepository` directly. The interface is the promise of what storage can do; the in-memory class is one way to do it. Later we can provide a database implementation with the same operations. This is also dependency injection: Spring sees the repository constructor parameter and supplies the repository bean. There is one implementation now, so Spring knows which object to pass.

The Map is suitable for this first local exercise: looking up by key is expected O(1), while listing all books is O(n). It is not persistent storage: a restart clears every book, and the data is not shared across application instances. Step 5 replaces it with JPA and a database.

The package layout now follows these responsibilities:

```text
com.revision.springboot2026
├── controller/       # HTTP mapping, status codes, request/response types
├── service/          # business rules and transaction boundary
├── repository/       # storage contract and in-memory implementation
├── domain/           # application domain objects
├── dto/              # API request and response models
├── exception/        # domain errors and HTTP error mapping
└── config/           # explicit application configuration
```

### Step 4 — Validation and predictable errors

This step is now connected end to end. Look at `dto/BookRequest.java`, `controller/BookController.java`, `service/BookService.java`, and the three files under `exception/`.

#### Validate the request at the HTTP boundary

`BookRequest` uses Jakarta Bean Validation constraints:

- `@NotBlank` rejects null, empty, or whitespace-only text.
- `@Size(max = ...)` limits title and author lengths.
- `@Min(0)` rejects negative publication years.
- `@Valid` on each `@RequestBody` parameter asks Spring to check these rules before it calls the controller method.

When validation fails, Spring raises `MethodArgumentNotValidException`. `ApiExceptionHandler` catches it and returns HTTP 400 with a stable JSON body containing field errors. Malformed JSON or a value that cannot be converted into the request record is handled as HTTP 400 too. Validation is kept in the request DTO so invalid client input does not reach the service.

Try these requests while the app is running:

```bash
# Valid create: 201 Created
curl -i -X POST http://localhost:8080/api/books \
  -H 'Content-Type: application/json' \
  -d '{"title":"Dune","author":"Frank Herbert","publicationYear":1965}'

# Blank title: 400 Bad Request with a title field error
curl -i -X POST http://localhost:8080/api/books \
  -H 'Content-Type: application/json' \
  -d '{"title":"  ","author":"Frank Herbert","publicationYear":1965}'

# Negative year: 400 Bad Request
curl -i -X POST http://localhost:8080/api/books \
  -H 'Content-Type: application/json' \
  -d '{"title":"Dune","author":"Frank Herbert","publicationYear":-1}'

# Malformed JSON: 400 Bad Request
curl -i -X POST http://localhost:8080/api/books \
  -H 'Content-Type: application/json' \
  -d '{"title":"Dune",'
```

#### Keep business errors separate from HTTP

`BookService` throws `BookNotFoundException` when an ID does not exist and `DuplicateBookException` when the same title/author combination is submitted. The service describes what went wrong in application terms; it does not choose HTTP status codes. `ApiExceptionHandler`, marked with `@RestControllerAdvice`, handles exceptions from controllers centrally:

| Failure | HTTP status | Why |
|---|---:|---|
| Invalid fields, malformed/missing JSON | 400 Bad Request | The request cannot be accepted as submitted. |
| Missing book ID | 404 Not Found | The requested resource does not exist. |
| Duplicate title and author | 409 Conflict | The request conflicts with the current catalog. |
| Unexpected internal failure | 500 Internal Server Error | The response gives a generic message, not an exception or stack trace. |

`@ExceptionHandler` selects a method for a particular exception type. More specific handlers are used for validation, not-found, and duplicate errors; the generic `Exception` handler is a final safety net. `ApiError` gives errors a consistent shape: status, short error name, safe message, and a map of field errors.

This demonstrates `@ControllerAdvice`/`@RestControllerAdvice` and `@ExceptionHandler`, but the handler currently returns a small application-specific error record. Spring MVC also has built-in error handling; later you can compare it with a customized `ProblemDetail` response.

#### Duplicate rule and its cost

The exercise treats title and author as a duplicate pair, ignoring letter case and surrounding spaces. The service checks the current list before saving, which is O(n) for n books. That is fine for this small in-memory lesson. A real database should enforce uniqueness with a unique constraint because a read-then-write check alone can race when two requests arrive at the same time.

Validation does not prove that a request is authorized, that the book is unique in a distributed system, or that a database write will succeed. Those concerns belong to later security, persistence, and transaction lessons.

### Step 5 — Add persistence with JPA and H2

1. Mark the persistence model with `@Entity`; define its ID with `@Id` and `@GeneratedValue`.
2. Create a `BookRepository` extending `JpaRepository<Book, Long>`.
3. Add a service using constructor injection and `@Transactional` for write operations.
4. Configure an in-memory H2 database in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:springboot2026
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

Use `ddl-auto=update` only for this disposable learning database. Learn schema migrations with Flyway or Liquibase before using a persistent or shared database. Keep H2 Console development-only and do not expose it in production.

Practice repository-derived queries, pagination (`Pageable`), sorting, and the difference between lazy and eager associations. Avoid returning JPA entities directly from API endpoints; map to DTOs.

### Step 6 — Configuration and profiles

Move environment-specific settings out of Java code. Add `application-dev.properties` and `application-test.properties`, then activate a profile from IntelliJ's run configuration or with:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
# Gradle equivalent
./gradlew bootRun --args='--spring.profiles.active=dev'
```

Use `@ConfigurationProperties` for grouped settings. Never commit real passwords, API keys, or production connection strings.

### Step 7 — Tests at multiple levels

- **Unit test** the service with a mocked repository; cover success and failure paths.
- **MVC slice test** controller validation, JSON, and status codes with `@WebMvcTest` and `MockMvc`.
- **Repository test** persistence behavior with `@DataJpaTest`.
- **Integration test** the application flow with `@SpringBootTest` when the wider context is the behavior being tested.

Give tests descriptive names, isolate test data, and assert observable behavior. A test that only verifies a mock was called is not a substitute for checking the API contract.

### Step 8 — Production-minded additions

After CRUD and tests work, implement these as focused exercises:

- Structured logging and request correlation; never log secrets.
- Spring Boot Actuator health/info endpoints and deliberate exposure configuration.
- Database migration with Flyway or Liquibase.
- Spring Security: secure one route, understand authentication vs authorization, then implement a small role-based rule. Treat JWT as a protocol and key-management topic, not merely a token-generation exercise.
- Pagination, filtering, sorting, and API versioning tradeoffs.
- A Dockerfile and compose-based local database if containers are part of your workflow.
- Concurrency and transaction behavior: isolation, lost updates, optimistic locking, and idempotency.

### Optional — Deploy the API with GitHub Actions and Google Cloud Run

This repository has a Dockerfile in `springboot2026 2/` and a workflow at `.github/workflows/deploy-cloud-run.yml`. For pull requests to `master` and relevant pushes, GitHub Actions first runs the Gradle tests/build and builds the Docker image. A push to `master` deploys to Cloud Run only after those checks pass. Pull requests only run the checks; they do not deploy. You can also start the workflow manually from GitHub's **Actions** tab. Cloud Build uses the source directory's Dockerfile to build the Java 21 container before Cloud Run starts it.

#### Why Cloud Run, and what does “free” mean?

Cloud Run is a practical fit for a small Spring Boot API: it accepts containers and automatically scales instances with incoming traffic. Google provides a monthly free usage allowance, but this is not a promise that every deployment stays free. A billing account is required, and usage beyond the allowance can be charged. Check the [current Cloud Run pricing](https://cloud.google.com/run/pricing) and set a budget alert before relying on it.

For users in India, `asia-south1` (Mumbai) is available as a region. The free configuration can scale down to zero, so the first request after inactivity may take longer while the app starts. Keeping an instance warm can reduce that delay but may incur a charge. [Cloud Run regions](https://cloud.google.com/run/docs/locations)

This application currently keeps books in memory. The workflow caps it at one instance so concurrent instances do not each hold a different copy of the book list. Data is still lost when that instance restarts or scales down. Durable storage and strong availability require a database and suitable running capacity; expect those to add cost.

#### One-time setup

1. Create a Google Cloud project and enable billing. Enable the Cloud Run, Cloud Build, Artifact Registry, and IAM Credentials APIs.
2. Set up [Workload Identity Federation for GitHub Actions](https://github.com/google-github-actions/auth#setting-up-workload-identity-federation). Restrict the identity provider to this GitHub repository, and use a dedicated deploy service account. For source deployments, Google lists these roles for the deployer: `roles/run.sourceDeveloper`, `roles/serviceusage.serviceUsageConsumer`, and `roles/iam.serviceAccountUser` on the Cloud Run service identity. Grant the Cloud Build service account `roles/run.builder`. See Google's [source deployment guide](https://cloud.google.com/run/docs/deploying-source-code) for details.
3. In GitHub, open **Settings → Secrets and variables → Actions → Variables** and create these repository variables:

   | Variable | Value |
   |---|---|
   | `GCP_PROJECT_ID` | Your Google Cloud project ID |
   | `GCP_REGION` | `asia-south1` for Mumbai, or another supported region |
   | `GCP_WORKLOAD_IDENTITY_PROVIDER` | Full Workload Identity Provider resource name |
   | `GCP_DEPLOY_SERVICE_ACCOUNT` | Deploy service account email |
   | `CLOUD_RUN_SERVICE` | For example, `springboot2026-api` |

   Workload Identity Federation gives GitHub short-lived credentials. Do not create or store a long-lived service-account key in the repository.
4. Merge the workflow and application files to `master`, or manually run **Deploy Spring Boot API to Cloud Run** from the **Actions** tab after the variables and cloud permissions are configured. The workflow log shows the service URL. Open `/api/health` or `/api/books` on that URL to reach the API.

The current automated test suite contains a Spring application context-load test. The workflow runs that test and builds the executable jar, then builds the Docker image. Add focused service and API tests as those behaviors are implemented; the existing workflow will run them automatically.

### Deploy to the EC2 development instance from `master`

The workflow at `.github/workflows/deploy-ec2-master.yml` runs tests and builds the JAR on GitHub-hosted runners for pull requests to `master`. A push to `master` deploys that JAR using a self-hosted GitHub Actions runner on the EC2 instance, restarts the `springboot2026` systemd service, and checks `/api/health`. This deployment does not require Docker or inbound SSH access from GitHub. The existing Cloud Run workflow also deploys pushes to `master`; leave it enabled only if you intend to deploy to both EC2 and Cloud Run.

#### One-time EC2 setup

This service file assumes the instance login is `ec2-user` and Java is at `/usr/bin/java`. Adjust `deploy/springboot2026.service` if your instance uses a different login or Java path.

1. From the repository root on your Mac, copy and install the service unit (replace the address if the instance's public IP changes):

   ```bash
   scp deploy/springboot2026.service ec2-user@<EC2_PUBLIC_IP>:/tmp/springboot2026.service
   ssh ec2-user@<EC2_PUBLIC_IP> 'sudo install -o root -g root -m 0644 /tmp/springboot2026.service /etc/systemd/system/springboot2026.service && sudo systemctl daemon-reload && sudo systemctl enable springboot2026'
   ```

2. Allow the deploy account to restart only this service without an interactive sudo prompt. On the instance, run `sudo visudo -f /etc/sudoers.d/springboot2026-deploy` and add:

   ```text
   ec2-user ALL=(root) NOPASSWD: /usr/bin/systemctl restart springboot2026
   ```

   Save, then verify the file with `sudo visudo -cf /etc/sudoers.d/springboot2026-deploy`.

3. Install a self-hosted GitHub Actions runner on the instance. In the repository, open **Settings → Actions → Runners → New self-hosted runner**, choose **Linux** and **x64**, then follow GitHub's current download and configuration commands while logged in as `ec2-user`. Add the label `ec2-dev` when configuring the runner. Install and start it as a service with the `svc.sh` commands GitHub shows. Run it as `ec2-user`, never as root. Do not use this runner for pull-request jobs from untrusted contributors; this workflow uses GitHub-hosted runners for PR builds and schedules the EC2 runner only for pushes to `master`.
4. Make sure the EC2 security group allows inbound TCP 8080 from the clients that should reach the API. The workflow's health check runs on the instance itself, so it does not need public access to port 8080 or SSH access from GitHub.
5. Stop any manually started copy of the application before its first service-managed deployment. Push an application change to `master`; the Actions run must pass the build job before deployment starts. Check the workflow log and then request `http://<EC2_PUBLIC_IP>:8080/api/health`.

Each deployment keeps a commit-specific JAR under `~/springboot2026/releases` for manual rollback. The EC2 public IP should be replaced with an Elastic IP if you need a stable endpoint; Elastic IP billing depends on current AWS account and usage terms.

### Alternative — Deploy to AWS with GitHub Actions and ECS Express Mode

This is a setup guide; the repository's current deployment workflow still targets Cloud Run. AWS App Runner is closed to new customers, so for a new AWS account the managed container option to investigate is **Amazon ECS Express Mode**. It accepts a container image and creates an ECS/Fargate service, load balancer, networking, and autoscaling. Express Mode has no separate service fee, but its Fargate, load balancer, logs, and data transfer are billed resources. [AWS App Runner availability notice](https://docs.aws.amazon.com/apprunner/latest/dg/apprunner-availability-change.html), [ECS Express Mode overview and pricing](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/express-service-overview.html)

AWS Free Plan credits are temporary: new customers can get up to $200, and the Free Plan ends after six months or when credits are used up. The Free Plan also limits which AWS services can be used. Check that ECS/Fargate is available to your account and estimate the full resources before creating them. [AWS Free Tier FAQ](https://aws.amazon.com/free/free-tier-faqs/)

#### Setup steps

1. **Check the account and budget.** In AWS Console → Billing, confirm whether your account is on the Free or Paid Plan, check the remaining credits and expiry date, and create a budget alert. Do not upgrade the account plan unless you have decided that you accept pay-as-you-go charges.
2. **Choose the AWS region.** `ap-south-1` (Mumbai) is a reasonable starting region for users in India. Confirm that ECS Express Mode and the supporting services are available in the selected region.
3. **Build the container locally.** From the application directory, run `docker build --tag springboot2026-api:local .`. The repository Dockerfile packages Java 21 and exposes port 8080. If the build fails, resolve that before configuring AWS; the container must be buildable before it can be pushed to ECR.
4. **Create an Amazon ECR repository.** ECR stores the image that ECS Express Mode runs. Use one repository, for example `springboot2026-api`, in the same AWS account and region as the service. AWS's deployment example uses an image tag based on the Git commit so each release can be identified and rolled back.
5. **Create the ECS service roles.** ECS Express Mode needs a task execution role and an infrastructure role. Follow AWS's [first-service guide](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/express-service-getting-started.html); avoid using broad administrator permissions for the application runtime.
6. **Create a GitHub Actions deploy role with OIDC.** Add the GitHub OIDC identity provider in IAM and create a role whose trust policy is restricted to this GitHub repository and the `master` branch. Give it only the ECR push and ECS Express deployment permissions needed by the workflow. The workflow should use short-lived OIDC credentials, never a saved AWS access key. See the AWS-maintained [ECS Express Mode GitHub Action](https://github.com/aws-actions/amazon-ecs-deploy-express-service) and its IAM permission list.
7. **Create the ECS Express Mode service from an image.** Create the service with the image in ECR, container port `8080`, and health-check path `/api/health`. Because this example currently stores books in memory, start with one task (`minTaskCount: 1`, `maxTaskCount: 1`) to avoid different instances holding different book lists. This keeps compute running and may use credits or incur charges; the in-memory data can still disappear on a restart. Move the data to a shared database before scaling beyond one task.
8. **Add GitHub repository variables** under **Settings → Secrets and variables → Actions → Variables**:

   | Variable | Example / value |
   |---|---|
   | `AWS_REGION` | `ap-south-1` |
   | `AWS_ACCOUNT_ID` | Your 12-digit AWS account ID |
   | `AWS_ROLE_ARN` | ARN of the GitHub OIDC deploy role |
   | `ECR_REPOSITORY` | `springboot2026-api` |
   | `ECS_SERVICE` | Name of the ECS Express Mode service |
   | `ECS_EXECUTION_ROLE_ARN` | ECS task execution role ARN |
   | `ECS_INFRASTRUCTURE_ROLE_ARN` | ECS Express infrastructure role ARN |

9. **Wire the AWS workflow.** The workflow should run the existing verification job first, authenticate with `aws-actions/configure-aws-credentials`, log in to ECR, build and push the Docker image tagged with the commit SHA, then deploy that exact image with `aws-actions/amazon-ecs-deploy-express-service`. Use the AWS-maintained workflow example for the current action inputs and IAM permissions. Deploy only from `master`; pull requests should verify the code and build the image without deploying.
10. **Smoke-test and roll back by image tag.** After deployment completes, request `https://<service-url>/api/health` and then exercise the book API. Keep the previous commit-tagged image in ECR so the workflow can redeploy it if the new revision fails.

Do not enable both the Cloud Run and AWS workflows to deploy automatically on every `master` push unless deploying to both providers is intentional. Keep this AWS path as a reviewed alternative until the account's Free Plan access, costs, and target service are confirmed.

## 5. Interview coding practice

Create `src/test/java/.../practice/` tests for each problem first. Implement the solution as a small pure Java method, document complexity, and include edge cases. Do not solve these with a controller or database.

### Arrays, strings, and maps

- Two Sum: return the indices of two values matching a target. Target expected `O(n)` time with a hash map.
- First non-repeating character: return the first character with frequency one.
- Anagram check: compare character counts; state whether case, spaces, and Unicode matter.
- Longest substring without repeating characters: sliding window; target `O(n)`.
- Merge overlapping intervals: sort then scan; target `O(n log n)`.
- Top K frequent values: use a frequency map and heap or bucket strategy.

### Collections, stacks, and linked structures

- Valid parentheses using a stack; discuss behavior for non-bracket characters.
- Implement an LRU cache with a hash map plus doubly linked list; explain `O(1)` get/put.
- Reverse a linked list iteratively and recursively; detect a cycle with slow/fast pointers.
- Find duplicates while respecting whether output order must be preserved.

### Search, sorting, and data structures

- Binary search in a sorted array, including empty input and duplicate values.
- Find the first and last position of a target with lower/upper bounds.
- Implement a queue using two stacks or a stack using queues.
- Compare a heap, balanced search tree, and hash map for a use case.

### Java and backend design prompts

- Explain `HashMap` hashing, collisions, equality, and why mutable keys are risky.
- Explain `ArrayList` vs `LinkedList`, checked vs unchecked exceptions, and `==` vs `equals`.
- Design an immutable value object and define equality correctly.
- Design a rate limiter, idempotent create endpoint, or paginated search API; identify data structures and failure cases.
- Explain transaction boundaries, N+1 queries, optimistic locking, and what belongs in controller/service/repository layers.

For every problem, say the assumptions out loud, start with a correct simple approach, improve it, state time/space complexity, and test empty, minimal, duplicate, and boundary inputs.

## 6. Suggested revision checklist

- [ ] Java syntax, OOP, immutability, collections, generics, exceptions, streams, and `java.time`
- [ ] Big-O analysis and array/string/map/stack/queue/tree coding problems
- [ ] Spring IoC, application context, component scanning, and constructor injection
- [ ] REST methods, status codes, DTOs, serialization, and validation
- [ ] Service/repository boundaries and transaction basics
- [ ] JPA entities, repositories, relationships, pagination, and query performance
- [ ] Central exception handling and safe error responses
- [ ] Profiles and externalized configuration
- [ ] Unit, MVC slice, repository, and integration tests
- [ ] Security fundamentals, Actuator, migrations, logging, and operational basics
- [ ] Explain tradeoffs and complexity clearly during a code review or interview

## Troubleshooting

```bash
# Check which process owns the default port
lsof -i :8080

# Override the server port for one run
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
./gradlew bootRun --args='--server.port=8081'

# Run with dev profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
./gradlew bootRun --args='--spring.profiles.active=dev'
```

If the build fails, first check the selected JDK in IntelliJ and `java -version`, then check dependency resolution and the wrapper's configured version. Avoid `kill -9` as a routine fix; stop the application from IntelliJ or identify the process before terminating it.
