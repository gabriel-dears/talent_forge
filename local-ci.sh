#!/bin/bash

set -e  # Exit immediately if a command exits with a non-zero status

DOCKER_IMAGE="talent-forge-app"
DOCKER_COMPOSE_FILE="docker-compose.yml"

echo "🧹 Cleaning and building the application..."
./mvnw clean install

echo "🐳 Shutting down existing Docker Compose containers..."
docker compose -f $DOCKER_COMPOSE_FILE down --remove-orphans

echo "🐳 Building Docker image..."
docker build -t $DOCKER_IMAGE .

echo "🚀 Starting Docker Compose..."
docker compose -f $DOCKER_COMPOSE_FILE up --build -d

echo "✅ Done. App should be running and connected to PostgreSQL."

echo ""
echo "📋 To see logs, run: docker-compose logs -f"
