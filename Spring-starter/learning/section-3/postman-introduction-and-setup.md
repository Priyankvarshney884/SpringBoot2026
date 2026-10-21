# Introduction and Setup for Postman

**Section:** Section 3 — Building Your First Spring Boot API  
**Topic:** Introduction and Setup for Postman

**Course lesson:** Play29. Introduction and Setup for POSTMAN  
**Course duration:** Not provided  
**Course status:** Not completed

## Postman kis kaam aata hai?

Postman API requests create aur send karne, response inspect karne, aur requests ko collections mein organize karne wala API client hai. Browser address bar se POST body ya custom headers asaani se bhejna mushkil hota hai; Postman ye kaam visual interface se karne deta hai.

Jaise mobile app ready hone se pehle backend endpoint check karna ho, developer Postman se API call karke dekhta hai ki route, input aur response expected hain ya nahi.

## Pehli request setup karna

1. Postman install ya supported web experience open karo.
2. Nayi HTTP request banao aur method choose karo, jaise `GET`.
3. URL enter karo, jaise `http://localhost:8080/hello`.
4. **Send** dabao.
5. Status code, response body, headers aur response time inspect karo.

POST ke liye method `POST` select karo, **Body → raw → JSON** choose karo, JSON payload enter karo, aur verify karo ki `Content-Type: application/json` set hai.

## Local API test karne ki shartein

`localhost` aapke apne computer ko refer karta hai. Local endpoint tabhi response dega jab application chal rahi ho aur URL/port sahi ho. Agar server start nahi hai to Postman API response nahi bana sakta; connection error dikhayega.

Sensitive tokens ya production data ko shared collections mein save karne se bacho. Team ke saath share karne wali request mein secrets ke liye variables/secret storage use karo.

## Common beginner mistakes

- URL mein galat port ya path dena.
- Request method aur body format endpoint ke contract se mismatch karna.
- Local app start kiye bina `localhost` request bhejna.
- Response body dekhna par status code aur response headers ignore karna.
- API keys ko exported/shared collection mein plain text rakhna.

## Recap

Postman API ko manually exercise aur debug karne ka client hai. Method, URL, headers aur body configure karke request bhejo, phir status, response aur headers ko saath mein samjho.
