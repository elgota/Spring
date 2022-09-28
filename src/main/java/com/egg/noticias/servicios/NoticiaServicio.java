
package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Imagen;
import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.excepciones.MiException;
import com.egg.noticias.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiaServicio {
    
    @Autowired
    private NoticiaRepositorio noticiaRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void crearNoticia(MultipartFile archivo,String titulo, String cuerpo) throws MiException{
        
        validar(titulo, cuerpo);
        
        Noticia noticia = new Noticia();
        
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setAlta(new Date());
        
        Imagen imagen = imagenServicio.guardar(archivo);
        
        noticia.setImagen(imagen);
        
        
        noticiaRepositorio.save(noticia);
    }
    
    public List<Noticia> listarNoticias(){
        
        List<Noticia> noticias = new ArrayList();
        
        noticias = noticiaRepositorio.findAll();
        
        return noticias;
    }
    
    public void modificarNoticia(String titulo, String cuerpo) throws MiException{
        
        validar(titulo, cuerpo);
        
        Optional<Noticia> respuesta = noticiaRepositorio.findById(titulo);
        
        if (respuesta.isPresent()) {
            
            Noticia noticia = respuesta.get();
            
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            
        }
    }
    
    @Transactional(readOnly = true)//Sólo sirve para leer
    public Noticia getOne(String id){
        return noticiaRepositorio.getOne(id);
    }
    
    @Transactional
    public void eliminarNoticia(String id){
        noticiaRepositorio.deleteById(id);
    }
    
    private void validar(String titulo, String cuerpo)throws MiException{
                
        if (titulo == null) {
            throw new MiException("El título no puede estar vacío...");
        }
        
        if (cuerpo.isEmpty() || cuerpo == null) {
            
            throw new MiException("El cuerpo de la noticia no puede estar vacío...");
        }
    }

}
