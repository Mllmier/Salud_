package Controller;

import DAOImpl.RecepcionistaDAOImpl;
import Utilidades.EnviadorCredenciales;
import Utilidades.GeneradorContraseñas;
import com.toedter.calendar.JDateChooser;
import dao.RecepcionistaDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Recepcionista;

public class ControllerRecepcionista {

    private static ControllerRecepcionista instancia;

    private DefaultTableModel tableModelRecepcionista;
    private RecepcionistaDAO recepcionistaDAO = RecepcionistaDAOImpl.getInstancia();
    private String documentoOriginal;

    private JTable tablaRecepcionistas;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtCedula;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtCodigoEmpleado;
    private JTextField txtContraseña;
    private JComboBox<String> cbSexo;
    private JComboBox<String> cbEps;
    private JComboBox<String> cbHorario;
    private JDateChooser dateChooserNacimiento;
    private JDateChooser dateChooserContratacion;
    private JComboBox<String> jComboBox_estado_recepcionista;

    private GeneradorContraseñas generadorContraseñas = new GeneradorContraseñas();
    private EnviadorCredenciales enviadorCredenciales = EnviadorCredenciales.getInstancia();

    private ControllerRecepcionista() {}

    public static synchronized ControllerRecepcionista getInstancia() {
        if (instancia == null) {
            instancia = new ControllerRecepcionista();
        }
        return instancia;
    }

    public void setTablaRecepcionistas(JTable tablaRecepcionistas) {
        this.tablaRecepcionistas = tablaRecepcionistas;
        this.tableModelRecepcionista = (DefaultTableModel) tablaRecepcionistas.getModel();
    }

    public void setTxtNombre(JTextField txtNombre) { this.txtNombre = txtNombre; }
    public void setTxtApellidos(JTextField txtApellidos) { this.txtApellidos = txtApellidos; }
    public void setTxtCedula(JTextField txtCedula) { this.txtCedula = txtCedula; }
    public void setTxtCorreo(JTextField txtCorreo) { this.txtCorreo = txtCorreo; }
    public void setTxtTelefono(JTextField txtTelefono) { this.txtTelefono = txtTelefono; }
    public void setTxtCodigoEmpleado(JTextField txtCodigoEmpleado) { this.txtCodigoEmpleado = txtCodigoEmpleado; }
    public void setTxtContraseña(JTextField txtContraseña) { this.txtContraseña = txtContraseña; }
    public void setCbSexo(JComboBox<String> cbSexo) { this.cbSexo = cbSexo; }
    public void setCbEps(JComboBox<String> cbEps) { this.cbEps = cbEps; }
    public void setCbHorario(JComboBox<String> cbHorario) { this.cbHorario = cbHorario; }
    public void setDateChooserNacimiento(JDateChooser dateChooserNacimiento) { this.dateChooserNacimiento = dateChooserNacimiento; }
    public void setDateChooserContratacion(JDateChooser dateChooserContratacion) { this.dateChooserContratacion = dateChooserContratacion; }
    public void setjComboBox_estado_recepcionista(JComboBox<String> jComboBox_estado_recepcionista) { this.jComboBox_estado_recepcionista = jComboBox_estado_recepcionista; }

