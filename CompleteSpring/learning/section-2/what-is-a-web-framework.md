# What is a Web Framework?

**Section:** Section 2 — Introduction to Spring Boot  
**Topic:** What is a Web Framework?

**Course duration:** 8 minutes  
**Course status:** Not completed

## Framework kya hota hai?

Framework reusable foundation hota hai jo software banane ke liye structure aur common tools deta hai. Web framework HTTP ke through baat karne wali applications—jaise website ya REST API—banane mein help karta hai.

Socho har restaurant ko order lene ke liye apna payment network, receipt format aur delivery tracking system zero se banana pade. Kaafi effort repeat hoga. Framework common infrastructure ready deta hai; developers apne app ke specific rules aur features add karte hain.

## Web framework kin kaamon mein help karta hai?

Framework ke hisaab se yeh facilities mil sakti hain:

- **Request routing:** URL aur HTTP method ko sahi handler tak pahunchana.
- **Input handling:** Path values, query parameters, headers aur request body read karna.
- **Response creation:** HTML ya JSON ke saath sahi HTTP status code bhejna.
- **Middleware ya filters:** Logging aur authentication jaisa shared logic request ke aas-paas chalana.
- **Integrations:** Database, templates aur validation tools ke saath connect karna.

## Request ka example

Maan lo shopping app `GET /products/42` request bhejti hai. Web framework URL aur method ko match karke request sahi handler tak pahunchata hai. Handler product details laata hai, aur framework response client ko bhejne mein madad karta hai. Framework request-response ka common setup sambhalta hai; product ka business behavior application define karti hai.

```text
Client → HTTP request → Framework routing → Application handler
Client ← HTTP response ← Framework response handling ← Handler result
```

## Framework aur library mein farq

Library aam taur par ek capability deti hai jise application apni zaroorat par call karti hai. Framework aksar application ka overall flow set karta hai aur specific waqt par application code call karta hai—jaise matching request aane par. Real projects mein frameworks aur libraries dono saath use hote hain.

## Common beginner mistakes

- Sochna ki framework poori application bana deta hai; application ke rules developer ko implement karne hote hain.
- Har framework ko same samajhna; features aur conventions alag ho sakte hain.
- Request ka flow samjhe bina framework use karna.

## Recap

Web framework HTTP request aur response handle karne ke liye reusable structure deta hai. Yeh repetitive infrastructure ko sambhalta hai, taaki developer application ke actual behavior par focus kar sake.
