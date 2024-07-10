package com.alura.literalura.services;


import com.alura.literalura.entities.Autor;
import org.springframework.stereotype.Service;

@Service
public class AutorService {
    public Autor convertirName(Autor autor){
        try{
            autor.setName(autor.getName().split(",")[1]
                    .replace(" ", "") + " " +
                    autor.getName().split(",")[0]);
        } catch (Exception e){
        }

        return autor;
    }
}
