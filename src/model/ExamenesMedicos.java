/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Maria liz
 */
public class ExamenesMedicos {
    private String idExamenes;
    private String nombre;
    private String tipo;
    private String valor;
    private Sede sede;
    

    public ExamenesMedicos(String idExamenes, String nombre, String tipo, String valor,Sede  sede  ) {
        this.idExamenes = idExamenes;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.sede=sede;
    }
    
    

    public String getIdExamenes() {
        return idExamenes;
    }

    public void setIdExamenes(String idExamenes) {
        this.idExamenes = idExamenes;
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
    
    
    
}
