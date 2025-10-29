
package Controller;

import DAOImpl.CitaDAOImpl;
import DAOImpl.PacienteDAOImpl;
import Listener.CitaListener;
import model.Cita;
import com.toedter.calendar.JDateChooser;
import dao.CitaDAO;
import dao.MedicoDAO;
import dao.PacienteDAO;
import dao.SalasDAO;
import dao.SedeDAO;


import Listener.CitaListener;

import java.awt.Color;
import java.awt.Component;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Cita.EstadoCita;
import model.Medico;
import model.Paciente;
import model.Salas;
import model.Sede;
import DAOImpl.CitaDAOImpl;

import DAOImpl.CitaDAOImpl;
import DAOImpl.MedicoDAOImpl;
import DAOImpl.SalasDAOImpl;
import DAOImpl.SedeDAOImpl;
import DAOImpl.OrdenMedicaDAOImpl;
import dao.OrdenMedicaDAO;
import model.OrdenMedica;

import DAOImpl.MedicoDAOImpl;
import DAOImpl.PacienteDAOImpl;
import DAOImpl.SalasDAOImpl;
import DAOImpl.SedeDAOImpl;


/**
 *
 * @author Maria liz
 */
public class ControllerCitasPaciente {
    private JLabel  lblIdCita;
    private JLabel lblDocumento;
    private JLabel lblNombre;
    private JLabel lblApellido;
    private JLabel lblEmail;
    private JComboBox cboTipoCita;
    private JLabel lblEspecialidad;
    private JComboBox cboEstado;
    private JComboBox cboMotivoCita;
    private JLabel lblEps;
    private Medico medicoSeleccionado;
    private Paciente pacienteActual;
    private JComboBox cboConsultorio;
    private  Salas salaSeleccinada;
    private JComboBox cboSede;
    private JComboBox cboMedico;
    private JLabel lblEspecialidadMedico;
    private JComboBox cboHoraCita;
    private JTable tableCitas;
    private JDateChooser JDateFechaCita;
    private DefaultTableModel tableModelCitas;
    private Sede sedeSelecccionada;
    private CitaDAO citasDAO ;
    private PacienteDAO pacienteDAO;
    private MedicoDAO medicoDAO=new MedicoDAOImpl();
    private SedeDAO sedesDAO = new SedeDAOImpl();
    private SalasDAO salasDAO = new SalasDAOImpl();
    private JTextField txtIdCita2;
    private JDateChooser jDateChooserCita;
    private JComboBox<String> cboHoraCita2;
    private JComboBox<String> cboMotivoCita2;
    private JComboBox<String> cboTipoCita2;
    private JComboBox<String> lblEstadoCita2;
    private JComboBox<String> cboConsultorio2;
    private JComboBox<String> cboSede2;

    private JComboBox<String> cboMedico2;
    private JLabel lblEspecialidadMedico2;
    private JLabel lblNombrePaciente2;
    private JLabel lblApellidoPaciente2;
    private JLabel lblEmail2;
    private JLabel lblDocumentoPaciente2;
    private JLabel lblEps2;
    private static ControllerCitasPaciente instance;
    private OrdenMedicaDAO ordenMedicaDAO = new OrdenMedicaDAOImpl();



    // Listeners
    private List<CitaListener> listeners = new ArrayList<>();

  public ControllerCitasPaciente() {
       this.citasDAO = new CitaDAOImpl();
        this.pacienteDAO = new PacienteDAOImpl();
        this.medicoDAO = new MedicoDAOImpl();
        this.sedesDAO = new SedeDAOImpl();
        this.salasDAO = new SalasDAOImpl();
        this.ordenMedicaDAO = new OrdenMedicaDAOImpl();

    }
  public OrdenMedica obtenerOrdenMedicaPorCita(String idCita) {
    return ordenMedicaDAO.obtenerPorIdCita(idCita);
}

    

    public static synchronized ControllerCitasPaciente getInstance() {
        if (instance == null) {
            instance = new ControllerCitasPaciente();
        }
        return instance;
    }


