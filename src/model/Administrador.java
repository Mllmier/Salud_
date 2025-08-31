package model;

import java.time.LocalDate;
import java.util.Date;



public class Administrador extends Persona {
    private String codigoEmpleado;

    public Administrador(String numeroDocumento, String nombres, String apellidos, String codigoEmpleado,LocalDate fechaNacimiento,String sexo,String eps,String email,String celular,String contraseña) {
        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, eps, email, celular,contraseña);
        this.codigoEmpleado = codigoEmpleado;
    }
        
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }


}