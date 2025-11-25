package DAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dao.RecepcionistaDAO;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Recepcionista;

public class RecepcionistaDAOImpl implements RecepcionistaDAO {

    private static RecepcionistaDAOImpl instancia;
    private static final String JSON_BASE_PATH = "src/resources/data/";
    private static final String ARCHIVO_JSON = JSON_BASE_PATH + "recepcionista.json";
    private final Gson gson;

    private RecepcionistaDAOImpl() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    public static synchronized RecepcionistaDAOImpl getInstancia() {
        if (instancia == null) {
            instancia = new RecepcionistaDAOImpl();
        }
        return instancia;
    }

  
    @Override
    public List<Recepcionista> cargarTodos() {
        asegurarArchivoExiste();
        File archivo = new File(ARCHIVO_JSON);

        if (archivo.length() == 0) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<ArrayList<Recepcionista>>(){}.getType();
            List<Recepcionista> recepcionistas = gson.fromJson(reader, tipoLista);
            return recepcionistas != null ? recepcionistas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public boolean guardarRecepcionista(Recepcionista recepcionista) {
        if (recepcionista == null) {
            throw new IllegalArgumentException("El recepcionista no puede ser nulo");
        }

        try {
            List<Recepcionista> recepcionistas = cargarTodos();
            recepcionistas.add(recepcionista);
            guardarTodos(recepcionistas);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar recepcionista: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void guardarTodos(List<Recepcionista> recepcionistas) {
        if (recepcionistas == null) {
            recepcionistas = new ArrayList<>();
        }

        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(recepcionistas, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar recepcionistas: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar los recepcionistas", e);
        }
    }

    @Override
    public List<Recepcionista> obtenerTodosRecepcionistas() {
        return cargarTodos();
    }

    @Override
    public boolean eliminarRecepcionista(String numeroDocumento) {
        try {
            if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
                throw new IllegalArgumentException("Número de documento no puede ser nulo o vacío");
            }

            List<Recepcionista> recepcionistas = cargarTodos();
            boolean removed = recepcionistas.removeIf(r ->
                r != null && numeroDocumento.equals(r.getNumeroDocumento())
            );

            if (removed) {
                guardarTodos(recepcionistas);
                System.out.println("Recepcionista con documento " + numeroDocumento + " eliminado.");
            }

            return removed;

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Recepcionista buscarPorDocumento(String documento) {
        if (documento == null) {
            return null;
        }

        List<Recepcionista> recepcionistas = cargarTodos();
        for (Recepcionista recepcionista : recepcionistas) {
            if (recepcionista != null && documento.equals(recepcionista.getNumeroDocumento())) {
                return recepcionista;
            }
        }
        return null;
    }

    @Override
    public boolean actualizarRecepcionista(String cedulaOriginal, Recepcionista recepcionistaActualizado) {
        try {
            if (cedulaOriginal == null || recepcionistaActualizado == null) {
                return false;
            }

            List<Recepcionista> recepcionistas = cargarTodos();

            for (int i = 0; i < recepcionistas.size(); i++) {
                Recepcionista r = recepcionistas.get(i);
                if (r != null && cedulaOriginal.equals(r.getNumeroDocumento())) {
                    recepcionistas.set(i, recepcionistaActualizado);
                    guardarTodos(recepcionistas);
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
    public boolean existeRecepcionista(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento no puede ser nulo o vacío");
        }

        List<Recepcionista> recepcionistas = cargarTodos();
        return recepcionistas.stream()
                .filter(Objects::nonNull)
                .anyMatch(r -> numeroDocumento.equals(r.getNumeroDocumento()));
    }

    @Override
    public boolean existeEmail(String email) {
        return cargarTodos().stream()
                .filter(Objects::nonNull)
                .anyMatch(r -> email.equalsIgnoreCase(r.getEmail()));
    }

    @Override
    public boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña) {
        List<Recepcionista> recepcionistas = cargarTodos();
        boolean encontrado = false;

        for (Recepcionista recepcionista : recepcionistas) {
            if (recepcionista.getEmail().equalsIgnoreCase(emailActual)) {
                if (nuevoEmail != null && !nuevoEmail.isEmpty()) {
                    recepcionista.setEmail(nuevoEmail);
                }
                if (nuevaContraseña != null && !nuevaContraseña.isEmpty()) {
                    recepcionista.setContraseña(nuevaContraseña);
                }
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            guardarTodos(recepcionistas);
            return true;
        }
        return false;
    }

  
    @Override
    public boolean actualizarEstado(String numeroDocumento, String nuevoEstado) {
        try {
            List<Recepcionista> recepcionistas = cargarTodos();

            for (Recepcionista recepcionista : recepcionistas) {
                if (recepcionista != null && recepcionista.getNumeroDocumento().equals(numeroDocumento)) {
                    recepcionista.setEstado(nuevoEstado);
                    guardarTodos(recepcionistas);
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar estado del recepcionista: " + e.getMessage());
        }
        return false;
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
                System.err.println("Fecha inválida en JSON: " + date);
                return null;
            }
        }
    }
}
