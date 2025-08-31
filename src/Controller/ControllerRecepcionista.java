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
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Recepcionista;


public class ControllerRecepcionista {
    private static ControllerRecepcionista instancia;
  
    private DefaultTableModel tableModelRecepcionista;
    private RecepcionistaDAO recepcionistaDAO = RecepcionistaDAOImpl.getInstancia();
    private String documentoOriginal;
    
    // Componentes de la vista
    private JTable tablaRecepcionistas;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtDocumento;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JPasswordField pwdContraseña;
    private JTextField txtCodigoEmpleado;
    private JDateChooser dateChooserNacimiento;
    private JDateChooser dateChooserContratacion;
    private JComboBox<String> cbSexo;
    private JComboBox<String> cbHorario;
    private JComboBox<String> cbEps;
    
    private final GeneradorContraseñas generadorContraseñas = new GeneradorContraseñas();
    private final EnviadorCredenciales enviadorCredenciales = EnviadorCredenciales.getInstancia();

    private ControllerRecepcionista() {}
    
    public static synchronized ControllerRecepcionista getInstancia() {
        if (instancia == null) {
            instancia = new ControllerRecepcionista();
        }
        return instancia;
    }

    // Setters para los componentes de la vista
    public void setTablaRecepcionistas(JTable tablaRecepcionistas) {
        this.tablaRecepcionistas = tablaRecepcionistas;
        this.tableModelRecepcionista = (DefaultTableModel) tablaRecepcionistas.getModel();
    }
    
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
    
    public void setTxtApellido(JTextField txtApellido) {
        this.txtApellido = txtApellido;
    }
    
