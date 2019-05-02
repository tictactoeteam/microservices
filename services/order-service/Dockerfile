FROM tictactoeteam/base:latest

COPY . .

RUN make jar

CMD ["java", "-jar", "order-service.jar"]
