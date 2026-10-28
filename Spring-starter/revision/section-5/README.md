# Section 5: Persisting Users with JPA

Is section mein Section 4 ke in-memory User API ko database-backed persistence ki taraf le jayenge. Saare lesson modules isi `README.md` mein rahenge—har topic ke liye alag Markdown file nahi banegi. Code/configuration changes alag approval steps honge; ek waqt mein sirf current approved file edit hogi.

## Section roadmap

| Lesson | Topic | Duration | Course status |
|---|---|---:|---|
| Play44 | Introducing and Understanding JPA | 8 min | Not completed |
| Play45 | Understanding Data Access Layer | 3 min | Not completed |
| Play46 | H2 database | 3 min | Not completed |
| Play47 | NEW — Spring Boot 4 Update | 1 min | Not completed |
| Play48 | Configuring our Project for JPA | 10 min | Not completed |
| Play49 | Understanding Entities in JPA | 10 min | Not completed |
| Play50 | Defining JPA Repositories | 5 min | Not completed |
| Play51 | Making User Persistent | 9 min | Not completed |
| Play52 | Testing changes | 15 min | Not completed |

## Play44 — Introducing and Understanding JPA

**Course duration:** 8 minutes · **Course status:** Not completed

JPA (Jakarta Persistence) Java applications mein relational data ko objects ke saath map karne ka standard hai. Java object ko database row aur uske fields ko columns ke saath map kiya ja sakta hai. Isse application domain objects ke through data par kaam kar sakti hai, jabki persistence provider mapping aur database operations handle karta hai.

JPA khud database nahi hai aur na hi ek specific implementation. Hibernate ek common JPA provider hai; Spring Data JPA repository abstraction aur common data-access code deta hai. Spring Boot configuration ke through in components ko application mein wire kar sakta hai.

Section 4 mein users ek Java `List` mein store hote hain, isliye app restart par list reset hoti hai. JPA-backed persistence ka goal user data ko relational database mein store karna hai, jisse application restart ke baad bhi records available rahen.

### Basic conceptual flow

```text
Application User object banati ya update karti hai
        ↓
Repository / EntityManager persistence operation karta hai
        ↓
JPA provider object-relational mapping apply karta hai
        ↓
SQL database row ko read ya write karta hai
```

### JPA, Hibernate aur Spring Data JPA ka farq

- **JPA:** persistence mapping aur operations ke liye standard/API contract.
- **Hibernate:** JPA ko implement karne wala persistence provider; SQL execution aur object-row mapping karta hai.
- **Spring Data JPA:** repository interfaces aur common CRUD operations provide karne wali Spring abstraction.

In terms ko interchangeable mat samjho: JPA specification hai, Hibernate implementation hai, aur Spring Data JPA repository layer ko simplify karta hai.

### Current project se relation

Section 5 ke start par project Spring Boot 4.1.1 aur Java 21 par tha, aur `UserService` in-memory `List` use karta tha. Play48 ke approved setup ke baad `pom.xml` mein JPA/H2 dependencies aur `application.properties` mein in-memory datasource/JPA settings maujood hain. Is section ke implementation steps mein `User` entity aur `UserRepository` add kiye gaye, aur `UserService` ko repository-backed banaya gaya. Neeche Play49–52 mein mapping, data flow aur verification ka breakdown hai.

### Is section ka implementation flow

1. JPA/H2 dependency versions aur compatibility verify karna.
2. Approved JPA/H2 dependencies ko `pom.xml` mein add karna.
3. H2 connection aur JPA settings configure karna.
4. Existing `User` class ko JPA entity banana, table identity aur generated ID samajhna.
5. `JpaRepository` interface define karna.
6. `UserService` ko in-memory list se repository-backed operations mein badalna.
7. API aur persistence behavior test karna, including create/read/update aur restart/configuration expectations.

Setup, entity mapping, repository aur service implementation steps complete hain. Har coding/configuration file review ke liye alag dikhayi gayi aur `PASS` ke baad hi agle file par kaam hua. Existing Java version aur Spring Boot version nahi badle.

### Recap

JPA relational database persistence ke liye Java standard hai. Hibernate provider ke roop mein JPA implement karta hai, jabki Spring Data JPA repository code ko simplify karta hai. Is section mein in-memory user list ko entity/repository-backed H2 persistence se replace kiya gaya; runtime verification Play52 ke checklist mein hai.

