# Routing-Transport

This repository contains a demo application for managing transportation data. The project is split into a Java backend and a Vue frontend.

For a description of the proposed UI screens (login, user management, roles, permissions, companies and other CRUD screens) see [docs/UI-Design.md](docs/UI-Design.md).

## Structure
- **backend** – Spring Boot application
- **frontend** – Vue 3 application powered by Vite

## Running the project
Clone the repo and start the backend followed by the frontend:
```bash
cd backend && ./mvnw spring-boot:run
```
In a separate terminal:
```bash
cd frontend && npm install && npm run dev
```

The application will be available on `http://localhost:5173` with the API served from `http://localhost:8081`.


## Deployment under NGINX

When deploying the application to a subdirectory such as `/routing_transport/`, serve the built Vue files and proxy the API through NGINX:

```
server {
    listen 80;
    server_name ideabotkey.in www.ideabotkey.in;

    # Path where the Vue build is deployed
    root /var/www/ideabotkey;
    index index.html;

    # Redirect the domain root to the application
    location = / {
        return 301 /routing_transport/;
    }

    # Proxy API requests to the Spring Boot backend
    location ^~ /routing_transport/api/ {
        proxy_pass http://localhost:8932;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }

    # Serve the Vue frontend and enable history mode
    location /routing_transport/ {
        root /var/www/ideabotkey;
        try_files $uri $uri/ /routing_transport/index.html;
    }
}
```

Add a `<base>` tag to `frontend/index.html` so client-side navigation works when the app is hosted under the subdirectory:

```html
<base href="/routing_transport/" />
```

### Verification steps
1. Build the frontend with `npm run build` and check that `dist/index.html` contains the `<base>` tag.
2. Test the NGINX configuration with `nginx -t`.
3. `curl http://ideabotkey.in/routing_transport/` should serve the app.
4. `curl http://ideabotkey.in/routing_transport/api/health` should hit the Spring Boot service running on port `8932` with context path `/routing_transport/api`.
