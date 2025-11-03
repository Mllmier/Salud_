package model;

import java.time.LocalDate;
import java.time.LocalTime;
import model.Medico;
import model.Paciente;
import model.Salas;
import model.Sede;

public class Cita {
 private  String IdCita;
    private LocalDate fechaCita;
    private String hora;
    private String tipoCita;
    private String motivo;
    private EstadoCita estado;
   private String documentoPaciente;
    private Paciente paciente;
    private Medico medico;
    private String documentoMedico;
    private Salas sala;
    private Sede sede;
    public enum EstadoCita {
        PROGRAMADA,
        COMPLETADA,
        CANCELADA,
        NOASISTIO;
        
        public static EstadoCita getPROGRAMADA() {
            return PROGRAMADA;
        }

        public static EstadoCita getCOMPLETADA() {
            return COMPLETADA;
        }

        public static EstadoCita getCANCELADA() {
            return CANCELADA;
        }
    }

    
    public Cita(String IdCita, LocalDate fechaCita, String hora, String motivo, String tipoCita, Salas sala, EstadoCita estado, Paciente paciente, Medico medico, Sede sede) {
        
        this.IdCita=IdCita;
        this.motivo=motivo;
        this.fechaCita = fechaCita;
        this.hora = hora;
        this.tipoCita=tipoCita;
        this.motivo=motivo;
        this.estado = estado;
        this.medico=medico;
        this.sala=sala;
        this.sede=sede;
        this.paciente=paciente;
        
           }
    

    public String getIdCita() {
        return IdCita;
    }

    public void setIdCita(String idCita) {
        this.IdCita = idCita;
    }
    

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

  

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }


    public String getDocumentoPaciente() {
        return documentoPaciente;
    }

    public void setDocumentoPaciente(String documentoPaciente) {
        this.documentoPaciente = documentoPaciente;
    }
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getDocumentoMedico() {
        return documentoMedico;
    }

    public void setDocumentoMedico(String documentoMedico) {
        this.documentoMedico = documentoMedico;
    }

    public Salas getSala() {
        return sala;
    }

    public void setSala(Salas sala) {
        this.sala = sala;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
}