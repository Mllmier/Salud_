/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Listener;

import model.Cita;

/**
 *
 * @author Maria liz
 */
public interface CitaListener {

 void citaAgregada(Cita cita);
void citaActualizada(Cita cita);
void citaEliminada(String idCita);

}
