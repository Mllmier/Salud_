
package farmasalud.view;

import Controller.ControllerCitas;
import Controller.ControllerCitasPaciente;
import dao.usuarioDAO;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Maria liz
 */
public class Paciente extends javax.swing.JFrame {
  private String documentoPaciente;
      private final ControllerCitasPaciente controller;
    private ConsultarCita dialogConsulta;
    private model.Paciente pacienteLogueado;





    public Paciente( ) {
        initComponents();
 this.controller = ControllerCitasPaciente.getInstance();


  this.setLocationRelativeTo(null);

    }
   public Paciente(model.Paciente paciente) {
        initComponents();
        this.controller = ControllerCitasPaciente.getInstance(); 
        this.pacienteLogueado = paciente;
        this.setLocationRelativeTo(null);
    }

     
   
    public void inicializarConPaciente(String documento) {
        if (documento == null || documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Documento inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.documentoPaciente = documento;
    }


   
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        btnConsultar = new javax.swing.JButton();
        btnAgendar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(28, 43, 110));
        jPanel2.setName(""); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Citas Asignadas ");

        jSeparator4.setBackground(new java.awt.Color(204, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel6.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 1090, 140));

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        jPanel6.add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 460, 171, 48));

        btnAgendar.setText("Agendar Cita ");
        btnAgendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendarActionPerformed(evt);
            }
        });
        jPanel6.add(btnAgendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 530, 160, 48));

        jButton1.setText("Cambiar Contraseña");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, -1, 48));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\HP\\OneDrive\\Desktop\\Imagen de WhatsApp 2025-11-25 a las 07.52.27_c077e10c.jpg")); // NOI18N
        jLabel1.setText("jLabel1");
        jPanel6.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 600, 340));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("Farma Salud");
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("\"Tu bienestar, nuestra prioridad.\"  ");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 40, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 1100, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
   if (documentoPaciente == null || documentoPaciente.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "No se ha cargado el documento del paciente", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (dialogConsulta == null) {
        dialogConsulta = new ConsultarCita(this, true);
        dialogConsulta.setDocumentoPaciente(documentoPaciente);
    } else {
        dialogConsulta.actualizarTablaCitas(); 
    }

    dialogConsulta.setVisible(true);   
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnAgendarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendarActionPerformed
     if (documentoPaciente == null || documentoPaciente.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "No se ha cargado el documento del paciente", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        
        if (dialogConsulta == null) {
            dialogConsulta = new ConsultarCita(this, true);
            dialogConsulta.setDocumentoPaciente(documentoPaciente);
        }

        DialogAgendarCita dialogAgendar = new DialogAgendarCita(this, true);
        dialogAgendar.setDialogConsultarCitas(dialogConsulta);
        dialogAgendar.setDocumentoPaciente(documentoPaciente);
        dialogAgendar.setVisible(true);

        dialogConsulta.actualizarTablaCitas();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, 
            "Error abriendo agenda de citas: " + ex.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_btnAgendarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     CredencialesPacienteDailog dialog = new CredencialesPacienteDailog(this, "arai@gmail.com");
    dialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Paciente().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgendar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration//GEN-END:variables
}
