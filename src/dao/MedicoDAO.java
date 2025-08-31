package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Medico;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.stream.JsonWriter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public interface MedicoDAO {
    
List<Medico> cargarTodos();
    boolean guardarMedico(Medico medico);
    void guardarTodos(List<Medico> medicos);
    List<Medico> obtenerTodosMedicos();
    boolean eliminarMedico(String numeroDocumento);
    Medico buscarMedicoPorIdentificacion(String documento);
    Medico buscarPorDocumentoMedico(String documento);
    boolean actualizarMedico(String cedulaOriginal, Medico medicoActualizado);
    Medico buscarPorNombreYApellido(String nombre, String apellido);
    boolean existeMedico(String numeroDocumento);
    boolean existeEmail(String email);
    boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña);
}