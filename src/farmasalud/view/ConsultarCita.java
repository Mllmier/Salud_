/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package farmasalud.view;

import Listener.CitaListener;
import Controller.ControllerCitasPaciente;
import DAOImpl.OrdenMedicaDAOImpl;
import DAOImpl.PacienteDAOImpl;
import dao.CitaDAO;
import dao.OrdenMedicaDAO;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;
import model.Cita;
import model.OrdenMedica;


/**
 *
 * @author Maria liz
 */
public class ConsultarCita extends javax.swing.JDialog implements CitaListener {
    private final ControllerCitasPaciente controllerCitasPaciente = ControllerCitasPaciente.getInstance();
    private boolean isTableInitialized = false;
    CitaDAO citasDAO;
    private String documentoPaciente;
    private final ControllerCitasPaciente controller;
    

    public ConsultarCita(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
         this.controller = ControllerCitasPaciente.getInstance();
        this.controller.addCitaListener(this);
        configurarControlador();
          this.setLocationRelativeTo(null);
      
        
    }
    public void InicializarMenu(){
        JMenuItem dialogCancelar =new JMenuItem("Cancelar o Modificar Cita ");
        tableCitas.add(dialogCancelar);
    }





    private void configurarControlador() {
        controller.setJDateFechaCita(dateCita);
        controller.setTablaCitas(tableCitas);
        controller.initTableModelCita();
        
        if (documentoPaciente != null) {
            controller.cargarCitasPorPaciente(documentoPaciente);
        }
    }
    

    @Override
    public void citaAgregada(Cita cita) {
        controller.cargarCitasPorPaciente(documentoPaciente);
    }

    @Override
    public void dispose() {
        controller.removeCitaListener(this);
        super.dispose();
    }

  public void setDocumentoPaciente(String documento) {
    this.documentoPaciente = documento;
    if (documento != null && !documento.trim().isEmpty()) {
        controller.setTablaCitas(tableCitas);         
        controller.initTableModelCita();             
        controller.cargarCitasPorPaciente(documento);
                controller.configurarColoresTablaCitas();

           configurarPopupMenu(); 
           
    }
}
   private void configurarPopupMenu() {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem itemCancelar = new JMenuItem("Reprogramar");
    popupMenu.add(itemCancelar);

     itemCancelar.addActionListener(new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent evt) {
                int filaSeleccionada = tableCitas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    String estado = tableCitas.getValueAt(filaSeleccionada, 11).toString(); 
                    if (estado.equalsIgnoreCase("Completada")) {
                        JOptionPane.showMessageDialog(null, "No se puede reprogramar una cita completada.");
                        return;
                    }

                    DialogModificar dialog = new DialogModificar(null, true);
                    dialog.cargarDatosDesdeFilaSeleccionada(tableCitas, filaSeleccionada);  
                    dialog.setLocationRelativeTo(null);
                    dialog.setTablaCitasPaciente(tableCitas); 
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.");
                }
            }
        });

     JMenuItem itemModificarAvanzado = new JMenuItem("Cancelar");
    popupMenu.add(itemModificarAvanzado);

    itemModificarAvanzado.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            int filaSeleccionada = tableCitas.getSelectedRow();
            if (filaSeleccionada >= 0) {
                String estado = tableCitas.getValueAt(filaSeleccionada, 11).toString();
                if (estado.equalsIgnoreCase("Completada")) {
                    JOptionPane.showMessageDialog(null, "No se puede cancelar una cita completada.");
                    return;
                }

                DialogCancelar2 dialog2 = new DialogCancelar2(null, true); 
                dialog2.mostrarDatosCitaEnLabels(tableCitas, filaSeleccionada);
                dialog2.setTablaCitasPaciente(tableCitas);
                dialog2.setLocationRelativeTo(null);
                dialog2.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.");
            }
        }
    });

  JMenuItem itemVerOrden = new JMenuItem("Ver orden médica");
popupMenu.add(itemVerOrden);

itemVerOrden.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
        int filaSeleccionada = tableCitas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            String idCita = tableCitas.getValueAt(filaSeleccionada, 5).toString(); 
            OrdenMedica orden = controller.obtenerOrdenMedicaPorCita(idCita); 

            if (orden != null) {
                DialogOrdenMedica dialogOrden = new DialogOrdenMedica(null, true);
                dialogOrden.setOrdenMedica(orden);  
                dialogOrden.cargarDatos();        
                dialogOrden.setLocationRelativeTo(null);
                dialogOrden.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una orden médica para esta cita.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.");
        }
    }
});


 


    tableCitas.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent evt) {
            mostrarPopup(evt);
        }

        @Override
        public void mouseReleased(MouseEvent evt) {
            mostrarPopup(evt);
        }

        private void mostrarPopup(MouseEvent evt) {
            if (evt.isPopupTrigger()) {
                int fila = tableCitas.rowAtPoint(evt.getPoint());
                if (fila >= 0) {
                    tableCitas.setRowSelectionInterval(fila, fila);
                    popupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
                }
            }
        }
    });
}
   public JTable getTableCitas() { 
       return tableCitas; 
   }

   public void actualizarTablaCitas() {
    if (documentoPaciente != null && !documentoPaciente.trim().isEmpty()) {
        controller.cargarCitasPorPaciente(documentoPaciente);
    } else {
        System.out.println("No hay documentoPaciente para actualizar la tabla.");
    }
    }
   
    @Override
    public void citaEliminada(String idCita) {
        controller.cargarCitasPorPaciente(documentoPaciente);
    }
     @Override
    public void citaActualizada(Cita cita) {
        controller.cargarCitasPorPaciente(documentoPaciente);
    }
  
     

    

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateCita = new com.toedter.calendar.JDateChooser();
        btnBuscarCita = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCitas = new javax.swing.JTable();
        btnRefrescar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Fecha Cita ");

        btnBuscarCita.setText("Buscar");
        btnBuscarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCitaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateCita, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnBuscarCita)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateCita, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCita, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        tableCitas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableCitas);

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        jButton1.setText("generar pdf");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(34, 34, 34)
                .addComponent(btnRefrescar)
                .addGap(62, 62, 62))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRefrescar)
                    .addComponent(jButton1))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCitaActionPerformed
controllerCitasPaciente.buscarCitasPorFecha(); 
    }//GEN-LAST:event_btnBuscarCitaActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
 controllerCitasPaciente.cargarCitasPorPaciente(documentoPaciente);        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    
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
            java.util.logging.Logger.getLogger(ConsultarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultarCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConsultarCita dialog = new ConsultarCita(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscarCita;
    private javax.swing.JButton btnRefrescar;
    private com.toedter.calendar.JDateChooser dateCita;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableCitas;
    // End of variables declaration//GEN-END:variables
}
