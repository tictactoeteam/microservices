run:
	gradle run

build:
	gradle build

build-docker:
	docker build . -t registry.gitlab.com/typokign/microservices/cart-service

lint:
	gradle check

jar:
	gradle jar && cp build/libs/cart-1.0.jar cart-service.jar

.PHONY: run build build-docker lint jar
