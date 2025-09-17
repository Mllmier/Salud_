package dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface AdminDAO {
    JsonObject obtenerAdministradorPorEmail(String email) throws IOException;
    boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña) throws IOException;
    boolean existeEmail(String email) throws IOException;
    boolean actualizarDatosAdministrador(String emailOriginal, JsonObject nuevosDatos) throws IOException;
    boolean verificarCredenciales(String email, String password) throws IOException;

}