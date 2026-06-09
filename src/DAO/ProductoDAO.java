/*
 * Clase DAO (Data Access Object)
 * 
 * Esta clase se encarga de realizar todas las operaciones
 * relacionadas con la base de datos para la entidad Producto.
 */
package DAO;

import Conexion.ConexionDB;
import Modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ProductoDAO
 *
 * Contiene los métodos CRUD: - Crear productos - Leer/Listar productos -
 * Actualizar productos - Eliminar productos
 *
 * Utiliza SQLite mediante JDBC.
 *
 * @author ACER
 */
public class ProductoDAO {

    /**
     * Inserta un nuevo producto en la base de datos.
     *
     * @param p -> Objeto Producto con los datos a guardar
     * @return true si el registro fue exitoso
     */
    public boolean agregar(Producto p) {

        // Consulta SQL para insertar datos
        String sql = "INSERT INTO producto(nombre,categoria,precio,cantidad) VALUES(?,?,?,?)";

        // Try-with-resources:
        // Cierra automáticamente la conexión y el PreparedStatement
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignación de valores a los parámetros SQLÑ
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCantidad());

            // Ejecuta la inserción
            ps.executeUpdate();
         
            return true;

        } catch (Exception e) {

            // Muestra error en consola
            System.out.println("Error agregar: " + e);
            return false;
        }
    }

    /**
     * Obtiene todos los productos registrados.
     *
     * @return Lista de productos
     */
    public List<Producto> listar() {

        // Lista donde se almacenarán los productos
        List<Producto> lista = new ArrayList<>();

        // Consulta SQL
        String sql = "SELECT * FROM producto";

        try (Connection con = ConexionDB.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            // Recorre cada fila obtenida de la consulta
            while (rs.next()) {

                // Se crea un objeto Producto por cada registro
                Producto p = new Producto();

                // Se asignan los datos del ResultSet al objeto
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setCategoria(rs.getString("categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setCantidad(rs.getInt("cantidad"));

                // Se agrega el producto a la lista
                lista.add(p);
            }

        } catch (Exception e) {

            System.out.println("Error listar: " + e);
        }

        // Retorna la lista completa
        return lista;
    }

    /**
     * Actualiza la información de un producto existente.
     *
     * @param p -> Producto con los nuevos datos
     * @return true si la actualización fue exitosa
     */
    public boolean actualizar(Producto p) {

        // Consulta SQL para actualizar
        String sql = "UPDATE producto SET nombre=?, categoria=?, precio=?, cantidad=? WHERE id=?";

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Se reemplazan los parámetros
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getCantidad());
            ps.setInt(5, p.getId());

            // Ejecuta la actualización
            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error actualizar: " + e);

            return false;
        }
    }

    /**
     * Elimina un producto según su ID.
     *
     * @param id -> Identificador del producto
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(int id) {

        // Consulta SQL para eliminar
        String sql = "DELETE FROM producto WHERE id=?";

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Reemplaza el parámetro ID
            ps.setInt(1, id);

            // Ejecuta eliminación
            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error eliminar: " + e);

            return false;
        }
    }
}
