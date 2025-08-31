
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAOImpl.MedicamentosDAOImpl;
import dao.MedicamentosDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Medicamento;


public class ControllerMedicamento {
     private final javax.swing.JFrame view; 
    
    public ControllerMedicamento(javax.swing.JFrame view) {
        this.view = view;
    }
    
    
    private DefaultTableModel tablaModelMedicamento;
    private MedicamentosDAO medicamentoDAO = new MedicamentosDAOImpl();
    private String codMedicamento;
    
    private JTable TabladeMedicamentos;
    
    
    private JTextField txtCodMedicamento;
    private JTextField txtMedicamento;
    private JTextField txtDescripcion;
    private JTextField txtLaboratorio;
    private JTextField txtCantidad;
    private JTextField txtLote;
    private JTextField txtFechaVencimiento;
    private JComboBox<String> cbDisponible;
    private JTextField txtPrecio;   


    public void setTabladeMedicamentos(JTable TabladeMedicamentos){
        
       this.TabladeMedicamentos = TabladeMedicamentos;
       this.tablaModelMedicamento = (DefaultTableModel) TabladeMedicamentos.getModel();
    }

    public void setTxtCodMedicamento(JTextField txtCodMedicamento) {
        this.txtCodMedicamento = txtCodMedicamento;
    }

    public void setTxtMedicamento(JTextField txtMedicamento) {
        this.txtMedicamento = txtMedicamento;
    }

    public void setTxtDescripcion(JTextField txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }

    public void setTxtLaboratorio(JTextField txtLaboratorio) {
        this.txtLaboratorio = txtLaboratorio;
    }

    public void setTxtCantidad(JTextField txtCantidad) {
        this.txtCantidad = txtCantidad;
    }

    public void setTxtLote(JTextField txtLote) {
        this.txtLote = txtLote;
    }

    public void setTxtFechaVencimiento(JTextField txtFechaVencimiento) {
        this.txtFechaVencimiento = txtFechaVencimiento;
    }

