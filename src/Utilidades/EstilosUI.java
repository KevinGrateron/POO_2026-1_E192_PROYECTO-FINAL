/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.Graphics;

/**
 *
 * @author ACER
 */
public class EstilosUI {
    // ─── PALETA DE COLORES ────────────────────────────────────────────────────

    public static final Color COLOR_PRIMARIO = new Color(25, 118, 210);   // Azul
    public static final Color COLOR_PRIMARIO_DARK = new Color(13, 71, 161);
    public static final Color COLOR_PELIGRO = new Color(211, 47, 47);  // Rojo
    public static final Color COLOR_PELIGRO_DARK = new Color(183, 28, 28);
    public static final Color COLOR_EXITO = new Color(46, 125, 50);  // Verde
    public static final Color COLOR_EXITO_DARK = new Color(27, 94, 32);
    public static final Color COLOR_ADVERTENCIA = new Color(245, 124, 0);  // Naranja
    public static final Color COLOR_ADVERTENCIA_DARK = new Color(230, 81, 0);
    public static final Color COLOR_NEUTRO = new Color(97, 97, 97);   // Gris
    public static final Color COLOR_NEUTRO_DARK = new Color(66, 66, 66);
    public static final Color COLOR_FONDO = new Color(245, 245, 245);
    public static final Color COLOR_FONDO_PANEL = Color.WHITE;
    public static final Color COLOR_TABLA_HEADER = new Color(33, 33, 33);
    public static final Color COLOR_TABLA_FILA1 = Color.WHITE;
    public static final Color COLOR_TABLA_FILA2 = new Color(232, 240, 254);
    public static final Color COLOR_TEXTO_HEADER = Color.WHITE;
    public static final Color COLOR_BORDE = new Color(207, 216, 220);
    public static final Color COLOR_RESUMEN = new Color(227, 242, 253);

    // ─── FUENTES ──────────────────────────────────────────────────────────────
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_LABEL = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_TABLA = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_HEADER = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_RESUMEN = new Font("Segoe UI", Font.BOLD, 13);

    // ─── MÉTODOS DE CONSTRUCCIÓN ─────────────────────────────────────────────
    /**
     * Crea un botón con estilo uniforme.
     *
     * @param texto Texto del botón
     * @param fondo Color de fondo
     * @param oscuro Color hover/press
     */
    public static JButton crearBoton(String texto, Color fondo, Color oscuro) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(
                        getModel().isPressed() ? oscuro
                        : getModel().isRollover() ? fondo.darker()
                        : fondo
                );

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                g2.dispose();

                super.paintComponent(g);
            }
        };
        btn.setFont(FUENTE_BOTON);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getDefaultCursor().getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setMargin(new Insets(6, 14, 6, 14));
        return btn;
    }

    /**
     * Campo de texto estilizado
     */
    public static JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_CAMPO);
        campo.setPreferredSize(new Dimension(0, 32));
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(4, 8, 4, 8)));
        return campo;
    }

    /**
     * Campo numérico (solo dígitos y punto decimal)
     */
    public static JTextField crearCampoNumerico() {
        JTextField campo = crearCampoTexto();
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != '\b') {
                    e.consume();
                }
            }
        });
        return campo;
    }

    /**
     * Estiliza el encabezado de una JTable
     */
    public static void estilizarTabla(JTable tabla) {
        tabla.setFont(FUENTE_TABLA);
        tabla.setRowHeight(32);
        tabla.setSelectionBackground(new Color(187, 222, 251));
        tabla.setSelectionForeground(Color.BLACK);
        tabla.setGridColor(COLOR_BORDE);
        tabla.setShowGrid(true);
        tabla.setIntercellSpacing(new Dimension(1, 1));
        tabla.setFillsViewportHeight(true);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(FUENTE_HEADER);
        header.setBackground(COLOR_TABLA_HEADER);
        header.setForeground(COLOR_TEXTO_HEADER);
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        // Filas alternadas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? COLOR_TABLA_FILA1 : COLOR_TABLA_FILA2);
                }
                setBorder(new EmptyBorder(0, 8, 0, 8));
                return this;
            }
        });
    }

    /**
     * Panel con sombra suave y bordes redondeados
     */
    public static JPanel crearPanelTarjeta() {
        JPanel p = new JPanel();
        p.setBackground(COLOR_FONDO_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(16, 16, 16, 16)));
        return p;
    }

    /**
     * Panel de resumen / pie de tabla
     */
    public static JPanel crearPanelResumen() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 8));
        p.setBackground(COLOR_RESUMEN);
        p.setBorder(new LineBorder(new Color(144, 202, 249), 1, true));
        return p;
    }

    /**
     * Etiqueta de sección con línea separadora
     */
    public static JLabel crearLabelSeccion(String texto) {
        JLabel lbl = new JLabel("  " + texto);
        lbl.setFont(FUENTE_SUBTITULO);
        lbl.setForeground(COLOR_PRIMARIO);
        lbl.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, COLOR_PRIMARIO));
        return lbl;
    }
}
