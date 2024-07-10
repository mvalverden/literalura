package com.alura.literalura.services;

import com.alura.literalura.entities.Autor;
import com.alura.literalura.entities.Libro;
import com.alura.literalura.model.DatosLibro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    public Libro checkAutorRepetido(DatosLibro datosLibro, List<Autor> autores){
        Libro libroNuevo = new Libro(datosLibro);
        Optional<Autor> autorDuplicado = autores.stream()
                .filter(a -> a.getName().equalsIgnoreCase(libroNuevo.getAutor().getName()))
                .findFirst();
        if(autorDuplicado.isPresent()){
            libroNuevo.setAutor(autorDuplicado.get());
            return libroNuevo;
        } else{
            return libroNuevo;
        }
    }
}
