/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;




import model.Cita;

import com.toedter.calendar.JDateChooser;
import dao.CitaDAO;
import dao.MedicoDAO;
import dao.SalasDAO;
import dao.SedeDAO;

import Listener.CitaListener;


import java.awt.Color;
import java.awt.Component;
import model.Paciente;
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
import model.Salas;
import dao.PacienteDAO;
import DAOImpl.PacienteDAOImpl;
import model.Sede;
import DAOImpl.CitaDAOImpl;
import DAOImpl.MedicoDAOImpl;
import DAOImpl.SalasDAOImpl;
import DAOImpl.SedeDAOImpl;
import java.io.IOException;
import model.Medicamento;





/**
 *
 * @author Maria liz
 */
public class ControllerCitas {
  private DefaultTableModel tableModelCita;
    private String idCitaOriginal;
    private MedicoDAO medicoDAO = new MedicoDAOImpl();
    private PacienteDAO pacienteDAO;
    private SalasDAO salasDAO = new SalasDAOImpl();
    private SedeDAO sedeDAO = new SedeDAOImpl();
    private Paciente pacienteSeleccionado;
    private Medico medicoSeleccionado;
    private DefaultTableModel tableModelMedico;
    private JTable tablaCitas;
    private JLabel txtIdCita;
    private JDateChooser JDateFechaCita;
    private JComboBox<String> cboHoraCita;
    private JComboBox<String> cboEstadoCita;
    private JComboBox<String> cboTipoCita;
    private JComboBox<String> cboMotivoCita;
    private JComboBox<String> cboConsultorio;
    private DefaultTableModel tableModelPaciente;
    private JTable tablePaciente;
    private JLabel txtIdCita2;
    private JDateChooser JDateFechaCita2;
     private JComboBox<String> cboHoraCita2;
    private JComboBox<String> cboEstadoCita2;
    private JComboBox<String> cboTipoCita2;
    private JComboBox<String> cboMotivoCita2;
    private JComboBox<String> cboConsultorio2;
    private JLabel lblMedico;
    private JLabel lblTotalCitas;
    private JLabel lblCitasProgramadas;
    private JLabel lblCitasCanceladas;
    private JLabel lblCitasCompletadas;
    private JTable tableMedico;
    private JLabel lblEspecialidadMedico;
    private JLabel lblNombreMedico;
    private JLabel lblApellidoMedico;
    private JDateChooser fechaConsultarCitaMedico;
    private DefaultTableModel tableModelConsultarMedico;
    private JTable tableConsultarMedico;
    private JComboBox cboSede;
    private JComboBox cboSede2;
    private Sede sedeSeleccionada;
    private Salas salaSeleccionada;
    private ControllerPaciente controllerPaciente;
    private static ControllerCitas instance;
    private final CitaDAO citasDAO;


    
    
   public  ControllerCitas() {
        citasDAO = new CitaDAOImpl();
        medicoDAO = new MedicoDAOImpl();
        salasDAO = new SalasDAOImpl();
        sedeDAO = new SedeDAOImpl();
        pacienteDAO = new PacienteDAOImpl();
    }
public static ControllerCitas getInstance() {
    if (instance == null) {
        instance = new ControllerCitas();
    }
    return instance;
}

private final List<CitaListener> listeners = new ArrayList<>();

public void addCitaListener(CitaListener listener) {
    if (listener != null && !listeners.contains(listener)) {
        listeners.add(listener);
    }
}

public void removeCitaListener(CitaListener listener) {
    listeners.remove(listener);
}

public void notificarCitaAgregada(Cita cita) {
    for (CitaListener listener : listeners) {
        listener.citaAgregada(cita);
    }
}
public List<Cita> obtenerTodasLasCitas() {
        return citasDAO.cargarTodos();
    }
public void setControllerPaciente(ControllerPaciente controllerPaciente) {
    this.controllerPaciente = controllerPaciente;
}
public Cita buscarCitaPorId(String id) {
    return citasDAO.buscarPorId(id);
}
 public void notificarCitaActualizada(Cita cita) {
        for (CitaListener listener : listeners) {
            listener.citaActualizada(cita);
        }
    }
  public void actualizarCita(Cita cita) {
        boolean actualizado = citasDAO.actualizarCita(cita.getIdCita(), cita);
        if (actualizado) {
            System.out.println("Cita actualizada correctamente.");
            notificarCitaActualizada(cita);
        } else {
            System.out.println("No se encontró la cita para actualizar.");
        }
    }



