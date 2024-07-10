# Literalura

Literalura es un proyecto de aplicación Java para gestionar libros y autores. Aquí te explico cómo descargarlo y usarlo.

## Requisitos
- Java 17
- Maven
- PostgreSQL 15
- Driver JDBC para PostgreSQL

## Instrucciones

### Descarga del Repositorio
1. Clona este repositorio en tu máquina local:
   git clone https://github.com/mvalverden/literalura.git

2. Accede al directorio del proyecto:
cd literalura

### Configuración de la Base de Datos
1. Asegúrate de tener PostgreSQL instalado y configurado.
2. Crea una base de datos en PostgreSQL para la aplicación.

### Configuración de la Aplicación
1. Abre el proyecto en tu IDE preferido (por ejemplo, IntelliJ IDEA, Eclipse).
2. Configura las credenciales de la base de datos en el archivo `application.properties`:

  spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_base_de_datos
  spring.datasource.username=username
  spring.datasource.password=password

  
### Ejecutar la Aplicación
1. Desde tu IDE, busca y ejecuta la clase `LiteraluraApplication`.
2. Después, abre tu navegador y visita http://localhost:8080 para ver la aplicación.

### Explorar el Menú
Cuando la aplicación arranque, verás este menú:
Elige una opción ingresando su número:
1. Buscar un libro por título
2. Ver todos los libros disponibles
3. Mostrar todos los autores registrados
4. Ver autores que están vivos en un año específico
5. Buscar libros por idioma
0. Salir
