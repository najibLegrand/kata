APP_NAME ?= delivery-scheduler
REGISTRY ?= ghcr.io/your-org
TAG ?= dev
IMAGE := $(REGISTRY)/$(APP_NAME):$(TAG)

.DEFAULT_GOAL := help

## build: Build the app jar (skip tests)
build:
	mvn -q -DskipTests package

## test: Run unit + integration tests
test:
	mvn -q verify

## run: Run locally with dev profile
run:
	SPRING_PROFILES_ACTIVE=dev mvn spring-boot:run

## docker-build: Build Docker image
docker-build:
	docker build -t $(APP_NAME):$(TAG) .

## docker-run: Run Docker container locally (port 8080)
docker-run:
	docker run --rm -p 8080:8080 --env-file .env -e SPRING_PROFILES_ACTIVE=dev $(APP_NAME):$(TAG)

## up: Start compose stack
up:
	docker compose up -d

## down: Stop compose stack
down:
	docker compose down

## logs: Follow api logs
logs:
	docker compose logs -f api

## smoke: Simple healthcheck & API smoke tests
smoke:
	bash scripts/smoke.sh

## help: Show this help
help:
	@grep -E '^##' Makefile | sed -e 's/## //'