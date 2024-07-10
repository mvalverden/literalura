package com.alura.literalura.repository;

import com.alura.literalura.entities.Autor;
import com.alura.literalura.entities.Libro;
import com.alura.literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long>{

    @Query("SELECT l FROM Libro l WHERE l.autor = :autor")
    List<Libro> buscarLibroPorAutor(Autor autor);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    Optional<List<Libro>> buscarPorIdioma(Idioma idioma);

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    Optional<Libro> buscarPorTitulo(@Param("titulo") String titulo);
}