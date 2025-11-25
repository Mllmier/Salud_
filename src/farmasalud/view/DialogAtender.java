/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package farmasalud.view;

import Controller.ControllerCargarMedicosCitas;
import Controller.ControllerCitas;
import Controller.ControllerOrdenMedica;
import Controller.ControllerPaciente;
import DAOImpl.OrdenMedicaDAOImpl;
import Listener.PacienteListener;
import dao.OrdenMedicaDAO;
import jakarta.mail.internet.ParseException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Cita;
import model.Medico;
import model.OrdenMedica;
import model.Paciente;


 
/**
 *
 * @author Maria liz
 */
public class DialogAtender extends javax.swing.JDialog implements PacienteListener {
private Cita citaSeleccionada;
    private Paciente paciente;

private Cita cita;
private DefaultTableModel tableModel;
private int filaSeleccionada;
private int columnaEstado = 7; 
    private String documentoPaciente;

    OrdenMedicaDAO OrdenMEdica = new OrdenMedicaDAOImpl();
    
    public DialogAtender(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    ControllerPaciente.getInstance().agregarPacienteListener(this);

    }
      @Override
public void actualizar(Paciente paciente) {
    if (paciente != null && this.documentoPaciente != null 
        && paciente.getNumeroDocumento().equals(documentoPaciente)) {

        setPesoYAltura(paciente.getPeso(), paciente.getAltura());
        JOptionPane.showMessageDialog(this, "Peso y altura actualizados del paciente.");
    }
}
public void setDocumentoPaciente(String documentoPaciente) {
    this.documentoPaciente = documentoPaciente;
}


    public void setFechaYHora(String fecha, String hora,String idCita,String estado,String motivo,String sede,String documento) {
    
    lblFechaCita.setText(fecha);  
    lblIdCita.setText(idCita);
    lblHoraCita.setText(hora);  
    lblEstado.setText(estado);
    lblMotivo.setText(motivo);
    lblSede.setText(sede);
    
}
    
    public void setCita(Cita cita) {
    this.cita = cita;
   
}
    
    public void setCitaSeleccionada(Cita cita) {
    this.citaSeleccionada = cita;
}
 

  




// Dentro de tu clase

private boolean esFechaValida(String fecha, String formato) {
    try {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false); // Para validar estrictamente la fecha
        sdf.parse(fecha);
        return true;
    } catch (Exception e) {
        return false;
    }
}
  public void setMedicamento(String medicamento) {
    if (txtMedicamento.getText().trim().isEmpty()) {
        txtMedicamento.setText(medicamento);
    } else {
        txtMedicamento.append("\n" + medicamento);
    }
}
public void setExamenes(String examen){
    if (txtExamenes.getText().trim().isEmpty()) {
        txtExamenes.setText(examen);
    }else{
     txtExamenes.append("\n"+examen);
    }
}
private Medico medicoSeleccionado;

public void setMedicoSeleccionado(Medico medico) {
    this.medicoSeleccionado = medico;
}
public void setTableModel(DefaultTableModel tableModel) {
    this.tableModel = tableModel;
}

