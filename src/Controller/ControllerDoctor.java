package Controller;

import DAOImpl.MedicoDAOImpl;
import Utilidades.EnviadorCredenciales;
import Utilidades.GeneradorContraseñas;
import com.toedter.calendar.JDateChooser;
import dao.MedicoDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Medico;

public class ControllerDoctor {
    private static ControllerDoctor instancia;
  
    private DefaultTableModel tableModelDoctor;
    private MedicoDAO medicoDAO = MedicoDAOImpl.getInstancia();
    private String documentoOriginal;
    
    private JTable tablaDoctores;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtCedula;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JTextField txtContraseña;
    private JComboBox<String> cbSexo;
    private JComboBox<String> cbHorario;
    private JComboBox<String> cbEspecialidad;
    private JComboBox<String> jComboBox_estado_doctor;
    private JDateChooser dateChooserNacimiento;
    private JDateChooser dateChooserContratacion;

    public void setCbSede(JComboBox<String> cbSede) {
        this.cbSede = cbSede;
    }
    private JComboBox<String> cbSede;

    private GeneradorContraseñas generadorContraseñas = new GeneradorContraseñas();
    private EnviadorCredenciales enviadorCredenciales = EnviadorCredenciales.getInstancia();
    
    private ControllerDoctor() {}
    
    public static synchronized ControllerDoctor getInstancia() {
        if (instancia == null) {
            instancia = new ControllerDoctor();
        }
        return instancia;
    }
    
    // ================== Setters para inyectar componentes ==================
    public void setTablaDoctores(JTable tablaDoctores) {
        this.tablaDoctores = tablaDoctores;
        this.tableModelDoctor = (DefaultTableModel) tablaDoctores.getModel();
    }
    public void setTxtNombre(JTextField txtNombre) { this.txtNombre = txtNombre; }
    public void setTxtApellidos(JTextField txtApellidos) { this.txtApellidos = txtApellidos; }
    public void setTxtCedula(JTextField txtCedula) { this.txtCedula = txtCedula; }
    public void setTxtCorreo(JTextField txtCorreo) { this.txtCorreo = txtCorreo; }
    public void setTxtTelefono(JTextField txtTelefono) { this.txtTelefono = txtTelefono; }
    public void setTxtContraseña(JTextField txtContraseña) { this.txtContraseña = txtContraseña; }
    public void setCbSexo(JComboBox<String> cbSexo) { this.cbSexo = cbSexo; }
    public void setCbHorario(JComboBox<String> cbHorario) { this.cbHorario = cbHorario; }
    public void setCbEspecialidad(JComboBox<String> cbEspecialidad) { this.cbEspecialidad = cbEspecialidad; }
    public void setDateChooserNacimiento(JDateChooser dateChooserNacimiento) { this.dateChooserNacimiento = dateChooserNacimiento; }
    public void setDateChooserContratacion(JDateChooser dateChooserContratacion) { this.dateChooserContratacion = dateChooserContratacion; }
    public void setjComboBox_estado_doctor(JComboBox<String> jComboBox_estado_doctor) { this.jComboBox_estado_doctor = jComboBox_estado_doctor; }

