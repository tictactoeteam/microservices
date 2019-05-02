build:
	docker-compose build --parallel

up:
	docker-compose up

lint:
	bash ./scripts/lint.sh

.PHONY: build up lint
