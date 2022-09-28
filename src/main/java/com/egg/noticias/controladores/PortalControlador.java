
package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.servicios.NoticiaServicio;
import com.egg.noticias.servicios.UsuarioServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private NoticiaServicio noticiaServicio = new NoticiaServicio();
    
    @Autowired
    private UsuarioServicio usuarioServicio = new UsuarioServicio();
    
    @GetMapping("/")
    public String indexLista(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);  
        
        return "index.html";
    }
    
    @GetMapping("/registrar")
    public String registrar(){
        
        return "registro.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombreUsuario, @RequestParam String password, @RequestParam String password2, Date alta, ModelMap modelo) throws MiException{
        
            
            try {
            usuarioServicio.registrar(nombreUsuario, password, password2, alta);
            
            modelo.put("exito", "Se completó el registro con éxito");
            
            return "index.html";
            
            } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "registro.html";
        }
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo){
        
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña incorrecto!");
        }
        return "login.html";
    }
    
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio.html";
    }
    
    
    
    

}
