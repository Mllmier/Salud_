package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Recepcionista;

public interface RecepcionistaDAO {
List<Recepcionista> cargarTodos();
    boolean guardarRecepcionista(Recepcionista recepcionista);
    void guardarTodos(List<Recepcionista> recepcionistas);
    boolean eliminarRecepcionista(String numeroDocumento);
    Recepcionista obtenerPorDocumento(String documento);
    boolean actualizarRecepcionista(String documentoOriginal, Recepcionista recepcionistaActualizado);
    public Recepcionista buscarPorEmail(String email);
    public boolean existeEmail(String email);
    public boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña);
    
}