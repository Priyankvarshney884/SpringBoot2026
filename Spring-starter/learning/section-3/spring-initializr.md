# Spring Initializr

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Spring Initializr

**Course lesson:** Play21. Spring Initializr  
**Course duration:** 8 minutes  
**Course status:** Not completed

## Spring Initializr kya karta hai?

Spring Initializr ek project generator hai. Aap project metadata, Java version, Spring Boot version aur required dependencies select karte ho; Initializr ek ready-to-open project structure aur build file generate karta hai.

Isse ghar banane ke liye approved layout aur material list milne jaisa samjho. Initializr final application nahi banata; woh basic structure tayyar karta hai, jise aap apne use case ke code se complete karte ho.

## Project generate karte waqt kya select karein?

- **Project:** Maven ya Gradle, jo team/course use karta ho.
- **Language:** Java, Kotlin ya Groovy; is course ke liye Java.
- **Spring Boot version:** Course/project ke version ke saath compatible stable version.
- **Group aur Artifact:** Package identity aur project name, jaise `com.example` aur `hello-api`.
- **Packaging aur Java:** App ki deployment aur JDK requirement ke mutabik.
- **Dependencies:** Sirf required features, jaise REST endpoint ke liye Spring Web.

## Typical flow

```text
start.spring.io kholo
        ↓
Project details aur required dependencies select karo
        ↓
Generate karke ZIP download karo
        ↓
Extract karo, IntelliJ mein open karo, Maven/Gradle sync hone do
```

Example: basic REST API ke liye Java + Maven + Spring Web select karoge. Isse build file mein web dependency aur app ka starter structure aayega. Baad mein aap controller class likhoge.

## Common beginner mistakes

- Har available dependency select kar lena; isse project unnecessarily complex ho sakta hai.
- Java version ko installed JDK se mismatch karna.
- Course ke Boot version aur generated project version mein farq ko notice na karna.
- ZIP extract karne ke bajay compressed archive ko hi project samajhna.

## Recap

Spring Initializr project ka starting structure aur build configuration generate karta hai. Sahi metadata aur minimum required dependencies select karo, phir generated project ko IDE mein kholkar samjho.
