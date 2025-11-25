package model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;

public class Recepcionista extends Persona {

    @SerializedName("codigoEmpleado")
    private String codigoEmpleado;

    @SerializedName("fechaContratacion")
    private LocalDate fechaContratacion;

    @SerializedName("horario")
    private String horario;

    @SerializedName("estado")
    private String estado; 

    public Recepcionista(String numeroDocumento, String nombres, String apellidos,
                         LocalDate fechaNacimiento, String sexo, String eps,
                         String email, String celular, String contraseña,
                         String codigoEmpleado, LocalDate fechaContratacion,
                         String horario, String estado) {

        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, null, email, celular, contraseña);
        this.codigoEmpleado = codigoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.horario = horario;
        this.estado = estado;
    }

    public Recepcionista(String numeroDocumento, String nombres, String apellidos,
                         LocalDate fechaNacimiento, String sexo, String eps,
                         String email, String celular, String contraseña,
                         String codigoEmpleado, LocalDate fechaContratacion,
                         String horario) {

        this(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, eps,
             email, celular, contraseña, codigoEmpleado, fechaContratacion, horario, "Activo");
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
