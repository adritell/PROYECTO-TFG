package com.api.videojuegos.repository;

import com.api.videojuegos.entity.Token;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByToken(String token);
    void deleteByUser_Email(String email);

    // Consulta para eliminar tokens vencidos
    @Transactional
    @Modifying
    @Query("DELETE FROM Token t WHERE t.expirationDate <= :currentDate")
    void deleteExpiredTokens(Date currentDate);
}

