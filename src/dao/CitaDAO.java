/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.time.LocalDate;
import java.util.List;
import model.Cita;
import model.OrdenMedica;

/**
 *
 * @author Maria liz
 */
public interface CitaDAO {
    List<Cita> cargarTodos();
    List<Cita> obtenerCitaPorCodigo(String IdCitas);
    void guardarCita(Cita cita);
    void guardarTodos(List<Cita> citas);
    List<Cita> obtenerCitasPorPaciente(String documentoPaciente);
    List<Cita> obtenerCitasMedico(String documentoMedico);
    Cita obtenerCitaPorId(String idCita);
    Cita buscarPorId(String idCita);
    boolean eliminarCita(String IdCita);
    boolean actualizarCita(String idCitaOriginal, Cita citaActualizada);
    int contarCitasPorMedicoYFecha(String documentoMedico, LocalDate fecha);
}
