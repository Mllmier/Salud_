/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package farmasalud.view;

import Listener.CitaListener;
import Controller.ControllerCitasPaciente;
import dao.PacienteDAO;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.Cita;
import model.Medico;
import model.Paciente;

/**
 *
 * @author Maria liz
 */
public class DialogAgendarCita extends javax.swing.JDialog implements CitaListener {
    private ConsultarCita dialogConsultarCitas;
    private final ControllerCitasPaciente controller;

       private final ControllerCitasPaciente controllerCitasPaciente = ControllerCitasPaciente.getInstance();
    private String documentoPaciente;

 
    PacienteDAO pacienteDAO;




    public DialogAgendarCita(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       this.controller = ControllerCitasPaciente.getInstance();
        this.controller.addCitaListener(this); 
        configurarCitas();
       this.setLocationRelativeTo(null);

    }

    @Override
    public void citaAgregada(Cita cita) {
        if (dialogConsultarCitas != null) {
        System.out.println("Actualizando tabla del diálogo de consulta...");
        dialogConsultarCitas.actualizarTablaCitas();
    }
    }

    @Override
    public void dispose() {
        controller.removeCitaListener(this);
        super.dispose();
    }

    public void setDialogConsultarCitas(ConsultarCita dialogConsultarCitas) {
          this.dialogConsultarCitas = dialogConsultarCitas;
    if (dialogConsultarCitas != null) {
        controller.setTablaCitas(dialogConsultarCitas.getTableCitas());
    }
    }

