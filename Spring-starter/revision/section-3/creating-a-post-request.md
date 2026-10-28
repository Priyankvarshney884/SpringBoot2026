# Creating a POST Request

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Creating a POST request

**Course lesson:** Play28. Creating a POST request  
**Course duration:** 3 minutes  
**Course status:** Not completed

## POST request kab use hoti hai?

`POST` client se server ko data bhejne ke liye use hota hai, jaise naya customer ya order create karna. E-commerce app mein checkout ke waqt client order details JSON body ke roop mein bhej sakta hai.

## Request example

```http
POST /api/greetings HTTP/1.1
Content-Type: application/json

{
  "name": "Asha"
}
```

Spring Web controller ka simplified example:

```java
@RestController
@RequestMapping("/api/greetings")
class GreetingController {
    @PostMapping
    String greet(@RequestBody GreetingRequest request) {
        return "Hello, " + request.name();
    }
}

record GreetingRequest(String name) {}
```

`@PostMapping` POST method ko route karta hai. `@RequestBody` JSON body ko Java object mein convert karta hai. Real create endpoint mein server aksar resource save karke `201 Created` aur created resource/location return karta hai. Upar ka snippet request-body mapping samjhane ke liye hai; yeh existing workspace mein configured/runnable nahi kyunki Spring Web dependency abhi nahi hai.

## Request bhejte waqt

API client mein method `POST` select karo, endpoint URL set karo, Body mein raw JSON bhejo, aur `Content-Type: application/json` ensure karo. Response mein status code aur body dono inspect karo.

## Common beginner mistakes

- JSON body bhejna lekin `Content-Type` set na karna.
- `@RequestBody` bhoolna jab JSON ko Java object mein padhna ho.
- `GET` se data create karne ki koshish karna.
- Har POST ko automatically database mein save samajhna; persistence logic alag implement karni padti hai.

## Recap

POST request body mein data bhejti hai. Spring MVC mapping request ko controller se connect karti hai, aur `@RequestBody` JSON ko Java type mein read karne deta hai. Resource create hua ho to response status contract ke mutabik bhejo.
