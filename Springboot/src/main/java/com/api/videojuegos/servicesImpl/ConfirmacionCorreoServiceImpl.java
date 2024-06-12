package com.api.videojuegos.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.videojuegos.entity.Usuario;
import com.api.videojuegos.repository.UsuarioRepository;
import com.api.videojuegos.service.ConfirmacionCorreoService;

@Service
public class ConfirmacionCorreoServiceImpl implements ConfirmacionCorreoService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public boolean confirmarCorreo(String token) {
        Usuario usuario = usuarioRepository.findByTokenConfirmacion(token);
        if (usuario != null) {
            // Realizar la lógica de activación de la cuenta
            usuario.setTokenConfirmacion(null); // Limpiar el token de confirmación después de la confirmación
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}

