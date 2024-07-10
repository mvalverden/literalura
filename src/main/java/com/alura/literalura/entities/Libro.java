package com.alura.literalura.entities;

import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Idioma;
import jakarta.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Long numeroDescargas;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromApiString(datosLibro.idioma().get(0));
        this.numeroDescargas = datosLibro.numeroDescargas();
        Optional<Autor> autorOptional = datosLibro.autor().stream()
                .map(Autor::new)
                .findFirst();

        Optional<DatosAutor> autor = datosLibro.autor().stream()
                .findFirst();
        if (autor.isPresent()) {
            this.autor = new Autor(autor.get());
        } else {
            System.out.println("Este libro no tiene autor.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    @Override
    public String toString() {
        String nombreAutor = "Este libro no tiene autor.";
        if (autor != null) {
            nombreAutor = autor.getName();
        }

        return "\n-----LIBRO-----" +
                "\nTitulo: " + this.titulo +
                "\nAutor: " + nombreAutor +
                "\nIdioma: " + idioma +
                "\nNumero de descargas: " + numeroDescargas +
               "\n---------------\n";



    }
}