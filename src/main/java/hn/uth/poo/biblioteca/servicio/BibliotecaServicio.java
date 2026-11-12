package hn.uth.poo.biblioteca.servicio;

/**
 *
 * @author ELIZ
 */
import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.time.Year;
import java.util.List;

public class BibliotecaServicio {
    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public BibliotecaServicio() {
        this.autorDAO = new AutorDAOImpl();
        this.libroDAO = new LibroDAOImpl();
    }

    // ==================== REGLAS DE NEGOCIO: AUTORES ====================

    public void registrarAutor(String nombre, String nacionalidad) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("El nombre del autor es obligatorio.");
        }
        String nacLimpia = (nacionalidad != null && !nacionalidad.trim().isEmpty())
                ? nacionalidad.trim()
                : "No especificada";

        Autor autor = new Autor(nombre.trim(), nacLimpia);
        autorDAO.insertar(autor);
    }

    public void actualizarAutor(int id, String nombre, String nacionalidad) {
        Autor existente = autorDAO.buscarPorId(id);
        if (existente == null) {
            throw new DatoInvalidoException("No existe ningún autor con el ID: " + id);
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException("El nombre del autor no puede quedar vacío.");
        }
        String nacLimpia = (nacionalidad != null && !nacionalidad.trim().isEmpty())
                ? nacionalidad.trim()
                : "No especificada";

        existente.setNombre(nombre.trim());
        existente.setNacionalidad(nacLimpia);
        autorDAO.actualizar(existente);
    }

    public void eliminarAutor(int id) {
        Autor existente = autorDAO.buscarPorId(id);
        if (existente == null) {
            throw new DatoInvalidoException("No existe ningún autor con el ID: " + id);
        }
        int cantidadLibros = autorDAO.contarLibrosPorAutor(id);
        if (cantidadLibros > 0) {
            throw new DatoInvalidoException(
                    "No se puede eliminar al autor '" + existente.getNombre()
                    + "' porque tiene " + cantidadLibros + " libro(s) asociado(s)."
            );
        }
        autorDAO.eliminar(id);
    }

    public Autor buscarAutorPorId(int id) {
        return autorDAO.buscarPorId(id);
    }

    public List<Autor> listarAutores() {
        return autorDAO.listarTodos();
    }

    // ==================== REGLAS DE NEGOCIO: LIBROS ====================

    public void registrarLibro(String isbn, String titulo, int anio, double precio, int autorId) {
        validarDatosLibro(isbn, titulo, anio, precio, autorId, null);

        Libro libro = new Libro(isbn.trim(), titulo.trim(), anio, precio, autorId);
        libroDAO.insertar(libro);
    }

    public void actualizarLibro(int id, String isbn, String titulo, int anio, double precio, int autorId) {
        Libro existente = libroDAO.buscarPorId(id);
        if (existente == null) {
            throw new DatoInvalidoException("No existe ningún libro con el ID: " + id);
        }

        validarDatosLibro(isbn, titulo, anio, precio, autorId, id);

        existente.setIsbn(isbn.trim());
        existente.setTitulo(titulo.trim());
        existente.setAnio(anio);
        existente.setPrecio(precio);
        existente.setAutorId(autorId);
        libroDAO.actualizar(existente);
    }

    public void eliminarLibro(int id) {
        Libro existente = libroDAO.buscarPorId(id);
        if (existente == null) {
            throw new DatoInvalidoException("No existe ningún libro con el ID: " + id);
        }
        libroDAO.eliminar(id);
    }

    public Libro buscarLibroPorId(int id) {
        return libroDAO.buscarPorId(id);
    }

    public List<Libro> listarLibros() {
        return libroDAO.listarTodos();
    }

    public List<Libro> buscarLibros(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            throw new DatoInvalidoException("Debe ingresar un texto para realizar la búsqueda.");
        }
        return libroDAO.buscarPorTituloOAutor(criterio);
    }

    private void validarDatosLibro(String isbn, String titulo, int anio, double precio, int autorId, Integer idLibroActual) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new DatoInvalidoException("El ISBN es obligatorio.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DatoInvalidoException("El título del libro es obligatorio.");
        }
        int anioActual = Year.now().getValue();
        if (anio < 1450 || anio > anioActual + 1) {
            throw new DatoInvalidoException("El año de publicación debe estar entre 1450 y " + (anioActual + 1) + ".");
        }
        if (precio <= 0) {
            throw new DatoInvalidoException("El precio debe ser mayor que cero.");
        }
        Autor autor = autorDAO.buscarPorId(autorId);
        if (autor == null) {
            throw new DatoInvalidoException("El autor con ID " + autorId + " no existe. Registre primero el autor.");
        }

        Libro libroConMismoIsbn = libroDAO.buscarPorIsbn(isbn.trim());
        if (libroConMismoIsbn != null) {
            if (idLibroActual == null || libroConMismoIsbn.getId() != idLibroActual) {
                throw new DatoInvalidoException("Ya existe un libro registrado con el ISBN: " + isbn.trim());
            }
        }
    }
}
