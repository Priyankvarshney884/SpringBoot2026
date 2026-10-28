# Dependencies in Spring Boot

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Dependencies in Spring Boot

**Course lesson:** Play23. Let’s discuss Dependencies in Spring Boot  
**Course duration:** 7 minutes  
**Course status:** Not completed

## Dependency kya hoti hai?

Dependency woh external library ya module hota hai jiska code aapki application use karti hai. Jaise API ko HTTP requests handle karne ke liye web framework chahiye; us capability ki dependency project mein add ki jaati hai.

Restaurant example mein kitchen ke paas oven aur weighing scale jaise tools hote hain. Har tool ka specific kaam hai. Isi tarah, project mein dependency ek capability provide karti hai; build tool us library ko download karke compile/run classpath mein rakhta hai.

## Spring Boot starter ka role

Spring Boot **starter** related dependencies ka convenient bundle hota hai. REST API ke liye `spring-boot-starter-web` (Boot version ke mutabik available starter) Spring MVC aur embedded server jaise common pieces laata hai. Starter choose karne se compatible libraries ko ek ek karke dhoondhne ki zaroorat kam hoti hai.

Maven dependency ka conceptual example:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Boot-managed project mein aksar version Spring Boot dependency management se aata hai. Current workspace mein Boot configuration nahi hai, isliye yeh snippet abhi ke `pom.xml` mein tested/runnable nahi hai.

## Dependency add hone par kya hota hai?

```text
pom.xml mein dependency declare hoti hai
        ↓
Maven artifact aur uski transitive dependencies resolve karta hai
        ↓
IDE project classpath update karta hai
        ↓
Application un libraries ke APIs use kar sakti hai
```

## Common beginner mistakes

- Duplicate ya unused libraries add karna.
- Starter aur uske andar aane wali transitive dependencies ko same cheez samajhna.
- Boot-managed dependency ka version manually set karke compatibility tod dena.
- Dependency add karne ke baad Maven/Gradle sync na karna.

## Recap

Dependencies application ko external capabilities deti hain. Spring Boot starters common compatible dependency sets add karna aasaan banate hain. Sirf zaroori dependency add karo aur project ke Boot version ke saath compatibility dekho.
