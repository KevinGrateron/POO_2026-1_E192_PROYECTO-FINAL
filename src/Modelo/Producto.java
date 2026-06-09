/*
 * Clase Producto
 * 
 * Esta clase representa la entidad Producto dentro
 * del sistema de inventario.
 * 
 * Aquí se almacenan los datos principales de cada producto.
 */
package Modelo;

/**
 * Clase Producto
 *
 * Funciona como modelo de datos para: - Guardar información - Transportar datos
 * entre capas - Representar registros de la base de datos
 *
 * Atributos: - id - nombre - categoria - precio - cantidad
 *
 * @author Kevin Grateron (Desarrollador, DBA) Edwin Gamez (Desarrollador, QA)
 * Caleb Enciso (Desarrollador, QA)
 */
public class Producto {

    /*
     * ====================================================
     * ATRIBUTOS DE LA CLASE
     * ====================================================
     */
    // Identificador único del producto
    private int id;

    // Nombre del producto
    private String nombre;

    // Categoría a la que pertenece
    private String categoria;

    // Precio del producto
    private double precio;

    // Cantidad disponible en inventario
    private int cantidad;

    /*
     * ====================================================
     * CONSTRUCTOR VACÍO
     * ====================================================
     * 
     * Permite crear objetos sin enviar parámetros.
     */
    public Producto() {
    }

    /*
     * ====================================================
     * CONSTRUCTOR CON PARÁMETROS
     * ====================================================
     * 
     * Permite crear un producto con todos sus datos.
     */
    public Producto(int id,
            String nombre,
            String categoria,
            double precio,
            int cantidad) {

        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    /*
     * ====================================================
     * GETTERS Y SETTERS
     * ====================================================
     * 
     * Getters:
     * Obtienen valores de atributos.
     * 
     * Setters:
     * Modifican valores de atributos.
     */
    /**
     * Obtiene ID del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna ID del producto
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna nombre del producto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene categoría del producto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Asigna categoría del producto
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Asigna precio del producto
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene cantidad del producto
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Asigna cantidad del producto
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
