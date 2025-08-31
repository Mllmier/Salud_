package model;

import java.util.Date;
import java.util.List;

public class InformeDiario {
    private int idInforme;
    private Date fecha;
    private String resumen;
    private List<Medicamento> medicamentos;
    private Recepcionista recepcionista;

    public InformeDiario(int idInforme, Date fecha, String resumen, List<Medicamento> medicamentos, Recepcionista recepcionista) {
        this.idInforme = idInforme;
        this.fecha = fecha;
        this.resumen = resumen;
        this.medicamentos = medicamentos;
        this.recepcionista = recepcionista;
    }

    public int getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(int idInforme) {
        this.idInforme = idInforme;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Recepcionista getRecepcionista() {
        return recepcionista;
    }

    public void setRecepcionista(Recepcionista recepcionista) {
        this.recepcionista = recepcionista;
    }

    
}