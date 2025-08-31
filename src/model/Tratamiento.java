/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usuario
 */
public class Tratamiento {
    private String idTratamiento;
    private Enfermedad enfermedad;

    

    public Tratamiento() {}

    public Tratamiento(String idTratamiento, Enfermedad enfermedad) {
        this.idTratamiento = idTratamiento;
        this.enfermedad = enfermedad;
    }
    
    public String getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(String idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }
}
