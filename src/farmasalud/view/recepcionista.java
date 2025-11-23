/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package farmasalud.view;

import Listener.CitaListener;
import Controller.ControllerCitas;
import Controller.ControllerCitasPaciente;
import dao.PacienteDAO;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Controller.ControllerPaciente;
import DAOImpl.CitaDAOImpl;
import DAOImpl.MedicoDAOImpl;
import DAOImpl.PacienteDAOImpl;
import Listener.PacienteListener;
import dao.CitaDAO;
import dao.MedicoDAO;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import model.Cita;
import model.Cita.EstadoCita;
import model.Medico;
import model.Persona;
import model.Paciente;

/**
 *
 * @author Maria liz
 */
public class recepcionista extends javax.swing.JFrame implements CitaListener,PacienteListener{

     private DefaultTableModel tableModel;
    private ControllerPaciente controller;
    private DefaultTableModel tableModelCita;
    private DefaultTableModel tableModelConsultarMedico=new DefaultTableModel();
    private MedicoDAO medicoDAO = new MedicoDAOImpl();
    private final ControllerCitas controlllercitas=ControllerCitas.getInstance();
        ControllerCitas controllerCitas = ControllerCitas.getInstance();
    
    public recepcionista() {
        initComponents();
       this.controllerCitas = ControllerCitas.getInstance();
        ControllerCitas.getInstance().addCitaListener(this);
   ControllerCitasPaciente.getInstance().addCitaListener(this);
   
         controller =ControllerPaciente.getInstance(); 
         controllerCitas.setTableConsultarMedico(tableConsultarMedico);
         controllerCitas.initTableModelConsultarCitaMedico();
         if (tablePaciente == null) {
        throw new IllegalStateException("La tabla tablePaciente no está inicializada en el diseño");
    }
          if (tablePaciente == null || tablaCitas == null) {
            throw new IllegalStateException("Las tablas no están inicializadas en el diseño");
        }
       
        configurarPacientes();
        controllerCitas.setTablePaciente(tablePaciente);
        controllerCitas.cargarPacienteEnTabla();
    
       configurarCitas();
        controllerCitas.actualizarEstadisticasCitas();
        this.setTitle("Sistema de Recepción");
        this.setLocationRelativeTo(null);
         if (txtIdCita == null || JDateFechaNacimiento == null) {
            JOptionPane.showMessageDialog(this,
                "Error: Componentes críticos no inicializados",
                "Error Grave",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
         btnBuscarCita.addActionListener(e -> buscarCitaPorDocumento());
    }
    
@Override
public void citaAgregada(Cita cita) {
    System.out.println("Listener recibido en recepcionista, recargando tabla...");
     controllerCitas.cargarCitasEnTabla();
}
@Override
public void citaEliminada(String idCita) {
    System.out.println("Listener recibido en recepcionista: cita eliminada con id " + idCita);
    controllerCitas.cargarCitasEnTabla(); 
}
@Override
public void citaActualizada(Cita cita) {
    System.out.println("Listener recibido en recepcionista: cita actualizada " + cita);
    controllerCitas.cargarCitasEnTabla(); 
}



    private void configurarPacientes() {
        try {

            controller.setTablaPacientes(tablaPacientes);
            controller.setTxtNombre(txtPriNombreR);
            controller.setTxtApellido(txtPriApellidoR);
            controller.setTxtDocumento(txtDocumentoR);
            controller.setTxtEmail(txtEmailR);
            controller.setDateChooserNacimiento(JDateFechaNacimiento);      
            controller.setTxtCelular(txtCelularR);
            controller.setCbSexo(cbSexo);
            controller.setCbEps(cbEps);
            controller.setCbTipoDocumento(cbTipoDocumento);
            controller.setCbTipoSangre(cboTipoSangre);
            controller.setTxtAreaAntecedentes(jTextArea2);
            controller.setTxtContraseña(txtContraseña);
            controller.setTxtPeso(txtPeso);
            controller.setTxtAltura(txtAltura);

            controller.initTablePaciente();
                        controller.setTablaPacientes(tablaPacientes);

            controller.cargarDatosEnTablaPaciente();
    

            tablaPacientes.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    controller.cargarDatosPacienteEnFormulario();
                }
            });

        } catch (Exception e) {
            mostrarError("Error configurando pacientes: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
private void configurarCitas(){
    controllerCitas.setTablaCitas(tablaCitas);
    controllerCitas.initTableModelCita();
                

   
        controllerCitas.setTablePaciente(tablePaciente);
        controllerCitas.setTablaCitas(tablaCitas);
        controllerCitas.setTxtIdCita(txtIdCita);
        controllerCitas.setJDateFechaCita(JDateFechaCita);    
        controllerCitas.setCboHoraCita(cboHoraCita);
        controllerCitas.setCboTipoCita(cboTipoCita);
        controllerCitas.setCboMotivoCita(cboMotivoCita);
        controllerCitas.setCboEstadoCita(cboEstadoCita);
        controllerCitas.cargarSalasEnComboBox(lblConsultorio);
        controllerCitas.cargarMedicosPorSedeYEspecialidad(cbNombreApellidoMedico, cboSede, cboEspecialidadMedico,lblConsultorio);
        controllerCitas.cargarSedesEnComboBox(cboSede);

     
       
        controllerCitas.setCboSede(cboSede);
        controllerCitas.setLblConsultorio(lblConsultorio);        
        controllerCitas.cargarPacienteEnTabla();
        controllerCitas.cargarCitasEnTabla();
        controllerCitas.cargarCitasEnTabla();

        
    controllerCitas.setTxtIdCita2(txtIdCita2);
    controllerCitas.setJDateFechaCita2(JDateFechaCita2);      
    controllerCitas.setCboHoraCita(cboHoraCita);
    controllerCitas.setCboHoraCita2(cboHoraCita2);
    controllerCitas.setCboMotivoCita2(cboMotivoCita2);
    controllerCitas.setCboTipoCita2(cboTipoCita2);
    controllerCitas.setCboEstadoCita2(cboEstadoCita2);
    controllerCitas.setLblEps2(cboEps2);
   controllerCitas.setLblNumeroDocumento2(txtNumeroDocumento2);
   
    controllerCitas.cargarSedesEnComboBox(cboSede2);
        cboEstadoCita.removeAllItems();
    cboEstadoCita.addItem("PROGRAMADA");
    cboEstadoCita.setSelectedIndex(0);
    cboEstadoCita.setEnabled(false);
     cboEstadoCita2.removeAllItems();
    cboEstadoCita2.addItem("PROGRAMADA");
    cboEstadoCita2.addItem("CANCELADA");
    cboEstadoCita2.setSelectedIndex(0);
    cboEstadoCita2.setEnabled(true);  
    controllerCitas.cargarSalasEnComboBox(lblConsultorio2);
    controllerCitas.cargarEspecialidadesPorSede(cboEspecialidadMedico2, cboSede2);
     controllerCitas.cargarMedicosPorSedeYEspecialidad(cbNombreApellidoMedico, cboSede, cboEspecialidadMedico,lblConsultorio);
     controllerCitas.setCboMedico(cboMedico);
   
   
      cboSede.addActionListener(e -> {
      controllerCitas.cargarEspecialidadesPorSede(cboEspecialidadMedico, cboSede);
     });

     cboEspecialidadMedico.addActionListener(e -> {
         controllerCitas.cargarMedicosPorSedeYEspecialidad(cbNombreApellidoMedico, cboSede, cboEspecialidadMedico,lblConsultorio);
      });

   
      cboSede2.addActionListener(e -> {
        controllerCitas.cargarEspecialidadesPorSede(cboEspecialidadMedico2, cboSede2);
     });

      cboEspecialidadMedico2.addActionListener(e -> {
        controllerCitas.cargarMedicosPorSedeYEspecialidad(cboMedico, cboSede2, cboEspecialidadMedico2,lblConsultorio2);
        });

    controllerCitas.cargarCitasEnTabla();
    controllerCitas.cargarPacienteEnTabla();
       controllerCitas.setCboSede2(cboSede2);
        controllerCitas.setLblConsultorio2(lblConsultorio2);
    controllerCitas.actualizarEstadisticasCitas();
    controllerCitas.configurarDateChooser();

    tablePaciente.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            controllerCitas.seleccionarPaciente();
        }
    });
   
