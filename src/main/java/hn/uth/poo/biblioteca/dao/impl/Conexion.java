
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author ELIZ
 */
import hn.uth.poo.biblioteca.excepciones.DatosException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    public static Connection obtenerConexion() {
        try {
            Connection con = DriverManager.getConnection(URL);
            try (Statement st = con.createStatement()) {
                st.execute("PRAGMA foreign_keys = ON;");
            }
            return con;
        } catch (SQLException e) {
            throw new DatosException("Error al conectar con la base de datos SQLite.", e);
        }
    }

    public static void inicializarBaseDatos() {
        String sqlAutores = """
                CREATE TABLE IF NOT EXISTS autores (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    nacionalidad TEXT
                );
                """;

        String sqlLibros = """
                CREATE TABLE IF NOT EXISTS libros (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    isbn TEXT NOT NULL UNIQUE,
                    titulo TEXT NOT NULL,
                    anio INTEGER,
                    precio REAL NOT NULL,
                    autor_id INTEGER NOT NULL,
                    FOREIGN KEY (autor_id) REFERENCES autores(id)
                );
                """;

        try (Connection con = obtenerConexion();
             Statement st = con.createStatement()) {
            st.execute(sqlAutores);
            st.execute(sqlLibros);
        } catch (SQLException e) {
            throw new DatosException("Error al inicializar las tablas de la base de datos.", e);
        }
    }
}
