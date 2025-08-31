package model;

import java.util.List;

public class Enfermedad {
    private int idEnfermedad;
    private String nombre;
    private String tipo;
    private List<String> sintomas;
    private List<String> causas;
    
    public Enfermedad() {
        // Constructor vacío necesario para GSON
    }
    
    public Enfermedad(int idEnfermedad, String nombre, String tipo, List<String> sintomas, List<String> causas) {
        this.idEnfermedad = idEnfermedad;
        this.nombre = nombre;
        this.tipo = tipo;
        this.sintomas = sintomas;
        this.causas = causas;
    }
    
    // Getters y setters
    public int getIdEnfermedad() {
        return idEnfermedad;
    }
    
    public void setIdEnfermedad(int idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public List<String> getSintomas() {
        return sintomas;
    }
    
    public void setSintomas(List<String> sintomas) {
        this.sintomas = sintomas;
    }
    
    public List<String> getCausas() {
        return causas;
    }
    
    public void setCausas(List<String> causas) {
        this.causas = causas;
    }
    
    @Override
    public String toString() {
        return "Enfermedad{" + 
               "idEnfermedad=" + idEnfermedad + 
               ", nombre='" + nombre + '\'' + 
               ", tipo='" + tipo + '\'' + 
               ", sintomas=" + sintomas + 
               ", causas=" + causas + 
               '}';
    }
}