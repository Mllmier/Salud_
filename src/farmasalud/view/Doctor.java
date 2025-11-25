/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package farmasalud.view;

import Controller.ControllerCargarMedicosCitas;
import Controller.ControllerCitas;
import Controller.ControllerOrdenMedica;
import Controller.ControllerPaciente;
import DAOImpl.MedicoDAOImpl;
import dao.MedicoDAO;
import dao.PacienteDAO;
import java.time.ZoneId;
import java.text.SimpleDateFormat;
import java.awt.Color;
import javax.swing.RowFilter;
import javax.swing.RowFilter.Entry;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Cita;
import model.Medicamento;
import model.Medico;
import model.OrdenMedica;
import model.Paciente; 


/**
 *
 * @author HP
 */
public class Doctor extends javax.swing.JFrame {
   private DefaultTableModel tableModelConsultarMedico=new DefaultTableModel();
    private JPopupMenu popupMenu;
    private JMenuItem itemAtender;
    private JMenuItem itemHistorial;
    private Medico medicoLogueado;

    private ControllerPaciente controller;
    private DefaultTableModel tableModelPaciente;
    private ControllerOrdenMedica controllerorden;
    private final ControllerCitas controlllercitas=ControllerCitas.getInstance();
   ControllerCitas controllerCitas = ControllerCitas.getInstance();
    private DefaultTableModel tableModelCita;
    ControllerCargarMedicosCitas controllerCargarMedicos = new ControllerCargarMedicosCitas();


