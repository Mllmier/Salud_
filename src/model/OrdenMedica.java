/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import farmasalud.view.Enfermedades;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Maria liz
 */
public class OrdenMedica  {
  
    
   private String idCita;
   private   String nombre ;
   private String apellido ;
   private  String email ;
   private  String altura ;
   private  String peso;
   private  String fechaNacimiento ;
   private  String tipoSangre ;
   private  String antecedentes ;
   private  String celular ;
    private String sexo ;
   private String eps ;
    private String diagnostico ;
    private String receta;
    private String examen;
    private List<String> areamedicamentos;
    private String fecha;
    private String hora;
    private String nombreMedico;
private String apellidoMedico;
private String especialidadMedico;
private String motivo;
private String sede;
private String estado;

   
     public OrdenMedica(String nombre, String apellido ,String email ,String altura , String peso,
             String fechaNacimiento , String tipoSangre , String antecedentes , String celular 
             ,String sexo ,String eps ,String diagnostico,String receta,String examen, List<String> areamedicamentos
             ,String fecha,String hora,String nombreMedico,String apellidoMedico,
             String especialidadMedico,String idCita,String motivo,String sede,String estado){
     this.nombre = nombre != null ? nombre : "";
     this.apellido= apellido != null ? apellido : "";
     this.email = email != null ? email : "";
     this.altura = altura != null ? altura : "";
     this.peso = peso != null ? peso : "";
     this.fechaNacimiento = fechaNacimiento != null ? fechaNacimiento : "";
     this.tipoSangre = tipoSangre != null ? tipoSangre : "";
     this.antecedentes = antecedentes != null ? antecedentes : "";
     this.celular = celular != null ? celular : "";
     this.sexo=sexo != null ? sexo : "";
     this.eps=eps != null ? eps : "";
     this.diagnostico= diagnostico != null ? diagnostico : "";
     this.receta= receta != null ? receta : "";
     this.examen= examen != null ? examen : "";
    this.areamedicamentos = (areamedicamentos != null) ? areamedicamentos : new ArrayList<>();  
    this.fecha= fecha != null ? fecha : "";
     this.hora= hora != null ? hora : "";
     this.nombreMedico = nombreMedico != null ? nombreMedico : "";
     this.apellidoMedico= apellidoMedico != null ? apellidoMedico : "";
     this.especialidadMedico= especialidadMedico != null ? especialidadMedico : "";
     this.idCita= idCita != null ? idCita : "";
     this.sede= sede != null ? sede : "";
     this.estado= estado != null ? estado : "";
    this.motivo= motivo != null ? motivo : "";


     }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getAltura() {
        return altura;
    }

    public String getPeso() {
        return peso;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public String getCelular() {
        return celular;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEps() {
        return eps;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

   public List<String> getAreamedicamentos() {
    return areamedicamentos;
}

public void setAreamedicamentos(List<String> areamedicamentos) {
    this.areamedicamentos = areamedicamentos;
}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getApellidoMedico() {
        return apellidoMedico;
    }

    public void setApellidoMedico(String apellidoMedico) {
        this.apellidoMedico = apellidoMedico;
    }

    public String getEspecialidadMedico() {
        return especialidadMedico;
    }

    public void setEspecialidadMedico(String especialidadMedico) {
        this.especialidadMedico = especialidadMedico;
    }
    // otros atributos...

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }





  
}
  
    
  

