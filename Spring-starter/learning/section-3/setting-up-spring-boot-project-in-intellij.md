# Setting up and understanding the Spring Boot project in IntelliJ

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Setting up and understanding the Spring Boot project in IntelliJ

**Course lesson:** Play22. Setting up and understanding the Spring Boot Project in IntelliJ  
**Course duration:** 9 minutes  
**Course status:** Not completed

## Project IntelliJ mein kaise open karein?

Spring Initializr se downloaded ZIP ko pehle extract karo. IntelliJ IDEA mein extracted project folder open karo. IDE `pom.xml` ya Gradle build file detect karke dependencies sync karega; sync complete hone do aur project ke JDK ko configured Java version se match karo.

Yeh kisi naye office mein pehle floor plan aur departments samajhne jaisa hai. Code likhne se pehle dekho ki startup class, source files aur build configuration kahan hain.

## Spring Boot project ke common parts

```text
hello-api/
├── pom.xml
└── src/
    ├── main/java/.../Application.java
    └── test/java/.../ApplicationTests.java
```

- **`pom.xml`** Maven dependencies, Java version aur build plugins describe karta hai.
- **`src/main/java`** application ka Java source code rakhta hai.
- **`src/main/resources`** configuration aur doosre runtime resources ke liye hota hai.
- **`src/test/java`** tests rakhta hai.
- **Main application class** mein `@SpringBootApplication` aur `main` method hota hai; yahin se app start hoti hai.

IDE ki Maven/Gradle window se project sync ya build status dekha ja sakta hai. Main class ke Run action se app start karo. Web app ke liye console mein server ke start hone ka log check karo.

## Current workspace se relation

Is workspace ka `pom.xml` filhaal simple Maven Java setup hai; ismein Spring Boot parent, dependencies ya standard `src/main/resources` configuration file abhi nahi hai. `Main.java` plain Java program hai. Isliye ise abhi Spring Boot project kehna sahi nahi hoga; Initializr-generated project ka layout upar example ke roop mein dikhaya gaya hai.

## Common beginner mistakes

- Sirf ek `.java` file open karke project root/build file miss kar dena.
- Maven/Gradle sync complete hone se pehle errors ko application error samajhna.
- IntelliJ project JDK aur Maven compiler Java version alag rakhna.
- `@SpringBootApplication` ko har class par lagana; yeh aam taur par main application class par hota hai.

## Recap

IntelliJ mein extracted project root open karo, build file sync hone do aur JDK verify karo. `pom.xml`, source/resources folders aur main application class ko pehchanne se project ka flow samajhna aasaan hota hai.
