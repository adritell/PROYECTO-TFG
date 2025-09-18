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
import org.springframework.security.authentication.BadCredentialsException;
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
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        Usuario user = new Usuario();
        user.setFirstName(request.getNombre());
        user.setLastName(request.getApellidos());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActivo(true);
        user.setRoles(Collections.singleton(Rol.ROLE_USER));

        userRepository.save(user);
        confirmacionCorreoService.confirmarCorreo(user.getEmail());    
    }

    @Override
    public JwtAuthenticationResponse signin(LoginRequest request) {
        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password."));

        if (!user.isActivo()) {
            throw new BadCredentialsException("You must confirm your email before logging in.");
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String nombre = user.getFirstName();
        Set<Rol> roles = user.getRoles();

        LocalDateTime expirationDateTime = LocalDateTime.now().plusDays(1);
        Instant expirationInstant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();

        String jwt = jwtServiceImpl.createToken(user, expirationInstant, roles, nombre);

        return new JwtAuthenticationResponse(jwt);
    }
}
