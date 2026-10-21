# Designing our First Hello World API

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Designing our First Hello World API

**Course lesson:** Play24. Designing our First Hello World API  
**Course duration:** 5 minutes  
**Course status:** Not completed

## Hello World API kya karegi?

Pehli API ka goal chhota rakho: client ek HTTP request bheje aur server ek simple greeting return kare. Jaise health check ya welcome message ke liye mobile app backend se response maang sakti hai.

Pehle contract decide karo: endpoint `GET /hello` hoga aur response `Hello, Spring Boot!` text hoga. Method read-only request ke liye `GET` hai; path is operation ka address hai.

## Spring MVC controller ka example

Yeh illustrative snippet Spring Web dependency wale Spring Boot project mein use hota hai:

```java
@RestController
class HelloController {
    @GetMapping("/hello")
    String hello() {
        return "Hello, Spring Boot!";
    }
}
```

`@RestController` class ko web requests handle karne wala component batata hai. `@GetMapping` `GET /hello` ko `hello()` method se map karta hai. Method ka returned text HTTP response body ban jaata hai.

## Request kaise bhejein?

Application start hone ke baad browser, curl ya API client se yeh request bhejo:

```http
GET http://localhost:8080/hello
```

Agar route sahi register hua aur server chal raha hai, response body `Hello, Spring Boot!` hogi. Current workspace mein Spring Web dependency nahi hai, isliye snippet existing project mein bina setup ke run nahi hoga.

## Common beginner mistakes

- `@RestController` bhool jana, jisse Spring method ko web endpoint ki tarah register na kare.
- URL ya port galat use karna.
- Application start hone se pehle request bhejna.
- Expect karna ki Java class akeli API banati hai; web dependency aur Spring application startup bhi chahiye.

## Recap

Ek chhoti API design karte waqt method, path aur response pehle define karo. Spring Web controller annotations request ko Java method se jodte hain, aur method ka result client ko response mein milta hai.
