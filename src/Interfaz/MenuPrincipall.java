/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import Utilidades.EstilosUI;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ACER
 */
public class MenuPrincipall extends JFrame{

    private static final String NOMBRE_SISTEMA = "Sistema de Inventario";
    private static final String VERSION = "v1.0.0";

    private JLabel lblReloj;

    // ══════════════════════════════════════════════════════════════════════════
    public MenuPrincipall() {
        super(NOMBRE_SISTEMA);
        setSize(900, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // controlamos el cierre
        setLocationRelativeTo(null);

        buildUI();
        iniciarReloj();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                accionSalir();
            }
        });
    }

    // ══════════════════════════════════════════════════════════════════════════
    //   UI
    // ══════════════════════════════════════════════════════════════════════════
    private void buildUI() {
        getContentPane().setBackground(EstilosUI.COLOR_FONDO);
        setLayout(new BorderLayout());

        setJMenuBar(crearMenuBar());
        add(crearPanelTitulo(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearBarraEstado(), BorderLayout.SOUTH);
    }

    // ── Barra de título interna ───────────────────────────────────────────────
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, EstilosUI.COLOR_PRIMARIO_DARK,
                        getWidth(), 0, EstilosUI.COLOR_PRIMARIO);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setPreferredSize(new Dimension(0, 64));
        panel.setBorder(new EmptyBorder(0, 24, 0, 24));

        // Lado izquierdo: ícono + nombre
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        izquierda.setOpaque(false);

        JLabel ico = new JLabel("📦");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 0));
        textos.setOpaque(false);

        JLabel nombre = new JLabel(NOMBRE_SISTEMA);
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nombre.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Control de Productos & Proveedores  ·  " + VERSION);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sub.setForeground(new Color(180, 210, 255));

        textos.add(nombre);
        textos.add(sub);
        izquierda.add(ico);
        izquierda.add(textos);

        // Lado derecho: reloj
        lblReloj = new JLabel();
        lblReloj.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblReloj.setForeground(new Color(200, 225, 255));

        panel.add(izquierda, BorderLayout.WEST);
        panel.add(lblReloj, BorderLayout.EAST);
        return panel;
    }

    // ── Panel central: tarjetas de acceso rápido ──────────────────────────────
    private JPanel crearPanelCentral() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(EstilosUI.COLOR_FONDO);
        wrapper.setBorder(new EmptyBorder(30, 40, 20, 40));

        // Mensaje de bienvenida
        JLabel bienvenida = new JLabel("¿Qué deseas gestionar hoy?");
        bienvenida.setFont(new Font("Segoe UI", Font.BOLD, 22));
        bienvenida.setForeground(EstilosUI.COLOR_PRIMARIO_DARK);
        bienvenida.setBorder(new EmptyBorder(0, 0, 24, 0));
        wrapper.add(bienvenida, BorderLayout.NORTH);

        // Grid de tarjetas
        JPanel grid = new JPanel(new GridLayout(1, 3, 20, 20));
        grid.setOpaque(false);

        grid.add(crearTarjeta("📦", "Productos",
                "Gestiona el catálogo, precios\ny existencias del inventario.",
                EstilosUI.COLOR_PRIMARIO, () -> new prueba().setVisible(true)));

        grid.add(crearTarjeta("🏭", "Proveedores",
                "Administra los proveedores\ny sus datos de contacto.",
                EstilosUI.COLOR_EXITO, () -> new Proveedorr().setVisible(true)));

        grid.add(crearTarjeta("👤", "Usuarios",
                "Gestiona los usuarios\ncon acceso al sistema.",
                EstilosUI.COLOR_ADVERTENCIA,
                () -> JOptionPane.showMessageDialog(this,
                        "Módulo de Usuarios en desarrollo.", "Próximamente",
                        JOptionPane.INFORMATION_MESSAGE)));

        wrapper.add(grid, BorderLayout.CENTER);
        return wrapper;
    }

    /**
     * Construye una tarjeta de acceso rápido.
     */
    private JPanel crearTarjeta(String emoji, String titulo, String descripcion,
            Color color, Runnable accion) {
        JPanel card = new JPanel(new GridBagLayout()) {
            boolean hover = false;

            {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        hover = true;
                        repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        hover = false;
                        repaint();
                        setCursor(Cursor.getDefaultCursor());
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        accion.run();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hover ? color.darker() : Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                // Acento de color en la parte superior
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), 8, 8, 8);
                g2.fillRect(0, 4, getWidth(), 4);

                // Sombra suave
                if (hover) {
                    g2.setColor(new Color(0, 0, 0, 30));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 16, 16);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setBorder(new CompoundBorder(
                new LineBorder(EstilosUI.COLOR_BORDE, 1, true),
                new EmptyBorder(28, 24, 28, 24)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel icoLabel = new JLabel(emoji, SwingConstants.CENTER);
        icoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 46));
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 0, 12, 0);
        card.add(icoLabel, gbc);

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);
        tituloLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        tituloLabel.setForeground(EstilosUI.COLOR_PRIMARIO_DARK);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(tituloLabel, gbc);

        JLabel descLabel = new JLabel("<html><center>" + descripcion.replace("\n", "<br>") + "</center></html>",
                SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(EstilosUI.COLOR_NEUTRO);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 16, 0);
        card.add(descLabel, gbc);

        JButton btn = EstilosUI.crearBoton("Abrir módulo", color, color.darker());
        btn.addActionListener(e -> accion.run());
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 0, 20);
        card.add(btn, gbc);

        return card;
    }

    // ── Barra de estado inferior ──────────────────────────────────────────────
    private JPanel crearBarraEstado() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 6));
        panel.setBackground(new Color(33, 33, 33));
        panel.setBorder(new MatteBorder(1, 0, 0, 0, new Color(60, 60, 60)));

        JLabel estado = new JLabel("●  Sistema activo");
        estado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        estado.setForeground(new Color(102, 187, 106)); // verde

        JLabel sep1 = new JLabel("|");
        sep1.setForeground(new Color(80, 80, 80));

        JLabel usuario = new JLabel("👤 admin");
        usuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        usuario.setForeground(new Color(180, 180, 180));

        JLabel sep2 = new JLabel("|");
        sep2.setForeground(new Color(80, 80, 80));

        JLabel version = new JLabel(VERSION);
        version.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        version.setForeground(new Color(120, 120, 120));

        panel.add(estado);
        panel.add(sep1);
        panel.add(usuario);
        panel.add(sep2);
        panel.add(version);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //   MENÚ BAR
    // ══════════════════════════════════════════════════════════════════════════
    private JMenuBar crearMenuBar() {
        JMenuBar barra = new JMenuBar();
        barra.setBackground(new Color(30, 30, 30));
        barra.setBorder(new MatteBorder(0, 0, 1, 0, new Color(60, 60, 60)));

        barra.add(crearMenuTablas());
        barra.add(Box.createHorizontalGlue());
        barra.add(crearMenuOpciones());
        return barra;
    }

    private JMenu crearMenuTablas() {
        JMenu menu = crearMenuEstilizado("📋  Tablas");

        menu.add(crearMenuItem("📦  Productos", () -> new InterfazProducto().setVisible(true)));
        menu.add(crearMenuItem("🏭  Proveedores", () -> new InterfazProveedor().setVisible(true)));
        menu.addSeparator();
        menu.add(crearMenuItem("👤  Usuarios",
                () -> JOptionPane.showMessageDialog(this,
                        "Módulo de Usuarios en desarrollo.", "Próximamente",
                        JOptionPane.INFORMATION_MESSAGE)));
        return menu;
    }

    private JMenu crearMenuOpciones() {
        JMenu menu = crearMenuEstilizado("⚙  Opciones");
        menu.add(crearMenuItem("🚪  Cerrar sesión", this::cerrarSesion));
        menu.add(crearMenuItem("❌  Salir", this::accionSalir));
        return menu;
    }

    // ── Helpers para estilizar menús ─────────────────────────────────────────
    private JMenu crearMenuEstilizado(String texto) {
        JMenu menu = new JMenu(texto);
        menu.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        menu.setForeground(new Color(220, 220, 220));
        menu.setBorder(new EmptyBorder(0, 8, 0, 8));
        menu.getPopupMenu().setBorder(new LineBorder(EstilosUI.COLOR_BORDE, 1));
        return menu;
    }

    private JMenuItem crearMenuItem(String texto, Runnable accion) {
        JMenuItem item = new JMenuItem(texto);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.setBackground(Color.WHITE);
        item.setBorder(new EmptyBorder(8, 16, 8, 24));
        item.addActionListener(e -> accion.run());
        return item;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //   ACCIONES
    // ══════════════════════════════════════════════════════════════════════════
    private void accionSalir() {
        int resp = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas salir del sistema?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (resp == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void cerrarSesion() {
        int resp = JOptionPane.showConfirmDialog(this,
                "¿Deseas cerrar sesión y volver al inicio?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (resp == JOptionPane.YES_OPTION) {
            dispose();
            new Loginn();
        }
    }

    // ── Reloj en tiempo real ──────────────────────────────────────────────────
    private void iniciarReloj() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy   HH:mm:ss");
        Timer timer = new Timer(1000, e
                -> lblReloj.setText(LocalDateTime.now().format(fmt) + "  "));
        timer.setInitialDelay(0);
        timer.start();
    }

    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
