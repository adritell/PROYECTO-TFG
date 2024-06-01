package com.api.videojuegos.servicesImpl;

import com.api.videojuegos.entity.Calificacion;
import com.api.videojuegos.repository.CalificacionRepository;
import com.api.videojuegos.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionServiceImpl implements CalificacionService {

	@Autowired
    CalificacionRepository calificacionRepository;

    

    @Override
    public List<Calificacion> getCalificacionesByVideojuegoId(Long videojuegoId) {
        return calificacionRepository.findByVideojuegoId(videojuegoId);
    }
}
