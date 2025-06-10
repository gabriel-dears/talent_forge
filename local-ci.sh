#!/bin/bash

set -e  # Exit on first error

DOCKER_IMAGE="talent-forge-app"
DOCKER_COMPOSE_FILE="docker-compose.yml"

echo "🧹 Cleaning and building the application with code coverage..."
./mvnw clean verify

echo "📊 Code coverage report generated at:"
echo "    target/site/jacoco/index.html"

echo "🐳 Shutting down existing Docker Compose containers..."
docker compose -f $DOCKER_COMPOSE_FILE down --remove-orphans

echo "🐳 Building Docker image..."
docker build -t $DOCKER_IMAGE .

echo "🚀 Starting Docker Compose..."
docker compose -f $DOCKER_COMPOSE_FILE up --build -d

echo "✅ Done. App should be running and connected to PostgreSQL."

echo ""
echo "📋 To see logs, run: docker-compose logs -f"
