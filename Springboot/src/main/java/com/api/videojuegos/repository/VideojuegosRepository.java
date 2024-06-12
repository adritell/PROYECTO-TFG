package com.api.videojuegos.repository;

import com.api.videojuegos.entity.Videojuegos;
import org.springframework.data.jpa.repository.JpaRepository;
/*import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;*/
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideojuegosRepository extends JpaRepository<Videojuegos, Long> {
	
	
	 /*@Modifying
	 @Query("DELETE FROM Usuario u WHERE :videojuego MEMBER OF u.videojuegosFavoritos")
	 void deleteFavoritesByVideojuego(@Param("videojuego") Videojuegos videojuego);*/
	
    List<Videojuegos> findByNombreContainingIgnoreCase(String nombre);
    Optional<Videojuegos> findByNombre(String nombre);
}
