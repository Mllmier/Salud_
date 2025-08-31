/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAOImpl.SalasDAOImpl;
import dao.SalasDAO;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
import model.Salas;

public class ControllerSalas {
  
    private DefaultTableModel tableModelSalas;
    private SalasDAO salasDAO = new SalasDAOImpl();
    private String codigoOriginal;
    
    private JTable tablaSalas;
    private JTextField txtNombreSala;
    private JTextField txtCodigoSala;
    private JComboBox<String> cbTipoSala;
    private JSpinner spCapacidad;
    
    public void setTablaSalas(JTable tablaSalas) {
        this.tablaSalas = tablaSalas;
        this.tableModelSalas = (DefaultTableModel) tablaSalas.getModel();
    }
    
    public void setTxtNombreSala(JTextField txtNombreSala) {
        this.txtNombreSala = txtNombreSala;
    }
    
    public void setTxtCodigoSala(JTextField txtCodigoSala) {
        this.txtCodigoSala = txtCodigoSala;
    }
    
    public void setCbTipoSala(JComboBox<String> cbTipoSala) {
        this.cbTipoSala = cbTipoSala;
    }
    
    public void setSpCapacidad(JSpinner spCapacidad) {
        this.spCapacidad = spCapacidad;
    }
    
    public void initTableSalas() {
        tableModelSalas = new DefaultTableModel(
            new Object[]{"Nombre Sala", "Código Sala", "Tipo Sala", "Capacidad"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaSalas.setModel(tableModelSalas);
    }
    
    public void cargarDatosEnTablaSalas() {
        tableModelSalas.setRowCount(0);
        List<Salas> salas = salasDAO.cargarTodasSalas();
        for (Salas sala : salas) {
            Object[] row = {
                sala.getNombreSala(),
                sala.getCodigoSala(),
                sala.getTipoSala(),
                sala.getCapacidadSala()
            };
            tableModelSalas.addRow(row);
        }
    }
    
    public void guardarSalaDesdeFormulario() {
        try {
            String nombreSala = txtNombreSala.getText().trim();
            String codigoSala = txtCodigoSala.getText().trim();
            String tipoSala = cbTipoSala.getSelectedItem().toString();
            int capacidad = (int) spCapacidad.getValue();
            
            if (nombreSala.isEmpty() || codigoSala.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre y código son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Solución: Verificar nulos en la comparación
            boolean existe = salasDAO.cargarTodasSalas().stream()
                .anyMatch(s -> s != null && codigoSala.equals(s.getCodigoSala()));
            
            if (existe) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe una sala con este código",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
                
            Salas nuevaSala = new Salas(
                nombreSala,
                codigoSala,
                tipoSala,
                String.valueOf(capacidad)
            );
            
            if (salasDAO.guardarSalaConValidacion(nuevaSala)) {
                JOptionPane.showMessageDialog(null, "Sala guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaSalas();
                limpiarSala();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar la sala", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar Sala: " + e.getMessage(),
                "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }    
    }
    
    public void limpiarSala() {
        txtNombreSala.setText("");
        txtCodigoSala.setText("");
        cbTipoSala.setSelectedIndex(0);
        spCapacidad.setValue(0);
    }
    
    public void eliminarSalaSeleccionada() {
        int filaSeleccionada = tablaSalas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Seleccione una sala de la tabla.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoSala = tableModelSalas.getValueAt(filaSeleccionada, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
            null, 
            "¿Eliminar la sala con código " + codigoSala + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = salasDAO.eliminarSala(codigoSala);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, 
                    "Sala eliminada correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaSalas();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "No se pudo eliminar la sala", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void actualizarSala() {
        try {
            int filaSeleccionada = tablaSalas.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null, 
                    "Seleccione una sala de la tabla para actualizar", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codigoOriginal = tableModelSalas.getValueAt(filaSeleccionada, 1).toString();

            String nombreSala = txtNombreSala.getText().trim();
            String codigoSala = txtCodigoSala.getText().trim();
            String tipoSala = cbTipoSala.getSelectedItem().toString();
            int capacidad = (int) spCapacidad.getValue();

            if (nombreSala.isEmpty() || codigoSala.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Nombre y código son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!codigoOriginal.equals(codigoSala)) {
                boolean existe = salasDAO.cargarTodasSalas().stream()
                    .anyMatch(s -> s != null && s.getCodigoSala().equals(codigoSala));
                if (existe) {
                    JOptionPane.showMessageDialog(null,
                        "Ya existe una sala con este código",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Salas salaActualizada = new Salas(
                nombreSala,
                codigoSala,
                tipoSala,
                String.valueOf(capacidad)
            );

            boolean actualizado = salasDAO.actualizarSalas(codigoOriginal, salaActualizada);
            if (actualizado) {
                JOptionPane.showMessageDialog(null,
                    "Sala actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaSalas();
                limpiarSala();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar la sala. Verifique los datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar sala: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void cargarDatosSalaEnFormulario() {
        int filaSeleccionada = tablaSalas.getSelectedRow();
        if (filaSeleccionada != -1) {
            txtNombreSala.setText(tableModelSalas.getValueAt(filaSeleccionada, 0).toString());
            txtCodigoSala.setText(tableModelSalas.getValueAt(filaSeleccionada, 1).toString());
            cbTipoSala.setSelectedItem(tableModelSalas.getValueAt(filaSeleccionada, 2).toString());
            spCapacidad.setValue(Integer.parseInt(tableModelSalas.getValueAt(filaSeleccionada, 3).toString()));
            
            codigoOriginal = txtCodigoSala.getText();
        }
    }
}