    tablaCitas.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting() && tablaCitas.getSelectedRow() != -1) {
      cargarDatosCitaEnFormularioActualizacion();
        }
    });

  

controllerCitas.setLblCitasProgramadas(lblCitasProgramadas);
controllerCitas.setLblCitasCanceladas(lblCitasCanceladas);
controllerCitas.setLblCitasCompletadas(lblCitasCompletadas);


             controllerCitas.configurarColoresTablaCitas();    
    }
private void cargarDatosCitaEnFormularioActualizacion() {
    int filaSeleccionada = tablaCitas.getSelectedRow();
    if (filaSeleccionada == -1) return;

    try {
        // 📋 Datos básicos del paciente
        txtNumeroDocumento2.setText(tablaCitas.getValueAt(filaSeleccionada, 0).toString());
        txtNombre2.setText(tablaCitas.getValueAt(filaSeleccionada, 1).toString());
        txtApellido2.setText(tablaCitas.getValueAt(filaSeleccionada, 2).toString());
        cboEps2.setText(tablaCitas.getValueAt(filaSeleccionada, 3).toString());
        txtEmail2.setText(tablaCitas.getValueAt(filaSeleccionada, 4).toString());
        txtIdCita2.setText(tablaCitas.getValueAt(filaSeleccionada, 5).toString());

        // 📅 Fecha y hora
        Object fechaValue = tablaCitas.getValueAt(filaSeleccionada, 8);
        if (fechaValue != null) {
            try {
                LocalDate fecha = (fechaValue instanceof LocalDate)
                        ? (LocalDate) fechaValue
                        : LocalDate.parse(fechaValue.toString());
                JDateFechaCita2.setDate(java.sql.Date.valueOf(fecha));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Error al cargar la fecha: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        cboHoraCita2.setSelectedItem(tablaCitas.getValueAt(filaSeleccionada, 6).toString());
        cboMotivoCita2.setSelectedItem(tablaCitas.getValueAt(filaSeleccionada, 7).toString());
        cboTipoCita2.setSelectedItem(tablaCitas.getValueAt(filaSeleccionada, 9).toString());
        lblConsultorio2.setText(tablaCitas.getValueAt(filaSeleccionada, 10).toString());
        cboEstadoCita2.setSelectedItem(tablaCitas.getValueAt(filaSeleccionada, 11).toString());

        // 🧑‍⚕️ Datos del médico
        String nombreMedicoStr = tablaCitas.getValueAt(filaSeleccionada, 13).toString().trim();
        String especialidadMedicoStr = tablaCitas.getValueAt(filaSeleccionada, 14).toString().trim();
        String sedeStr = tablaCitas.getValueAt(filaSeleccionada, 15).toString().trim();

     
        cboSede2.setSelectedItem(sedeStr);

        controllerCitas.cargarEspecialidadesPorSede(cboEspecialidadMedico2, cboSede2);

        cboEspecialidadMedico2.setSelectedItem(especialidadMedicoStr);

        controllerCitas.cargarMedicosPorSedeYEspecialidad(cboMedico, cboSede2, cboEspecialidadMedico2, lblConsultorio2);
        boolean encontrado = false;
        for (int i = 0; i < cboMedico.getItemCount(); i++) {
            String item = cboMedico.getItemAt(i);
            if (item != null && item.equalsIgnoreCase(nombreMedicoStr)) {
                cboMedico.setSelectedIndex(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            cboMedico.addItem(nombreMedicoStr);
            cboMedico.setSelectedItem(nombreMedicoStr);
        }

    } catch (Exception e) {
        mostrarError("Error al cargar datos de la cita: " + e.getMessage());
        e.printStackTrace();
    }
}


    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

private void buscarCitaPorDocumento() {
    String documentoABuscar = txtBuscarIdCita.getText().trim();
    if (documentoABuscar.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Ingrese un número de documento para buscar", 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    controllerCitas.buscarCitaPorDocumento(documentoABuscar);
}
 @Override
public void actualizar(Paciente pacienteActualizado) {
    DefaultTableModel model = (DefaultTableModel) tablaPacientes.getModel();
    int colPeso = model.findColumn("Peso");
    int colAltura = model.findColumn("Altura");

    if (colPeso != -1 && colAltura != -1) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String cedula = model.getValueAt(i, 0).toString();
            if (cedula.equals(pacienteActualizado.getNumeroDocumento())) {
                model.setValueAt(pacienteActualizado.getPeso(), i, colPeso);
                model.setValueAt(pacienteActualizado.getAltura(), i, colAltura);
                break;
            }
        }
    }
}



public void verificarDisponibilidadHoraActualizacion() {
    Date fecha = JDateFechaCita2.getDate();
    if (fecha == null || cboHoraCita2.getSelectedItem() == null || txtIdCita2.getText().isEmpty()) return;
    
    LocalDate fechaCita = fecha.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate();
    String hora = cboHoraCita2.getSelectedItem().toString();
    String idCitaActual = txtIdCita2.getText().trim();
    String documentoMedico = tablaCitas.getValueAt(tablaCitas.getSelectedRow(), 12).toString();
    
    if (controllerCitas.existeOtraCitaEnMismaHora(fechaCita, hora, documentoMedico, idCitaActual)) {
        JOptionPane.showMessageDialog(this,
            "El médico ya tiene otra cita programada para esta hora",
            "Hora ocupada",
            JOptionPane.WARNING_MESSAGE);
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        agendar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblNombreRecepcion1 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        panelBtnInicio1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        panelBtnAgendar1 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        AgendarPaciente = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        panelBtnInformes1 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        panelCitasMedico = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        TabbetCitas = new javax.swing.JTabbedPane();
        panelInicio = new javax.swing.JPanel();
        lblCitasProgramadas = new javax.swing.JLabel();
        lblCitasCanceladas = new javax.swing.JLabel();
        lblCitasCompletadas = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        panelModificarCita = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaCitas = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        txtBuscarIdCita = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        btnBuscarCita = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel47 = new javax.swing.JLabel();
        cboTipoCita2 = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel48 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel49 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        cboHoraCita2 = new javax.swing.JComboBox<>();
        jSeparator6 = new javax.swing.JSeparator();
        btnActualizarCita = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cboMotivoCita2 = new javax.swing.JComboBox<>();
        jSeparator14 = new javax.swing.JSeparator();
        cboEstadoCita2 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jSeparator16 = new javax.swing.JSeparator();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        cboEps2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jSeparator19 = new javax.swing.JSeparator();
        jSeparator20 = new javax.swing.JSeparator();
        txtNombre2 = new javax.swing.JLabel();
        JDateFechaCita2 = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        cboSede2 = new javax.swing.JComboBox<>();
        txtApellido2 = new javax.swing.JLabel();
        txtEmail2 = new javax.swing.JLabel();
        txtNumeroDocumento2 = new javax.swing.JLabel();
        txtIdCita2 = new javax.swing.JLabel();
        jSeparator21 = new javax.swing.JSeparator();
        cboMedico = new javax.swing.JComboBox<>();
        cboEspecialidadMedico2 = new javax.swing.JComboBox<>();
        lblConsultorio2 = new javax.swing.JLabel();
        panelGuardarPaciente = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        cbTipoDocumento = new javax.swing.JComboBox<>();
        jSeparator7 = new javax.swing.JSeparator();
        txtDocumentoR = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        txtPriNombreR = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        txtPriApellidoR = new javax.swing.JTextField();
        jSeparator23 = new javax.swing.JSeparator();
        txtTelefono = new javax.swing.JLabel();
        txtCelularR = new javax.swing.JTextField();
        jSeparator29 = new javax.swing.JSeparator();
        jLabel67 = new javax.swing.JLabel();
        txtEmailR = new javax.swing.JTextField();
        jSeparator22 = new javax.swing.JSeparator();
        jLabel65 = new javax.swing.JLabel();
        jSeparator28 = new javax.swing.JSeparator();
        jLabel66 = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        cbEps = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jSeparator24 = new javax.swing.JSeparator();
        jSeparator27 = new javax.swing.JSeparator();
        cboTipoSangre = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        btnguardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        JDateFechaNacimiento = new com.toedter.calendar.JDateChooser();
        txtContraseña = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtAltura = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPacientes = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableConsultarMedico = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        txtMedico = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jDateConsultarCitaMedico = new com.toedter.calendar.JDateChooser();
        btnConsultarCitaMedico = new javax.swing.JButton();
        panelAgendar = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        cboTipoCita = new javax.swing.JComboBox<>();
        cboHoraCita = new javax.swing.JComboBox<>();
        jLabel62 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        btnAgendarCita = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboEstadoCita = new javax.swing.JComboBox<>();
        cboMotivoCita = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cboSede = new javax.swing.JComboBox<>();
        jSeparator25 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();
        cbNombreApellidoMedico = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator26 = new javax.swing.JSeparator();
        jSeparator30 = new javax.swing.JSeparator();
        JDateFechaCita = new com.toedter.calendar.JDateChooser();
        txtIdCita = new javax.swing.JLabel();
        jSeparator31 = new javax.swing.JSeparator();
        jSeparator32 = new javax.swing.JSeparator();
        jSeparator33 = new javax.swing.JSeparator();
        jSeparator34 = new javax.swing.JSeparator();
        jSeparator35 = new javax.swing.JSeparator();
        jSeparator36 = new javax.swing.JSeparator();
        jSeparator37 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        cboEspecialidadMedico = new javax.swing.JComboBox<>();
        lblConsultorio = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        refrecarTablaPaciente = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        txtDocumentoPaciente = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        btnBuscarPaciente = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePaciente = new javax.swing.JTable();
        jLabel79 = new javax.swing.JLabel();

        jPopupMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPopupMenu1MouseClicked(evt);
            }
        });

        agendar.setText("Agendar Cita");
        agendar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agendarMouseClicked(evt);
            }
        });
        agendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(agendar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 60, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 170, -1));

        jPanel3.setBackground(new java.awt.Color(28, 43, 110));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblNombreRecepcion1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNombreRecepcion1.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreRecepcion1.setText("Recepcionista");
        jPanel3.add(lblNombreRecepcion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 230, -1));

        jSeparator9.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator9.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 230, 20));

        panelBtnInicio1.setBackground(new java.awt.Color(28, 43, 110));
        panelBtnInicio1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelBtnInicio1MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelBtnInicio1MouseMoved(evt);
            }
        });
        panelBtnInicio1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBtnInicio1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelBtnInicio1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelBtnInicio1MouseExited(evt);
            }
        });
        panelBtnInicio1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 21)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Inicio");
        panelBtnInicio1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 15, 110, 28));
        panelBtnInicio1.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 40));

        jPanel3.add(panelBtnInicio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 300, 60));

        panelBtnAgendar1.setBackground(new java.awt.Color(28, 43, 110));
        panelBtnAgendar1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelBtnAgendar1MouseMoved(evt);
            }
        });
        panelBtnAgendar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBtnAgendar1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelBtnAgendar1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelBtnAgendar1MouseExited(evt);
            }
        });
        panelBtnAgendar1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 21)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Agender cita");
        panelBtnAgendar1.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 15, 138, 28));

        jPanel3.add(panelBtnAgendar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 300, 60));

        AgendarPaciente.setBackground(new java.awt.Color(28, 43, 110));
        AgendarPaciente.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                AgendarPacienteMouseMoved(evt);
            }
        });
        AgendarPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AgendarPacienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AgendarPacienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AgendarPacienteMouseExited(evt);
            }
        });
        AgendarPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 21)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(255, 255, 255));
        jLabel58.setText("Agendar Paciente ");
        AgendarPaciente.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 15, 180, 28));
        AgendarPaciente.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 40));

        jPanel3.add(AgendarPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 300, 60));

        panelBtnInformes1.setBackground(new java.awt.Color(28, 43, 110));
        panelBtnInformes1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panelBtnInformes1MouseMoved(evt);
            }
        });
        panelBtnInformes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelBtnInformes1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelBtnInformes1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelBtnInformes1MouseExited(evt);
            }
        });
        panelBtnInformes1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel73.setFont(new java.awt.Font("Segoe UI", 0, 21)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Modificar Cita");
        panelBtnInformes1.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 15, 138, 28));
        panelBtnInformes1.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, 40));

        jPanel3.add(panelBtnInformes1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 300, 60));

        panelCitasMedico.setBackground(new java.awt.Color(28, 43, 110));
        panelCitasMedico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCitasMedicoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelCitasMedicoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelCitasMedicoMouseExited(evt);
            }
        });

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Citas Por Medico");

        javax.swing.GroupLayout panelCitasMedicoLayout = new javax.swing.GroupLayout(panelCitasMedico);
        panelCitasMedico.setLayout(panelCitasMedicoLayout);
        panelCitasMedicoLayout.setHorizontalGroup(
            panelCitasMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCitasMedicoLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );
        panelCitasMedicoLayout.setVerticalGroup(
            panelCitasMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCitasMedicoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel31)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel3.add(panelCitasMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 300, 60));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 770));

        jPanel9.setBackground(new java.awt.Color(10, 92, 184));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("CITAS ");
        jPanel9.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 530, 40));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 1000, 130));

        TabbetCitas.setBackground(new java.awt.Color(255, 255, 255));

        panelInicio.setBackground(new java.awt.Color(255, 255, 255));
        panelInicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCitasProgramadas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelInicio.add(lblCitasProgramadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 110, 110, 40));

        lblCitasCanceladas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelInicio.add(lblCitasCanceladas, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 230, 110, 40));

        lblCitasCompletadas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelInicio.add(lblCitasCompletadas, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 360, 110, 40));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setText("Citas Programadas");
        panelInicio.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, -1, -1));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("Citas Canceladas");
        panelInicio.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 200, 150, 20));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setText("Citas Completadas");
        panelInicio.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 310, -1, -1));

        TabbetCitas.addTab("Inicio", panelInicio);

        panelModificarCita.setBackground(new java.awt.Color(255, 255, 255));
        panelModificarCita.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablaCitas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaCitas.getTableHeader().setResizingAllowed(false);
        tablaCitas.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tablaCitas);

        panelModificarCita.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 940, 180));

        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel44.setText("LISTADO DE CITAS");
        jPanel21.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 261, 31));

        txtBuscarIdCita.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jPanel21.add(txtBuscarIdCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 174, 31));
        jPanel21.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 2, -1, 32));

        btnBuscarCita.setText("Buscar");
        btnBuscarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCitaActionPerformed(evt);
            }
        });
        jPanel21.add(btnBuscarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Documento Paciente");
        jPanel21.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 8, 180, -1));

        jButton1.setText("refrescar");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(335, 11, -1, -1));

        panelModificarCita.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 940, 50));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel46.setText("Nombre ");
        jPanel5.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 6, -1, -1));

        jSeparator2.setBackground(new java.awt.Color(10, 92, 184));
        jSeparator2.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 73, 121, 10));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel47.setText("Tipo de cita");
        jPanel5.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        cboTipoCita2.setBackground(new java.awt.Color(0, 0, 0, 0));
        cboTipoCita2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "Prioritaria", "Regular", "Control" }));
        cboTipoCita2.setBorder(null);
        jPanel5.add(cboTipoCita2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 120, 40));

        jSeparator3.setBackground(new java.awt.Color(10, 92, 184));
        jSeparator3.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 140, 10));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel48.setText("Especialidad");
        jPanel5.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 110, -1));

        jSeparator4.setBackground(new java.awt.Color(10, 92, 184));
        jSeparator4.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 140, 10));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel49.setText("Fecha Cita:");
        jPanel5.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 120, -1));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel43.setText("Hora");
        jPanel5.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 60, -1));

        cboHoraCita2.setBackground(new java.awt.Color(0, 0, 0, 0));
        cboHoraCita2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00" }));
        cboHoraCita2.setBorder(null);
        cboHoraCita2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHoraCita2ActionPerformed(evt);
            }
        });
        jPanel5.add(cboHoraCita2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 130, 40));

        jSeparator6.setBackground(new java.awt.Color(10, 92, 184));
        jSeparator6.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 110, 0));

        btnActualizarCita.setBackground(new java.awt.Color(10, 92, 184));
        btnActualizarCita.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnActualizarCita.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarCita.setText("Actualizar");
        btnActualizarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarCitaMouseClicked(evt);
            }
        });
        btnActualizarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCitaActionPerformed(evt);
            }
        });
        jPanel5.add(btnActualizarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 190, 134, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Motivo Cita");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 104, -1));

        cboMotivoCita2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Control", "Seguimiento", "Prevencion", "Sintomas Agudos", "Enfermedad Cronica", "Problemas Especificos" }));
        cboMotivoCita2.setBorder(null);
        jPanel5.add(cboMotivoCita2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 30, 150, 40));

        jSeparator14.setBackground(new java.awt.Color(10, 92, 184));
        jSeparator14.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 70, 150, 10));

        cboEstadoCita2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "PROGRAMADA", "COMPLETADA", "CANCELADA" }));
        cboEstadoCita2.setBorder(null);
        cboEstadoCita2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstadoCita2ActionPerformed(evt);
            }
        });
        jPanel5.add(cboEstadoCita2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 110, 150, 40));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Estado Cita");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 105, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Apellido ");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 6, 94, -1));

        jSeparator11.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 73, 135, 10));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Eps");
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(327, 6, 60, -1));

        jSeparator12.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(297, 70, 120, 10));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Telefono");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 100, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Documento");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 108, -1));

        jSeparator13.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(427, 70, 130, 10));

        jSeparator15.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 140, 10));

        jSeparator16.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 120, 10));

        jSeparator17.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 130, 10));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Consultorio");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, -1, -1));

        jSeparator18.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator18, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 230, 120, 10));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Id Cita");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 71, -1));
        jPanel5.add(cboEps2, new org.netbeans.lib.awtextra.AbsoluteConstraints(299, 37, 120, 40));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText(" Medico");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 140, -1));

        jSeparator19.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator19, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 150, 150, 10));

        jSeparator20.setBackground(new java.awt.Color(10, 92, 184));
        jSeparator20.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 130, 10));
        jPanel5.add(txtNombre2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 40));
        jPanel5.add(JDateFechaCita2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 140, 40));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("Sede");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 40, -1));

        cboSede2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSede2.setBorder(null);
        jPanel5.add(cboSede2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 130, 40));
        jPanel5.add(txtApellido2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 140, 40));
        jPanel5.add(txtEmail2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 120, 40));
        jPanel5.add(txtNumeroDocumento2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 30, 140, 40));
        jPanel5.add(txtIdCita2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, 110, 40));

        jSeparator21.setBackground(new java.awt.Color(10, 92, 184));
        jSeparator21.setForeground(new java.awt.Color(10, 92, 184));
        jPanel5.add(jSeparator21, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 130, 10));

        cboMedico.setBorder(null);
        jPanel5.add(cboMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 120, 40));

        cboEspecialidadMedico2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cboEspecialidadMedico2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 140, 40));

        lblConsultorio2.setText("jLabel19");
        jPanel5.add(lblConsultorio2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 140, 40));

        panelModificarCita.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 940, 250));

        TabbetCitas.addTab("Modificar Cita ", panelModificarCita);

        panelGuardarPaciente.setBackground(new java.awt.Color(255, 255, 255));
        panelGuardarPaciente.setForeground(new java.awt.Color(255, 255, 255));
        panelGuardarPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel13.setAutoscrolls(true);
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbTipoDocumento.setBackground(new java.awt.Color(0, 0, 0, 0));
        cbTipoDocumento.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        cbTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RC", "TI", "CC" }));
        cbTipoDocumento.setBorder(null);
        cbTipoDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoDocumentoActionPerformed(evt);
            }
        });
        jPanel13.add(cbTipoDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 7, 70, 30));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 60, 20));

        txtDocumentoR.setBackground(new java.awt.Color(0, 0, 0, 0));
        txtDocumentoR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDocumentoR.setBorder(null);
        txtDocumentoR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDocumentoRFocusLost(evt);
            }
        });
        txtDocumentoR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoRActionPerformed(evt);
            }
        });
        txtDocumentoR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDocumentoRKeyTyped(evt);
            }
        });
        jPanel13.add(txtDocumentoR, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, 150, 30));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 150, 10));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText(" Nombre*");
        jPanel13.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 10, 97, -1));

        txtPriNombreR.setBackground(new java.awt.Color(0, 0, 0, 0));
        txtPriNombreR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPriNombreR.setBorder(null);
        txtPriNombreR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriNombreRActionPerformed(evt);
            }
        });
        txtPriNombreR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriNombreRKeyTyped(evt);
            }
        });
        jPanel13.add(txtPriNombreR, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 160, 30));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 160, 10));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setText("Apellido*");
        jPanel13.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, 30));

        txtPriApellidoR.setBackground(new java.awt.Color(0, 0, 0, 0));
        txtPriApellidoR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtPriApellidoR.setBorder(null);
        txtPriApellidoR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriApellidoRActionPerformed(evt);
            }
        });
        txtPriApellidoR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriApellidoRKeyTyped(evt);
            }
        });
        jPanel13.add(txtPriApellidoR, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 177, 30));

        jSeparator23.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator23.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator23, new org.netbeans.lib.awtextra.AbsoluteConstraints(714, 44, 180, 10));

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtTelefono.setText("Celular*");
        jPanel13.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, 80, -1));

        txtCelularR.setBackground(new java.awt.Color(0, 0, 0, 0));
        txtCelularR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtCelularR.setBorder(null);
        txtCelularR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCelularRFocusLost(evt);
            }
        });
        txtCelularR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularRActionPerformed(evt);
            }
        });
        txtCelularR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularRKeyTyped(evt);
            }
        });
        jPanel13.add(txtCelularR, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 160, 40));

        jSeparator29.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator29.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator29, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 170, 10));

        jLabel67.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel67.setText("Email*");
        jPanel13.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, 72, -1));

        txtEmailR.setBackground(new java.awt.Color(0, 0, 0, 0));
        txtEmailR.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtEmailR.setBorder(null);
        txtEmailR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailRFocusLost(evt);
            }
        });
        txtEmailR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailRActionPerformed(evt);
            }
        });
        txtEmailR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailRKeyTyped(evt);
            }
        });
        jPanel13.add(txtEmailR, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 160, 40));

        jSeparator22.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator22.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator22, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, 160, 10));

        jLabel65.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel65.setText(" Fecha Nacimiento*");
        jPanel13.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jSeparator28.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator28.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, 180, 10));

        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel66.setText("Sexo*");
        jPanel13.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 61, -1));

        cbSexo.setBackground(new java.awt.Color(0, 0, 0, 0));
        cbSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "M", "F" }));
        cbSexo.setBorder(null);
        cbSexo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbSexoFocusLost(evt);
            }
        });
        cbSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSexoActionPerformed(evt);
            }
        });
        jPanel13.add(cbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, 180, 40));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel34.setText("Eps*");
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 110, 50, -1));

        cbEps.setBackground(new java.awt.Color(0, 0, 0, 0));
        cbEps.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "Coosalud", "Sanistas", "Comfamiliar", "Nueva Eps" }));
        cbEps.setBorder(null);
        cbEps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEpsActionPerformed(evt);
            }
        });
        jPanel13.add(cbEps, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 100, 180, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Grupo Sangre");
        jPanel13.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jSeparator24.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator24.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator24, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 200, 10));

        jSeparator27.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator27.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 180, 10));

        cboTipoSangre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "A+", "A-", "0+", "O-", "B+", "B-", "AB+", "AB-" }));
        cboTipoSangre.setBorder(null);
        cboTipoSangre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoSangreActionPerformed(evt);
            }
        });
        jPanel13.add(cboTipoSangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 200, 40));

        jSeparator1.setForeground(new java.awt.Color(10, 92, 184));
        jPanel13.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 160, 10));

        btnguardar.setBackground(new java.awt.Color(10, 92, 184));
        btnguardar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnguardar.setForeground(new java.awt.Color(255, 255, 255));
        btnguardar.setText("Guardar");
        btnguardar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnguardarFocusLost(evt);
            }
        });
        btnguardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnguardarMouseClicked(evt);
            }
        });
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });
        btnguardar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnguardarKeyTyped(evt);
            }
        });
        jPanel13.add(btnguardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 210, -1, 34));

        btnModificar.setBackground(new java.awt.Color(10, 92, 184));
        btnModificar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel13.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, -1, 35));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane6.setViewportView(jTextArea2);

        jPanel13.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 210, 80));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Antecedentes");
        jPanel13.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 140, 29));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Documento*");
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 12, -1, -1));
        jPanel13.add(JDateFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 180, 40));
        jPanel13.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 232, -1, 0));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("Peso ");
        jPanel13.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 160, -1, -1));

        txtPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesoActionPerformed(evt);
            }
        });
        jPanel13.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 180, 40));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel30.setText("Altura");
        jPanel13.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, -1, -1));

        txtAltura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlturaActionPerformed(evt);
            }
        });
        jPanel13.add(txtAltura, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 160, 40));

        panelGuardarPaciente.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 940, 260));

        tablaPacientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tablaPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaPacientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPacientesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaPacientes);

        panelGuardarPaciente.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 302, 920, 200));

        TabbetCitas.addTab("AgendarPacientes", panelGuardarPaciente);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableConsultarMedico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableConsultarMedico.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        tableConsultarMedico.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableConsultarMedico.setRowSelectionAllowed(false);
        tableConsultarMedico.setSelectionBackground(new java.awt.Color(0, 102, 204));
        jScrollPane1.setViewportView(tableConsultarMedico);

        jPanel6.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 950, 390));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel32.setText("Medico");

        txtMedico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMedicoKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setText("Fecha Consultar  ");

        btnConsultarCitaMedico.setText("Buscar");
        btnConsultarCitaMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarCitaMedicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jDateConsultarCitaMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(btnConsultarCitaMedico)
                .addGap(40, 40, 40))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnConsultarCitaMedico)
                            .addComponent(jDateConsultarCitaMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(txtMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 930, 60));

        TabbetCitas.addTab("Citas Por Medico", jPanel6);

        panelAgendar.setBackground(new java.awt.Color(255, 255, 255));
        panelAgendar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel42.setText("Fecha de Cita*");
        jPanel8.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, -1, -1));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel41.setText("Tipo de cita*");
        jPanel8.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 90, -1, -1));

        cboTipoCita.setBackground(new java.awt.Color(0, 0, 0, 0));
        cboTipoCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "Prioritaria", "Regular", "Control" }));
        cboTipoCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTipoCitaActionPerformed(evt);
            }
        });
        jPanel8.add(cboTipoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 160, 50));

        cboHoraCita.setBackground(new java.awt.Color(0, 0, 0, 0));
        cboHoraCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "07:00 am", "07:30am", "08:00am", "08:30am", "09:00am", "09:30am", "10:00am", "10:30am", "11:00am", "11:30am", "12:00pm", "01:30pm", "02:00pm", "02:30pm", "03:00pm", "03:30pm", "04:00pm", "04:30pm" }));
        cboHoraCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHoraCitaActionPerformed(evt);
            }
        });
        jPanel8.add(cboHoraCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 120, 150, 50));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel62.setText("Hora*");
        jPanel8.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, -1, -1));

        jLabel71.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel71.setText("Consultorio*");
        jPanel8.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        btnAgendarCita.setBackground(new java.awt.Color(10, 92, 184));
        btnAgendarCita.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnAgendarCita.setForeground(new java.awt.Color(255, 255, 255));
        btnAgendarCita.setText("AGENDAR");
        btnAgendarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgendarCitaMouseClicked(evt);
            }
        });
        btnAgendarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarCitaActionPerformed(evt);
            }
        });
        jPanel8.add(btnAgendarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 190, -1, 29));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Estado de la cita");
        jPanel8.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 154, 20));

        cboEstadoCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "PROGRAMADA", "COMPLETADA", "CANCELADA" }));
        jPanel8.add(cboEstadoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 150, 40));

        cboMotivoCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccionar>", "Control", "Seguimiento", "Prevencion", "Sintomas Agudos", "Enfermedad Cronica", "Problemas Especificos" }));
        cboMotivoCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMotivoCitaActionPerformed(evt);
            }
        });
        jPanel8.add(cboMotivoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 40, 160, 40));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Motivo Cita ");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, -1, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("Sede");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        cboSede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSedeActionPerformed(evt);
            }
        });
        jPanel8.add(cboSede, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 150, 50));
        jPanel8.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 140, 0));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel35.setText("Medico");
        jPanel8.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

        jPanel8.add(cbNombreApellidoMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, 160, 50));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel36.setText("Especialidad ");
        jPanel8.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, -1, -1));

        jSeparator5.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 160, 10));

        jSeparator26.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 150, 10));

        jSeparator30.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 10));
        jPanel8.add(JDateFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 160, 40));
        jPanel8.add(txtIdCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 130, 140, 40));

        jSeparator31.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator31, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 160, -1));

        jSeparator32.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 160, -1));

        jSeparator33.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator33, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 160, -1));

        jSeparator34.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator34, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 160, 10));

        jSeparator35.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator35, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, 150, 10));

        jSeparator36.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator36, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, 140, 10));

        jSeparator37.setForeground(new java.awt.Color(10, 92, 184));
        jPanel8.add(jSeparator37, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 170, 140, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setText("Id Cita");
        jPanel8.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 100, -1, -1));

        jPanel8.add(cboEspecialidadMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 160, 50));
        jPanel8.add(lblConsultorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 160, 50));

        panelAgendar.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 900, 230));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel38.setBackground(new java.awt.Color(0, 0, 0));
        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel38.setText("Datos de la Cita");

        refrecarTablaPaciente.setText("refrescar");
        refrecarTablaPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refrecarTablaPacienteMouseClicked(evt);
            }
        });
        refrecarTablaPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refrecarTablaPacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(284, 284, 284)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 252, Short.MAX_VALUE)
                .addComponent(refrecarTablaPaciente)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(refrecarTablaPaciente)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAgendar.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 900, -1));

        txtDocumentoPaciente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtDocumentoPaciente.setBorder(null);
        txtDocumentoPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoPacienteActionPerformed(evt);
            }
        });
        txtDocumentoPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDocumentoPacienteKeyTyped(evt);
            }
        });

        jLabel70.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel70.setText("Documento*");

        btnBuscarPaciente.setText("Buscar");
        btnBuscarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPacienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel70)
                .addGap(29, 29, 29)
                .addComponent(txtDocumentoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(btnBuscarPaciente)
                .addContainerGap(336, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocumentoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel70)
                    .addComponent(btnBuscarPaciente))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panelAgendar.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 21, 880, 60));

        tablePaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Documento", "Nombre", "Apellido", "Eps", "Telefono"
            }
        ));
        jScrollPane2.setViewportView(tablePaciente);

        panelAgendar.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 86, 900, 110));

        TabbetCitas.addTab("Agendar", panelAgendar);

        jPanel1.add(TabbetCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 970, 600));

        jLabel79.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel79.setText("X");
        jPanel1.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 10, 20, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelBtnInicio1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInicio1MouseMoved
     
    }//GEN-LAST:event_panelBtnInicio1MouseMoved

    private void panelBtnInicio1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInicio1MouseClicked
        TabbetCitas.setSelectedIndex(0);
    }//GEN-LAST:event_panelBtnInicio1MouseClicked

    private void panelBtnInicio1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInicio1MouseExited
        panelBtnInicio1.setBackground(new Color(28,43,110));
    }//GEN-LAST:event_panelBtnInicio1MouseExited

    private void panelBtnAgendar1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnAgendar1MouseMoved
    }//GEN-LAST:event_panelBtnAgendar1MouseMoved

    private void panelBtnAgendar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnAgendar1MouseClicked
        TabbetCitas.setSelectedIndex(2);
    }//GEN-LAST:event_panelBtnAgendar1MouseClicked

    private void panelBtnAgendar1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnAgendar1MouseExited
        panelBtnAgendar1.setBackground(new Color(28,43,110));
    }//GEN-LAST:event_panelBtnAgendar1MouseExited

    private void AgendarPacienteMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendarPacienteMouseMoved
      
    }//GEN-LAST:event_AgendarPacienteMouseMoved

    private void AgendarPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendarPacienteMouseClicked
        TabbetCitas.setSelectedIndex(3);
    }//GEN-LAST:event_AgendarPacienteMouseClicked

    private void AgendarPacienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendarPacienteMouseExited
        AgendarPaciente.setBackground(new Color(28,43,110));
    }//GEN-LAST:event_AgendarPacienteMouseExited

    private void panelBtnInformes1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInformes1MouseMoved
       
    }//GEN-LAST:event_panelBtnInformes1MouseMoved

    private void panelBtnInformes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInformes1MouseClicked
        TabbetCitas.setSelectedIndex(1);
    }//GEN-LAST:event_panelBtnInformes1MouseClicked

    private void panelBtnInformes1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInformes1MouseExited
        panelBtnInformes1.setBackground(new Color(28,43,110));
    }//GEN-LAST:event_panelBtnInformes1MouseExited

    private void panelBtnInicio1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInicio1MouseEntered
        panelBtnInicio1.setBackground(new Color(10,92,184));

    }//GEN-LAST:event_panelBtnInicio1MouseEntered

    private void panelBtnInicio1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInicio1MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_panelBtnInicio1MouseDragged

    private void panelBtnAgendar1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnAgendar1MouseEntered
        panelBtnAgendar1.setBackground(new Color(10,92,184));
    }//GEN-LAST:event_panelBtnAgendar1MouseEntered

    private void agendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendarActionPerformed
