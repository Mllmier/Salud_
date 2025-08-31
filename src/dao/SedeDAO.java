/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author usuario
 */


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
import java.util.stream.Collectors;
import model.Sede;

/**
 *
 * @author usuario
 */
public interface SedeDAO {
  List<Sede> cargarTodasSedes();
    boolean guardarSedeConValidacion(Sede sede);
    void guardarTodos(List<Sede> sedes);
    boolean actualizarSede(String codigoOriginalSede, Sede sedeActualizada);
    boolean existeCodigoSede(String codigoSede);
    String generarCodigoUnico();
    boolean eliminarSede(String codigoSede);
    Sede buscarPorNombre(String nombreSede);
    Sede buscarSedePorCodigo(String codigoSede);
    List<Sede> buscarSedes(String criterio) throws IOException;
}

