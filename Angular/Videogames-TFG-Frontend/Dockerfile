# Construir la aplicación Angular
FROM node:18 as build
WORKDIR /app
COPY package*.json ./
RUN npm cache clean --force
RUN npm install --verbose
RUN npm install -g @angular/cli
COPY . .
RUN npm run build --configuration=production

# Ejecutar la aplicación Angular en local
FROM node:18
WORKDIR /app
COPY --from=build /app /app
RUN npm install -g @angular/cli

EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0"]
