/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.news.Repositorios;

import com.noticias.news.Entidades.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<usuario, String> {
    
    @Query("SELECT u FROM usuario u WHERE u.email = :email")
    public usuario BuscarPorEmail(@Param("email") String email);
}
