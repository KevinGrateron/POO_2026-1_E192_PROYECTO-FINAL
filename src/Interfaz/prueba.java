/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import Utilidades.ConfirmarActualizarDialog;
import Utilidades.ConfirmarBorrarDialog;
import Utilidades.EstilosUI;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

/**
 *
 * @author ACER
 */
public class prueba extends JFrame{
    private static final String NOMBRE_ENTIDAD  = "Producto";
    private static final String[] COLUMNAS_TABLA = {"ID", "Nombre", "Categoría", "Precio", "Cantidad"};
 
    // ─── Componentes del formulario ────────────────────────────────────────────
    private JTextField txtId, txtNombre, txtCategoria, txtPrecio, txtCantidad;
    private JTextField txtBuscar;
 
    // ─── Tabla ─────────────────────────────────────────────────────────────────
    private JTable tabla;
    private DefaultTableModel modeloTabla;
 
    // ─── Botones ───────────────────────────────────────────────────────────────
    private JButton btnAgregar, btnActualizar, btnBorrar, btnLimpiar;
 
    // ─── Panel de resumen ──────────────────────────────────────────────────────
    private JLabel lblResumenTotal, lblResumenCantidad, lblResumenPrecioPromedio;
 
    // ─── Estado de selección ───────────────────────────────────────────────────
    private int filaSeleccionada = -1;
 
    // ─── Formato numérico ──────────────────────────────────────────────────────
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
 
    // ══════════════════════════════════════════════════════════════════════════
    public prueba() {
        super("Gestión de " + NOMBRE_ENTIDAD + "s");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(EstilosUI.COLOR_FONDO);
 
        inicializarUI();
        cargarDatosDemo();   // <─ Reemplaza con tu llamada al DAO
        actualizarResumen();
 
        setVisible(true);
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   CONSTRUCCIÓN DE LA INTERFAZ
    // ══════════════════════════════════════════════════════════════════════════
    private void inicializarUI() {
        setLayout(new BorderLayout(0, 0));
 
        add(crearPanelTitulo(),      BorderLayout.NORTH);
        add(crearPanelCentral(),     BorderLayout.CENTER);
    }
 
    // ── Título ─────────────────────────────────────────────────────────────────
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstilosUI.COLOR_PRIMARIO);
        panel.setBorder(new EmptyBorder(14, 24, 14, 24));
 
        JLabel titulo = new JLabel("Gestión de " + NOMBRE_ENTIDAD + "s");
        titulo.setFont(EstilosUI.FUENTE_TITULO);
        titulo.setForeground(Color.WHITE);
 
