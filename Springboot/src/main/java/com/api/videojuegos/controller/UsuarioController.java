package com.api.videojuegos.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.videojuegos.dto.UsuarioAdminResponse;
import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.dto.VideojuegoResponse;
import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.exceptions.BadRequestException;
import com.api.videojuegos.exceptions.UnauthorizedAccessException;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.service.JwtService;
import com.api.videojuegos.service.UsuarioService;

/**
 * Controlador para las operaciones relacionadas con usuarios.
 */
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    
    // Inicialización del logger para el controlador de usuarios
    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    // Inyección de dependencias del servicio de usuarios
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    UsuarioRepository userRepository;
    
    @Autowired
    JwtService jwtService;

    /**
     * Obtiene la lista de todos los usuarios. Devuelve información detallada para administradores
     * y reducida para usuarios normales.
     * @param token Token de autorización.
     * @return Lista de respuestas de usuario.
     */
    @GetMapping
    public ResponseEntity<?> getAllUsuarios(@RequestHeader(name = "Authorization") String token) {
        try {
            // Extraer el valor del token eliminando el prefijo "Bearer "
            String tokenValue = token.substring(7);
            
            // Obtener el email del usuario desde el token
            String userEmail = jwtService.extractUserName(tokenValue);
            
            // Obtener el usuario desde el email
            Usuario usuario = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            
            // Verificar si el usuario es administrador
            boolean isAdmin = usuario.getRoles().stream().anyMatch(role -> role == Rol.ROLE_ADMIN);
            
            // Obtener todos los usuarios
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            
            if (isAdmin) {
                // Si el usuario es administrador, devolver información detallada
                List<UsuarioAdminResponse> adminResponses = usuarios.stream()
                        .map(user -> new UsuarioAdminResponse(
                                user.getId(),
                                user.getFirstName(),
                                user.getLastName(),
                                user.getEmail(),
                                user.isActivo(),
                                user.getRoles().stream().map(Rol::name).collect(Collectors.toSet())
                        ))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(adminResponses, HttpStatus.OK);
            } else {
                // Si el usuario no es administrador, devolver información reducida
                List<UsuarioResponse> userResponses = usuarios.stream()
                        .map(user -> new UsuarioResponse(
                        		user.getId(),
                                user.getFirstName(),
                                user.getEmail()
                        ))
                        .collect(Collectors.toList());
                return new ResponseEntity<>(userResponses, HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error("Error al obtener todos los usuarios", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Obtiene un usuario por su ID. Devuelve información detallada para administradores
     * y reducida para usuarios normales.
     * @param id ID del usuario a obtener.
     * @param token Token de autorización.
     * @return Respuesta de usuario correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
        try {
            // Extraer el valor del token eliminando el prefijo "Bearer "
            String tokenValue = token.substring(7);

            // Obtener el email del usuario desde el token
            String userEmail = jwtService.extractUserName(tokenValue);

            // Obtener el usuario desde el email
            Usuario currentUser = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            // Verificar si el usuario es administrador
            boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role == Rol.ROLE_ADMIN);

            // Obtener el usuario por su ID
            Optional<Usuario> optionalUsuario = usuarioService.getUsuarioById(id);
            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();

                if (isAdmin) {
                    // Si el usuario es administrador, devolver información detallada
                    UsuarioAdminResponse adminResponse = new UsuarioAdminResponse(
                            usuario.getId(),
                            usuario.getFirstName(),
                            usuario.getLastName(),
                            usuario.getEmail(),
                            usuario.isActivo(),
                            usuario.getRoles().stream().map(Rol::name).collect(Collectors.toSet())
                    );
                    return new ResponseEntity<>(adminResponse, HttpStatus.OK);
                } else {
                    // Si el usuario no es administrador, devolver información reducida
                    UsuarioResponse userResponse = new UsuarioResponse(
                    		usuario.getId(),
                            usuario.getFirstName(),
                            usuario.getEmail()
                    );
                    return new ResponseEntity<>(userResponse, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error al obtener usuario por ID: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Actualiza un usuario existente por su ID.
     * Requiere el rol 'ROLE_ADMIN'.
     * @param id ID del usuario a actualizar.
     * @param usuario Objeto Usuario con los datos actualizados.
     * @return El usuario actualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioAdminResponse usuarioAdminResponse, @RequestHeader(name = "Authorization") String token) {
        // Obtener el usuario actualmente autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si el usuario actual es un administrador
        if (!usuarioService.isAdmin(authentication.getName())) {
            // El usuario actual no es un administrador, lanzar una excepción o devolver un error apropiado
            throw new UnauthorizedAccessException("Solo los administradores pueden actualizar usuarios");
        }

        // Registro de información sobre la solicitud de actualización de un usuario
        logger.info("Actualizando usuario con ID: {}", id);

        // Convertir UsuarioAdminResponse a Usuario
        Usuario usuario = new Usuario();
        usuario.setFirstName(usuarioAdminResponse.getNombre());
        usuario.setLastName(usuarioAdminResponse.getApellidos());
        usuario.setEmail(usuarioAdminResponse.getEmail());
        usuario.setPassword(usuarioAdminResponse.getPassword());
        usuario.setActivo(usuarioAdminResponse.isActivo());
        if (usuarioAdminResponse.getRoles() == null) {
            throw new BadRequestException("El campo 'roles' no puede ser nulo");
        }
        usuario.setRoles(usuarioAdminResponse.getRoles().stream().map(Rol::valueOf).collect(Collectors.toSet()));

        // Llamada al servicio de usuarios para actualizar el usuario
        return usuarioService.updateUser(id, usuario);
    }



    // Método para eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        // Obtener el usuario actualmente autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar si el usuario actual es un administrador
        if (!usuarioService.isAdmin(authentication.getName())) {
            // El usuario actual no es un administrador, lanzar una excepción o devolver un error apropiado
            throw new UnauthorizedAccessException("Solo los administradores pueden eliminar usuarios");
        }

        // Verificar si el usuario con el ID proporcionado existe
        if (!usuarioService.existsById(id)) {
            logger.warn("No se encontró ningún usuario con el ID: {}", id);
            throw new BadRequestException("No se encontró ningún usuario con el ID proporcionado");
        }

        // Eliminar el usuario
        usuarioService.deleteUser(id);
        logger.info("Usuario eliminado correctamente con ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * Crea un nuevo usuario. Requiere el rol 'ROLE_ADMIN'.
     * @param usuarioAdminResponse Objeto UsuarioAdminResponse que contiene la información del nuevo usuario.
     * @return El usuario creado.
     */
    @PostMapping
    public Usuario crearUsuario(@RequestBody UsuarioAdminResponse usuarioAdminResponse, @RequestHeader(name = "Authorization") String token) {
    	// Obtener el usuario actualmente autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        boolean isAdmin = usuarioService.isAdmin(userEmail);

        // Verificar si el usuario actual es un administrador
        if (!isAdmin) {
            // El usuario actual no es un administrador, lanzar una excepción o devolver un error apropiado
        	throw new UnauthorizedAccessException("Solo los admin pueden crear usuarios");
        }
        

        // Registro de información sobre la solicitud de creación de un nuevo usuario
        logger.info("Creando un nuevo usuario");

        Usuario usuario = new Usuario();
        usuario.setFirstName(usuarioAdminResponse.getNombre());
        usuario.setLastName(usuarioAdminResponse.getApellidos());
        usuario.setEmail(usuarioAdminResponse.getEmail());
        usuario.setPassword(usuarioAdminResponse.getPassword());
        usuario.setActivo(usuarioAdminResponse.isActivo());
        if (usuarioAdminResponse.getRoles() == null) {
            throw new BadRequestException("El campo 'roles' no puede ser nulo");
        }
        usuario.setRoles(usuarioAdminResponse.getRoles().stream().map(Rol::valueOf).collect(Collectors.toSet()));

        // Llamada al servicio de usuarios para crear un nuevo usuario
        return usuarioService.createUser(usuario);
    }
    
    
    
    /*
     NUEVOS METODOS A PROBAR
      */
    
    @GetMapping("/{id}/videojuegos-favoritos")
    public ResponseEntity<List<VideojuegoResponse>> getVideojuegosFavoritosByUsuario(@PathVariable Long id) {
        try {
            List<Videojuegos> videojuegosFavoritos = usuarioService.getVideojuegosFavoritosByUsuarioId(id);

            List<VideojuegoResponse> videojuegoResponses = videojuegosFavoritos.stream()
                .map(videojuego -> new VideojuegoResponse(
                		videojuego.getId(),
                        videojuego.getNombre(),
                        videojuego.getGenero(),
                        videojuego.getDescripcion(),
                        videojuego.getAnioPublicacion(),
                        videojuego.getPrecio(),
                        videojuego.getCalificacionPorEdades(),
                        videojuego.getPublicador(),
                        videojuego.getImagePath(),
                        videojuego.getPlataformas()
                ))
                .collect(Collectors.toList());

            return new ResponseEntity<>(videojuegoResponses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting videojuegos favoritos for usuario id: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
