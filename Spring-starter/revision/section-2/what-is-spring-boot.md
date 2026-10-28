# What is Spring Boot?

**Section:** Section 2 — Introduction to Spring Boot  
**Topic:** What is Spring Boot?

**Course lesson:** Play18. What is Spring Boot?  
**Course duration:** 11 minutes  
**Course status:** Not completed

## Spring Boot kya hai?

Spring Boot Java applications banane ka framework hai jo Spring Framework par build hota hai. Yeh common configuration ke liye useful defaults deta hai, taaki application ko set up, run aur package karna aasaan ho.

Maan lo team ko inventory service ki REST API banani hai. Spring Boot ke saath team ko har common web component manually configure karne ki zaroorat kam hoti hai; woh jaldi endpoint aur inventory rules par kaam shuru kar sakti hai. Zaroorat pade to defaults ko customize bhi kiya ja sakta hai.

## Spring Boot ke useful features

- **Auto-configuration:** Application mein available libraries aur settings ke basis par common Spring components configure karta hai. Yeh conditional hota hai aur customize ya override kiya ja sakta hai.
- **Starters:** Web ya data access jaise common kaam ke liye related dependencies ko bundle karte hain.
- **Embedded server:** Web application apna server include kar sakti hai, jisse app ko alag se installed server par deploy kiye bina run karna possible hota hai.
- **Operational features:** Actuator include aur configure karne par monitoring aur management endpoints de sakta hai.

## Startup ka aam flow

```text
Application run karo
        ↓
Spring Boot application context set up karta hai
        ↓
Auto-configuration aur application components tayyar hote hain
        ↓
Web application mein embedded server start hota hai
```

Exact setup dependencies aur configuration par depend karta hai. Auto-configuration helpful starting point hai; iska matlab yeh nahi ki kisi application ko kabhi configuration ki zaroorat nahi padegi.

## Spring Boot kab kaam aata hai?

REST APIs, web applications aur services ke liye Spring Boot useful hai jab team convention-based Spring setup ke saath jaldi development shuru karna chahti ho. Application ki specific requirement ho to developers explicit configuration likh sakte hain.

## Common beginner mistakes

- Auto-configuration ka matlab bilkul configuration nahi, aisa samajhna.
- Dependencies add karna bina yeh samjhe ki kaunsa starter ya library unhe la rahi hai.
- Har Spring Boot application ko embedded server ki zaroorat hai, aisa assume karna.
- Spring Boot ko Spring Framework se alag samajhna; Boot, Spring applications banane ka streamlined tareeka deta hai.

## Recap

Spring Boot Spring Framework par build hota hai aur auto-configuration, starters, aur embedded server jaise features se common setup simplify karta hai. Yeh useful defaults deta hai, aur application ko zaroorat ke mutabik configure karne ki flexibility bhi rakhta hai.
