/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.news.Repositorios;

import com.noticias.news.Entidades.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String> {

    //buscar por titulo:
    @Query("SELECT n FROM Noticia n WHERE n.titulo = :titulo ")
    public Noticia BuscarNoticiaPorTiulo(@Param("titulo") String titulo);

   //Eliminar noticia: 
   // @Query("DELETE n FROM Noticia n where n.titulo = :titulo")
   // public Noticia EliminarNoticia(@Param("titulo") String titulo);

   //Actualizar noticia
   // @Query("UPDATE n FROM Noticia n WHERE n.titulo = :titulo")
   // public Noticia ActualizarNoticia(@Param("titulo") String titulo);
   //    

}
