/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.app;

/**
 *
 * @author ELIZ
 */
import hn.uth.poo.biblioteca.dao.impl.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.BibliotecaServicio;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BibliotecaServicio servicio = new BibliotecaServicio();

    public static void main(String[] args) {
        try {
            Conexion.inicializarBaseDatos();
        } catch (DatosException e) {
            System.out.println("Error crítico al iniciar la base de datos: " + e.getMessage());
            return;
        }

        int opcion = -1;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarAutor();
                    case 2 -> listarAutores();
                    case 3 -> actualizarAutor();
                    case 4 -> eliminarAutor();
                    case 5 -> registrarLibro();
                    case 6 -> listarLibros();
                    case 7 -> buscarLibros();
                    case 8 -> actualizarLibro();
                    case 9 -> eliminarLibro();
                    case 0 -> System.out.println("\nSaliendo del sistema de Biblioteca. ¡Hasta luego!");
                    default -> System.out.println("\n[!] Opción no válida. Intente de nuevo.");
                }
            } catch (DatoInvalidoException e) {
                System.out.println("\n[VALIDACIÓN]: " + e.getMessage());
            } catch (DatosException e) {
                System.out.println("\n[ERROR DE BASE DE DATOS]: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERROR INESPERADO]: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n==========================================");
        System.out.println("      SISTEMA DE GESTIÓN DE BIBLIOTECA    ");
        System.out.println("==========================================");
        System.out.println("--- GESTIÓN DE AUTORES ---");
        System.out.println("1. Registrar nuevo autor");
        System.out.println("2. Listar todos los autores");
        System.out.println("3. Actualizar autor");
        System.out.println("4. Eliminar autor");
        System.out.println("--- GESTIÓN DE LIBROS ---");
        System.out.println("5. Registrar nuevo libro");
        System.out.println("6. Listar todos los libros");
        System.out.println("7. Buscar libros (por título o autor)");
        System.out.println("8. Actualizar libro");
        System.out.println("9. Eliminar libro");
        System.out.println("0. Salir");
        System.out.println("==========================================");
    }

    // ==================== OPCIONES DE AUTORES ====================

    private static void registrarAutor() {
        System.out.println("\n--- REGISTRAR AUTOR ---");
        System.out.print("Nombre del autor: ");
        String nombre = scanner.nextLine();
        System.out.print("Nacionalidad: ");
        String nacionalidad = scanner.nextLine();

        servicio.registrarAutor(nombre, nacionalidad);
        System.out.println(">> Autor registrado exitosamente.");
    }

    private static void listarAutores() {
        System.out.println("\n--- LISTA DE AUTORES ---");
        List<Autor> autores = servicio.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }
        System.out.printf("%-5s | %-25s | %-20s%n", "ID", "NOMBRE", "NACIONALIDAD");
        System.out.println("--------------------------------------------------------");
        for (Autor a : autores) {
            System.out.printf("%-5d | %-25s | %-20s%n", a.getId(), a.getNombre(), a.getNacionalidad());
        }
    }

    private static void actualizarAutor() {
        listarAutores();
        System.out.println("\n--- ACTUALIZAR AUTOR ---");
        int id = leerEntero("Ingrese el ID del autor a actualizar: ");
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva nacionalidad: ");
        String nacionalidad = scanner.nextLine();

        servicio.actualizarAutor(id, nombre, nacionalidad);
        System.out.println(">> Autor actualizado correctamente.");
    }

    private static void eliminarAutor() {
        listarAutores();
        System.out.println("\n--- ELIMINAR AUTOR ---");
        int id = leerEntero("Ingrese el ID del autor a eliminar: ");

        servicio.eliminarAutor(id);
        System.out.println(">> Autor eliminado correctamente.");
    }

    // ==================== OPCIONES DE LIBROS ====================

    private static void registrarLibro() {
        System.out.println("\n--- REGISTRAR LIBRO ---");
        listarAutores();
        int autorId = leerEntero("\nIngrese el ID del autor del libro: ");
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        int anio = leerEntero("Año de publicación: ");
        double precio = leerDouble("Precio: ");

        servicio.registrarLibro(isbn, titulo, anio, precio, autorId);
        System.out.println(">> Libro registrado exitosamente.");
    }

    private static void listarLibros() {
        System.out.println("\n--- LISTA DE LIBROS ---");
        List<Libro> libros = servicio.listarLibros();
        imprimirTablaLibros(libros);
    }

    private static void buscarLibros() {
        System.out.println("\n--- BUSCAR LIBROS ---");
        System.out.print("Ingrese título o nombre del autor: ");
        String criterio = scanner.nextLine();
        List<Libro> resultados = servicio.buscarLibros(criterio);
        imprimirTablaLibros(resultados);
    }

    private static void actualizarLibro() {
        listarLibros();
        System.out.println("\n--- ACTUALIZAR LIBRO ---");
        int id = leerEntero("Ingrese el ID del libro a actualizar: ");
        listarAutores();
        int autorId = leerEntero("Nuevo ID de autor: ");
        System.out.print("Nuevo ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Nuevo título: ");
        String titulo = scanner.nextLine();
        int anio = leerEntero("Nuevo año de publicación: ");
        double precio = leerDouble("Nuevo precio: ");

        servicio.actualizarLibro(id, isbn, titulo, anio, precio, autorId);
        System.out.println(">> Libro actualizado correctamente.");
    }

    private static void eliminarLibro() {
        listarLibros();
        System.out.println("\n--- ELIMINAR LIBRO ---");
        int id = leerEntero("Ingrese el ID del libro a eliminar: ");

        servicio.eliminarLibro(id);
        System.out.println(">> Libro eliminado correctamente.");
    }

    private static void imprimirTablaLibros(List<Libro> libros) {
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros.");
            return;
        }
        System.out.printf("%-4s | %-15s | %-28s | %-6s | %-10s | %-20s%n",
                "ID", "ISBN", "TÍTULO", "AÑO", "PRECIO", "AUTOR");
        System.out.println("-----------------------------------------------------------------------------------------------");
        for (Libro l : libros) {
            System.out.printf("%-4d | %-15s | %-28s | %-6d | L. %-7.2f | %-20s%n",
                    l.getId(), l.getIsbn(), l.getTitulo(), l.getAnio(), l.getPrecio(), l.getAutorNombre());
        }
    }

    // ==================== UTILIDADES DE LECTURA ====================

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Debe ingresar un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();
            try {
                return Double.parseDouble(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Debe ingresar un valor numérico válido (ej. 350.50).");
            }
        }
    }
}
