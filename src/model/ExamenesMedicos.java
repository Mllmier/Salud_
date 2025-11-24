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
    private Salas salas;
    

    public ExamenesMedicos(String idExamenes, String nombre, String tipo, String valor,Salas  salas  ) {
        this.idExamenes = idExamenes;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.salas=salas;
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

    public Salas getSalas() {
        return salas;
    }

    public void setSalas(Salas salas) {
        this.salas = salas;
    }
    
    
    
}