for (int i = 0; i < TabbetCitas.getTabCount(); i++) {
    if ("Agendar".equals(TabbetCitas.getTitleAt(i))) { // Reemplaza "Agendar" con el título real
        TabbetCitas.setSelectedIndex(i);
        break;
    }
}
    }//GEN-LAST:event_agendarActionPerformed

    private void agendarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agendarMouseClicked
    // TODO add your handling code here:
    }//GEN-LAST:event_agendarMouseClicked

    private void jPopupMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPopupMenu1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPopupMenu1MouseClicked

    private void tablaPacientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPacientesMouseClicked
        if (SwingUtilities.isRightMouseButton(evt)) {
            jPopupMenu1.show(tablaPacientes, evt.getX(), evt.getY());
        }       // TODO add your handling code here:
    }//GEN-LAST:event_tablaPacientesMouseClicked

    private void btnBuscarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPacienteActionPerformed
 String documento = txtDocumentoPaciente.getText().trim();
    if (documento.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Ingrese un número de documento", 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    controllerCitas.buscarPacientePorDocumento(documento);
    }//GEN-LAST:event_btnBuscarPacienteActionPerformed

    private void txtDocumentoPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoPacienteActionPerformed

    }//GEN-LAST:event_txtDocumentoPacienteActionPerformed

    private void cboMotivoCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMotivoCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMotivoCitaActionPerformed

    private void btnAgendarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarCitaActionPerformed
        controllerCitas.guardarCitaDesdeFormulario();
    }//GEN-LAST:event_btnAgendarCitaActionPerformed

    private void btnAgendarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgendarCitaMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgendarCitaMouseClicked

    private void cboHoraCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHoraCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHoraCitaActionPerformed

    private void cboTipoCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoCitaActionPerformed

    private void cboEstadoCita2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstadoCita2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEstadoCita2ActionPerformed

    private void btnActualizarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCitaActionPerformed
       controllerCitas.actualizarCita();
        if (cboTipoCita2.getSelectedItem() == null
            || cboTipoCita2.getSelectedItem().toString().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Rellene todos los campos obligatorios");
            return;
        }
      
    }//GEN-LAST:event_btnActualizarCitaActionPerformed

    private void btnActualizarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarCitaMouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarCitaMouseClicked

    private void cboHoraCita2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHoraCita2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHoraCita2ActionPerformed

    private void btnBuscarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarCitaActionPerformed

    private void txtPriApellidoRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriApellidoRKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten letras", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtPriApellidoRKeyTyped

    private void txtPriApellidoRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriApellidoRActionPerformed

    }//GEN-LAST:event_txtPriApellidoRActionPerformed

    private void txtEmailRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailRKeyTyped

    }//GEN-LAST:event_txtEmailRKeyTyped

    private void txtEmailRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailRActionPerformed

    private void txtEmailRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailRFocusLost
        String correo = txtEmailR.getText().trim();
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Correo inválido. debe ingresar @", "Error", JOptionPane.ERROR_MESSAGE);
            txtEmailR.requestFocus();
        }
    }//GEN-LAST:event_txtEmailRFocusLost

    private void btnguardarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnguardarKeyTyped

    }//GEN-LAST:event_btnguardarKeyTyped

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        if (txtDocumentoR.getText().trim().isEmpty()
            || txtPriNombreR.getText().trim().isEmpty()
            || txtPriApellidoR.getText().trim().isEmpty()
            || txtEmailR.getText().trim().isEmpty()
            || txtCelularR.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Rellene todos los campos obligatorios");
            return;
        }
        if (!txtPriApellidoR.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Solo se permiten letras en el apellido", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (!txtPriNombreR.getText().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Solo se permiten letras en el Nombre", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnguardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnguardarMouseClicked

        controller.guardarPacienteDesdeFormulario();
    }//GEN-LAST:event_btnguardarMouseClicked

    private void btnguardarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnguardarFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_btnguardarFocusLost

    private void cbEpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEpsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEpsActionPerformed

    private void cbSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSexoActionPerformed

    }//GEN-LAST:event_cbSexoActionPerformed

    private void cbSexoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusLost

    }//GEN-LAST:event_cbSexoFocusLost

    private void cboTipoSangreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTipoSangreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboTipoSangreActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        controller.actualizarPaciente();        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtCelularRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularRKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != '.') {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten números", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtCelularRKeyTyped

    private void txtCelularRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularRActionPerformed

    private void txtCelularRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularRFocusLost
        if (txtTelefono.getText().length() == 10) {
            JOptionPane.showMessageDialog(null, "Debe ingresar exactamente 10 números",
                "Error", JOptionPane.WARNING_MESSAGE);
            txtTelefono.requestFocus();
        }
    }//GEN-LAST:event_txtCelularRFocusLost

    private void txtDocumentoRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoRKeyTyped
        String documento = txtDocumentoR.getText().trim();

        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
            return;
        }
        if (documento.length() >= 7) {
            if (!documento.matches("^\\d{6,9}$")) {
                JOptionPane.showMessageDialog(
                    null,
                    "Documento inválido. Solo se permiten números de 7 o 10 dígitos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtDocumentoRKeyTyped

    private void txtDocumentoRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoRActionPerformed

    }//GEN-LAST:event_txtDocumentoRActionPerformed

    private void txtDocumentoRFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDocumentoRFocusLost

    }//GEN-LAST:event_txtDocumentoRFocusLost

    private void cbTipoDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoDocumentoActionPerformed

    private void txtPriNombreRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriNombreRKeyTyped
        
    }//GEN-LAST:event_txtPriNombreRKeyTyped

    private void txtPriNombreRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriNombreRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriNombreRActionPerformed

    private void refrecarTablaPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refrecarTablaPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refrecarTablaPacienteActionPerformed

    private void refrecarTablaPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refrecarTablaPacienteMouseClicked
       controllerCitas.cargarPacienteEnTabla();
    }//GEN-LAST:event_refrecarTablaPacienteMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    controllerCitas.cargarCitasEnTabla();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
 controllerCitas.cargarCitasEnTabla();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

    private void txtFechaCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaCitaActionPerformed

    private void AgendarPacienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AgendarPacienteMouseEntered
   AgendarPaciente.setBackground(new Color(10,92,184));        // TODO add your handling code here:
    }//GEN-LAST:event_AgendarPacienteMouseEntered

    private void panelBtnInformes1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBtnInformes1MouseEntered
     panelBtnInformes1.setBackground(new Color(10,92,184));    // TODO add your handling code here:
    }//GEN-LAST:event_panelBtnInformes1MouseEntered

    private void cboSedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSedeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSedeActionPerformed

    private void btnConsultarCitaMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarCitaMedicoActionPerformed
       String nombreApellido = txtMedico.getText();
    Date fechaSeleccionada = jDateConsultarCitaMedico.getDate();

    if (nombreApellido == null || nombreApellido.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el nombre o apellido del médico", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (fechaSeleccionada == null) {
        JOptionPane.showMessageDialog(this, "Seleccione una fecha válida", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    tableModelConsultarMedico.setRowCount(0);

    controllerCitas.cargarCitasPorMedicoYFecha(nombreApellido, fechaSeleccionada);



    }//GEN-LAST:event_btnConsultarCitaMedicoActionPerformed

    private void txtMedicoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMedicoKeyTyped
char c = evt.getKeyChar();
      if (Character.isISOControl(c)) {
        return;
    }

        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
            evt.consume(); 
            JOptionPane.showMessageDialog(null, "Solo se permiten letras", "Entrada inválida", JOptionPane.WARNING_MESSAGE);
        }      
    }//GEN-LAST:event_txtMedicoKeyTyped

    private void txtDocumentoPacienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDocumentoPacienteKeyTyped
  char c = evt.getKeyChar();

        if (!Character.isDigit(c)) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten números", "Entrada inválida", JOptionPane.WARNING_MESSAGE);
        }       
    }//GEN-LAST:event_txtDocumentoPacienteKeyTyped

    private void panelCitasMedicoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCitasMedicoMouseEntered
    panelCitasMedico.setBackground(new Color(10,92,184));       // TODO add your handling code here:
    }//GEN-LAST:event_panelCitasMedicoMouseEntered

    private void panelCitasMedicoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCitasMedicoMouseExited
