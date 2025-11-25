package DAOImpl;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import dao.MedicamentosDAO;
import model.Medicamento;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MedicamentosDAOImpl implements MedicamentosDAO {
    private static final String JSON_BASE_PATH = System.getProperty("user.dir") + "/src/resources/data/";
    private static final String ARCHIVO_JSON = JSON_BASE_PATH + "medicamentos.json";
    private final Gson gson;

    public MedicamentosDAOImpl() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    @Override
    public List<Medicamento> cargarTodos() throws IOException {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<Medicamento>>(){}.getType();
            List<Medicamento> medicamentos = gson.fromJson(reader, tipoLista);
            return medicamentos != null ? medicamentos : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar medicamentos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void guardarMedicamento(Medicamento medicamento) throws IOException {
        List<Medicamento> medicamentos = cargarTodos();
        medicamentos.add(medicamento);
        guardarTodos(medicamentos);
    }

    @Override
    public void guardarTodos(List<Medicamento> medicamentos) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(medicamentos, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar medicamentos: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar los medicamentos", e);
        }
    }

    @Override
    public boolean eliminarMedicamento(String codMedicamento) {
        if (codMedicamento == null || codMedicamento.trim().isEmpty()) {
            System.err.println("Error: Código de medicamento nulo o vacío");
            return false;
        }

        try {
            List<Medicamento> medicamentos = cargarTodos();
            boolean encontrado = false;

            Iterator<Medicamento> iterator = medicamentos.iterator();
            while (iterator.hasNext()) {
                Medicamento medicamento = iterator.next();
                String codigoActual = medicamento.getIdMedicamento();
                
                if (codigoActual != null && codigoActual.equals(codMedicamento)) {
                    iterator.remove();
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                guardarTodos(medicamentos);
                return true;
            } else {
                System.err.println("No se encontró medicamento con código: " + codMedicamento);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar medicamento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarMedicamento(String codMedicamento, Medicamento medicamentoActualizado) {
        try {
            List<Medicamento> medicamentos = cargarTodos();
            
            for (int i = 0; i < medicamentos.size(); i++) {
                if (medicamentos.get(i).getIdMedicamento().equals(codMedicamento)) {
                    medicamentos.set(i, medicamentoActualizado);
                    guardarTodos(medicamentos);
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
    public boolean existeCodigoMedicamento(String codMedicamento) throws IOException {
        if (codMedicamento == null || codMedicamento.trim().isEmpty()) {
            return false;
        }
        
        List<Medicamento> medicamentos = cargarTodos();
        return medicamentos.stream()
                    .anyMatch(s -> codMedicamento.equalsIgnoreCase(s.getIdMedicamento()));
    }

    @Override
    public String generarCodigoUnico() throws IOException {
        List<Medicamento> medicamentos = cargarTodos();
        int maxNumero = 0;
        
        for (Medicamento medicamento : medicamentos) {
            try {
                String codigo = medicamento.getIdMedicamento();
                if (codigo != null && codigo.startsWith("Med.")) {
                    int numero = Integer.parseInt(codigo.substring(4));
                    if (numero > maxNumero) {
                        maxNumero = numero;
                    }
                }
            } catch (NumberFormatException e) {
            }
        }
        
        return String.format("Med.%03d", maxNumero + 1);
    }

    @Override
    public List<Medicamento> buscarMedicamentos(String criterio) throws IOException {
        List<Medicamento> medicamentos = cargarTodos();
        List<Medicamento> resultados = new ArrayList<>();
        
        if (criterio == null || criterio.trim().isEmpty()) {
            return medicamentos;
        }
        
        String criterioLower = criterio.toLowerCase();
        
        for (Medicamento med : medicamentos) {
            if (med.getIdMedicamento().toLowerCase().contains(criterioLower) || 
                med.getNombre().toLowerCase().contains(criterioLower)) {
                resultados.add(med);
            }
        }
        
        return resultados;
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
}