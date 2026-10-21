# Introduction to Spring Framework

**Section:** Section 2 — Introduction to Spring Boot  
**Topic:** Introduction to Spring Framework

**Course lesson:** Play17. Introduction to Spring Framework  
**Course duration:** 11 minutes  
**Course status:** Not completed

## Spring Framework kya hai?

Spring Framework Java applications banane ke liye tools aur programming model deta hai. Yeh application ke components ko organize karne aur unke beech collaboration manage karne mein help karta hai. Iska use web apps ke saath doosri tarah ki Java applications mein bhi hota hai.

Real-world example ke liye online store dekho. Product page ko product details chahiye hoti hain; uske liye controller service se help leta hai, aur service repository se data mangti hai. In objects ko manually baar-baar banana aur connect karna mushkil ho sakta hai. Spring yeh object management aur wiring sambhal sakta hai.

## Inversion of Control aur Dependency Injection

**Inversion of Control (IoC)** ka matlab hai ki selected objects ko create aur manage karne ka control application code se Spring container ko de diya jaata hai. Isse har class ko apne collaborators khud banane ki zaroorat nahi hoti.

**Dependency Injection (DI)** woh tareeka hai jisse container ek object ko uski required dependencies provide karta hai. `ProductController` ko `ProductService` ki zaroorat ho, to controller service ko `new` karke banane ke bajay Spring se receive kar sakta hai.

```text
Spring container → ProductService banata hai
                 → ProductController ko service inject karta hai
                 → Request aane par controller service se kaam leta hai
```

Spring container ke manage kiye object ko aam taur par **bean** kehte hain. Configuration ya annotations Spring ko batate hain ki kaun se components manage karne hain aur unka relation kya hai.

## Application ka aam flow

```text
Web request → Controller → Service → Repository → Data source
```

Controller web request handle karta hai, service application ya business logic rakhti hai, aur repository data access sambhalti hai. Spring in components ko create karke dependencies wire kar sakta hai. Yeh common design pattern hai, har application ke liye fixed rule nahi.

## Spring Framework aur Spring Boot

Spring Framework core programming model aur capabilities deta hai. Spring Boot, Spring ke upar build hota hai aur auto-configuration, starter dependencies, aur application run karne ke convenient options se common setup ko tez karta hai. Boot Spring ko use karta hai; uski jagah nahi leta.

## Common beginner mistakes

- Dependency aur dependency injection ko same samajhna: dependency required collaborator hai; DI us collaborator ko provide karne ka tareeka hai.
- Assume karna ki har Java object Spring bean hai; container sirf registered/configured objects manage karta hai.
- Saari business logic controller mein rakh dena.
- Spring Framework aur Spring Boot ko competing alternatives samajhna.

## Recap

Spring Framework Java application components ko organize aur connect karne mein help karta hai. IoC object management Spring ko deta hai, aur DI dependencies provide karta hai. Spring Boot common Spring setup ko simple banata hai.
