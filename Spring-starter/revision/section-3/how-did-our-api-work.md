# How did our API work?

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** How did our API work?

**Course lesson:** Play25. How did our API work?  
**Course duration:** 4 minutes  
**Course status:** Not completed

## Request se response tak

Hello endpoint call karte waqt client HTTP request bhejta hai. Embedded server request receive karta hai, Spring ka web layer route dhoondhta hai, controller method chalti hai aur uska result HTTP response ban kar client ko wapas jaata hai.

```text
Client
  → HTTP GET /hello
  → Embedded server
  → Spring DispatcherServlet / request mapping
  → HelloController method
  ← HTTP status + response body
```

Restaurant order example mein customer ka order counter tak aata hai; counter use sahi kitchen station ko bhejta hai; tayyar item customer ko wapas milta hai. Web framework ka routing layer request ko sahi handler tak pahunchane ka kaam karta hai.

## Spring ke parts ka role

- **Embedded server:** Network par HTTP request accept karta hai, jaise localhost par port `8080`.
- **DispatcherServlet:** Spring MVC mein incoming requests ke central front controller ki tarah kaam karta hai.
- **Request mapping:** `@GetMapping("/hello")` jaise mapping se path aur method ke basis par handler select hota hai.
- **Controller:** Endpoint ka application-specific behavior chalata hai.
- **HTTP response:** Status code, headers aur response body client tak pahunchte hain.

Simple string response ko Spring HTTP response body mein convert karta hai. Object return karne par configured message converter aam taur par JSON serialization karta hai.

## Debug karne ka seedha tareeka

Request fail ho to pehle app ke startup logs aur port dekho; phir HTTP method/path check karo; uske baad controller mapping aur response status inspect karo. Isse issue ko network, routing ya application logic ke area mein narrow down kar sakte ho.

## Common beginner mistakes

- Sirf controller method ko poora request flow samajhna; server aur Spring routing bhi beech mein hote hain.
- `localhost` ko remote client ka address samajhna; yeh same machine ko refer karta hai.
- Response body aur status code ko same cheez samajhna.
- Har returned Java object automatically JSON hota hai, aisa assume karna; converters/configuration matter karte hain.

## Recap

HTTP request server tak aati hai, Spring mapping matching controller method select karti hai, aur result HTTP response ke roop mein client ko milta hai. Is flow ko jaanne se API issues debug karna aasaan hota hai.
