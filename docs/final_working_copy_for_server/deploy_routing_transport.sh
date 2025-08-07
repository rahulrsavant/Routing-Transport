#!/bin/bash

echo "=== Step 6: Build Frontend (Vue) ==="
cd ~/codebase2/Routing-Transport/frontend || exit
npm install
npm run build
sudo cp -r dist/* /var/www/ideabotkey/routing_transport/

echo "=== Step 7: Build Backend (Spring Boot) ==="
cd ~/codebase2/Routing-Transport/backend || exit
./mvnw clean package -DskipTests
sudo cp target/*.jar /opt/routing_transport/app.jar

echo "=== Step 9: Start & Enable Backend Service ==="
sudo systemctl daemon-reload
sudo systemctl start routing_transport
sudo systemctl enable routing_transport
sudo systemctl status routing_transport

echo "=== Step 10: Enable & reload NGINX site ==="
sudo ln -sf /etc/nginx/sites-available/ideabotkey.in /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl reload nginx

echo "=== Step 11: Verify Everything ==="
curl -v http://localhost:8932/routing_transport/api/health
sudo tail -f /var/log/nginx/access.log &
sudo tail -f /var/log/nginx/error.log &
sudo journalctl -u routing_transport -f
