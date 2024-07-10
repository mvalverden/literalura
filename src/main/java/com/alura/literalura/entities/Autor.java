package com.alura.literalura.entities;

import com.alura.literalura.model.DatosAutor;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birth_year;
    private Integer death_year;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.name = datosAutor.name();
        this.birth_year = datosAutor.birth_year();
        this.death_year = datosAutor.death_year();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(d -> d.setAutor(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        List<String> titulos = this.libros.stream()
                .map(Libro::getTitulo).
                collect(Collectors.toList());

        return  "\nAutor: " + this.name +
                "\nFecha de nacimiento: " + this.birth_year +
                "\nFecha de fallecimiento: " + this.death_year +
                "\nLibros: " + titulos + "\n";
    }
}