package Controller;

import DAOImpl.SedeDAOImpl;
import dao.SedeDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Sede;

public class ControllerSede {

    private static ControllerSede instancia;

    public static synchronized ControllerSede getInstancia() {
        if (instancia == null) {
            instancia = new ControllerSede();
        }
        return instancia;
    }

    private DefaultTableModel tableModelSedes;
    private SedeDAO sedeDAO = SedeDAOImpl.getInstance();
    private String codigoOriginal;

    private JTable tablaSedes;
    private JTextField txtNombreSede;
    private JTextField txtCodigoSede;
    private JTextField txtDireccion;
    private JTextField txtHorarioAtencion;

    public void setTablaSedes(JTable tablaSedes) {
        this.tablaSedes = tablaSedes;
        this.tableModelSedes = (DefaultTableModel) tablaSedes.getModel();
    }

    public void setTxtNombreSede(JTextField txtNombreSede) {
        this.txtNombreSede = txtNombreSede;
    }

    public void setTxtCodigoSede(JTextField txtCodigoSede) {
        this.txtCodigoSede = txtCodigoSede;
    }

    public void setTxtDireccion(JTextField txtDireccion) {
        this.txtDireccion = txtDireccion;
    }

    public void setTxtHorarioAtencion(JTextField txtHorarioAtencion) {
        this.txtHorarioAtencion = txtHorarioAtencion;
    }

    public void initTableSedes() {
        tableModelSedes = new DefaultTableModel(
            new Object[]{"Código Sede", "Nombre Sede", "Dirección", "Horario Atención"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaSedes.setModel(tableModelSedes);
    }

    public void cargarDatosEnTablaSedes() {
        tableModelSedes.setRowCount(0);
        List<Sede> sedes = sedeDAO.cargarTodasSedes();
        for (Sede sede : sedes) {
            Object[] row = {
                sede.getIdSede(),
                sede.getNombreSede(),
                sede.getDireccion(),
                sede.getHorarioFuncionamiento()
            };
            tableModelSedes.addRow(row);
        }
    }

    public void guardarSedeDesdeFormulario() {
        try {
            String nombreSede = txtNombreSede.getText().trim();
            String codigoSede = txtCodigoSede.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String horarioAtencion = txtHorarioAtencion.getText().trim();

            if (nombreSede.isEmpty() || codigoSede.isEmpty() || direccion.isEmpty() || horarioAtencion.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean existe = sedeDAO.cargarTodasSedes().stream()
                .anyMatch(s -> codigoSede.equals(s.getIdSede()));

            if (existe) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe una sede con este código",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sede nuevaSede = new Sede(
                codigoSede,
                nombreSede,
                direccion,
                horarioAtencion
            );

            sedeDAO.guardarSedeConValidacion(nuevaSede);
            JOptionPane.showMessageDialog(null,
                "Sede guardada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
            cargarDatosEnTablaSedes();
            limpiarSede();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al guardar Sede: " + e.getMessage(),
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void actualizarSede() {
        try {
            int filaSeleccionada = tablaSedes.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(null,
                    "Seleccione una sede de la tabla para actualizar",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codigoOriginal = tableModelSedes.getValueAt(filaSeleccionada, 0).toString();

            String nombreSede = txtNombreSede.getText().trim();
            String codigoSede = txtCodigoSede.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String horarioAtencion = txtHorarioAtencion.getText().trim();

            if (nombreSede.isEmpty() || codigoSede.isEmpty() || direccion.isEmpty() || horarioAtencion.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Todos los campos son obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!codigoOriginal.equals(codigoSede)) {
                boolean existe = sedeDAO.cargarTodasSedes().stream()
                    .anyMatch(s -> s.getIdSede().equals(codigoSede));
                if (existe) {
                    JOptionPane.showMessageDialog(null,
                        "Ya existe una sede con este código",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Sede sedeActualizada = new Sede(
                codigoSede,
                nombreSede,
                direccion,
                horarioAtencion
            );

            boolean actualizado = sedeDAO.actualizarSede(codigoOriginal, sedeActualizada);
            if (actualizado) {
                JOptionPane.showMessageDialog(null,
                    "Sede actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaSedes();
                limpiarSede();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar la sede. Verifique los datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar sede: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void eliminarSedeSeleccionada() {
        int filaSeleccionada = tablaSedes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null,
                "Seleccione una sede de la tabla.",
                "Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigoSede = tableModelSedes.getValueAt(filaSeleccionada, 0).toString();

        int confirmacion = JOptionPane.showConfirmDialog(
            null,
            "¿Eliminar la sede con código " + codigoSede + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = sedeDAO.eliminarSede(codigoSede);
            if (eliminado) {
                JOptionPane.showMessageDialog(null,
                    "Sede eliminada correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTablaSedes();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo eliminar la sede",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void buscarSedes(String criterio) {
        try {
            List<Sede> resultados = sedeDAO.buscarSedes(criterio);
            tableModelSedes.setRowCount(0);
            for (Sede sede : resultados) {
                Object[] row = {
                    sede.getIdSede(),
                    sede.getNombreSede(),
                    sede.getDireccion(),
                    sede.getHorarioFuncionamiento()
                };
                tableModelSedes.addRow(row);
            }

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "No se encontraron sedes que coincidan con: " + criterio,
                    "Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al buscar sedes: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void limpiarSede() {
        txtNombreSede.setText("");
        txtCodigoSede.setText("");
        txtDireccion.setText("");
        txtHorarioAtencion.setText("");
    }

    public void generarNuevoCodigo() {
        txtCodigoSede.setText(sedeDAO.generarCodigoUnico());
    }

    public List<Sede> listarSedes() {
        try {
            return sedeDAO.cargarTodasSedes();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
