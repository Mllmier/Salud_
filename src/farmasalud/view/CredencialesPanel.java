package farmasalud.view;

import DAOImpl.AdminDAOImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.AdminDAO;
import com.google.gson.JsonObject;
import java.io.IOException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class CredencialesPanel {
    private AdminDAO adminDAO;
    private JFrame parentFrame;
    private String emailAdmin;

    public CredencialesPanel(JFrame parentFrame, String emailAdmin) {
        this.adminDAO = new AdminDAOImpl();
        this.parentFrame = parentFrame;
        this.emailAdmin = emailAdmin;
    }

    public void mostrarPanel() {
        // Primero mostrar diálogo de verificación de contraseña
        mostrarDialogoVerificacion();
    }

    private void mostrarDialogoVerificacion() {
        // Configuración de colores y fuentes
        Color primaryColor = new Color(0, 102, 204);
        Color backgroundColor = new Color(248, 249, 250);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);

        // Crear panel de verificación
        JPanel panelVerificacion = new JPanel(new BorderLayout(10, 10));
        panelVerificacion.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelVerificacion.setBackground(backgroundColor);

        // Título
        JLabel titleLabel = new JLabel("Verificación de Seguridad");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(primaryColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        panelVerificacion.add(titleLabel, BorderLayout.NORTH);

        // Panel de contenido
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mensaje
        JLabel lblMensaje = new JLabel("<html><div style='width: 250px; text-align: justify;'>"
                + "Para acceder a tus credenciales, por favor ingresa tu contraseña actual:"
                + "</div></html>");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(lblMensaje, gbc);

        // Campo de contraseña
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setFont(labelFont);
        JPasswordField txtPass = new JPasswordField(20);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(lblPass, gbc);
        
        gbc.gridx = 1;
        contentPanel.add(txtPass, gbc);

        panelVerificacion.add(contentPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(backgroundColor);
        
        JButton btnCancelar = new JButton("Cancelar");
        styleButton(btnCancelar, new Color(108, 117, 125), buttonFont);
        JButton btnVerificar = new JButton("Verificar");
        styleButton(btnVerificar, primaryColor, buttonFont);

        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnVerificar);

        panelVerificacion.add(buttonPanel, BorderLayout.SOUTH);

        // Configurar diálogo de verificación
        JDialog dialogVerificacion = new JDialog(parentFrame, "Verificación Requerida", true);
        dialogVerificacion.setContentPane(panelVerificacion);
        dialogVerificacion.pack();
        dialogVerificacion.setResizable(false);
        dialogVerificacion.setLocationRelativeTo(parentFrame);

        // Acción del botón Verificar
        btnVerificar.addActionListener(e -> {
            String password = new String(txtPass.getPassword());
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(dialogVerificacion, 
                    "Por favor ingresa tu contraseña", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Verificar credenciales
                boolean credencialesValidas = adminDAO.verificarCredenciales(emailAdmin, password);
                
                if (credencialesValidas) {
                    dialogVerificacion.dispose();
                    mostrarCredenciales(); // Mostrar el panel de credenciales
                } else {
                    JOptionPane.showMessageDialog(dialogVerificacion, 
                        "Contraseña incorrecta", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    txtPass.setText("");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dialogVerificacion, 
                    "Error al verificar credenciales: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción del botón Cancelar
        btnCancelar.addActionListener(e -> dialogVerificacion.dispose());

        // Hacer visible el diálogo
        dialogVerificacion.setVisible(true);
    }

    private void mostrarCredenciales() {
        try {
            // Obtener datos del administrador
            JsonObject admin = adminDAO.obtenerAdministradorPorEmail(emailAdmin);
            
            if (admin == null) {
                JOptionPane.showMessageDialog(parentFrame, 
                    "Administrador no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Configuración de colores y fuentes
            Color primaryColor = new Color(0, 102, 204); // Azul profesional
            Color backgroundColor = new Color(248, 249, 250); // Gris claro
            Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
            Font textFont = new Font("Segoe UI", Font.PLAIN, 13);
            Font buttonFont = new Font("Segoe UI", Font.BOLD, 12);

            // Crear panel principal
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(new EmptyBorder(20, 20, 20, 20));
            panel.setBackground(backgroundColor);

            // Panel de título
            JLabel titleLabel = new JLabel("Mis Credenciales");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            titleLabel.setForeground(primaryColor);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
            panel.add(titleLabel, BorderLayout.NORTH);

            // Panel de información
            JPanel infoPanel = new JPanel(new GridBagLayout());
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))), 
                new EmptyBorder(15, 15, 15, 15)
            ));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Componente para el email
            JLabel lblEmail = new JLabel("Correo electrónico:");
            lblEmail.setFont(labelFont);
            JTextField txtEmail = new JTextField(admin.get("email").getAsString(), 25);
            txtEmail.setFont(textFont);
            txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            infoPanel.add(lblEmail, gbc);
            
            gbc.gridx = 1;
            gbc.gridwidth = 2;
            infoPanel.add(txtEmail, gbc);

            // Componente para la contraseña
            JLabel lblPass = new JLabel("Contraseña:");
            lblPass.setFont(labelFont);
            JPasswordField txtPass = new JPasswordField(admin.get("contraseña").getAsString(), 25);
            txtPass.setFont(textFont);
            txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            
            JCheckBox chkMostrar = new JCheckBox("Mostrar contraseña");
            chkMostrar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            chkMostrar.setBackground(Color.WHITE);
            
            chkMostrar.addActionListener(e -> {
                if (chkMostrar.isSelected()) {
                    txtPass.setEchoChar((char) 0);
                } else {
                    txtPass.setEchoChar('•');
                }
            });

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            infoPanel.add(lblPass, gbc);
            
            gbc.gridx = 1;
            infoPanel.add(txtPass, gbc);
            
            gbc.gridx = 2;
            infoPanel.add(chkMostrar, gbc);

            // Panel de botones
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            buttonPanel.setBackground(backgroundColor);
            
            JButton btnCancelar = new JButton("Cancelar");
            styleButton(btnCancelar, new Color(108, 117, 125), buttonFont);
            JButton btnGuardar = new JButton("Guardar Cambios");
            styleButton(btnGuardar, primaryColor, buttonFont);

            btnGuardar.addActionListener(e -> guardarCambios(
                admin.get("email").getAsString(),
                txtEmail.getText(),
                new String(txtPass.getPassword())
            ));

            btnCancelar.addActionListener(e -> {
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                window.dispose();
            });

            buttonPanel.add(btnCancelar);
            buttonPanel.add(btnGuardar);

            // Ensamblar componentes
            panel.add(infoPanel, BorderLayout.CENTER);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            // Configurar diálogo
            JDialog dialog = new JDialog(parentFrame, "Gestión de Credenciales", true);
            dialog.setContentPane(panel);
            dialog.pack();
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parentFrame, 
                "Error al cargar credenciales: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button, Color color, Font font) {
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker()),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
    }

    private void guardarCambios(String emailActual, String nuevoEmail, String nuevaContraseña) {
        try {
            // Validaciones
            if (nuevoEmail.isEmpty() || nuevaContraseña.isEmpty()) {
                showErrorDialog("Todos los campos son obligatorios");
                return;
            }

            if (!nuevoEmail.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                showErrorDialog("Ingrese un correo electrónico válido");
                return;
            }

            if (!emailActual.equalsIgnoreCase(nuevoEmail) && adminDAO.existeEmail(nuevoEmail)) {
                showErrorDialog("El correo electrónico ya está en uso");
                return;
            }

            if (nuevaContraseña.length() < 8) {
                showErrorDialog("La contraseña debe tener al menos 8 caracteres");
                return;
            }

            // Actualizar credenciales
            boolean success = adminDAO.actualizarCredenciales(emailActual, nuevoEmail, nuevaContraseña);

            if (success) {
                JOptionPane.showMessageDialog(parentFrame,
                    "<html><div style='text-align: center;'>Credenciales actualizadas correctamente</div></html>",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
                
                Window window = SwingUtilities.getWindowAncestor(parentFrame);
                if (window != null) {
                    window.dispose();
                }
            } else {
                throw new IOException("No se pudo actualizar las credenciales");
            }
        } catch (IOException ex) {
            showErrorDialog("Error al guardar cambios: " + ex.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(parentFrame,
            "<html><div style='width: 200px;'>" + message + "</div></html>",
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}