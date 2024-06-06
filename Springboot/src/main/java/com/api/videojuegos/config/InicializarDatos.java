package com.api.videojuegos.config;

import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.repository.VideojuegosRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InicializarDatos implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VideojuegosRepository videojuegosRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Usuario 1
        	// Crear un conjunto de roles
        	Set<Rol> roles = new HashSet<>();
        	roles.add(Rol.ROLE_ADMIN);
            Usuario usuario1 = new Usuario();
            usuario1.setFirstName("Adrián");
            usuario1.setLastName("Tellado");
            usuario1.setEmail("adri@gmail.com");
            usuario1.setPassword(passwordEncoder.encode("password"));
            usuario1.setRoles(roles);
            usuario1.setActivo(true);
            usuarioRepository.save(usuario1);

            // Usuario 2
            Set<Rol> roles2 = new HashSet<>();
        	roles2.add(Rol.ROLE_USER);
            Usuario usuario2 = new Usuario();
            usuario2.setFirstName("Juan");
            usuario2.setLastName("Álvarez");
            usuario2.setEmail("juan@gmail.com");
            usuario2.setPassword(passwordEncoder.encode("password"));
            usuario2.setRoles(roles2);
            usuario2.setActivo(true);
            usuarioRepository.save(usuario2);
            
            // Usuario 3
            Usuario usuario3 = new Usuario();
            usuario3.setFirstName("Pepe");
            usuario3.setLastName("Gómez");
            usuario3.setEmail("pepe@gmail.com");
            usuario3.setPassword(passwordEncoder.encode("password"));
            usuarioRepository.save(usuario3);

        } catch (Exception e) {
        	
        }
    }
}
