 package com.api.videojuegos.repository;

import com.api.videojuegos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long id);
    boolean existsByEmail(String email);
    Usuario findByTokenConfirmacion(String token);
    
    

 // Consulta personalizada para traer los usuarios que han seleccionado un videojuego específico como favorito, basándose en el ID del videojuego
    @Query("SELECT u FROM Usuario u JOIN u.videojuegosFavoritos v WHERE v.id = :videojuegoId")
    List<Usuario> findUsuariosFavoritosByVideojuegoId(@Param("videojuegoId") Long videojuegoId);
}
