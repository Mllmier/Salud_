/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Sede {
   private String idSede;
    private String nombreSede;
    private String direccion;
    private String horarioFuncionamiento;

    
    public Sede() {}

    public Sede(String idSede, String nombreSede, String direccion, String horarioFuncionamiento) {
        this.idSede = idSede;
        this.nombreSede = nombreSede;
        this.direccion = direccion;
        this.horarioFuncionamiento = horarioFuncionamiento;
    }  
    public String getIdSede() {
        return idSede;
    }

    public void setIdSede(String idSede) {
        this.idSede = idSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHorarioFuncionamiento() {
        return horarioFuncionamiento;
    }

    public void setHorarioFuncionamiento(String horarioFuncionamiento) {
        this.horarioFuncionamiento = horarioFuncionamiento;
    }
@Override
public String toString() {
    return nombreSede; 
}

}
