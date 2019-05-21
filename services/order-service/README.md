# Order Service

CODEOWNER: Tomasz

The order service allows users to place orders by providing their cart and coin of choice. It then calculates the total price and returns it
along with a cryptocurrency address to send funds to.

## Routes

### POST /api/v1/orders
Place a new order and return the price and receive address

**Body**
```json
{
  "cart": [
    {
      "product": string; // UUID
      "quantity": number;
    },
    { ... }
  ],
  "coin": "tbtc" | "tltc" | "tzec" | "txlm";
}
```