package DAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dao.MedicoDAO;
import model.Medico;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MedicoDAOImpl implements MedicoDAO {
    private static MedicoDAOImpl instancia;
    private static final String JSON_BASE_PATH = "src/resources/data/";
    private static final String ARCHIVO_JSON = JSON_BASE_PATH + "medico.json";
    private final Gson gson;

    public MedicoDAOImpl() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public static synchronized MedicoDAOImpl getInstancia() {
        if (instancia == null) {
            instancia = new MedicoDAOImpl();
        }
        return instancia;
    }

    @Override
    public List<Medico> cargarTodos() {
        asegurarArchivoExiste();
        File archivo = new File(ARCHIVO_JSON);

        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Medico>>(){}.getType();
            List<Medico> medicos = gson.fromJson(reader, tipoLista);
            return medicos != null ? medicos : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean guardarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("El médico no puede ser nulo");
        }

        try {
            List<Medico> medicos = cargarTodos();
            medicos.add(medico);
            guardarTodos(medicos);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar médico: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void guardarTodos(List<Medico> medicos) {
        if (medicos == null) {
            medicos = new ArrayList<>();
        }

        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(medicos, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar médicos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar los médicos", e);
        }
    }

    @Override
    public List<Medico> obtenerTodosMedicos() {
        return cargarTodos();
    }

    @Override
    public boolean eliminarMedico(String numeroDocumento) {
        try {
            if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
                throw new IllegalArgumentException("Número de documento no puede ser nulo o vacío");
            }

            List<Medico> medicos = cargarTodos();
            boolean removed = medicos.removeIf(m -> 
                m != null && numeroDocumento.equals(m.getNumeroDocumento())
            );
            
            if (removed) {
                guardarTodos(medicos);
                System.out.println("Médico con documento " + numeroDocumento + " eliminado.");
            }
            
            return removed;
            
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Medico buscarMedicoPorIdentificacion(String documento) {
        if (documento == null) {
            return null;
        }
        
        List<Medico> medicos = cargarTodos();
        for (Medico medico : medicos) {
            if (medico != null && documento.equals(medico.getNumeroDocumento())) {
                return medico; 
            }
        }
        return null;
    }

    @Override
    public Medico buscarPorDocumentoMedico(String documento) {
        if (documento == null) {
            return null;
        }
        
        List<Medico> medicos = cargarTodos();
        return medicos.stream()
            .filter(Objects::nonNull)
            .filter(p -> documento.equals(p.getNumeroDocumento()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean actualizarMedico(String cedulaOriginal, Medico medicoActualizado) {
        try {
            if (cedulaOriginal == null || medicoActualizado == null) {
                return false;
            }

            List<Medico> medicos = cargarTodos();
            
            for (int i = 0; i < medicos.size(); i++) {
                Medico m = medicos.get(i);
                if (m != null && cedulaOriginal.equals(m.getNumeroDocumento())) {
                    medicos.set(i, medicoActualizado);
                    guardarTodos(medicos);
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
    public Medico buscarPorNombreYApellido(String nombre, String apellido) {
        List<Medico> medicos = cargarTodos();
        for (Medico medico : medicos) {
            if (medico.getNombres().equalsIgnoreCase(nombre) && 
                medico.getApellidos().equalsIgnoreCase(apellido)) {
                return medico;
            }
        }
        return null;
    }

    @Override
    public boolean existeMedico(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento no puede ser nulo o vacío");
        }

        List<Medico> medicos = cargarTodos();
        return medicos.stream()
            .filter(Objects::nonNull)
            .anyMatch(m -> numeroDocumento.equals(m.getNumeroDocumento()));
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

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        
        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value != null) {
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
    @Override
    public boolean existeEmail(String email) {
    return cargarTodos().stream()
        .filter(Objects::nonNull)
        .anyMatch(m -> email.equalsIgnoreCase(m.getEmail()));
}
    @Override
public boolean actualizarCredenciales(String numeroDocumento, String nuevoEmail, String nuevaContrasena) {
    try {
        List<Medico> lista = cargarTodos(); // Carga el JSON completo

        for (Medico medico : lista) {
            if (medico.getNumeroDocumento().equals(numeroDocumento)) {

                // Actualizar directamente SIN VALIDAR si el correo existe
                medico.setEmail(nuevoEmail);
                medico.setContraseña(nuevaContrasena);

                guardarTodos(lista); // Guardar cambios en el JSON
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

@Override
public boolean actualizarEstado(String numeroDocumento, String nuevoEstado) {
    List<Medico> medicos = cargarTodos();
    for (Medico m : medicos) {
        if (m.getNumeroDocumento().equals(numeroDocumento)) {
            m.setEstado(nuevoEstado);
            guardarTodos(medicos);
            return true;
        }
    }
    return false;
}


}