## Play45 — Understanding the Data Access Layer

**Course duration:** 3 minutes · **Course status:** Not completed

Data Access Layer (DAL) application ke business code aur data source ke beech boundary hoti hai. Is layer ka kaam data read/write karna hai, taaki controller ko SQL ya storage details handle na karni pade.

Is project ka intended request flow:

```text
HTTP request
        ↓
Controller — route aur HTTP request/response
        ↓
Service — user-related application logic
        ↓
Repository — data access abstraction
        ↓
JPA provider (Hibernate) — ORM aur SQL execution
        ↓
Database (is section mein H2)
```

Section 4 mein `UserService` seedha in-memory `List<User>` use karta tha. Ab `UserRepository` database operations ka boundary hai. Service user operations ko coordinate karti hai, aur controller HTTP layer tak limited rehta hai. Har chhote example mein extra layer add karna zaroori nahi, lekin repository ko service/controller responsibilities se alag rakhne se data access ko baad mein replace ya test karna aasaan ho sakta hai.

### JPA repository ka role

Spring Data JPA repository aam taur par entity type aur ID type ke saath declare hoti hai. Spring Data common CRUD operations ka implementation provide kar sakta hai, isliye basic reads/writes ke liye har baar DAO implementation manually likhne ki zaroorat nahi hoti. Custom queries ki zaroorat ho toh woh alag define ki ja sakti hain.

### Trade-off aur boundaries

- Repository persistence details ko encapsulate karti hai, lekin service ko business decisions ka owner rehna chahiye.
- Controller se repository ko directly call karna chhote demo mein possible hai, par layers mix ho jaati hain aur business logic test/maintain karna mushkil ho sakta hai.
- DAL abstraction database ko automatically replaceable nahi bana deti—database-specific SQL, transaction behavior aur schema differences phir bhi matter karte hain.

### Recap

DAL data storage operations ko application ke baaki parts se separate karti hai. Is User API ka flow Controller → Service → Repository → JPA provider → H2 hai. Entity, repository aur service implementation maujood hain; context aur main API flow ka automated test pass hua, manual edge checks Play52 mein listed hain.

## Play46 — H2 Database

**Course duration:** 3 minutes · **Course status:** Not completed

H2 ek lightweight relational database hai jo Java applications ke saath embedded mode mein chal sakta hai. Learning aur automated tests mein iska setup quick hota hai, kyunki local database server alag se install/run karna zaroori nahi hota.

### In-memory aur file-based modes

- **In-memory H2:** database process/application lifetime tak memory mein rehta hai. App band hone par data aam taur par chala jaata hai. Short exercises aur isolated tests ke liye useful.
- **File-based H2:** database files disk par store hoti hain, isliye process restart ke baad data reh sakta hai. File path/configuration aur cleanup ka dhyan rakhna padta hai.

H2 kaunsa mode use kar raha hai, yeh JDBC URL/configuration par depend karta hai. Sirf “H2 use ho raha hai” kehne se data persistence across restarts guarantee nahi hoti.

### Spring Boot project mein role

H2 database engine hai; JPA/Hibernate usse connect karke entity operations ko SQL mein translate karte hain. Spring Boot datasource configuration se connection setup hota hai. Development/test ke liye embedded H2 convenient hai, lekin production-like behavior verify karna ho toh target production database ke saath bhi tests zaroori ho sakte hain—dialect, constraints aur SQL behavior different ho sakte hain.

```text
Spring Data repository call
        ↓
JPA provider (Hibernate)
        ↓ JDBC
H2 relational database
```

### Current workspace se relation

Play48 ke approved setup ke baad H2 driver `pom.xml` mein aur datasource URL `application.properties` mein configured hain. Play49–51 mein `User` entity, repository aur repository-backed service add hue; runtime verification ke steps Play52 mein diye hain.

### Common confusion

- H2 ko JPA ya Hibernate samajhna: H2 database hai, JPA persistence standard hai, Hibernate provider hai.
- In-memory H2 ko durable database samajhna: app/process band hone par data aam taur par reset hota hai.
- Embedded database par pass hue tests ko production DB compatibility ka proof maan lena.

### Recap

