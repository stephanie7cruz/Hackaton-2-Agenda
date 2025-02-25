import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgendaI extends JFrame {
    private Agenda agenda;
    private JTable tablaContactos;
    private DefaultTableModel modeloTabla;
    private JTextField nombreField, apellidoField, telefonoField;
    private JLabel espaciosLabel;
    private static final Color COLOR_PRIMARIO = new Color(41, 128, 185);
    private static final Color COLOR_FONDO = new Color(236, 240, 241);

    public AgendaI() {
        String input = JOptionPane.showInputDialog("Ingrese el tamaño de la agenda (o presione Cancelar para usar el tamaño por defecto de 10):");
        this.agenda = (input != null && !input.isEmpty()) ? new Agenda(Integer.parseInt(input)) : new Agenda();
        this.configurarVentana();
        this.crearComponentes();
        this.setVisible(true);
    }

    private void configurarVentana() {
        this.setTitle("Agenda Telefónica");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));
        this.getContentPane().setBackground(COLOR_FONDO);
    }

    private void crearComponentes() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(COLOR_FONDO);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_FONDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        nombreField = crearCampoTexto("Nombre:");
        apellidoField = crearCampoTexto("Apellido:");
        telefonoField = crearCampoTexto("Teléfono:");

        agregarCampo(formPanel, new JLabel("Nombre:"), nombreField, gbc, 0);
        agregarCampo(formPanel, new JLabel("Apellido:"), apellidoField, gbc, 1);
        agregarCampo(formPanel, new JLabel("Teléfono:"), telefonoField, gbc, 2);

        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botonesPanel.setBackground(COLOR_FONDO);

        JButton agregarBtn = crearBoton("Agregar", new Color(46, 204, 113));
        JButton eliminarBtn = crearBoton("Eliminar", new Color(217, 231, 60));
        JButton modificarBtn = crearBoton("Modificar", new Color(52, 152, 219));
        JButton limpiarBtn = crearBoton("Limpiar", new Color(255, 73, 0));

        botonesPanel.add(agregarBtn);
        botonesPanel.add(eliminarBtn);
        botonesPanel.add(modificarBtn);
        botonesPanel.add(limpiarBtn);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(botonesPanel, gbc);

        espaciosLabel = new JLabel("Espacios libres: " + this.agenda.espacioLibres());
        espaciosLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(COLOR_FONDO);
        infoPanel.add(espaciosLabel);

        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Apellido", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaContactos = new JTable(modeloTabla);
        tablaContactos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaContactos.getTableHeader().setBackground(COLOR_PRIMARIO);
        tablaContactos.getTableHeader().setForeground(Color.WHITE);
        tablaContactos.setRowHeight(25);
        tablaContactos.setShowGrid(true);
        tablaContactos.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scrollPane = new JScrollPane(tablaContactos);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(COLOR_FONDO);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(infoPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(mainPanel);

        agregarBtn.addActionListener(e -> agregarContacto());
        eliminarBtn.addActionListener(e -> eliminarContacto());
        modificarBtn.addActionListener(e -> modificarContacto());
        limpiarBtn.addActionListener(e -> limpiarCampos());

        tablaContactos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaContactos.getSelectedRow() != -1) {
                int row = tablaContactos.getSelectedRow();
                nombreField.setText((String) tablaContactos.getValueAt(row, 0));
                apellidoField.setText((String) tablaContactos.getValueAt(row, 1));
                telefonoField.setText((String) tablaContactos.getValueAt(row, 2));
            }
        });
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField field = new JTextField(20);
        field.setBorder(BorderFactory.createCompoundBorder(field.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return field;
    }

    private void agregarCampo(JPanel panel, JLabel label, JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(100, 35));
        return boton;
    }

    private void agregarContacto() {
        if (validarCampos()) {
            Contacto nuevoContacto = new Contacto(nombreField.getText().trim(), apellidoField.getText().trim(), telefonoField.getText().trim());
            if (agenda.agendaLlena()) {
                mostrarMensaje("La agenda está llena", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (agenda.existeContacto(nuevoContacto)) {
                mostrarMensaje("El contacto ya existe en la agenda", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            agenda.anadirContacto(nuevoContacto);
            actualizarTabla();
            limpiarCampos();
            actualizarEspaciosLibres();
        }
    }

    private void eliminarContacto() {
        int filaSeleccionada = tablaContactos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarMensaje("Por favor, seleccione un contacto para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String nombre = (String) tablaContactos.getValueAt(filaSeleccionada, 0);
            String apellido = (String) tablaContactos.getValueAt(filaSeleccionada, 1);
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el contacto " + nombre + " " + apellido + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                Contacto contacto = new Contacto(nombre, apellido, "");
                agenda.eliminarContacto(contacto);
                actualizarTabla();
                limpiarCampos();
                actualizarEspaciosLibres();
            }
        }
    }

    private void modificarContacto() {
        int filaSeleccionada = tablaContactos.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarMensaje("Por favor, seleccione un contacto para modificar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (validarCampos()) {
                String nombreOriginal = (String) tablaContactos.getValueAt(filaSeleccionada, 0);
                String apellidoOriginal = (String) tablaContactos.getValueAt(filaSeleccionada, 1);
                String nuevoTelefono = telefonoField.getText().trim();
                agenda.modificarTelefono(nombreOriginal, apellidoOriginal, nuevoTelefono);
                actualizarTabla();
                limpiarCampos();
            }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Contacto[] contactos = agenda.getContactos();
        for (Contacto contacto : contactos) {
            if (contacto != null) {
                modeloTabla.addRow(new Object[]{contacto.getNombre(), contacto.getApellido(), contacto.getTelefono()});
            }
        }
    }

    private void actualizarEspaciosLibres() {
        espaciosLabel.setText("Espacios libres: " + agenda.espacioLibres());
    }

    private boolean validarCampos() {
        String nombre = nombreField.getText().trim();
        String apellido = apellidoField.getText().trim();
        String telefono = telefonoField.getText().trim();
        if (!nombre.isEmpty() && !apellido.isEmpty() && !telefono.isEmpty()) {
            return true;
        } else {
            mostrarMensaje("Por favor, complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void limpiarCampos() {
        nombreField.setText("");
        apellidoField.setText("");
        telefonoField.setText("");
        tablaContactos.clearSelection();
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgendaI());
    }
}
