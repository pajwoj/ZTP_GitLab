FROM maven:latest AS backend-builder
WORKDIR /app
COPY . /app
RUN mvn -f /app/pom.xml clean install

FROM node:latest AS frontend-builder
WORKDIR /app/frontend
COPY frontend/package.json frontend/package-lock.json /app/frontend/
RUN npm install
COPY frontend /app/frontend
RUN npm run build

FROM node:latest AS production
RUN apt-get update
RUN apt-get install -y openjdk-17-jdk maven libgtk2.0-0 libgtk-3-0 libgbm-dev libnotify-dev libnss3 libxss1 libasound2 libxtst6 xauth xvfb

WORKDIR /app

COPY --from=backend-builder /app /app
COPY --from=frontend-builder /app/frontend /app/frontend

# Expose ports
EXPOSE 8080
EXPOSE 3000

# Start both backend and frontend servers
CMD ["sh", "-c", "mvn spring-boot:run > /dev/null 2>&1 & cd frontend && npm start"]
