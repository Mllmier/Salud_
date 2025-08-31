/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAOImpl.CitaDAOImpl;
import DAOImpl.EnfermedadesDAOImpl;
import DAOImpl.MedicamentosDAOImpl;
import DAOImpl.MedicoDAOImpl;
import DAOImpl.PacienteDAOImpl;
import DAOImpl.SalasDAOImpl;
import DAOImpl.SedeDAOImpl;

import dao.CitaDAO;

import dao.MedicoDAO;
import dao.PacienteDAO;
import dao.SalasDAO;
import dao.SedeDAO;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Cita;
import model.Enfermedad;
import model.Medico;
import model.Paciente;
import dao.EnfermedadesDAO;

import dao.MedicamentosDAO;
import java.io.IOException;
import java.time.LocalDate;
import javax.swing.JComboBox;
import model.Medicamento;

/**
 *
 * @author Maria liz
 */
public class ControllerCargarMedicosCitas {
    private JTable tableCitasPorMedico;
    private PacienteDAO pacienteDAO;
    private final CitaDAO citasDAO;
ControllerCargarMedicosCitas controller;
    private MedicoDAO medicoDAO ;
  private Medico medicoActual; 
   private String documentoMedicoActual;
   private EnfermedadesDAO enfermedadDAO;
    private DefaultTableModel tableModelEnfermedades;
    private Integer idOriginal;
    private JTable tablaEnfermedades;
     private DefaultTableModel tablaModelMedicamento;
    private MedicamentosDAO medicamentoDAO ;
    private String codMedicamento;
    private JTextField txtCodMedicamento;
    private JTextField txtMedicamento;
    private JTextField txtDescripcion;
    private JTextField txtLaboratorio;
    private JTextField txtCantidad;
    private JTextField txtLote;
    private JTextField txtFechaVencimiento;
    private JComboBox<String> cbDisponible;
    private JTextField txtPrecio;   
    private ControllerCargarMedicosCitas controllerCargarMedico;
    private SalasDAO salasDAO ;
    private SedeDAO sedesDAO ;

    
    private JTable TabladeMedicamentos;
    
    
    private static ControllerCargarMedicosCitas instance;

