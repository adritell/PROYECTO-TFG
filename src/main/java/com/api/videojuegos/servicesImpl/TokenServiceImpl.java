package com.api.videojuegos.servicesImpl;

import com.api.videojuegos.repository.TokenRepository;
import com.api.videojuegos.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;


	 /*elimina todos los tokens asociados con un usuario específico.*/
    @Override
    public void deleteByUser_Email(String email) {
        tokenRepository.deleteByUser_Email(email);
    }


}
