# Spring Boot Auto-Configuration

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Understanding Spring Boot Auto-Configuration

**Course lesson:** Play26. Understanding Spring Boot Auto-Configuration  
**Course duration:** 3 minutes  
**Course status:** Not completed

## Auto-configuration kya karti hai?

Spring Boot auto-configuration available dependencies, existing beans aur properties ko dekhkar common application setup configure karne ki koshish karti hai. Isse har common component ko manually define nahi karna padta.

Jaise office meeting room mein projector connected ho to room system usse identify karke use ready kar sakta hai. Isi tarah, classpath par Spring Web available ho to Boot web application ke liye suitable configuration la sakta hai. Yeh conditions par based hota hai, andaza laga kar blindly setup nahi hota.

## High-level flow

```text
@SpringBootApplication
        ↓
Auto-configuration candidates evaluate hote hain
        ↓
Classpath + properties + existing beans check hote hain
        ↓
Matching defaults context mein configure hote hain
```

Auto-configuration aam taur par **back-off** karti hai jab application apna matching bean provide kare. Isse default setup use bhi kar sakte ho aur zaroorat par customize bhi.

## Auto-configuration aur component scanning

`@SpringBootApplication` configuration, auto-configuration aur component scanning ko combine karta hai. Component scanning aapke packages mein `@Component`, `@Service`, `@Controller` jaise application classes dhoondhta hai. Auto-configuration framework ke common beans ko conditions ke basis par set up karti hai. Dono related hain, par same kaam nahi karte.

## Common beginner mistakes

- Auto-configuration ka matlab zero configuration samajhna.
- Assume karna ki har dependency hamesha apni configuration activate karegi; conditions apply hoti hain.
- Apna bean define karne ke baad Boot default bhi same kaam kar raha hoga, aisa assume karna; back-off behavior check karo.
- Problem aane par condition report ya startup logs dekhe bina random configuration add karna.

## Recap

Auto-configuration classpath, properties aur existing beans ke basis par Spring ke common defaults set up karti hai. Yeh developer ka time bachati hai aur custom beans ke through override karne ki flexibility deti hai.