    private DefaultTableModel tableModelCitasPorMedico;
       public static ControllerCargarMedicosCitas getInstance() {
        if (instance == null) {
            instance = new ControllerCargarMedicosCitas();
        }
        return instance;
    }
      public ControllerCargarMedicosCitas() {
       this.citasDAO = new CitaDAOImpl();
        this.pacienteDAO = new PacienteDAOImpl();
        this.medicoDAO = new MedicoDAOImpl();
        this.sedesDAO = new SedeDAOImpl();
        this.salasDAO = new SalasDAOImpl();
            this.enfermedadDAO = new EnfermedadesDAOImpl();
        this.idOriginal = null;
    }
          public Paciente buscarPacientePorDocumento(String documento) {
        try {
            return pacienteDAO.buscarPorDocumento(documento);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
      
          
    public void setTabladeMedicamentos(JTable TabladeMedicamentos){
        
       this.TabladeMedicamentos = TabladeMedicamentos;
       this.tablaModelMedicamento = (DefaultTableModel) TabladeMedicamentos.getModel();
    }


    
        public void setMedicoActual(Medico medico) {
        this.medicoActual = medico;
    }

public void setDocumentoMedico(String documento) {
    this.documentoMedicoActual = documento;
}


public void setTablaEnfermedades(JTable tabla) {
    this.tablaEnfermedades = tabla;
}

    
      public void setTablaCitas(JTable tableCitasPorMedico) {
        this.tableCitasPorMedico = tableCitasPorMedico;
        this.tableModelCitasPorMedico = (DefaultTableModel) tableCitasPorMedico.getModel();
    }
      
      
     public  void initTableModelCita() {
    if (tableCitasPorMedico == null) {
        throw new IllegalStateException("La tabla de citas no ha sido inicializada");
    }
    
    tableModelCitasPorMedico = new DefaultTableModel(
    new Object[]{ "Documento", "Nombre", "Apellido", "Eps", "Telefono", "Id Cita", "Hora Cita", 
                 "Motivo", "Fecha Cita", "Tipo Cita", "Consultorio", "Estado", 
                  "NombreMedico", "ApellidoMedico", "Especialidad", "Sede" }, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

    tableCitasPorMedico.setModel(tableModelCitasPorMedico); 
    }
   public void cargarCitasMedicoEnTabla() {
    if (tableCitasPorMedico == null) {
        throw new IllegalStateException("La tabla de citas por medico no ha sido inicializada.");
    }

    tableModelCitasPorMedico.setRowCount(0);

    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (cita.getMedico() == null || !cita.getMedico().getNumeroDocumento().equals(documentoMedicoActual)) {
            continue; 
        }

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
            tableModelCitasPorMedico.addRow(row);
        }
    }
}
   public void cargarCitasMedicoPorFecha(Date fechaSeleccionada) {
    if (tableCitasPorMedico == null) {
        throw new IllegalStateException("La tabla de citas por medico no ha sido inicializada.");
    }

    tableModelCitasPorMedico.setRowCount(0); 

    List<Cita> citas = citasDAO.cargarTodos();

    for (Cita cita : citas) {
        if (cita.getMedico() == null || !cita.getMedico().getNumeroDocumento().equals(documentoMedicoActual)) {
            continue;
        }

        if (cita.getFechaCita() == null || !cita.getFechaCita().equals(fechaSeleccionada)) {
            continue;
        }

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
            tableModelCitasPorMedico.addRow(row);
        }
    }
}
    public void initTableEnfermedades() {
        tableModelEnfermedades = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Tipo", "Síntomas", "Causas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            // Asegurar que la columna ID muestre valores enteros
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };
        if (tablaEnfermedades != null) {
            tablaEnfermedades.setModel(tableModelEnfermedades);
        }
    }
    
    public void cargarDatosEnTabla() {
        if (tableModelEnfermedades == null) {
            initTableEnfermedades();
        }
        
        tableModelEnfermedades.setRowCount(0);
        List<Enfermedad> enfermedades = enfermedadDAO.cargarTodasEnfermedades();
        
        for (Enfermedad enfermedad : enfermedades) {
            int id = enfermedad.getIdEnfermedad();
            Object[] row = {
                id,
                enfermedad.getNombre(),
                enfermedad.getTipo(),
                convertirListaAString(enfermedad.getSintomas()),
                convertirListaAString(enfermedad.getCausas())
            };
            tableModelEnfermedades.addRow(row);
            
            System.out.println("Cargando enfermedad ID: " + id + " - " + enfermedad.getNombre());
        }
    }
    
    private String convertirListaAString(List<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return "";
        }
        return String.join(", ", lista);
    }
    

public void buscarEnfermedadesPorNombre(String nombreBuscado) {
    if (tableModelEnfermedades == null || tablaEnfermedades == null) {
        return;
    }

    tableModelEnfermedades.setRowCount(0); 

    List<Enfermedad> enfermedades = enfermedadDAO.cargarTodasEnfermedades();

    for (Enfermedad enfermedad : enfermedades) {
        if (enfermedad.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase())) {
            Object[] row = {
                enfermedad.getIdEnfermedad(),
                enfermedad.getNombre(),
                enfermedad.getTipo(),
                convertirListaAString(enfermedad.getSintomas()),
                convertirListaAString(enfermedad.getCausas())
            };
            tableModelEnfermedades.addRow(row);
        }
    }
}
public void cargarMedicamentosEnTabla() {
    try {
        List<Medicamento> medicamentos = new MedicamentosDAOImpl().cargarTodos();
        tablaModelMedicamento.setRowCount(0); 

        for (Medicamento m : medicamentos) {
            tablaModelMedicamento.addRow(new Object[]{
                m.getIdMedicamento(),
                m.getNombre(),
                m.getDescripcion(),
                m.getLaboratorio(),
                m.getCantidad(),
                m.getLote(),
                m.getFechaVencimiento(),
                m.getDisponible(),
                m.getPrecio()
            });
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar medicamentos: " + e.getMessage());
    }
}
   public void buscarMedicamentos(String criterio) {
    try {
        List<Medicamento> resultados = medicamentoDAO.buscarMedicamentos(criterio);
        
        tablaModelMedicamento.setRowCount(0);
        
        for (Medicamento medicamento : resultados) {
            Object[] row = {
                medicamento.getIdMedicamento(),
                medicamento.getNombre(),
                medicamento.getDescripcion(),
                medicamento.getLaboratorio(),
                medicamento.getCantidad(),
                medicamento.getLote(),
                medicamento.getFechaVencimiento(),
                medicamento.getDisponible(),
                medicamento.getPrecio()
            };
            tablaModelMedicamento.addRow(row);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null,
            "Error al buscar medicamentos: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}


}


    


