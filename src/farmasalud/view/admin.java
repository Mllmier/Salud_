/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package farmasalud.view;

import Controller.ControllerDoctor;
import Controller.ControllerEnfermedades;
import Controller.ControllerMedicamento;
import Controller.ControllerRecepcionista;
import Controller.ControllerSalas;
import Controller.ControllerSede;
import com.google.gson.JsonObject;
import dao.AdminDAO;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author usuario
 */
public class admin extends javax.swing.JFrame {

    private ControllerDoctor controllerDoctor = ControllerDoctor.getInstancia();
    private ControllerRecepcionista controllerRecepcionista = ControllerRecepcionista.getInstancia();
    private ControllerSalas controllerSalas = new ControllerSalas();
    private ControllerMedicamento controllerMedicamento = new ControllerMedicamento(this);
    private void limpiarDoctor() {controllerDoctor.limpiarFormulario();}
    private ControllerSede controllerSede = new ControllerSede();
    private ControllerEnfermedades controllerEnfermedades = new ControllerEnfermedades();
    private JPasswordField pwdContraseña = new JPasswordField();

    

    /**
     * Creates new form admin
     */
    public admin() {
        initComponents();
        
        configurarControllerDoctor();
        configurarControllerRecepcionista();
        configurarControllerSalas();
        configurarControllerMedicamento();
        configurarControllerSede();
        configurarControllerEnfermedades();
        
        
        configurarListeners();
        
        
        
        
        //doctores
    }
    private void configurarControllerDoctor() {
    controllerDoctor.setTablaDoctores(TablaDoctores);
    controllerDoctor.setTxtNombre(txtNombre);
    controllerDoctor.setTxtApellidos(txtApellidos);
    controllerDoctor.setTxtCedula(txtCedula);
    controllerDoctor.setTxtCorreo(txtCorreo);
    controllerDoctor.setTxtTelefono(txtTelefono);
    controllerDoctor.setCbSexo(cbSexo2);
    controllerDoctor.setCbHorario(Jcombo_horario);
    controllerDoctor.setCbEspecialidad(cbEspecialidad);
    pwdContraseña.setEchoChar('•');
    controllerDoctor.setDateChooserNacimiento(Fecha_nacimiento);
    controllerDoctor.setDateChooserContratacion(Fecha_contratacion_doctor);
    
    controllerDoctor.initTableDoctor();
    controllerDoctor.cargarDatosEnTablaDoctor();
}
    
    private void configurarControllerRecepcionista() {
        controllerRecepcionista.setTablaRecepcionistas(TabladeRecepcionistas);
        controllerRecepcionista.setTxtNombre(Jtexfieldnombre_recep);
        controllerRecepcionista.setTxtApellido(jtextfieldApellido_recep);
        controllerRecepcionista.setTxtDocumento(jtextfielID_recep);
        controllerRecepcionista.setTxtEmail(Jtextfield_correo_recep);
        controllerRecepcionista.setDateChooserNacimiento(Fecha_Nacimiento_Recep);
        controllerRecepcionista.setTxtCelular(jtextfieldTelefono_recep);
        controllerRecepcionista.setTxtCodigoEmpleado(JtexfieldCodigo_recep);
        controllerRecepcionista.setDateChooserContratacion(Fecha_Contratacion_Recepcionista);
        controllerRecepcionista.setCbSexo(JcomboSexo);
        controllerRecepcionista.setCbEps(JcomboSexo1);
        controllerRecepcionista.setCbHorario(JComboTurno);
        
        controllerRecepcionista.initTableRecepcionista();
        controllerRecepcionista.cargarDatosEnTablaRecepcionista();
    }
    
