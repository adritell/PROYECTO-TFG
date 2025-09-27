# 🎮 Videogames TFG – Plataforma estilo Steam

Este proyecto fue desarrollado como **Trabajo de Fin de Grado** del ciclo de **Desarrollo de Aplicaciones Web**.  
Simula una plataforma similar a Steam, permitiendo la gestión de videojuegos y usuarios, con funcionalidades completas tanto para usuarios como para administradores.  

---

## 🚀 Stack tecnológico

### Backend
- **Java con Spring Boot** → Framework robusto y seguro para la creación de aplicaciones backend.  
- **Spring Security + JWT** → Autenticación y gestión de roles (usuario/admin).  
- **JPA/Hibernate** → Acceso y persistencia de datos.  

### Base de datos
- **MySQL** → Base de datos relacional para usuarios, juegos, comentarios y demás entidades.  

### Frontend
- **Angular (TypeScript)** → Framework moderno para interfaces dinámicas y modulares.  
- **Bootstrap + SASS** → Estilos responsivos y consistentes.  

### Otros
- **Docker & Docker Compose** → Para levantar fácilmente los servicios.  
- **Lombok** → Reducción de código boilerplate en Java.  

---

## ✨ Funcionalidades principales

- 👤 Registro, login y cierre de sesión de usuarios.  
- 🔐 Autenticación con JWT y gestión de sesiones.  
- 🛒 Biblioteca personal de juegos comprados.  
- ⭐ Lista de favoritos.  
- 💬 Sistema de comentarios y calificaciones.  
- 📝 Edición de perfil de usuario.  
- 📊 Panel de administración para gestionar usuarios y catálogo de juegos.  

---

## 📋 Requisitos funcionales (resumen)

- **RF01:** Registro e inicio de sesión.  
- **RF02:** Cierre de sesión.  
- **RF03:** Exploración de juegos.  
- **RF04:** Comentarios y calificaciones.  
- **RF05:** Edición, creación y actualización de usuarios.  
- **RF06:** Lista de favoritos.  
- **RF07:** Integración con base de datos.  
- **RF08:** Autenticación con JWT.  
- **RF09:** Gestión de sesiones.  
- **RF10:** Gestión de roles.  

---

## 📂 Estructura del proyecto

- **Backend (Spring Boot):** Carpeta `/Springboot`  
- **Frontend (Angular):** Carpeta `/Angular/Videogames-TFG-Frontend`  
- **Docker Compose:** Archivo `docker-compose.yml` en la raíz  

---

## ⚡ Despliegue con Docker

0. Liberar los puertos usados para los servicios en el DockerCompose o cambiar los
 puertos que usan (Opcional)
 *Si se quiere parar el servidor mysql por tenerlo en el puerto 3306, ejecutar el comando:
 sudo systemctl stop mysql
 Para este punto, en Ubuntu podemos usar el comando: sudo fuser-k 8080/tcp (sustituir el
 8080 por el puerto que se quiera liberar)


1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuusuario/videogames-tfg.git
   cd videogames-tfg


2. Abrir la terminal y dirigirse al directorio raíz del repositorio en la terminal
 (donde se encuentra el archivo docker-compose.yml).

 
3. Ejecutar el comando:
    docker-compose build
 
4. Ejecutar el comando:
     docker-compose up


 5. Ya debería de estar creado el contenedor.
   *Extra. Para acceder al servicio mysql del contenedor y ejecutar el script de inserción de
   datos de videojuegos.
   -Ejecutar, una vez arrancado el contenedor mysql con el servicio, el comando:
       docker exec-it <nombre_del_contenedor_mysql> mysql-uroot-proot

    -Meter los datos del script ubicado en la carpeta resources de la api en la carpeta
   Springboot



📸 Vista previa

[🎥 Ver demo en Loom](https://www.loom.com/share/81ad83e4e06b4cb09acca02435a8149e?sid=a461196b-5691-4da2-9c15-63ddc95d2484)
