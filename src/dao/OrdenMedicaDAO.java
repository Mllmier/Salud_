/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import model.OrdenMedica;

/**
 *
 * @author Maria liz
 */
public interface OrdenMedicaDAO {
     List<OrdenMedica> cargarTodas();
    void guardarOrdenMedica(OrdenMedica orden);
    void guardarTodas(List<OrdenMedica> ordenes);
        public OrdenMedica obtenerPorIdCita(String idCita);

}
