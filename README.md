# Silk Road 2.0 CS4B Microservices Project

**Github Users: This repo was originally hosted [as a Gitlab Repo](https://gitlab.com/TypoKign/microservices.git). Visit it there to see CI/CD pipelines, merge request history, and more.**

Final class project of CS4B. The task was to create a RESTful microservices stack and a JavaFX frontend for an online web store.

## Packages

- [frontend/](Frontend) - JavaFX app to interact with the backend (via Retrofit)
- [services/auth-service/](Auth Service) - Java microservice for creating user accounts and logging in
- [services/cart-service/](Cart Service) - Java microservice for storing products in a user's cart
- [services/deposit-service/](Deposit Service) - TypeScript + Node microservice for tracking cryptocurrency deposits to pay for orders
- [services/gateway](Gateway) - Nginx reverse proxy to expose API routes
- [services/order-service](Order Service) - Java microservice for placing and tracking orders
- [services/product-service](Product Service) - Java microservice for retrieving available products and stock

## Backing Services

### RabbitMQ - Event Bus/Message Broker

RabbitMQ serves as our asynchronous event bus for communication between services. No two services should connect directly to each other. Instead, any significant change of state should be announced over RabbitMQ so that 
interested services can update their internal datastores.

### PostgreSQL - Persistent Database

We are operating a single Postgres cluster for all of our persistent data. Data in PostgreSQL is guaranteed to be saved to disk if a pod crashes, unlike Redis. Services are encouraged to maintain their own 
non-authoritative cache of existing data in isolated databases if it interests them.

### Redis - Key-Value Store

Redis can be used for high-performance caching of data that need not be persistent. Examples are the cart service, which stores a cache of users’ carts, where speed is more important than persistence.

## Deploying the Stack

While the assignment did not require it, we went a little overboard with trying to make this project as realistic as possible. Each microservice is Dockerized and contains a Helm chart for deploying to a Kubernetes instance.
We used Gitlab CI to lint, build, and deploy the project to a GKE instance with 2 x n1-standard-4 nodes. The Gitlab CI configuration can be found at [.gitlab-ci.yml](.gitlab-ci.yml). Some of the additional infrastructure can 
be found in the [infra/](infra/) directory.

When a branch is pushed to the repository, it kicks off a Gitlab CI pipeline that:
 - Lints each service via Checkstyle
 - Builds and pushes Docker images for each service to the Gitlab registry
 - Deploys the Helm chart at [chart/](chart/) to an isolated Kubernetes namespace.

We then used [cert-manager](https://github.com/jetstack/cert-manager) to automatically provision an HTTPS certificate and expose the stack at https://branchname.k8s.typokign.com. This made it extremely easy for us to test API
routes for in-flight merge requests, and made it easy to integrate the frontend with new API routes before landing them, simply by changing the base URL that the frontend used. A merge to master deploys the stack to
https://k8s.typokign.com/

If you'd like to deploy the stack for yourself, there are some various Helm charts and Kubernetes secrets in the infra/ folder that need to be deployed before deploying the main chart with `helm install chart/`. If you're
using Gitlab CI, you also need to set two variables in the CI/CD settings:
 - `KUBECONFIG` - contents of your `~/.kube/config` file with cluster address and authentication info
 - `SECRETS` - contents of [infra/secrets.yml](infra/secrets.yml). these are needed so that each new namespace contains necessary secrets
 