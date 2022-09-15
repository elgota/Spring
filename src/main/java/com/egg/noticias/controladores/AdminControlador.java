
package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.servicios.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AdminControlador {
    
    @Autowired
    NoticiaServicio noticiaServicio = new NoticiaServicio();
    
    @GetMapping("/admin")
    public String admin(ModelMap modelo){

        List<Noticia> noticias = noticiaServicio.listarNoticias();
        modelo.addAttribute("noticias", noticias);
        return "panel_admin.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable String id, ModelMap modelo){
        
        modelo.put("noticia", noticiaServicio.getOne(id));
        
        return "noticia_modificar.html";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id){
        noticiaServicio.eliminarNoticia(id);
        
        return "redirect:../admin";
    }

}
