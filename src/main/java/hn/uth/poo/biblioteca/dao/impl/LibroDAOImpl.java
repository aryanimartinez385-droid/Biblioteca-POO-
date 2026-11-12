package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author ELIZ
 */
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAOImpl implements LibroDAO{
    
    @Override
    public void insertar(Libro libro) {
        String sql = "INSERT INTO libros (isbn, titulo, anio, precio, autor_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutorId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al insertar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Libro libro) {
        String sql = "UPDATE libros SET isbn = ?, titulo = ?, anio = ?, precio = ?, autor_id = ? WHERE id = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setInt(3, libro.getAnio());
            ps.setDouble(4, libro.getPrecio());
            ps.setInt(5, libro.getAutorId());
            ps.setInt(6, libro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al actualizar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al eliminar libro: " + e.getMessage(), e);
        }
    }

    @Override
    public Libro buscarPorId(int id) {
        String sql = """
                SELECT l.id, l.isbn, l.titulo, l.anio, l.precio, l.autor_id, a.nombre AS autor_nombre
                FROM libros l
                INNER JOIN autores a ON l.autor_id = a.id
                WHERE l.id = ?
                """;
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearLibro(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar libro por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Libro buscarPorIsbn(String isbn) {
        String sql = """
                SELECT l.id, l.isbn, l.titulo, l.anio, l.precio, l.autor_id, a.nombre AS autor_nombre
                FROM libros l
                INNER JOIN autores a ON l.autor_id = a.id
                WHERE LOWER(l.isbn) = LOWER(?)
                """;
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearLibro(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar libro por ISBN: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Libro> listarTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = """
                SELECT l.id, l.isbn, l.titulo, l.anio, l.precio, l.autor_id, a.nombre AS autor_nombre
                FROM libros l
                INNER JOIN autores a ON l.autor_id = a.id
                ORDER BY l.id ASC
                """;
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar libros: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public List<Libro> buscarPorTituloOAutor(String criterio) {
        List<Libro> lista = new ArrayList<>();
        String sql = """
                SELECT l.id, l.isbn, l.titulo, l.anio, l.precio, l.autor_id, a.nombre AS autor_nombre
                FROM libros l
                INNER JOIN autores a ON l.autor_id = a.id
                WHERE LOWER(l.titulo) LIKE LOWER(?) OR LOWER(a.nombre) LIKE LOWER(?)
                ORDER BY l.id ASC
                """;
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String filtro = "%" + criterio.trim() + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLibro(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar libros por criterio: " + e.getMessage(), e);
        }
        return lista;
    }
@Override
    public List<Libro> listarPorAutor(int autorId) {
        List<Libro> lista = new ArrayList<>();
        String sql = """
                SELECT l.id, l.isbn, l.titulo, l.anio, l.precio, l.autor_id, a.nombre AS autor_nombre
                FROM libros l
                INNER JOIN autores a ON l.autor_id = a.id
                WHERE l.autor_id = ?
                ORDER BY l.id ASC
                """;
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, autorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearLibro(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar libros por autor: " + e.getMessage(), e);
        }
        return lista;
    }
    private Libro mapearLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro(
                rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("titulo"),
                rs.getInt("anio"),
                rs.getDouble("precio"),
                rs.getInt("autor_id")
        );
        libro.setAutorNombre(rs.getString("autor_nombre"));
        return libro;
    }
}
