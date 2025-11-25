/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import DAOImpl.ExamenesDAOImpl;
import dao.ExamenesMedicosDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.ExamenesMedicos;
import model.Medicamento;
import model.Sede;

/**
 *
 * @author Maria liz
 */
public class ControllerExamenes  {
    private  static ControllerExamenes instance;
    private JTable tableExamenes;
    private JTextField txtIdExamenes;
    private JTextField txtValor;
    private JTextField txtNombre;
    private JTextField txtTipoExamen;
    private JComboBox cboSede;
    private DefaultTableModel tableModelExamenes;
     
     private final ExamenesMedicosDAO examenesDAO;

    private ControllerExamenes() {
        this.examenesDAO = new ExamenesDAOImpl(); 
    }

    public static ControllerExamenes getInstance() {
        if (instance == null) {
            instance = new ControllerExamenes();
        }
        return instance;
    }

    public JTable getTableExamenes() {
        return tableExamenes;
    }

    public void setTableExamenes(JTable tableExamenes) {
        this.tableExamenes = tableExamenes;
    }

    public JTextField getTxtValor() {
        return txtValor;
    }

    public void setTxtValor(JTextField txtValor) {
        this.txtValor = txtValor;
    }

    public JTextField getTxtTipoExamen() {
        return txtTipoExamen;
    }

    public void setTxtTipoExamen(JTextField txtTipoExamen) {
        this.txtTipoExamen = txtTipoExamen;
    }

    public JComboBox getCboSede() {
        return cboSede;
    }

    public void setCboSede(JComboBox cboSede) {
        this.cboSede = cboSede;
    }

    public DefaultTableModel getTableModelExamenes() {
        return tableModelExamenes;
    }

