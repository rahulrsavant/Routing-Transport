#!/bin/bash

echo "🛠️  Building backend with Maven..."
./mvnw clean package -DskipTests

echo "✅ Build complete. Copying JAR to deploy location..."

sudo cp target/*.jar /opt/routing_transport/app.jar

echo "🔄 Restarting backend service..."
sudo systemctl restart routing_transport

echo "🎉 Backend Deploy finished!"