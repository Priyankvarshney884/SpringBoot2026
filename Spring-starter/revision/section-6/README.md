# Section 6: Building the Commerce API

Is section mein user API ko address, product, cart aur order features ke saath expand kiya gaya hai. Har lesson ke notes isi document mein hain. Java files one-by-one review hui hain; is document ka review abhi pending hai.

## Section progress

| Lesson | Topic | Duration | Repository implementation |
|---|---|---:|---|
| Play 53 | Completing User Entity | 4 min | Implemented and reviewed |
| Play 54 | Organizing Our Project into Packages | 1 min | Implemented and reviewed |
| Play 55 | Address Entity and User Relationship | 14 min | Implemented and reviewed |
| Play 56 | Introduction to DTOs | 4 min | Implemented and reviewed |
| Play 57 | Migrating to DTOs | 22 min | Implemented and reviewed |
| Play 58 | Setting up Product Entity | 5 min | Implemented and reviewed |
| Play 59 | Creating and Updating Products | 28 min | Implemented and reviewed |
| Play 60 | Getting, Deleting and Searching Products | 25 min | Implemented and reviewed |
| Play 61 | Setting up User Cart | 5 min | Implemented and reviewed |
| Play 62 | Adding a Product to Cart | 25 min | Implemented and reviewed |
| Play 63 | Removing an Item from Cart | 12 min | Implemented and reviewed |
| Play 64 | Fetching Cart | 8 min | Implemented and reviewed |
| Play 65 | Order Entity and Repository | 12 min | Implemented and reviewed |
| Play 66 | Placing an Order | 42 min | Implemented and reviewed |

Course video completion status is not tracked here. The full Section 6 build and tests have not been run after the product, cart, and order additions. A previous `mvn compile` passed after package/DTO work; Section 5's `mvn test` passed with 1 test, 0 failures, and 0 errors before this section's later additions.

## Project layout

The application class remains in the root package so Spring component scanning covers its subpackages.

```text
src/main/java/com/example/Spring_starter/
├── SpringStarterApplication.java
├── Bean/Student.java
├── controller/       # HTTP routes and status codes
├── dto/              # API request/response records
├── mapper/           # Entity and DTO conversion
├── model/            # JPA entities and enums
├── repository/       # Spring Data JPA interfaces
└── service/          # Application and business logic
```

The app uses Java 21, Spring Boot 4.1.1, Spring MVC, Spring Data JPA and in-memory H2. H2 data is reset when the app restarts. Hibernate maintains the development schema with `spring.jpa.hibernate.ddl-auto=update`.

## Play 53 — Completing User Entity

`User` maps to the `users` table; `user` can be treated as a reserved word by SQL databases. The entity has a generated `Long` ID, name, email, phone, a `UserRole`, and an optional address relationship.

`UserRole` is an enum with `CUSTOMER` and `ADMIN`. `@Enumerated(EnumType.STRING)` stores names rather than ordinal numbers, so changing enum order will not silently reinterpret existing rows.

```text
User
├── id: generated primary key
├── name, email, phone
├── role: CUSTOMER | ADMIN
└── address: optional one-to-one association
```

**Common mistake:** Persisting an enum as an ordinal ties existing database values to declaration order. Prefer string values for this learning model.

## Play 54 — Organizing the Project into Packages

The classes are grouped by responsibility: controllers handle HTTP, services coordinate use cases, repositories access persistence, models represent stored data, DTOs define the API contract, and mappers convert between DTOs and entities. `SpringStarterApplication` stays at `com.example.Spring_starter`, which is the common parent package and allows component scanning to discover these subpackages.

**Request flow:**

```text
HTTP request → Controller → Service → Repository → H2
HTTP response ← Controller ← Mapper ← Entity
```

**Common mistake:** Moving the application class below a feature package can narrow component scanning and leave controllers or services undiscovered.

## Play 55 — Address Entity and User Relationship

`Address` is a separate entity with street, city, state, postal code, country, and Hibernate-managed creation/update timestamps. `User` owns the one-to-one association through the `address_id` join column. `CascadeType.ALL` propagates persistence operations; `orphanRemoval = true` removes an address row when it is detached from its user.

The relationship is optional: a request may omit its address. Replacing a user address in the current PUT flow is full replacement; an omitted address becomes `null` and orphan removal applies.

**Common mistake:** Adding `cascade = ALL` without considering deletion behavior can remove related rows unexpectedly. Use it only when the related object's lifecycle belongs to the parent.

## Plays 56–57 — DTOs and Migrating the User API

A Data Transfer Object (DTO) defines the fields accepted or returned by an API. `UserRequestDto` contains name, email, phone, role, and `AddressDto`; `UserResponseDto` adds the generated ID and returns address fields without exposing the JPA entity.

`UserMapper` converts requests into entities and entities into responses. The controller accepts/returns DTOs, while the service and repository work with entities.

```text
Request JSON → UserRequestDto → UserMapper → User → UserService → UserRepository
Response JSON ← UserResponseDto ← UserMapper ← User
```

**Why DTOs:** They keep persistence details separate from the public API and make request/response fields explicit. Add validation annotations to request DTOs when validation rules are introduced.

**Common mistake:** Returning JPA entities directly can expose internal fields or serialize lazy relationships unexpectedly.

## Play 58 — Setting up Product Entity

`Product` maps to `products` and has a generated ID, name, description, `BigDecimal` price, integer stock, category, image URL, active flag, and creation/update timestamps. `ProductRepository` extends `JpaRepository<Product, Long>` for CRUD operations.

`BigDecimal` is used for currency to avoid floating-point rounding. Its database column has precision 10 and scale 2.

## Play 59 — Creating and Updating Products