    public void setTableModelExamenes(DefaultTableModel tableModelExamenes) {
        this.tableModelExamenes = tableModelExamenes;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtIdExamenes() {
        return txtIdExamenes;
    }

    public void setTxtIdExamenes(JTextField txtIdExamenes) {
        this.txtIdExamenes = txtIdExamenes;
    }
    
        public void cargarDatosExamenes() throws IOException{
     tableModelExamenes.setRowCount(0);
     List<ExamenesMedicos>examen = examenesDAO.cargarTodos();
     
     for(ExamenesMedicos examenes : examen){
       Object[]row = {
          examenes.getIdExamenes(),
          examenes.getNombre(),
          examenes.getTipo(),
          examenes.getValor(),
          examenes.getSede(),
        
         
       };
       tableModelExamenes.addRow(row);
     }
    }
    
    
    
    public void guardarExamenesDesdeFormulario(){
     try{
       String idExamen = examenesDAO.generarCodigoUnico();
       String nombres = txtNombre.getText().trim();
       String tipoExamen = txtTipoExamen.getText().trim();
       Sede sedes = (Sede) cboSede.getSelectedItem();
       String precio = txtValor.getText().trim();
       
       if ( nombres.isEmpty() || tipoExamen.isEmpty() || precio.isEmpty() ||
           idExamen.isEmpty() ){
        JOptionPane.showMessageDialog(null,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
       }
       
       txtIdExamenes.setText(idExamen);
    
            ExamenesMedicos nuevoExamen = new ExamenesMedicos( 
            idExamen,
            nombres,
            tipoExamen, 
            precio,
            sedes
             );
            examenesDAO.guardarExamenesMedicos(nuevoExamen);
            JOptionPane.showMessageDialog(null,
                    "Medicamento guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
           
              limpiarFormulario();
           cargarDatosExamenes();

     }catch(Exception e){
       JOptionPane.showMessageDialog(null,
                    "Error al guardar medicamento: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
     }
    }
    public void setupTableModelExamenes(){
     tableModelExamenes = new DefaultTableModel(
      new Object[]{"idExamen","Nombre","Tipo Examen","Precio","Sede"},0){
                  @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if ( columnIndex == 9) return LocalDate.class;
                return String.class;
            }
      };
     tableExamenes.setModel(tableModelExamenes);
    }
    public void cargarDatosEnTablaExamenes() {
    int filaSeleccionada = tableExamenes.getSelectedRow();
    if (filaSeleccionada == -1) {
        return;
    }

    try {
        // Verificar y obtener cada valor de la tabla
        Object idExamenesObj = tableModelExamenes.getValueAt(filaSeleccionada, 0);
        Object nombreObj = tableModelExamenes.getValueAt(filaSeleccionada, 1);
        Object tipoExamenObj = tableModelExamenes.getValueAt(filaSeleccionada, 2);
        Object preciosObj = tableModelExamenes.getValueAt(filaSeleccionada, 3);
        Object sedeObj = tableModelExamenes.getValueAt(filaSeleccionada, 4);

        String idExamen = (idExamenesObj != null) ? idExamenesObj.toString() : "";
        String nombre = (nombreObj != null) ? nombreObj.toString() : "";
        String tipoExamenes = (tipoExamenObj != null) ? tipoExamenObj.toString() : "";
        String precio = (preciosObj != null) ? preciosObj.toString() : "";
        String sedes = (sedeObj != null) ? sedeObj.toString() : "";

        txtIdExamenes.setText(idExamen);
        txtIdExamenes.setEditable(false);
        txtNombre.setText(nombre);
        txtTipoExamen.setText(tipoExamenes);
        txtValor.setText(precio);

        
        for (int i = 0; i < cboSede.getItemCount(); i++) {
                Sede s = (Sede) cboSede.getItemAt(i);
                if (s.toString().equals(sedes)) {
                    cboSede.setSelectedItem(s);
                    break;
                }
            }
       


    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            null,
            "Error al cargar datos del medicamento: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
    public void cargarSedesEnCombo(List<Sede> sedesDisponibles) {
    cboSede.removeAllItems();

    for (Sede s : sedesDisponibles) {
        cboSede.addItem(s);
    }
}
public void actualizarExamenes() {
    try {
        String idExamen = txtIdExamenes.getText().trim();
        String nombres = txtNombre.getText().trim();
        String tipoExamen = txtTipoExamen.getText().trim();
        Sede sedes = (Sede) cboSede.getSelectedItem();
        String precio = txtValor.getText().trim();

        if (idExamen.isEmpty() || nombres.isEmpty() || tipoExamen.isEmpty() || precio.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ExamenesMedicos examenActualizado = new ExamenesMedicos(
                idExamen,
                nombres,
                tipoExamen,
                precio,
                sedes
        );

        boolean actualizado = examenesDAO.actualizarExamenesMedicos(idExamen, examenActualizado);

        if (actualizado) {
            JOptionPane.showMessageDialog(null,
                    "Examen actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
   
            cargarDatosExamenes();
              limpiarFormulario();
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
                "Error al actualizar examen: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
 public void eliminaraExamenSeleccionado(){
     int filaSeleccionada = tableExamenes.getSelectedRow();
      if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un examen de la tabla para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
      String idExamen = (String ) tableModelExamenes.getValueAt(filaSeleccionada,0);
      String nombre = (String) tableModelExamenes.getValueAt(filaSeleccionada, 1);
      
      int confirmacion = JOptionPane.showConfirmDialog(
                null, 
                "¿Está seguro que desea eliminar el examen:\n" +
        "Nombre: " + nombre + "\n" +
        "Código: " + idExamen + "?",
        "Confirmar eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
        );
      
      if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = examenesDAO.eliminarExamenesMedicos(idExamen );
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Examen eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaExamenes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el Examen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
 public void limpiarFormulario(){
      txtIdExamenes.setText("");
      txtNombre.setEditable(false);
      txtTipoExamen.setText("");
      txtValor.setText("");
   
      cboSede.setSelectedIndex(0);
     
    }
  public void mostrarTodosExamenes() {
    try {
        cargarDatosExamenes();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null,
            "Error al cargar examenes: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
  
  public void mostrarTablaExamenes(JTable tablaDestino) {
    try {
        this.tableExamenes = tablaDestino;

        this.tableModelExamenes = new DefaultTableModel(
                new Object[]{"ID Examen", "Nombre", "Tipo", "Precio", "Sede"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableExamenes.setModel(tableModelExamenes);

        cargarDatosExamenes();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
                "Error al mostrar tabla de exámenes: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}



    
}
