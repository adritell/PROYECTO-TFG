package com.api.videojuegos.controller;

import com.api.videojuegos.dto.CalificacionResponse;
import com.api.videojuegos.dto.ComentarioResponse;
import org.springframework.data.domain.*;
import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.dto.VideojuegoResponse;
import com.api.videojuegos.entity.Calificacion;
import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.service.CalificacionService;
import com.api.videojuegos.service.ComentarioService;
import com.api.videojuegos.service.UsuarioService;
import com.api.videojuegos.service.VideojuegosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador para las operaciones relacionadas con los videojuegos.
 */
@RestController
@RequestMapping("/api/v1/videojuegos")
public class VideojuegosController {

    private static final Logger logger = LoggerFactory.getLogger(VideojuegosController.class);

    @Autowired
    private VideojuegosService videojuegosService;
    
    @Autowired
    private CalificacionService calificacionService;
    
    @Autowired
    private ComentarioService comentarioService;
    
    @Autowired
    private UsuarioService usuarioService;

    
    
    
    @GetMapping
    public ResponseEntity<Page<VideojuegoResponse>> getAllVideojuegos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Videojuegos> videojuegosPage = videojuegosService.getVideojuegosPaginados(pageable);

            Page<VideojuegoResponse> videojuegoResponsePage = videojuegosPage.map(videojuego -> new VideojuegoResponse(
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
            ));

