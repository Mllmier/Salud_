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

    @SerializedName("estado")
    private String estado;// Nuevo campo: Activo o Deshabilitado
    
    @SerializedName("sede")
    private String sede;

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getSede() {
        return sede;
    }

    public Medico(String numeroDocumento, String nombres, String apellidos,
                  LocalDate fechaNacimiento, String sexo,
                  String email, String celular, String contraseña,
                  String especialidad, LocalDate fechaContratacion,
                  String horario, String estado,String sede) {
        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, null, email, celular, contraseña);
        this.especialidad = especialidad;
        this.fechaContratacion = fechaContratacion;
        this.horario = horario;
        this.estado = estado;
        this.sede=sede;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
