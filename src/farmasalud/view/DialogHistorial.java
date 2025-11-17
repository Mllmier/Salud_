/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package farmasalud.view;

import Controller.ControllerOrdenMedica;
import model.Cita;
import Controller.ControllerPaciente;
import Controller.ControllerCargarMedicosCitas;
import DAOImpl.OrdenMedicaDAOImpl;

import Listener.PacienteListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Medico;
import model.OrdenMedica;

/**
 *
 * @author HP
 */
public class DialogHistorial extends javax.swing.JDialog implements PacienteListener {
private Paciente paciente;
   private Cita citaSeleccionada;
private DefaultTableModel tableModel;
private OrdenMedicaDAOImpl ordenDao = new OrdenMedicaDAOImpl();
   private int filaSeleccionada;
   private int columnaEstado = 7; 

   private String documentoPaciente;
   
    public DialogHistorial(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ControllerPaciente.getInstance().agregarPacienteListener(this);
        
        
    }
    



      @Override
public void actualizar(model.Paciente paciente) {
    if (paciente != null && this.documentoPaciente != null 
        && paciente.getNumeroDocumento().equals(documentoPaciente)) {

        setPesoYAltura(paciente.getPeso(), paciente.getAltura());
        JOptionPane.showMessageDialog(this, "Peso y altura actualizados del paciente.");
    }
}
public void setPesoYAltura(double peso, double altura) {
    IblPeso.setText(String.valueOf(peso));
    IblAltura.setText(String.valueOf(altura));
}

