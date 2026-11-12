
package hn.uth.poo.biblioteca.modelo;

/**
 *
 * @author ELIZ
 */
public class Libro {
    private int id;
    private String isbn;
    private String titulo;
    private int anio;
    private double precio;
    private int autorId;
    private String autorNombre; // Útil para mostrar el nombre del autor en consultas JOIN

    public Libro() {
    }

    public Libro(String isbn, String titulo, int anio, double precio, int autorId) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.precio = precio;
        this.autorId = autorId;
    }

    public Libro(int id, String isbn, String titulo, int anio, double precio, int autorId) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.precio = precio;
        this.autorId = autorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", isbn=" + isbn + ", titulo=" + titulo + ", anio=" + anio + ", precio=" + precio + ", autorId=" + autorId + '}';
    }
}