    public void setCbDisponible(JComboBox<String> cbDisponible) {
        this.cbDisponible = cbDisponible;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public void cargarDatosMedicamentos() throws IOException{
     tablaModelMedicamento.setRowCount(0);
     List<Medicamento>medicamentos = medicamentoDAO.cargarTodos();
     
     for(Medicamento medicamento : medicamentos){
       Object[]row = {
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
    }
    
    
    public void guardarMedicamentoDesdeFormulario(){
     try{
       String codmedicamento = medicamentoDAO.generarCodigoUnico();
       String nombres = txtMedicamento.getText().trim();
       String descripciones = txtDescripcion.getText().trim();
       String laboratorios = txtLaboratorio.getText().trim();
       String cantidades = txtCantidad.getText().trim();
       String fechStr = txtLote.getText().trim();
       String fechaStr = txtFechaVencimiento.getText().trim();
       String disponibles = cbDisponible.getSelectedItem().toString();
       String precios = txtPrecio.getText().trim();
       
       if ( nombres.isEmpty() || descripciones.isEmpty() || cantidades.isEmpty() ||
           fechStr.isEmpty() || fechaStr.isEmpty() || disponibles.isEmpty() || precios.isEmpty()){
        JOptionPane.showMessageDialog(view,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
       }
       
       txtCodMedicamento.setText(codmedicamento);
       
       LocalDate lote;
       
            try {
                lote = LocalDate.parse(fechStr);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(view,
                        "Formato de fecha inválido. Usa YYYY-MM-DD",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate fechaVencimiento;
            try {
                fechaVencimiento = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(view,
                        "Formato de fecha inválido. Usa YYYY-MM-DD",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            Medicamento nuevoMedicamento = new Medicamento( 
             codmedicamento,
             nombres,
             descripciones,
             laboratorios,
             cantidades,
                    lote,
                    fechaVencimiento,
             disponibles,
             precios
             );
            medicamentoDAO.guardarMedicamento(nuevoMedicamento);
            JOptionPane.showMessageDialog(view,
                    "Medicamento guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            limpiarFormulario();
            cargarDatosMedicamentos();

     }catch(Exception e){
       JOptionPane.showMessageDialog(view,
                    "Error al guardar medicamento: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
     }
    }
    
     public void limpiarFormulario(){
      txtCodMedicamento.setText("");
      txtCodMedicamento.setEditable(false);
      txtMedicamento.setText("");
      txtDescripcion.setText("");
      txtLaboratorio.setText("");
      txtCantidad.setText("");
      txtLote.setText("");
      txtFechaVencimiento.setText("");
      cbDisponible.setSelectedIndex(0);
      txtPrecio.setText("");
    }
     
     
      public void eliminaraMedicamentoSeleccionado(){
     int filaSeleccionada = TabladeMedicamentos.getSelectedRow();
      if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, "Seleccione un medicamento de la tabla para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
      String codMedicamento = (String ) tablaModelMedicamento.getValueAt(filaSeleccionada,0);
      String nombre = (String) tablaModelMedicamento.getValueAt(filaSeleccionada, 1);
      
      int confirmacion = JOptionPane.showConfirmDialog(
                view, 
                "¿Está seguro que desea eliminar el medicaemnto:\n" +
        "Nombre: " + nombre + "\n" +
        "Código: " + codMedicamento + "?",
        "Confirmar eliminación",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
        );
      
      if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = medicamentoDAO.eliminarMedicamento(codMedicamento);
            if (eliminado) {
                JOptionPane.showMessageDialog(view, "Medicamento eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaMedicamentos();
            } else {
                JOptionPane.showMessageDialog(view, "No se encontró el medicamento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
      
      
     public void actualizarMedicamento(){
      try{
        String codmedicamentos = this.codMedicamento;
        String nombres = txtMedicamento.getText().trim();
        String descripciones = txtDescripcion.getText().trim();
        String laboratorios = txtLaboratorio.getText().trim();
        String cantidades = txtCantidad.getText().trim();
        String lotes = txtLote.getText().trim();
        String fechasvencimientos = txtFechaVencimiento.getText().trim();
        String disponibles = cbDisponible.getSelectedItem().toString();
        String precios = txtPrecio.getText().trim();
        
        if (codmedicamentos.isEmpty() || nombres.isEmpty() || descripciones.isEmpty() || cantidades.isEmpty() ||
           lotes.isEmpty() || fechasvencimientos.isEmpty() || disponibles.isEmpty() || precios.isEmpty()){
        JOptionPane.showMessageDialog(view,
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
       }
          LocalDate lote;
       
            try {
                lote = LocalDate.parse(lotes);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(view,
                        "Formato de fecha inválido. Usa YYYY-MM-DD",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate fechaVencimiento;
            try {
                fechaVencimiento = LocalDate.parse(fechasvencimientos);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(view,
                        "Formato de fecha inválido. Usa YYYY-MM-DD",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        Medicamento medicamentoActualizado = new Medicamento(
         codmedicamentos,nombres,descripciones,laboratorios,
         cantidades,lote,fechaVencimiento,disponibles,precios
        );
        
        boolean actualizado = medicamentoDAO.actualizarMedicamento(codMedicamento,medicamentoActualizado);
        
          if (actualizado) {
              JOptionPane.showMessageDialog(view,
                    "Medicamento actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                cargarDatosEnTablaMedicamentos();
                limpiarFormulario();
          }
      }catch(Exception e){
       JOptionPane.showMessageDialog(view,
                "Error al actualizar medicamento: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
      }
    }
     
     
     
     
     public void cargarDatosEnTablaMedicamentos() {
    int filaSeleccionada = TabladeMedicamentos.getSelectedRow();
    if (filaSeleccionada == -1) {
        return;
    }

    try {
        // Verificar y obtener cada valor de la tabla
        Object codMedicamentoObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 0);
        Object nombreObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 1);
        Object descripcionObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 2);
        Object laboratorioObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 3);
        Object cantidadObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 4);
        Object loteObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 5);
        Object fechaVencimientoObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 6);
        Object disponibleObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 7);
        Object precioObj = tablaModelMedicamento.getValueAt(filaSeleccionada, 8);

        // Convertir a String, manejando valores nulos
        String codMedicamento = (codMedicamentoObj != null) ? codMedicamentoObj.toString() : "";
        String nombre = (nombreObj != null) ? nombreObj.toString() : "";
        String descripcion = (descripcionObj != null) ? descripcionObj.toString() : "";
        String laboratorio = (laboratorioObj != null) ? laboratorioObj.toString() : "";
        String cantidad = (cantidadObj != null) ? cantidadObj.toString() : "";
        String lote = (loteObj != null) ? loteObj.toString() : "";
        String fechaVencimiento = (fechaVencimientoObj != null) ? fechaVencimientoObj.toString() : "";
        String disponible = (disponibleObj != null) ? disponibleObj.toString() : "";
        String precio = (precioObj != null) ? precioObj.toString() : "";

        // Asignar los valores a los campos de texto
        txtCodMedicamento.setText(codMedicamento);
        txtCodMedicamento.setEditable(false);
        txtMedicamento.setText(nombre);
        txtDescripcion.setText(descripcion);
        txtLaboratorio.setText(laboratorio);
        txtCantidad.setText(cantidad);
        txtLote.setText(lote);
        txtFechaVencimiento.setText(fechaVencimiento);
        cbDisponible.setSelectedItem(disponible);
        txtPrecio.setText(precio);

        
         this.codMedicamento = codMedicamento;
    } catch (Exception e) {
        JOptionPane.showMessageDialog(view,
            "Error al cargar datos del medicamento: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}




    
            
    public void setupTableModelMedicamentos(){
     tablaModelMedicamento = new DefaultTableModel(
      new Object[]{"codMedicamento","Nombre","Descripcion","Laboratorio","Cantidad","Lote","Fecha Vencimiento","Disponible","Precio"},0){
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
     TabladeMedicamentos.setModel(tablaModelMedicamento);
    }
    public void buscarMedicamentos(String criterio) {
    try {
        List<Medicamento> resultados = medicamentoDAO.buscarMedicamentos(criterio);
        
        // Limpiar la tabla
        tablaModelMedicamento.setRowCount(0);
        
        // Llenar con los resultados
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
        JOptionPane.showMessageDialog(view,
            "Error al buscar medicamentos: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
    
    public void mostrarTodosMedicamentos() {
    try {
        cargarDatosMedicamentos();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(view,
            "Error al cargar medicamentos: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
    

}
