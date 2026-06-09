/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author ACER
 */
public class ConfirmarActualizarDialog extends JDialog {
    private boolean confirmado = false;
 
    /**
     * @param padre          Ventana padre
     * @param entidad        Nombre del tipo de entidad, ej: "Producto"
     * @param nombreRegistro Nombre/identificador del registro a actualizar
     */
    public ConfirmarActualizarDialog(Frame padre, String entidad, String nombreRegistro) {
        super(padre, "Confirmar actualización", true);
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
 
        // ── Franja naranja superior ────────────────────────────────────────────
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 16));
        header.setBackground(EstilosUI.COLOR_ADVERTENCIA);
 
        JLabel icono = new JLabel("✏️");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        icono.setForeground(Color.WHITE);
 
        JLabel titulo = new JLabel("Actualizar " + entidad);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
 
        header.add(icono);
        header.add(titulo);
        root.add(header, BorderLayout.NORTH);
 
        // ── Cuerpo ─────────────────────────────────────────────────────────────
        JPanel cuerpo = new JPanel(new BorderLayout(0, 16));
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setBorder(new EmptyBorder(20, 24, 10, 24));
 
        JLabel pregunta = new JLabel("<html>¿Deseas guardar los cambios realizados en este registro?</html>");
        pregunta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pregunta.setForeground(new Color(50, 50, 50));
        cuerpo.add(pregunta, BorderLayout.NORTH);
 
        // Tarjeta con el nombre
        JPanel tarjeta = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        tarjeta.setBackground(new Color(255, 243, 224));
        tarjeta.setBorder(new CompoundBorder(
                new LineBorder(new Color(255, 204, 128), 1, true),
                new EmptyBorder(4, 8, 4, 8)));
 
        JLabel lblEntidad = new JLabel(entidad + ": ");
        lblEntidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblEntidad.setForeground(EstilosUI.COLOR_ADVERTENCIA);
 
        JLabel lblNombre = new JLabel(nombreRegistro);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(EstilosUI.COLOR_ADVERTENCIA_DARK);
 
        tarjeta.add(lblEntidad);
        tarjeta.add(lblNombre);
        cuerpo.add(tarjeta, BorderLayout.CENTER);
 
        JLabel nota = new JLabel("ℹ  Los datos anteriores serán reemplazados.");
        nota.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        nota.setForeground(new Color(150, 90, 0));
        cuerpo.add(nota, BorderLayout.SOUTH);
 
        root.add(cuerpo, BorderLayout.CENTER);
 
        // ── Botones ────────────────────────────────────────────────────────────
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        botones.setBackground(new Color(250, 250, 250));
        botones.setBorder(new MatteBorder(1, 0, 0, 0, EstilosUI.COLOR_BORDE));
 
        JButton btnCancelar    = EstilosUI.crearBoton("Cancelar",        EstilosUI.COLOR_NEUTRO,      EstilosUI.COLOR_NEUTRO_DARK);
        JButton btnActualizar  = EstilosUI.crearBoton("Sí, actualizar",  EstilosUI.COLOR_ADVERTENCIA, EstilosUI.COLOR_ADVERTENCIA_DARK);
 
        btnCancelar.addActionListener  (e -> dispose());
        btnActualizar.addActionListener(e -> { confirmado = true; dispose(); });
 
        // ESC cancela
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
 
        getRootPane().setDefaultButton(btnActualizar); // Enter confirma (el usuario ya revisó los datos)
 
        botones.add(btnCancelar);
        botones.add(btnActualizar);
        root.add(botones, BorderLayout.SOUTH);
    }
 
    /** @return true si el usuario confirmó la actualización */
    public boolean fueConfirmado() {
        return confirmado;
    }

}
