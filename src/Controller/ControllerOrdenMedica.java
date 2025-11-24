/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAOImpl.CitaDAOImpl;
import DAOImpl.OrdenMedicaDAOImpl;
import DAOImpl.PacienteDAOImpl;
import Utilidades.PdfPacienteGenerator;
import dao.CitaDAO;
import dao.MedicoDAO;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Enfermedad;
import model.Medicamento;
import model.Medico;
import model.OrdenMedica;
import model.Paciente;
import dao.OrdenMedicaDAO;
import dao.PacienteDAO;
import dao.SalasDAO;
import dao.SedeDAO;
import exceptions.ValidacionException;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import model.Cita;


/**
 *
 * @author HP
 */
public class ControllerOrdenMedica {
  
private PacienteDAO pacienteDAO = new PacienteDAOImpl(); // Asegúrate de tenerlo

private Medico medicoLogueado;

 private JLabel lblNombre;
 private JLabel lblApellido;
  private JLabel lblEmail;
  private JLabel lblAltura;
  private JLabel txtPeso;
   private JLabel lblFechaNacimiento;
   private JLabel lblTipoSangre;
   private JTextArea txtAntecedentes;
   private JLabel lblCelular;
   private JLabel lblSexo;
   private JLabel lblEps;
   private JTextArea areaDiagnostico;
  private JTextArea textAreareceta;
  private JTextArea txtAreaMedicamentos;
  private JLabel lblFechaCita;
  private JLabel lblHoraCita;
  private JLabel lblIdCita;
  private JLabel lblSede;
  private JLabel lblEstado;
  private JLabel lblMotivo;
  private OrdenMedicaDAO ordenmedica;
  
     private Cita cita; 
    
    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }
    public ControllerOrdenMedica(OrdenMedicaDAO ordenmedica){
     this.ordenmedica = ordenmedica;
   
  }
     private static ControllerOrdenMedica instance;

    private ControllerOrdenMedica() {}

    public static ControllerOrdenMedica getInstance() {
        if (instance == null) {
            instance = new ControllerOrdenMedica();
        }
        return instance;
    }

        public ControllerOrdenMedica(JLabel lblNombre,JLabel lblApellido,JLabel lblEmail,JLabel lblAltura,
                                 JLabel txtPeso,JLabel lblFechaNacimiento,JLabel lblTipoSangre,JTextArea txtAntecedentes,JLabel lblCelular,
                                 JLabel lblSexo,JLabel lblEps,JTextArea areaDiagnostico,JTextArea textAreareceta,JTextArea txtAreaMedicamentos,JLabel lblFechaCita,
                                 JLabel lblHoraCita,Medico medicoLogueado,OrdenMedicaDAO ordenmedica,JLabel lblIdCita,JLabel lblSede,JLabel lblMotivo,JLabel lblEstado){
        
        this.lblNombre = lblNombre;
        this.lblApellido = lblApellido;
        this.lblEmail = lblEmail;
        this.lblAltura = lblAltura;
        this.txtPeso = txtPeso;
        this.lblFechaNacimiento = lblFechaNacimiento;
        this.lblTipoSangre = lblTipoSangre;
        this.txtAntecedentes = txtAntecedentes;
        this.lblCelular = lblCelular;
        this.lblSexo = lblSexo;
        this.lblEps = lblEps;
        this.areaDiagnostico = areaDiagnostico;
        this.txtAreaMedicamentos =txtAreaMedicamentos;
        this.textAreareceta=textAreareceta;
        this.lblFechaCita=lblFechaCita;
        this.lblHoraCita=lblHoraCita;
        this.medicoLogueado= medicoLogueado;
        this.ordenmedica = ordenmedica;  
        this.lblIdCita = lblIdCita;
        this.lblEstado = lblEstado;
        this.lblSede = lblSede;
        this.lblMotivo = lblMotivo;
    
            
           
    
    }
    public OrdenMedica extraerDatosFormulario() {
        String nombre = lblNombre != null ? lblNombre.getText() : "";
        String apellido = lblApellido != null ? lblApellido.getText() : "";
        String email = lblEmail != null ? lblEmail.getText() : "";
        String altura = lblAltura != null ? lblAltura.getText() : "";
        String peso = txtPeso != null ? txtPeso.getText() : "";
        String fechaNacimiento = lblFechaNacimiento != null ? lblFechaNacimiento.getText() : "";
        String tipoSangre = lblTipoSangre != null ? lblTipoSangre.getText() : "";
        String antecedentes = txtAntecedentes != null ? txtAntecedentes.getText() : "";
        String celular = lblCelular != null ? lblCelular.getText() : "";
        String sexo = lblSexo != null ? lblSexo.getText() : "";
        String eps = lblEps != null ? lblEps.getText() : "";
        String diagnostico = areaDiagnostico != null ? areaDiagnostico.getText() : "";
        String receta= textAreareceta != null ? textAreareceta.getText() : "";
        List<String> listaMedicamentos = new ArrayList<>();
      if (txtAreaMedicamentos != null) {
    String texto = txtAreaMedicamentos.getText().trim();
    if (!texto.isEmpty()) {
        String[] medicamentos = texto.split("\\n"); // separa por líneas
        for (String med : medicamentos) {
            if (!med.trim().isEmpty()) {
                listaMedicamentos.add(med.trim());
            }
        }
    }
}
        String fechacita= lblFechaCita != null ? lblFechaCita.getText() : "";
        String horacita=lblHoraCita != null ? lblHoraCita.getText() : "";
        String nombreMedico = medicoLogueado != null ? medicoLogueado.getNombres(): "";
        String apellidoMedico = medicoLogueado != null ? medicoLogueado.getApellidos() : "";
        String especialidad = medicoLogueado != null ? medicoLogueado.getEspecialidad() : "";
       String idCita = lblIdCita != null ? lblIdCita.getText(): "";
       String motivo = lblMotivo != null ? lblMotivo.getText(): "";
       String sede = lblSede != null ? lblSede.getText(): "";
       String estado = lblEstado != null ? lblEstado.getText(): "";

        return new OrdenMedica(
            nombre, apellido, email, altura, peso, fechaNacimiento,
            tipoSangre, antecedentes, celular, sexo, eps, diagnostico,receta,listaMedicamentos,fechacita,horacita,
                nombreMedico,apellidoMedico,especialidad,idCita,sede,motivo,estado
        );
    }
public void descargarPdfOrdenMedica(OrdenMedica orden) {
    try {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Orden Médica");
        chooser.setSelectedFile(new File("OrdenMedica_" + orden.getIdCita() + ".pdf"));

        int opcion = chooser.showSaveDialog(null);
        if (opcion != JFileChooser.APPROVE_OPTION) return;

        String ruta = chooser.getSelectedFile().getAbsolutePath();

        PdfPacienteGenerator.generarOrdenMedica(orden, ruta);

        JOptionPane.showMessageDialog(null,
            "Orden médica guardada correctamente.",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al generar PDF: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

}