    private void configurarControllerSalas() {
        controllerSalas.setTablaSalas(TablaDeSalas);
        controllerSalas.setTxtNombreSala(txtNombreSala);
        controllerSalas.setTxtCodigoSala(txtCodigoSala);
        controllerSalas.setCbTipoSala(Combo_TipoSala);
        controllerSalas.setSpCapacidad(Jspinner_CapacidadSala);
        
        controllerSalas.initTableSalas();
        controllerSalas.cargarDatosEnTablaSalas();
    }
    
    
    private void configurarControllerMedicamento() {
    // Configurar tabla
    controllerMedicamento.setTabladeMedicamentos(jTable_Medicamentos);
    
    // Configurar campos de texto
    controllerMedicamento.setTxtCodMedicamento(Jtexfieldnombre_IDmEDICAMENTO);
    controllerMedicamento.setTxtMedicamento(jtextfieldNombre_Medicamento);
    controllerMedicamento.setTxtDescripcion(Jtextfield_Descripcion_Medicamento);
    controllerMedicamento.setTxtLaboratorio(jtextfiel_Laboratorio_Medicamento);
    controllerMedicamento.setTxtCantidad(jtextfield_cantidad_Medicamento);
    controllerMedicamento.setTxtLote(JtexfieldLote_Medicamento);
    controllerMedicamento.setTxtFechaVencimiento(Jtexfieldfechavencimiento_Medicamento);
    controllerMedicamento.setCbDisponible(jComboBox_Disponibilidad_Medicamento);
    controllerMedicamento.setTxtPrecio(JtexfieldPrecio_Medicamento);
    
    // Inicializar tabla y cargar datos
    controllerMedicamento.setupTableModelMedicamentos();
    try {
        controllerMedicamento.cargarDatosMedicamentos();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar medicamentos: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void configurarControllerSede() {
    // Configurar los componentes de la interfaz con el controlador
    controllerSede.setTxtCodigoSede(jTextField_ID_Sede); // ID_SEDE
    controllerSede.setTxtNombreSede(jTextField_Nombre_sede); // NOMBRE SEDE
    controllerSede.setTxtDireccion(Jtextfield_Direccion_sede); // DIRECCION DE LA SEDE
    controllerSede.setTxtHorarioAtencion(jTextField_Horario_atencion); // HORARIO DE ATENCION
    
    // Configurar tabla
    controllerSede.setTablaSedes(jTable_Sedes); // Tabla de sedes
    
    // Inicializar tabla
    controllerSede.initTableSedes();
    
        try {
            controllerSede.cargarDatosEnTablaSedes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar sedes: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        }
    
}
    
    private void configurarControllerEnfermedades() {
    controllerEnfermedades.setTablaEnfermedades(jTable3); // Asigna la tabla de enfermedades
    controllerEnfermedades.setTxtIdEnfermedad(jTextField7); // ID Enfermedad
    controllerEnfermedades.setTxtNombre(jTextField6); // Nombre
    controllerEnfermedades.setTxtTipo(jTextField9); // Tipo
    controllerEnfermedades.setTxtSintomas(jTextArea1); // Síntomas (necesitarías cambiar a JTextArea en el controller)
    controllerEnfermedades.setTxtCausas(jTextArea2); // Causas (necesitarías cambiar a JTextArea en el controller)
    
    controllerEnfermedades.initTableEnfermedades();
    controllerEnfermedades.cargarDatosEnTabla();
}
    
   

    
    private void configurarListeners() {
        // Listener para tabla de doctores
        TablaDoctores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    controllerDoctor.cargarDatosDoctorEnFormulario();
                }
            }
        });
        
        // Listener para tabla de recepcionistas
        TabladeRecepcionistas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    controllerRecepcionista.cargarDatosRecepcionistaEnFormulario();
                }
            }
        });
       
        // Listener para tabla de salas
        TablaDeSalas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    controllerSalas.cargarDatosSalaEnFormulario();
                }
            }
        });
       
        //Listener para tabla de medicamentos
        jTable_Medicamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                controllerMedicamento.cargarDatosEnTablaMedicamentos();
            }
        }
    });
        
        //Listener para Tabla de sedes
        jTable_Sedes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                controllerSede.cargarDatosSedeEnFormulario();
            }
        }
    });
        //Listener para tabla de medicamentos
        jTable3.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                controllerEnfermedades.cargarDatosEnFormulario();
            }
        }
    });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Panel_inicio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Panel_ENFERMEDADES = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Panel_recepcionistas = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        Panel_medicamento = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Panel_Salas = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        Panel_MISDATOS = new javax.swing.JPanel();
        jLabel65 = new javax.swing.JLabel();
        Panel_farmaceutica1 = new javax.swing.JPanel();
        jLabel90 = new javax.swing.JLabel();
        Panel_sedes = new javax.swing.JPanel();
        jLabel95 = new javax.swing.JLabel();
        Panel_farmaceutica7 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        Btn_salir = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        Panel_doctor1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        Paneles_jtablepane = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cbSexo2 = new javax.swing.JComboBox<>();
        cbEspecialidad = new javax.swing.JComboBox<>();
        Fecha_nacimiento = new com.toedter.calendar.JDateChooser();
        Fecha_contratacion_doctor = new com.toedter.calendar.JDateChooser();
        Jcombo_horario = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDoctores = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        Jtexfieldnombre_recep = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jtextfieldApellido_recep = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        Jtextfield_correo_recep = new javax.swing.JTextField();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jtextfielID_recep = new javax.swing.JTextField();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jtextfieldTelefono_recep = new javax.swing.JTextField();
        jSeparator11 = new javax.swing.JSeparator();
        jPanel14 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        JtexfieldCodigo_recep = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        JcomboSexo = new javax.swing.JComboBox<>();
        JcomboSexo1 = new javax.swing.JComboBox<>();
        JComboTurno = new javax.swing.JComboBox<>();
        Fecha_Contratacion_Recepcionista = new com.toedter.calendar.JDateChooser();
        Fecha_Nacimiento_Recep = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabladeRecepcionistas = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        txtNombreSala = new javax.swing.JTextField();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel33 = new javax.swing.JLabel();
        txtCodigoSala = new javax.swing.JTextField();
        jSeparator13 = new javax.swing.JSeparator();
        Combo_TipoSala = new javax.swing.JComboBox<>();
        Jspinner_CapacidadSala = new javax.swing.JSpinner();
        jLabel34 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaDeSalas = new javax.swing.JTable();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_Medicamentos = new javax.swing.JTable();
        jButton_BuscarMedicamento = new javax.swing.JButton();
        Jtextfield_BuscadorMedicamento = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jLabel67 = new javax.swing.JLabel();
        Jtexfieldnombre_IDmEDICAMENTO = new javax.swing.JTextField();
        jSeparator25 = new javax.swing.JSeparator();
        jLabel68 = new javax.swing.JLabel();
        jtextfieldNombre_Medicamento = new javax.swing.JTextField();
        jSeparator26 = new javax.swing.JSeparator();
        jLabel69 = new javax.swing.JLabel();
        Jtextfield_Descripcion_Medicamento = new javax.swing.JTextField();
        jSeparator27 = new javax.swing.JSeparator();
        jLabel70 = new javax.swing.JLabel();
        jtextfiel_Laboratorio_Medicamento = new javax.swing.JTextField();
        jSeparator28 = new javax.swing.JSeparator();
        jLabel71 = new javax.swing.JLabel();
        jtextfield_cantidad_Medicamento = new javax.swing.JTextField();
        jSeparator29 = new javax.swing.JSeparator();
        jPanelEliminarMedicamento = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel75 = new javax.swing.JLabel();
        jPanel_AgregarMedicamento = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jPanelModificarMedicamento = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        JtexfieldLote_Medicamento = new javax.swing.JTextField();
        jSeparator30 = new javax.swing.JSeparator();
        jLabel83 = new javax.swing.JLabel();
        Jtexfieldfechavencimiento_Medicamento = new javax.swing.JTextField();
        jSeparator31 = new javax.swing.JSeparator();
        jLabel85 = new javax.swing.JLabel();
        jComboBox_Disponibilidad_Medicamento = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        JtexfieldPrecio_Medicamento = new javax.swing.JTextField();
        jSeparator32 = new javax.swing.JSeparator();
        jButton_MostrarTodosMedicamentos = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jTextField_Horario_atencion = new javax.swing.JTextField();
        jTextField_ID_Sede = new javax.swing.JTextField();
        jTextField_Nombre_sede = new javax.swing.JTextField();
        Jtextfield_Direccion_sede = new javax.swing.JTextField();
        jPanel_guardar_sedes = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jPanel_Modificar_sedes = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jPanel_Eliminar_sedes = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_Sedes = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jTextField_buscar_sedes = new javax.swing.JTextField();
        jButton_buscar_sede = new javax.swing.JButton();
        panel_enfermedades_1 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel63 = new javax.swing.JLabel();
        jPanel56 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        Jbuton_buscador = new javax.swing.JButton();
        Buscador_enfermedades_txt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(10, 92, 184));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel_inicio.setBackground(new java.awt.Color(10, 92, 184));
        Panel_inicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_inicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_inicioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_inicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_inicioMouseExited(evt);
            }
        });
        Panel_inicio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("INICIO");
        Panel_inicio.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 60, 41));

        jPanel2.add(Panel_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 320, 60));

        Panel_ENFERMEDADES.setBackground(new java.awt.Color(10, 92, 184));
        Panel_ENFERMEDADES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_ENFERMEDADES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_ENFERMEDADESMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_ENFERMEDADESMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_ENFERMEDADESMouseExited(evt);
            }
        });
        Panel_ENFERMEDADES.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("GESTION ENFERMEDADES");
        Panel_ENFERMEDADES.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 230, 50));

        jPanel2.add(Panel_ENFERMEDADES, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 320, -1));

        Panel_recepcionistas.setBackground(new java.awt.Color(10, 92, 184));
        Panel_recepcionistas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_recepcionistas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_recepcionistasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_recepcionistasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_recepcionistasMouseExited(evt);
            }
        });
        Panel_recepcionistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("GESTION RECEPCIONISTAS");
        Panel_recepcionistas.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 240, 41));

        jPanel2.add(Panel_recepcionistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 320, 60));

        Panel_medicamento.setBackground(new java.awt.Color(10, 92, 184));
        Panel_medicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_medicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_medicamentoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_medicamentoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_medicamentoMouseExited(evt);
            }
        });
        Panel_medicamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTION MEDICAMENTOS");
        Panel_medicamento.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 41));

        jPanel2.add(Panel_medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 320, 60));

        Panel_Salas.setBackground(new java.awt.Color(10, 92, 184));
        Panel_Salas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_Salas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_SalasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_SalasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_SalasMouseExited(evt);
            }
        });
        Panel_Salas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("GESTION SALAS");
        Panel_Salas.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 156, 41));

        jPanel2.add(Panel_Salas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 320, 60));

        Panel_MISDATOS.setBackground(new java.awt.Color(10, 92, 184));
        Panel_MISDATOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_MISDATOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_MISDATOSMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_MISDATOSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_MISDATOSMouseExited(evt);
            }
        });
        Panel_MISDATOS.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("MIS DATOS");
        Panel_MISDATOS.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 41));

        Panel_farmaceutica1.setBackground(new java.awt.Color(10, 92, 184));
        Panel_farmaceutica1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_farmaceutica1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_farmaceutica1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_farmaceutica1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_farmaceutica1MouseExited(evt);
            }
        });
        Panel_farmaceutica1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel90.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("GESTION FARMACEUTICA");
        Panel_farmaceutica1.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 41));

        Panel_MISDATOS.add(Panel_farmaceutica1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 320, 60));

        jPanel2.add(Panel_MISDATOS, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 320, 60));

        Panel_sedes.setBackground(new java.awt.Color(10, 92, 184));
        Panel_sedes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_sedes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_sedesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_sedesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_sedesMouseExited(evt);
            }
        });
        Panel_sedes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel95.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("GESTION SEDES");
        jLabel95.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel95MouseClicked(evt);
            }
        });
        Panel_sedes.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 41));

        Panel_farmaceutica7.setBackground(new java.awt.Color(10, 92, 184));
        Panel_farmaceutica7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_farmaceutica7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_farmaceutica7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_farmaceutica7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_farmaceutica7MouseExited(evt);
            }
        });
        Panel_farmaceutica7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("GESTION FARMACEUTICA");
        Panel_farmaceutica7.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 41));

        Panel_sedes.add(Panel_farmaceutica7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 320, 60));

        jPanel2.add(Panel_sedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 320, 60));

        Btn_salir.setBackground(new java.awt.Color(10, 92, 184));
        Btn_salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_salirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_salirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_salirMouseExited(evt);
            }
        });
        Btn_salir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SALIR");
        Btn_salir.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 70, 41));

        jPanel2.add(Btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 700, 320, 70));

        Panel_doctor1.setBackground(new java.awt.Color(10, 92, 184));
        Panel_doctor1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Panel_doctor1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_doctor1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel_doctor1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Panel_doctor1MouseExited(evt);
            }
        });
        Panel_doctor1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("GESTION DOCTORES");
        Panel_doctor1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 187, 50));

        jPanel2.add(Panel_doctor1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 320, -1));

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Logo Medicina Salud Minimalista Corporativo Azul  (3).jpg"))); // NOI18N
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 240, 160));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 770));

        jPanel3.setBackground(new java.awt.Color(10, 92, 184));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setText("mis credenciales");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, -1, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 1020, 80));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Paneles_jtablepane.addTab("INICIO", jPanel8);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "DATOS DOCTOR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("NOMBRE:");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 60, 30));

        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(null);
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jPanel4.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 250, 20));

        jLabel7.setText("APELLIDOS:");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 90, 30));

        txtApellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApellidos.setBorder(null);
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });
        jPanel4.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 250, 20));

        jLabel8.setText("CORREO:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 90, 30));

        txtCorreo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCorreo.setBorder(null);
        txtCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCorreoFocusLost(evt);
            }
        });
        jPanel4.add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 250, 20));

        jLabel9.setText("C.C :");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 30, 30));

        txtCedula.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCedula.setBorder(null);
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });
        jPanel4.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 250, 20));

        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setBorder(null);
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        jPanel4.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 250, 20));

        jLabel11.setText("TELEFONO :");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 70, 30));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 250, 10));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 250, 10));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 250, 10));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 250, 10));

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 250, 10));

        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel15.setText("AGREGAR DOCTOR");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 50));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add-button (1).png"))); // NOI18N
        jPanel5.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 70, 70));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 370, 70));

        jPanel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 350, 60));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel16.setText("MODIFICAR DOCTOR");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, 50));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/pen.png"))); // NOI18N
        jPanel6.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 80, 70));

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 370, 70));

        jPanel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel9MouseClicked(evt);
            }
        });
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel13.setText("ELIMINAR DOCTOR");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 50));

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/delete_1.png"))); // NOI18N
        jPanel9.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 80, 70));

        jPanel4.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 370, 70));

        jButton1.setText("ACTUALIZAR TABLA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 590, -1, -1));

        cbSexo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        cbSexo2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "GENERO:"));
        jPanel4.add(cbSexo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, -1, -1));

        cbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione  la especialidad del doctor>", "Medico general", "Cardilogo", "terapeusta", " ", " " }));
        cbEspecialidad.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "ESPECIALIDAD:"));
        jPanel4.add(cbEspecialidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 260, 40));

        Fecha_nacimiento.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Fecha de Nacimiento"));
        jPanel4.add(Fecha_nacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 160, -1));

        Fecha_contratacion_doctor.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Fecha de Contratacion"));
        jPanel4.add(Fecha_contratacion_doctor, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 170, -1));

        Jcombo_horario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<SELECCIONE EL HORARIO>", "Lunes-miercoles-viernes", "Martes-jueves-sabado", "Sabado-domingo" }));
        Jcombo_horario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Horario:"));
        jPanel4.add(Jcombo_horario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 340, 40));

        jPanel10.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 393, 630));

        TablaDoctores.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        TablaDoctores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "NOMBRE", "APELLIDO", "CORREO", "CEDULA", "TELEFONO", "ESPECIALIDAD", "FECHA NACIMIENTO", "SEXO", "EPS"
            }
        ));
        jScrollPane1.setViewportView(TablaDoctores);

        jPanel10.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 620, 630));

        jTable2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "DOCTORES DISPONIBLES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        jPanel10.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 570, 590));

        jLabel10.setText("C.C :");
        jPanel10.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 30, 30));

        jTextField5.setText("jTextField1");
        jPanel10.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 250, -1));

        Paneles_jtablepane.addTab("Gestion_doc", jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "DATOS RECEPCIONISTAS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setText("NOMBRE:");
        jPanel13.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 60, 30));

        Jtexfieldnombre_recep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Jtexfieldnombre_recep.setBorder(null);
        Jtexfieldnombre_recep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Jtexfieldnombre_recepKeyTyped(evt);
            }
        });
        jPanel13.add(Jtexfieldnombre_recep, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 250, 20));

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 250, 10));

        jLabel18.setText("APELLIDOS:");
        jPanel13.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 90, 30));

        jtextfieldApellido_recep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtextfieldApellido_recep.setBorder(null);
        jtextfieldApellido_recep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtextfieldApellido_recepKeyTyped(evt);
            }
        });
        jPanel13.add(jtextfieldApellido_recep, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 250, 20));

        jSeparator8.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 250, 10));

        jLabel19.setText("CORREO:");
        jPanel13.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 90, 30));

        Jtextfield_correo_recep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Jtextfield_correo_recep.setBorder(null);
        Jtextfield_correo_recep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                Jtextfield_correo_recepFocusLost(evt);
            }
        });
        jPanel13.add(Jtextfield_correo_recep, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 250, 20));

        jSeparator9.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 250, 10));

        jLabel20.setText("C.C :");
        jPanel13.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 30, 30));

        jtextfielID_recep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtextfielID_recep.setBorder(null);
        jtextfielID_recep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtextfielID_recepKeyTyped(evt);
            }
        });
        jPanel13.add(jtextfielID_recep, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 250, 20));

        jSeparator10.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 250, 10));

        jLabel21.setText("TELEFONO:");
        jPanel13.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 90, 30));

        jtextfieldTelefono_recep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtextfieldTelefono_recep.setBorder(null);
        jtextfieldTelefono_recep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtextfieldTelefono_recepKeyTyped(evt);
            }
        });
        jPanel13.add(jtextfieldTelefono_recep, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 250, 20));

        jSeparator11.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 250, 10));

        jPanel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setText("ELIMINAR RECEPCIONISTA");
        jPanel14.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel23.setText("AGREGAR DOCTOR");
        jPanel15.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel26.setText("AGREGAR DOCTOR");
        jPanel18.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel27.setText("AGREGAR DOCTOR");
        jPanel19.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel18.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel14.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 350, 60));

        jPanel13.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 550, 370, 60));

        jPanel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setText("AGREGAR RECEPCIONISTA");
        jPanel16.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 240, 40));

        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel25.setText("AGREGAR DOCTOR");
        jPanel17.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel13.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 370, 60));

        jPanel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setText("MODIFICAR RECEPCIONISTA");
        jPanel20.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 250, 40));

        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel29.setText("AGREGAR DOCTOR");
        jPanel21.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel20.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel30.setText("AGREGAR DOCTOR");
        jPanel22.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel31.setText("AGREGAR DOCTOR");
        jPanel23.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel22.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel20.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 350, 60));

        jPanel13.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 370, 60));

        jLabel43.setText("CODIGO:");
        jPanel13.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 60, 30));

        JtexfieldCodigo_recep.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JtexfieldCodigo_recep.setBorder(null);
        JtexfieldCodigo_recep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JtexfieldCodigo_recepKeyTyped(evt);
            }
        });
        jPanel13.add(JtexfieldCodigo_recep, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 250, 20));

        jSeparator14.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator14.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 250, 10));

        JcomboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< SELECCIONE SU SEXO >", "MASCULINO", "FEMENINO", "GENERO NO BINARIO", " " }));
        JcomboSexo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "SEXO:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        JcomboSexo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(JcomboSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 140, 40));

        JcomboSexo1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<TU EPS>", "EPS SANITAS", "EPS SURA", "FAMISANAR", "SALUD TOTAL", "COOMEVA EPS" }));
        JcomboSexo1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "EPS:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        JcomboSexo1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(JcomboSexo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 120, 40));

        JComboTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< SELECCIONE EL TURNO >", "MAÑANA", "TARDE", "NOCHE", " " }));
        JComboTurno.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "TURNO:", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        JComboTurno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(JComboTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, 110, 40));

        Fecha_Contratacion_Recepcionista.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Fecha de Contratacion:"));
        jPanel13.add(Fecha_Contratacion_Recepcionista, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 340, 50));

        Fecha_Nacimiento_Recep.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Fecha de Nacimiento"));
        jPanel13.add(Fecha_Nacimiento_Recep, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 340, 50));

        jPanel11.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 390, 680));

        TabladeRecepcionistas.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        TabladeRecepcionistas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CEDULA", "NOMBRE", "APELLIDO", "FECHA NAC", "SEXO", "EPS", "CORREO", "TELEFONO", "COD. EMPLEADO", "F.CONTRATO", "TURNO"
            }
        ));
        TabladeRecepcionistas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabladeRecepcionistasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TabladeRecepcionistas);

        jPanel11.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 630, 680));

        Paneles_jtablepane.addTab("Gestion_Recep", jPanel11);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "DATOS DE SALAS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("NOMBRE DE LA SALA:");
        jPanel24.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, 30));

        txtNombreSala.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombreSala.setBorder(null);
        jPanel24.add(txtNombreSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 250, 20));

        jSeparator12.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator12.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 250, 10));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setText("CODIGO DE LA SALA:");
        jPanel24.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 160, 30));

        txtCodigoSala.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoSala.setBorder(null);
        jPanel24.add(txtCodigoSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 60, 250, 20));

        jSeparator13.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator13.setForeground(new java.awt.Color(0, 0, 0));
        jPanel24.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 250, 10));

        Combo_TipoSala.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Combo_TipoSala.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "< SELECCIONE UN TIPO DE SALA >", "Sala de Medicina General", "Consultorios Externos", "Sala de Urgencias", "Sala de Pediatría", "Sala de Rayos X", "Sala de Fisioterapia" }));
        Combo_TipoSala.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Combo_TipoSala.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel24.add(Combo_TipoSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 470, -1));
        jPanel24.add(Jspinner_CapacidadSala, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 260, 30));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("CAPACIDAD DE CAMAS:");
        jPanel24.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 200, 30));

        jPanel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel25MouseClicked(evt);
            }
        });
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel36.setText("AGREGAR");
        jPanel25.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 120, 40));

        jPanel24.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 270, 60));

        jPanel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel26MouseClicked(evt);
            }
        });
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel38.setText("MODIFICAR");
        jPanel26.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 120, 40));

        jPanel24.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 280, 60));

        jPanel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel27MouseClicked(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel40.setText("ELIMINAR");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel24.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, 280, 60));

        jPanel12.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1000, 220));

        TablaDeSalas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        TablaDeSalas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NOMBRE", "CODIGO", "TIPO", "CAPACIDAD"
            }
        ));
        jScrollPane4.setViewportView(TablaDeSalas);

        jPanel12.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 1010, 380));

        Paneles_jtablepane.addTab("Gestion_salas", jPanel12);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_Medicamentos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, java.awt.Color.darkGray, null), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_BOTTOM, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(51, 0, 255))); // NOI18N
        jTable_Medicamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable_Medicamentos);

        jPanel31.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 67, 610, 540));

        jButton_BuscarMedicamento.setText("Buscar");
        jButton_BuscarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BuscarMedicamentoActionPerformed(evt);
            }
        });
        jButton_BuscarMedicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton_BuscarMedicamentoKeyPressed(evt);
            }
        });
        jPanel31.add(jButton_BuscarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, 130, 30));

        Jtextfield_BuscadorMedicamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        Jtextfield_BuscadorMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jtextfield_BuscadorMedicamentoActionPerformed(evt);
            }
        });
        Jtextfield_BuscadorMedicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jtextfield_BuscadorMedicamentoKeyPressed(evt);
            }
        });
        jPanel31.add(Jtextfield_BuscadorMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 430, 30));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "DATOS DEL MEDICAMENTO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(0, 51, 255))); // NOI18N
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel67.setText("ID Medicamento:");
        jPanel35.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, 30));

        Jtexfieldnombre_IDmEDICAMENTO.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Jtexfieldnombre_IDmEDICAMENTO.setBorder(null);
        Jtexfieldnombre_IDmEDICAMENTO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Jtexfieldnombre_IDmEDICAMENTOKeyTyped(evt);
            }
        });
        jPanel35.add(Jtexfieldnombre_IDmEDICAMENTO, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 250, 20));

        jSeparator25.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator25.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator25, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 250, 10));

        jLabel68.setText("Nombre:");
        jPanel35.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 90, 30));

        jtextfieldNombre_Medicamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtextfieldNombre_Medicamento.setBorder(null);
        jtextfieldNombre_Medicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtextfieldNombre_MedicamentoKeyTyped(evt);
            }
        });
        jPanel35.add(jtextfieldNombre_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 250, 20));

        jSeparator26.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator26.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator26, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 250, 10));

        jLabel69.setText("Descripcion:");
        jPanel35.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 90, 30));

        Jtextfield_Descripcion_Medicamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Jtextfield_Descripcion_Medicamento.setBorder(null);
        Jtextfield_Descripcion_Medicamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                Jtextfield_Descripcion_MedicamentoFocusLost(evt);
            }
        });
        jPanel35.add(Jtextfield_Descripcion_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 250, 20));

        jSeparator27.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator27.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator27, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 250, 10));

        jLabel70.setText("Laboratorio:");
        jPanel35.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 70, 30));

        jtextfiel_Laboratorio_Medicamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtextfiel_Laboratorio_Medicamento.setBorder(null);
        jtextfiel_Laboratorio_Medicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtextfiel_Laboratorio_MedicamentoKeyTyped(evt);
            }
        });
        jPanel35.add(jtextfiel_Laboratorio_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 250, 20));

        jSeparator28.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator28.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator28, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 250, 10));

        jLabel71.setText("Cantidad:");
        jPanel35.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 90, 30));

        jtextfield_cantidad_Medicamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtextfield_cantidad_Medicamento.setBorder(null);
        jtextfield_cantidad_Medicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtextfield_cantidad_MedicamentoKeyTyped(evt);
            }
        });
        jPanel35.add(jtextfield_cantidad_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 250, 20));

        jSeparator29.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator29.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator29, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 250, 10));

        jPanelEliminarMedicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelEliminarMedicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelEliminarMedicamentoMouseClicked(evt);
            }
        });
        jPanelEliminarMedicamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel72.setText("ELIMINAR MEDICAMENTO");
        jPanelEliminarMedicamento.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, 40));

        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel73.setText("AGREGAR DOCTOR");
        jPanel41.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanelEliminarMedicamento.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel74.setText("AGREGAR DOCTOR");
        jPanel42.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel75.setText("AGREGAR DOCTOR");
        jPanel43.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel42.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanelEliminarMedicamento.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 350, 60));

        jPanel35.add(jPanelEliminarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 370, 60));

        jPanel_AgregarMedicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel_AgregarMedicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_AgregarMedicamentoMouseClicked(evt);
            }
        });
        jPanel_AgregarMedicamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setText("AGREGAR MEDICAMENTO");
        jPanel_AgregarMedicamento.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 240, 40));

        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel77.setText("AGREGAR DOCTOR");
        jPanel45.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel_AgregarMedicamento.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel35.add(jPanel_AgregarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 370, 60));

        jPanelModificarMedicamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanelModificarMedicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelModificarMedicamentoMouseClicked(evt);
            }
        });
        jPanelModificarMedicamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel78.setText("MODIFICAR MEDICAMENTO");
        jPanelModificarMedicamento.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 250, 40));

        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel79.setText("AGREGAR DOCTOR");
        jPanel47.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanelModificarMedicamento.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanel48.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel80.setText("AGREGAR DOCTOR");
        jPanel48.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel81.setText("AGREGAR DOCTOR");
        jPanel49.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 190, 40));

        jPanel48.add(jPanel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 350, 60));

        jPanelModificarMedicamento.add(jPanel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 350, 60));

        jPanel35.add(jPanelModificarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 370, 60));

        jLabel82.setText("Lote:");
        jPanel35.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 60, 30));

        JtexfieldLote_Medicamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JtexfieldLote_Medicamento.setBorder(null);
        JtexfieldLote_Medicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JtexfieldLote_MedicamentoKeyTyped(evt);
            }
        });
        jPanel35.add(JtexfieldLote_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 250, 20));

        jSeparator30.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator30.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator30, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 250, 10));

        jLabel83.setText("Fecha Vencimiento:");
        jPanel35.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 140, 30));

        Jtexfieldfechavencimiento_Medicamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Jtexfieldfechavencimiento_Medicamento.setBorder(null);
        Jtexfieldfechavencimiento_Medicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Jtexfieldfechavencimiento_MedicamentoKeyTyped(evt);
            }
        });
        jPanel35.add(Jtexfieldfechavencimiento_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 230, 20));

        jSeparator31.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator31.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator31, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 230, 10));

        jLabel85.setText("Disponible:");
        jPanel35.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 120, 30));

        jComboBox_Disponibilidad_Medicamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<DISPONIBILIDAD>", "DISPONIBLE", "NO DISPONIBLE" }));
        jComboBox_Disponibilidad_Medicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_Disponibilidad_MedicamentoActionPerformed(evt);
            }
        });
        jPanel35.add(jComboBox_Disponibilidad_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 230, -1));

        jLabel84.setText("Precio:");
        jPanel35.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 50, 30));

        JtexfieldPrecio_Medicamento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        JtexfieldPrecio_Medicamento.setBorder(null);
        JtexfieldPrecio_Medicamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                JtexfieldPrecio_MedicamentoKeyTyped(evt);
            }
        });
        jPanel35.add(JtexfieldPrecio_Medicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, 230, 20));

        jSeparator32.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator32.setForeground(new java.awt.Color(0, 0, 0));
        jPanel35.add(jSeparator32, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 230, 10));

        jPanel31.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 390, 640));

        jButton_MostrarTodosMedicamentos.setText("Mostrar todos");
        jButton_MostrarTodosMedicamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_MostrarTodosMedicamentosActionPerformed(evt);
            }
        });
        jButton_MostrarTodosMedicamentos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton_MostrarTodosMedicamentosKeyPressed(evt);
            }
        });
        jPanel31.add(jButton_MostrarTodosMedicamentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 620, 600, -1));

        Paneles_jtablepane.addTab("Gestion_medicamento", jPanel31);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "DATOS DE LA SEDE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField_Horario_atencion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "HORARIO DE ATENCION"));
        jPanel44.add(jTextField_Horario_atencion, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 270, -1));

        jTextField_ID_Sede.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "ID_SEDE"));
        jPanel44.add(jTextField_ID_Sede, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 270, -1));

        jTextField_Nombre_sede.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "NOMBRE SEDE"));
        jPanel44.add(jTextField_Nombre_sede, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 270, -1));

        Jtextfield_Direccion_sede.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "DIRECCION DE LA SEDE"));
        jPanel44.add(Jtextfield_Direccion_sede, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 270, -1));

        jPanel_guardar_sedes.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_guardar_sedes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_guardar_sedes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_guardar_sedesMouseClicked(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel45.setText("GUARDAR SEDE");

        javax.swing.GroupLayout jPanel_guardar_sedesLayout = new javax.swing.GroupLayout(jPanel_guardar_sedes);
        jPanel_guardar_sedes.setLayout(jPanel_guardar_sedesLayout);
        jPanel_guardar_sedesLayout.setHorizontalGroup(
            jPanel_guardar_sedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_guardar_sedesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addContainerGap())
        );
        jPanel_guardar_sedesLayout.setVerticalGroup(
            jPanel_guardar_sedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_guardar_sedesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));
        jPanel50.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        jPanel_Modificar_sedes.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Modificar_sedes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_Modificar_sedes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_Modificar_sedesMouseClicked(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setText("MODIFICAR SEDE");

        javax.swing.GroupLayout jPanel_Modificar_sedesLayout = new javax.swing.GroupLayout(jPanel_Modificar_sedes);
        jPanel_Modificar_sedes.setLayout(jPanel_Modificar_sedesLayout);
        jPanel_Modificar_sedesLayout.setHorizontalGroup(
            jPanel_Modificar_sedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_Modificar_sedesLayout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(15, 15, 15))
        );
        jPanel_Modificar_sedesLayout.setVerticalGroup(
            jPanel_Modificar_sedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_Modificar_sedesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel_Eliminar_sedes.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Eliminar_sedes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel_Eliminar_sedes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_Eliminar_sedesMouseClicked(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel47.setText("ELIMINAR SEDE");

        javax.swing.GroupLayout jPanel_Eliminar_sedesLayout = new javax.swing.GroupLayout(jPanel_Eliminar_sedes);
        jPanel_Eliminar_sedes.setLayout(jPanel_Eliminar_sedesLayout);
        jPanel_Eliminar_sedesLayout.setHorizontalGroup(
            jPanel_Eliminar_sedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_Eliminar_sedesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel47)
                .addContainerGap())
        );
        jPanel_Eliminar_sedesLayout.setVerticalGroup(
            jPanel_Eliminar_sedesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_Eliminar_sedesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTable_Sedes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable_Sedes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable_Sedes);

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setText("SEDES DISPONIBLES");

        jTextField_buscar_sedes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_buscar_sedesActionPerformed(evt);
            }
        });
        jTextField_buscar_sedes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_buscar_sedesKeyPressed(evt);
            }
        });

        jButton_buscar_sede.setText("BUSCAR");
        jButton_buscar_sede.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton_buscar_sede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_buscar_sedeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addComponent(jScrollPane7)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel_guardar_sedes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_Modificar_sedes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_Eliminar_sedes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40))))
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 368, Short.MAX_VALUE))
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_buscar_sedes)
                .addGap(18, 18, 18)
                .addComponent(jButton_buscar_sede, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel37Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel37Layout.createSequentialGroup()
                                .addComponent(jPanel_guardar_sedes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel_Modificar_sedes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel_Eliminar_sedes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jTextField_buscar_sedes))
                    .addComponent(jButton_buscar_sede, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addContainerGap())
        );

        Paneles_jtablepane.addTab("Gestion_Sedes", jPanel37);

        panel_enfermedades_1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel54.setBackground(new java.awt.Color(255, 255, 255));
        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "DATOS ENFERMEDAD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jPanel54.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "NOMBRE DE LA ENFERMEDAD"));
        jPanel54.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 256, -1));

        jTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "ID DE ENFERMEDAD"));
        jPanel54.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 256, -1));

        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "TIPO DE ENFERMEDAD"));
        jPanel54.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 256, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "SINTOMAS"));
        jScrollPane8.setViewportView(jTextArea1);

        jPanel54.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 246, -1));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "CAUSAS"));
        jScrollPane9.setViewportView(jTextArea2);

        jPanel54.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, 246, 110));

        panel_enfermedades_1.add(jPanel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 6, 640, 240));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane10.setViewportView(jTable3);

        panel_enfermedades_1.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 990, 390));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setText("ENFERMEDADES");
        panel_enfermedades_1.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 120, 40));

        jPanel56.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        panel_enfermedades_1.add(jPanel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, -1, -1));

        jPanel57.setBackground(new java.awt.Color(255, 255, 255));
        jPanel57.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel57MouseClicked(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel89.setText("MODIFICAR ENFERMEDAD");

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel57Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_enfermedades_1.add(jPanel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 300, 60));

        jPanel58.setBackground(new java.awt.Color(255, 255, 255));
        jPanel58.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel88.setText("GUARDAR ENFERMEDAD");

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel58Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(jLabel88)
                .addGap(16, 16, 16))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        panel_enfermedades_1.add(jPanel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 300, 60));

        Jbuton_buscador.setText("BUSCAR");
        Jbuton_buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jbuton_buscadorActionPerformed(evt);
            }
        });
        panel_enfermedades_1.add(Jbuton_buscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 253, 160, 30));

        Buscador_enfermedades_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscador_enfermedades_txtActionPerformed(evt);
            }
        });
        Buscador_enfermedades_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Buscador_enfermedades_txtKeyPressed(evt);
            }
        });
        panel_enfermedades_1.add(Buscador_enfermedades_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 620, 30));

        Paneles_jtablepane.addTab("Gestion_enfermedades", panel_enfermedades_1);

        jPanel1.add(Paneles_jtablepane, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 1020, 730));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1343, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Panel_inicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_inicioMouseClicked
        Paneles_jtablepane.setSelectedIndex(0);
    }//GEN-LAST:event_Panel_inicioMouseClicked

    private void Panel_ENFERMEDADESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_ENFERMEDADESMouseClicked
        Paneles_jtablepane.setSelectedIndex(6);
    }//GEN-LAST:event_Panel_ENFERMEDADESMouseClicked

    private void Panel_recepcionistasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_recepcionistasMouseClicked
        Paneles_jtablepane.setSelectedIndex(2);
    }//GEN-LAST:event_Panel_recepcionistasMouseClicked

    private void Panel_medicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_medicamentoMouseClicked
        Paneles_jtablepane.setSelectedIndex(4);
    }//GEN-LAST:event_Panel_medicamentoMouseClicked

    private void Panel_inicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_inicioMouseEntered
        Panel_inicio.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_inicioMouseEntered

    private void Panel_ENFERMEDADESMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_ENFERMEDADESMouseEntered
        Panel_ENFERMEDADES.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_ENFERMEDADESMouseEntered

    private void Panel_recepcionistasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_recepcionistasMouseEntered
        Panel_recepcionistas.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_recepcionistasMouseEntered

    private void Panel_medicamentoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_medicamentoMouseEntered
        Panel_medicamento.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_medicamentoMouseEntered

    private void Btn_salirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_salirMouseEntered
        Btn_salir.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Btn_salirMouseEntered

    private void Panel_inicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_inicioMouseExited
        Panel_inicio.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_inicioMouseExited

    private void Panel_ENFERMEDADESMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_ENFERMEDADESMouseExited
        Panel_ENFERMEDADES.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_ENFERMEDADESMouseExited

    private void Panel_recepcionistasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_recepcionistasMouseExited
        Panel_recepcionistas.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_recepcionistasMouseExited

    private void Panel_medicamentoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_medicamentoMouseExited
        Panel_medicamento.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_medicamentoMouseExited

    private void Btn_salirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_salirMouseExited
        Btn_salir.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Btn_salirMouseExited

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped

        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten letras", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped

        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten letras", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE /*&& c != '.'*/) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten números", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE /*&& c != '.'*/) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten números", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCorreoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCorreoFocusLost
        String correo = txtCorreo.getText().trim();
        // Regex para validar formato básico de correo (ej: usuario@dominio.com)
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Correo inválido. Ejemplo válido: usuario@dominio.com", "Error", JOptionPane.ERROR_MESSAGE);
            txtCorreo.requestFocus(); // Regresa el foco al campo
        }
    }//GEN-LAST:event_txtCorreoFocusLost

    private void Jtexfieldnombre_recepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jtexfieldnombre_recepKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten letras", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_Jtexfieldnombre_recepKeyTyped

    private void jtextfieldApellido_recepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextfieldApellido_recepKeyTyped
        char c = evt.getKeyChar();

        if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten letras", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jtextfieldApellido_recepKeyTyped

    private void Jtextfield_correo_recepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Jtextfield_correo_recepFocusLost
        String correo = Jtextfield_correo_recep.getText().trim();
        // Regex para validar formato básico de correo (ej: usuario@dominio.com)
        if (!correo.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(null, "Correo inválido. Ejemplo válido: usuario@dominio.com", "Error", JOptionPane.ERROR_MESSAGE);
            Jtextfield_correo_recep.requestFocus(); // Regresa el foco al campo
        }
    }//GEN-LAST:event_Jtextfield_correo_recepFocusLost

    private void jtextfielID_recepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextfielID_recepKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE /*&& c != '.'*/) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten números", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jtextfielID_recepKeyTyped

    private void jtextfieldTelefono_recepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextfieldTelefono_recepKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE /*&& c != '.'*/) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Solo se permiten números", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jtextfieldTelefono_recepKeyTyped


    private void Btn_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_salirMouseClicked
        int respuesta = JOptionPane.showConfirmDialog(
        this, 
        "¿Está seguro que desea cerrar la aplicación?",
        "Confirmar cierre",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
    
    if (respuesta == JOptionPane.YES_OPTION) {
        System.exit(0);
        
    }
    }//GEN-LAST:event_Btn_salirMouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        controllerDoctor.guardarDoctorDesdeFormulario();
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controllerDoctor.cargarDatosEnTablaDoctor();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseClicked
         controllerDoctor.eliminarDoctorSeleccionado();
    }//GEN-LAST:event_jPanel9MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        if (TablaDoctores.getSelectedRow() == -1) {
            controllerDoctor.cargarDatosDoctorEnFormulario();
        } else {
            controllerDoctor.actualizarDoctor();
            limpiarDoctor();
        }
    
    }//GEN-LAST:event_jPanel6MouseClicked

    private void JtexfieldCodigo_recepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtexfieldCodigo_recepKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_JtexfieldCodigo_recepKeyTyped

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        controllerRecepcionista.guardarRecepcionistaDesdeFormulario();
    }//GEN-LAST:event_jPanel16MouseClicked

    private void TabladeRecepcionistasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabladeRecepcionistasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabladeRecepcionistasMouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        if (TabladeRecepcionistas.getSelectedRow() == -1) {
            controllerRecepcionista.cargarDatosRecepcionistaEnFormulario();
        } else {
            controllerRecepcionista.actualizarRecepcionista();
            controllerRecepcionista.limpiarFormulario();
        }
                                     
    
    }//GEN-LAST:event_jPanel20MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        controllerRecepcionista.eliminarRecepcionistaSeleccionado();
    }//GEN-LAST:event_jPanel14MouseClicked

    private void jPanel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel25MouseClicked
        controllerSalas.guardarSalaDesdeFormulario();
    }//GEN-LAST:event_jPanel25MouseClicked

    private void jPanel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel26MouseClicked

         if (TablaDeSalas.getSelectedRow() == -1) {
            controllerSalas.cargarDatosSalaEnFormulario();
        } else {
            controllerSalas.actualizarSala();
            controllerSalas.limpiarSala();
        }
    }//GEN-LAST:event_jPanel26MouseClicked

    private void jPanel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel27MouseClicked
        controllerSalas.eliminarSalaSeleccionada();
    }//GEN-LAST:event_jPanel27MouseClicked

    private void Panel_SalasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_SalasMouseClicked
        Paneles_jtablepane.setSelectedIndex(3);
    }//GEN-LAST:event_Panel_SalasMouseClicked

    private void Panel_SalasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_SalasMouseEntered
        Panel_Salas.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_SalasMouseEntered

    private void Panel_SalasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_SalasMouseExited
       Panel_Salas.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_SalasMouseExited

    private void Panel_MISDATOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_MISDATOSMouseClicked

        String emailAdminActual = "admin@clinica.com"; // Reemplaza con tu variable real
    
    // Mostrar el panel premiu
    new MisDatosPanel().mostrarPanel(this, emailAdminActual);
    }//GEN-LAST:event_Panel_MISDATOSMouseClicked

    private void Panel_MISDATOSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_MISDATOSMouseEntered
        Panel_MISDATOS.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_MISDATOSMouseEntered

    private void Panel_MISDATOSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_MISDATOSMouseExited
        Panel_MISDATOS.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_MISDATOSMouseExited

    private void Jtexfieldnombre_IDmEDICAMENTOKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jtexfieldnombre_IDmEDICAMENTOKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Jtexfieldnombre_IDmEDICAMENTOKeyTyped

    private void jtextfieldNombre_MedicamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextfieldNombre_MedicamentoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtextfieldNombre_MedicamentoKeyTyped

    private void Jtextfield_Descripcion_MedicamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Jtextfield_Descripcion_MedicamentoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_Jtextfield_Descripcion_MedicamentoFocusLost

    private void jtextfiel_Laboratorio_MedicamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextfiel_Laboratorio_MedicamentoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jtextfiel_Laboratorio_MedicamentoKeyTyped

    private void jtextfield_cantidad_MedicamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtextfield_cantidad_MedicamentoKeyTyped
        char c = evt.getKeyChar();
    if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
        evt.consume();
        JOptionPane.showMessageDialog(null, "Solo se permiten números", "Error", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_jtextfield_cantidad_MedicamentoKeyTyped

    private void jPanelEliminarMedicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelEliminarMedicamentoMouseClicked
        controllerMedicamento.eliminaraMedicamentoSeleccionado();
        try {
            controllerMedicamento.cargarDatosMedicamentos(); // Forzar recarga
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar la tabla: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jPanelEliminarMedicamentoMouseClicked

    private void jPanel_AgregarMedicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_AgregarMedicamentoMouseClicked
        controllerMedicamento.guardarMedicamentoDesdeFormulario();
        try {
            controllerMedicamento.cargarDatosMedicamentos(); // Forzar recarga
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar la tabla: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jPanel_AgregarMedicamentoMouseClicked

    private void jPanelModificarMedicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelModificarMedicamentoMouseClicked
        if (jTable_Medicamentos.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, 
            "Seleccione un medicamento de la tabla para modificar", 
            "Advertencia", 
            JOptionPane.WARNING_MESSAGE);
    } else {
        controllerMedicamento.actualizarMedicamento();
        try {
            controllerMedicamento.cargarDatosMedicamentos(); // Forzar recarga
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar la tabla: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_jPanelModificarMedicamentoMouseClicked

    private void JtexfieldLote_MedicamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtexfieldLote_MedicamentoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_JtexfieldLote_MedicamentoKeyTyped

    private void Jtexfieldfechavencimiento_MedicamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jtexfieldfechavencimiento_MedicamentoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_Jtexfieldfechavencimiento_MedicamentoKeyTyped

    private void jComboBox_Disponibilidad_MedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_Disponibilidad_MedicamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_Disponibilidad_MedicamentoActionPerformed

    private void JtexfieldPrecio_MedicamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JtexfieldPrecio_MedicamentoKeyTyped
        char c = evt.getKeyChar();
    if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE && c != '.') {
        evt.consume();
        JOptionPane.showMessageDialog(null, "Solo se permiten números y punto decimal", 
            "Error", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_JtexfieldPrecio_MedicamentoKeyTyped

    private void jButton_BuscarMedicamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_BuscarMedicamentoKeyPressed
         
    }//GEN-LAST:event_jButton_BuscarMedicamentoKeyPressed

    private void Jtextfield_BuscadorMedicamentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jtextfield_BuscadorMedicamentoKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String criterio = Jtextfield_BuscadorMedicamento.getText().trim();
        controllerMedicamento.buscarMedicamentos(criterio);
    }
    }//GEN-LAST:event_Jtextfield_BuscadorMedicamentoKeyPressed

    private void jButton_MostrarTodosMedicamentosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton_MostrarTodosMedicamentosKeyPressed
        
    }//GEN-LAST:event_jButton_MostrarTodosMedicamentosKeyPressed

    private void jButton_MostrarTodosMedicamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_MostrarTodosMedicamentosActionPerformed
        controllerMedicamento.mostrarTodosMedicamentos();
        Jtextfield_BuscadorMedicamento.setText("");
    }//GEN-LAST:event_jButton_MostrarTodosMedicamentosActionPerformed

    private void jButton_BuscarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BuscarMedicamentoActionPerformed
        String criterio = Jtextfield_BuscadorMedicamento.getText().trim();
        controllerMedicamento.buscarMedicamentos(criterio);
    }//GEN-LAST:event_jButton_BuscarMedicamentoActionPerformed

    private void jPanel_guardar_sedesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_guardar_sedesMouseClicked
        controllerSede.guardarSedeDesdeFormulario();
    }//GEN-LAST:event_jPanel_guardar_sedesMouseClicked

    private void jPanel_Modificar_sedesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_Modificar_sedesMouseClicked
        if (jTable_Sedes.getSelectedRow() == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una sede de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
    } else {
        controllerSede.actualizarSede();
    }
    }//GEN-LAST:event_jPanel_Modificar_sedesMouseClicked

    private void jPanel_Eliminar_sedesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_Eliminar_sedesMouseClicked
        controllerSede.eliminarSedeSeleccionada();
    }//GEN-LAST:event_jPanel_Eliminar_sedesMouseClicked

    private void jTextField_buscar_sedesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_buscar_sedesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_buscar_sedesActionPerformed

    private void Jtextfield_BuscadorMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jtextfield_BuscadorMedicamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jtextfield_BuscadorMedicamentoActionPerformed

    private void jTextField_buscar_sedesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_buscar_sedesKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String criterio = jTextField_buscar_sedes.getText().trim();
        controllerSede.buscarSedes(criterio);
    }
    }//GEN-LAST:event_jTextField_buscar_sedesKeyPressed

    private void jButton_buscar_sedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_buscar_sedeActionPerformed
        String criterio = jTextField_buscar_sedes.getText().trim();
        controllerSede.buscarSedes(criterio);
    }//GEN-LAST:event_jButton_buscar_sedeActionPerformed

    private void Panel_farmaceutica1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_farmaceutica1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel_farmaceutica1MouseClicked

    private void Panel_farmaceutica1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_farmaceutica1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel_farmaceutica1MouseEntered

    private void Panel_farmaceutica1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_farmaceutica1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel_farmaceutica1MouseExited

    private void Panel_farmaceutica7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_farmaceutica7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel_farmaceutica7MouseClicked

    private void Panel_farmaceutica7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_farmaceutica7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel_farmaceutica7MouseEntered

    private void Panel_farmaceutica7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_farmaceutica7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel_farmaceutica7MouseExited

    private void Panel_sedesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_sedesMouseClicked
        Paneles_jtablepane.setSelectedIndex(5);
    }//GEN-LAST:event_Panel_sedesMouseClicked

    private void Panel_sedesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_sedesMouseEntered
        Panel_sedes.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_sedesMouseEntered

    private void Panel_sedesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_sedesMouseExited
        Panel_sedes.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_sedesMouseExited

    private void Buscador_enfermedades_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscador_enfermedades_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Buscador_enfermedades_txtActionPerformed

    private void jPanel57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel57MouseClicked
        if (jTable3.getSelectedRow() == -1) {
            controllerEnfermedades.cargarDatosEnFormulario();
        } else {
            controllerEnfermedades.actualizarEnfermedad();
            controllerDoctor.limpiarFormulario();
        }
    }//GEN-LAST:event_jPanel57MouseClicked

    private void Jbuton_buscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jbuton_buscadorActionPerformed
        String criterio = Buscador_enfermedades_txt.getText().trim();
        controllerEnfermedades.buscarEnfermedades(criterio);
    }//GEN-LAST:event_Jbuton_buscadorActionPerformed

    private void Buscador_enfermedades_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Buscador_enfermedades_txtKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        String criterio = Buscador_enfermedades_txt.getText().trim();
        controllerEnfermedades.buscarEnfermedades(criterio);
    }
    }//GEN-LAST:event_Buscador_enfermedades_txtKeyPressed

    private void jLabel95MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel95MouseClicked
        Paneles_jtablepane.setSelectedIndex(5);
    }//GEN-LAST:event_jLabel95MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String emailAdminActual = "admin@clinica.com"; // Reemplaza con tu valor real
    new CredencialesPanel(this, emailAdminActual).mostrarPanel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void Panel_doctor1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_doctor1MouseClicked
       Paneles_jtablepane.setSelectedIndex(1);
    }//GEN-LAST:event_Panel_doctor1MouseClicked

    private void Panel_doctor1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_doctor1MouseEntered
         Panel_doctor1.setBackground(new Color(10, 132, 215));
    }//GEN-LAST:event_Panel_doctor1MouseEntered

    private void Panel_doctor1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_doctor1MouseExited
        Panel_doctor1.setBackground(new Color(10, 92, 184));
    }//GEN-LAST:event_Panel_doctor1MouseExited


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
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Btn_salir;
    private javax.swing.JTextField Buscador_enfermedades_txt;
    private javax.swing.JComboBox<String> Combo_TipoSala;
    private com.toedter.calendar.JDateChooser Fecha_Contratacion_Recepcionista;
    private com.toedter.calendar.JDateChooser Fecha_Nacimiento_Recep;
    private com.toedter.calendar.JDateChooser Fecha_contratacion_doctor;
    private com.toedter.calendar.JDateChooser Fecha_nacimiento;
    private javax.swing.JComboBox<String> JComboTurno;
    private javax.swing.JButton Jbuton_buscador;
    private javax.swing.JComboBox<String> JcomboSexo;
    private javax.swing.JComboBox<String> JcomboSexo1;
    private javax.swing.JComboBox<String> Jcombo_horario;
    private javax.swing.JSpinner Jspinner_CapacidadSala;
    private javax.swing.JTextField JtexfieldCodigo_recep;
    private javax.swing.JTextField JtexfieldLote_Medicamento;
    private javax.swing.JTextField JtexfieldPrecio_Medicamento;
    private javax.swing.JTextField Jtexfieldfechavencimiento_Medicamento;
    private javax.swing.JTextField Jtexfieldnombre_IDmEDICAMENTO;
    private javax.swing.JTextField Jtexfieldnombre_recep;
    private javax.swing.JTextField Jtextfield_BuscadorMedicamento;
    private javax.swing.JTextField Jtextfield_Descripcion_Medicamento;
    private javax.swing.JTextField Jtextfield_Direccion_sede;
    private javax.swing.JTextField Jtextfield_correo_recep;
    private javax.swing.JPanel Panel_ENFERMEDADES;
    private javax.swing.JPanel Panel_MISDATOS;
    private javax.swing.JPanel Panel_Salas;
    private javax.swing.JPanel Panel_doctor1;
    private javax.swing.JPanel Panel_farmaceutica1;
    private javax.swing.JPanel Panel_farmaceutica7;
    private javax.swing.JPanel Panel_inicio;
    private javax.swing.JPanel Panel_medicamento;
    private javax.swing.JPanel Panel_recepcionistas;
    private javax.swing.JPanel Panel_sedes;
    private javax.swing.JTabbedPane Paneles_jtablepane;
    private javax.swing.JTable TablaDeSalas;
    private javax.swing.JTable TablaDoctores;
    private javax.swing.JTable TabladeRecepcionistas;
    private javax.swing.JComboBox<String> cbEspecialidad;
    private javax.swing.JComboBox<String> cbSexo2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton_BuscarMedicamento;
    private javax.swing.JButton jButton_MostrarTodosMedicamentos;
    private javax.swing.JButton jButton_buscar_sede;
    private javax.swing.JComboBox<String> jComboBox_Disponibilidad_Medicamento;
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelEliminarMedicamento;
    private javax.swing.JPanel jPanelModificarMedicamento;
    private javax.swing.JPanel jPanel_AgregarMedicamento;
    private javax.swing.JPanel jPanel_Eliminar_sedes;
    private javax.swing.JPanel jPanel_Modificar_sedes;
    private javax.swing.JPanel jPanel_guardar_sedes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator26;
    private javax.swing.JSeparator jSeparator27;
    private javax.swing.JSeparator jSeparator28;
    private javax.swing.JSeparator jSeparator29;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator30;
    private javax.swing.JSeparator jSeparator31;
    private javax.swing.JSeparator jSeparator32;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable_Medicamentos;
    private javax.swing.JTable jTable_Sedes;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField jTextField_Horario_atencion;
    private javax.swing.JTextField jTextField_ID_Sede;
    private javax.swing.JTextField jTextField_Nombre_sede;
    private javax.swing.JTextField jTextField_buscar_sedes;
    private javax.swing.JTextField jtextfielID_recep;
    private javax.swing.JTextField jtextfiel_Laboratorio_Medicamento;
    private javax.swing.JTextField jtextfieldApellido_recep;
    private javax.swing.JTextField jtextfieldNombre_Medicamento;
    private javax.swing.JTextField jtextfieldTelefono_recep;
    private javax.swing.JTextField jtextfield_cantidad_Medicamento;
    private javax.swing.JPanel panel_enfermedades_1;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCodigoSala;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreSala;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
