/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noticias.news.Servicios;

import com.noticias.news.Entidades.imagen;
import com.noticias.news.Entidades.usuario;
import com.noticias.news.Repositorios.UsuarioRepositorio;
import com.noticias.news.enumeraciones.Rol;
import com.noticias.news.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    public UsuarioRepositorio UsuarioRepositorio;

    @Autowired
    public imagenService imagenService;

    @Transactional
    public void registrar(MultipartFile archivo,String nombre, String email, String password, String password2) throws MiException {
        validar(nombre, email, password, password2);
        usuario Usuario = new usuario();

        Usuario.setNombre(nombre);
        
        Usuario.setEmail(email);
        
        Usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        Usuario.setRol(Rol.USER);
        
        imagen imagen = imagenService.guardarImagen(archivo);
        
        Usuario.setImagen(imagen);

        UsuarioRepositorio.save(Usuario);
    }
    
    public usuario getOne(String id){
        return UsuarioRepositorio.getOne(id);
    }
    
    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Optional<usuario> respuesta = UsuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {

            usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuario.setRol(Rol.USER);
            
            String idImagen = null;
            
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            
            imagen imagen = imagenService.modificarImagen(archivo, idImagen);
            
            usuario.setImagen(imagen);
            
            UsuarioRepositorio.save(usuario);
        }

    }

    public void validar(String nombre, String email, String password, String password2) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede estar nulo o vacio.");
        }
        if (nombre.isEmpty() || email == null) {
            throw new MiException("El email no puede estar nulo o vacio.");
        }
        if (nombre.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacia ni tener menos de 5 caracteres.");
        }
        if (!password2.equals(password) || password2.isEmpty()) {
            throw new MiException("La contraseña no puede estar nula o vacia.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        usuario Usuario = UsuarioRepositorio.BuscarPorEmail(email);

        if (Usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + Usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", Usuario);

            return new User(Usuario.getEmail(), Usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

}
