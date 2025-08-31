package farmasalud.view;

import DAOImpl.AdminDAOImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import dao.AdminDAO;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MisDatosPanel {
    private static final Color PRIMARY_COLOR = new Color(0, 102, 204);
    private static final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font VALUE_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private JsonObject adminOriginal;
    private JDialog dialog;
    private JTextField[] camposEdicion;
    private JButton btnGuardar;
    private JButton btnEditar;
    private boolean modoEdicion = false;

    public void mostrarPanel(JFrame parentFrame, String emailAdmin) {
        try {
            adminOriginal = new AdminDAOImpl().obtenerAdministradorPorEmail(emailAdmin);
            
            if (adminOriginal == null) {
                JOptionPane.showMessageDialog(parentFrame, "Administrador no encontrado");
                return;
            }

            // Configurar el diálogo con sombra y bordes redondeados
            dialog = new JDialog(parentFrame, "Mis Datos Personales", true) {
                @Override
                public void paint(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                    super.paint(g2);
                    g2.dispose();
                }
            };
            dialog.setUndecorated(true);
            dialog.setBackground(new Color(0, 0, 0, 0));
            dialog.setSize(500, 650);
            dialog.setLocationRelativeTo(parentFrame);

            // Panel principal con efecto de vidrio
            JPanel mainPanel = new JPanel(new BorderLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    // Fondo degradado
                    GradientPaint gp = new GradientPaint(0, 0, new Color(248, 248, 255), 0, getHeight(), new Color(220, 230, 250));
                    g2d.setPaint(gp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    
                    // Borde sutil
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setColor(new Color(200, 200, 255));
                    g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 20, 20);
                }
            };
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            mainPanel.setOpaque(false);

            // Encabezado con icono
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setOpaque(false);
            
            JLabel title = new JLabel("Mis Datos Personales", SwingConstants.CENTER);
            title.setFont(TITLE_FONT);
            title.setForeground(PRIMARY_COLOR);
            title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
            
            headerPanel.add(title, BorderLayout.CENTER);
            
            // Icono de usuario (puedes reemplazarlo con tu propia imagen)
            JLabel iconLabel = new JLabel(new ImageIcon("src/resources/images/user_icon.png")); // Ajusta la ruta
            iconLabel.setPreferredSize(new Dimension(80, 80));
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(iconLabel, BorderLayout.WEST);

            // Panel de información con efecto de tarjeta
            JPanel infoCard = new JPanel(new GridBagLayout()) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(Color.WHITE);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    g2d.setColor(new Color(230, 230, 230));
                    g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                }
            };
            infoCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            infoCard.setOpaque(false);
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Campos de información editables
            camposEdicion = new JTextField[8];
            
            agregarCampoEditable(infoCard, gbc, 0, "Nombres:", adminOriginal.get("nombres").getAsString());
            agregarCampoEditable(infoCard, gbc, 1, "Apellidos:", adminOriginal.get("apellidos").getAsString());
            agregarCampoEditable(infoCard, gbc, 2, "Documento:", adminOriginal.get("numeroDocumento").getAsString());
            agregarCampoEditable(infoCard, gbc, 3, "Código Empleado:", adminOriginal.get("codigoEmpleado").getAsString());
            
            LocalDate fechaNacimiento = LocalDate.parse(adminOriginal.get("fechaNacimiento").getAsString(), 
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            agregarCampoEditable(infoCard, gbc, 4, "Fecha Nacimiento:", 
                fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            
            agregarCampoEditable(infoCard, gbc, 5, "Sexo:", adminOriginal.get("sexo").getAsString());
            agregarCampoEditable(infoCard, gbc, 6, "EPS:", adminOriginal.get("eps").getAsString());
            agregarCampoEditable(infoCard, gbc, 7, "Celular:", adminOriginal.get("celular").getAsString());

            // Panel de botones
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
            buttonPanel.setOpaque(false);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

            // Botón de editar
            btnEditar = crearBotonEstilizado("Editar Datos", PRIMARY_COLOR);
            btnEditar.addActionListener(e -> toggleModoEdicion());
            
            // Botón de guardar
            btnGuardar = crearBotonEstilizado("Guardar Cambios", new Color(0, 150, 0));
            btnGuardar.setVisible(false);
            btnGuardar.addActionListener(e -> guardarCambios());
            
            // Botón de cancelar
            JButton btnCancelar = crearBotonEstilizado("Cancelar", new Color(150, 0, 0));
            btnCancelar.setVisible(false);
            btnCancelar.addActionListener(e -> toggleModoEdicion(false));
            
            // Botón de cerrar
            JButton btnCerrar = crearBotonEstilizado("Cerrar", new Color(100, 100, 100));
            btnCerrar.addActionListener(e -> dialog.dispose());

            buttonPanel.add(btnEditar);
            buttonPanel.add(btnGuardar);
            buttonPanel.add(btnCancelar);
            buttonPanel.add(btnCerrar);

            // Ensamblar componentes
            mainPanel.add(headerPanel, BorderLayout.NORTH);
            mainPanel.add(new JScrollPane(infoCard), BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            dialog.setContentPane(mainPanel);
            dialog.setVisible(true);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, 
                "Error al cargar datos: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCampoEditable(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        
        JLabel lbl = new JLabel(label);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(new Color(70, 70, 70));
        panel.add(lbl, gbc);
        
        gbc.gridx = 1;
        JTextField txt = new JTextField(value);
        txt.setFont(VALUE_FONT);
        txt.setForeground(new Color(50, 50, 50));
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        txt.setBackground(new Color(250, 250, 250));
        txt.setEditable(false);
        
        camposEdicion[row] = txt;
        panel.add(txt, gbc);
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else {
                    g2.setColor(color);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // Sin borde
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(LABEL_FONT);
        btn.setContentAreaFilled(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void toggleModoEdicion() {
        toggleModoEdicion(!modoEdicion);
    }

    private void toggleModoEdicion(boolean activarEdicion) {
        modoEdicion = activarEdicion;
        
        for (JTextField campo : camposEdicion) {
            campo.setEditable(modoEdicion);
            campo.setBackground(modoEdicion ? Color.WHITE : new Color(250, 250, 250));
        }
        
        btnEditar.setVisible(!modoEdicion);
        btnGuardar.setVisible(modoEdicion);
    }

    private void guardarCambios() {
        try {
            // Validar campos
            for (JTextField campo : camposEdicion) {
                if (campo.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, 
                        "Todos los campos son obligatorios",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Validar formato de fecha
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate.parse(camposEdicion[4].getText(), formatter);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(dialog, 
                    "Formato de fecha inválido (debe ser dd/MM/yyyy)",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Crear objeto con los datos modificados
            JsonObject adminModificado = new JsonObject();
            adminModificado.addProperty("nombres", camposEdicion[0].getText());
            adminModificado.addProperty("apellidos", camposEdicion[1].getText());
            adminModificado.addProperty("numeroDocumento", camposEdicion[2].getText());
            adminModificado.addProperty("codigoEmpleado", camposEdicion[3].getText());
            adminModificado.addProperty("fechaNacimiento", 
                LocalDate.parse(camposEdicion[4].getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            adminModificado.addProperty("sexo", camposEdicion[5].getText());
            adminModificado.addProperty("eps", camposEdicion[6].getText());
            adminModificado.addProperty("celular", camposEdicion[7].getText());
            adminModificado.addProperty("email", adminOriginal.get("email").getAsString());
            adminModificado.addProperty("contraseña", adminOriginal.get("contraseña").getAsString());

            // Actualizar en el JSON
            AdminDAO adminDAO = new AdminDAOImpl();
            boolean success = adminDAO.actualizarDatosAdministrador(
                adminOriginal.get("email").getAsString(),
                adminModificado
            );

            if (success) {
                JOptionPane.showMessageDialog(dialog,
                    "Datos actualizados correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                adminOriginal = adminModificado;
                toggleModoEdicion(false);
            } else {
                throw new IOException("No se pudieron guardar los cambios");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(dialog,
                "Error al guardar cambios: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}