package com.api.videojuegos.servicesImpl;

import com.api.videojuegos.dto.UsuarioAdminResponse;
import com.api.videojuegos.dto.UsuarioResponse;
import com.api.videojuegos.entity.Rol;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.exceptions.BadRequestException;
import com.api.videojuegos.exceptions.ResourceNotFoundException;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.repository.VideojuegosRepository;
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
    
    @Autowired
    private VideojuegosRepository videojuegosRepository;

    @Override
    public Usuario createUser(Usuario user) {
        return userRepository.save(user);
    }
    
   

    @Override
    public List<UsuarioResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UsuarioResponse(user.getId(),user.getFirstName(), user.getEmail()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Usuario> getAllUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public UsuarioResponse findUserById(Long id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UsuarioResponse(user.getId() ,user.getFirstName(), user.getEmail());
    }

    
    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Usuario updateUser(Long id, Usuario user) {
        return userRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setFirstName(user.getFirstName());
            usuarioExistente.setLastName(user.getLastName());
            usuarioExistente.setEmail(user.getEmail());
            usuarioExistente.setPassword(user.getPassword()); // Asegúrate de manejar la contraseña adecuadamente (cifrado, etc.)
            usuarioExistente.setActivo(user.isActivo());
            usuarioExistente.setRoles(user.getRoles());
            return userRepository.save(usuarioExistente);
        }).orElseThrow(() -> new BadRequestException("Usuario con ID " + id + " no encontrado"));
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

        return user.getRoles().stream()
                .anyMatch(role -> role == Rol.ROLE_ADMIN);
    }
    
    
    @Override
    public List<Usuario> getUsuariosFavoritosByVideojuegoId(Long videojuegoId) {
        return userRepository.findUsuariosFavoritosByVideojuegoId(videojuegoId);
    }
    
    
    @Override
    public Optional<Usuario> getUsuarioById(Long id) {
        return userRepository.findById(id);
    }
    
    //Metodo para obetener los videojuegos favoritos de un usuario por su id
    @Override
    public List<Videojuegos> getVideojuegosFavoritosByUsuarioId(Long usuarioId) {
        Optional<Usuario> usuario = userRepository.findById(usuarioId);
        return usuario.map(Usuario::getVideojuegosFavoritos).orElse(Collections.emptySet()).stream().toList();
    }
    
    
    // Nuevo método para añadir un videojuego a favoritos
    public void addVideojuegoToFavorites(Long usuarioId, Long idVideojuego) {
        Optional<Usuario> optionalUsuario = userRepository.findById(usuarioId);

        Videojuegos videojuego = videojuegosRepository.findById(idVideojuego)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado"));


        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.getVideojuegosFavoritos().add(videojuego);
            userRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
    
    /*Nuevo método para eliminar videojuegos de la lista de favoritos de un usuario */
    public void removeVideojuegoFromFavorites(Long usuarioId, Long videojuegoId) {

        Usuario usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Videojuegos videojuego = videojuegosRepository.findById(videojuegoId)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado"));

        usuario.getVideojuegosFavoritos().remove(videojuego);
        userRepository.save(usuario);
    }
    
    
    
    
    public Optional<Usuario> findById(Long id) {
        return userRepository.findById(id);
    }



	
    
    



	
}
