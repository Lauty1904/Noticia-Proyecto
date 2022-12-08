/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.news.Servicios;

import com.noticias.news.Entidades.imagen;
import com.noticias.news.Repositorios.imagenRepositorio;
import com.noticias.news.excepciones.MiException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class imagenService {
    
    @Autowired
    private imagenRepositorio imagenRepositorio;
    
    public imagen guardarImagen(MultipartFile archivo) throws MiException{
        if (archivo != null) {
            try {
                imagen imagen = new imagen();
                
                imagen.setContenido(archivo.getBytes());
                
                imagen.setMime(archivo.getContentType());
                
                imagen.setNombre(archivo.getName());
                
                return imagenRepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    public imagen modificarImagen(MultipartFile archivo, String idImagen)throws MiException{
       if (archivo != null) {
            try {
                
                if (idImagen != null) {
                    Optional<imagen> respuesta = imagenRepositorio.findById(idImagen);
                    
                    if (respuesta.isPresent()) {
                        respuesta.get();
                    }
                }
                
                imagen imagen = new imagen();
                
                imagen.setContenido(archivo.getBytes());
                
                imagen.setMime(archivo.getContentType());
                
                imagen.setNombre(archivo.getName());
                
                return imagenRepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
        
    }
}
