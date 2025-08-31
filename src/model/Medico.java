package model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;

public class Medico extends Persona {
    @SerializedName("especialidad")
    private String especialidad;
    
    @SerializedName("fechaContratacion")
    private LocalDate fechaContratacion;
    
    @SerializedName("horario")
    private String horario;
    
    
    public Medico(String numeroDocumento, String nombres, String apellidos, 
                 LocalDate fechaNacimiento, String sexo, 
                 String email, String celular,String contraseña, String especialidad,
                 LocalDate fechaContratacion, String horario) {
        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, null, email, celular,contraseña);
        this.especialidad = especialidad;
        this.fechaContratacion = fechaContratacion;
        this.horario = horario;
    }

   
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}