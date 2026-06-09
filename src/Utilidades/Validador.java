/*
 * Clase Validador
 * 
 * Esta clase contiene métodos estáticos utilizados
 * para validar información ingresada por el usuario.
 * 
 * Se usa principalmente para:
 * - Verificar campos vacíos
 * - Validar números
 */
package Utilidades;

/**
 * Clase utilitaria para validaciones
 *
 * Los métodos son estáticos porque no es necesario crear objetos de esta clase
 * para utilizarlos.
 *
 * Ejemplo: Validador.esNumero("123");
 *
 * @author ACER
 */
public class Validador {

    /**
     * Verifica si alguno de los campos está vacío
     *
     * @param nombre Campo nombre
     * @param categoria Campo categoría
     * @param precio Campo precio
     * @param cantidad Campo cantidad
     *
     * @return true si algún campo está vacío
     */
    public static boolean camposVacios(
            String nombre,
            String categoria,
            String precio,
            String cantidad
    ) {

        /*
         * isEmpty():
         * Retorna true si el texto está vacío ("")
         */
        return nombre.isEmpty()
                || categoria.isEmpty()
                || precio.isEmpty()
                || cantidad.isEmpty();
    }

    /**
     * Verifica si un texto puede convertirse a número
     *
     * @param texto Cadena a validar
     *
     * @return true si es un número válido
     */
    public static boolean esNumero(String texto) {

        try {

            /*
             * Intenta convertir el texto a número decimal
             * 
             * Si funciona:
             * retorna true
             */
            Double.parseDouble(texto);

            return true;

        } catch (NumberFormatException e) {

            /*
             * Si ocurre error:
             * significa que no es un número válido
             */
            return false;
        }
    }
}
