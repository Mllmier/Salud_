/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import model.OrdenMedica;
import dao.OrdenMedicaDAO;
import java.io.File;
import model.Cita;

/**
 *
 * @author Maria liz
 */
public class OrdenMedicaDAOImpl implements OrdenMedicaDAO{
private static final String JSON_BASE_PATH = "src/resources/data/";
private static final String ARCHIVO_JSON = JSON_BASE_PATH + "ordenmedica.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<OrdenMedica> cargarTodas() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<ArrayList<OrdenMedica>>() {}.getType();
            List<OrdenMedica> ordenes = gson.fromJson(reader, tipoLista);
            return ordenes != null ? ordenes : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error al cargar órdenes médicas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void guardarOrdenMedica(OrdenMedica orden) {
        List<OrdenMedica> ordenes = cargarTodas();
        ordenes.add(orden);
        guardarTodas(ordenes);
    }

    @Override
    public void guardarTodas(List<OrdenMedica> ordenes) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(ordenes, writer);
        } catch (Exception e) {
            System.err.println("Error al guardar órdenes médicas: " + e.getMessage());
        }
    }
@Override
public OrdenMedica obtenerPorIdCita(String idCita) {
    for (OrdenMedica orden : cargarTodas()) {
        if (orden.getIdCita() != null && orden.getIdCita().equals(idCita)) {
            return orden;
        }
    }
    return null;
}

 
/*

public String generarCodigoOrden(){
List<OrdenMedica> ordenmedica = cargarTodas();
int maxNumero=0;

for(OrdenMedica orden : ordenmedica ){
  try{
  }catch(NumberFormatException e){
  
  
  }
}

}*/


}
