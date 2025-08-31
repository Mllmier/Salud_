package model;

import java.time.LocalDate;
import java.util.Objects;

public class Recepcionista extends Persona {
    private String codigoEmpleado;
    private LocalDate fechaContratacion;
    private String horario;

    public Recepcionista(
        String numeroDocumento, 
        String nombres, 
        String apellidos, 
        LocalDate fechaNacimiento, 
        String sexo, 
        String eps, 
        String email, 
        String celular,
        String contraseña,
        String codigoEmpleado,
        LocalDate fechaContratacion,
        String horario
    ) {
        super(
            validarNoNulo(numeroDocumento, "Número de documento"),
            validarNoNulo(nombres, "Nombres"),
            validarNoNulo(apellidos, "Apellidos"),
            validarNoNulo(fechaNacimiento, "Fecha de nacimiento"),
            validarNoNulo(sexo, "Sexo"),
            validarNoNulo(eps, "EPS"),
            validarNoNulo(email, "Email"),
            validarNoNulo(celular, "Celular"),
            validarNoNulo(contraseña, "Contraseña")
        );
        
        setCodigoEmpleado(codigoEmpleado);
        setFechaContratacion(fechaContratacion);
        setHorario(horario);
    }

    // Método auxiliar para validación
    private static <T> T validarNoNulo(T valor, String nombreCampo) {
        if (valor == null) {
            throw new IllegalArgumentException(nombreCampo + " no puede ser nulo");
        }
        if (valor instanceof String && ((String) valor).trim().isEmpty()) {
            throw new IllegalArgumentException(nombreCampo + " no puede estar vacío");
        }
        return valor;
    }

    // Getters y setters mejorados
    public String getCodigoEmpleado() {
        return this.codigoEmpleado;
    }

    public final void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = validarNoNulo(codigoEmpleado, "Código de empleado");
    }

    public LocalDate getFechaContratacion() {
        return this.fechaContratacion;
    }

    public final void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = validarNoNulo(fechaContratacion, "Fecha de contratación");
    }

    public String getHorario() {
        return this.horario;
    }

    public final void setHorario(String horario) {
        this.horario = validarNoNulo(horario, "Horario");
    }

    // Métodos equals y hashCode más seguros
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Recepcionista that = (Recepcionista) o;
        
        // Comparación segura contra nulos
        return Objects.equals(this.getNumeroDocumento(), that.getNumeroDocumento()) && 
               Objects.equals(this.codigoEmpleado, that.codigoEmpleado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            getNumeroDocumento(), 
            codigoEmpleado
        );
    }
    
    @Override
    public String toString() {
        return "Recepcionista{" +
               "documento='" + getNumeroDocumento() + '\'' +
               ", nombres='" + getNombres() + '\'' +
               ", apellidos='" + getApellidos() + '\'' +
               ", codigoEmpleado='" + codigoEmpleado + '\'' +
               '}';
    }
}