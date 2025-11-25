/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dao.PacienteDAO;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Paciente;

/**
 *
 * @author Maria liz
 */
public class PacienteDAOImpl implements PacienteDAO{
   private static final String ARCHIVO_JSON = "src/resources/data/pacientes.json";
    private final Gson gson;

    public PacienteDAOImpl() {
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    }

    private void asegurarArchivoExiste() {
        File archivo = new File(ARCHIVO_JSON);
        if (!archivo.exists()) {
            try {
                archivo.getParentFile().mkdirs();
                archivo.createNewFile();
                guardarTodos(new ArrayList<>());
            } catch (IOException e) {
                System.err.println("Error al crear archivo JSON: " + e.getMessage());
            }
        }
    }

    @Override
    public List<Paciente> cargarTodos() {
        asegurarArchivoExiste();
        File archivo = new File(ARCHIVO_JSON);
        if (archivo.length() == 0) return new ArrayList<>();

        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Paciente>>() {}.getType();
            List<Paciente> pacientes = gson.fromJson(reader, tipoLista);
            return pacientes != null ? pacientes : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean guardarPaciente(Paciente paciente) {
        if (paciente == null) throw new IllegalArgumentException("El paciente no puede ser nulo");
        try {
            List<Paciente> pacientes = cargarTodos();
            pacientes.add(paciente);
            guardarTodos(pacientes);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar paciente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void guardarTodos(List<Paciente> pacientes) {
        if (pacientes == null) throw new IllegalArgumentException("Lista no puede ser nula");
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(pacientes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    @Override
    public boolean eliminarPaciente(String numeroDocumento) {
        List<Paciente> pacientes = cargarTodos();
        boolean removed = pacientes.removeIf(p -> numeroDocumento.equals(p.getNumeroDocumento()));
        if (removed) guardarTodos(pacientes);
        return removed;
    }

    @Override
    public Paciente buscarPorDocumento(String documento) {
        return cargarTodos().stream()
            .filter(p -> documento.equals(p.getNumeroDocumento()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public Paciente buscarPorEmail(String email) {
        return cargarTodos().stream()
            .filter(p -> email.equalsIgnoreCase(p.getEmail()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean actualizarPaciente(String documentoOriginal, Paciente actualizado) {
        List<Paciente> pacientes = cargarTodos();
        for (int i = 0; i < pacientes.size(); i++) {
            if (documentoOriginal.equals(pacientes.get(i).getNumeroDocumento())) {
                pacientes.set(i, actualizado);
                guardarTodos(pacientes);
                return true;
            }
        }
        return false;
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value != null ? value.format(formatter) : null);
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            String date = in.nextString();
            return (date == null || date.trim().isEmpty()) ? null : LocalDate.parse(date, formatter);
        }
    }   
    @Override
    public boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña) {
    List<Paciente> pacientes = cargarTodos();
    boolean encontrado = false;
    
    for (Paciente paciente : pacientes) {
        if (paciente.getEmail().equalsIgnoreCase(emailActual)) {
            if (nuevoEmail != null && !nuevoEmail.isEmpty()) {
                paciente.setEmail(nuevoEmail);
            }
            
            if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                paciente.setContraseña(nuevaContraseña);
            }
            
            encontrado = true;
            break;
        }
    }
    
    if (encontrado) {
        guardarTodos(pacientes);
        return true;
    }
    return false;
}
    @Override
public boolean existeEmail(String email) {
    return cargarTodos().stream()
        .anyMatch(p -> p.getEmail().equalsIgnoreCase(email));
}

}
