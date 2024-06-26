package com.api.videojuegos.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.repository.ComentarioRepository;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.repository.VideojuegosRepository;
import com.api.videojuegos.service.ComentarioService;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository commentRepository;
    
    @Autowired
    UsuarioRepository userRepository;
    
    @Autowired
    VideojuegosRepository videojuegosRepository;
    
    @Override
    public List<Comentario> getAllComments() {
        return commentRepository.findAll();
    }
    
    @Override
    public Comentario findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }
    
    @Override
    public void addComment(Comentario comment) {
        commentRepository.save(comment);
    }
    
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    
    @Override
    public List<Comentario> getComentariosByUser(String email) {
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return commentRepository.findByUsuario(user);
    }
    
    @Override
    public List<Comentario> getComentariosByVideojuegoId(Long videojuegoId) {
        Videojuegos videojuego = videojuegosRepository.findById(videojuegoId).orElse(null);
        return commentRepository.findByVideojuegos(videojuego);
    }
    
    @Override
    public Comentario updateComment(Comentario comentario) {
        return commentRepository.save(comentario);
    }

}
