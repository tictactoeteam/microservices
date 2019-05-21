# Gateway

CODEOWNER: David

The gateway is an nginx-powered reverse proxy that routes API requests to their respective microservice. 
It is the only service with a Kubernetes Ingress, so all API routes must be registered here and routed to the correct service.