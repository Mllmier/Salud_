package DAOImpl;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dao.SedeDAO;
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
import java.util.stream.Collectors;
import model.Sede;

public class SedeDAOImpl implements SedeDAO {
    private static final String JSON_BASE_PATH = "src/resources/data/";
    private static final String ARCHIVO_JSON = JSON_BASE_PATH + "sedes.json";
    private Gson gson;
    private static SedeDAOImpl instance;
    
    public SedeDAOImpl() {
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
    }
    
    public static synchronized SedeDAOImpl getInstance() {
        if (instance == null) {
            instance = new SedeDAOImpl();
        }
        return instance;
    }
    
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
    public List<Sede> cargarTodasSedes() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Sede>>(){}.getType();
            List<Sede> sedes = gson.fromJson(reader, tipoLista);
            return sedes != null ? sedes : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Error al cargar sedes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public boolean guardarSedeConValidacion(Sede sede) {
        if (sede == null || sede.getIdSede() == null) {
            System.err.println("Error: La sede o su código son nulos/vacíos");
            return false;
        }
        
        if (existeCodigoSede(sede.getIdSede())) {
            System.err.println("Error: Ya existe una sede con el código " + sede.getIdSede());
            return false;
        }
        
        List<Sede> sedes = cargarTodasSedes();
        sedes.add(sede);
        guardarTodos(sedes);
        return true;
    }
    
    @Override
    public void guardarTodos(List<Sede> sedes) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(sedes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar sedes: " + e.getMessage());
        }
    }
    
    @Override
    public boolean actualizarSede(String codigoOriginalSede, Sede sedeActualizada) {
        if (codigoOriginalSede == null || codigoOriginalSede.trim().isEmpty()) {
            System.err.println("Error: Código original de sede es nulo o vacío");
            return false;
        }
        
        if (sedeActualizada == null || sedeActualizada.getIdSede() == null) {
            System.err.println("Error: Sede actualizada o su código es nulo");
            return false;
        }

        try {
            List<Sede> sedes = cargarTodasSedes();
            
            for (int i = 0; i < sedes.size(); i++) {
                Sede sedeExistente = sedes.get(i);
                String codigoExistente = sedeExistente.getIdSede();
                
                if (codigoExistente != null && codigoExistente.equals(codigoOriginalSede)) {
                    sedes.set(i, sedeActualizada);
                    guardarTodos(sedes);
                    return true;
                }
            }
            
            System.err.println("No se encontró sede con código: " + codigoOriginalSede);
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar sede: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean existeCodigoSede(String codigoSede) {
        if (codigoSede == null || codigoSede.trim().isEmpty()) {
            return false;
        }
        
        List<Sede> sedes = cargarTodasSedes();
        return sedes.stream()
                   .anyMatch(s -> codigoSede.equalsIgnoreCase(s.getIdSede()));
    }
    
    @Override
    public String generarCodigoUnico() {
        List<Sede> sedes = cargarTodasSedes();
        int maxNumero = 0;
        
        for (Sede sede : sedes) {
            try {
                String codigo = sede.getIdSede();
                if (codigo != null && codigo.startsWith("SED.")) {
                    int numero = Integer.parseInt(codigo.substring(4));
                    if (numero > maxNumero) {
                        maxNumero = numero;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignorar códigos que no siguen el formato esperado
            }
        }
        
        return String.format("SED.%03d", maxNumero + 1);
    }

    @Override
    public boolean eliminarSede(String codigoSede) {
        if (codigoSede == null || codigoSede.trim().isEmpty()) {
            System.err.println("Error: Código de sede nulo o vacío");
            return false;
        }

        try {
            List<Sede> sedes = cargarTodasSedes();
            boolean encontrado = false;

            Iterator<Sede> iterator = sedes.iterator();
            while (iterator.hasNext()) {
                Sede sede = iterator.next();
                String codigoActual = sede.getIdSede();
                
                if (codigoActual != null && codigoActual.equals(codigoSede)) {
                    iterator.remove();
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                guardarTodos(sedes);
                return true;
            } else {
                System.err.println("No se encontró sede con código: " + codigoSede);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar sede: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Sede buscarPorNombre(String nombreSede) {
        List<Sede> sedes = cargarTodasSedes();
        return sedes.stream()
                .filter(s -> s.getNombreSede().equalsIgnoreCase(nombreSede))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public Sede buscarSedePorCodigo(String codigoSede) {
        if (codigoSede == null || codigoSede.trim().isEmpty()) {
            return null;
        }
        
        List<Sede> sedes = cargarTodasSedes();
        for (Sede sede : sedes) {
            if (codigoSede.equalsIgnoreCase(sede.getIdSede())) {
                return sede;
            }
        }
        return null;
    }
    
    @Override
    public List<Sede> buscarSedes(String criterio) throws IOException {
        List<Sede> todasSedes = cargarTodasSedes();
        String criterioLower = criterio.toLowerCase();
        
        return todasSedes.stream()
            .filter(sede -> 
                (sede.getIdSede() != null && sede.getIdSede().toLowerCase().contains(criterioLower)) ||
                (sede.getNombreSede() != null && sede.getNombreSede().toLowerCase().contains(criterioLower)) ||
                (sede.getDireccion() != null && sede.getDireccion().toLowerCase().contains(criterioLower)) ||
                (sede.getHorarioFuncionamiento() != null && sede.getHorarioFuncionamiento().toLowerCase().contains(criterioLower)))
            .collect(Collectors.toList());
    }
}