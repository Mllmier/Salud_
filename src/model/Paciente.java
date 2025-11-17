package model;

import java.time.LocalDate;

public class Paciente extends Persona {
    private String tipoSangre;
    private String antecedentes;
    private double  peso;
    private String tipoDocumento;
    private double altura;
    
   

    public Paciente(
        String numeroDocumento, String nombres,  String apellidos, LocalDate fechaNacimiento, String sexo,String eps,String email, 
        String celular,
        String contraseña,
        String tipoDocumento,
        String tipoSangre,
        String antecedentes,
        double  peso,
        double altura) {
        super(numeroDocumento, nombres, apellidos, fechaNacimiento, sexo, eps, email, celular, contraseña);
        
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento no puede ser nulo o vacío");
        }
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento no puede ser nulo o vacío");
        }
        
        this.tipoSangre = tipoSangre;
        this.antecedentes = antecedentes;
        this.peso=peso;
        this.tipoDocumento=tipoDocumento;
        this.altura=altura;
    }

    // Getters y Setters
    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
     public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento no puede ser nulo o vacío");
        }
        this.tipoDocumento = tipoDocumento;
    }
 
    
   

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
}