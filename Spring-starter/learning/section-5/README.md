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

Section 5 ke start par project Spring Boot 4.1.1 aur Java 21 par tha, aur `UserService` in-memory `List` use karta tha. Play48 ke approved setup ke baad `pom.xml` mein JPA/H2 dependencies aur `application.properties` mein in-memory datasource/JPA settings maujood hain. Lekin `User` abhi entity nahi aur service in-memory list hi use karti hai, isliye actual user records abhi database mein persist nahi hote.

### Is section ka implementation flow

1. JPA/H2 dependency versions aur compatibility verify karna.
2. Approved JPA/H2 dependencies ko `pom.xml` mein add karna.
3. H2 connection aur JPA settings configure karna.
4. Existing `User` class ko JPA entity banana, table identity aur generated ID samajhna.
5. `JpaRepository` interface define karna.
6. `UserService` ko in-memory list se repository-backed operations mein badalna.
7. API aur persistence behavior test karna, including create/read/update aur restart/configuration expectations.

Is flow ke pehle teen setup steps approve hokar complete hain; entity mapping se aage ke steps pending hain. Har coding/configuration file ek-ek karke review aur `PASS` ke baad hi aage badhegi. Existing Java version aur Spring Boot version bina clear reason aur approval ke change nahi honge.

### Recap

JPA relational database persistence ke liye Java standard hai. Hibernate provider ke roop mein JPA implement karta hai, jabki Spring Data JPA repository code ko simplify karta hai. Hamara next learning goal in-memory user list ko approved JPA/H2 setup ke through persistent storage se replace karna hai.

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

Section 4 mein `UserService` seedha in-memory `List<User>` use karta tha. JPA setup ke baad repository database operations ka boundary banegi. Service user operations ko coordinate karegi, aur controller HTTP layer tak limited rahega. Har chhote example mein extra layer add karna zaroori nahi, lekin repository ko service/controller responsibilities se alag rakhne se data access ko baad mein replace ya test karna aasaan ho sakta hai.

### JPA repository ka role

Spring Data JPA repository aam taur par entity type aur ID type ke saath declare hoti hai. Spring Data common CRUD operations ka implementation provide kar sakta hai, isliye basic reads/writes ke liye har baar DAO implementation manually likhne ki zaroorat nahi hoti. Custom queries ki zaroorat ho toh woh alag define ki ja sakti hain.

### Trade-off aur boundaries

- Repository persistence details ko encapsulate karti hai, lekin service ko business decisions ka owner rehna chahiye.
- Controller se repository ko directly call karna chhote demo mein possible hai, par layers mix ho jaati hain aur business logic test/maintain karna mushkil ho sakta hai.
- DAL abstraction database ko automatically replaceable nahi bana deti—database-specific SQL, transaction behavior aur schema differences phir bhi matter karte hain.

### Recap

DAL data storage operations ko application ke baaki parts se separate karti hai. Is User API ka target flow Controller → Service → Repository → JPA provider → H2 hai. H2 dependency/configuration maujood hai, lekin repository aur entity-based persistence abhi implement nahi hui.

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

Play48 ke approved setup ke baad H2 driver `pom.xml` mein aur datasource URL `application.properties` mein configured hain. `User` entity aur repository abhi add nahi hue, isliye user records abhi H2 mein store nahi ho rahe; woh next implementation lessons ka kaam hai.

### Common confusion

- H2 ko JPA ya Hibernate samajhna: H2 database hai, JPA persistence standard hai, Hibernate provider hai.
- In-memory H2 ko durable database samajhna: app/process band hone par data aam taur par reset hota hai.
- Embedded database par pass hue tests ko production DB compatibility ka proof maan lena.

### Recap

H2 ek lightweight relational database hai jo local learning aur tests mein convenient hai. Database mode aur JDBC URL decide karte hain ki data memory-only hai ya file-backed. Is project mein Play48 se `jdbc:h2:mem:userdb` configure hai; actual entity persistence next lessons mein implement aur verify hogi.

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

**Current caveat:** Configuration add ho gayi hai, lekin `User` class abhi JPA `@Entity` nahi bani aur repository abhi define nahi hui. Isliye user table/entity persistence abhi complete nahi. Agla step Play49 ke liye `User.java` ko entity mapping mein badalna hoga, approval ke baad.

### Configuration verify karna

Entity aur repository present hone ke baad application/test context start karke datasource/JPA initialization logs check karenge. Sirf properties file hona persistence verify nahi karta. H2 in-memory hone ke kaaran process restart ke across records rehne ki expectation nahi rakhni.

**Official reference:** [Spring Boot 4.1 — SQL Databases](https://docs.spring.io/spring-boot/4.1/reference/data/sql.html)

## Remaining lesson modules

Play49 se Play52 tak ke notes isi document mein un lessons par kaam karte waqt add honge. Entity fields, repository behavior aur persistence-test expectations ko project/course details se verify karenge; unverified behavior assume nahi karenge.
