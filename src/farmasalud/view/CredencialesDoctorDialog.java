package farmasalud.view;

import DAOImpl.MedicoDAOImpl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Medico;

public class CredencialesDoctorDialog extends JDialog {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheck;
    private JButton cancelButton;
    private JButton saveButton;
    
    private String currentEmail;
    private MedicoDAOImpl medicoDAO;

    public CredencialesDoctorDialog(Frame parent, String currentEmail) {
        super(parent, "Mis Credenciales - Médico", true);
        this.currentEmail = currentEmail;
        this.medicoDAO = MedicoDAOImpl.getInstancia();
        
        initComponents();
        loadUserData();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de título
        JLabel titleLabel = new JLabel("Gestión de Credenciales Médico");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        
        formPanel.add(new JLabel("Correo electrónico:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField);
        
        showPasswordCheck = new JCheckBox("Mostrar contraseña");
        showPasswordCheck.addActionListener(e -> {
            if (showPasswordCheck.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
        formPanel.add(showPasswordCheck);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> dispose());
        
        saveButton = new JButton("Guardar Cambios");
        saveButton.addActionListener(new SaveButtonListener());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadUserData() {
        Medico medico = medicoDAO.buscarMedicoPorIdentificacion(currentEmail);
        if (medico != null) {
            emailField.setText(medico.getEmail());
            passwordField.setText(medico.getContraseña());
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron los datos del médico", 
                "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nuevoEmail = emailField.getText().trim();
            String nuevaContraseña = new String(passwordField.getPassword()).trim();
            
            // Validaciones básicas
            if (nuevoEmail.isEmpty()) {
                JOptionPane.showMessageDialog(CredencialesDoctorDialog.this, 
                    "El correo electrónico no puede estar vacío", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el nuevo email ya está en uso (excepto si es el mismo)
            if (!nuevoEmail.equalsIgnoreCase(currentEmail) && medicoDAO.existeEmail(nuevoEmail)) {
                JOptionPane.showMessageDialog(CredencialesDoctorDialog.this, 
                    "El correo electrónico ya está en uso por otro médico", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Medico medicoActual = medicoDAO.buscarMedicoPorIdentificacion(currentEmail);
                if (medicoActual != null) {
                    medicoActual.setEmail(nuevoEmail);
                    medicoActual.setContraseña(nuevaContraseña);
                    
                    boolean success = medicoDAO.actualizarMedico(medicoActual.getNumeroDocumento(), medicoActual);
                    
                    if (success) {
                        JOptionPane.showMessageDialog(CredencialesDoctorDialog.this, 
                            "Credenciales actualizadas correctamente", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        currentEmail = nuevoEmail;
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(CredencialesDoctorDialog.this, 
                            "No se pudo actualizar las credenciales", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(CredencialesDoctorDialog.this, 
                    "Error al guardar los cambios: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}