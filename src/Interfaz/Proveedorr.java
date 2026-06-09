/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import DAO.ProveedorDAO;
import Utilidades.ConfirmarBorrarDialog;
import Utilidades.EstilosUI;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import Modelo.Proveedor;
import Utilidades.ConfirmarActualizarDialog;

/**
 *
 * @author ACER
 */
public class Proveedorr extends JFrame{
      // ─── Entidad y columnas ────────────────────────────────────────────────────
    private static final String   NOMBRE_ENTIDAD  = "Proveedor";
    private static final String[] COLUMNAS_TABLA  = {"NIT", "Nombre", "Teléfono", "Dirección", "Ciudad"};
 
    // ─── DAO ──────────────────────────────────────────────────────────────────
    private final ProveedorDAO dao = new ProveedorDAO();
 
    // ─── Campos del formulario ─────────────────────────────────────────────────
    private JTextField txtNit, txtNombre, txtTelefono, txtDireccion, txtCiudad;
    private JTextField txtBuscar;
 
    // ─── Tabla ─────────────────────────────────────────────────────────────────
    private JTable             tabla;
    private DefaultTableModel  modeloTabla;
 
    // ─── Botones ───────────────────────────────────────────────────────────────
    private JButton btnAgregar, btnActualizar, btnBorrar, btnLimpiar;
 
    // ─── Resumen ───────────────────────────────────────────────────────────────
    private JLabel lblTotalProveedores, lblCiudades, lblUltimo;
 
    // ─── Estado ────────────────────────────────────────────────────────────────
    private int filaSeleccionada = -1;
 
