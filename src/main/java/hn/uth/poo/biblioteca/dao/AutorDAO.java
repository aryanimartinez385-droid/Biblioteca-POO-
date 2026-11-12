/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;

/**
 *
 * @author ELIZ
 */
import hn.uth.poo.biblioteca.modelo.Autor;
import java.util.List;

public interface AutorDAO {
    void insertar(Autor autor);
    void actualizar(Autor autor);
    void eliminar(int id);
    Autor buscarPorId(int id);
    List<Autor> listarTodos();
    int contarLibrosPorAutor(int autorId);
}