panelCitasMedico.setBackground(new Color(28,43,110));   
    }//GEN-LAST:event_panelCitasMedicoMouseExited

    private void panelCitasMedicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCitasMedicoMouseClicked
TabbetCitas.setSelectedIndex(4);       // TODO add your handling code here:
    }//GEN-LAST:event_panelCitasMedicoMouseClicked

    private void txtPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesoActionPerformed

    private void txtAlturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlturaActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_txtAlturaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(recepcionista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(recepcionista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(recepcionista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(recepcionista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new recepcionista().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AgendarPaciente;
    private com.toedter.calendar.JDateChooser JDateFechaCita;
    private com.toedter.calendar.JDateChooser JDateFechaCita2;
    private com.toedter.calendar.JDateChooser JDateFechaNacimiento;
    private javax.swing.JTabbedPane TabbetCitas;
    private javax.swing.JMenuItem agendar;
    private javax.swing.JButton btnActualizarCita;
    private javax.swing.JButton btnAgendarCita;
    private javax.swing.JButton btnBuscarCita;
    private javax.swing.JButton btnBuscarPaciente;
    private javax.swing.JButton btnConsultarCitaMedico;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnguardar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbEps;
    private javax.swing.JComboBox<String> cbNombreApellidoMedico;
    private javax.swing.JComboBox<String> cbSexo;
    private javax.swing.JComboBox<String> cbTipoDocumento;
    private javax.swing.JLabel cboEps2;
    private javax.swing.JComboBox<String> cboEspecialidadMedico;
    private javax.swing.JComboBox<String> cboEspecialidadMedico2;
    private javax.swing.JComboBox<String > cboEstadoCita;
    private javax.swing.JComboBox<String> cboEstadoCita2;
    private javax.swing.JComboBox<String> cboHoraCita;
    private javax.swing.JComboBox<String> cboHoraCita2;
    private javax.swing.JComboBox<String> cboMedico;
    private javax.swing.JComboBox<String> cboMotivoCita;
    private javax.swing.JComboBox<String> cboMotivoCita2;
    private javax.swing.JComboBox<String> cboSede;
    private javax.swing.JComboBox<String> cboSede2;
    private javax.swing.JComboBox<String> cboTipoCita;
    private javax.swing.JComboBox<String> cboTipoCita2;
    private javax.swing.JComboBox<String> cboTipoSangre;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateConsultarCitaMedico;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator33;
    private javax.swing.JSeparator jSeparator34;
    private javax.swing.JSeparator jSeparator35;
    private javax.swing.JSeparator jSeparator36;
    private javax.swing.JSeparator jSeparator37;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JLabel lblCitasCanceladas;
    private javax.swing.JLabel lblCitasCompletadas;
    private javax.swing.JLabel lblCitasProgramadas;
    private javax.swing.JLabel lblConsultorio;
    private javax.swing.JLabel lblConsultorio2;
    private javax.swing.JLabel lblNombreRecepcion1;
    private javax.swing.JPanel panelAgendar;
    private javax.swing.JPanel panelBtnAgendar1;
    private javax.swing.JPanel panelBtnInformes1;
    private javax.swing.JPanel panelBtnInicio1;
    private javax.swing.JPanel panelCitasMedico;
    private javax.swing.JPanel panelGuardarPaciente;
    private javax.swing.JPanel panelInicio;
    private javax.swing.JPanel panelModificarCita;
    private javax.swing.JButton refrecarTablaPaciente;
    private javax.swing.JTable tablaCitas;
    private javax.swing.JTable tablaPacientes;
    private javax.swing.JTable tableConsultarMedico;
    private javax.swing.JTable tablePaciente;
    private javax.swing.JTextField txtAltura;
    private javax.swing.JLabel txtApellido2;
    private javax.swing.JTextField txtBuscarIdCita;
    private javax.swing.JTextField txtCelularR;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtDocumentoPaciente;
    private javax.swing.JTextField txtDocumentoR;
    private javax.swing.JLabel txtEmail2;
    private javax.swing.JTextField txtEmailR;
    private javax.swing.JLabel txtIdCita;
    private javax.swing.JLabel txtIdCita2;
    private javax.swing.JTextField txtMedico;
    private javax.swing.JLabel txtNombre2;
    private javax.swing.JLabel txtNumeroDocumento2;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtPriApellidoR;
    private javax.swing.JTextField txtPriNombreR;
    private javax.swing.JLabel txtTelefono;
    // End of variables declaration//GEN-END:variables
}
