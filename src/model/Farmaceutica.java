/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Maria liz
 */

import java.time.LocalDate;

public class Farmaceutica extends Persona {
    private String codigoEmpleado;
    private LocalDate fechaContratacion;
    private String turno;

    public Farmaceutica(String numeroDocumento, String nombres, String apellidos, LocalDate fechaNacimiento, String sexo, String eps, String email, String celular,String contraseña,String codigoEmpleado, LocalDate fechaContratacion, String turno ) {
        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, eps, email, celular,contraseña);
        this.codigoEmpleado = codigoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.turno = turno;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    
    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
