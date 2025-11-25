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
import model.Medicamento;
import dao.ExamenesMedicosDAO;
import model.ExamenesMedicos;

/**
 *
 * @author Maria liz
 */
public class ExamenesDAOImpl implements ExamenesMedicosDAO{
      private static final String JSON_BASE_PATH = System.getProperty("user.dir") + "/src/resources/data/";
    private static final String ARCHIVO_JSON = JSON_BASE_PATH + "examenes.json";

    private final Gson gson;

    public ExamenesDAOImpl() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
    }

    @Override
    public List<ExamenesMedicos> cargarTodos() throws IOException {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<ExamenesMedicos>>(){}.getType();
            List<ExamenesMedicos> examenes = gson.fromJson(reader, tipoLista);
            return examenes != null ? examenes : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error al cargar Examenes Médicos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void guardarExamenesMedicos(ExamenesMedicos examenesMedicos) throws IOException {
        List<ExamenesMedicos> examenes = cargarTodos();
        examenes.add(examenesMedicos);
        guardarTodos(examenes);
    }

    @Override
    public void guardarTodos(List<ExamenesMedicos> examenes) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(examenes, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar examenes: " + e.getMessage());
            throw new RuntimeException("No se pudo guardar los examenes", e);
        }
    }

    @Override
    public boolean eliminarExamenesMedicos(String idExamenes) {
        if (idExamenes == null || idExamenes.trim().isEmpty()) {
            System.err.println("Error: Código de examen nulo o vacío");
            return false;
        }

        try {
            List<ExamenesMedicos> examenes = cargarTodos();
            boolean encontrado = false;

            Iterator<ExamenesMedicos> iterator = examenes.iterator();
            while (iterator.hasNext()) {
                ExamenesMedicos examen = iterator.next();
                if (examen.getIdExamenes() != null && examen.getIdExamenes().equals(idExamenes)) {
                    iterator.remove();
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                guardarTodos(examenes);
                return true;
            } else {
                System.err.println("No se encontró examen con código: " + idExamenes);
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error al eliminar examen: " + e.getMessage());
            return false;
        }
    }
     @Override
    public boolean actualizarExamenesMedicos(String idExamenes, ExamenesMedicos examenes) {
        try {
            List<ExamenesMedicos> examen = cargarTodos();
            
            for (int i = 0; i < examen.size(); i++) {
                if (examen.get(i).getIdExamenes().equals(idExamenes)) {
                    examen.set(i, examenes);
                    guardarTodos(examen);
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
    public boolean existeCodigoMedicamento(String idExamenes) throws IOException {
        if (idExamenes == null || idExamenes.trim().isEmpty()) {
            return false;
        }

        List<ExamenesMedicos> examenes = cargarTodos();
        return examenes.stream()
                .anyMatch(e -> idExamenes.equalsIgnoreCase(e.getIdExamenes()));
    }

    @Override
    public String generarCodigoUnico() throws IOException {
        List<ExamenesMedicos> examenes = cargarTodos();
        int max = 0;

        for (ExamenesMedicos examen : examenes) {
            try {
                if (examen.getIdExamenes().startsWith("Med.")) {
                    int numero = Integer.parseInt(examen.getIdExamenes().substring(4));
                    if (numero > max) max = numero;
                }
            } catch (Exception ignore) {}
        }

        return String.format("Med.%03d", max + 1);
    }

    @Override
    public List<ExamenesMedicos> buscarExamenes(String criterio) throws IOException {
        List<ExamenesMedicos> resultado = new ArrayList<>();
        for (ExamenesMedicos examen : cargarTodos()) {
            if (examen.getNombre().toLowerCase().contains(criterio.toLowerCase())) {
                resultado.add(examen);
            }
        }
        return resultado;
    }

    private static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            if (value != null) out.value(value.format(formatter));
            else out.nullValue();
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            String date = in.nextString();
            if (date == null || date.trim().isEmpty()) return null;

            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("Fecha inválida en JSON: " + date);
                return null;
            }
        }
    }
}
