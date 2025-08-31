package model;

import java.time.LocalDate;
import java.util.Date;

public class Medicamento {
    private String idMedicamento;
    private String nombre;
    private String descripcion;
    private String laboratorio;
    private String cantidad;
    private LocalDate lote;
    private LocalDate fechaVencimiento;
    private String disponible;
    private String precio;
    public enum estadoMedicamento{
      PENDIENTE,
      ENTREGADO
     }

    public Medicamento(String idMedicamento, String nombre, String descripcion, String laboratorio, String cantidad, LocalDate lote,LocalDate fechaVencimiento,String disponible, String precio) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.laboratorio = laboratorio;
        this.cantidad = cantidad;
        this.lote = lote;
        this.fechaVencimiento = fechaVencimiento;
        this.disponible = disponible;
        this.precio = precio;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getLote() {
        return lote;
    }

    public void setLote(LocalDate lote) {
        this.lote = lote;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    
}