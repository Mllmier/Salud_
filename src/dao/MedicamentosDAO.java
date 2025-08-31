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
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Medicamento;




public interface MedicamentosDAO {
    List<Medicamento> cargarTodos() throws IOException;
    void guardarMedicamento(Medicamento medicamento) throws IOException;
    void guardarTodos(List<Medicamento> medicamentos);
    boolean eliminarMedicamento(String codMedicamento);
    boolean actualizarMedicamento(String codMedicamento, Medicamento medicamentoActualizado);
    boolean existeCodigoMedicamento(String codMedicamento) throws IOException;
    String generarCodigoUnico() throws IOException;
    List<Medicamento> buscarMedicamentos(String criterio) throws IOException;
}
