# Construir la aplicación Angular y usar para desarrollo
FROM node:18
WORKDIR /app

# Copiar archivos necesarios
COPY package*.json ./

# Instalar dependencias
RUN npm install --verbose

# Copiar todo el código fuente
COPY . .

# Exponer el puerto usado por Angular CLI
EXPOSE 4200

# Ejecutar el servidor de desarrollo de Angular
CMD ["npm", "start"]









# Construir la aplicación Angular
#FROM node:18 as build
#WORKDIR /app
#COPY package*.json ./
#RUN npm install --verbose
#COPY . .
#RUN npm run build --configuration=production

# Servir los archivos estáticos con Nginx
#FROM nginx:stable-alpine
#COPY --from=build /app/dist/your-angular-app /usr/share/nginx/html
#EXPOSE 80
#CMD ["nginx", "-g", "daemon off;"]
