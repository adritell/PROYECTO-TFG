package com.api.videojuegos.servicesImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.api.videojuegos.entity.Comentario;
import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.entity.Videojuegos;
import com.api.videojuegos.repository.ComentarioRepository;
import com.api.videojuegos.repository.VideojuegosRepository;
import com.api.videojuegos.service.VideojuegosService;

import jakarta.transaction.Transactional;

@Service
public class VideojuegoServiceImpl implements VideojuegosService {

    @Autowired
    private VideojuegosRepository videojuegosRepository;
    
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public List<Videojuegos> getAllVideojuegos() {
        return videojuegosRepository.findAll();
    }

    @Override
    public Optional<Videojuegos> getVideojuegoById(Long id) {
        return videojuegosRepository.findById(id);
    }

    @Override
    public Videojuegos createVideojuego(Videojuegos videojuego) {
        return videojuegosRepository.save(videojuego);
    }

    @Override
    public Videojuegos updateVideojuego(Long id, Videojuegos videojuego) {
        if (videojuegosRepository.existsById(id)) {
            videojuego.setId(id);
            return videojuegosRepository.save(videojuego);
        } else {
            return null;
        }
    }
    
    @Override
    public Optional<Videojuegos> findByNombre(String nombre) {
        return videojuegosRepository.findByNombre(nombre);
    }

    @Transactional
    public boolean deleteVideojuego(Long id) {
        Optional<Videojuegos> videojuegoOpt = videojuegosRepository.findById(id);

        if (videojuegoOpt.isPresent()) {
            Videojuegos videojuego = videojuegoOpt.get();
            
            // Desvincula el videojuego de todos los usuarios que lo tienen en favoritos
            for (Usuario usuario : videojuego.getUsuariosFavoritos()) {
                usuario.getVideojuegosFavoritos().remove(videojuego);
            }

            // Eliminar comentarios asociados al videojuego
            List<Comentario> comentarios = comentarioRepository.findAll();
            if (comentarios != null) {
                for (Comentario comentario : comentarios) {
                	System.out.println(comentario);
                    comentarioRepository.delete(comentario);
                }
            }

            // Eliminar el videojuego
            videojuegosRepository.delete(videojuego);
            
            return true;
        } else {
            return false;
        }
    }


    @Override
    public List<Videojuegos> searchVideojuegos(String passwd) {
        return videojuegosRepository.findByNombreContainingIgnoreCase(passwd);
    }
    
    
    // Nuevo método para obtener videojuegos paginados
    public Page<Videojuegos> getVideojuegosPaginados(Pageable pageable) {
        return videojuegosRepository.findAll(pageable);
    }
}