            return new ResponseEntity<>(videojuegoResponsePage, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting paginated videojuegos.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    /**
     * Obtiene la lista de todos los videojuegos.
     * @return Lista de todos los videojuegos.
     
    @GetMapping
    public ResponseEntity<List<VideojuegoResponse>> getAllVideojuegos() {
        try {
            List<Videojuegos> videojuegos = videojuegosService.getAllVideojuegos();

            List<VideojuegoResponse> videojuegoResponses = videojuegos.stream()
                .map(videojuego -> new VideojuegoResponse(
                    videojuego.getId(),
                    videojuego.getNombre(),
                    videojuego.getGenero(),
                    videojuego.getDescripcion(),
                    videojuego.getAnioPublicacion(),
                    videojuego.getCalificacionPorEdades(),
                    videojuego.getPublicador(),
                    videojuego.getPlataformas()
                ))
                .collect(Collectors.toList());

            return new ResponseEntity<>(videojuegoResponses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting all videojuegos.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    
    /**
     * Obtiene un videojuego por su ID.
     * @param id ID del videojuego a obtener.
     * @return Videojuego correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getVideojuegosById(@PathVariable Long id) {
        try {
            Optional<Videojuegos> optionalVideojuegos = videojuegosService.getVideojuegoById(id);
            if (optionalVideojuegos.isPresent()) {
                Videojuegos videojuegos = optionalVideojuegos.get();

                // Mapear Videojuegos a VideojuegoResponse
                VideojuegoResponse videojuegoResponse = new VideojuegoResponse(
                		videojuegos.getId(),
                        videojuegos.getNombre(),
                        videojuegos.getGenero(),
                        videojuegos.getDescripcion(),
                        videojuegos.getAnioPublicacion(),
                        videojuegos.getPrecio(),
                        videojuegos.getCalificacionPorEdades(),
                        videojuegos.getPublicador(),
                        videojuegos.getImagePath(),
                        videojuegos.getPlataformas()
                );

                logger.info("Returned videojuegos with ID: {}", id);
                return new ResponseEntity<>(videojuegoResponse, HttpStatus.OK);
            } else {
                logger.warn("Videojuegos with ID {} not found.", id);
                return new ResponseEntity<>("Videojuegos not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while getting videojuegos with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Crea un nuevo videojuego.
     * @param videojuegos Objeto Videojuegos que contiene la información del nuevo videojuego.
     * @return El videojuego creado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createVideojuegos(@RequestBody Videojuegos videojuegos) {
        try {
            Videojuegos createdVideojuegos = videojuegosService.createVideojuego(videojuegos);
            logger.info("Created videojuegos with ID: {}", createdVideojuegos.getId());
            return new ResponseEntity<>(createdVideojuegos, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while creating a new videojuegos.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Actualiza un videojuego existente.
     * @param id ID del videojuego a actualizar.
     * @param videojuegos Objeto Videojuegos con la información actualizada.
     * @return El videojuego actualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateVideojuegos(@PathVariable Long id, @RequestBody Videojuegos videojuegos) {
        try {
            Videojuegos updatedVideojuegos = videojuegosService.updateVideojuego(id, videojuegos);
            if (updatedVideojuegos != null) {
                logger.info("Updated videojuegos with ID: {}", id);
                return new ResponseEntity<>(updatedVideojuegos, HttpStatus.OK);
            } else {
                logger.warn("Videojuegos with ID {} not found for update.", id);
                return new ResponseEntity<>("Videojuegos not found for update", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while updating videojuegos with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Elimina un videojuego por su ID.
     * @param id ID del videojuego a eliminar.
     * @return Respuesta de éxito.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteVideojuegos(@PathVariable Long id) {
        try {
            boolean deleted = videojuegosService.deleteVideojuego(id);
            if (deleted) {
                logger.info("Deleted videojuegos with ID: {}", id);
                return new ResponseEntity<>("Videogame deleted sucessfully. ",HttpStatus.NO_CONTENT);
            } else {
                logger.warn("Videojuegos with ID {} not found for deletion.", id);
                return new ResponseEntity<>("Videojuegos not found for deletion", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while deleting videojuegos with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    /**
     * Busca videojuegos por una palabra clave.
     * @param keyword Palabra clave para buscar videojuegos.
     * @return Lista de videojuegos que coinciden con la palabra clave.
     */
    @GetMapping("/search")
    public ResponseEntity<List<Videojuegos>> searchVideojuegos(@RequestParam String keyword) {
        try {
            List<Videojuegos> searchResults = videojuegosService.searchVideojuegos(keyword);
            logger.info("Returned {} search results for keyword: {}", searchResults.size(), keyword);
            return new ResponseEntity<>(searchResults, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while searching videojuegos.", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
    
    
    
    
    
    
    
    /* NUEVOS METODOS A PROBAR:*/
    
    /**
     * Obtiene las calificaciones de un videojuego por su ID.
     * @param id ID del videojuego.
     * @return Lista de calificaciones asociadas al videojuego.
     */
    @GetMapping("/{id}/calificaciones")
    public ResponseEntity<List<CalificacionResponse>> getCalificacionesByVideojuego(@PathVariable Long id) {
        try {
            List<Calificacion> calificaciones = calificacionService.getCalificacionesByVideojuegoId(id);
            
            // Mapear cada Calificacion a CalificacionResponse
            List<CalificacionResponse> calificacionResponses = calificaciones.stream()
                .map(calificacion -> new CalificacionResponse(
                    calificacion.getId(),
                    calificacion.getValoracion(),
                    calificacion.getUsuario().getUsername()
                ))
                .collect(Collectors.toList());

            return new ResponseEntity<>(calificacionResponses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting calificaciones for videojuego id: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    
 // Endpoint para obtener los comentarios de un videojuego
    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<ComentarioResponse>> getComentariosByVideojuego(@PathVariable Long id) {
        try {
            List<Comentario> comentarios = comentarioService.getComentariosByVideojuegoId(id);

            List<ComentarioResponse> comentarioResponses = comentarios.stream()
                .map(comentario -> new ComentarioResponse(
                    comentario.getId(),
                    comentario.getText(),
                    new UsuarioResponse(comentario.getUsuario().getId(),comentario.getUsuario().getFirstName(), comentario.getUsuario().getEmail()),
                    comentario.getFecha()
                ))
                .collect(Collectors.toList());

            return ResponseEntity.ok(comentarioResponses); // Especifica el tipo de ResponseEntity
        } catch (Exception e) {
            logger.error("Error while getting comentarios for videojuego id: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Especifica el tipo de ResponseEntity
        }
    }
    
    
    /*
     endpoint para traer los usuarios que han seleccionado un videojuego específico como favorito, basándose en el ID del videojuego.
     Para futuras funciones como medir la popularidad de un videojuego basado en cuántos usuarios lo han marcado como favorito, 
     crear sistemas de recomendaciones personalizadas para otros usuarios,etc.*/
     
    @GetMapping("/{id}/usuarios-favoritos")
    public ResponseEntity<List<UsuarioResponse>> getUsuariosFavoritosByVideojuego(@PathVariable Long id) {
        try {
            List<Usuario> usuariosFavoritos = usuarioService.getUsuariosFavoritosByVideojuegoId(id);

            List<UsuarioResponse> usuarioResponses = usuariosFavoritos.stream()
                .map(usuario -> new UsuarioResponse(usuario.getId() ,usuario.getFirstName(), usuario.getEmail()))
                .collect(Collectors.toList());

            return new ResponseEntity<>(usuarioResponses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting usuarios favoritos for videojuego id: " + id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
}
