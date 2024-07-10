package com.alura.literalura.repository;

import com.alura.literalura.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.death_year >= :fecha_death AND a.birth_year <= :fecha_death")
    Optional<List<Autor>> autoresVivos(Integer fecha_death);

    @Query("SELECT a FROM Autor a WHERE a.name = :name")
    Optional<Autor> encontrarPorNombre(String name);

}