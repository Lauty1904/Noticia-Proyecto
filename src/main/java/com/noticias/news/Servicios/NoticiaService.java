/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.news.Servicios;

import com.noticias.news.Entidades.Noticia;
import com.noticias.news.Repositorios.NoticiaRepositorio;
import com.noticias.news.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class NoticiaService {
    @Autowired
    private NoticiaRepositorio NoticiaRepositorio;
    
    @Transactional
    public void CrearNoticia(String titulo, String cuerpo) throws MiException {
                
        validar(titulo,cuerpo);
        Noticia noticia = new Noticia();

        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);

        NoticiaRepositorio.save(noticia);

    }
    
    public List<Noticia> listarNoticia(){
    List<Noticia> noticia = new ArrayList();
    
    noticia=NoticiaRepositorio.findAll();
    
    return noticia;
    }
    
    public void ModificarNoticia(String titulo, String cuerpo)throws MiException{
        validar(titulo,cuerpo);     
        Optional<Noticia> respuesta = NoticiaRepositorio.findById(titulo);
        
        if(respuesta.isPresent()){
          Noticia noticia = respuesta.get();
          noticia.setCuerpo(cuerpo);
          noticia.setTitulo(titulo);
          
        }
        
    }
    
    public Noticia GetOne(String id){
    return NoticiaRepositorio.getOne(id);
    
    }
    
    private void validar(String titulo, String cuerpo)throws MiException{
    if(titulo==null || titulo.isEmpty()){
        throw new MiException("El titulo no puede estar en blanco.");
        }
        if(cuerpo==null||titulo.isEmpty()){
        throw new MiException("El cuerpo no puede estar en blanco.");
        }
    }

}
