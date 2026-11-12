
package hn.uth.poo.biblioteca.dao;

/**
 *
 * @author ELIZ
 */
import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;

public interface LibroDAO {
   
    void insertar(Libro libro);
    void actualizar(Libro libro);
    void eliminar(int id);
    Libro buscarPorId(int id);
    Libro buscarPorIsbn(String isbn);
    List<Libro> listarTodos();
    List<Libro> listarPorAutor(int autorId);
    List<Libro> buscarPorTituloOAutor(String criterio);
}