    public void setTxtDocumento(JTextField txtDocumento) {
        this.txtDocumento = txtDocumento;
    }
    
    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }
    
    public void setTxtCelular(JTextField txtCelular) {
        this.txtCelular = txtCelular;
    }
    
    public void setPwdContraseña(JPasswordField pwdContraseña) {
        this.pwdContraseña = pwdContraseña;
        if (this.pwdContraseña != null) {
            this.pwdContraseña.setEchoChar('•'); // Ocultar caracteres de la contraseña
        }
    }
    
    public void setTxtCodigoEmpleado(JTextField txtCodigoEmpleado) {
        this.txtCodigoEmpleado = txtCodigoEmpleado;
    }
    
    public void setDateChooserNacimiento(JDateChooser dateChooserNacimiento) {
        this.dateChooserNacimiento = dateChooserNacimiento;
    }
    
    public void setDateChooserContratacion(JDateChooser dateChooserContratacion) {
        this.dateChooserContratacion = dateChooserContratacion;
    }
    
    public void setCbSexo(JComboBox<String> cbSexo) {
        this.cbSexo = cbSexo;
    }
    
    public void setCbHorario(JComboBox<String> cbHorario) {
        this.cbHorario = cbHorario;
    }
    
    public void setCbEps(JComboBox<String> cbEps) {
        this.cbEps = cbEps;
    }
    
    public void initTableRecepcionista() {
        tableModelRecepcionista = new DefaultTableModel(
            new Object[]{"Documento", "Nombres", "Apellidos", "Fecha Nac.", "Sexo", 
                        "EPS", "Email", "Teléfono", "Código Empleado", "Fecha Contratación", "Horario"}, 0) {
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
                JOptionPane.showMessageDialog(null, 
                    "No se encontraron recepcionistas registrados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            for (Recepcionista recepcionista : recepcionistas) {
                Object[] row = {
                    recepcionista.getNumeroDocumento(),
                    recepcionista.getNombres(),
                    recepcionista.getApellidos(),
                    recepcionista.getFechaNacimiento(),
                    recepcionista.getSexo(),
                    recepcionista.getEps(),
                    recepcionista.getEmail(),
                    recepcionista.getCelular(),
                    recepcionista.getCodigoEmpleado(),
                    recepcionista.getFechaContratacion(),
                    recepcionista.getHorario()
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
    
    private LocalDate convertirFecha(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    private boolean validarCamposRecepcionista() {
        if (txtDocumento.getText().trim().isEmpty()) {
            mostrarError("El número de documento es obligatorio", txtDocumento);
            return false;
        }
        
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("Los nombres son obligatorios", txtNombre);
            return false;
        }
        
        if (txtApellido.getText().trim().isEmpty()) {
            mostrarError("Los apellidos son obligatorios", txtApellido);
            return false;
        }
        
        if (dateChooserNacimiento.getDate() == null) {
            mostrarError("La fecha de nacimiento es obligatoria", dateChooserNacimiento);
            return false;
        }
        
        if (cbSexo.getSelectedItem() == null) {
            mostrarError("El sexo es obligatorio", cbSexo);
            return false;
        }
        
        if (cbEps.getSelectedItem() == null) {
            mostrarError("La EPS es obligatoria", cbEps);
            return false;
        }
        
        if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            mostrarError("El correo electrónico no tiene un formato válido", txtEmail);
            return false;
        }
        
        if (txtCelular.getText().trim().isEmpty()) {
            mostrarError("El celular es obligatorio", txtCelular);
            return false;
        }
        
        if (txtCodigoEmpleado.getText().trim().isEmpty()) {
            mostrarError("El código de empleado es obligatorio", txtCodigoEmpleado);
            return false;
        }
        
        if (dateChooserContratacion.getDate() == null) {
            mostrarError("La fecha de contratación es obligatoria", dateChooserContratacion);
            return false;
        }
        
        if (cbHorario.getSelectedItem() == null) {
            mostrarError("El horario es obligatorio", cbHorario);
            return false;
        }
        
        return true;
    }
    
    private void mostrarError(String mensaje, Object componente) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        if (componente instanceof JTextField) {
            ((JTextField) componente).requestFocus();
        } else if (componente instanceof JDateChooser) {
            ((JDateChooser) componente).requestFocus();
        } else if (componente instanceof JComboBox) {
            ((JComboBox<?>) componente).requestFocus();
        }
    }
    
    public void guardarRecepcionistaDesdeFormulario() {
        try {
            if (!validarCamposRecepcionista()) return;
            
            String documento = txtDocumento.getText().trim();
            if (recepcionistaDAO.obtenerPorDocumento(documento) != null) {
                mostrarError("Ya existe un recepcionista con este documento", txtDocumento);
                return;
            }
            
            // Generar contraseña automática
            String contrasena = generadorContraseñas.generarContrasena(10);
            String contrasenaEncriptada = generadorContraseñas.encriptarContrasena(contrasena);
            
            Recepcionista nuevoRecepcionista = new Recepcionista(
                documento,
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                convertirFecha(dateChooserNacimiento.getDate()),
                cbSexo.getSelectedItem().toString(),
                cbEps.getSelectedItem().toString(),
                txtEmail.getText().trim(),
                txtCelular.getText().trim(),
                contrasenaEncriptada,
                txtCodigoEmpleado.getText().trim(),
                convertirFecha(dateChooserContratacion.getDate()),
                cbHorario.getSelectedItem().toString()
            );
            
            if (recepcionistaDAO.guardarRecepcionista(nuevoRecepcionista)) {
                enviarCredenciales(nuevoRecepcionista, contrasena);
                cargarDatosEnTablaRecepcionista();
                limpiarFormulario();
            }
        } catch (Exception e) {
            manejarError("Error al guardar recepcionista", e);
        }    
    }
    
    private void enviarCredenciales(Recepcionista recepcionista, String contrasena) {
        boolean envioExitoso = enviadorCredenciales.enviarCredenciales(
            recepcionista.getEmail(), 
            recepcionista.getNombres() + " " + recepcionista.getApellidos(), 
            contrasena
        );
        
        if (envioExitoso) {
            JOptionPane.showMessageDialog(null, 
                "Credenciales enviadas al correo electrónico", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "No se pudo enviar el correo con las credenciales", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void limpiarFormulario() {
        txtDocumento.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        // Verificar que pwdContraseña no sea null antes de intentar operaciones sobre él
        if (pwdContraseña != null) {
            pwdContraseña.setText("");
        }
        txtCodigoEmpleado.setText("");
        dateChooserNacimiento.setDate(null);
        dateChooserContratacion.setDate(null);
        cbSexo.setSelectedIndex(0);
        cbHorario.setSelectedIndex(0);
        cbEps.setSelectedIndex(0);
    }
    
    public void eliminarRecepcionistaSeleccionado() {
        int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
        if (filaSeleccionada == -1) {
            mostrarAdvertencia("Seleccione un recepcionista de la tabla");
            return;
        }

        String documento = tableModelRecepcionista.getValueAt(filaSeleccionada, 0).toString();
        
        if (confirmarAccion("¿Eliminar al recepcionista con documento " + documento + "?")) {
            try {
                if (recepcionistaDAO.eliminarRecepcionista(documento)) {
                    mostrarExito("Recepcionista eliminado correctamente");
                    cargarDatosEnTablaRecepcionista();
                }
            } catch (Exception e) {
                manejarError("Error al eliminar recepcionista", e);
            }
        }
    }
    
    public void actualizarRecepcionista() {
        try {
            int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
            if (filaSeleccionada == -1) {
                mostrarAdvertencia("Seleccione un recepcionista de la tabla");
                return;
            }

            if (!validarCamposRecepcionista()) return;

            String documentoOriginal = tableModelRecepcionista.getValueAt(filaSeleccionada, 0).toString();
            String documento = txtDocumento.getText().trim();
            
            if (!documentoOriginal.equals(documento)) {
                if (recepcionistaDAO.obtenerPorDocumento(documento) != null) {
                    mostrarError("Ya existe un recepcionista con este documento", txtDocumento);
                    return;
                }
            }

            // Obtener recepcionista actual para mantener la contraseña
            Recepcionista recepcionistaActual = recepcionistaDAO.obtenerPorDocumento(documentoOriginal);
            String contraseña = recepcionistaActual.getContraseña();
            
            // Verificar si pwdContraseña es null antes de intentar obtener su valor
            if (pwdContraseña != null) {
                char[] nuevaContraseña = pwdContraseña.getPassword();
                if (nuevaContraseña != null && nuevaContraseña.length > 0) {
                    contraseña = generadorContraseñas.encriptarContrasena(new String(nuevaContraseña));
                }
            }

            Recepcionista recepcionistaActualizado = new Recepcionista(
                documento,
                txtNombre.getText().trim(),
                txtApellido.getText().trim(),
                convertirFecha(dateChooserNacimiento.getDate()),
                cbSexo.getSelectedItem().toString(),
                cbEps.getSelectedItem().toString(),
                txtEmail.getText().trim(),
                txtCelular.getText().trim(),
                contraseña,
                txtCodigoEmpleado.getText().trim(),
                convertirFecha(dateChooserContratacion.getDate()),
                cbHorario.getSelectedItem().toString()
            );

            if (recepcionistaDAO.actualizarRecepcionista(documentoOriginal, recepcionistaActualizado)) {
                mostrarExito("Recepcionista actualizado exitosamente");
                cargarDatosEnTablaRecepcionista();
                limpiarFormulario();
            }
        } catch (Exception e) {
            manejarError("Error al actualizar recepcionista", e);
        }
    }
    
    public void cargarDatosRecepcionistaEnFormulario() {
        int filaSeleccionada = tablaRecepcionistas.getSelectedRow();
        if (filaSeleccionada != -1) {
            try {
                String documento = tableModelRecepcionista.getValueAt(filaSeleccionada, 0).toString();
                Recepcionista recepcionista = recepcionistaDAO.obtenerPorDocumento(documento);
                
                if (recepcionista != null) {
                    txtDocumento.setText(recepcionista.getNumeroDocumento());
                    txtNombre.setText(recepcionista.getNombres());
                    txtApellido.setText(recepcionista.getApellidos());
                    dateChooserNacimiento.setDate(Date.from(recepcionista.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    cbSexo.setSelectedItem(recepcionista.getSexo());
                    cbEps.setSelectedItem(recepcionista.getEps());
                    txtEmail.setText(recepcionista.getEmail());
                    txtCelular.setText(recepcionista.getCelular());
                    txtCodigoEmpleado.setText(recepcionista.getCodigoEmpleado());
                    dateChooserContratacion.setDate(Date.from(recepcionista.getFechaContratacion().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    cbHorario.setSelectedItem(recepcionista.getHorario());
                    
                    // Verificar que pwdContraseña no sea null antes de intentar operaciones sobre él
                    if (pwdContraseña != null) {
                        pwdContraseña.setText("");
                        pwdContraseña.setToolTipText("Dejar vacío para mantener la contraseña actual");
                    }
                    
                    this.documentoOriginal = documento;
                }
            } catch (Exception e) {
                manejarError("Error al cargar datos del recepcionista", e);
            }
        }
    }
    
    // Asegurarse de que todos los componentes están inicializados antes de usarlos
    public boolean verificarComponentesInicializados() {
        boolean todosInicializados = true;
        
        if (tablaRecepcionistas == null) {
            System.err.println("Error: tablaRecepcionistas no está inicializada");
            todosInicializados = false;
        }
        
        if (txtNombre == null) {
            System.err.println("Error: txtNombre no está inicializado");
            todosInicializados = false;
        }
        
        if (txtApellido == null) {
            System.err.println("Error: txtApellido no está inicializado");
            todosInicializados = false;
        }
        
        if (txtDocumento == null) {
            System.err.println("Error: txtDocumento no está inicializado");
            todosInicializados = false;
        }
        
        if (txtEmail == null) {
            System.err.println("Error: txtEmail no está inicializado");
            todosInicializados = false;
        }
        
        if (txtCelular == null) {
            System.err.println("Error: txtCelular no está inicializado");
            todosInicializados = false;
        }
        
        if (pwdContraseña == null) {
            System.err.println("Error: pwdContraseña no está inicializado");
            todosInicializados = false;
        }
        
        if (txtCodigoEmpleado == null) {
            System.err.println("Error: txtCodigoEmpleado no está inicializado");
            todosInicializados = false;
        }
        
        if (dateChooserNacimiento == null) {
            System.err.println("Error: dateChooserNacimiento no está inicializado");
            todosInicializados = false;
        }
        
        if (dateChooserContratacion == null) {
            System.err.println("Error: dateChooserContratacion no está inicializado");
            todosInicializados = false;
        }
        
        if (cbSexo == null) {
            System.err.println("Error: cbSexo no está inicializado");
            todosInicializados = false;
        }
        
        if (cbHorario == null) {
            System.err.println("Error: cbHorario no está inicializado");
            todosInicializados = false;
        }
        
        if (cbEps == null) {
            System.err.println("Error: cbEps no está inicializado");
            todosInicializados = false;
        }
        
        return todosInicializados;
    }
    
    // Métodos auxiliares para manejo de mensajes
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    
    private boolean confirmarAccion(String mensaje) {
        return JOptionPane.showConfirmDialog(null, mensaje, "Confirmar", 
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
    
    private void manejarError(String mensaje, Exception e) {
        JOptionPane.showMessageDialog(null, mensaje + ": " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}