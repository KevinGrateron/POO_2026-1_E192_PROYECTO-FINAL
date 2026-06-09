/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import Conexion.ConexionDB;
import Utilidades.EstilosUI;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

/**
 *
 * @author ACER
 */
public class Loginn extends JFrame{
    // ─── Componentes ──────────────────────────────────────────────────────────
    private JTextField     tfUsuario;
    private JPasswordField tfPassword;
    private JButton        btnLogin;
    private JLabel         lblError;
    private JCheckBox      chkMostrarPass;
 
    // ══════════════════════════════════════════════════════════════════════════
    public Loginn() {
        super("Iniciar sesión – Sistema de Inventario");
        setUndecorated(true);                      // sin barra de título del SO
        setSize(820, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setShape(new RoundRectangle2D.Double(0, 0, 820, 520, 20, 20)); // esquinas redondeadas
 
        buildUI();
        setVisible(true);
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   UI
    // ══════════════════════════════════════════════════════════════════════════
    private void buildUI() {
        JPanel root = new JPanel(new GridLayout(1, 2, 0, 0));
        root.setBorder(new LineBorder(EstilosUI.COLOR_BORDE, 1));
        setContentPane(root);
 
        root.add(crearPanelIzquierdo());
        root.add(crearPanelDerecho());
 
        // Arrastrar ventana sin barra de título
        DragListener drag = new DragListener(this);
        root.addMouseListener(drag);
        root.addMouseMotionListener(drag);
    }
 
    // ── Panel izquierdo: ilustración / marca ─────────────────────────────────
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
                // Fondo degradado azul profundo
                GradientPaint gp = new GradientPaint(
                        0, 0,          new Color(13, 71, 161),
                        getWidth(), getHeight(), new Color(25, 118, 210));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
 
                // Círculos decorativos
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillOval(-60, -60, 260, 260);
                g2.fillOval(getWidth() - 120, getHeight() - 120, 200, 200);
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillOval(80, getHeight() - 140, 160, 160);
            }
        };
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 30, 10, 30);
 
        // Ícono grande
        JLabel icono = new JLabel("📦", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        gbc.gridy = 0;
        panel.add(icono, gbc);
 
        // Nombre del sistema
        JLabel nombreSistema = new JLabel("Sistema de Inventario", SwingConstants.CENTER);
        nombreSistema.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nombreSistema.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(nombreSistema, gbc);
 
        // Subtítulo
        JLabel sub = new JLabel("<html><center>Control de Productos<br>& Proveedores</center></html>", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(180, 210, 255));
        gbc.gridy = 2;
        gbc.insets = new Insets(6, 30, 30, 30);
        panel.add(sub, gbc);
 
        // Versión
        JLabel version = new JLabel("v1.0.0", SwingConstants.CENTER);
        version.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        version.setForeground(new Color(140, 180, 230));
        gbc.gridy = 3;
        panel.add(version, gbc);
 
        return panel;
    }
 
    // ── Panel derecho: formulario ─────────────────────────────────────────────
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(6, 40, 6, 40);
 
        // Botón de cerrar (X) arriba a la derecha
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        topBar.setOpaque(false);
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnCerrar.setForeground(EstilosUI.COLOR_NEUTRO);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> System.exit(0));
        topBar.add(btnCerrar);
        gbc.gridy = 0; gbc.insets = new Insets(0, 0, 0, 4);
        panel.add(topBar, gbc);
        gbc.insets = new Insets(6, 40, 6, 40);
 
        // Título
        JLabel titulo = new JLabel("Bienvenido");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(EstilosUI.COLOR_PRIMARIO_DARK);
        gbc.gridy = 1;
        panel.add(titulo, gbc);
 
        JLabel subtitulo = new JLabel("Inicia sesión para continuar");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(EstilosUI.COLOR_NEUTRO);
        gbc.gridy = 2; gbc.insets = new Insets(2, 40, 20, 40);
        panel.add(subtitulo, gbc);
        gbc.insets = new Insets(6, 40, 6, 40);
 
        // ── Campo Usuario ──
        gbc.gridy = 3;
        panel.add(crearLabel("👤  Usuario"), gbc);
 
        tfUsuario = EstilosUI.crearCampoTexto();
        tfUsuario.setPreferredSize(new Dimension(0, 40));
        tfUsuario.setToolTipText("Ingresa tu usuario");
        gbc.gridy = 4;
        panel.add(tfUsuario, gbc);
 
        // ── Campo Contraseña ──
        gbc.gridy = 5; gbc.insets = new Insets(14, 40, 4, 40);
        panel.add(crearLabel("Contraseña"), gbc);
        gbc.insets = new Insets(6, 40, 6, 40);
 
        tfPassword = new JPasswordField();
        tfPassword.setFont(EstilosUI.FUENTE_CAMPO);
        tfPassword.setPreferredSize(new Dimension(0, 40));
        tfPassword.setBorder(EstilosUI.crearCampoTexto().getBorder());
        gbc.gridy = 6;
        panel.add(tfPassword, gbc);
 
        // Mostrar contraseña
        chkMostrarPass = new JCheckBox("Mostrar contraseña");
        chkMostrarPass.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chkMostrarPass.setOpaque(false);
        chkMostrarPass.setForeground(EstilosUI.COLOR_NEUTRO);
        chkMostrarPass.addActionListener(e ->
                tfPassword.setEchoChar(chkMostrarPass.isSelected() ? (char) 0 : '•'));
        gbc.gridy = 7; gbc.insets = new Insets(2, 36, 6, 40);
        panel.add(chkMostrarPass, gbc);
        gbc.insets = new Insets(6, 40, 6, 40);
 
        // Mensaje de error (oculto por defecto)
        lblError = new JLabel(" ");
        lblError.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblError.setForeground(EstilosUI.COLOR_PELIGRO);
        gbc.gridy = 8;
        panel.add(lblError, gbc);
 
        // Botón Login
        btnLogin = EstilosUI.crearBoton("Iniciar sesión", EstilosUI.COLOR_PRIMARIO, EstilosUI.COLOR_PRIMARIO_DARK);
        btnLogin.setPreferredSize(new Dimension(0, 42));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        gbc.gridy = 9; gbc.insets = new Insets(8, 40, 6, 40);
        panel.add(btnLogin, gbc);
 
        btnLogin.addActionListener(e -> login());
 
        // Enter en contraseña → login
        tfPassword.addActionListener(e -> login());
        tfUsuario.addActionListener(e -> tfPassword.requestFocus());
 
        return panel;
    }
 
    private JLabel crearLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(EstilosUI.FUENTE_LABEL);
        l.setForeground(new Color(60, 60, 60));
        return l;
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   LÓGICA DE AUTENTICACIÓN
    // ══════════════════════════════════════════════════════════════════════════
    private void login() {
        String user = tfUsuario.getText().trim();
        String pass = new String(tfPassword.getPassword()).trim();
 
        if (user.isEmpty() || pass.isEmpty()) {
            mostrarError("⚠  Usuario y contraseña son requeridos.");
            return;
        }
 
        btnLogin.setEnabled(false);
        btnLogin.setText("Verificando...");
 
        // Ejecutar en hilo separado para no congelar la UI
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                 //── DESCOMENTA ESTO Y CONECTA TU BD ──────────────────────────
                 String sql = "SELECT * FROM usuario WHERE usuario=? AND password=?";
                 try (Connection con = ConexionDB.conectar();
                      PreparedStatement ps = con.prepareStatement(sql)) {
                     ps.setString(1, user);
                     ps.setString(2, pass);
                     try (ResultSet rs = ps.executeQuery()) {
                         return rs.next();
                     }
                 }
 
                // ── DEMO: credenciales admin/admin ────────────────────────────
                //Thread.sleep(600); // simula consulta de red
                //return user.equals("Admin") && pass.equals("12345");
            }
 
            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (ok) {
                        dispose();
                        new MenuPrincipall().setVisible(true);
                    } else {
                        mostrarError("✕  Usuario o contraseña incorrectos.");
                        tfPassword.setText("");
                        tfPassword.requestFocus();
                    }
                } catch (Exception ex) {
                    mostrarError("Error de conexión: " + ex.getMessage());
                } finally {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("Iniciar sesión");
                }
            }
        };
        worker.execute();
    }
 
    private void mostrarError(String msg) {
        lblError.setText(msg);
        // Animación de shake horizontal
        Timer timer = new Timer(30, null);
        int[] count = {0};
        int[] dx    = {-8};
        Point orig  = getLocation();
        timer.addActionListener(e -> {
            setLocation(orig.x + dx[0], orig.y);
            dx[0] = -dx[0];
            if (++count[0] >= 8) { timer.stop(); setLocation(orig); }
        });
        timer.start();
    }
 
    // ── Permite arrastrar la ventana sin barra de título ─────────────────────
    private static class DragListener extends MouseAdapter {
        private final JFrame frame;
        private Point start;
        DragListener(JFrame f) { this.frame = f; }
        @Override public void mousePressed (MouseEvent e) { start = e.getPoint(); }
        @Override public void mouseDragged (MouseEvent e) {
            if (start == null) return;
            Point loc = frame.getLocation();
            frame.setLocation(loc.x + e.getX() - start.x, loc.y + e.getY() - start.y);
        }
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Loginn::new);
    }
}
