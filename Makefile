build:
	docker-compose build --parallel

up:
	docker-compose up

lint:
	sh ./scripts/lint.sh

.PHONY: build up lint
