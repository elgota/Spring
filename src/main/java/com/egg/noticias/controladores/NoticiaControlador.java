
package com.egg.noticias.controladores;

import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class NoticiaControlador {
    
    @Autowired
    private NoticiaServicio noticiaServicio;
    
    @GetMapping("/noticia")
    public String noticia(){
        
        return "noticia.html";
    }
    
    
    @GetMapping("/carga")
    public String cargarNoticia() {
        return "cargar_noticia.html";
    }

    @PostMapping("/carga")
    public String carga(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo) {
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo);
            modelo.put("exito", "La noticia fue cargada correctamente!");
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            return "cargar_noticia.html";
        }
        
        return "index.html";
    }
    


}
