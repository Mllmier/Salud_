package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.FieldNamingPolicy;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import model.Enfermedad;

public interface EnfermedadesDAO {
    List<Enfermedad> cargarTodasEnfermedades();
    boolean guardarEnfermedad(Enfermedad enfermedad);
    boolean guardarListaEnfermedades(List<Enfermedad> enfermedades);
    boolean actualizarEnfermedad(int idOriginal, Enfermedad enfermedadActualizada);
    boolean existeIdEnfermedad(int idEnfermedad);
    int generarNuevoId();
    boolean eliminarEnfermedad(int idEnfermedad);
    Enfermedad buscarEnfermedadPorId(int idEnfermedad);
    List<Enfermedad> buscarEnfermedades(String criterio);
    List<Enfermedad> filtrarPorTipo(String tipo);
}