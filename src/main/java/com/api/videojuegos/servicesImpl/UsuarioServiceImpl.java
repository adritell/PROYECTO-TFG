package com.api.videojuegos.servicesImpl;

import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public Usuario createUser(Usuario user) {
        return userRepository.save(user);
    }

    @Override
    public List<UsuarioResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UsuarioResponse(user.getFirstName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse findUserById(Long id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UsuarioResponse(user.getFirstName(), user.getEmail());
    }

    
    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Usuario updateUser(Long id, Usuario user) {
        Optional<Usuario> optionalUsuario = userRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuarioExistente = optionalUsuario.get();
            usuarioExistente.setFirstName(user.getFirstName());
            usuarioExistente.setEmail(user.getEmail());
            usuarioExistente.setRoles(user.getRoles()); 
            
            return userRepository.save(usuarioExistente);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }
    
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
    @Override
    public boolean isAdmin(String email) {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.isAdmin(); 
    }
    
    
    @Override
    public List<Usuario> getUsuariosFavoritosByVideojuegoId(Long videojuegoId) {
        return userRepository.findUsuariosFavoritosByVideojuegoId(videojuegoId);
    }
    
    
    @Override
    public List<Videojuegos> getVideojuegosFavoritosByUsuarioId(Long usuarioId) {
        Optional<Usuario> usuario = userRepository.findById(usuarioId);
        return usuario.map(Usuario::getVideojuegosFavoritos).orElse(Collections.emptySet()).stream().toList();
    }
}