    public void setTableConsultarMedico(JTable tableConsultarMedico) {
        this.tableConsultarMedico = tableConsultarMedico;
    }
   
    public void setTablaMedico(JTable tableMedico) {
        if (tableMedico == null) {
        throw new IllegalArgumentException("La tabla de medico no puede ser nula");
       }
        this.tableMedico = tableMedico;
        this.tableModelMedico = (DefaultTableModel) tableMedico.getModel();
        
    }
    
    public void setTablaCitas(JTable tablaCitas) {
        this.tablaCitas = tablaCitas;
        this.tableModelCita = (DefaultTableModel) tablaCitas.getModel();
    }
    
    public void setCboHoraCita(JComboBox<String> cboHoraCita) {
        this.cboHoraCita = cboHoraCita;
    }
    
    public void setCboEstadoCita(JComboBox<String> cboEstadoCita) {
        this.cboEstadoCita = cboEstadoCita;
    }
    
    public void setCboTipoCita(JComboBox<String> cboTipoCita) {
        this.cboTipoCita = cboTipoCita;
    }
    
    public void setCboConsultorio(JComboBox<String> cboConsultorio) {
        this.cboConsultorio = cboConsultorio;
    }
    public void setJDateFechaCita(JDateChooser JDateFechaCita) {
        this.JDateFechaCita = JDateFechaCita;
        configurarDateChooser();
    }
    
    public void setTxtIdCita(JLabel txtIdCita) {
        this.txtIdCita = txtIdCita;
    }
    
   
     public void setTxtIdCita2(JLabel txtIdCita2) {
        this.txtIdCita2 = txtIdCita2;
    } 
     public void setJDateFechaCita2(JDateChooser JDateFechaCita2) {
        this.JDateFechaCita2 = JDateFechaCita2;
        configurarDateChooser();
    }  
     public void setCboHoraCita2(JComboBox<String> cboHoraCita2) {
        this.cboHoraCita2 = cboHoraCita2;
    }
    
    public void setCboEstadoCita2(JComboBox<String> cboEstadoCita2) {
        this.cboEstadoCita2 = cboEstadoCita2;
    }
    
    public void setCboTipoCita2(JComboBox<String> cboTipoCita2) {
        this.cboTipoCita2 = cboTipoCita2;
    }
    
    public void setCboConsultorio2(JComboBox<String> cboConsultorio2) {
        this.cboConsultorio2 = cboConsultorio2;
    }
     public void setCboMotivoCita2(JComboBox<String> cboMotivoCita2) {
        this.cboMotivoCita2 = cboMotivoCita2;
    } 
    
       public void setLblTotalCitas(JLabel lblTotalCitas) {
        this.lblTotalCitas = lblTotalCitas;
    }
    
    public void setLblCitasProgramadas(JLabel lblCitasProgramadas) {
        this.lblCitasProgramadas = lblCitasProgramadas;
    }
    
    public void setLblCitasCanceladas(JLabel lblCitasCanceladas) {
        this.lblCitasCanceladas = lblCitasCanceladas;
    }
    
    public void setLblCitasCompletadas(JLabel lblCitasCompletadas) {
        this.lblCitasCompletadas = lblCitasCompletadas;
    }

    public void setLblEspecialidadMedico(JLabel lblEspecialidadMedico) {
        this.lblEspecialidadMedico = lblEspecialidadMedico;
    }

    public void setLblNombreMedico(JLabel lblNombreMedico) {
        this.lblNombreMedico = lblNombreMedico;
    }

    public void setLblApellidoMedico(JLabel lblApellidoMedico) {
        this.lblApellidoMedico = lblApellidoMedico;
    }

    public void setCboMotivoCita(JComboBox<String> cboMotivoCita) {
        this.cboMotivoCita = cboMotivoCita;
    }

    public void setCboSede(JComboBox cboSede) {
        this.cboSede = cboSede;
    }

