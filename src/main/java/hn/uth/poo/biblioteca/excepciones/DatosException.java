
package hn.uth.poo.biblioteca.excepciones;

/**
 *
 * @author ELIZ
 */
public class DatosException extends RuntimeException{
    public DatosException(String mensaje) {
        super(mensaje);
    }

    public DatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
