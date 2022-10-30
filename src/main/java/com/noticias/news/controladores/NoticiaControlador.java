/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.news.controladores;

import com.noticias.news.Entidades.Noticia;
import com.noticias.news.Repositorios.NoticiaRepositorio;
import com.noticias.news.Servicios.NoticiaService;
import com.noticias.news.excepciones.MiException;
import java.util.List;
import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {

    @Autowired
    private NoticiaService NoticiaService;

    @GetMapping("/ingresar")//localhost:8080/noticia/ingresar
    public String noticia() {
        return "noticia.html";
    }

    @PostMapping("/ingresar")
    public String registro(@RequestParam(required = false) String titulo, @RequestParam(required = false) String cuerpo, ModelMap modelo) {

        try {
            NoticiaService.CrearNoticia(titulo, cuerpo);
            modelo.put("Exito", "La noticia fue cargada con exito.");
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
        }

        return "noticia.html";
    }
    
    @GetMapping("/lista")//Localhost:8080/noticia/lista
    public String Listar(ModelMap modelo){
        
    List<Noticia> noticias = NoticiaService.listarNoticia();
    
    modelo.addAttribute("noticias", noticias);
    
    return "lista.html";
    }
    
    @GetMapping("/modificar/{id}")//localhost:8080/noticia/modificar
    public String modificar(@PathVariable String id, ModelMap modelo){
    modelo.put("noticia", NoticiaService.GetOne(id));
    return "modificar.html";
    }
}
