run:
	gradle run

build:
	gradle build

build-docker:
	docker build . -t tictactoeteam/auth-service

lint:
	gradle check

jar:
	gradle jar && cp build/libs/auth-1.0.jar auth-service.jar

.PHONY: run build build-docker lint jar
