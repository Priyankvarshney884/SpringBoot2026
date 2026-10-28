# Status Codes in an API

**Section:** Section 1 — API Fundamentals  
**Topic:** Status Codes in API

**Course lesson:** Play14. Status Codes in API  
**Course duration:** 8 minutes  
**Course status:** Not completed

## HTTP status code kya batata hai?

HTTP response mein status code ek three-digit number hota hai jo client ko request ka broad result batata hai. Jaise delivery app order create karne ke baad code se samajh sakta hai ki order bana, request galat thi, ya server mein issue aaya. Response body extra detail de sakti hai.

## Status code ke paanch groups

| Range | Matlab | Aam situation |
| --- | --- | --- |
| `1xx` | Informational | Request process ho rahi hai. |
| `2xx` | Success | Request successfully handle hui. |
| `3xx` | Redirection | Client ko doosri location follow karni ho sakti hai. |
| `4xx` | Client error | Request ya client ki permission ki wajah se kaam nahi hua. |
| `5xx` | Server error | Server request handle karte waqt fail hua. |

## Kaam ke status codes

- **`200 OK`** — Request successful; data read karne mein common.
- **`201 Created`** — Naya resource successfully create hua.
- **`204 No Content`** — Kaam successful hua, response body nahi hai.
- **`301 Moved Permanently` / `302 Found`** — Resource doosri location par redirect ho raha hai.
- **`400 Bad Request`** — Request malformed hai ya input valid nahi hai.
- **`401 Unauthorized`** — Authentication missing ya invalid hai.
- **`403 Forbidden`** — Server ne client ko pehchana, par access allow nahi hai.
- **`404 Not Found`** — Maanga gaya resource nahi mila.
- **`409 Conflict`** — Request current resource state se conflict karti hai.
- **`500 Internal Server Error`** — Server par unexpected failure hui.
- **`503 Service Unavailable`** — Server filhaal request handle nahi kar pa raha.

## Example: store mein product create karna

```http
HTTP/1.1 201 Created
Content-Type: application/json
Location: /api/products/43

{
  "id": 43,
  "name": "Pencil"
}
```

`201 Created` batata hai ki product ban gaya. `Location` header client ko naye product ka address deta hai. Agar client kisi aise product ko maange jo exist nahi karta, API `404 Not Found` bhej sakti hai. Error body mein user ko samajhne layak message ho sakta hai, lekin internal sensitive details expose nahi karni chahiye.

## Common beginner mistakes

- Har result ke liye `200` bhejna, chahe request fail hui ho.
- `401` aur `403` ko same samajhna: `401` mein valid authentication nahi hai; `403` mein access mana hai.
- `204 No Content` ke saath response body bhejna.
- Sochna ki status code akela hi poori explanation deta hai; zaroorat par documented error body bhi padho.

## Recap

Status code ka pehla digit result ka group batata hai: information, success, redirect, client error ya server error. Sahi code choose karne se client request ka agla step decide kar sakta hai.
