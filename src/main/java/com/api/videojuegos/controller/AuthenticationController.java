package com.api.videojuegos.controller;

import com.api.videojuegos.dto.JwtAuthenticationResponse;
import com.api.videojuegos.entity.Token;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.repository.TokenRepository;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.request.LoginRequest;
import com.api.videojuegos.request.RegistroRequest;
import com.api.videojuegos.service.AuthenticationService;
import com.api.videojuegos.servicesImpl.JwtServiceImpl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para las operaciones de autenticación (signup, signin, logout).
 */

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AuthenticationService authenticationService;
    
    @Autowired
    TokenRepository tokenRepository;
    
    @Autowired
    UsuarioRepository userRepository;
    
    @Autowired
    JwtServiceImpl jwtServiceImpl; 

    /**
     * Registra un nuevo usuario en el sistema.
     * @param request Objeto RegistroRequest con los datos del nuevo usuario.
     * @return Respuesta de autenticación JWT.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegistroRequest request) {
        try {
            // Llamar al servicio de autenticación para registrar el usuario
            authenticationService.signup(request);
            
            // Devolver una respuesta exitosa al cliente
            return ResponseEntity.ok("User registered successfully. Please check your email for confirmation.");
        } catch (Exception e) {
            logger.error("Error during signup process.", e);
            return new ResponseEntity<>("Error during signup process", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Inicia sesión para un usuario existente.
     * @param request Objeto LoginRequest con las credenciales de inicio de sesión.
     * @return Respuesta de autenticación JWT.
     */
    @PostMapping("/signin")
    @Transactional
    public ResponseEntity<?> signin(@RequestBody LoginRequest request) {
        try {
            logger.info("Received signin request: {}", request);
            JwtAuthenticationResponse response = authenticationService.signin(request);
            
            // Eliminar cualquier token anterior asociado con el usuario
            tokenRepository.deleteByUser_Email(request.getEmail());
            
            logger.info("Signin successful for user: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during signin process.", e);
            return new ResponseEntity<>("Error during signin process", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            // Extraer el valor del token desde el encabezado
            String tokenValue = token.substring(7); // Eliminar "Bearer " del token

            
            // Verificar si el token ya está presente en la base de datos
            Token existingToken = tokenRepository.findByToken(tokenValue);
            if (existingToken != null) {
                // Si el token ya está en la base de datos, no es necesario guardarlo nuevamente
                return ResponseEntity.ok("User already logged out");
            }

            
            
            // Obtener el usuario asociado al token
            String userName = jwtServiceImpl.extractUserName(tokenValue);
            Usuario user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Calcular la fecha de expiración del token (por ejemplo, 7 días a partir de ahora)
            LocalDateTime expirationDateTime = LocalDateTime.now().plusDays(7);
            Instant expirationInstant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();

            // Verificar si el token ha expirado antes de agregarlo a la base de datos
            if (!jwtServiceImpl.isTokenExpired(tokenValue)) {
                // Guardar el token en la lista negra con el user id
                Token tokenEntity = new Token();
                tokenEntity.setUser(user);
                tokenEntity.setToken(tokenValue);
                tokenEntity.setExpirationDate(Date.from(expirationInstant)); // Establecer la fecha de expiración
                tokenRepository.save(tokenEntity);
            }

            // Otros pasos para cerrar sesión, como limpiar el contexto de seguridad, etc.
            SecurityContextHolder.clearContext();

            
            return ResponseEntity.ok("You have logged out successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during logout");
        }
    }

   

    
}
