FROM tictactoeteam/base:latest

COPY . .

RUN make jar

CMD ["java", "-jar", "auth-service.jar"]
