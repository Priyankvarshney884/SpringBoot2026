# Spring Boot Architecture

**Section:** Section 2 — Introduction to Spring Boot  
**Topic:** Spring Boot Architecture

**Course lesson:** Play19. Spring Boot Architecture  
**Course duration:** 4 minutes  
**Course status:** Not provided

## Aam layered structure

Spring Boot application ko responsibilities ke hisaab se layers mein organize karna common practice hai:

```text
Client
  ↓ HTTP request
Controller → Service → Repository → Database
  ↑ HTTP response
```

- **Controller (presentation layer):** Web request receive karta hai, input validate/translate karta hai aur response deta hai.
- **Service (application/business layer):** Application ka kaam coordinate karti hai aur business rules apply karti hai.
- **Repository (data-access layer):** Data store aur retrieve karne ke operations sambhalti hai.
- **Database ya data source:** Application ka data persist ya provide karta hai.

Online store ke example mein `GET /products/42` controller tak aati hai. Controller service se product maangta hai; service repository ke through database se product laati hai. Result wapas controller ke paas jaata hai aur client ko response milta hai. Har layer ka clear kaam hone se code samajhna aur badalna aasaan hota hai.

Yeh ek common convention hai, Spring Boot ki strict requirement nahi. Chhoti application kuch layers combine kar sakti hai, aur badi application additional layers rakh sakti hai.

## Spring Boot startup kahan fit hota hai?

Application aam taur par `@SpringBootApplication` annotation wale main method se start hoti hai. Yeh annotation configuration, component scanning aur auto-configuration features ko combine karta hai. Spring application context banata hai, jo configured components manage karta hai aur dependencies inject karta hai. Web application mein embedded server HTTP traffic receive karke Spring web layer ke through controller tak pahunchata hai.

```text
Main application → Application context → Web server → Controller
                                                     ↓
                                            Service → Repository
```

## Layers alag rakhne ka fayda

Jab controller HTTP input/output par focus karta hai aur service business rules par, tab har class ka purpose clear rehta hai. Isse rule change karna aur components ko alag se test karna aasaan ho sakta hai.

## Common beginner mistakes

- Database queries aur business rules dono controller mein rakh dena.
- Assume karna ki har Spring Boot project mein exactly yehi layers honi chahiye.
- Component scanning aur auto-configuration ko same samajhna: scanning application components dhoondhta hai; auto-configuration conditions ke basis par framework components set up karta hai.
- Layer diagram ko mandatory rule samajhna, jabki yeh organization pattern hai.

## Recap

Spring Boot application startup aur configuration ka foundation deta hai. Ek common architecture mein controller request handle karta hai, service business work karti hai, aur repository data access karti hai. Har layer ko focused rakhne se application samajhna aur maintain karna aasaan hota hai.