H2 ek lightweight relational database hai jo local learning aur tests mein convenient hai. Database mode aur JDBC URL decide karte hain ki data memory-only hai ya file-backed. Is project mein Play48 se `jdbc:h2:mem:userdb` configure hai aur Play49–51 mein entity persistence implement hui; API integration test pass hua, restart behavior manually verify karna baaki hai.

## Play47 — NEW: Spring Boot 4 Update

**Course duration:** 1 minute · **Course status:** Not completed

Spring Boot 4, Spring Framework 7 par based major generation hai. Is section ke liye practical point yeh hai ki Boot 4 project mein JPA/H2 dependencies add karte waqt Boot-managed versions aur current starter/module names use hon; purane tutorials ke dependency names ko bina verify kiye copy na karein.

Current workspace Spring Boot 4.1.1 aur Java 21 use karta hai. Play48 mein compatible JPA/H2 dependencies aur datasource configuration add ki gayi; Boot/Java version upgrade nahi hua.

Major version ke saath migration/API changes ho sakte hain. Existing project ke liye official Boot 4 documentation, migration guide aur system requirements dekho; phir dependencies, build, tests aur runtime behavior verify karo. Sirf version number dekhkar compatibility assume mat karo.

**Official references:** [Spring Boot 4.0 release announcement](https://spring.io/blog/2025/11/20/spring-boot-4-0-0-available-now/) · [Spring Boot 4.1 documentation](https://docs.spring.io/spring-boot/4.1/) · [System requirements](https://docs.spring.io/spring-boot/4.1/system-requirements.html) · [Migration guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)

## Play48 — Configuring the Project for JPA

**Course duration:** 10 minutes · **Course status:** Not completed

JPA use karne ke liye project ko persistence starter aur database driver chahiye. Is project mein Spring Boot 4.1.1 parent versions manage karta hai, isliye JPA/H2 dependencies ke explicit versions set nahi kiye. Approved `pom.xml` changes mein `spring-boot-starter-data-jpa` aur runtime-scoped H2 driver add hue.

Datasource aur JPA properties `src/main/resources/application.properties` mein hain:

```properties
spring.datasource.url=jdbc:h2:mem:userdb;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

- `spring.datasource.url` H2 in-memory database select karta hai; process/app restart ke baad data reset hota hai.
- `spring.datasource.username` aur blank password local demo connection ke liye hain—production credentials nahi.
- `spring.jpa.hibernate.ddl-auto=update` Hibernate ko discovered entity mappings ke mutabik development schema update karne deta hai. Isse production schema migrations ka substitute na samjho.
- `spring.jpa.show-sql=true` generated SQL console mein dikhata hai, jo learning ke liye helpful hai; verbose logging ke trade-off ka dhyan rakho.
- `DB_CLOSE_ON_EXIT=FALSE` embedded database shutdown ko Spring Boot ke lifecycle se coordinate karne mein help karta hai.

**Implementation update:** Play49 mein `User` ko `@Entity` banaya gaya, Play50 mein `UserRepository` add hua, aur Play51 mein service ke in-memory list operations repository calls se replace hue. Isse application H2 mein users read/write kar sakti hai; actual runtime verification Play52 ke steps se karein.

### Configuration verify karna

Entity aur repository present hain. `mvn test` ke dauran application context, datasource aur JPA initialization successful rahe. Sirf properties file hona persistence verify nahi karta; API integration test ne database-backed CRUD flow exercise kiya. H2 in-memory hone ke kaaran process restart ke across records rehne ki expectation nahi rakhni.

**Official reference:** [Spring Boot 4.1 — SQL Databases](https://docs.spring.io/spring-boot/4.1/reference/data/sql.html)

## Play49 — Understanding Entities in JPA

**Course duration:** 10 minutes · **Course status:** Not completed

JPA entity ek Java class hai jiska state relational table ke saath map hota hai. Is project mein `User` par `@Entity` laga hai, `id` par `@Id` aur `@GeneratedValue(strategy = GenerationType.IDENTITY)` hain. `name` default mapping ke through ek column banta hai. Explicit `@Table`/`@Column` tab add karo jab database names ya constraints ko customize karna ho.

JPA provider ko entity create karne ke liye no-argument constructor chahiye. Entity fields `private` rakh sakte hain; yahan getters/setters application aur JSON binding ke liye maujood hain. Generated ID ko create request se trust nahi karna chahiye: `UserService.addUser` save se pehle ID `null` karta hai, phir database generated ID assign karta hai.

```text
User Java object
    └── @Entity → user table
          ├── id   → primary key, database-generated
          └── name → column
```

**Common mistakes:** `@Entity` ya primary key annotation bhoolna; JPA ke no-argument constructor ko hata dena; generated ID ko application code se assign karna; in-memory H2 ko restart ke baad durable samajhna.

**Recap:** Entity class persistence mapping define karti hai. Is project mein `User` entity, `id` generated key, aur `name` mapped field hai.

## Play50 — Defining JPA Repositories

**Course duration:** 5 minutes · **Course status:** Not completed

`UserRepository` `JpaRepository<User, Long>` extend karti hai. Pehla type entity hai, doosra uska ID type. Spring Data JPA application startup par repository implementation banata hai; basic CRUD ke liye handwritten DAO class ki zaroorat nahi.

```java
public interface UserRepository extends JpaRepository<User, Long> {
}
```

Isse `findAll`, `findById`, aur `save` jaise operations milte hain. Custom finder ki zaroorat par method naming convention ya query annotation use kar sakte hain, par abhi API ko custom query ki zaroorat nahi.

**Recap:** Repository persistence operations ka boundary hai. Service is interface ko inject karti hai, controller repository ko seedha call nahi karta.

## Play51 — Making User Persistent

**Course duration:** 9 minutes · **Course status:** Not completed

`UserService` ab `List<User>` aur manually incremented ID nahi rakhti. Constructor injection se `UserRepository` milta hai; list/read/create/update operations repository par delegate hote hain:

| Service method | Repository operation | Behavior |
|---|---|---|
| `getAllUsers()` | `findAll()` | Saare persisted users |
| `addUser(user)` | `save(user)` | ID clear karke naya user insert |
| `findUserById(id)` | `findById(id)` | `Optional<User>` ke roop mein result |
| `updateUser(id, user)` | `findById`, then `save` | Milne par name update; missing ID par empty result |

Update flow pehle record dhoondhta hai, isliye missing ID par naya record accidentally create nahi hota. `save` updated state ko persist karta hai. Controller ka `Optional` handling same rehta hai aur missing user ke liye 404 bhej sakta hai.

**Execution flow:** HTTP request → `UserController` → `UserService` → `UserRepository` → Hibernate/JPA → H2. Repository API se data storage details service/controller se alag rehti hain.

**Recap:** Service business flow coordinate karti hai; repository database operations karti hai. H2 URL `jdbc:h2:mem:userdb` hai, isliye app/process restart par data reset hota hai.

## Play52 — Testing Changes

**Course duration:** 15 minutes · **Course status:** Not completed

Persistence change ke baad `mvn test` run kiya: **BUILD SUCCESS**, 1 test run, 0 failures, 0 errors. Spring context/JPA initialization ke saath existing MockMvc test ne create, list, get, update aur unknown-ID GET behavior verify kiya. Process restart behavior is test mein cover nahi hota.

### Verification checklist

1. `mvn test` se Spring context startup, entity scanning, repository creation aur H2 integration check karo — is workspace mein yeh pass hua.
2. Existing MockMvc test `POST /users` par `{"name":"Asha"}` bhejkar generated `id` aur name verify karta hai.
3. Test `GET /users` aur `GET /users/1` se saved record read karta hai.
4. Test `PUT /users/1` se name update karke agle GET mein updated value check karta hai.
5. Test unknown ID ke GET par 404 check karta hai. Unknown ID ke PUT par 404 ko alag manual/API check se verify karo.
6. App restart karke verify karo ki in-memory H2 data reset hota hai. Restart persistence chahiye toh file-backed datasource ya durable database configure karna hoga; yeh behavior abhi manually verify nahi hua.

`spring.jpa.hibernate.ddl-auto=update` development learning ke liye hai, production schema migration ka replacement nahi. H2 par successful checks production database compatibility prove nahi karte; target database par alag integration verification chahiye.

### Section recap

Section 4 ka in-memory user API ab JPA repository ke through H2 se users read/write karne ke liye structured hai. `User` entity mapping deti hai, `UserRepository` CRUD boundary hai, aur `UserService` operations coordinate karti hai. `mvn test` mein existing API integration test pass hua. Process restart behavior aur unknown-ID PUT ka manual check pending hai. Course-status table video/course completion track karti hai aur is implementation review se alag hai.
