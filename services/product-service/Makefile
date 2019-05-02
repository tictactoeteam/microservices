run:
	gradle run

build:
	gradle build

build-docker:
	docker build . -t tictactoeteam/product-service

lint:
	gradle check

jar:
	gradle jar && cp build/libs/product-1.0.jar product-service.jar

.PHONY: run build build-docker lint jar