    public void initTableRecepcionista() {
        tableModelRecepcionista = new DefaultTableModel(
            new Object[]{"Nombre", "Apellidos", "Cédula", "Teléfono", "Correo",
                         "Fecha Nac.", "Sexo", "EPS", "Código", "Fecha Contratación",
                         "Horario", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaRecepcionistas.setModel(tableModelRecepcionista);
    }

    public void cargarDatosEnTablaRecepcionista() {
        try {
            tableModelRecepcionista.setRowCount(0);
            List<Recepcionista> recepcionistas = recepcionistaDAO.cargarTodos();

            if (recepcionistas == null || recepcionistas.isEmpty()) {
                return;
            }

            for (Recepcionista r : recepcionistas) {
                Object[] row = {
                    r.getNombres(),
                    r.getApellidos(),
                    r.getNumeroDocumento(),
                    r.getCelular(),
                    r.getEmail(),
                    r.getFechaNacimiento(),
                    r.getSexo(),
                    r.getEps(),
                    r.getCodigoEmpleado(),
                    r.getFechaContratacion(),
                    r.getHorario(),
                    r.getEstado()
                };
                tableModelRecepcionista.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al cargar recepcionistas: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void guardarRecepcionistaDesdeFormulario() {
        try {
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String cedula = txtCedula.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String codigoEmpleado = txtCodigoEmpleado.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String eps = cbEps.getSelectedItem().toString();
            String horario = cbHorario.getSelectedItem().toString();

            if (nombres.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() ||
                correo.isEmpty() || telefono.isEmpty() || codigoEmpleado.isEmpty() ||
                dateChooserNacimiento.getDate() == null ||
                dateChooserContratacion.getDate() == null) {

                JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaContratacion = dateChooserContratacion.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String contraseña = generadorContraseñas.generarContrasena(10);

            Recepcionista nuevoRecepcionista = new Recepcionista(
                cedula, nombres, apellidos, fechaNacimiento, sexo, eps,
                correo, telefono, contraseña, codigoEmpleado,
                fechaContratacion, horario, "Activo"
            );

            if (recepcionistaDAO.guardarRecepcionista(nuevoRecepcionista)) {
                enviadorCredenciales.enviarCredenciales(correo, nombres + " " + apellidos, contraseña);
                JOptionPane.showMessageDialog(null,
                    "Recepcionista registrado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaRecepcionista();
                limpiarFormulario();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al guardar recepcionista: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void actualizarRecepcionista() {
        try {
            int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null,
                    "Seleccione un recepcionista de la tabla.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String documentoOriginal = tableModelRecepcionista.getValueAt(filaSeleccionada, 2).toString();
            Recepcionista actual = recepcionistaDAO.buscarPorDocumento(documentoOriginal);

            if (actual == null) {
                JOptionPane.showMessageDialog(null,
                    "No se encontró el recepcionista.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String cedula = txtCedula.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String codigoEmpleado = txtCodigoEmpleado.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String eps = cbEps.getSelectedItem().toString();
            String horario = cbHorario.getSelectedItem().toString();
            String estado = jComboBox_estado_recepcionista.getSelectedItem().toString();

            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaContratacion = dateChooserContratacion.getDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String contraseña = actual.getContraseña();

            Recepcionista actualizado = new Recepcionista(
                cedula, nombres, apellidos, fechaNacimiento, sexo, eps,
                correo, telefono, contraseña, codigoEmpleado,
                fechaContratacion, horario, estado
            );

            if (recepcionistaDAO.actualizarRecepcionista(documentoOriginal, actualizado)) {
                JOptionPane.showMessageDialog(null,
                    "Recepcionista actualizado correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaRecepcionista();
                limpiarFormulario();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar recepcionista: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void cargarDatosRecepcionistaEnFormulario() {
        int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombre.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 0).toString());
            txtApellidos.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 1).toString());
            txtCedula.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 2).toString());
            txtTelefono.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 3).toString());
            txtCorreo.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 4).toString());
            cbSexo.setSelectedItem(tableModelRecepcionista.getValueAt(filaSeleccionada, 6).toString());
            cbEps.setSelectedItem(tableModelRecepcionista.getValueAt(filaSeleccionada, 7).toString());
            txtCodigoEmpleado.setText(tableModelRecepcionista.getValueAt(filaSeleccionada, 8).toString());
            cbHorario.setSelectedItem(tableModelRecepcionista.getValueAt(filaSeleccionada, 10).toString());
            jComboBox_estado_recepcionista.setSelectedItem(tableModelRecepcionista.getValueAt(filaSeleccionada, 11).toString());
        }
    }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtCodigoEmpleado.setText("");
        cbSexo.setSelectedIndex(0);
        cbEps.setSelectedIndex(0);
        cbHorario.setSelectedIndex(0);
        dateChooserNacimiento.setDate(null);
        dateChooserContratacion.setDate(null);
        jComboBox_estado_recepcionista.setSelectedIndex(0);
    }
}
