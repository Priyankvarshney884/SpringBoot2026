# Introduction to application.properties

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Introduction to application.properties

**Course lesson:** Play27. Introduction to application.properties  
**Course duration:** 5 minutes  
**Course status:** Not completed

## `application.properties` kya hai?

`application.properties` Spring Boot application ki configuration values rakhne wali file hai. Isse port, application name aur feature settings jaise values Java code se alag rakhi ja sakti hain.

Maan lo ek service local machine par `8080` port use karti hai, lekin deployment environment mein alag port dena hai. Configuration file ya environment variable se value change karke same code alag environments mein chal sakta hai.

Default location:

```text
src/main/resources/application.properties
```

## Example

```properties
spring.application.name=hello-api
server.port=8081
```

Is example mein Boot application ka naam `hello-api` rakhta hai aur web server ko port `8081` par start karne ko kehta hai. Ab client URL `http://localhost:8081/hello` use karega.

Configuration sensitive ya deployment-specific ho to environment variables/external configuration use karo. Passwords aur API keys ko source control mein commit mat karo.

## Properties kaise apply hoti hain?

Spring Boot startup par configuration sources read karta hai. `application.properties` common source hai, aur environment variables/command-line arguments jaise sources values override kar sakte hain. Kaunsi value jeetegi woh property-source precedence par depend karta hai.

## Common beginner mistakes

- File ko `src/main/java` mein rakhna; aam location `src/main/resources` hai.
- Property key mein typo karna.
- Port change karke bhi purane URL par request bhejna.
- Secrets ko repository mein rakh dena.
- Assume karna ki har custom property apne aap kisi Java field se bind ho jaati hai; use bind/read karne ka mechanism chahiye.

## Recap

`application.properties` app configuration ko code se alag rakhti hai. Default location `src/main/resources` hai; environment-specific values aur secrets ko carefully manage karo.
