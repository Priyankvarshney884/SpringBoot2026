# Basics of an API

**Section:** Section 1 — API Fundamentals  
**Topic:** Basics of an API

**Course duration:** 10 minutes  
**Course status:** Not completed

## API kya hota hai?

API (Application Programming Interface) ek defined tareeka hai jisse do software systems ek doosre se baat karte hain. Web API mein yeh communication usually HTTP request aur response ke through hota hai, aur data aksar JSON format mein bheja jaata hai.

Real world mein socho ki aap food delivery app se restaurant ka menu dekhte ho. App ko restaurant ke database ya kitchen ka internal setup jaanne ki zaroorat nahi hoti. App API se menu maangta hai; API ek agreed format mein response deta hai. Yeh agreed interface hi integration ko possible banata hai.

## Web API exchange ke main parts

- **Client:** Request bhejne wala software, jaise mobile app ya browser.
- **Server:** Request receive karke response dene wala software.
- **Endpoint:** URL jo resource ya operation ko identify karta hai.
- **HTTP method:** Batata hai client kya action chahta hai, jaise read karna.
- **Headers:** Request/response ke baare mein extra metadata, jaise JSON format.
- **Body:** Optional data jo request mein bheja ya response mein diya jaata hai.
- **Status code:** Short numeric signal jo request ka result batata hai.

## Example: product details maangna

```http
GET /api/products/42 HTTP/1.1
Host: shop.example.com
Accept: application/json
```

Server is tarah response de sakta hai:

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "id": 42,
  "name": "Notebook",
  "price": 4.5
}
```

Is exchange mein client `GET` se data read karna chahta hai. `/api/products/42` product ko identify karta hai. `Accept` batata hai ki client JSON samajh sakta hai, aur response body mein product details aati hain. `200` batata hai ki request successfully handle hui.

## API kyun useful hai?

API systems ke beech ek stable contract banata hai. Mobile app aur website dono same server API use kar sakte hain, chahe server andar se data kaise store karta ho. Server ki internal implementation badal sakti hai, lekin API contract compatible rahe to clients ko har baar badalna nahi padta.

## Common beginner mistakes

- API ko sirf URL samajhna; method, headers, body aur response bhi exchange ka hissa hain.
- Client aur server ke roles ulte samajhna.
- Har successful request ke liye same status code ya response body expect karna.
- `Content-Type` aur `Accept` headers ko ignore karna jab format important ho.

## Recap

Web API client aur server ke beech request-response contract define karta hai. Kisi API call ko samajhne ke liye endpoint, method, bheja gaya data, status code aur response data dekho.