    public void setCboSede2(JComboBox cboSede2) {
        this.cboSede2 = cboSede2;
    }

    
    

    
    public void guardarCitaDesdeFormulario() {
        try {
              if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un paciente primero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }   if (medicoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un medico primero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

          String IdCita = ControllerCitas.getInstance().generarCodigoUnico();
            txtIdCita.setText(IdCita);  
            String fechaStr = JDateFechaCita.getDate().toString();
            String horaCita = cboHoraCita.getSelectedItem().toString();
            String motivo = cboMotivoCita.getSelectedItem().toString();
            String tipo = cboTipoCita.getSelectedItem().toString();
            EstadoCita estado = EstadoCita.valueOf(cboEstadoCita.getSelectedItem().toString()); 
            
           
            
             if (fechaStr.isEmpty() || horaCita.isEmpty() || motivo.isEmpty() || 
            tipo.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
                    
       Date fechaDate = JDateFechaCita.getDate();
     if (fechaDate == null) {
      JOptionPane.showMessageDialog(null, "La fecha de la cita es obligatoria", 
        "Error", JOptionPane.ERROR_MESSAGE);
    return;
}
     String nombreSede = cboSede.getSelectedItem().toString();
if (nombreSede.equals("<Seleccione>")) {
    JOptionPane.showMessageDialog(null,
        "Debe seleccionar una sede válida",
        "Error",
        JOptionPane.ERROR_MESSAGE);
    return;
}

sedeSeleccionada = sedeDAO.buscarPorNombre(nombreSede);
if (sedeSeleccionada == null) {
    JOptionPane.showMessageDialog(null,
        "La sede seleccionada no existe",
        "Error",
        JOptionPane.ERROR_MESSAGE);
    return;
}


String nombreConsultorio = cboConsultorio.getSelectedItem().toString();

if (nombreConsultorio.equals("<Seleccione>")) {
    JOptionPane.showMessageDialog(null, 
        "Debe seleccionar un consultorio válido", 
        "Error", 
        JOptionPane.ERROR_MESSAGE);
    return;
}

salaSeleccionada = salasDAO.buscarPorNombre(nombreConsultorio);

if (salaSeleccionada == null) {
    JOptionPane.showMessageDialog(null, 
        "El consultorio seleccionado no existe", 
        "Error", 
        JOptionPane.ERROR_MESSAGE);
    return;
}



     LocalDate fechaCita = fechaDate.toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDate();
         String documentoMedico = medicoSeleccionado.getNumeroDocumento();
     if (!ControllerCitas.getInstance().medicoTieneCupoEnFecha(documentoMedico, fechaCita)) {
    JOptionPane.showMessageDialog(null,
        "Este médico ya tiene 20 citas asignadas para el día " + fechaCita + ".",
        "Cupo completo",
        JOptionPane.WARNING_MESSAGE);
    return;
    }


       if (existeCitaEnMismaHora(fechaCita, horaCita)) {
            JOptionPane.showMessageDialog(null,
                "Ya existe una cita programada para esta hora. Por favor seleccione otra hora.",
                "Hora no disponible",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean existe = citasDAO.cargarTodos().stream()
    .anyMatch(p -> p.getIdCita() != null && p.getIdCita().equals(IdCita));
            if (existe) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe una cita  con este codigo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Cita nuevaCita = new Cita(
                IdCita, 
                fechaCita, 
                horaCita, 
                motivo, 
                tipo, 
                salaSeleccionada,
                estado, 
                pacienteSeleccionado,
                medicoSeleccionado,
                sedeSeleccionada
                
                
            );
              nuevaCita.setDocumentoPaciente(pacienteSeleccionado.getNumeroDocumento());
              nuevaCita.setDocumentoMedico(medicoSeleccionado.getNumeroDocumento());
            citasDAO.guardarCita(nuevaCita);
            notificarCitaAgregada(nuevaCita);
            actualizarEstadisticasCitas(); 
            JOptionPane.showMessageDialog(null, "Cita guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
          cargarCitasEnTabla();
         
          limpiarCita();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cita: " + e.getMessage(),
                "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }    
    }
     public void setTablePaciente(JTable tablePaciente) {
       if (tablePaciente == null) {
        throw new IllegalArgumentException("La tabla de pacientes no puede ser nula");
       }
       this.tablePaciente = tablePaciente;
       this.tableModelPaciente = (DefaultTableModel) tablePaciente.getModel();
}

public void cargarPacienteEnTabla() {
    if (tablePaciente == null || tableModelPaciente == null) {
        throw new IllegalStateException("La tabla de pacientes no ha sido inicializada.");
    }
    
    tableModelPaciente.setRowCount(0); 

    List<Paciente> pacientes = pacienteDAO.cargarTodos();

    for (Paciente paciente : pacientes) {
        Object[] row = {
            paciente.getNumeroDocumento(),
            paciente.getNombres(),
            paciente.getApellidos(),
            paciente.getEps(),
            paciente.getCelular()
          
        };
        tableModelPaciente.addRow(row);
    }
}
public void seleccionarPaciente() {
    int filaSeleccionada = tablePaciente.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        return;
    }
    
    String documento = tablePaciente.getValueAt(filaSeleccionada, 0).toString();
    
    pacienteSeleccionado = pacienteDAO.buscarPorDocumento(documento);
    
    if (pacienteSeleccionado != null) {
        JOptionPane.showMessageDialog(null,
            "Paciente seleccionado: " + pacienteSeleccionado.getNombres(),
            "Paciente Asignado",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
public void cargarMedicoEnTabla() {
    if (tableMedico == null || tableModelMedico == null) {
        throw new IllegalStateException("La tabla de medicos no ha sido inicializada.");
    }
    
    tableModelMedico.setRowCount(0); 

    List<Medico> medicos = medicoDAO.cargarTodos();

    for (Medico medico : medicos) {
        Object[] row = {
            medico.getNumeroDocumento(),
            medico.getNombres(),
            medico.getApellidos(),
            medico.getEspecialidad(),
            
          
        };
        tableModelMedico.addRow(row);
    }
}
   
public void seleccionarMedico() {
    int filaSeleccionada = tableMedico.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        return; 
    }
    
    String documento = tableMedico.getValueAt(filaSeleccionada, 0).toString(); 
    medicoSeleccionado = medicoDAO.buscarPorDocumentoMedico(documento);
    
    if (medicoSeleccionado != null) {
        JOptionPane.showMessageDialog(null,
            "medico seleccionado: " + medicoSeleccionado.getNombres(),
            "medico Asignado",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
     public  void initTableModelCita() {
    if (tablaCitas == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada");
    }
    
    tableModelCita = new DefaultTableModel(
    new Object[]{ "Documento", "Nombre", "Apellido", "Eps", "Telefono", "Id Cita", "Hora Cita", 
                 "Motivo", "Fecha Cita", "Tipo Cita", "Consultorio", "Estado", 
                 "id Medico", "NombreMedico", "ApellidoMedico", "Especialidad", "Sede" }, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    tablaCitas.setModel(tableModelCita); 
    }
    public void cargarCitasEnTabla() {
    if (tablaCitas == null || tableModelCita == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada.");
    }
    
    tableModelCita.setRowCount(0);
    
    
    List<Cita> citas = citasDAO.cargarTodos();
    for (Cita cita : citas) {

        Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
        Medico medico=medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
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
                medico.getNumeroDocumento(),
                medico.getNombres(),
                medico.getApellidos(),
                medico.getEspecialidad(),
                nombreSede
            };
            tableModelCita.addRow(row);
        }
    }
}
   public void actualizarCita() {
    try {
        int filaSeleccionada = tablaCitas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Seleccione una cita de la tabla para actualizar", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idCitaOriginal = tablaCitas.getValueAt(filaSeleccionada, 5).toString();
        String idCita = txtIdCita2.getText().trim();
        String horaCita = cboHoraCita2.getSelectedItem().toString();
        String motivo = cboMotivoCita2.getSelectedItem().toString();
        String tipo = cboTipoCita2.getSelectedItem().toString();
        EstadoCita estado = EstadoCita.valueOf(cboEstadoCita2.getSelectedItem().toString());   
        String documentoMedico = tablaCitas.getValueAt(filaSeleccionada, 12).toString();

        if (horaCita.isEmpty() || motivo.isEmpty() || 
            tipo.equals("<Seleccione>")) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
                  sedeSeleccionada = sedeDAO.buscarPorNombre(cboSede2.getSelectedItem().toString());
                 salaSeleccionada = salasDAO.buscarPorNombre(cboConsultorio2.getSelectedItem().toString());
        
      
        Date fechaC = JDateFechaCita2.getDate();
        if (fechaC == null) {
            JOptionPane.showMessageDialog(null, 
                "La fecha de la cita es obligatoria", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LocalDate fechaCita2 = fechaC.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
            
        if (existeOtraCitaEnMismaHora(fechaCita2, horaCita, documentoMedico, idCitaOriginal)) {
            JOptionPane.showMessageDialog(null,
                "El médico ya tiene otra cita programada para esta hora. Por favor seleccione otra hora u otro médico.",
                "Hora no disponible",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String documentoPaciente = tablaCitas.getValueAt(filaSeleccionada, 0).toString();

        Cita citaActualizada = new Cita(
            idCita, 
            fechaCita2, 
            horaCita, 
            motivo, 
            tipo, 
            salaSeleccionada,
            estado, 
            pacienteSeleccionado,
            medicoSeleccionado,
            sedeSeleccionada
        );
        
        citaActualizada.setDocumentoPaciente(documentoPaciente);
        citaActualizada.setDocumentoMedico(documentoMedico);
        
        boolean actualizado = citasDAO.actualizarCita(idCitaOriginal, citaActualizada);
        actualizarEstadisticasCitas(); 
        limpiarCita2();
        
        if (actualizado) {
            JOptionPane.showMessageDialog(null,
                "Cita actualizada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            cargarCitasEnTabla();
        } else {
            JOptionPane.showMessageDialog(null,
                "No se pudo actualizar la cita. Verifique los datos.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error al actualizar cita: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    public void limpiarCita() {
        txtIdCita.setText("");
        JDateFechaCita.setDate(null);
        cboHoraCita.setSelectedIndex(0);
        cboMotivoCita.setSelectedIndex(0);
        cboConsultorio.setSelectedIndex(0);
        cboTipoCita.setSelectedIndex(0);
        cboEstadoCita.setSelectedItem("<Seleccione>");
       

    }
    public void limpiarCita2() {
        txtIdCita2.setText("");
        JDateFechaCita2.setDate(null);
        cboHoraCita2.setSelectedIndex(0);
        cboMotivoCita2.setSelectedIndex(0);
        cboConsultorio2.setSelectedIndex(0);
        cboTipoCita2.setSelectedIndex(0);
        cboEstadoCita2.setSelectedItem("<Seleccione>");
        lblNombreMedico.setText("");
        lblApellidoMedico.setText("");
        lblNombreMedico.setText("");
        lblEspecialidadMedico.setText("");
        lblNombreMedico.setText("");
       
      
    }
  
public void buscarCitaPorDocumento(String documentoPaciente) {
    try {
        tableModelCita.setRowCount(0);

        List<Cita> citas = citasDAO.cargarTodos();

        for (Cita cita : citas) {
            if (cita.getDocumentoPaciente() != null && 
                cita.getDocumentoPaciente().toLowerCase().contains(documentoPaciente.toLowerCase())) {
                
                Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
                Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
                String nombreSede = (cita.getSede() != null) ? cita.getSede().getNombreSede() : "No asignada";
               String nombreSala = (cita.getSala() != null) ? cita.getSala().getNombreSala() : "No asignado";

                
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
                        medico.getNumeroDocumento(),
                        medico.getNombres(),
                        medico.getApellidos(),
                        medico.getEspecialidad(),
                        nombreSede 
                      
                    };
                    tableModelCita.addRow(row);
                }
            }
        }

        if (tableModelCita.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, 
                "No se encontraron citas para el paciente con documento: " + documentoPaciente, 
                "Búsqueda sin resultados", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Error al buscar citas: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}     
public void actualizarEstadisticasCitas() {
          List<Cita> citas = citasDAO.cargarTodos();
    
          int totalCitas = citas.size();
          int programadas = 0;
          int canceladas = 0;
          int completadas = 0;
    
          for (Cita cita : citas) {
           switch (cita.getEstado()) {
            case PROGRAMADA:
                programadas++;
                break;
            case CANCELADA:
                canceladas++;
                break;
            case COMPLETADA:
                completadas++;
                break;
            }
         }
    
             if (lblTotalCitas != null) lblTotalCitas.setText(String.valueOf(totalCitas));
             if (lblCitasProgramadas != null) lblCitasProgramadas.setText(String.valueOf(programadas));
             if (lblCitasCanceladas != null) lblCitasCanceladas.setText(String.valueOf(canceladas));
             if (lblCitasCompletadas != null) lblCitasCompletadas.setText(String.valueOf(completadas));
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
 public boolean existeOtraCitaEnMismaHora(LocalDate fecha, String hora, String documentoMedico, String idCitaExcluir) {
    List<Cita> citas = citasDAO.cargarTodos();
    
    for (Cita cita : citas) {
        if (!cita.getIdCita().equals(idCitaExcluir)) { 
            if (cita.getFechaCita().equals(fecha) && 
                cita.getHora().equals(hora) && 
                cita.getDocumentoMedico().equals(documentoMedico) &&
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
    
    tablaCitas.getColumnModel().getColumn(11).setCellRenderer(rendererEstado);
   }

   public void buscarPacientePorDocumento(String documento) {
    if (tablePaciente == null || tableModelPaciente == null) {
        JOptionPane.showMessageDialog(null, "La tabla de pacientes no está inicializada", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

       tablePaciente.clearSelection();

    for (int i = 0; i < tableModelPaciente.getRowCount(); i++) {
        String docTabla = tableModelPaciente.getValueAt(i, 0).toString(); 
        if (docTabla.equals(documento)) {
      
            tablePaciente.setRowSelectionInterval(i, i);
            tablePaciente.scrollRectToVisible(tablePaciente.getCellRect(i, 0, true));
            
          
            pacienteSeleccionado = pacienteDAO.buscarPorDocumento(documento);
            if (pacienteSeleccionado != null) {
                JOptionPane.showMessageDialog(null, 
                    "Paciente encontrado: " + pacienteSeleccionado.getNombres(),
                    "Búsqueda exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            return;
        }
    }

    JOptionPane.showMessageDialog(null, 
        "No se encontró un paciente con el documento: " + documento,
        "Búsqueda sin resultados", 
        JOptionPane.WARNING_MESSAGE);
    }
 public void buscarMedicoPorApellido(String apellido) {
    if (tableMedico == null || tableModelMedico == null) {
        JOptionPane.showMessageDialog(null, 
            "La tabla de médicos no está inicializada", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    boolean encontrado = false;
    
    for (int i = 0; i < tableModelMedico.getRowCount(); i++) {
        String apellidoTabla = tableModelMedico.getValueAt(i, 2).toString();
        if (apellidoTabla.equalsIgnoreCase(apellido)) {
            encontrado = true;
            break; 
        }
    }

    if (encontrado) {
        JOptionPane.showMessageDialog(null,
            "Se encontró  un médico con el apellido: " + apellido,
            "Búsqueda exitosa",
            JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(null,
            "No se encontraron médicos con el apellido: " + apellido,
            "Búsqueda sin resultados",
            JOptionPane.WARNING_MESSAGE);
    }
}
  public void cargarCitasPorPaciente(String documentoPaciente) {
    if (tablaCitas == null || tableModelCita == null) {
        throw new IllegalStateException("La tabla de citas no está inicializada.");
    }

    tableModelCita.setRowCount(0); 

    List<Cita> citasDelPaciente = citasDAO.cargarTodos().stream()
            .filter(cita -> cita.getDocumentoPaciente().equals(documentoPaciente))
            .collect(Collectors.toList());

    if (citasDelPaciente.isEmpty()) {
        JOptionPane.showMessageDialog(null, 
            "No tienes citas programadas.", 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    for (Cita cita : citasDelPaciente) {
        Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
        Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());
        
        if (paciente != null && medico != null) {
            Object[] row = {
                cita.getIdCita(),
                cita.getFechaCita(),
                cita.getHora(),
                cita.getMotivo(),
                cita.getTipoCita(),
                cita.getSala(),
                cita.getEstado().toString(),
                medico.getNombres() + " " + medico.getApellidos(),
                medico.getEspecialidad()
            };
            tableModelCita.addRow(row);
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
            
            List<Sede> sedes = sedeDAO.cargarTodasSedes();
            
            
            
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
public void configurarDateChooser() {
    Date fechaActual = new Date();
    
    if (JDateFechaCita != null) {
        JDateFechaCita.setMinSelectableDate(fechaActual);
        JDateFechaCita.setDateFormatString("yyyy-MM-dd");
    }
    
    if (JDateFechaCita2 != null) {
        JDateFechaCita2.setMinSelectableDate(fechaActual);
        JDateFechaCita2.setDateFormatString("yyyy-MM-dd");
    }
}
public boolean medicoTieneCupoEnFecha(String documentoMedico, LocalDate fecha) {
    return citasDAO.contarCitasPorMedicoYFecha(documentoMedico, fecha) < 20;
}
 public  void initTableModelConsultarCitaMedico() {
    if (tableConsultarMedico == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada");
    }
    
   tableModelConsultarMedico = new DefaultTableModel(
    new Object[]{ "Documento", "Nombre", "Apellido", "Eps", "Telefono", "Id Cita", "Hora Cita", 
                 "Motivo", "Fecha Cita", "Tipo Cita", "Consultorio", "Estado", 
                 "id Medico", "NombreMedico", "ApellidoMedico", "Especialidad", "Sede" }, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    tableConsultarMedico.setModel(tableModelConsultarMedico); 
    }
  

public void cargarCitasPorMedicoYFecha(String nombreApellido, Date fechaSeleccionada) {
    if (tableConsultarMedico == null || tableModelConsultarMedico == null) {
        JOptionPane.showMessageDialog(null,
            "La tabla de citas no está inicializada",
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (nombreApellido == null || nombreApellido.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null,
            "Ingrese el nombre o apellido del médico",
            "Advertencia",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (fechaSeleccionada == null) {
        JOptionPane.showMessageDialog(null,
            "Seleccione una fecha válida",
            "Advertencia",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    tableModelConsultarMedico.setRowCount(0); // Limpiar tabla

    LocalDate fechaLocal = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    List<Cita> todasCitas = ControllerCitas.getInstance().obtenerTodasLasCitas();

    boolean encontroCita = false;

    for (Cita cita : todasCitas) {
        Medico medico = medicoDAO.buscarPorDocumentoMedico(cita.getDocumentoMedico());

        if (medico != null) {
            // Comparación flexible de nombre o apellido
            String nombreCompleto = (medico.getNombres() + " " + medico.getApellidos()).toLowerCase();
            String[] partes = nombreApellido.trim().toLowerCase().split("\\s+");

            boolean coincideNombre = false;
            for (String parte : partes) {
                if (nombreCompleto.contains(parte)) {
                    coincideNombre = true;
                    break;
                }
            }

LocalDate fechaCita = cita.getFechaCita();
            boolean coincideFecha = fechaCita.equals(fechaLocal);

            if (coincideNombre && coincideFecha) {
                Paciente paciente = pacienteDAO.buscarPorDocumento(cita.getDocumentoPaciente());
                if (paciente != null) {
                    tableModelConsultarMedico.addRow(new Object[]{
                        paciente.getNumeroDocumento(),
                        paciente.getNombres(),
                        paciente.getApellidos(),
                        paciente.getEps(),
                        paciente.getCelular(),
                        cita.getIdCita(),
                        cita.getHora(),
                        cita.getMotivo(),
                        fechaCita,
                        cita.getTipoCita(),
                        cita.getSala(),
                        cita.getEstado(),
                        medico.getNumeroDocumento(),
                        medico.getNombres(),
                        medico.getApellidos(),
                        medico.getEspecialidad(),
                        cita.getSede()
                    });
                    encontroCita = true;
                }
            }
        }
    }

    if (!encontroCita) {
        JOptionPane.showMessageDialog(null,
            "No se encontraron citas para ese médico en la fecha seleccionada",
            "Sin resultados",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
 public String generarCodigoUnico() {
    List<Cita> citas = citasDAO.cargarTodos();
    int maxNumero = 0;

    for (Cita cita : citas) {
        try {
            String codigo = cita.getIdCita();
            if (codigo != null && codigo.startsWith("Med.")) {
                int numero = Integer.parseInt(codigo.substring(4));
                if (numero > maxNumero) {
                    maxNumero = numero;
                }
            }
        } catch (NumberFormatException e) {
           
        }
    }
    return String.format("Med.%03d", maxNumero + 1);
}
}
     

