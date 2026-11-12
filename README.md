# Examen Practico del Segundo Parcial - Biblioteca POO

## Datos del Estudiante
* **Nombre:** Aryani Elizabeth Martinez Lopez 
* **Número de Cuenta:** 202410011132
* **Asignatura:** Programación Orientada a Objetos (POO)
* **Universidad:** Universidad Tecnológica de Honduras (UTH)

---

## Descripción del Proyecto
Aplicación de consola desarrollada en Java bajo una arquitectura por capas (Modelo, Excepciones, DAO, Servicio y Presentación) utilizando Maven como gestor de dependencias y SQLite como motor de base de datos relacional con integridad referencial activa (`PRAGMA foreign_keys = ON`).

---

## Arquitectura de Paquetes
* `hn.uth.poo.biblioteca.modelo` — Clases de entidad (`Autor`, `Libro`).
* `hn.uth.poo.biblioteca.excepciones` — Excepciones personalizadas (`DatosException`, `DatoInvalidoException`).
* `hn.uth.poo.biblioteca.dao` — Conexión y contratos de persistencia (`Conexion`, `AutorDAO`, `LibroDAO`).
* `hn.uth.poo.biblioteca.dao.impl` — Implementaciones JDBC (`AutorDAOImpl`, `LibroDAOImpl`).
* `hn.uth.poo.biblioteca.servicio` — Reglas de negocio y validaciones (`BibliotecaServicio`).
* `hn.uth.poo.biblioteca.app` — Capa de presentación por consola (`Main`).


## Evidencias de Ejecución
(Las capturas de pantalla de las operaciones CRUD y validaciones de negocio se encuentran guardadas en la carpeta de evidencias del repositorio).

