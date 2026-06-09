/*
 * Clase encargada de realizar la conexión con la base de datos SQLite.
 * 
 * Esta clase utiliza JDBC para conectarse al archivo inventario.db
 * ubicado en la ruta especificada.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase ConexionDB
 *
 * Contiene un método estático para establecer la conexión con la base de datos.
 *
 * @author ACER
 */
public class ConexionDB {

    /**
     * Método que realiza la conexión con la base de datos.
     *
     * @return Connection -> Retorna el objeto de conexión si fue exitosa, o
     * null si ocurrió un error.
     */
    public static Connection conectar() {

        // Variable donde se almacenará la conexión
        Connection con = null;

        try {

            // Establece conexión con la base de datos SQLite
            con = DriverManager.getConnection(
                    "jdbc:sqlite:C:inventario.db"
            );

            // Mensaje opcional para verificar conexión exitosa
            // System.out.println("Conexión exitosa");
        } catch (SQLException e) {

            // Captura errores relacionados con la conexión
            System.out.println("Error conexion: " + e.getMessage());

        }

        // Retorna la conexión creada
        return con;
    }
}
