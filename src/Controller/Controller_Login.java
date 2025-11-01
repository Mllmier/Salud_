package Controller;

import dao.usuarioDAO;
import javax.swing.*;
import model.Medico;
import model.Recepcionista;
import model.Paciente;

public class Controller_Login {

    private final usuarioDAO usuarioDAO = new usuarioDAO();

    public void procesarLogin(String email, String contraseña, String rolSeleccionado, JFrame vistaActual) {
        try {
            if (email.isEmpty() || contraseña.isEmpty()) {
                mostrarError("Email y contraseña son requeridos");
                return;
            }

            if (rolSeleccionado.equals("<Seleccione una opción>")) {
                mostrarError("Debe seleccionar un rol válido");
                return;
            }

            Object usuario = usuarioDAO.validarCredenciales(email, contraseña, rolSeleccionado);

            if (usuario == null) {
                mostrarError("Usuario o contraseña incorrectos");
                return;
            }

            // --- Validar estado para roles restringidos ---
            if (usuario instanceof Medico) {
                Medico medico = (Medico) usuario;
                if (medico.getEstado() != null && medico.getEstado().equalsIgnoreCase("Desabilitado")) {
                    mostrarAdvertencia("Este doctor está deshabilitado. No puede iniciar sesión.");
                    return;
                }
                abrirVista(new farmasalud.view.Doctor(), vistaActual, "Bienvenido Doctor");
                return;
            }

            if (usuario instanceof Recepcionista) {
                Recepcionista recep = (Recepcionista) usuario;
                if (recep.getEstado() != null && recep.getEstado().equalsIgnoreCase("Desabilitado")) {
                    mostrarAdvertencia("Este recepcionista está deshabilitado. No puede iniciar sesión.");
                    return;
                }
                abrirVista(new farmasalud.view.recepcionista(), vistaActual, "Bienvenido Recepcionista");
                return;
            }

            if (rolSeleccionado.equals("Administrador")) {
                abrirVista(new farmasalud.view.admin(), vistaActual, "Bienvenido Administrador");
                return;
            }

            if (usuario instanceof Paciente) {
                Paciente paciente = (Paciente) usuario;
                farmasalud.view.Paciente framePaciente = new farmasalud.view.Paciente();
                framePaciente.inicializarConPaciente(paciente.getNumeroDocumento());
                abrirVista(framePaciente, vistaActual, "Bienvenido Paciente");
            }

        } catch (Exception e) {
            mostrarError("Error durante el login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private void abrirVista(JFrame nuevaVista, JFrame vistaActual, String mensajeBienvenida) {
        nuevaVista.setVisible(true);
        JOptionPane.showMessageDialog(null, mensajeBienvenida, "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
        vistaActual.dispose();
    }
}