    // ================== Inicializar Tabla ==================
    public void initTableDoctor() {
        tableModelDoctor = new DefaultTableModel(
            new Object[]{"Nombre", "Apellidos", "Cédula", "Teléfono", "Correo", 
                         "Fecha Nac.", "Sexo", "Especialidad", 
                         "Fecha Contratación", "Horario", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaDoctores.setModel(tableModelDoctor);
    }
    
    // ================== Cargar Médicos en Tabla ==================
    public void cargarDatosEnTablaDoctor() {
        try {
            tableModelDoctor.setRowCount(0);
            List<Medico> medicos = medicoDAO.cargarTodos();
            
            if (medicos == null || medicos.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron médicos registrados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            for (Medico medico : medicos) {
                Object[] row = {
                    medico.getNombres(),
                    medico.getApellidos(),
                    medico.getNumeroDocumento(),
                    medico.getCelular(),
                    medico.getEmail(),
                    medico.getFechaNacimiento(),
                    medico.getSexo(),
                    medico.getEspecialidad(),
                    medico.getFechaContratacion(),
                    medico.getHorario(),
                    medico.getEstado()
                };
                tableModelDoctor.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al cargar los médicos: " + e.getMessage(),
                "ERROR", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ================== Guardar Nuevo Médico ==================
    public void guardarDoctorDesdeFormulario() {
        try {
            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String cedula = txtCedula.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String horario = cbHorario.getSelectedItem().toString();
            String especialidad = cbEspecialidad.getSelectedItem().toString();
            String sedeSeleccionada = cbSede.getSelectedItem().toString();
            
            if (nombres.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || 
                correo.isEmpty() || telefono.isEmpty() ||
                dateChooserNacimiento.getDate() == null || 
                dateChooserContratacion.getDate() == null) {
                JOptionPane.showMessageDialog(null, 
                    "Todos los campos son obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(null,
                    "El correo electrónico no tiene un formato válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaContratacion = dateChooserContratacion.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            if (medicoDAO.existeMedico(cedula)) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe un doctor con esta cédula",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String contrasena = generadorContraseñas.generarContrasena(10);
            
            Medico nuevoMedico = new Medico(
                cedula, nombres, apellidos, fechaNacimiento, sexo,
                correo, telefono, contrasena, especialidad,
                fechaContratacion, horario, "Activo" , sedeSeleccionada// estado por defecto
            );
            
            if (medicoDAO.guardarMedico(nuevoMedico)) {
                boolean envioExitoso = enviadorCredenciales.enviarCredenciales(
                    correo, nombres + " " + apellidos, contrasena
                );
                if (envioExitoso) {
                    JOptionPane.showMessageDialog(null, 
                        "Doctor registrado exitosamente y credenciales enviadas.", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Doctor registrado pero no se pudo enviar el correo.", 
                        "Advertencia", 
                        JOptionPane.WARNING_MESSAGE);
                }
                cargarDatosEnTablaDoctor();
                limpiarFormulario();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al guardar Doctor: " + e.getMessage(),
                "ERROR", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }    
    }
    
    // ================== Actualizar Médico ==================
    public void actualizarDoctor() {
        try {
            int filaSeleccionada = tablaDoctores.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione un doctor para actualizar", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String documentoOriginal = tableModelDoctor.getValueAt(filaSeleccionada, 2).toString();
            Medico medicoActual = medicoDAO.buscarMedicoPorIdentificacion(documentoOriginal);
            if (medicoActual == null) {
                JOptionPane.showMessageDialog(null,
                    "No se encontró el doctor en la base de datos",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombres = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String cedula = txtCedula.getText().trim();
            String correo = txtCorreo.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String sexo = cbSexo.getSelectedItem().toString();
            String horario = cbHorario.getSelectedItem().toString();
            String especialidad = cbEspecialidad.getSelectedItem().toString();
            String estado = jComboBox_estado_doctor.getSelectedItem().toString();
            String sedeSeleccionada = cbSede.getSelectedItem().toString();

            if (nombres.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || 
                correo.isEmpty() || telefono.isEmpty() ||
                dateChooserNacimiento.getDate() == null || 
                dateChooserContratacion.getDate() == null) {
                JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(null,
                    "El correo electrónico no tiene un formato válido",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaNacimiento = dateChooserNacimiento.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaContratacion = dateChooserContratacion.getDate()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!documentoOriginal.equals(cedula) && medicoDAO.existeMedico(cedula)) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe un doctor con esta cédula",
                    "Validación",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String contraseña = medicoActual.getContraseña();

            Medico medicoActualizado = new Medico(
                cedula, nombres, apellidos, fechaNacimiento, sexo,
                correo, telefono, contraseña, especialidad,
                fechaContratacion, horario, estado, sedeSeleccionada
            );

            if (medicoDAO.actualizarMedico(documentoOriginal, medicoActualizado)) {
                JOptionPane.showMessageDialog(null,
                    "Doctor actualizado exitosamente",
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaDoctor();
                limpiarFormulario();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar doctor: " + e.getMessage(),
                "Error del Sistema",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // ================== Eliminar / Deshabilitar ==================
    public void eliminarDoctorSeleccionado() {
        int filaSeleccionada = tablaDoctores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Seleccione un doctor de la tabla.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String numeroDocumento = tableModelDoctor.getValueAt(filaSeleccionada, 2).toString();
        int confirmacion = JOptionPane.showConfirmDialog(
            null, 
            "¿Desea deshabilitar al doctor con cédula " + numeroDocumento + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean actualizado = medicoDAO.actualizarEstado(numeroDocumento, "Deshabilitado");
            if (actualizado) {
                JOptionPane.showMessageDialog(null, 
                    "Doctor deshabilitado correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaDoctor();
            }
        }
    }
    
    // ================== Utilidades ==================
    public void limpiarFormulario() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCedula.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        cbSexo.setSelectedIndex(0);
        cbHorario.setSelectedIndex(0);
        cbEspecialidad.setSelectedIndex(0);
        dateChooserNacimiento.setDate(null);
        dateChooserContratacion.setDate(null);
        jComboBox_estado_doctor.setSelectedIndex(0);
    }

    public void cargarDatosDoctorEnFormulario() {
        int filaSeleccionada = tablaDoctores.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombre.setText(tableModelDoctor.getValueAt(filaSeleccionada, 0).toString());
            txtApellidos.setText(tableModelDoctor.getValueAt(filaSeleccionada, 1).toString());
            txtCedula.setText(tableModelDoctor.getValueAt(filaSeleccionada, 2).toString());
            txtTelefono.setText(tableModelDoctor.getValueAt(filaSeleccionada, 3).toString());
            txtCorreo.setText(tableModelDoctor.getValueAt(filaSeleccionada, 4).toString());
            
            LocalDate fechaNac = (LocalDate) tableModelDoctor.getValueAt(filaSeleccionada, 5);
            dateChooserNacimiento.setDate(Date.from(fechaNac.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            
            cbSexo.setSelectedItem(tableModelDoctor.getValueAt(filaSeleccionada, 6).toString());
            cbEspecialidad.setSelectedItem(tableModelDoctor.getValueAt(filaSeleccionada, 7).toString());
            
            LocalDate fechaCont = (LocalDate) tableModelDoctor.getValueAt(filaSeleccionada, 8);
            dateChooserContratacion.setDate(Date.from(fechaCont.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            
            cbHorario.setSelectedItem(tableModelDoctor.getValueAt(filaSeleccionada, 9).toString());
            jComboBox_estado_doctor.setSelectedItem(tableModelDoctor.getValueAt(filaSeleccionada, 10).toString());
            this.documentoOriginal = txtCedula.getText();
        }
    }
}