The API uses `ProductRequestDto` for incoming product fields and `ProductResponseDto` for output, including generated ID and timestamps. `ProductMapper` handles conversions.

| Request | Behavior | Response |
|---|---|---|
| `POST /products` | Creates a product; server/database assigns the ID | `201 Created` with product DTO |
| `PUT /products/{id}` | Replaces the editable fields of an existing product | `200 OK`, or `404 Not Found` |

The service clears any incoming ID before create. Update first looks up the row, so an unknown ID does not accidentally become an insert.

**Common mistake:** Using `save` directly on an incoming entity with a client-supplied ID can blur create and update behavior. The service explicitly separates these paths.

## Play 60 — Getting, Deleting and Searching Products

`ProductRepository.findByNameContainingIgnoreCase` is a Spring Data derived query for partial, case-insensitive name search.

| Request | Behavior |
|---|---|
| `GET /products` | Lists products |
| `GET /products/{id}` | Returns one product or `404` |
| `GET /products/search?name=phone` | Searches product names; returns an empty list if none match |
| `DELETE /products/{id}` | Deletes an existing product (`204`) or returns `404` |

The fixed `/search` route is more specific than the `/{id}` route. The search method name is interpreted by Spring Data; no handwritten SQL is needed for this query.

## Play 61 — Setting up User Cart

`CartItem` represents one product line for one user. It stores a generated ID, `ManyToOne` references to `User` and `Product`, quantity, unit-price snapshot, and timestamps. The database table is `cart_items`.

`CartItemRepository` provides normal CRUD operations, a query for all items belonging to a user, and a query for one user's item for a given product. Its user-cart query uses `@EntityGraph(attributePaths = "product")` so product data is available for response mapping without a later lazy-load.

**Relationship shape:**

```text
User 1 ── * CartItem * ── 1 Product
```

The cart price is captured when the product is first added. A later catalog price change does not rewrite that stored cart price.

## Play 62 — Adding a Product to Cart

`POST /api/cart` reads the user ID from `X-User-Id` and accepts `CartItemRequestDto` with `productId` and `quantity`.

```http
POST /api/cart
X-User-Id: 1
Content-Type: application/json

{"productId": 3, "quantity": 2}
```

The service checks that the quantity is positive, the user and product exist, and the resulting cart quantity does not exceed available stock. If that product is already in the cart, the requested quantity is added; otherwise a new row is created. The operation runs in a transaction. The controller returns a `CartItemResponseDto` with `200 OK`; missing user/product maps to `404`, invalid quantity to `400`, and insufficient stock to `409`.

**Common mistake:** Checking only the newly requested amount against stock ignores quantity already in the cart. The service checks the combined quantity.

## Play 63 — Removing an Item from Cart

`DELETE /api/cart/{productId}` uses `X-User-Id` to scope deletion to that user's cart. A matching item is deleted and returns `204 No Content`; if the user/product pair has no cart row, the endpoint returns `404 Not Found`.

## Play 64 — Fetching Cart

`GET /api/cart` uses the same `X-User-Id` header and returns a list of `CartItemResponseDto` values with product ID/name, quantity, saved unit price, and timestamps. An empty cart returns `200 OK` with `[]`.

The response uses DTOs instead of serializing `CartItem` entities and their JPA relationships directly.

## Play 65 — Order Entity and Repository

`Order` maps to the `orders` table to avoid using SQL's `ORDER` keyword as a table name. It links to a user, stores an `OrderStatus`, total, timestamps, and order items. `OrderItem` maps to `order_items` and links to its order and product, with quantity and a unit-price snapshot.

`OrderStatus` values are `PLACED`, `CONFIRMED`, `SHIPPED`, `DELIVERED`, and `CANCELLED`; the entity stores the enum as a string. `Order.addItem` updates both sides of the association. `cascade = ALL` persists the child items with the order. `OrderRepository` extends `JpaRepository<Order, Long>`.

**Common mistake:** The `Order.items` collection is inverse (`mappedBy = "order"`); the `OrderItem.order` field owns the foreign key. Set both sides, as the `addItem` helper does.

## Play 66 — Placing an Order

`POST /api/orders` takes `X-User-Id` and no request body. In one transaction, the service:

1. Confirms that the user exists and the cart is not empty.
2. Copies each cart line into an `OrderItem`, preserving its unit-price snapshot.
3. Calculates `total` as the sum of `price × quantity` and saves the order with `PLACED` status.
4. Clears the cart only after saving the order; a failure rolls both changes back.

The response is an `OrderResponseDto` with `201 Created`. Unknown user returns `404`; an empty cart returns `400`.

**Current implementation boundary:** Stock is checked when an item is added to the cart, but `placeOrder` does not decrement product inventory. Concurrent checkout/inventory locking is outside this lesson implementation.

## Revision and practice

1. Trace a product create request through DTO, mapper, service, repository, and response.
2. Explain why `BigDecimal` is used for product/order prices and why cart/order lines keep a price snapshot.
3. Compare `@OneToOne`, `@ManyToOne`, and `@OneToMany` in the user/address, cart, and order models.
4. What does `@Transactional` protect during order placement? Which operations must succeed or roll back together?
5. Add validation annotations for product and cart request fields, then verify the API's error responses.
6. Extend checkout to update stock safely, considering two users ordering the last unit at the same time.

### Interview check

**Why use DTOs instead of returning JPA entities?** DTOs define a stable API contract, avoid exposing persistence internals, and prevent accidental serialization of entity relationships.

**Why use an order-item price snapshot?** An order is historical purchase data; later catalog price changes should not alter the amount recorded for an earlier purchase.

**What does a transaction provide during order placement?** It groups order persistence and cart clearing so a runtime failure does not leave only half of that workflow committed. It does not by itself prevent concurrent stock overselling.