    public void setDocumentoPaciente(String documento) {
        try {
            if (documento == null || documento.trim().isEmpty()) {
                throw new IllegalArgumentException("Documento de paciente no válido");
            }
            
            this.documentoPaciente = documento;
            
            if (!controller.cargarYPersistirPaciente(documento)) {
                throw new Exception("No se pudo cargar el paciente");
            }
            
            Paciente p = controller.getPacienteSeleccionado();
            if (p != null) {
                lblDocumentoPaciente.setText(p.getNumeroDocumento());
                lblNombrePaciente.setText(p.getNombres());
                lblApellidoPaciente.setText(p.getApellidos());
                lblEmailPaciente.setText(p.getEmail());
                lblEps.setText(p.getEps());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar paciente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarCitas() {

        controller.setCboHoraCita(cboHoraCita);
        controller.setCboTipoCita(cboTipoCita);
        controller.setCboMotivoCita(cboMotivoCita);
        controller.setCboEstado(cboEstadoCita);
        cboEstadoCita.removeAllItems();
        cboEstadoCita.addItem("PROGRAMADA");
         cboEstadoCita.setEnabled(false); 

        controller.setJDateFechaCita(jDateFechaCita);
                 Date fechaActual = new Date();
    
    if ( jDateFechaCita!= null) {
        jDateFechaCita.setMinSelectableDate(fechaActual);
        jDateFechaCita.setDateFormatString("yyyy-MM-dd");
    }
        controller.setLblIdCita(lblCita);
        controller.setCboSede(cboSede);
        controller.setLblConsultorio(lblConsultorio); 
        controller.setCboMedico(cboMedico);
        controller.setCboEspecialidadMedico(cboEspecialidadMedico);

        controller.cargarSalasEnComboBox(lblConsultorio);
        controller.cargarSedesEnComboBox(cboSede);
        controller.cargarMedicosEnComboBox();
        

        cboMedico.addActionListener(e -> {
            String seleccion = (String) cboMedico.getSelectedItem();
            if (seleccion != null && !seleccion.equals("<Seleccione>")) {
                Medico medico = controller.obtenerMedicoPorNombreCompleto(seleccion);
                if (medico != null && cboEspecialidadMedico != null) {
                    cboEspecialidadMedico.setSelectedItem(medico.getEspecialidad());
                }
            }
        });
         controller.cargarEspecialidadesPorSede(cboEspecialidadMedico, cboSede);
       controller.cargarMedicosPorSedeYEspecialidad(cboMedico, cboSede, cboEspecialidadMedico, lblConsultorio);
         cboSede.addActionListener(e -> {
             
      controller.cargarEspecialidadesPorSede(cboEspecialidadMedico, cboSede);
     });

     cboEspecialidadMedico.addActionListener(e -> {
         controller.cargarMedicosPorSedeYEspecialidad(cboMedico, cboSede, cboEspecialidadMedico,lblConsultorio);
      });

    }
    
    @Override
public void citaActualizada(Cita cita) {
   
    if (dialogConsultarCitas != null) {
        dialogConsultarCitas.actualizarTablaCitas();
    }
}

@Override
public void citaEliminada(String idCita) {
    if (dialogConsultarCitas != null) {
        dialogConsultarCitas.actualizarTablaCitas();
    }
}
      
   
   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        cboSede = new javax.swing.JComboBox<>();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        cboTipoCita = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboMotivoCita = new javax.swing.JComboBox<>();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        cboMedico = new javax.swing.JComboBox<>();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        cboEstadoCita = new javax.swing.JComboBox<>();
        jSeparator14 = new javax.swing.JSeparator();
        btnAgendar = new javax.swing.JButton();
        lblNombrePaciente = new javax.swing.JLabel();
        lblApellidoPaciente = new javax.swing.JLabel();
        lblDocumentoPaciente = new javax.swing.JLabel();
        lblEmailPaciente = new javax.swing.JLabel();
        jDateFechaCita = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        cboHoraCita = new javax.swing.JComboBox<>();
        jSeparator15 = new javax.swing.JSeparator();
        lblEps = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblCita = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        lblConsultorio = new javax.swing.JLabel();
        cboEspecialidadMedico = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Nombre ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 85, 36));

        jSeparator1.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator1.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 72, 180, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Apellido");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, 30));

        jSeparator2.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator2.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 180, 10));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Documento");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 110, 30));

        jSeparator3.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator3.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 180, 10));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Consultorio");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 350, 100, 30));

        jSeparator4.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator4.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 180, 10));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Sede");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, 90, 30));

        cboSede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSedeActionPerformed(evt);
            }
        });
        jPanel2.add(cboSede, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 180, 40));

        jSeparator5.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator5.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 180, 10));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Tipo Cita");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        cboTipoCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "Prioritaria", "Regular", "Control" }));
        jPanel2.add(cboTipoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 180, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Motivo Cita ");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 100, 30));

        cboMotivoCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "Control", "Seguimiento", "Prevencion", "Sintomas Agudos", "Enfermedad Cronica", "Problemas Especificos" }));
        jPanel2.add(cboMotivoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, 180, 40));

        jSeparator6.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator6.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 180, 10));

        jSeparator7.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator7.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 180, 10));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Email");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 80, 30));

        jSeparator8.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator8.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, 170, 10));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Eps");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, 60, 30));

        jSeparator9.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator9.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, 170, 10));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Medico");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, -1, -1));

        cboMedico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>" }));
        cboMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMedicoActionPerformed(evt);
            }
        });
        jPanel2.add(cboMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, 180, 40));

        jSeparator10.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator10.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 330, 180, 10));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Especialidad M");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 250, 130, 30));

        jSeparator12.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator12.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, 180, 10));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Fecha Cita");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, 100, 30));

        jSeparator13.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator13.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 380, 190, 10));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Estado");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        cboEstadoCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "PROGRAMADA", "COMPLETADA", "CANCELADA" }));
        jPanel2.add(cboEstadoCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 180, 40));

        jSeparator14.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator14.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, 180, 10));

        btnAgendar.setBackground(new java.awt.Color(109, 140, 172));
        btnAgendar.setText("Agendar ");
        btnAgendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, 110, 40));
        jPanel2.add(lblNombrePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 180, 40));
        jPanel2.add(lblApellidoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 150, 40));
        jPanel2.add(lblDocumentoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 150, 40));
        jPanel2.add(lblEmailPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 170, 40));
        jPanel2.add(jDateFechaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, 180, 40));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Hora Cita ");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 90, 30));

        cboHoraCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<Seleccione>", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00" }));
        jPanel2.add(cboHoraCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, 180, 40));

        jSeparator15.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator15.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 180, 10));
        jPanel2.add(lblEps, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 170, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Id Cita ");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        jPanel2.add(lblCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 180, 40));

        jSeparator11.setBackground(new java.awt.Color(28, 43, 110));
        jSeparator11.setForeground(new java.awt.Color(28, 43, 110));
        jPanel2.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, 180, 10));
        jPanel2.add(lblConsultorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 340, 190, 40));

        cboEspecialidadMedico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cboEspecialidadMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 240, 180, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 770, 510));

        jPanel3.setBackground(new java.awt.Color(28, 43, 110));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Agendar Cita");
        jPanel3.add(jLabel16);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarActionPerformed
   controller.guardarCitaDesdeFormulario();
    if (dialogConsultarCitas != null) {
    dialogConsultarCitas.actualizarTablaCitas();
   }
        
    }//GEN-LAST:event_btnAgendarActionPerformed

    private void cboSedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSedeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSedeActionPerformed

    private void cboMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMedicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMedicoActionPerformed

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
            java.util.logging.Logger.getLogger(DialogAgendarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAgendarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAgendarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAgendarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAgendarCita dialog = new DialogAgendarCita(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgendar;
    private javax.swing.JComboBox<String> cboEspecialidadMedico;
    private javax.swing.JComboBox<String> cboEstadoCita;
    private javax.swing.JComboBox<String> cboHoraCita;
    private javax.swing.JComboBox<String> cboMedico;
    private javax.swing.JComboBox<String> cboMotivoCita;
    private javax.swing.JComboBox<String> cboSede;
    private javax.swing.JComboBox<String> cboTipoCita;
    private com.toedter.calendar.JDateChooser jDateFechaCita;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblApellidoPaciente;
    private javax.swing.JLabel lblCita;
    private javax.swing.JLabel lblConsultorio;
    private javax.swing.JLabel lblDocumentoPaciente;
    private javax.swing.JLabel lblEmailPaciente;
    private javax.swing.JLabel lblEps;
    private javax.swing.JLabel lblNombrePaciente;
    // End of variables declaration//GEN-END:variables
}
