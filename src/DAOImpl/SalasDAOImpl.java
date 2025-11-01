package DAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dao.SalasDAO;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Salas;

public class SalasDAOImpl implements SalasDAO {

    private static SalasDAOImpl instancia;

    public static synchronized SalasDAOImpl getInstancia() {
        if (instancia == null) {
            instancia = new SalasDAOImpl();
        }
        return instancia;
    }

    private static final String JSON_BASE_PATH = System.getProperty("user.dir") + "/src/resources/data/";
    private static final String ARCHIVO_JSON = JSON_BASE_PATH + "salas.json";
    private Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();
    
    private class LocalDateAdapter extends TypeAdapter<LocalDate> {
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
    public List<Salas> cargarTodasSalas() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Salas>>(){}.getType();
            List<Salas> salas = gson.fromJson(reader, tipoLista);
            return salas != null ? salas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar salas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public boolean guardarSalaConValidacion(Salas sala) {
        if (sala == null || sala.getCodigoSala() == null || sala.getCodigoSala().trim().isEmpty()) {
            System.err.println("Error: La sala o su código son nulos/vacíos");
            return false;
        }
        
        if (existeCodigoSala(sala.getCodigoSala())) {
            System.err.println("Error: Ya existe una sala con el código " + sala.getCodigoSala());
            return false;
        }
        
        List<Salas> salas = cargarTodasSalas();
        salas.add(sala);
        guardarTodos(salas);
        return true;
    }
    
    @Override
    public void guardarTodos(List<Salas> salas) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(salas, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar salas: " + e.getMessage());
        }
    }
    
    @Override
    public boolean actualizarSalas(String codigoOriginalsala, Salas salasActualizado) {
        if (codigoOriginalsala == null || codigoOriginalsala.trim().isEmpty()) {
            System.err.println("Error: Código original de sala es nulo o vacío");
            return false;
        }
        
        if (salasActualizado == null || salasActualizado.getCodigoSala() == null) {
            System.err.println("Error: Sala actualizada o su código es nulo");
            return false;
        }

        try {
            List<Salas> salas = cargarTodasSalas();
            
            for (int i = 0; i < salas.size(); i++) {
                Salas salaExistente = salas.get(i);
                String codigoExistente = salaExistente.getCodigoSala();
                
                if (codigoExistente != null && codigoExistente.equals(codigoOriginalsala)) {
                    salas.set(i, salasActualizado);
                    guardarTodos(salas);
                    return true;
                }
            }
            
            System.err.println("No se encontró sala con código: " + codigoOriginalsala);
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar sala: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean existeCodigoSala(String codigoSala) {
        if (codigoSala == null || codigoSala.trim().isEmpty()) {
            return false;
        }
        
        List<Salas> salas = cargarTodasSalas();
        return salas.stream()
                   .anyMatch(s -> s != null && codigoSala.equalsIgnoreCase(s.getCodigoSala()));
    }
    
    @Override
    public Salas buscarPorNombre(String nombre) {
        if (nombre == null) return null;

        for (Salas sala : cargarTodasSalas()) {
            if (sala != null && sala.getNombreSala() != null &&
                sala.getNombreSala().trim().equalsIgnoreCase(nombre.trim())) {
                return sala;
            }
        }
        return null;
    }
    
    @Override
    public String generarCodigoUnico() {
        List<Salas> salas = cargarTodasSalas();
        int maxNumero = 0;
        
        for (Salas sala : salas) {
            try {
                String codigo = sala.getCodigoSala();
                if (codigo != null && codigo.startsWith("SAL.")) {
                    int numero = Integer.parseInt(codigo.substring(4));
                    if (numero > maxNumero) {
                        maxNumero = numero;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignorar códigos que no siguen el formato esperado
            }
        }
        
        return String.format("SAL.%03d", maxNumero + 1);
    }
    
    @Override
    public boolean eliminarSala(String codigoSala) {
        if (codigoSala == null || codigoSala.trim().isEmpty()) {
            System.err.println("Error: Código de sala nulo o vacío");
            return false;
        }

        try {
            List<Salas> salas = cargarTodasSalas();
            boolean encontrado = false;

            Iterator<Salas> iterator = salas.iterator();
            while (iterator.hasNext()) {
                Salas sala = iterator.next();
                String codigoActual = sala.getCodigoSala();
                
                if (codigoActual != null && codigoActual.equals(codigoSala)) {
                    iterator.remove();
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                guardarTodos(salas);
                return true;
            } else {
                System.err.println("No se encontró sala con código: " + codigoSala);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar sala: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}