# Types of API Requests

**Section:** Section 1 — API Fundamentals  
**Topic:** Types of API requests

**Course lesson:** Play15. Types of API requests

## Request type ka kya matlab hai?

Web API mein HTTP method batata hai ki client kisi resource ke saath kya karna chahta hai. URL resource ko identify karta hai; method requested action batata hai. Online store mein products ki list dekhna, naya product add karna, ya existing product update karna alag actions hain.

`/api/products` ke liye common methods:

| Method | Aam purpose | Example |
| --- | --- | --- |
| `GET` | Resource ya collection read karna | `GET /api/products/42` |
| `POST` | Resource create karna ya data process karna | `POST /api/products` |
| `PUT` | Di hui representation se resource replace karna | `PUT /api/products/42` |
| `PATCH` | Resource ka kuch hissa update karna | `PATCH /api/products/42` |
| `DELETE` | Resource remove karna | `DELETE /api/products/42` |

Doosre useful methods:

- **`HEAD`** — `GET` jaisa response maangta hai, par response body nahi maangta.
- **`OPTIONS`** — Poochta hai ki endpoint kaun se communication options support karta hai. Browser ise kuch cross-origin (CORS) checks mein use karta hai.

## Example: product banana aur phir padhna

Client `POST` se naya product bhej sakta hai:

```http
POST /api/products HTTP/1.1
Host: shop.example.com
Content-Type: application/json

{
  "name": "Pencil",
  "price": 1.25
}
```

Server `201 Created` aur naye product ki details, jaise uska ID, return kar sakta hai. Uske baad client `GET /api/products/43` se wahi product read karega. Method action batata hai; status code batata hai ki action ka result kya raha.

## PUT aur PATCH mein farq

`PUT` ka usual matlab hai resource ko di hui representation se replace karna. `PATCH` existing resource par selected changes apply karta hai. Har API ki exact behavior uske contract par depend karti hai, isliye documentation check karo.

## Safe aur idempotent requests

- **Safe method** ka intended purpose server state change kiye bina information read karna hota hai. `GET` iska common example hai.
- **Idempotent method** ko same request ke saath repeat karne par intended final effect same rehta hai. `GET`, `PUT`, aur `DELETE` generally idempotent hote hain; `POST` guaranteed idempotent nahi hota.

Yeh properties retries mein useful hoti hain. Maan lo order create karte waqt network timeout ho gaya. Client ko pata nahi chalta ki order bana ya nahi. `POST` repeat karne se duplicate order ban sakta hai, jab tak API idempotency key jaisa mechanism na de.

## Common beginner mistakes

- Data badalne ke liye `GET` use karna.
- Method ko endpoint samajhna: same `/api/products` par `GET` list padh sakta hai, jabki `POST` naya item bana sakta hai.
- `PUT` aur `PATCH` ko interchangeable samajhna.
- Assume karna ki method akela poora result define karta hai; API contract aur status code bhi dekho.

## Recap

HTTP method intended action batata hai—read, create, replace, partial update ya delete. Sahi method ko resource URL ke saath use karo aur API ke documented behavior ko follow karo.
