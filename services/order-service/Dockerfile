FROM registry.gitlab.com/typokign/microservices/base:latest

COPY . .

RUN make jar

CMD ["java", "-jar", "order-service.jar"]
