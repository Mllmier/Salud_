/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import model.Paciente;

/**
 *
 * @author Maria liz
 */
public interface PacienteDAO {
       boolean guardarPaciente(Paciente paciente);

    List<Paciente> cargarTodos();
    
    void guardarTodos(List<Paciente> pacientes);
    boolean eliminarPaciente(String numeroDocumento);
    Paciente buscarPorDocumento(String documento);
    boolean actualizarPaciente(String documentoOriginal, Paciente pacienteActualizado);
    Paciente buscarPorEmail(String email);
    boolean existeEmail(String email);
    boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña);

    

}

