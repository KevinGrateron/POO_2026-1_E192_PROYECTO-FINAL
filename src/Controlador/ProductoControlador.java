/*
 * Clase controladora encargada de conectar la vista
 * con las operaciones de la base de datos.
 */
package Controlador;

import DAO.ProductoDAO;
import Modelo.Producto;
import java.util.List;

/**
 * Clase ProductoControlador
 *
 * Esta clase actúa como intermediaria entre la interfaz gráfica (Vista) y la
 * capa de acceso a datos (DAO).
 *
 * Desde aquí se llaman los métodos para: - Guardar productos - Obtener
 * productos - Actualizar productos - Eliminar productos
 *
 * @author ACER
 */
public class ProductoControlador {

    // Objeto DAO encargado de comunicarse con la base de datos
    ProductoDAO dao = new ProductoDAO();

    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * @param p -> Objeto Producto con los datos a guardar
     * @return true si se guardó correctamente, false si ocurrió un error
     */
    public boolean guardarProducto(Producto p) {
        return dao.agregar(p);
    }

    /**
     * Obtiene la lista de todos los productos registrados.
     *
     * @return Lista de productos
     */
    public List<Producto> obtenerProductos() {
        return dao.listar();
    }

    /**
     * Actualiza la información de un producto existente.
     *
     * @param p -> Producto con los nuevos datos
     * @return true si se actualizó correctamente
     */
    public boolean actualizarProducto(Producto p) {
        return dao.actualizar(p);
    }

    /**
     * Elimina un producto según su ID.
     *
     * @param id -> Identificador del producto
     * @return true si se eliminó correctamente
     */
    public boolean eliminarProducto(int id) {
        return dao.eliminar(id);
    }
}
