
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author ELIZ
 */
import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO{
   
    @Override
    public void insertar(Autor autor) {
        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    autor.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al insertar autor: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Autor autor) {
        String sql = "UPDATE autores SET nombre = ?, nacionalidad = ? WHERE id = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.setInt(3, autor.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al actualizar autor: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM autores WHERE id = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatosException("Error al eliminar autor: " + e.getMessage(), e);
        }
    }

    @Override
    public Autor buscarPorId(int id) {
        String sql = "SELECT id, nombre, nacionalidad FROM autores WHERE id = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAutor(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al buscar autor por ID: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Autor> listarTodos() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, nacionalidad FROM autores ORDER BY id ASC";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearAutor(rs));
            }
        } catch (SQLException e) {
            throw new DatosException("Error al listar autores: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public int contarLibrosPorAutor(int autorId) {
        String sql = "SELECT COUNT(*) FROM libros WHERE autor_id = ?";
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, autorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DatosException("Error al contar libros del autor: " + e.getMessage(), e);
        }
        return 0;
    }

    private Autor mapearAutor(ResultSet rs) throws SQLException {
        return new Autor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nacionalidad")
        );
    }
}
