/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dao.CitaDAO;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Cita;

/**
 *
 * @author Maria liz
 */
public class CitaDAOImpl implements CitaDAO{
     private static final String JSON_BASE_PATH = "src/resources/data/";

    private static final String ARCHIVO_JSON = JSON_BASE_PATH+"citas.json";
    private final Gson gson;
    private List<Cita> citas = new ArrayList<>();

    public CitaDAOImpl() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    }

    @Override
    public List<Cita> cargarTodos() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            if (new File(ARCHIVO_JSON).length() == 0) {
                return new ArrayList<>();
            }
            
            try {
                return gson.fromJson(reader, new TypeToken<List<Cita>>() {}.getType());
            } catch (JsonSyntaxException e) {
                reader.close();
                try (Reader newReader = new FileReader(ARCHIVO_JSON)) {
                    Cita cita = gson.fromJson(newReader, Cita.class);
                    return cita != null ? Collections.singletonList(cita) : new ArrayList<>();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo: " + e.getMessage());
            return new ArrayList<>(); 
        }
    }

    @Override
    public List<Cita> obtenerCitaPorCodigo(String IdCitas) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita r : citas) {
            if (r.getIdCita().equals(IdCitas)) {
                resultado.add(r);
            }
        }
        return resultado;
    }

    @Override
    public void guardarCita(Cita cita) {
        List<Cita> citas = cargarTodos();
        citas.add(cita);
        guardarTodos(citas);
    }

    @Override
    public void guardarTodos(List<Cita> citas) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(citas, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar Cita : " + e.getMessage());
        }
    }

    @Override
    public List<Cita> obtenerCitasPorPaciente(String documentoPaciente) {
        List<Cita> todasLasCitas = cargarTodos();
        List<Cita> citasPaciente = new ArrayList<>();
        
        if (documentoPaciente == null || documentoPaciente.trim().isEmpty()) {
            return citasPaciente;
        }
        
        for (Cita cita : todasLasCitas) {
            if (cita.getPaciente().getNumeroDocumento() != null && 
                cita.getPaciente().getNumeroDocumento().equals(documentoPaciente)) {
                citasPaciente.add(cita);
            }
        }
        return citasPaciente;
    }

    @Override
    public List<Cita> obtenerCitasMedico(String documentoMedico) {
        List<Cita> todasLasCitas = cargarTodos();
        List<Cita> citasMedico = new ArrayList<>();
        
        if (documentoMedico == null || documentoMedico.trim().isEmpty()) {
            return citasMedico;
        }
        
        for (Cita cita : todasLasCitas) {
            if (cita.getMedico().getNumeroDocumento() != null && 
                cita.getMedico().getNumeroDocumento().equals(documentoMedico)) {
                citasMedico.add(cita);
            }
        }
        return citasMedico;
    }

    @Override
    public Cita obtenerCitaPorId(String idCita) {
        List<Cita> citas = cargarTodos(); 
        for (Cita cita : citas) {
            if (cita.getIdCita().equals(idCita)) {
                return cita;
            }
        }
        return null;
    }

    @Override
    public Cita buscarPorId(String idCita) {
        return obtenerCitaPorId(idCita);
    }

    @Override
    public boolean eliminarCita(String IdCita) {
        try {
            if (IdCita == null || IdCita.trim().isEmpty()) {
                throw new IllegalArgumentException("id Cita no puede ser nulo o vacío");
            }
            List<Cita> citas = cargarTodos();

            boolean removed = citas.removeIf(m -> 
                IdCita.equals(m.getIdCita())
            );
            
            if (removed) {
                guardarTodos(citas);
                System.out.println("Cita con id cita " + IdCita + " eliminado.");
            }
            
            return removed;
            
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return false;
        }     
    }

    @Override
    public boolean actualizarCita(String idCitaOriginal, Cita citaActualizada) {
        try {
            List<Cita> citas = cargarTodos();
            for (int i = 0; i < citas.size(); i++) {
                if (citas.get(i).getIdCita().equals(idCitaOriginal)) {
                    citas.set(i, citaActualizada);
                    guardarTodos(citas);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int contarCitasPorMedicoYFecha(String documentoMedico, LocalDate fecha) {
        int contador = 0;
        List<Cita> todasLasCitas = cargarTodos();

        for (Cita cita : todasLasCitas) {
            if (cita.getMedico().getNumeroDocumento() != null && cita.getFechaCita() != null) {
                if (cita.getMedico().getNumeroDocumento().equals(documentoMedico) && cita.getFechaCita().equals(fecha)) {
                    contador++;
                }
            }
        }

        return contador;
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if(value != null) {
                out.value(value.format(formatter));
            } else {
                out.nullValue();
            }
        }
        
        @Override 
        public LocalDate read(JsonReader in) throws IOException {
            String date = in.nextString();
            if (date == null || date.trim().isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Fecha inválida encontrada en JSON: " + date);
                return null;
            }
        }

    } 

     

}
