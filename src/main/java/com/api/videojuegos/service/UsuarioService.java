package com.api.videojuegos.service;

import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario createUser(Usuario user);

    UserDetailsService userDetailsService();

    List<UsuarioResponse> getAllUsers();
    
    List<Usuario> getAllUsuarios();
    
    Optional<Usuario> getUsuarioById(Long id);


    UsuarioResponse findUserById(Long id);
    
    
    Optional<Usuario> findByEmail(String email);
    
    boolean existsById(Long id); 
    
    Usuario updateUser(Long id, Usuario user); 
    
    void deleteUser(Long id); 
    
    boolean isAdmin(String username);
    
    List<Usuario> getUsuariosFavoritosByVideojuegoId(Long videojuegoId);

    List<Videojuegos> getVideojuegosFavoritosByUsuarioId(Long usuarioId);

}
