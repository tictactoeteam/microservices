run:
	gradle run

build:
	gradle build

build-docker:
	docker build . -t registry.gitlab.com/typokign/microservices/order-service

lint:
	gradle check

jar:
	gradle jar && cp build/libs/order-1.0.jar order-service.jar

.PHONY: run build build-docker lint jar
