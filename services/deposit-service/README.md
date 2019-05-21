# Deposit Service

CODEOWNER: David

The deposit service is responsible for generating cryptocurrency receive addresses on the omnibus wallets used by the project, and tracking transactions received at these addresses. 

Upon discovering a pending transaction, the transaction service will broadcast the address and amount of the transaction to Rabbit. When the transaction reaches a minimum number of confirmations, 
the transaction will be broadcasted to a different queue. At this time the transaction should be considered confirmed, and the associated order may be processed. The number of confirmations required for 
considering a deposit confirmed varies by coin, and is listed below:

|Coin|Number of Confirmations|
|:---|----------------------:|
|BTC |2|
|LTC |8|
|XLM |120|
|ZEC |8|

## Routes

### POST /api/v1/deposit-webhook
Not for customer use. This route is called by BitGo when a deposit is received at one of the omnibus wallets.
Of course we can't trust that users won't try to cheat the system by sending a fake webhook, so we call the BitGo API to verify that each webhook is legitimate.

**Body**
```json
{
  "type": "transfer";
  "wallet": string;
  "hash": string;
  "transfer": string;
  "coin": string;
}
```
