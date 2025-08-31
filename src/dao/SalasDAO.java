/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

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
import model.Salas;
import model.Sede;

/**
 *
 * @author usuario
 */
public interface SalasDAO {
    List<Salas> cargarTodasSalas();
    boolean guardarSalaConValidacion(Salas sala);
    void guardarTodos(List<Salas> salas);
    boolean actualizarSalas(String codigoOriginalsala, Salas salasActualizado);
    boolean existeCodigoSala(String codigoSala);
    Salas buscarPorNombre(String nombre);
    String generarCodigoUnico();
    boolean eliminarSala(String codigoSala);
}