    // ══════════════════════════════════════════════════════════════════════════
    public Proveedorr() {
        super("Gestión de " + NOMBRE_ENTIDAD + "es");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 680);
        setMinimumSize(new Dimension(900, 580));
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstilosUI.COLOR_FONDO);
 
        inicializarUI();
        cargarTabla();         // ← Carga datos desde la BD al abrir
        setVisible(true);
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   CONSTRUCCIÓN DE LA INTERFAZ
    // ══════════════════════════════════════════════════════════════════════════
    private void inicializarUI() {
        setLayout(new BorderLayout(0, 0));
        add(crearPanelTitulo(),  BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
    }
 
    // ── Título superior ───────────────────────────────────────────────────────
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, EstilosUI.COLOR_EXITO_DARK,
                        getWidth(), 0, EstilosUI.COLOR_EXITO);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setPreferredSize(new Dimension(0, 60));
        panel.setBorder(new EmptyBorder(0, 24, 0, 24));
 
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
        textos.setOpaque(false);
 
        JLabel titulo = new JLabel("🏭  Gestión de " + NOMBRE_ENTIDAD + "es");
        titulo.setFont(EstilosUI.FUENTE_TITULO);
        titulo.setForeground(Color.WHITE);
 
        JLabel sub = new JLabel("Administra los proveedores del sistema");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(180, 230, 180));
 
        textos.add(titulo);
        textos.add(sub);
        panel.add(textos, BorderLayout.WEST);
        return panel;
    }
 
    // ── SplitPane: formulario | tabla ─────────────────────────────────────────
    private JSplitPane crearPanelCentral() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelFormulario(), crearPanelDerecho());
        split.setDividerLocation(320);
        split.setDividerSize(6);
        split.setBorder(null);
        split.setContinuousLayout(true);
        return split;
    }
 
    // ── Formulario izquierdo ──────────────────────────────────────────────────
    private JPanel crearPanelFormulario() {
        JPanel panel = EstilosUI.crearPanelTarjeta();
        panel.setLayout(new BorderLayout(0, 16));
        panel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 0, 1, EstilosUI.COLOR_BORDE),
                new EmptyBorder(20, 20, 20, 20)));
 
        panel.add(EstilosUI.crearLabelSeccion("Datos del " + NOMBRE_ENTIDAD), BorderLayout.NORTH);
 
        // ── Campos ────────────────────────────────────────────────────────────
        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets   = new Insets(5, 0, 5, 0);
        gbc.fill     = GridBagConstraints.HORIZONTAL;
        gbc.weightx  = 1.0;
 
        txtNit       = EstilosUI.crearCampoNumerico();
        txtNombre    = EstilosUI.crearCampoTexto();
        txtTelefono  = EstilosUI.crearCampoTexto();
        txtDireccion = EstilosUI.crearCampoTexto();
        txtCiudad    = EstilosUI.crearCampoTexto();
 
        txtNit.setToolTipText("NIT del proveedor (solo números)");
        txtTelefono.setToolTipText("Ej: 3001234567");
 
        agregarCampo(campos, gbc, 0, "NIT *",        txtNit);
        agregarCampo(campos, gbc, 1, "Nombre *",     txtNombre);
        agregarCampo(campos, gbc, 2, "Teléfono *",   txtTelefono);
        agregarCampo(campos, gbc, 3, "Dirección *",  txtDireccion);
        agregarCampo(campos, gbc, 4, "Ciudad *",     txtCiudad);
 
        panel.add(campos,              BorderLayout.CENTER);
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);
        return panel;
    }
 
    private void agregarCampo(JPanel panel, GridBagConstraints gbc,
                               int fila, String labelTxt, JTextField campo) {
        gbc.gridx = 0; gbc.gridy = fila * 2;
        JLabel lbl = new JLabel(labelTxt);
        lbl.setFont(EstilosUI.FUENTE_LABEL);
        lbl.setForeground(EstilosUI.COLOR_NEUTRO);
        panel.add(lbl, gbc);
 
        gbc.gridy = fila * 2 + 1;
        panel.add(campo, gbc);
    }
 
    // ── Botones ───────────────────────────────────────────────────────────────
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
 
        btnAgregar    = EstilosUI.crearBoton("➕  Agregar",    EstilosUI.COLOR_EXITO,       EstilosUI.COLOR_EXITO_DARK);
        btnActualizar = EstilosUI.crearBoton("✏️  Actualizar", EstilosUI.COLOR_ADVERTENCIA,  EstilosUI.COLOR_ADVERTENCIA_DARK);
        btnBorrar     = EstilosUI.crearBoton("🗑  Borrar",     EstilosUI.COLOR_PELIGRO,      EstilosUI.COLOR_PELIGRO_DARK);
        btnLimpiar    = EstilosUI.crearBoton("🔄  Limpiar",    EstilosUI.COLOR_NEUTRO,       EstilosUI.COLOR_NEUTRO_DARK);
 
        btnActualizar.setEnabled(false);
        btnBorrar.setEnabled(false);
 
        btnAgregar.addActionListener   (e -> accionAgregar());
        btnActualizar.addActionListener(e -> accionActualizar());
        btnBorrar.addActionListener    (e -> accionBorrar());
        btnLimpiar.addActionListener   (e -> limpiarFormulario());
 
        panel.add(btnAgregar);
        panel.add(btnActualizar);
        panel.add(btnBorrar);
        panel.add(btnLimpiar);
        return panel;
    }
 
    // ── Panel derecho: búsqueda + tabla + resumen ─────────────────────────────
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(EstilosUI.COLOR_FONDO);
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
 
        panel.add(crearPanelBusqueda(), BorderLayout.NORTH);
        panel.add(crearPanelTabla(),   BorderLayout.CENTER);
        panel.add(crearPanelResumen(), BorderLayout.SOUTH);
        return panel;
    }
 
    // ── Búsqueda en tiempo real ───────────────────────────────────────────────
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
 
        panel.add(EstilosUI.crearLabelSeccion("Buscar " + NOMBRE_ENTIDAD), BorderLayout.WEST);
 
        txtBuscar = EstilosUI.crearCampoTexto();
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setText("🔍  Buscar por nombre o ciudad...");
 
        txtBuscar.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (txtBuscar.getText().startsWith("🔍")) {
                    txtBuscar.setText(""); txtBuscar.setForeground(Color.BLACK);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (txtBuscar.getText().isEmpty()) {
                    txtBuscar.setText("🔍  Buscar por nombre o ciudad...");
                    txtBuscar.setForeground(Color.GRAY);
                }
            }
        });
 
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { filtrarTabla(); }
            public void removeUpdate(DocumentEvent e)  { filtrarTabla(); }
            public void changedUpdate(DocumentEvent e) { filtrarTabla(); }
        });
 
        panel.add(txtBuscar, BorderLayout.CENTER);
        return panel;
    }
 
    // ── Tabla ─────────────────────────────────────────────────────────────────
    private JPanel crearPanelTabla() {
        modeloTabla = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla);
        EstilosUI.estilizarTabla(tabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 
        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(80);   // NIT
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);  // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(110);  // Teléfono
        tabla.getColumnModel().getColumn(3).setPreferredWidth(220);  // Dirección
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);  // Ciudad
 
        // Click en fila → carga datos en formulario
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) cargarFilaEnFormulario(fila);
            }
        });
 
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(new LineBorder(EstilosUI.COLOR_BORDE, 1, true));
 
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
 
    // ── Resumen inferior ──────────────────────────────────────────────────────
    private JPanel crearPanelResumen() {
        JPanel panel = EstilosUI.crearPanelResumen();
 
        lblTotalProveedores = new JLabel();
        lblCiudades         = new JLabel();
        lblUltimo           = new JLabel();
 
        for (JLabel l : new JLabel[]{lblTotalProveedores, lblCiudades, lblUltimo}) {
            l.setFont(EstilosUI.FUENTE_RESUMEN);
            l.setForeground(EstilosUI.COLOR_EXITO_DARK);
            panel.add(l);
        }
        return panel;
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   ACCIONES CRUD  ←  conectadas al ProveedorDAO
    // ══════════════════════════════════════════════════════════════════════════
 
    private void accionAgregar() {
        if (!validarFormulario()) return;
 
        Proveedor prov = construirProveedorDesdeFormulario();
 
        if (dao.registrarProveedor(prov)) {
            mostrarExito(NOMBRE_ENTIDAD + " registrado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            mostrarError("No se pudo registrar el proveedor.\nVerifica los datos o la conexión.");
        }
    }
 
    private void accionActualizar() {
        if (filaSeleccionada < 0) {
            mostrarError("Selecciona un proveedor en la tabla para actualizar.");
            return;
        }
        if (!validarFormulario()) return;
 
        ConfirmarActualizarDialog dialogo = new ConfirmarActualizarDialog(
                this, NOMBRE_ENTIDAD, txtNombre.getText().trim());
        dialogo.setVisible(true);
 
        if (dialogo.fueConfirmado()) {
            Proveedor prov = construirProveedorDesdeFormulario();
 
            if (dao.modificarProveedor(prov)) {
                mostrarExito(NOMBRE_ENTIDAD + " actualizado correctamente.");
                limpiarFormulario();
                cargarTabla();
            } else {
                mostrarError("No se pudo actualizar el proveedor.");
            }
        }
    }
 
    private void accionBorrar() {
        if (filaSeleccionada < 0) {
            mostrarError("Selecciona un proveedor en la tabla para eliminar.");
            return;
        }
 
        String nombre = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
        int    nit    = Integer.parseInt(modeloTabla.getValueAt(filaSeleccionada, 0).toString());
 
        ConfirmarBorrarDialog dialogo = new ConfirmarBorrarDialog(this, NOMBRE_ENTIDAD, nombre);
        dialogo.setVisible(true);
 
        if (dialogo.fueConfirmado()) {
            if (dao.eliminarProveedor(nit)) {
                mostrarExito(NOMBRE_ENTIDAD + " eliminado correctamente.");
                limpiarFormulario();
                cargarTabla();
            } else {
                mostrarError("No se pudo eliminar el proveedor.");
            }
        }
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   UTILIDADES
    // ══════════════════════════════════════════════════════════════════════════
 
    /** Lee la lista de proveedores del DAO y llena la tabla */
    private void cargarTabla() {
        modeloTabla.setRowCount(0);                   // limpia tabla
        List<Proveedor> lista = dao.listarProveedores();
 
        for (Proveedor p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getNit(),
                    p.getNombre(),
                    p.getTelefono(),
                    p.getDireccion(),
                    p.getCiudad()
            });
        }
        actualizarResumen(lista);
    }
 
    /** Construye un objeto Proveedor desde los campos del formulario */
    private Proveedor construirProveedorDesdeFormulario() {
        Proveedor prov = new Proveedor();
        prov.setNit      (Integer.parseInt(txtNit.getText().trim()));
        prov.setNombre   (txtNombre.getText().trim());
        prov.setTelefono (txtTelefono.getText().trim());
        prov.setDireccion(txtDireccion.getText().trim());
        prov.setCiudad   (txtCiudad.getText().trim());
        return prov;
    }
 
    /** Carga los datos de la fila seleccionada en el formulario */
    private void cargarFilaEnFormulario(int fila) {
        filaSeleccionada = fila;
        txtNit.setText      (modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText   (modeloTabla.getValueAt(fila, 1).toString());
        txtTelefono.setText (modeloTabla.getValueAt(fila, 2).toString());
        txtDireccion.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtCiudad.setText   (modeloTabla.getValueAt(fila, 4).toString());
 
        // El NIT es la PK → no se edita una vez seleccionado
        txtNit.setEditable(false);
        txtNit.setBackground(new Color(240, 240, 240));
 
        btnActualizar.setEnabled(true);
        btnBorrar.setEnabled(true);
        btnAgregar.setEnabled(false);
    }
 
    private void limpiarFormulario() {
        txtNit.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
 
        txtNit.setEditable(true);
        txtNit.setBackground(Color.WHITE);
 
        filaSeleccionada = -1;
        tabla.clearSelection();
        btnActualizar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnAgregar.setEnabled(true);
        txtNit.requestFocus();
    }
 
    /** Filtra la tabla mientras el usuario escribe en el buscador */
    private void filtrarTabla() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty() || texto.startsWith("🔍")) {
            tabla.setRowSorter(null);
            return;
        }
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tabla.setRowSorter(sorter);
        // Busca en: Nombre (col 1) y Ciudad (col 4)
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1, 4));
    }
 
    /** Actualiza los indicadores de resumen debajo de la tabla */
    private void actualizarResumen(List<Proveedor> lista) {
        long ciudadesUnicas = lista.stream()
                .map(Proveedor::getCiudad)
                .filter(c -> c != null && !c.isBlank())
                .distinct()
                .count();
 
        String ultimo = lista.isEmpty() ? "—"
                : lista.get(lista.size() - 1).getNombre();
 
        lblTotalProveedores.setText("🏭 Total proveedores: " + lista.size());
        lblCiudades.setText        ("🌆 Ciudades distintas: " + ciudadesUnicas);
        lblUltimo.setText          ("🕐 Último registrado: " + ultimo);
    }
 
    // ── Validación ────────────────────────────────────────────────────────────
    private boolean validarFormulario() {
        // NIT
        String nitTxt = txtNit.getText().trim();
        if (nitTxt.isEmpty()) {
            mostrarError("El campo 'NIT' es obligatorio."); txtNit.requestFocus(); return false;
        }
        try {
            int nit = Integer.parseInt(nitTxt);
            if (nit <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarError("El 'NIT' debe ser un número entero positivo."); txtNit.requestFocus(); return false;
        }
        // Nombre
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo 'Nombre' es obligatorio."); txtNombre.requestFocus(); return false;
        }
        // Teléfono
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarError("El campo 'Teléfono' es obligatorio."); txtTelefono.requestFocus(); return false;
        }
        // Dirección
        if (txtDireccion.getText().trim().isEmpty()) {
            mostrarError("El campo 'Dirección' es obligatoria."); txtDireccion.requestFocus(); return false;
        }
        // Ciudad
        if (txtCiudad.getText().trim().isEmpty()) {
            mostrarError("El campo 'Ciudad' es obligatorio."); txtCiudad.requestFocus(); return false;
        }
        return true;
    }
 
    private void mostrarError (String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error de validación", JOptionPane.ERROR_MESSAGE);
    }
    private void mostrarExito (String msg) {
        JOptionPane.showMessageDialog(this, msg, "Éxito",               JOptionPane.INFORMATION_MESSAGE);
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(InterfazProveedor::new);
    }
}