    private String documentoDoctor;
    public Doctor() {
      
    initComponents();
    
    
         popupMenu = new JPopupMenu();

        
         
         
        itemAtender = new JMenuItem("Atender cita");  
        popupMenu.add(itemAtender);
        
    

         JMenuItem itemNoAsistio = new JMenuItem("No asistió");
         popupMenu.add(itemNoAsistio);
       actualizarInterfaz();  
       
    

       
itemAtender.addActionListener(evt -> {
    int filaSeleccionada = tableCitasPorMedico.getSelectedRow();
    if (filaSeleccionada != -1) {
        String idCita = tableCitasPorMedico.getValueAt(filaSeleccionada, 5).toString();
        Cita cita = controllerCitas.buscarCitaPorId(idCita);
        
        if (cita == null) {
            JOptionPane.showMessageDialog(this, "No se encontró la cita con ID: " + idCita);
            return;
        }

        // Documento paciente
        Object valorDocumento = tableCitasPorMedico.getValueAt(filaSeleccionada, 0);
        String documentoPaciente = valorDocumento.toString(); 

        Paciente paciente = ControllerPaciente.getInstance().buscarPacientePorDocumento(documentoPaciente);
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el paciente con documento: " + documentoPaciente);
            return;
        }
        cita.setPaciente(paciente);

        // Datos de la tabla
        Object valorFecha = tableCitasPorMedico.getValueAt(filaSeleccionada, 8);
        Object valorHora = tableCitasPorMedico.getValueAt(filaSeleccionada, 6);
        Object valorMotivo = tableCitasPorMedico.getValueAt(filaSeleccionada, 15);
        Object valorEstado = tableCitasPorMedico.getValueAt(filaSeleccionada, 11);
        Object valorSede = tableCitasPorMedico.getValueAt(filaSeleccionada, 7);

        String estadoActual = valorEstado != null ? valorEstado.toString().trim().toUpperCase() : "";

        // ❌ NO Permitimos atender una cita ya completada
        if (estadoActual.equals("COMPLETADA")) {
            JOptionPane.showMessageDialog(this,
                "Esta cita ya fue atendida. No se puede volver a abrir.",
                "Acción no permitida",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // ❌ NO Permitimos atender si NO ASISTIÓ
        if (estadoActual.equals("NOASISTIO") || estadoActual.equals("NO ASISTIÓ")) {
            JOptionPane.showMessageDialog(this,
                "Esta cita fue marcada como 'NO ASISTIÓ'. No se puede atender.",
                "Acción no permitida",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // ✔ Si llega aquí, SÍ puede atender
        if (valorFecha != null && valorHora != null) {
            String fecha = valorFecha.toString();
            String hora = valorHora.toString();
            String motivo = valorMotivo != null ? valorMotivo.toString() : "";
            String sede = valorSede != null ? valorSede.toString() : "";
            String documento = valorDocumento.toString();

            DialogAtender dialog = new DialogAtender(null, true);
            dialog.setCita(cita);
            dialog.setCitaSeleccionada(cita);
            dialog.setDocumentoPaciente(documento);
            dialog.setMedicamento("");
            dialog.setMedicoSeleccionado(medicoLogueado);

            dialog.setFechaYHora(fecha, hora, idCita, estadoActual, sede, motivo, documento);

            dialog.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "La fila seleccionada no contiene hora o fecha válida.");
        }
    }
});



        // Agregar MouseListener para mostrar el menú contextual
        tableCitasPorMedico.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mostrarPopupMenu(e);
            }

            private void mostrarPopupMenu(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int fila = tableCitasPorMedico.rowAtPoint(e.getPoint());
                    if (fila != -1) {
                        tableCitasPorMedico.setRowSelectionInterval(fila, fila);
                        popupMenu.show(tableCitasPorMedico, e.getX(), e.getY());
                    }
                }
            }
        }); 
      itemNoAsistio.addActionListener(evt -> {
    int filaSeleccionada = tableCitasPorMedico.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una cita para marcar como 'No asistió'.");
        return;
    }

    // Obtener ID de la cita
    Object idObj = tableCitasPorMedico.getValueAt(filaSeleccionada, 5);
    String idCita = idObj != null ? idObj.toString().trim() : "";

    if (idCita.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontró Id de la cita en la fila seleccionada.");
        return;
    }

    // Buscar cita en memoria
    Cita cita = ControllerCitas.getInstance().buscarCitaPorId(idCita);
    if (cita == null) {
        JOptionPane.showMessageDialog(this, "No se encontró la cita con id: " + idCita);
        return;
    }

    // Si ya está marcada como NO ASISTIÓ evita volver a cambiar
    if (cita.getEstado() == Cita.EstadoCita.NOASISTIO) {
        JOptionPane.showMessageDialog(this,
            "Esta cita YA está marcada como 'No asistió'.",
            "Información",
            JOptionPane.INFORMATION_MESSAGE
        );
        return;
    }

    // Cambiar estado
    cita.setEstado(Cita.EstadoCita.NOASISTIO);
    ControllerCitas.getInstance().actualizarCita(cita);

    // REFRESCAR TABLA SEGÚN DISPONIBILIDAD DEL CONTROLLER
    try {
        if (controllerCargarMedicos != null) {
            controllerCargarMedicos.cargarCitasMedicoEnTabla();
        } else {
            // Si no existe el controlador, solo actualiza visualmente la tabla
            tableCitasPorMedico.setValueAt("NOASISTIO", filaSeleccionada, 11);
        }

        JOptionPane.showMessageDialog(this, "La cita fue marcada como 'No asistió'.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Error al actualizar la vista: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
        tableCitasPorMedico.setValueAt("NOASISTIO", filaSeleccionada, 11);
    }
});


     
    }
    private void actualizarEstadoEnTabla(String idCita, String nuevoEstado) {
    DefaultTableModel model = (DefaultTableModel) tableCitasPorMedico.getModel();
    int columnaId = 0;      // Asegúrate que el ID está en esta columna
    int columnaEstado = 11; // Asegúrate que el estado está en esta columna

    for (int i = 0; i < model.getRowCount(); i++) {
        Object valorId = model.getValueAt(i, columnaId);
        if (valorId != null && valorId.toString().equals(idCita)) {
            model.setValueAt(nuevoEstado, i, columnaEstado);
            break;
        }
    }
}


   
     public void inicializarConDoctor(String documento) {
        MedicoDAO medicodao = new MedicoDAOImpl();
         this.medicoLogueado = medicodao.buscarMedicoPorIdentificacion(documento);
        this.documentoDoctor = documento;
        actualizarInterfaz();

        ControllerCargarMedicosCitas controller = new ControllerCargarMedicosCitas();
        controller.setTablaCitas(tableCitasPorMedico); // tu JTable
        controller.setDocumentoMedico(documentoDoctor); // aquí ya tiene el valor correcto
        controller.initTableModelCita(); 
        controller.cargarCitasMedicoEnTabla(); 
   tableCitasPorMedico.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tableCitasPorMedico.getSelectedRow() != -1) {
                    mostrarDialogSiFilaSeleccionada(e);
                }
            }
        });
 
 

    }

    private void actualizarInterfaz() {
    }
    private void filtrarCitasPorFecha(Date fechaSeleccionada) {
    try {
        // Formatear la fecha seleccionada para comparación (mismo formato que en la tabla)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaSeleccionadaStr = sdf.format(fechaSeleccionada);
        
        System.out.println("Buscando fecha: " + fechaSeleccionadaStr); // Debug
        
        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tableCitasPorMedico.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tableCitasPorMedico.setRowSorter(sorter);

        // Crear filtro para mostrar solo las citas del día seleccionado
        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                try {
                    // Usar columna 8 que es "Fecha Cita"
                    Object fechaValue = entry.getValue(8);
                    
                    if (fechaValue != null) {
                        String fechaCitaStr = fechaValue.toString().trim();
                        System.out.println("Comparando: " + fechaCitaStr + " con " + fechaSeleccionadaStr); // Debug
                        
                        // Comparar las fechas como strings
                        boolean coincide = fechaCitaStr.equals(fechaSeleccionadaStr);
                        System.out.println("Resultado: " + coincide); // Debug
                        return coincide;
                    }
                    return false;
                } catch (Exception e) {
                    System.out.println("Error en filtro: " + e.getMessage());
                    return false;
                }
            }
        });
        
        // Mostrar resultado del filtrado
        int filasVisibles = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (sorter.convertRowIndexToView(i) >= 0) {
                filasVisibles++;
            }
        }
        System.out.println("Filas visibles después del filtro: " + filasVisibles);
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al filtrar citas: " + ex.getMessage(), 
                                    "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}
  
 private void mostrarDialogSiFilaSeleccionada(MouseEvent e) {
        int fila = tableCitasPorMedico.rowAtPoint(e.getPoint());
        if (fila >= 0) {
            tableCitasPorMedico.setRowSelectionInterval(fila, fila);
            
            Object valorFecha = tableCitasPorMedico.getValueAt(fila, 8);
            Object valorHora = tableCitasPorMedico.getValueAt(fila, 6);
            Object valoridCita=tableCitasPorMedico.getValueAt(fila,0);
            Object valorEstado=tableCitasPorMedico.getValueAt(fila, 11);
            Object valorMotivo=tableCitasPorMedico.getValueAt(fila, 15);
            Object valorSede=tableCitasPorMedico.getValueAt(fila,7);
            Object valorDocumento=tableCitasPorMedico.getValueAt(fila, 0);
            
            if (valorFecha != null && valorHora != null) {
                String fecha = valorFecha.toString();
                String hora = valorHora.toString();
                String idCita=valoridCita.toString();
                String estado=valorEstado.toString();
                String motivo=valorMotivo.toString();
                String sede=valorSede.toString();
                String documento=valorDocumento.toString();

                DialogAtender dialog = new DialogAtender(null, true);
                dialog.setFechaYHora(fecha, hora,idCita,estado,motivo,sede,documento);
                dialog.setLocationRelativeTo(tableCitasPorMedico);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(tableCitasPorMedico, "La fila seleccionada no contiene hora o fecha válida.");
            }
        }
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
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblIconRecepcion = new javax.swing.JLabel();
        Diagnostico = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanelDiagnostico = new javax.swing.JPanel();
        YY = new javax.swing.JScrollPane();
        tableCitasPorMedico = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        dateChooserF = new com.toedter.calendar.JDateChooser();
        dateChooserFecha = new javax.swing.JButton();
        btnCredenciales = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(28, 43, 110));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Doctor");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(64, 21, -1, 42));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 69, 228, 10));

        lblIconRecepcion.setBackground(new java.awt.Color(255, 255, 255));
        lblIconRecepcion.setText("      ");
        lblIconRecepcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));
        jPanel2.add(lblIconRecepcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 97, 102, 117));

        Diagnostico.setBackground(new java.awt.Color(28, 43, 110));
        Diagnostico.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                DiagnosticoAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        Diagnostico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DiagnosticoMouseReleased(evt);
            }
        });
        Diagnostico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosticoKeyPressed(evt);
            }
        });
        Diagnostico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 27)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Diagnostico");
        Diagnostico.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 50));

        jPanel2.add(Diagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 260, 70));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 630));

        jPanel3.setBackground(new java.awt.Color(10, 92, 184));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 17, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Farma Salud");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 17, 149, 54));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("\"Tu bienestar, nuestra prioridad.\"  ");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 28, -1, -1));
        jPanel3.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 79, 65));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 1280, 100));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        PanelDiagnostico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tableCitasPorMedico.setModel(new javax.swing.table.DefaultTableModel(
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
        YY.setViewportView(tableCitasPorMedico);

        PanelDiagnostico.add(YY, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 1010, 380));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Fecha Cita ");
        PanelDiagnostico.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 50, -1, -1));
        PanelDiagnostico.add(dateChooserF, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 150, 30));

        dateChooserFecha.setText("Buscar");
        dateChooserFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateChooserFechaActionPerformed(evt);
            }
        });
        PanelDiagnostico.add(dateChooserFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, -1, -1));

        btnCredenciales.setText("Cambiar Credenciales");
        btnCredenciales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCredencialesActionPerformed(evt);
            }
        });
        PanelDiagnostico.add(btnCredenciales, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        jTabbedPane1.addTab("Diagnostico", PanelDiagnostico);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 100, 1040, 530));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DiagnosticoAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_DiagnosticoAncestorMoved
        // TODO add your handling code here:

    }//GEN-LAST:event_DiagnosticoAncestorMoved

    private void DiagnosticoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosticoMouseReleased

    private void DiagnosticoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosticoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosticoKeyPressed

    private void DiagnosticoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseClicked
        // TODO add your handling code here:
                   jTabbedPane1.setSelectedIndex(0); 

    }//GEN-LAST:event_DiagnosticoMouseClicked

    private void DiagnosticoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseEntered
        // TODO add your handling code here:
                Diagnostico.setBackground(new Color(10, 92, 184)); 

    }//GEN-LAST:event_DiagnosticoMouseEntered

    private void DiagnosticoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DiagnosticoMouseExited
        // TODO add your handling code here:
                Diagnostico.setBackground(new Color(28,43,110)); 

    }//GEN-LAST:event_DiagnosticoMouseExited

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void dateChooserFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateChooserFechaActionPerformed
     Date fecha = dateChooserF.getDate();
    
    // Validación de fecha seleccionada
    if (fecha == null) {
        JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    Date fechaSeleccionada = dateChooserF.getDate();

    // Validación de fecha pasada
    Calendar calHoy = Calendar.getInstance();
    calHoy.set(Calendar.HOUR_OF_DAY, 0);
    calHoy.set(Calendar.MINUTE, 0);
    calHoy.set(Calendar.SECOND, 0);
    calHoy.set(Calendar.MILLISECOND, 0);
    Date hoySinHora = calHoy.getTime();

    if (fechaSeleccionada.before(hoySinHora)) {
        JOptionPane.showMessageDialog(this, "No puede seleccionar fechas pasadas.", "Error", JOptionPane.ERROR_MESSAGE);
        dateChooserF.setDate(null);
        return;
    }
    
    dateChooserF.setDateFormatString("dd/MM/yyyy"); 
    
    // Validación de día de semana (lunes a viernes)
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(fechaSeleccionada);
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
        JOptionPane.showMessageDialog(this, "Seleccione un día entre lunes y viernes.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // 🔥 Filtrar citas por la fecha seleccionada
    filtrarCitasPorFecha(fechaSeleccionada);
    }//GEN-LAST:event_dateChooserFechaActionPerformed

    private void btnCredencialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCredencialesActionPerformed
    DialogCambiarContraseña dialog = new DialogCambiarContraseña(this, true, medicoLogueado);
    dialog.setVisible(true);
    }//GEN-LAST:event_btnCredencialesActionPerformed

    /*
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
      /*  try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Doctor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

       Create and display the form */
       
            
 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Diagnostico;
    private javax.swing.JPanel PanelDiagnostico;
    private javax.swing.JScrollPane YY;
    private javax.swing.JButton btnCredenciales;
    private com.toedter.calendar.JDateChooser dateChooserF;
    private javax.swing.JButton dateChooserFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblIconRecepcion;
    private javax.swing.JTable tableCitasPorMedico;
    // End of variables declaration//GEN-END:variables
}
