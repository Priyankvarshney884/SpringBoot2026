# Section 6: Building the Commerce API

Is section mein existing user API ko expand karke address, products, cart aur orders ke concepts implement karenge. Saare lesson notes isi `README.md` mein rahenge. Code files ek-ek karke review hongi; is roadmap ke baad pehla implementation step Play53 mein `User` entity ko course requirements ke mutabik complete karna hai.

## Section roadmap

| Lesson | Topic | Duration | Course status |
|---|---|---:|---|
| Play53 | Completing User Entity | 4 min | Not completed |
| Play54 | Organizing Our Project into Packages | 1 min | Not completed |
| Play55 | Defining Address Entity and Relationship with User | 14 min | Not completed |
| Play56 | Introduction to DTO — Data Transfer Objects | 4 min | Not completed |
| Play57 | Migrating to DTOs | 22 min | Not completed |
| Play58 | Setting up Product Entity | 5 min | Not completed |
| Play59 | Endpoints for Creating and Updating Products | 28 min | Not completed |
| Play60 | Endpoints for Getting, Deleting and Searching Products | 25 min | Not completed |
| Play61 | Setting up User Cart | 5 min | Not completed |
| Play62 | Endpoint to Add User to Cart | 25 min | Not completed |
| Play63 | Endpoint to Remove Item from Cart | 12 min | Not completed |
| Play64 | Endpoint to Fetching Cart | 8 min | Not completed |
| Play65 | Defining Order Entity and Repository | 12 min | Not completed |
| Play66 | Endpoint to Place an Order | 42 min | Not completed |

## Current project baseline

Section 5 ke baad project Java 21, Spring Boot 4.1.1, Spring MVC, Spring Data JPA aur in-memory H2 use karta hai. `User` entity mein abhi generated `id` aur `name` hain; table ka naam `users` hai. `UserRepository` aur repository-backed `UserService` maujood hain. `mvn test` Section 5 ke baad pass hua: 1 test, 0 failures aur 0 errors.

Abhi Java source classes ek hi `com.example.Spring_starter` package mein hain. Play54 package organization ko address karega; usse pehle naye packages/files create nahi karne. Package move ke dauran application class ka component-scan root, imports aur test package ko saath mein review karna hoga.

## Implementation sequence

1. Play53: course/API contract ke mutabik `User` ke required fields aur entity mapping complete karna.
2. Play54: package layout propose karke approved source classes ko organize karna.
3. Play55: `Address` entity aur `User` relationship map karna.
4. Play56–57: DTO ka purpose samajhna aur API request/response ko DTOs par migrate karna.
5. Play58–60: `Product` persistence aur create, update, read, delete, search endpoints.
6. Play61–64: user cart model aur add, remove, fetch operations.
7. Play65–66: order model/repository aur order placement endpoint.

Entity fields, relationship cardinality, validation rules, endpoint paths, request/response shapes aur cart/order behavior ko implementation se pehle available course/project contract se confirm karenge; is roadmap mein unspecified behavior assume nahi kiya gaya.

## Play53 — Completing User Entity

**Course duration:** 4 minutes · **Course status:** Not completed

JPA entity Java object ko relational row se map karti hai. Section 5 ke baad `User` entity mein generated `id` aur `name` hain. Is lesson ka implementation goal user entity ko agle domain lessons ke liye complete karna hai.

Entity mein kaunse additional fields honge, unke types, nullability aur constraints course/API requirements se verify karke decide honge. Agle lesson mein `Address` relationship alag se define hoti hai, isliye us relationship ko bina contract dekhe `User` mein guess karke add nahi karna.

### Current mapping

```text
User Java entity
    ├── id   → generated primary key
    └── name → mapped column
```

Implementation ke baad is lesson mein final field list, mapping annotations, generated-ID behavior, common entity mistakes aur verification result document karenge.
