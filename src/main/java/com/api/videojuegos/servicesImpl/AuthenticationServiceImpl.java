package com.api.videojuegos.servicesImpl;

import com.api.videojuegos.dto.JwtAuthenticationResponse;
import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.request.RegistroRequest;
import com.api.videojuegos.request.LoginRequest;
import com.api.videojuegos.service.AuthenticationService;
import com.api.videojuegos.service.ConfirmacionCorreoService;
import com.api.videojuegos.service.JwtService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ConfirmacionCorreoService confirmacionCorreoService;
    @Autowired
    private JwtServiceImpl jwtServiceImpl;
    
    public AuthenticationServiceImpl(UsuarioRepository userRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     ConfirmacionCorreoService confirmacionCorreoService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.confirmacionCorreoService = confirmacionCorreoService;
     }

    @Override
    public void signup(RegistroRequest request) {
        // Verificar si el correo electrónico ya está en uso
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }
        
        // Crear un nuevo usuario
        Usuario user = new Usuario();
        user.setFirstName(request.getNombre());
        user.setLastName(request.getApellidos());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActivo(false); // La cuenta está inactiva hasta que se confirme por correo electrónico
        user.setRoles(Collections.singleton(Rol.ROLE_USER));
        
        // Guardar el usuario en la base de datos
        userRepository.save(user);
        
        // Enviar correo electrónico de confirmación
        confirmacionCorreoService.confirmarCorreo(user.getEmail());    
        }

    @Override
    public JwtAuthenticationResponse signin(LoginRequest request) {
        // Obtener el usuario correspondiente al correo electrónico proporcionado
        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        
        // Verificar si el usuario ha confirmado su correo electrónico
        if (!user.isActivo()) {
            // Si el usuario no ha confirmado su correo electrónico, devuelve un mensaje de error
            throw new IllegalArgumentException("You must confirm your email before logging in.");
        }

        // Autenticar al usuario utilizando el AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Obtener información adicional del usuario para volcarla en el token
        String nombre = user.getFirstName();
        Set<Rol> roles = user.getRoles();
        
        // Calcular la fecha de expiración del token (por ejemplo, 1 días a partir de ahora)
        LocalDateTime expirationDateTime = LocalDateTime.now().plusDays(1);
        Instant expirationInstant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();
        
        // Generar el token JWT con la información adicional en el payload
        String jwt = jwtServiceImpl.createToken(user, expirationInstant, roles,nombre);
        
        // Crear el objeto de respuesta que incluye el token
        JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt);
        /*response.setNombre(nombre);
        response.setRoles(roles);
        response.setExpirationDate(expirationDateTime);*/
        
        return response;
    }
}