        JLabel subtitulo = new JLabel("Administra el inventario de " + NOMBRE_ENTIDAD.toLowerCase() + "s");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitulo.setForeground(new Color(200, 220, 255));
 
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);
        panel.add(textos, BorderLayout.WEST);
        return panel;
    }
 
    // ── Panel Central: formulario izquierda | tabla derecha ─────────────────
    private JSplitPane crearPanelCentral() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelFormulario(), crearPanelDerecho());
        split.setDividerLocation(310);
        split.setDividerSize(6);
        split.setBorder(null);
        split.setContinuousLayout(true);
        return split;
    }
 
    // ── Formulario (izquierda) ────────────────────────────────────────────────
    private JPanel crearPanelFormulario() {
        JPanel panel = EstilosUI.crearPanelTarjeta();
        panel.setLayout(new BorderLayout(0, 16));
        panel.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 0, 1, EstilosUI.COLOR_BORDE),
                new EmptyBorder(20, 20, 20, 20)));
 
        // Título sección
        panel.add(EstilosUI.crearLabelSeccion("Datos del " + NOMBRE_ENTIDAD), BorderLayout.NORTH);
 
        // Campos
        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
 
        txtId        = EstilosUI.crearCampoTexto();
        txtNombre    = EstilosUI.crearCampoTexto();
        txtCategoria = EstilosUI.crearCampoTexto();
        txtPrecio    = EstilosUI.crearCampoNumerico();
        txtCantidad  = EstilosUI.crearCampoNumerico();
 
        txtId.setEditable(false);
        txtId.setBackground(new Color(240, 240, 240));
        txtId.setToolTipText("El ID se genera automáticamente");
 
        agregarCampo(campos, gbc, 0, "ID (auto)",    txtId);
        agregarCampo(campos, gbc, 1, "Nombre *",     txtNombre);
        agregarCampo(campos, gbc, 2, "Categoría *",  txtCategoria);
        agregarCampo(campos, gbc, 3, "Precio ($) *", txtPrecio);
        agregarCampo(campos, gbc, 4, "Cantidad *",   txtCantidad);
 
        panel.add(campos, BorderLayout.CENTER);
 
        // Botones
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
 
    // ── Botones de acción ─────────────────────────────────────────────────────
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 0, 0, 0));
 
        btnAgregar   = EstilosUI.crearBoton("Agregar",    EstilosUI.COLOR_EXITO,      EstilosUI.COLOR_EXITO_DARK);
        btnActualizar= EstilosUI.crearBoton("Actualizar", EstilosUI.COLOR_ADVERTENCIA, EstilosUI.COLOR_ADVERTENCIA_DARK);
        btnBorrar    = EstilosUI.crearBoton("Borrar",     EstilosUI.COLOR_PELIGRO,     EstilosUI.COLOR_PELIGRO_DARK);
        btnLimpiar   = EstilosUI.crearBoton("Limpiar",    EstilosUI.COLOR_NEUTRO,      EstilosUI.COLOR_NEUTRO_DARK);
 
        // Actualizar y Borrar deshabilitados hasta seleccionar fila
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
        panel.add(crearPanelTabla(),    BorderLayout.CENTER);
        panel.add(crearPanelResumen(),  BorderLayout.SOUTH);
        return panel;
    }
 
    // ── Búsqueda ──────────────────────────────────────────────────────────────
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setOpaque(false);
 
        JLabel lbl = EstilosUI.crearLabelSeccion("Buscar " + NOMBRE_ENTIDAD);
        panel.add(lbl, BorderLayout.WEST);
 
        txtBuscar = EstilosUI.crearCampoTexto();
        txtBuscar.setToolTipText("Buscar por nombre o categoría...");
 
        // Placeholder
        txtBuscar.setText("Buscar por nombre o categoría...");
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (txtBuscar.getText().startsWith("🔍")) { txtBuscar.setText(""); txtBuscar.setForeground(Color.BLACK); }
            }
            @Override public void focusLost(FocusEvent e) {
                if (txtBuscar.getText().isEmpty()) { txtBuscar.setText("🔍  Buscar por nombre o categoría..."); txtBuscar.setForeground(Color.GRAY); }
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
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(140);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
 
        // Click en fila → cargar en formulario
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
 
    // ── Resumen debajo de la tabla ────────────────────────────────────────────
    private JPanel crearPanelResumen() {
        JPanel panel = EstilosUI.crearPanelResumen();
 
        lblResumenTotal         = new JLabel();
        lblResumenCantidad      = new JLabel();
        lblResumenPrecioPromedio= new JLabel();
 
        for (JLabel l : new JLabel[]{lblResumenTotal, lblResumenCantidad, lblResumenPrecioPromedio}) {
            l.setFont(EstilosUI.FUENTE_RESUMEN);
            l.setForeground(EstilosUI.COLOR_PRIMARIO_DARK);
            panel.add(l);
        }
 
        // Separadores visuales
        JSeparator sep1 = new JSeparator(JSeparator.VERTICAL);
        sep1.setPreferredSize(new Dimension(1, 20));
        sep1.setForeground(EstilosUI.COLOR_BORDE);
 
        JSeparator sep2 = new JSeparator(JSeparator.VERTICAL);
        sep2.setPreferredSize(new Dimension(1, 20));
        sep2.setForeground(EstilosUI.COLOR_BORDE);
 
        panel.add(new JLabel()); // spacer
        panel.add(sep1);
        panel.add(sep2);
        return panel;
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   ACCIONES CRUD
    // ══════════════════════════════════════════════════════════════════════════
 
    private void accionAgregar() {
        if (!validarFormulario()) return;
 
        // ── Aquí llamas a tu servicio/DAO: servicioProducto.guardar(producto); ──
        String id = String.valueOf(modeloTabla.getRowCount() + 1); // ID demo
        modeloTabla.addRow(new Object[]{
                id,
                txtNombre.getText().trim(),
                txtCategoria.getText().trim(),
                txtPrecio.getText().trim(),
                txtCantidad.getText().trim()
        });
 
        mostrarMensajeExito(NOMBRE_ENTIDAD + " agregado correctamente.");
        limpiarFormulario();
        actualizarResumen();
    }
 
    private void accionActualizar() {
        if (filaSeleccionada < 0) {
            mostrarError("Selecciona un " + NOMBRE_ENTIDAD.toLowerCase() + " en la tabla.");
            return;
        }
        if (!validarFormulario()) return;
 
        // Mostrar diálogo de confirmación
        ConfirmarActualizarDialog dialogo = new ConfirmarActualizarDialog(this, NOMBRE_ENTIDAD,
                txtNombre.getText().trim());
        dialogo.setVisible(true);
 
        if (dialogo.fueConfirmado()) {
            // ── Aquí llamas a tu servicio/DAO: servicioProducto.actualizar(producto); ──
            modeloTabla.setValueAt(txtNombre.getText().trim(),    filaSeleccionada, 1);
            modeloTabla.setValueAt(txtCategoria.getText().trim(), filaSeleccionada, 2);
            modeloTabla.setValueAt(txtPrecio.getText().trim(),    filaSeleccionada, 3);
            modeloTabla.setValueAt(txtCantidad.getText().trim(),  filaSeleccionada, 4);
 
            mostrarMensajeExito(NOMBRE_ENTIDAD + " actualizado correctamente.");
            limpiarFormulario();
            actualizarResumen();
        }
    }
 
    private void accionBorrar() {
        if (filaSeleccionada < 0) {
            mostrarError("Selecciona un " + NOMBRE_ENTIDAD.toLowerCase() + " en la tabla.");
            return;
        }
 
        String nombre = modeloTabla.getValueAt(filaSeleccionada, 1).toString();
 
        // Mostrar diálogo de confirmación de borrado
        ConfirmarBorrarDialog dialogo = new ConfirmarBorrarDialog(this, NOMBRE_ENTIDAD, nombre);
        dialogo.setVisible(true);
 
        if (dialogo.fueConfirmado()) {
            // ── Aquí llamas a tu servicio/DAO: servicioProducto.eliminar(id); ──
            modeloTabla.removeRow(filaSeleccionada);
            mostrarMensajeExito(NOMBRE_ENTIDAD + " eliminado correctamente.");
            limpiarFormulario();
            actualizarResumen();
        }
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    //   UTILIDADES
    // ══════════════════════════════════════════════════════════════════════════
 
    /** Carga los datos de la fila seleccionada en el formulario */
    private void cargarFilaEnFormulario(int fila) {
        filaSeleccionada = fila;
        txtId.setText       (modeloTabla.getValueAt(fila, 0).toString());
        txtNombre.setText   (modeloTabla.getValueAt(fila, 1).toString());
        txtCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtPrecio.setText   (modeloTabla.getValueAt(fila, 3).toString());
        txtCantidad.setText (modeloTabla.getValueAt(fila, 4).toString());
        btnActualizar.setEnabled(true);
        btnBorrar.setEnabled(true);
        btnAgregar.setEnabled(false);
    }
 
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        filaSeleccionada = -1;
        tabla.clearSelection();
        btnActualizar.setEnabled(false);
        btnBorrar.setEnabled(false);
        btnAgregar.setEnabled(true);
        txtNombre.requestFocus();
    }
 
    /** Filtra la tabla según el texto de búsqueda */
    private void filtrarTabla() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        if (texto.isEmpty() || texto.startsWith("🔍")) return;
 
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabla);
        tabla.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1, 2)); // Busca en Nombre y Categoría
    }
 
    /** Actualiza los indicadores de resumen debajo de la tabla */
    private void actualizarResumen() {
        double totalValor = 0;
        int    totalUnidades = 0;
        int    filas = modeloTabla.getRowCount();
 
        for (int i = 0; i < filas; i++) {
            try {
                double precio   = Double.parseDouble(modeloTabla.getValueAt(i, 3).toString());
                int    cantidad = Integer.parseInt  (modeloTabla.getValueAt(i, 4).toString());
                totalValor    += precio * cantidad;
                totalUnidades += cantidad;
            } catch (NumberFormatException ignored) {}
        }
 
        double promedio = filas > 0 ? totalValor / (totalUnidades > 0 ? totalUnidades : 1) : 0;
 
        lblResumenTotal.setText         ("💰 Valor total inventario: $" + df.format(totalValor));
        lblResumenCantidad.setText      ("📦 Total unidades: " + totalUnidades);
        lblResumenPrecioPromedio.setText("📊 Precio promedio ponderado: $" + df.format(promedio));
    }
 
    /** Validación básica del formulario */
    private boolean validarFormulario() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo 'Nombre' es obligatorio."); txtNombre.requestFocus(); return false;
        }
        if (txtCategoria.getText().trim().isEmpty()) {
            mostrarError("El campo 'Categoría' es obligatorio."); txtCategoria.requestFocus(); return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarError("El campo 'Precio' debe ser un número positivo."); txtPrecio.requestFocus(); return false;
        }
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarError("El campo 'Cantidad' debe ser un número entero positivo."); txtCantidad.requestFocus(); return false;
        }
        return true;
    }
 
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error de validación",
                JOptionPane.ERROR_MESSAGE);
    }
 
    private void mostrarMensajeExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }
 
    /** Datos de demostración – elimina en producción */
    private void cargarDatosDemo() {
        modeloTabla.addRow(new Object[]{"1", "Teclado Mecánico RGB",  "Periféricos", "120000", "15"});
        modeloTabla.addRow(new Object[]{"2", "Monitor 24\" Full HD",  "Pantallas",   "450000", "8"});
        modeloTabla.addRow(new Object[]{"3", "Mouse Inalámbrico",      "Periféricos", "65000",  "30"});
        modeloTabla.addRow(new Object[]{"4", "Silla Ergonómica Pro",   "Mobiliario",  "890000", "5"});
        modeloTabla.addRow(new Object[]{"5", "Webcam 1080p",           "Cámaras",     "175000", "12"});
    }
 
    // ══════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(prueba::new);
    }
}
