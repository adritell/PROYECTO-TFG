package com.api.videojuegos;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.videojuegos.controller.ComentarioController;
import com.api.videojuegos.dto.ComentarioRequest;
import com.api.videojuegos.dto.ComentarioResponse;
import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.service.ComentarioService;
import com.api.videojuegos.service.UsuarioService;
import com.api.videojuegos.service.VideojuegosService;

@ExtendWith(MockitoExtension.class)
class ComentarioControllerTest {

    @Mock
    private ComentarioService comentarioService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private VideojuegosService videojuegosService;

    @InjectMocks
    private ComentarioController comentarioController;

    @Test
    void getAllComentarios_shouldReturnListOfComentarios() {
        // Arrange
        List<Comentario> mockComentarios = Arrays.asList(new Comentario(), new Comentario());
        when(comentarioService.getAllComments()).thenReturn(mockComentarios);

        // Act
        ResponseEntity<List<ComentarioResponse>> response = comentarioController.getAllComentarios("dummy-token");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockComentarios, response.getBody());
    }

    @Test
    void getComentarioById_shouldReturnComentario() {
        // Arrange
        long comentarioId = 1L;
        String token = "dummy-token";
        Comentario mockComentario = new Comentario();
        when(comentarioService.findById(comentarioId)).thenReturn(mockComentario);

        // Act
        ResponseEntity<ComentarioResponse> response = comentarioController.getComentarioById(comentarioId, token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ComentarioResponse expectedResponse = new ComentarioResponse(
            mockComentario.getId(),
            mockComentario.getText(),
            new UsuarioResponse(mockComentario.getUsuario().getId(),mockComentario.getUsuario().getFirstName(), mockComentario.getUsuario().getEmail()),
            mockComentario.getFecha()
        );
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void addComentario_shouldReturnCreated() {
        // Arrange
        ComentarioRequest comentarioRequest = new ComentarioRequest();
        comentarioRequest.setText("Test comment");
        comentarioRequest.setGame("Test Game");

        Usuario mockUsuario = new Usuario();

        Videojuegos mockVideojuego = new Videojuegos();
        when(videojuegosService.findByNombre(comentarioRequest.getGame())).thenReturn(Optional.of(mockVideojuego));

        Comentario mockComentario = new Comentario();
        doNothing().when(comentarioService).addComment(mockComentario);

        // Act
        ResponseEntity<Void> response = comentarioController.addComentario(comentarioRequest, "");

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void deleteComentario_shouldReturnNoContent() {
        // Arrange
        long comentarioId = 1L;
        doNothing().when(comentarioService).deleteComment(comentarioId);

        // Act
        ResponseEntity<Void> response = comentarioController.deleteComentario(comentarioId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
