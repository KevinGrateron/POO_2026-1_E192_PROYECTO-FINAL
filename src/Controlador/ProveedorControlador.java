/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.ProveedorDAO;
import Interfaz.InterfazProveedor; 
import Modelo.Proveedor;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author ACER
 */
public class ProveedorControlador {
    
    private ProveedorDAO dao = new ProveedorDAO();
    private InterfazProveedor vista; 

   
    public ProveedorControlador(InterfazProveedor vista) {
        this.vista = vista;
    }

    /**
     * 
     */
    public void registrarProveedorDesdeVista() {
        try {
            // Validamos que los campos obligatorios no estén vacíos
            if (vista.txtNit.getText().isEmpty() || vista.txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, llene los campos obligatorios (NIT y Nombre)");
                return;
            }

            // Capturamos los datos directamente de los JTextField de la vista
            int nit = Integer.parseInt(vista.txtNit.getText().trim());
            String nombre = vista.txtNombre.getText().trim();
            String telefono = vista.txtTelefono.getText().trim();
            String direccion = vista.txtDireccion.getText().trim();
            String ciudad = vista.txtCiudad.getText().trim();

            // Se pasan los parámetros en el orden exacto del constructor de Proveedor
            Proveedor p = new Proveedor(nit, nombre, telefono, direccion, ciudad);

            // Se lo mandamos al DAO
            if (guardarProveedor(p)) {
                JOptionPane.showMessageDialog(vista, "¡Proveedor registrado con éxito!");
                
                // Actualizamos la tabla al instante
                vista.cargarTablaProveedores();
                
                // Limpiamos las casillas de texto para un nuevo registro
                limpiarCamposVista();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar en la base de datos.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El campo NIT debe ser un número válido sin puntos ni espacios.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error al registrar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     *  Recolecta los datos de la vista y modifica el registro por su NIT
     */
    public void modificarProveedorDesdeVista() {
        try {
            if (vista.txtNit.getText().isEmpty() || vista.txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, seleccione o ingrese el NIT y Nombre del proveedor a modificar.");
                return;
            }

            int nit = Integer.parseInt(vista.txtNit.getText().trim());
            String nombre = vista.txtNombre.getText().trim();
            String telefono = vista.txtTelefono.getText().trim();
            String direccion = vista.txtDireccion.getText().trim();
            String ciudad = vista.txtCiudad.getText().trim();

            //  Se pasan los parámetros en el orden exacto del constructor de Proveedor
            Proveedor p = new Proveedor(nit, nombre, telefono, direccion, ciudad);

            if (actualizarProveedor(p)) {
                JOptionPane.showMessageDialog(vista, "¡Proveedor modificado con éxito!");
                vista.cargarTablaProveedores(); 
                limpiarCamposVista();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al modificar en la base de datos.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El campo NIT debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error al modificar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Captura el NIT de la vista y elimina el proveedor
     */
    public void eliminarProveedorDesdeVista() {
        try {
            if (vista.txtNit.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingrese el NIT del proveedor que desea eliminar.");
                return;
            }

            int nit = Integer.parseInt(vista.txtNit.getText().trim());

            // Pregunta de confirmación al usuario
            int confirmar = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este proveedor?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmar == JOptionPane.YES_OPTION) {
                if (eliminarProveedor(nit)) {
                    JOptionPane.showMessageDialog(vista, "¡Proveedor eliminado con éxito!");
                    vista.cargarTablaProveedores(); 
                    limpiarCamposVista();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar: Verifique si el NIT existe en la base de datos.");
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "El campo NIT debe ser un número válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error al eliminar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Método auxiliar para limpiar los JTextFields de la vista fácilmente
     */
    public void limpiarCamposVista() {
        vista.txtNit.setText("");
        vista.txtNombre.setText("");
        vista.txtTelefono.setText("");
        vista.txtDireccion.setText("");
        vista.txtCiudad.setText("");
    }

    
    public boolean guardarProveedor(Proveedor p) {
        return dao.registrarProveedor(p);
    }

    public List<Proveedor> obtenerProveedores() {
        return dao.listarProveedores();
    }

    public boolean actualizarProveedor(Proveedor p) {
        return dao.modificarProveedor(p);
    }

    public boolean eliminarProveedor(int id) {
        return dao.eliminarProveedor(id);
    }
}