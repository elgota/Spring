
package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Usuario;
import com.egg.noticias.enumeraciones.Rol;
import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServicio implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Transactional
    public void registrar(String nombreUsuario, String password, String password2, Date alta) throws MiException{
        
        validar(nombreUsuario, password, password2);
        
        Usuario usuario = new Usuario();
        
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuario.setAlta(alta);
        
        usuarioRepositorio.save(usuario);
    }
    
    private void validar(String nombreUsuario, String password, String password2) throws MiException{
        
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El nombre de usuario no puede estar vacio.");
        }
        
        if (password.isEmpty() || password == null || password.length() <= 5){
            
            throw new MiException("La contraseña no puede estar vacía o ser menor o igual a 5 caracteres.");
        }
        
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas deben ser iguales...");
            
        }
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepositorio.buscarPorUsuario(nombreUsuario);
        
        if (usuario != null) {
            
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString());
            
            permisos.add(p);
            
            return new User(usuario.getNombreUsuario(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