public void setFilaSeleccionada(int fila) {
    this.filaSeleccionada = fila;
}



  private void buscarPaciente() {
        String documento = txtDocumento.getText().trim();

        if (documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el documento del paciente.");
            return;
        }

        ControllerCargarMedicosCitas controller = new ControllerCargarMedicosCitas();
        model.Paciente paciente = controller.buscarPacientePorDocumento(documento);

        if (paciente != null) {
            lblNombre.setText(paciente.getNombres() != null ? paciente.getNombres() : "");
            lblApellido.setText(paciente.getApellidos() != null ? paciente.getApellidos() : "");

            Object fecha = paciente.getFechaNacimiento();
            String fechaFormateada = "";

            if (fecha == null) {
                System.out.println("Fecha de nacimiento es null");
                fechaFormateada = "No disponible";
            } else if (fecha instanceof Date) {
                fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format((Date) fecha);
            } else if (fecha instanceof String fechaStr) {
                System.out.println("Fecha como String: " + fechaStr);
                try {
                    // Intentar yyyy-MM-dd
                    Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
                    fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(parsed);
                } catch (Exception e1) {
                    try {
                        // Intentar dd/MM/yyyy
                        Date parsed = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                        fechaFormateada = new SimpleDateFormat("dd/MM/yyyy").format(parsed);
                    } catch (Exception e2) {
                        System.out.println("Formato de fecha no reconocido: " + fechaStr);
                        fechaFormateada = "Formato inválido";
                    }
                }

            } else if (fecha instanceof LocalDate localDate) {
                fechaFormateada = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                System.out.println("Tipo inesperado de fecha: " + fecha.getClass());
                fechaFormateada = "No disponible";
            }

            lblFechaNacimiento.setText(fechaFormateada);

            lblSexo.setText(paciente.getSexo() != null ? paciente.getSexo() : "");
            lblEps.setText(paciente.getEps() != null ? paciente.getEps() : "");
            lblEmail.setText(paciente.getEmail() != null ? paciente.getEmail() : "");
            lblCelular.setText(paciente.getCelular() != null ? paciente.getCelular() : "");
            lblTipoSangre.setText(paciente.getTipoSangre() != null ? paciente.getTipoSangre() : "");

            txtAntecedentes.setText(paciente.getAntecedentes() != null ? paciente.getAntecedentes() : "");
            txtAntecedentes.setEditable(false);

            txtPeso.setText(String.valueOf(paciente.getPeso()));
            lblAltura.setText(String.valueOf(paciente.getAltura()));

        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el paciente con el documento ingresado.",
                    "Paciente no encontrado",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposPaciente();
        }
    }
  public void setPesoYAltura(double peso, double altura) {
    txtPeso.setText(String.valueOf(peso));
    lblAltura.setText(String.valueOf(altura));
}


    private void limpiarCamposPaciente() {
        lblNombre.setText("");
        lblApellido.setText("");
        lblFechaNacimiento.setText("");
        lblSexo.setText("");
        lblEps.setText("");
        lblEmail.setText("");
        lblCelular.setText("");
        lblAltura.setText("");
        txtPeso.setText("");
        txtAntecedentes.setText("");
        lblIdCita.setText("");
        lblFechaCita.setText("");
        lblHoraCita.setText("");
    }
    public void setDiagnostico(String diagnostico) {
    areaDiagnostico.setText(diagnostico);
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
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaDiagnostico = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblApellido = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        lblFechaNacimiento = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        lblTipoSangre = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        lblEps = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        lblFechaCita = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblHoraCita = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAntecedentes = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        lblAltura = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblSede = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        lblMotivo = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        lblIdCita = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnSeleccionarEnfermedad = new javax.swing.JButton();
        lblDiagnostico = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnSeleccionarMedicamento = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMedicamento = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAreceta = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtExamenes = new javax.swing.JTextArea();
        btnSeleccionarExamenes = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(28, 43, 110));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 930, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 930, 110));

        areaDiagnostico.setColumns(20);
        areaDiagnostico.setRows(5);
        areaDiagnostico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                areaDiagnosticoMouseClicked(evt);
            }
        });
        areaDiagnostico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                areaDiagnosticoKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(areaDiagnostico);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 320, 60));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Nombre ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 80, -1));
        jPanel2.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 120, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Apellido");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator1.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(93, 42, 120, 10));
        jPanel2.add(lblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 110, 40));

        jSeparator2.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator2.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 120, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Email ");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));
        jPanel2.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 110, 40));

        jSeparator3.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 120, 10));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Altura ");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jSeparator4.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator4.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, 120, 10));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Fecha Nacimiento ");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));
        jPanel2.add(lblFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 10, 140, 30));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));

        jSeparator6.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator6.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 140, 10));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Grupo sangre ");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 140, -1));
        jPanel2.add(lblTipoSangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 140, 30));

        jSeparator7.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator7.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 140, 10));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Eps");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Peso ");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        txtPeso.setBorder(null);
        txtPeso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPesoMouseClicked(evt);
            }
        });
        txtPeso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesoActionPerformed(evt);
            }
        });
        jPanel2.add(txtPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 130, 30));

        jSeparator8.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator8.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 130, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Sexo");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, -1, -1));
        jPanel2.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 130, 40));

        jSeparator9.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator9.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 130, 10));
        jPanel2.add(lblEps, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 130, 40));

        jSeparator10.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator10.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, 130, 10));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setText("Id Cita");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, -1, -1));

        jSeparator11.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator11.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 140, 10));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("Fecha Cita ");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 90, 30));
        jPanel2.add(lblFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 140, 40));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("HoraCita ");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, -1, -1));
        jPanel2.add(lblHoraCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 140, 110, 30));

        jSeparator12.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator12.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, 110, 10));

        txtAntecedentes.setEditable(false);
        txtAntecedentes.setColumns(20);
        txtAntecedentes.setRows(5);
        jScrollPane2.setViewportView(txtAntecedentes);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 140, 170, 70));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Telefono");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, -1, -1));
        jPanel2.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, 120, 40));

        jSeparator13.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator13.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 120, 10));

        lblAltura.setBorder(null);
        lblAltura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblAlturaActionPerformed(evt);
            }
        });
        jPanel2.add(lblAltura, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 120, 30));

        lblEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEstadoMouseClicked(evt);
            }
        });
        jPanel2.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, 110, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Estado");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 190, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Sede");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, -1, -1));
        jPanel2.add(lblSede, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 120, 40));

        jSeparator14.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator14.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 120, -1));

        jSeparator15.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator15.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 110, 10));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Motivo Cita");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, -1, -1));
        jPanel2.add(lblMotivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 220, 100, 40));

        jSeparator16.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator16.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 260, 100, -1));

        lblIdCita.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lblIdCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 160, 20));

        jSeparator17.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, 160, 10));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setText("Antendentes");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 860, 280));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Documento ");

        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel6)
                .addGap(54, 54, 54)
                .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(btnBuscar)
                .addContainerGap(378, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnBuscar))
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 860, 40));

        btnSeleccionarEnfermedad.setText("Seleccionar Enfermedad ");
        btnSeleccionarEnfermedad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarEnfermedadActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarEnfermedad, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 460, 170, 40));

        lblDiagnostico.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDiagnostico.setText("Diagnostico");
        jPanel1.add(lblDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 640, -1, -1));

        btnSeleccionarMedicamento.setText("Seleccionar Medicamento");
        btnSeleccionarMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarMedicamentoActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarMedicamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 520, 170, 40));

        txtMedicamento.setColumns(20);
        txtMedicamento.setRows(5);
        txtMedicamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMedicamentoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(txtMedicamento);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 450, 210, 80));

        txtAreceta.setColumns(20);
        txtAreceta.setRows(5);
        txtAreceta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtArecetaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(txtAreceta);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 550, -1, -1));

        jLabel4.setText("Receta");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 526, 40, 30));

        jLabel12.setText("Examenes");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, -1, -1));

        txtExamenes.setColumns(20);
        txtExamenes.setRows(5);
        jScrollPane5.setViewportView(txtExamenes);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 560, 310, 70));

        btnSeleccionarExamenes.setText("Seleccionar Examen");
        btnSeleccionarExamenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarExamenesActionPerformed(evt);
            }
        });
        jPanel1.add(btnSeleccionarExamenes, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 570, 170, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSeleccionarEnfermedadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarEnfermedadActionPerformed
java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
    Enfermedades dialog = new Enfermedades((java.awt.Frame) parentWindow, true);
    dialog.setDialogAtender(this); 
    dialog.setLocationRelativeTo(parentWindow);
    dialog.inicializarListener(); 
    dialog.setVisible(true);
   
    }//GEN-LAST:event_btnSeleccionarEnfermedadActionPerformed

    private void txtPesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPesoActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
  String documento = txtDocumento.getText().trim();
    
    if (documento.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Ingrese un número de documento.", 
            "Campo vacío", 
            JOptionPane.WARNING_MESSAGE);
        txtDocumento.requestFocus(); // Enfocar el campo
        return;
    }

    // 2. Validar que solo contenga números (si es necesario)
    if (!documento.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, 
            "El documento solo puede contener números.", 
            "Error de formato", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.setText(""); // Limpiar el campo
        txtDocumento.requestFocus();
        return;
    }

    // 3. Validar longitud (ejemplo: DNI debe tener 8 dígitos)
    if (documento.length() < 7 || documento.length() > 10) {
        JOptionPane.showMessageDialog(this, 
            "El documento debe tener entre 7 y 10 dígitos.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.requestFocus();
        return;
    }

    // 4. Ejecutar la búsqueda del paciente
    try {
        buscarPaciente(); // Llama a tu método de búsqueda
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error al buscar paciente: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }       

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        // TODO add your handling code here:
         String documento = txtDocumento.getText().trim();
    
    // Validar que no esté vacío
    if (documento.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "El documento no puede estar vacío.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.requestFocus();
        return;
    }
    
    // Validar que solo contenga números
    if (!documento.matches("\\d+")) {
        JOptionPane.showMessageDialog(this, 
            "Solo se permiten números en el documento.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.setText("");
        txtDocumento.requestFocus();
        return;
    }
    
    // Validar longitud (7 a 10 dígitos)
    if (documento.length() < 7 || documento.length() > 10) {
        JOptionPane.showMessageDialog(this, 
            "El documento debe tener entre 7 y 10 dígitos.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        txtDocumento.requestFocus();
        return;
    }
    }//GEN-LAST:event_txtDocumentoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    if (citaSeleccionada == null) {
        JOptionPane.showMessageDialog(this, "No se ha seleccionado ninguna cita.");
        return;
    }

        if (txtMedicamento.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El medicamento no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        txtMedicamento.requestFocus();
        return;
    }
    
    if (areaDiagnostico.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "El diagnóstico no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
        areaDiagnostico.requestFocus();
        return;
    }

    // Validar formatos numéricos
    try {
        double altura = Double.parseDouble(lblAltura.getText().trim());
        if (altura <= 0) {
            JOptionPane.showMessageDialog(this, "La altura debe ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Formato de altura inválido", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        double peso = Double.parseDouble(txtPeso.getText().trim());
        if (peso <= 0) {
            JOptionPane.showMessageDialog(this, "El peso debe ser mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Formato de peso inválido", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Si todo está correcto, guardar
    try {
        OrdenMedica guardarOrden = getOrdenMedica();
        OrdenMEdica.guardarOrdenMedica(guardarOrden);
       OrdenMedica ordenmedica = getOrdenMedica();
   if (citaSeleccionada != null) {
        citaSeleccionada.setEstado(Cita.EstadoCita.COMPLETADA);
        ControllerCitas.getInstance().actualizarCita(citaSeleccionada);
        
        // Actualizar cita en controlador / DAO para persistir
        ControllerCitas controllerCitas = ControllerCitas.getInstance();
        controllerCitas.actualizarCita(citaSeleccionada);

        // Notificar actualización para refrescar interfaces
        controllerCitas.notificarCitaActualizada(citaSeleccionada);

        // Actualizar estado en la tabla (columnaEstado es el índice correcto)
        if (tableModel != null && filaSeleccionada != -1) {
            tableModel.setValueAt("Completada", filaSeleccionada, columnaEstado);
        }
    }    
   
       
   Paciente paciente = citaSeleccionada.getPaciente();
   if (paciente == null) {
            JOptionPane.showMessageDialog(this, "La cita seleccionada no tiene paciente asociado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
paciente.setPeso(Integer.parseInt(txtPeso.getText().trim()));
paciente.setAltura(Double.parseDouble(lblAltura.getText().trim()));

// Guardar los cambios en el DAO
ControllerPaciente controllerPaciente = ControllerPaciente.getInstance();
controllerPaciente.actualizarPaciente(paciente.getNumeroDocumento(), paciente);

// Notificar a las interfaces que escuchan cambios en pacientes
controllerPaciente.notificarPacienteActualizado(paciente);
   
   JOptionPane.showMessageDialog(this, "Orden Guardada correctamente");
       
        this.dispose();
    } catch(Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar la orden: " + e.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); 
    }
      
    


          
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSeleccionarMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarMedicamentoActionPerformed
java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
    DialogMedicamentos dialog = new DialogMedicamentos((java.awt.Frame) parentWindow, true);
    dialog.setDialogMedicamento(this); 
    dialog.setLocationRelativeTo(parentWindow);
    dialog.inicializarListener(); 
    dialog.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnSeleccionarMedicamentoActionPerformed

    private void areaDiagnosticoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_areaDiagnosticoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_areaDiagnosticoMouseClicked

    private void areaDiagnosticoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_areaDiagnosticoKeyTyped
        // TODO add your handling code here:
     
    }//GEN-LAST:event_areaDiagnosticoKeyTyped

    private void txtArecetaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtArecetaMouseClicked
       
    }//GEN-LAST:event_txtArecetaMouseClicked

    private void txtMedicamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMedicamentoMouseClicked
     
    }//GEN-LAST:event_txtMedicamentoMouseClicked

    private void lblAlturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblAlturaActionPerformed
     

    }//GEN-LAST:event_lblAlturaActionPerformed

    private void txtPesoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPesoMouseClicked
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtPesoMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
       
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void lblEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEstadoMouseClicked
        // TODO add your handling code here:
        
   
    }//GEN-LAST:event_lblEstadoMouseClicked

    private void btnSeleccionarExamenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarExamenesActionPerformed
        // TODO add your handling code here:
        java.awt.Window parentWindow = SwingUtilities.getWindowAncestor(this);
    Dialog_Examenes dialog = new Dialog_Examenes((java.awt.Frame) parentWindow, true);
    dialog.setDialogExamenes(this);
    dialog.setLocationRelativeTo(parentWindow);
    dialog.inicializarListener(); 
    dialog.setVisible(true);
        
    }//GEN-LAST:event_btnSeleccionarExamenesActionPerformed

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
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAtender.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAtender dialog = new DialogAtender(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaDiagnostico;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSeleccionarEnfermedad;
    private javax.swing.JButton btnSeleccionarExamenes;
    private javax.swing.JButton btnSeleccionarMedicamento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField lblAltura;
    private javax.swing.JLabel lblApellido;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblDiagnostico;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEps;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaCita;
    private javax.swing.JLabel lblFechaNacimiento;
    private javax.swing.JLabel lblHoraCita;
    private javax.swing.JLabel lblIdCita;
    private javax.swing.JLabel lblMotivo;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblSede;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblTipoSangre;
    private javax.swing.JTextArea txtAntecedentes;
    private javax.swing.JTextArea txtAreceta;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextArea txtExamenes;
    private javax.swing.JTextArea txtMedicamento;
    private javax.swing.JTextField txtPeso;
    // End of variables declaration//GEN-END:variables


public OrdenMedica getOrdenMedica() {
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
    String receta = txtAreceta != null ? txtAreceta.getText() : "";
    String examen = txtExamenes != null ? txtExamenes.getText() : "";
List<String> listaMedicamentos = new ArrayList<>();
if (txtMedicamento != null) {
    String texto = txtMedicamento.getText().trim();
    if (!texto.isEmpty()) {
        String[] medicamentos = texto.split("\\n"); // separa por líneas
        for (String med : medicamentos) {
            if (!med.trim().isEmpty()) {
                listaMedicamentos.add(med.trim());
            }
        }
    }
}    String fechacita= lblFechaCita != null ? lblFechaCita.getText() : "";
    String horacita=lblHoraCita != null ? lblHoraCita.getText() : "";
    String nombreMedico = medicoSeleccionado != null ? medicoSeleccionado.getNombres(): "";
    String apellidoMedico = medicoSeleccionado != null ? medicoSeleccionado.getApellidos() : "";
    String especialidad = medicoSeleccionado != null ? medicoSeleccionado.getEspecialidad() : "";
    String idCita = lblIdCita != null ? lblIdCita.getText() : "";
   String sede = lblSede != null ? lblSede.getText() : "";
   String motivo = lblMotivo!= null ? lblMotivo.getText() : ""; 
    String estado = lblEstado != null ? lblEstado.getText() : "";


    return new OrdenMedica(nombre, apellido, email, altura, peso, fechaNacimiento, 
                         tipoSangre, antecedentes, celular, sexo, eps, diagnostico,receta,examen,
            listaMedicamentos,fechacita,horacita,nombreMedico,apellidoMedico,especialidad,idCita,sede,motivo,estado);
   }
}

