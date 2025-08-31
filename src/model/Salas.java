/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author usuario
 */
public class Salas {
    private String nombreSala;
    private String codigoSala;
    private String tipoSala;
    private String capacidadSala;

    public String getNombreSala() {
        return nombreSala;
    }

    public void setNombreSala(String nombreSala) {
        this.nombreSala = nombreSala;
    }

    public String getCodigoSala() {
        return codigoSala;
    }

    public void setCodigoSala(String codigoSala) {
        this.codigoSala = codigoSala;
    }

    public String getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(String tipoSala) {
        this.tipoSala = tipoSala;
    }

    public String getCapacidadSala() {
        return capacidadSala;
    }

    public void setCapacidadSala(String capacidadSala) {
        this.capacidadSala = capacidadSala;
    }

    public Salas(String nombreSala, String codigoSala, String tipoSala, String capacidadSala) {
        this.nombreSala = nombreSala;
        this.codigoSala = codigoSala;
        this.tipoSala = tipoSala;
        this.capacidadSala = capacidadSala;
    }
}
