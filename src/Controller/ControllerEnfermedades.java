package Controller;

import DAOImpl.EnfermedadesDAOImpl;
import dao.EnfermedadesDAO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Enfermedad;

public class ControllerEnfermedades {
  
    private DefaultTableModel tableModelEnfermedades;
    private final EnfermedadesDAO enfermedadDAO;
    private Integer idOriginal;
    
    private JTable tablaEnfermedades;
    private JTextField txtIdEnfermedad;
    private JTextField txtNombre;
    private JTextField txtTipo;
    private JTextArea txtSintomas;
    private JTextArea txtCausas;
    
    public ControllerEnfermedades() {
        this.enfermedadDAO = new EnfermedadesDAOImpl();
        this.idOriginal = null;
    }
    
    // Setters para los componentes de la vista
    public void setTablaEnfermedades(JTable tablaEnfermedades) {
        this.tablaEnfermedades = tablaEnfermedades;
        this.tableModelEnfermedades = (DefaultTableModel) tablaEnfermedades.getModel();
    }
    
    public void setTxtIdEnfermedad(JTextField txtIdEnfermedad) {
        this.txtIdEnfermedad = txtIdEnfermedad;
    }
    
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }
    
    public void setTxtTipo(JTextField txtTipo) {
        this.txtTipo = txtTipo;
    }
    
    public void setTxtSintomas(JTextArea txtSintomas) {
        this.txtSintomas = txtSintomas;
    }
    
    public void setTxtCausas(JTextArea txtCausas) {
        this.txtCausas = txtCausas;
    }
    
    // Inicializar tabla
    public void initTableEnfermedades() {
        tableModelEnfermedades = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Tipo", "Síntomas", "Causas"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            // Asegurar que la columna ID muestre valores enteros
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return String.class;
            }
        };
        if (tablaEnfermedades != null) {
            tablaEnfermedades.setModel(tableModelEnfermedades);
        }
    }
    
    // Cargar datos en la tabla
    public void cargarDatosEnTabla() {
        if (tableModelEnfermedades == null) {
            initTableEnfermedades();
        }
        
        tableModelEnfermedades.setRowCount(0);
        List<Enfermedad> enfermedades = enfermedadDAO.cargarTodasEnfermedades();
        
        for (Enfermedad enfermedad : enfermedades) {
            int id = enfermedad.getIdEnfermedad();
            Object[] row = {
                id, // Aseguramos que el ID se envía como entero
                enfermedad.getNombre(),
                enfermedad.getTipo(),
                convertirListaAString(enfermedad.getSintomas()),
                convertirListaAString(enfermedad.getCausas())
            };
            tableModelEnfermedades.addRow(row);
            
            // Verificación para depuración
            System.out.println("Cargando enfermedad ID: " + id + " - " + enfermedad.getNombre());
        }
    }
    
    // Convertir lista a string para mostrar en la tabla
    private String convertirListaAString(List<String> lista) {
        if (lista == null || lista.isEmpty()) {
            return "";
        }
        return String.join(", ", lista);
    }
    
    // Convertir texto a lista
    private List<String> convertirStringALista(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return Arrays.asList();
        }
        return Arrays.stream(texto.split(","))
                   .map(String::trim)
                   .filter(s -> !s.isEmpty())
                   .collect(Collectors.toList());
    }
    
    // Guardar nueva enfermedad
    public void guardarEnfermedadDesdeFormulario() {
        try {
            // Validar que los componentes no sean nulos
            if (txtIdEnfermedad == null || txtNombre == null || txtTipo == null) {
                JOptionPane.showMessageDialog(null, 
                    "Error: Componentes del formulario no inicializados", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Generar nuevo ID automáticamente si el campo está vacío
            int id;
            String idText = txtIdEnfermedad.getText().trim();
            if (idText.isEmpty()) {
                id = enfermedadDAO.generarNuevoId();
                txtIdEnfermedad.setText(String.valueOf(id));
            } else {
                try {
                    id = Integer.parseInt(idText);
                    if (id <= 0) {
                        JOptionPane.showMessageDialog(null,
                            "El ID debe ser un número entero positivo",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                        "El ID debe ser un número válido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            String nombre = txtNombre.getText().trim();
            String tipo = txtTipo.getText().trim();
            List<String> sintomas = txtSintomas != null ? convertirStringALista(txtSintomas.getText()) : Arrays.asList();
            List<String> causas = txtCausas != null ? convertirStringALista(txtCausas.getText()) : Arrays.asList();
            
            if (nombre.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(null, 
                    "Nombre y Tipo son campos obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (enfermedadDAO.existeIdEnfermedad(id)) {
                JOptionPane.showMessageDialog(null,
                    "Ya existe una enfermedad con este ID",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
                
            Enfermedad nuevaEnfermedad = new Enfermedad(id, nombre, tipo, sintomas, causas);
            
            if (enfermedadDAO.guardarEnfermedad(nuevaEnfermedad)) {
                JOptionPane.showMessageDialog(null, 
                    "Enfermedad guardada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al guardar la enfermedad", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "El ID debe ser un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al guardar Enfermedad: " + e.getMessage(),
                "ERROR", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }    
    }
    
    // Limpiar formulario
    public void limpiarFormulario() {
        if (txtIdEnfermedad != null) txtIdEnfermedad.setText("");
        if (txtNombre != null) txtNombre.setText("");
        if (txtTipo != null) txtTipo.setText("");
        if (txtSintomas != null) txtSintomas.setText("");
        if (txtCausas != null) txtCausas.setText("");
        this.idOriginal = null;
    }
    
    // Eliminar enfermedad seleccionada
    public void eliminarEnfermedadSeleccionada() {
        if (tablaEnfermedades == null) {
            JOptionPane.showMessageDialog(null, 
                "Tabla de enfermedades no inicializada", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int filaSeleccionada = tablaEnfermedades.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, 
                "Seleccione una enfermedad de la tabla.", 
                "Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Asegurar que obtenemos un entero y no un objeto
        Object idObj = tableModelEnfermedades.getValueAt(filaSeleccionada, 0);
        int idEnfermedad;
        
        if (idObj instanceof Integer) {
            idEnfermedad = (Integer) idObj;
        } else if (idObj instanceof String) {
            try {
                idEnfermedad = Integer.parseInt((String) idObj);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                    "Error: ID de enfermedad no válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null,
                "Error: Tipo de dato de ID no reconocido",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            null, 
            "¿Eliminar la enfermedad con ID " + idEnfermedad + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = enfermedadDAO.eliminarEnfermedad(idEnfermedad);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, 
                    "Enfermedad eliminada correctamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null, 
                    "No se pudo eliminar la enfermedad", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Actualizar enfermedad
    public void actualizarEnfermedad() {
        try {
            // Validar que hay un ID original (que se ha seleccionado una enfermedad)
            if (idOriginal == null) {
                JOptionPane.showMessageDialog(null, 
                    "No hay enfermedad seleccionada para actualizar", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar componentes
            if (txtIdEnfermedad == null || txtNombre == null || txtTipo == null) {
                JOptionPane.showMessageDialog(null, 
                    "Error: Componentes del formulario no inicializados", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idText = txtIdEnfermedad.getText().trim();
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "El campo ID no puede estar vacío",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int id;
            try {
                id = Integer.parseInt(idText);
                if (id <= 0) {
                    JOptionPane.showMessageDialog(null,
                        "El ID debe ser un número entero positivo",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                    "El ID debe ser un número válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String nombre = txtNombre.getText().trim();
            String tipo = txtTipo.getText().trim();
            List<String> sintomas = txtSintomas != null ? convertirStringALista(txtSintomas.getText()) : Arrays.asList();
            List<String> causas = txtCausas != null ? convertirStringALista(txtCausas.getText()) : Arrays.asList();

            if (nombre.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "Nombre y Tipo son campos obligatorios",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si el ID ha cambiado
            if (idOriginal != id) {
                if (enfermedadDAO.existeIdEnfermedad(id)) {
                    JOptionPane.showMessageDialog(null,
                        "Ya existe una enfermedad con este ID",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Enfermedad enfermedadActualizada = new Enfermedad(id, nombre, tipo, sintomas, causas);
            
            // Imprimir para depuración
            System.out.println("Actualizando enfermedad - ID Original: " + idOriginal);
            System.out.println("Nueva información: " + enfermedadActualizada);

            boolean actualizado = enfermedadDAO.actualizarEnfermedad(idOriginal, enfermedadActualizada);
            if (actualizado) {
                JOptionPane.showMessageDialog(null,
                    "Enfermedad actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                cargarDatosEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar la enfermedad. Verifique los datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "El ID debe ser un número válido",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al actualizar enfermedad: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Cargar datos en el formulario al seleccionar una fila
    public void cargarDatosEnFormulario() {
        if (tablaEnfermedades == null) {
            JOptionPane.showMessageDialog(null, 
                "Tabla de enfermedades no inicializada", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int filaSeleccionada = tablaEnfermedades.getSelectedRow();
        
        if (filaSeleccionada != -1) {
            try {
                // Obtener ID y manejar diferentes tipos posibles
                Object idObj = tableModelEnfermedades.getValueAt(filaSeleccionada, 0);
                int id;
                
                if (idObj instanceof Integer) {
                    id = (Integer) idObj;
                } else if (idObj instanceof String) {
                    id = Integer.parseInt((String) idObj);
                } else {
                    throw new IllegalArgumentException("Tipo de ID no válido: " + (idObj != null ? idObj.getClass().getName() : "null"));
                }
                
                // Si el ID es 0, buscar la enfermedad en la DAO por su posición o nombre
                if (id == 0) {
                    String nombre = (String) tableModelEnfermedades.getValueAt(filaSeleccionada, 1);
                    List<Enfermedad> enfermedades = enfermedadDAO.buscarEnfermedades(nombre);
                    if (!enfermedades.isEmpty()) {
                        for (Enfermedad e : enfermedades) {
                            if (e.getNombre().equals(nombre)) {
                                id = e.getIdEnfermedad();
                                break;
                            }
                        }
                    }
                }
                
                // Recuperar la enfermedad completa desde el DAO para asegurar datos correctos
                Enfermedad enfermedad = enfermedadDAO.buscarEnfermedadPorId(id);
                if (enfermedad != null) {
                    if (txtIdEnfermedad != null) txtIdEnfermedad.setText(String.valueOf(enfermedad.getIdEnfermedad()));
                    if (txtNombre != null) txtNombre.setText(enfermedad.getNombre());
                    if (txtTipo != null) txtTipo.setText(enfermedad.getTipo());
                    if (txtSintomas != null) txtSintomas.setText(convertirListaAString(enfermedad.getSintomas()));
                    if (txtCausas != null) txtCausas.setText(convertirListaAString(enfermedad.getCausas()));
                    
                    this.idOriginal = enfermedad.getIdEnfermedad();
                    
                    // Imprimir para depuración
                    System.out.println("Cargado en formulario - ID: " + this.idOriginal);
                } else {
                    // Si no se encuentra por ID, usar los datos de la tabla directamente
                    String nombre = (String) tableModelEnfermedades.getValueAt(filaSeleccionada, 1);
                    String tipo = (String) tableModelEnfermedades.getValueAt(filaSeleccionada, 2);
                    String sintomas = (String) tableModelEnfermedades.getValueAt(filaSeleccionada, 3);
                    String causas = (String) tableModelEnfermedades.getValueAt(filaSeleccionada, 4);
                    
                    if (txtIdEnfermedad != null) txtIdEnfermedad.setText(String.valueOf(id));
                    if (txtNombre != null) txtNombre.setText(nombre);
                    if (txtTipo != null) txtTipo.setText(tipo);
                    if (txtSintomas != null) txtSintomas.setText(sintomas);
                    if (txtCausas != null) txtCausas.setText(causas);
                    
                    this.idOriginal = id;
                    
                    // Advertencia para depuración
                    System.out.println("ADVERTENCIA: Usando datos de tabla directamente - ID: " + id);
                }
            } catch (Exception e) {
                System.err.println("Error al cargar datos de enfermedad: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al cargar los datos de la enfermedad seleccionada: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Generar nuevo ID
    public void generarNuevoId() {
        if (txtIdEnfermedad != null) {
            int nuevoId = enfermedadDAO.generarNuevoId();
            txtIdEnfermedad.setText(String.valueOf(nuevoId));
            System.out.println("Nuevo ID generado: " + nuevoId);
        }
        this.idOriginal = null; // Al generar nuevo ID, no hay enfermedad original
    }
    
    // Buscar enfermedades
    public void buscarEnfermedades(String criterio) {
        try {
            if (tableModelEnfermedades == null) {
                initTableEnfermedades();
            }
            
            List<Enfermedad> resultados = enfermedadDAO.buscarEnfermedades(criterio);
            
            tableModelEnfermedades.setRowCount(0);
            
            for (Enfermedad enfermedad : resultados) {
                Object[] row = {
                    enfermedad.getIdEnfermedad(),
                    enfermedad.getNombre(),
                    enfermedad.getTipo(),
                    convertirListaAString(enfermedad.getSintomas()),
                    convertirListaAString(enfermedad.getCausas())
                };
                tableModelEnfermedades.addRow(row);
            }
            
            if (resultados.isEmpty() && !criterio.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "No se encontraron enfermedades que coincidan con: " + criterio,
                    "Búsqueda",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al buscar enfermedades: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Filtrar por tipo
    public void filtrarPorTipo(String tipo) {
        try {
            if (tableModelEnfermedades == null) {
                initTableEnfermedades();
            }
            
            List<Enfermedad> resultados = enfermedadDAO.filtrarPorTipo(tipo);
            
            tableModelEnfermedades.setRowCount(0);
            
            for (Enfermedad enfermedad : resultados) {
                Object[] row = {
                    enfermedad.getIdEnfermedad(),
                    enfermedad.getNombre(),
                    enfermedad.getTipo(),
                    convertirListaAString(enfermedad.getSintomas()),
                    convertirListaAString(enfermedad.getCausas())
                };
                tableModelEnfermedades.addRow(row);
            }
            
            if (resultados.isEmpty() && !tipo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                    "No se encontraron enfermedades del tipo: " + tipo,
                    "Filtrado",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error al filtrar enfermedades: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}