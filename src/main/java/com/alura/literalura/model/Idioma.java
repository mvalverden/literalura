package com.alura.literalura.model;

public enum Idioma {

    Inglés ("en", "Inglés"),
    Español ("es", "Español"),
    Francés ("fr", "Francés"),
    Portugués ("pt", "Portugués");

    private String idiomaGutendex;
    private String idiomaEspaniol;

    Idioma(String idiomaGutendex, String idiomaEspañol){
        this.idiomaGutendex = idiomaGutendex;
        this.idiomaEspaniol = idiomaEspañol;
    }

    public static Idioma fromApiString(String text){
        for(Idioma idioma : Idioma.values()){
            if(idioma.idiomaGutendex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("No hay libros en el idioma: " + text);
    }

    public static Idioma fromInputString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idiomaEspaniol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No hay libros en el idioma: " + text);
    }

}