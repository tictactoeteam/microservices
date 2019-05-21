# Auth Service

CODEOWNER: David

The Authentication Service (auth-service) is in charge of validating user credentials and providing a session token for future requests

## Routes

### POST /api/v1/users

Create a user with the specified username, email and password

**Body**
```json
{
  "username": string;
  "email": string;
  "password": string;
}
```

### POST /api/v1/session
**Body**
```json
{
  "username"?: string; // one of username, email must be provided
  "email"?: string; // one of username, email must be provided
  "password": string;
}
```

Log in and provision a JWT for future authenticated calls

## JSON Web Tokens

The auth-service provisions JWTs upon authenticating a user. JWTs are cryptographically signed certifications that a user is authenticated. The token contains the user’s ID, an expiration time, and a cryptographic signature proving it was authorized by the auth-service. Other services can verify that a JWT is valid by validating the expiration time and cryptographic signature. The authentication service's public key will be provided to each API service.

**IMPORTANT:** JWTs are by design irrevocable. For this reason, the expiration time shall be no longer than one hour. Clients will have to re-authenticate if their session lasts longer than one hour.