    public void addCitaListener(CitaListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeCitaListener(CitaListener listener) {
        listeners.remove(listener);
    }

   public void notificarCitaAgregada(Cita cita) {
    System.out.println("Notificando nueva cita a listeners en ControllerCitas");
    for (CitaListener listener : listeners) {
        listener.citaAgregada(cita);
    }
}

    public void setTxtIdCita2(JTextField txtIdCita2) {
        this.txtIdCita2 = txtIdCita2;
    }

    public void setjDateChooserCita(JDateChooser jDateChooserCita) {
        this.jDateChooserCita = jDateChooserCita;
          configurarDateChooser();
    
    }
     public Paciente buscarPorDocumento(String documento) {
        if (documento == null || documento.isEmpty()) {
            return null;
        }
        return pacienteDAO.buscarPorDocumento(documento);
    }

    public void setCboHoraCita2(JComboBox<String> cboHoraCita2) {
        this.cboHoraCita2 = cboHoraCita2;
    }

    public void setCboMotivoCita2(JComboBox<String> cboMotivoCita2) {
        this.cboMotivoCita2 = cboMotivoCita2;
    }

    public void setCboTipoCita2(JComboBox<String> cboTipoCita2) {
        this.cboTipoCita2 = cboTipoCita2;
    }

    public void setLblEstadoCita2(JComboBox<String> lblEstadoCita2) {
        this.lblEstadoCita2 = lblEstadoCita2;
    }

    public void setCboConsultorio2(JComboBox<String> cboConsultorio2) {
        this.cboConsultorio2 = cboConsultorio2;
    }

    public void setCboSede2(JComboBox<String> cboSede2) {
        this.cboSede2 = cboSede2;
    }

    public void setCboMedico2(JComboBox<String> cboMedico2) {
        this.cboMedico2 = cboMedico2;
    }

    public void setLblEspecialidadMedico2(JLabel lblEspecialidadMedico2) {
        this.lblEspecialidadMedico2 = lblEspecialidadMedico2;
    }

    public void setLblNombrePaciente2(JLabel lblNombrePaciente2) {
        this.lblNombrePaciente2 = lblNombrePaciente2;
    }

    public void setLblApellidoPaciente2(JLabel lblApellidoPaciente2) {
        this.lblApellidoPaciente2 = lblApellidoPaciente2;
    }

    public void setLblEmail2(JLabel lblEmail2) {
        this.lblEmail2 = lblEmail2;
    }

    public void setLblDocumentoPaciente2(JLabel lblDocumentoPaciente2) {
        this.lblDocumentoPaciente2 = lblDocumentoPaciente2;
    }

    public void setLblEps2(JLabel lblEps2) {
        this.lblEps2 = lblEps2;
    }


    public void setPacienteActual(Paciente pacienteActual){
        this.pacienteActual=pacienteActual;
    }
    public void setLblEspecialidadMedico(JLabel lblEspecialidadMedico) {
        this.lblEspecialidadMedico = lblEspecialidadMedico;
         configurarComboMedico();
    }
    
    

    public void setLblDocumento(JLabel lblDocumento) {
        this.lblDocumento = lblDocumento;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public void setLblApellido(JLabel lblApellido) {
        this.lblApellido = lblApellido;
    }

    public void setLblEmail(JLabel lblEmail) {
        this.lblEmail = lblEmail;
    }

    public void setCboTipoCita(JComboBox cboTipoCita) {
        this.cboTipoCita = cboTipoCita;
    }

    public void setLblIdCita(JLabel lblIdCita) {
        this.lblIdCita = lblIdCita;
    }
   public void setJDateFechaCita(JDateChooser JDateFechaCita) {
    this.JDateFechaCita = JDateFechaCita;
   
    

}
    public void LblEspecialidad(JLabel lblEspecialidadMedico) {
        this.lblEspecialidadMedico = lblEspecialidadMedico;
    }

    public void setCboEstado(JComboBox cboEstado) {
        this.cboEstado = cboEstado;
    }

    public void setCboMotivoCita(JComboBox cboMotivoCita) {
        this.cboMotivoCita = cboMotivoCita;
    }

    public void setCLblEps(JLabel lblEps) {
        this.lblEps = lblEps;
    }

    public void setCboConsultorio(JComboBox cboConsultorio) {
        this.cboConsultorio = cboConsultorio;
    }

    public void setCboSede(JComboBox cboSede) {
        this.cboSede = cboSede;
    }

    public void setCboHoraCita(JComboBox cboHoraCita) {
        this.cboHoraCita = cboHoraCita;
    }

   public void setCboMedico(JComboBox cboMedico) {
    this.cboMedico = cboMedico;
    cargarMedicosEnComboBox();          
    configurarComboMedico();             
}
     public boolean cargarYPersistirPaciente(String documento) {
        try {
            this.pacienteActual = pacienteDAO.buscarPorDocumento(documento);
            if (this.pacienteActual == null) {
                System.err.println("Paciente no encontrado en BD: " + documento);
                return false;
            }
            
            if (lblDocumento != null) lblDocumento.setText(pacienteActual.getNumeroDocumento());
            if (lblNombre != null) lblNombre.setText(pacienteActual.getNombres());
            if (lblApellido != null) lblApellido.setText(pacienteActual.getApellidos());
            if (lblEmail != null) lblEmail.setText(pacienteActual.getEmail());
            if (lblEps != null) lblEps.setText(pacienteActual.getEps());
            
            return true;
        } catch (Exception e) {
            System.err.println("Error al cargar paciente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

   
   public void guardarCitaDesdeFormulario() {
    if (pacienteActual == null || pacienteActual.getNumeroDocumento() == null) {
        JOptionPane.showMessageDialog(null, 
            "Error: No se ha cargado correctamente el paciente", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    // Modifica esta parte del método guardarCitaDesdeFormulario:
     if (cboMedico.getSelectedItem() == null || "<Seleccione>".equals(cboMedico.getSelectedItem().toString())) {
    JOptionPane.showMessageDialog(null, "Debe seleccionar un médico", "Error", JOptionPane.ERROR_MESSAGE);
    return;
    }

     System.out.println("Médico seleccionado: " + cboMedico.getSelectedItem());

     String nombreCompleto = cboMedico.getSelectedItem().toString();
      medicoSeleccionado = obtenerMedicoPorNombreCompleto(nombreCompleto);


    try {
        String idCita = generarIdCita();
        Date fechaDate = JDateFechaCita.getDate();
        if (fechaDate == null) {
    JOptionPane.showMessageDialog(null, 
        "Por favor seleccione una fecha válida", 
        "Fecha requerida", 
        JOptionPane.ERROR_MESSAGE);
    JDateFechaCita.requestFocus(); // Enfoca el campo de fecha
    return;
}
        String horaCita = cboHoraCita.getSelectedItem().toString();
        String motivo = cboMotivoCita.getSelectedItem().toString();
        String tipoCita = cboTipoCita.getSelectedItem().toString();
        EstadoCita estado = EstadoCita.valueOf(cboEstado.getSelectedItem().toString());
       if (!estado.equals(EstadoCita.PROGRAMADA)) {
        JOptionPane.showMessageDialog(null,
        "Solo se permite agendar citas con estado PROGRAMADA.",
        "Estado inválido",
        JOptionPane.WARNING_MESSAGE);
    return;
}
     if (cboTipoCita.getSelectedIndex() <= 0 || 
     cboMotivoCita.getSelectedIndex() <= 0 || 
     cboHoraCita.getSelectedIndex() <= 0) {
     JOptionPane.showMessageDialog(null, "Todos los campos deben tener una selección válida", "Error", JOptionPane.ERROR_MESSAGE);
      return;
     }

       if (idCita.isEmpty() || fechaDate == null || horaCita.isEmpty() || motivo.isEmpty() || 
       tipoCita.isEmpty() || tipoCita.equalsIgnoreCase("<Seleccione>")) {
       JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        return;
}


        String nombreSede = cboSede.getSelectedItem().toString();
        if (nombreSede.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una sede válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        sedeSelecccionada = sedesDAO.buscarPorNombre(nombreSede);
        if (sedeSelecccionada == null) {
            JOptionPane.showMessageDialog(null, "La sede seleccionada no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        

        String nombreConsultorio = cboConsultorio.getSelectedItem().toString();
        if (nombreConsultorio.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un consultorio válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        salaSeleccinada = salasDAO.buscarPorNombre(nombreConsultorio);
        if (salaSeleccinada == null) {
            JOptionPane.showMessageDialog(null, "El consultorio seleccionado no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDate fechaCita = fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusMonths(6);

       if (fechaCita.isAfter(limite)) {
        JOptionPane.showMessageDialog(null,
        "No puede agendar una cita con más de 6 meses de anticipación.",
        "Fecha no permitida",
        JOptionPane.WARNING_MESSAGE);
         return;
        }
   
         String nombreCompletoMedico = medicoSeleccionado.getNombres() + " " + medicoSeleccionado.getApellidos();
         String idCitaActual = lblIdCita.getText().trim();
      if (existeOtraCitaEnMismaHora(fechaCita, horaCita, nombreCompletoMedico, idCitaActual)) {
       JOptionPane.showMessageDialog(null,
        "El médico ya tiene una cita en esta fecha y hora. Por favor seleccione otra hora.",
        "Horario no disponible",
        JOptionPane.WARNING_MESSAGE);
         return;
}
        boolean existe = citasDAO.cargarTodos().stream()
            .anyMatch(p -> p.getIdCita() != null && p.getIdCita().equals(idCita));
        if (existe) {
            JOptionPane.showMessageDialog(null,
                "Ya existe una cita con este código",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        Cita nuevaCita = new Cita(
            idCita,
            fechaCita,
            horaCita,
            motivo,
            tipoCita,
            salaSeleccinada,
            estado,
            pacienteActual,
            medicoSeleccionado,
            sedeSelecccionada
        );

        nuevaCita.setDocumentoPaciente(pacienteActual.getNumeroDocumento());
        nuevaCita.setDocumentoMedico(medicoSeleccionado.getNumeroDocumento());

        citasDAO.guardarCita(nuevaCita);
        JOptionPane.showMessageDialog(null, "Cita guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        notificarCitaAgregada(nuevaCita);
        ControllerCitas.getInstance().notificarCitaAgregada(nuevaCita);



        cargarCitasPacienteEnTabla();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al guardar cita: " + e.getMessage(),
            "ERROR", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } 
}



   public void setTablaCitas(JTable tableCitas) {
        this.tableCitas = tableCitas;
        this.tableModelCitas = (DefaultTableModel) tableCitas.getModel();
    }
     public  void initTableModelCita() {
    if (tableCitas == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada");
    }
    
    tableModelCitas = new DefaultTableModel(
    new Object[]{ "Documento", "Nombre", "Apellido", "Eps", "Telefono", "Id Cita", "Hora Cita", 
                 "Motivo", "Fecha Cita", "Tipo Cita", "Consultorio", "Estado", 
                  "NombreMedico", "ApellidoMedico", "Especialidad", "Sede" }, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    tableCitas.setModel(tableModelCitas); 
    }
    public void cargarCitasPacienteEnTabla() {
    if (tableCitas == null || tableModelCitas == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada.");
    }
    
    tableModelCitas.setRowCount(0);
    
    
    List<Cita> citas = citasDAO.cargarTodos();
    for (Cita cita : citas) {
        Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
        Medico medico = cita.getMedico(); 
         String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";
         String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";
        if (paciente != null && medico !=null) {
            Object[] row = {
                paciente.getNumeroDocumento(),
                paciente.getNombres(),
                paciente.getApellidos(),
                paciente.getEps(),
                paciente.getCelular(),
                cita.getIdCita(),
                cita.getHora(),
                cita.getMotivo(),
                cita.getFechaCita(),
                cita.getTipoCita(),
                nombreSala,
                cita.getEstado().toString(),
                medico.getNombres(),
                medico.getApellidos(),
                medico.getEspecialidad(),
                nombreSede
            };
            tableModelCitas.addRow(row);
        }
    }
}  
   public void buscarCitaPorFecha(LocalDate fecha, String documentoPaciente) {
    if (tableCitas == null || tableModelCitas == null) {
        throw new IllegalStateException("La tabla de citas no está inicializada.");
    }

    tableModelCitas.setRowCount(0); 
    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (cita.getFechaCita().isEqual(fecha) &&
            cita.getDocumentoPaciente().equals(documentoPaciente)) {

            Paciente paciente = pacienteDAO.buscarPorDocumento(documentoPaciente);
            Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
            String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";
            String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";

            Object[] row = {
                paciente.getNumeroDocumento(),
                paciente.getNombres(),
                paciente.getApellidos(),
                paciente.getEps(),
                paciente.getCelular(),
                cita.getIdCita(),
                cita.getHora(),
                cita.getMotivo(),
                cita.getFechaCita(),
                cita.getTipoCita(),
                nombreSala,
                cita.getEstado().toString(),
                medico.getNumeroDocumento(),
                medico.getNombres(),
                medico.getApellidos(),
                medico.getEspecialidad(),
                nombreSede
            };

            tableModelCitas.addRow(row);

            if (lblNombre != null) lblNombre.setText(paciente.getNombres());
            if (lblApellido != null) lblApellido.setText(paciente.getApellidos());
            if (lblEmail != null) lblEmail.setText(paciente.getEmail());
            if (lblEps != null) lblEps.setText(paciente.getEps());
            if (cboConsultorio != null) cboConsultorio.setSelectedItem(nombreSala);
            if (cboSede != null) cboSede.setSelectedItem(nombreSede);
            if (cboEstado != null) cboEstado.setSelectedItem(cita.getEstado().toString());
            if (cboHoraCita != null) cboHoraCita.setSelectedItem(cita.getHora());
            if (cboMotivoCita != null) cboMotivoCita.setSelectedItem(cita.getMotivo());
            if (cboTipoCita != null) cboTipoCita.setSelectedItem(cita.getTipoCita());
            if (lblEspecialidadMedico != null) lblEspecialidadMedico.setText(medico.getEspecialidad());
            if (cboMedico != null) cboMedico.setSelectedItem(medico.getNombres());
            if (lblEspecialidadMedico != null) lblEspecialidadMedico.setText(medico.getEspecialidad());
        }
    }

    if (tableModelCitas.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null,
            "No tienes citas en la fecha seleccionada.",
            "Sin resultados",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
public void buscarCitasPorFecha() {
    try {
        if (JDateFechaCita == null) {
            JOptionPane.showMessageDialog(null, 
                "El selector de fecha no está configurado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (JDateFechaCita.getDate() == null) {
            JOptionPane.showMessageDialog(null, 
                "Por favor seleccione una fecha", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate fechaSeleccionada = JDateFechaCita.getDate().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        tableModelCitas.setRowCount(0);

        List<Cita> citasEncontradas = citasDAO.cargarTodos().stream()
            .filter(cita -> cita.getFechaCita().equals(fechaSeleccionada))
            .collect(Collectors.toList());

        if (citasEncontradas.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No hay citas programadas para " + fechaSeleccionada, 
                "Resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Cita cita : citasEncontradas) {
                Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
                Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
                
                if (paciente != null && medico != null) {
                    Object[] row = {
                        paciente.getNumeroDocumento(),
                        paciente.getNombres(),
                        paciente.getApellidos(),
                        paciente.getEps(),
                        paciente.getCelular(),
                        cita.getIdCita(),
                        cita.getHora(),
                        cita.getMotivo(),
                        cita.getFechaCita(),
                        cita.getTipoCita(),
                        cita.getSala() != null ? cita.getSala().getNombreSala() : "No asignado",
                        cita.getEstado().toString(),
                        medico.getNumeroDocumento(),
                        medico.getNombres(),
                        medico.getApellidos(),
                        medico.getEspecialidad(),
                        cita.getSede() != null ? cita.getSede().getNombreSede() : "No asignada"
                    };
                    tableModelCitas.addRow(row);
                }
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al buscar citas: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

public void configurarDateChooser() {
    Date fechaActual = new Date();

    if (jDateChooserCita != null) {
        jDateChooserCita.setMinSelectableDate(fechaActual);
        jDateChooserCita.setDateFormatString("yyyy-MM-dd");
    }

    if (JDateFechaCita != null) {
        JDateFechaCita.setMinSelectableDate(fechaActual);
        JDateFechaCita.setDateFormatString("yyyy-MM-dd");
    }
}

public boolean existeCitaEnMismaHora(LocalDate fecha, String hora) {
    List<Cita> citas = citasDAO.cargarTodos();
    
    for (Cita cita : citas) {
        if (cita.getFechaCita().equals(fecha) && 
            cita.getHora().equals(hora) && 
            cita.getEstado() != EstadoCita.CANCELADA) {
            return true;
        }
    }
    return false;
}
 public boolean existeOtraCitaEnMismaHora(LocalDate fecha, String hora, String nombreCompletoMedico, String idCitaExcluir) {
    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (!cita.getIdCita().equals(idCitaExcluir)) {
            if (cita.getFechaCita().equals(fecha) &&
                cita.getHora().equals(hora) &&
                cita.getMedico() != null &&
                (cita.getMedico().getNombres() + " " + cita.getMedico().getApellidos()).equalsIgnoreCase(nombreCompletoMedico) &&
                cita.getEstado() != EstadoCita.CANCELADA) {
                return true;
            }
        }
    }
    return false;
}

public void configurarColoresTablaCitas() {
    DefaultTableCellRenderer rendererEstado = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(table, value, 
                    isSelected, hasFocus, row, column);
            
            String estado = value != null ? value.toString() : "";
            
            if (isSelected) {
                c.setBackground(new Color(57, 105, 138)); 
                c.setForeground(Color.WHITE);
            } else if ("CANCELADA".equalsIgnoreCase(estado)) {
                c.setBackground(Color.RED);
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(table.getBackground());
                c.setForeground(table.getForeground());
            }
            
            return c;
        }
    };
    
    tableCitas.getColumnModel().getColumn(11).setCellRenderer(rendererEstado);
   }

 
 public void cargarCitasPorPaciente(String documentoPaciente) {
    if (tableCitas == null || tableModelCitas == null) {
        return;
    }

    tableModelCitas.setRowCount(0); 

    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (documentoPaciente.equals(cita.getDocumentoPaciente())) {
            Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
            Medico medico = cita.getMedico();
            String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";
            String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";

            if (paciente != null && medico != null) {
                Object[] row = {
                    paciente.getNumeroDocumento(),
                    paciente.getNombres(),
                    paciente.getApellidos(),
                    paciente.getEps(),
                    paciente.getCelular(),
                    cita.getIdCita(),
                    cita.getHora(),
                    cita.getMotivo(),
                    cita.getFechaCita(),
                    cita.getTipoCita(),
                    nombreSala,
                    cita.getEstado().toString(),
                    medico.getNombres(),
                    medico.getApellidos(),
                    medico.getEspecialidad(),
                    nombreSede
                };
                tableModelCitas.addRow(row);
            }
        }
    }
}


  public void cargarSalasEnComboBox(JComboBox<String> comboBox) {
    if (comboBox == null) {
        System.err.println("Error: El JComboBox de sala es nulo");
        return;
    }

        try {
            comboBox.removeAllItems();
            comboBox.addItem("<Seleccione>");
            
            List<Salas> sala = salasDAO.cargarTodasSalas();
            
            
            
            for (Salas salas : sala) {
                if (salas != null && salas.getNombreSala() != null && !salas.getNombreSala().trim().isEmpty()) {
                    comboBox.addItem(salas.getNombreSala());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar consultorio en ComboBox: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar los consultorio: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
  public void cargarSedesEnComboBox(JComboBox<String> comboBox) {
    if (comboBox == null) {
        System.err.println("Error: El JComboBox de sedes es nulo");
        return;
    }

        try {
            comboBox.removeAllItems();
            comboBox.addItem("<Seleccione>");
            
            List<Sede> sedes = sedesDAO.cargarTodasSedes();
            
            
            
            for (Sede sede : sedes) {
                if (sede != null && sede.getNombreSede() != null && !sede.getNombreSede().trim().isEmpty()) {
                    comboBox.addItem(sede.getNombreSede());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar sedes en ComboBox: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error al cargar las sedes: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


public void inicializarInterfazPaciente(String documentoPaciente) {
    
        cargarDatosPaciente(documentoPaciente);
    }


public void cargarDatosPaciente(String documento) {
  
    try {
        this.pacienteActual = pacienteDAO.buscarPorDocumento(documento);
        
        if (pacienteActual == null) {
            System.err.println("[ERROR] No se encontró paciente con documento: " + documento);
            throw new RuntimeException("Paciente no encontrado");
        }
        
        
        if (lblDocumento != null) lblDocumento.setText(pacienteActual.getNumeroDocumento());
        if (lblNombre != null) lblNombre.setText(pacienteActual.getNombres());
        if (lblApellido != null) lblApellido.setText(pacienteActual.getApellidos());
        if (lblEmail != null) lblEmail.setText(pacienteActual.getEmail());
        if (lblEps != null) lblEps.setText(pacienteActual.getEps());
        
    } catch (Exception e) {
        System.err.println("[ERROR] Error al cargar paciente: " + e.getMessage());
        e.printStackTrace();
        throw new RuntimeException("Error al cargar paciente", e);
    }
}
public Paciente getPacienteSeleccionado() {
    return pacienteActual;
}
public void configurarComboMedico() {
    if (cboMedico == null) return;

    for (ActionListener al : cboMedico.getActionListeners()) {
        cboMedico.removeActionListener(al);
    }

    cboMedico.addActionListener(e -> {
        String seleccion = (String) cboMedico.getSelectedItem();

        if (seleccion != null && !seleccion.equals("<Seleccione>")) {
            medicoSeleccionado = obtenerMedicoPorNombreCompleto(seleccion);

            if (medicoSeleccionado != null && lblEspecialidadMedico != null) {
                lblEspecialidadMedico.setText(medicoSeleccionado.getEspecialidad());
            }
        } else {
            medicoSeleccionado = null;

            if (lblEspecialidadMedico != null) {
                lblEspecialidadMedico.setText("");
            }
        }
    });
}

public void cargarMedicosEnComboBox() {
    if (cboMedico == null) return;

    try {
        cboMedico.removeAllItems();
        cboMedico.addItem("<Seleccione>");

        List<Medico> medicos = medicoDAO.cargarTodos();

        for (Medico medico : medicos) {
            if (medico != null && medico.getNombres() != null && medico.getApellidos() != null) {
                String nombreCompleto = (medico.getNombres() + " " + medico.getApellidos()).trim().replaceAll("\\s+", " ");
                cboMedico.addItem(nombreCompleto);
            }
        }
    } catch (Exception e) {
        System.err.println("Error al cargar médicos en ComboBox: " + e.getMessage());
        e.printStackTrace();
    }
}
public void filtrarMedicosPorEspecialidad(String especialidad) {
    if (cboMedico == null) return;
    
    cboMedico.removeAllItems();
    cboMedico.addItem("<Seleccione>");
    
    if (especialidad == null || especialidad.equals("<Seleccione>")) {
        cargarMedicosEnComboBox();
        return;
    }
    
    List<Medico> medicos = medicoDAO.cargarTodos();
    for (Medico medico : medicos) {
        if (medico.getEspecialidad() != null && 
            medico.getEspecialidad().equalsIgnoreCase(especialidad)) {
            String nombreCompleto = medico.getNombres() + " " + medico.getApellidos();
            cboMedico.addItem(nombreCompleto);
        }
    }
}
public Medico obtenerMedicoPorNombreCompleto(String nombreCompleto) {
    if (nombreCompleto == null || nombreCompleto.isEmpty() || "<Seleccione>".equals(nombreCompleto)) {
        return null;
    }
    
    nombreCompleto = nombreCompleto.trim().replaceAll("\\s+", " ");
    
    List<Medico> medicos = medicoDAO.cargarTodos();
    for (Medico medico : medicos) {
        if (medico != null && medico.getNombres() != null && medico.getApellidos() != null) {
            String nombreCompletoMedico = (medico.getNombres() + " " + medico.getApellidos())
                .trim().replaceAll("\\s+", " ");
                
            if (nombreCompletoMedico.equalsIgnoreCase(nombreCompleto)) {
                return medico;
            }
        }
    }
    System.err.println("No se encontró médico con nombre: " + nombreCompleto);
    return null;
}
 public static void resetInstance() {
        instance = null;
    }
public void cargarDatosDesdeFilaSeleccionada(JTable table, int fila) {
    if (table == null || fila < 0 || fila >= table.getRowCount()) {
        JOptionPane.showMessageDialog(null, "Seleccione una cita válida", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    DefaultTableModel model = (DefaultTableModel) table.getModel();

    ControllerCitasPaciente controller = ControllerCitasPaciente.getInstance();
    controller.cargarSalasEnComboBox(cboConsultorio2);
    controller.cargarSedesEnComboBox(cboSede2);

    lblDocumentoPaciente2.setText(model.getValueAt(fila, 0).toString());
    lblNombrePaciente2.setText(model.getValueAt(fila, 1).toString());
    lblApellidoPaciente2.setText(model.getValueAt(fila, 2).toString());
    lblEps2.setText(model.getValueAt(fila, 3).toString());
    lblEmail2.setText(model.getValueAt(fila, 4).toString());

    txtIdCita2.setText(model.getValueAt(fila, 5).toString());

    cboHoraCita2.setSelectedItem(model.getValueAt(fila, 6).toString());
    cboMotivoCita2.setSelectedItem(model.getValueAt(fila, 7).toString());

    Object fechaObj = model.getValueAt(fila, 8);
    if (fechaObj instanceof LocalDate fechaLocal) {
        Date fecha = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        jDateChooserCita.setDate(fecha);
    } else if (fechaObj instanceof Date fecha) {
        jDateChooserCita.setDate(fecha);
    } else {
        jDateChooserCita.setDate(null);
    }

    cboTipoCita2.setSelectedItem(model.getValueAt(fila, 9).toString());

    cboConsultorio2.setSelectedItem(model.getValueAt(fila, 10).toString());

    lblEstadoCita2.setSelectedItem(model.getValueAt(fila, 11).toString());

    String nombreMedico = model.getValueAt(fila, 12).toString() + " " + model.getValueAt(fila, 13).toString();
    cboMedico2.setSelectedItem(nombreMedico.trim());

    lblEspecialidadMedico2.setText(model.getValueAt(fila, 14).toString());

    cboSede2.setSelectedItem(model.getValueAt(fila, 15).toString());
}
public void actualizarCitaDesdeFormulario(JTable tablaCitas) {
    int filaSeleccionada = tablaCitas.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(null, "Seleccione una cita para modificar", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        if (lblIdCita == null) {
       JOptionPane.showMessageDialog(null, "Error interno: lblIdCita no fue inicializado correctamente.");
        return;
   }

        String idCitaOriginal = tablaCitas.getValueAt(filaSeleccionada, 5).toString();
        String idCitaNueva = lblIdCita.getText().trim();
        String hora = (String) cboHoraCita.getSelectedItem();
        String motivo = (String) cboMotivoCita.getSelectedItem();
        String tipo = (String) cboTipoCita.getSelectedItem();
        EstadoCita estado = EstadoCita.valueOf(cboEstado.getSelectedItem().toString());
        cboEstado.removeAllItems();
        cboEstado.addItem("PROGRAMADA");
        cboEstado.setEnabled(false); 

        String nombreSede = (String) cboSede.getSelectedItem();
        String nombreConsultorio = (String) cboConsultorio.getSelectedItem();
        Date fecha = JDateFechaCita.getDate();
        if (hora == null || hora.equals("<Seleccione>") ||
    motivo == null || motivo.equals("<Seleccione>") ||
    tipo == null || tipo.equals("<Seleccione>") ||
    nombreSede == null || nombreSede.equals("<Seleccione>") ||
    nombreConsultorio == null || nombreConsultorio.equals("<Seleccione>")) {

    JOptionPane.showMessageDialog(null,
        "Debe seleccionar todos los campos: hora, motivo, tipo, sede y consultorio",
        "Error",
        JOptionPane.WARNING_MESSAGE);
    return;
}


        if (fecha == null) {
            JOptionPane.showMessageDialog(null, "Seleccione una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Sede sede = sedesDAO.buscarPorNombre(nombreSede);
        Salas sala = salasDAO.buscarPorNombre(nombreConsultorio);
        LocalDate fechaLocal = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
          LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusMonths(6);

       if (fechaLocal.isAfter(limite)) {
        JOptionPane.showMessageDialog(null,
        "No puede agendar una cita con más de 6 meses de anticipación.",
        "Fecha no permitida",
        JOptionPane.WARNING_MESSAGE);
         return;
        }
       

        Medico medico = obtenerMedicoPorNombreCompleto((String) cboMedico.getSelectedItem());
        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un médico válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cita citaActualizada = new Cita(
            idCitaNueva,
            fechaLocal,
            hora,
            motivo,
            tipo,
            sala,
            estado,
            pacienteActual,
            medico,
            sede
        );

        citaActualizada.setDocumentoPaciente(pacienteActual.getNumeroDocumento());
        citaActualizada.setDocumentoMedico(medico.getNumeroDocumento());

        boolean exito = citasDAO.actualizarCita(idCitaOriginal, citaActualizada);

        if (exito) {
            JOptionPane.showMessageDialog(null, "Cita actualizada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarCitasPorPaciente(pacienteActual.getNumeroDocumento());

        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la cita", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al actualizar cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
public boolean cancelarCitaPorId(String idCita) {
    try {
        List<Cita> citas = citasDAO.cargarTodos();

        for (Cita cita : citas) {
            if (cita.getIdCita().equals(idCita)) {
                cita.setEstado(Cita.EstadoCita.CANCELADA);
                citasDAO.guardarTodos(citas); 
                notificarCitaAgregada(cita);  
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public List<Cita> obtenerCitasPorMedico(String documentoMedico) {
    List<Cita> todasLasCitas = citasDAO.cargarTodos();
    List<Cita> citasMedico = new ArrayList<>();
    
    for (Cita cita : todasLasCitas) {
        if (cita.getDocumentoMedico() != null && 
            cita.getDocumentoMedico().equals(documentoMedico)) {
            citasMedico.add(cita);
        }
    }
    return citasMedico;
}
private String generarIdCita() {
    List<Cita> citas = citasDAO.cargarTodos();
    int maxId = 0;

    for (Cita c : citas) {
        if (c.getIdCita() != null && c.getIdCita().startsWith("Medd")) {
            try {
                int numero = Integer.parseInt(c.getIdCita().substring(4)); // cambiar 5 por 4
                if (numero > maxId) {
                    maxId = numero;
                }
            } catch (NumberFormatException e) {
                // Ignorar IDs no válidos
            }
        }
    }

    return "Medd" + String.format("%04d", maxId + 1);
}

}