    public void setDocumentoPaciente(String doumentoPaciente){
    this.documentoPaciente = doumentoPaciente;
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

   

    public void buscarPaciente(){
        String documento = txtDocumento.getText().trim();

        if (documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el documento del paciente.");
            return;
        }

        ControllerCargarMedicosCitas controller = new ControllerCargarMedicosCitas();
        model.Paciente paciente = controller.buscarPacientePorDocumento(documento);
        
        
        if (paciente != null) {
            IblNombre.setText(paciente.getNombres() != null ? paciente.getNombres() : "");
            IblApellido.setText(paciente.getApellidos() != null ? paciente.getApellidos() : "");

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

            IblFechaNacimiento.setText(fechaFormateada);

            IblSexo.setText(paciente.getSexo() != null ? paciente.getSexo() : "");
            IblEps.setText(paciente.getEps() != null ? paciente.getEps() : "");
            IblEmail.setText(paciente.getEmail() != null ? paciente.getEmail() : "");
            IblTelefono.setText(paciente.getCelular() != null ? paciente.getCelular() : "");
            IblGrupoSangre.setText(paciente.getTipoSangre() != null ? paciente.getTipoSangre() : "");

            txtAntecedentes.setText(paciente.getAntecedentes() != null ? paciente.getAntecedentes() : "");
            txtAntecedentes.setEditable(false);

            IblPeso.setText(String.valueOf(paciente.getPeso()));
            IblAltura.setText(String.valueOf(paciente.getAltura()));
            
             OrdenMedicaDAOImpl ordenDao = new OrdenMedicaDAOImpl();
        OrdenMedica ultimaOrden = ordenDao.obtenerPorIdCita(paciente.getNumeroDocumento());
        
        if (ultimaOrden != null) {
            txtDiagnosticos.setText(ultimaOrden.getDiagnostico());
            txtEnfermedades.setText(ultimaOrden.getReceta());
            txtDiagnosticos.setEditable(false);
            txtEnfermedades.setEditable(false);
        } else {
            txtDiagnosticos.setText("");
            txtEnfermedades.setText("");
            txtDiagnosticos.setEditable(true);
            txtEnfermedades.setEditable(true);
        }
             
// === Buscar y mostrar Orden Médica del paciente ===
// === Mostrar enfermedades y medicamentos de la Orden Médica (ya existente) ===


/* txtDiagnosticos.setText(ordenes.getDiagnostico() != null ? ordenes.getDiagnostico() : "");
    txtDiagnosticos.setEditable(false);
   
    txtEnfermedades.setText(ordenes.getReceta() != null ? ordenes.getReceta() : "");
txtEnfermedades.setEditable(false);*/
 
  // === Buscar y mostrar Orden Médica del paciente ===




  } else { 
      JOptionPane.showMessageDialog(this, "No se encontró el paciente con el documento ingresado.", "Paciente no encontrado", 
              JOptionPane.INFORMATION_MESSAGE); 
      limpiarCamposPaciente(); 
      limpiarCamposOrdenMedica();
        }
    }
    
    

public void limpiarCamposOrdenMedica() {
    txtDiagnosticos.setText("");
    txtEnfermedades.setText("");
    
    // Configurar campos como editables nuevamente
    txtDiagnosticos.setEditable(true);
    txtEnfermedades.setEditable(true);
}
    /*
    txtDiagnosticos.setText(ordenes.getDiagnostico() != null ? ordenes.getDiagnostico() : "");
    txtDiagnosticos.setEditable(false);
   

    
    txtEnfermedades.setText(ordenes.getReceta() != null ? ordenes.getReceta() : "");
txtEnfermedades.setEditable(false);*/
     
       private void limpiarCamposPaciente() {
        IblNombre.setText("");
        IblApellido.setText("");
        IblFechaNacimiento.setText("");
        IblSexo.setText("");
        IblEps.setText("");
        IblEmail.setText("");
        IblTelefono.setText("");
        IblAltura.setText("");
        IblPeso.setText("");
        txtAntecedentes.setText("");
        txtEnfermedades.setText("");
        txtDiagnosticos.setText("");
       
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDocumento = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        IblNombre = new javax.swing.JLabel();
        IblApellido = new javax.swing.JLabel();
        IblEmail = new javax.swing.JLabel();
        IblTelefono = new javax.swing.JLabel();
        IblSexo = new javax.swing.JLabel();
        IblFechaNacimiento = new javax.swing.JLabel();
        IblAltura = new javax.swing.JLabel();
        IblPeso = new javax.swing.JLabel();
        IblGrupoSangre = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        IblEps = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        txtIdcita = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAntecedentes = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDiagnosticos = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtEnfermedades = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(28, 43, 110));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 990, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 110));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Documento");

        Buscar.setText("Buscar");
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(Buscar)
                .addContainerGap(460, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar))
                .addGap(10, 10, 10))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 920, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Nombre");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Apellido");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Email");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Fecha De Nacimiento");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 40, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Telefono");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Altura");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Peso");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Sexo");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Grupo Sangre");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 190, -1, -1));
        jPanel1.add(IblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 110, 20));
        jPanel1.add(IblApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 90, 20));
        jPanel1.add(IblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 90, 20));
        jPanel1.add(IblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 80, 20));
        jPanel1.add(IblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 100, 20));
        jPanel1.add(IblFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, 100, 20));
        jPanel1.add(IblAltura, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, 80, 20));
        jPanel1.add(IblPeso, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, 100, 20));
        jPanel1.add(IblGrupoSangre, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 200, 90, 20));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 53, 120, 10));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 90, 10));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 90, 10));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 263, 100, 10));

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 80, -1));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 63, 80, 10));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 80, 10));

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 80, 10));

        jSeparator9.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 220, 100, 10));

        jLabel12.setText("Eps");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));
        jPanel1.add(IblEps, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 240, 80, 10));

        jSeparator10.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 253, 80, 10));

        jLabel15.setText("id cita");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, -1, -1));
        jPanel1.add(txtIdcita, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 250, 90, 20));

        jSeparator11.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 270, 80, 10));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 920, 290));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Antecedentes");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, -1, -1));

        txtAntecedentes.setColumns(20);
        txtAntecedentes.setRows(5);
        jScrollPane1.setViewportView(txtAntecedentes);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 240, 70));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Diagnosticos");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 480, -1, -1));

        txtDiagnosticos.setColumns(20);
        txtDiagnosticos.setRows(5);
        jScrollPane2.setViewportView(txtDiagnosticos);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 510, 310, 110));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Enfermedades");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 480, -1, -1));

        txtEnfermedades.setColumns(20);
        txtEnfermedades.setRows(5);
        jScrollPane3.setViewportView(txtEnfermedades);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 510, 240, 110));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 990, 690));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        // TODO add your handling code here:
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
    }//GEN-LAST:event_BuscarActionPerformed

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
            java.util.logging.Logger.getLogger(DialogHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogHistorial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogHistorial dialog = new DialogHistorial(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton Buscar;
    private javax.swing.JLabel IblAltura;
    private javax.swing.JLabel IblApellido;
    private javax.swing.JLabel IblEmail;
    private javax.swing.JLabel IblEps;
    private javax.swing.JLabel IblFechaNacimiento;
    private javax.swing.JLabel IblGrupoSangre;
    private javax.swing.JLabel IblNombre;
    private javax.swing.JLabel IblPeso;
    private javax.swing.JLabel IblSexo;
    private javax.swing.JLabel IblTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextArea txtAntecedentes;
    private javax.swing.JTextArea txtDiagnosticos;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextArea txtEnfermedades;
    private javax.swing.JLabel txtIdcita;
    // End of variables declaration//GEN-END:variables

   

    
}
