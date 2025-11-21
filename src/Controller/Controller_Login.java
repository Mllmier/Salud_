package Controller;

import dao.usuarioDAO;
import javax.swing.*;
import model.Medico;
import model.Recepcionista;
import model.Paciente;

public class Controller_Login {

    private final usuarioDAO usuarioDAO = new usuarioDAO();
    

public void procesarLogin(String email, String passwordOIdentificacion, String rolSeleccionado, JFrame vistaActual) {
    try {

        if (email.isEmpty() || passwordOIdentificacion.isEmpty()) {
            mostrarError("Debe ingresar todos los campos.");
            return;
        }

        if (rolSeleccionado.equals("<Seleccione una opción>")) {
            mostrarError("Debe seleccionar un rol válido");
            return;
        }

        Object usuario = null;

      // --- LOGIN PARA PACIENTE (igual que doctor y recepcionista) ---
if (rolSeleccionado.equalsIgnoreCase("Paciente")) {

    usuario = usuarioDAO.validarCredenciales(email, passwordOIdentificacion, "Paciente");

    if (usuario == null) {
        mostrarError("Correo o contraseña incorrectos.");
        return;
    }

    Paciente paciente = (Paciente) usuario;

    farmasalud.view.Paciente framePaciente = new farmasalud.view.Paciente();
    framePaciente.inicializarConPaciente(paciente.getNumeroDocumento());
    abrirVista(framePaciente, vistaActual, "Bienvenido Paciente");
    return;
}


        // --- OTROS ROLES (Password normal) ---
        usuario = usuarioDAO.validarCredenciales(email, passwordOIdentificacion, rolSeleccionado);

        if (usuario == null) {
            mostrarError("Credenciales incorrectas");
            return;
        }

        // Doctor
        if (usuario instanceof Medico) {
            Medico medico = (Medico) usuario;
            if ("Desabilitado".equalsIgnoreCase(medico.getEstado())) {
                mostrarAdvertencia("Este doctor está deshabilitado.");
                return;
            }
            farmasalud.view.Doctor frameDoctor = new farmasalud.view.Doctor();
            frameDoctor.inicializarConDoctor(medico.getNumeroDocumento());
            abrirVista(frameDoctor, vistaActual, "Bienvenido Doctor");
            return;
        }

        // Recepcionista
        if (usuario instanceof Recepcionista) {
            Recepcionista recep = (Recepcionista) usuario;
            if ("Desabilitado".equalsIgnoreCase(recep.getEstado())) {
                mostrarAdvertencia("Este recepcionista está deshabilitado.");
                return;
            }
            abrirVista(new farmasalud.view.recepcionista(), vistaActual, "Bienvenido Recepcionista");
            return;
        }

        // Admin
        if (rolSeleccionado.equals("Administrador")) {
            abrirVista(new farmasalud.view.admin(), vistaActual, "Bienvenido Administrador");
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