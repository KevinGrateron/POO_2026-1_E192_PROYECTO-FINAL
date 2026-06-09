/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import javax.swing.JDialog;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;


/**
 *
 * @author ACER
 */
public class ConfirmarBorrarDialog extends JDialog {
    private boolean confirmado = false;
 
    /**
     * @param padre        Ventana padre (para centrar)
     * @param entidad      Nombre del tipo de entidad, ej: "Producto"
     * @param nombreRegistro Nombre/identificador del registro a borrar
     */
    public ConfirmarBorrarDialog(Frame padre, String entidad, String nombreRegistro) {
        super(padre, "Confirmar eliminación", true); // modal
        setSize(440, 260);
        setResizable(false);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
 
        buildUI(entidad, nombreRegistro);
    }
 
    private void buildUI(String entidad, String nombreRegistro) {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(Color.WHITE);
        setContentPane(root);
 
        // ── Franja roja superior ───────────────────────────────────────────────
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 16));
        header.setBackground(EstilosUI.COLOR_PELIGRO);
 
        JLabel icono = new JLabel("🗑");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        icono.setForeground(Color.WHITE);
 
        JLabel titulo = new JLabel("Eliminar " + entidad);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
 
        header.add(icono);
        header.add(titulo);
        root.add(header, BorderLayout.NORTH);
 
        // ── Cuerpo ─────────────────────────────────────────────────────────────
        JPanel cuerpo = new JPanel(new BorderLayout(0, 16));
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setBorder(new EmptyBorder(20, 24, 10, 24));
 
        JLabel pregunta = new JLabel("<html>¿Estás seguro de que deseas eliminar este registro?</html>");
        pregunta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pregunta.setForeground(new Color(50, 50, 50));
        cuerpo.add(pregunta, BorderLayout.NORTH);
 
        // Tarjeta con el nombre del elemento a borrar
        JPanel tarjeta = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        tarjeta.setBackground(new Color(255, 235, 238));
        tarjeta.setBorder(new CompoundBorder(
                new LineBorder(new Color(239, 154, 154), 1, true),
                new EmptyBorder(4, 8, 4, 8)));
 
        JLabel lblEntidad = new JLabel(entidad + ": ");
        lblEntidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblEntidad.setForeground(EstilosUI.COLOR_PELIGRO);
 
        JLabel lblNombre = new JLabel(nombreRegistro);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(EstilosUI.COLOR_PELIGRO_DARK);
 
        tarjeta.add(lblEntidad);
        tarjeta.add(lblNombre);
        cuerpo.add(tarjeta, BorderLayout.CENTER);
 
        JLabel advertencia = new JLabel("⚠  Esta acción no se puede deshacer.");
        advertencia.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        advertencia.setForeground(new Color(180, 60, 60));
        cuerpo.add(advertencia, BorderLayout.SOUTH);
 
        root.add(cuerpo, BorderLayout.CENTER);
 
        // ── Botones ────────────────────────────────────────────────────────────
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        botones.setBackground(new Color(250, 250, 250));
        botones.setBorder(new MatteBorder(1, 0, 0, 0, EstilosUI.COLOR_BORDE));
 
        JButton btnCancelar = EstilosUI.crearBoton("Cancelar", EstilosUI.COLOR_NEUTRO, EstilosUI.COLOR_NEUTRO_DARK);
        JButton btnEliminar = EstilosUI.crearBoton("Sí, eliminar", EstilosUI.COLOR_PELIGRO, EstilosUI.COLOR_PELIGRO_DARK);
 
        btnCancelar.addActionListener(e -> dispose());
        btnEliminar.addActionListener(e -> { confirmado = true; dispose(); });
 
        // ESC para cancelar
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
 
        // Enter sobre "Sí, eliminar" solo si el foco está en ese botón
        getRootPane().setDefaultButton(btnCancelar); // cancelar es la acción por defecto (seguridad)
 
        botones.add(btnCancelar);
        botones.add(btnEliminar);
        root.add(botones, BorderLayout.SOUTH);
    }
 
    /** @return true si el usuario confirmó la eliminación */
    public boolean fueConfirmado() {
        return confirmado;
    }
}
