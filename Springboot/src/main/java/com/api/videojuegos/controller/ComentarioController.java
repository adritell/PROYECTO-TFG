package com.api.videojuegos.controller;

import com.api.videojuegos.dto.ComentarioRequest;
import com.api.videojuegos.dto.ComentarioResponse;
import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.entity.Token;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.exceptions.BadRequestException;
import com.api.videojuegos.exceptions.UnauthorizedAccessException;
import com.api.videojuegos.repository.TokenRepository;
import com.api.videojuegos.service.ComentarioService;
import com.api.videojuegos.service.UsuarioService;
import com.api.videojuegos.service.VideojuegosService;
import com.api.videojuegos.servicesImpl.JwtServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Controlador para las operaciones relacionadas con los comentarios.
 */

@RestController
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {

    private static final Logger logger = LoggerFactory.getLogger(ComentarioController.class);

    @Autowired
    ComentarioService comentarioService;

    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    JwtServiceImpl jwtService;

    @Autowired
    VideojuegosService videojuegosService;
    
    @Autowired
    TokenRepository tokenRepository;

    
    /**
     * Obtiene todos los comentarios.
     * @return Lista de todos los comentarios.
     */
    @GetMapping
    public ResponseEntity<List<ComentarioResponse>> getAllComentarios(@RequestHeader(name = "Authorization") String token) {
        try {
            // Extraer el nombre de usuario del token JWT
            String tokenValue = token.startsWith("Bearer ") ? token.substring(7) : token;
            String userName = jwtService.extractUserName(tokenValue);

            // Verificar si el usuario es administrador
            boolean isAdmin = usuarioService.isAdmin(userName);

            List<Comentario> comentarios;

            // Si el usuario es administrador, obtener todos los comentarios
            if (isAdmin) {
                comentarios = comentarioService.getAllComments();
            } else {
                // Si no es administrador, obtener solo los comentarios del usuario
                comentarios = comentarioService.getComentariosByUser(userName);
            }

            // Convertir Comentarios a ComentarioResponse
            List<ComentarioResponse> comentarioResponses = comentarios.stream()
                    .map(comentario -> new ComentarioResponse(
                            comentario.getId(),
                            comentario.getText(),
                            new UsuarioResponse(comentario.getUsuario().getId() ,comentario.getUsuario().getFirstName(), comentario.getUsuario().getEmail()),
                            comentario.getFecha()
                    ))
                    .collect(Collectors.toList());

            logger.info("Returned {} comentarios.", comentarios.size());
            return new ResponseEntity<>(comentarioResponses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting all comments.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* Obtiene un comentario por su ID.
    * @param id ID del comentario a obtener.
    * @return ComentarioResponse con los detalles del comentario.
    */
   @GetMapping("/{id}")
   public ResponseEntity<ComentarioResponse> getComentarioById(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
       try {
           // Obtener el comentario por su ID
           Comentario comentario = comentarioService.findById(id);
           
           if (comentario == null) {
               return new ResponseEntity<>(HttpStatus.NOT_FOUND);
           }

           // Convertir Comentario a ComentarioResponse
           ComentarioResponse comentarioResponse = new ComentarioResponse(
                   comentario.getId(),
                   comentario.getText(),
                   new UsuarioResponse(comentario.getUsuario().getId(),comentario.getUsuario().getFirstName(), comentario.getUsuario().getEmail()),
                   comentario.getFecha()
           );

           logger.info("Returned comentario with ID: {}", id);
           return new ResponseEntity<>(comentarioResponse, HttpStatus.OK);
       } catch (Exception e) {
           logger.error("Error while getting comentario by ID.", e);
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

    /**
     * Agrega un nuevo comentario.
     * @param comentarioRequest Objeto ComentarioRequest con la información del nuevo comentario.
     * @return Respuesta de éxito.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> addComentario(@RequestBody ComentarioRequest comentarioRequest, @RequestHeader("Authorization") String token) {
        try {
            // Extraer el valor del token desde el encabezado
            String tokenValue = token.substring(7); // Eliminar "Bearer " del token

            // Verificar si el token ya está presente en la base de datos y si no esta (se encuentra invalidado por cierre de sesión) realiza el comentario  si no hay errores adicionales
            Token existingToken = tokenRepository.findByToken(tokenValue);
            if (existingToken == null) {
            	 // Obtener el usuario basado en el token de autorización
            	/*
            	 * Al utilizar el token de autorización para extraer el nombre de usuario, estás obteniendo 
      directamente la identidad del usuario que está autenticado en el sistema. Este enfoque garantiza que el 
      usuario que realiza el comentario sea el mismo que está autorizado mediante el token de autorización. Al 
      verificar la autenticidad del token en la base de datos, te aseguras de que el usuario que está realizando
       la solicitud sea válido y esté activo en el sistema.
            	 */
            	// Obtener el usuario basado en el token de autorización
                String userEmail = jwtService.extractUserName(tokenValue);
                Optional<Usuario> usuarioOptional = usuarioService.findByEmail(userEmail);
                Usuario usuario = usuarioOptional.orElse(null);

                if (usuario == null) {
                    System.out.println("El usuario no existe");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Manejar el caso en que el usuario no exista
                }

                // Obtener el videojuego basado en el nombre proporcionado en la solicitud
                Optional<Videojuegos> videojuegoOptional = videojuegosService.findByNombre(comentarioRequest.getGame());
                Videojuegos videojuego = videojuegoOptional.orElse(null);

                if (videojuego == null) {
                    System.out.println("El videojuego no exite");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

                // Crear un nuevo objeto de Comentario y configurar el usuario y el videojuego
                Comentario comentario = new Comentario();
                comentario.setText(comentarioRequest.getText());
                comentario.setUsuario(usuario);
                comentario.setVideojuegos(videojuego);
                comentario.setFecha(LocalDateTime.now()); 


                // Guardar el comentario en la base de datos
                comentarioService.addComment(comentario);
                logger.info("Added new comment with ID: {}", comentario.getId());
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                // Manejar el caso en que el token no exista en la base de datos
            	System.out.println("El token no exite");

                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("Error while adding a new comment.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /**
     * Actualiza un comentario existente por su ID.
     * Requiere el rol 'ROLE_USER'.
     * @param id ID del comentario a actualizar.
     * @param comentarioRequest Objeto ComentarioRequest con los datos actualizados del comentario.
     * @return Respuesta de éxito.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> updateComentario(@PathVariable Long id, @RequestBody ComentarioRequest comentarioRequest) {
        
            // Obtener el usuario actualmente autenticado desde el contexto de seguridad
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            boolean isAdmin = usuarioService.isAdmin(userEmail);

            // Buscar el comentario por su ID
            Comentario comentarioExistente = comentarioService.findById(id);
            
            // Verificar si el comentario existe
            if (comentarioExistente == null) {
                throw new BadRequestException("Comentario no encontrado");
            }
            
            // Verificar si el usuario es administrador o el propietario del comentario
            if (!isAdmin && !comentarioExistente.getUsuario().getEmail().equals(userEmail)) {
                throw new UnauthorizedAccessException("No tiene permiso para actualizar este comentario");
            }
            
            // Actualizar el texto del comentario
            comentarioExistente.setText(comentarioRequest.getText());
            
         // Actualizar la fecha a la fecha actual
            comentarioExistente.setFecha(LocalDateTime.now()); 

            
            // Guardar el comentario actualizado en la base de datos
            comentarioService.addComment(comentarioExistente);
            
            logger.info("Updated comment with ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
    }

    /**
     * Elimina un comentario por su ID.
     * @param id ID del comentario a eliminar.
     * @return Respuesta de éxito.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        // Obtener el usuario actualmente autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        boolean isAdmin = usuarioService.isAdmin(userEmail);

        // Obtener el comentario
        Comentario comentario = comentarioService.findById(id);
        if (comentario == null) {
            throw new BadRequestException("Comentario no encontrado");
        }

        // Verificar si el usuario es administrador o el propietario del comentario
        if (!isAdmin && !comentario.getUsuario().getEmail().equals(userEmail)) {
            throw new UnauthorizedAccessException("No tiene permiso para eliminar este comentario");
        }

        // Eliminar el comentario
        comentarioService.deleteComment(id);
        logger.info("Deleted comment with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
