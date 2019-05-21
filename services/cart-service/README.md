# Cart Service

CODEOWNER: David

The Cart Service will store products that users have added to their cart in Redis, a high-performance cache.

## Routes

### GET /api/v1/cart
Returns the cart belonging to the currently authenticated user

### PUT /api/v1/cart
Clears the user's cart then replaces it with the new cart specified in the request body

**Body**
```json
{
  "cart": [
    {
      "product": string, // UUID
      "quantity": number
    },
    { ... }
  ]
}

```

### DELETE /api/v1/cart
Clears the user's cart