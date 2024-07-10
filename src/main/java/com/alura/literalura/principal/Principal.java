package com.alura.literalura.principal;

import com.alura.literalura.entities.Autor;
import com.alura.literalura.entities.Libro;
import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.services.AutorService;
import com.alura.literalura.services.ConsumirAPI;
import com.alura.literalura.services.ConversorDatos;
import com.alura.literalura.services.LibroService;

import java.util.*;

public class Principal {

    private Scanner lectura = new Scanner(System.in);
    private ConsumirAPI consumirApi = new ConsumirAPI();
    private ConversorDatos conversor = new ConversorDatos();
    private String URL_BASE = "https://gutendex.com/books/";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private LibroService serviceLibros;
    private AutorService autorService;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository,
                     LibroService serviceLibros, AutorService autorService) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.serviceLibros = serviceLibros;
        this.autorService = autorService;
    }


    public void mostrarMenu() {

        var opcion = -1;

        while (opcion != 0) {

            String menu = """
                    Elija la opción a través de su número:
                    1. Buscar libro por titulo
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    0. Salir
                    """;

            System.out.println(menu);
            try {
                System.out.print("Digite el valor correspondiente a la acción: ");
                opcion = lectura.nextInt();
                lectura.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\nParece q digitaste algo mal.\n");
                lectura.nextLine();
            }

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    filtrarAutoresVivos();
                    break;
                case 5:
                    filtrarPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opcion inválida, pruebe nuevamente.");
            }
        }
    }

    private Libro obtenerDatosApi() {
        System.out.print("Ingrese el nombre del libro que desea buscar: ");
        String nombreLibro = lectura.nextLine();
        String json = consumirApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Datos datos = conversor.conversorDatos(json, Datos.class);
        Optional<DatosLibro> datosLibro = datos.libros().stream()
                .filter(d -> d.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();

        Libro libroNuevo = null;
        if (datosLibro.isPresent()) {
            List<Autor> autores = autorRepository.findAll();
            try {
                libroNuevo = serviceLibros.checkAutorRepetido(datosLibro.get(), autores);
            } catch (Exception e) {
                libroNuevo = new Libro(datosLibro.get());
                libroNuevo.setAutor(null);
            }
        }
        return libroNuevo;
    }

    private void buscarLibro() {
        Libro libroNuevo = obtenerDatosApi();
        if (libroNuevo != null) {
            Optional<Libro> libroExistente = libroRepository.buscarPorTitulo(libroNuevo.getTitulo());
            if (libroExistente.isPresent()) {
                System.out.println(libroExistente.get());
            } else {
                // Verificar si el autor existe
                if (libroNuevo.getAutor() != null) {
                    Optional<Autor> autorExistente = autorRepository.encontrarPorNombre(libroNuevo.getAutor().getName());
                    if (autorExistente.isPresent()) {
                        libroNuevo.setAutor(autorExistente.get());
                    } else {
                        autorRepository.save(libroNuevo.getAutor());
                    }
                }
                libroRepository.save(libroNuevo);
                System.out.println(libroNuevo);
            }
        } else {
            System.out.println("\n~ Ese libro no existe en la base de datos.)\n");
        }
    }

    private void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();

        for (Autor autor : autores) {
//            autorService.convertirName(autor);
            autor.setLibros(libroRepository.buscarLibroPorAutor(autor));
        }

        autores.forEach(System.out::println);
    }

    private void filtrarAutoresVivos() {
        Integer limitYear = 0;
        System.out.print("\nIngrese el año vivo de autor(es) que desea buscar: ");
        try {
            limitYear = lectura.nextInt();
        } catch (IllegalArgumentException e) {
            System.out.println("Dato invalido: " + e);

        }
        Optional<List<Autor>> autorOptional = autorRepository.autoresVivos(limitYear);

        if (autorOptional.isPresent()) {
            List<Autor> autoresVivos = autorOptional.get();
            if (autoresVivos.isEmpty()) {
                System.out.println("No hay autores disponibles para dicha fecha!\n");
            }
            for (Autor autor : autoresVivos) {
                if (autor.getBirth_year() <= limitYear && autor.getDeath_year() >= limitYear) {
                    System.out.println(autor);
                }
            }
        }
    }

    private void filtrarPorIdioma() {
        try {
            System.out.print("""
        Ingrese el idioma para buscar los libros: 
        Inglés
        Español
        Francés
        Portugués
        """
            );

            String idioma = lectura.nextLine();
            Idioma idiomaNuevo = Idioma.fromInputString(idioma);

            Optional<List<Libro>> buscarPorIdioma = libroRepository.buscarPorIdioma(idiomaNuevo);

            if (buscarPorIdioma.isPresent()) {
                List<Libro> librosPorIdioma = buscarPorIdioma.get();
                if (librosPorIdioma.isEmpty()) {
                    System.out.println("\nNo hay libros en " + idioma + " todavía! \n");
                } else {
                    librosPorIdioma.forEach(System.out::println);
                }
            } else {
                System.out.println("\nNo se encontraron libros para el idioma " + idioma);
            }
        } catch (Exception e) {
            System.out.println("Ingresa un valor válido: " + e.getMessage() + "\n");
        }
    }
}