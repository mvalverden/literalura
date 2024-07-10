package com.alura.literalura.services;

public interface IConversorDatos {
    <T> T conversorDatos(String json, Class<T> clase);
}
