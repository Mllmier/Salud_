/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.io.IOException;
import java.util.List;
import model.ExamenesMedicos;


/**
 *
 * @author Maria liz
 */
public interface ExamenesMedicosDAO {
    List<ExamenesMedicos> cargarTodos() throws IOException;
    void guardarExamenesMedicos(ExamenesMedicos examenesMedicos) throws IOException;
    void guardarTodos(List<ExamenesMedicos> examenesMedicos);
    boolean eliminarExamenesMedicos(String idExamenes);
    boolean actualizarExamenesMedicos(String idExamenes, ExamenesMedicos examenesMedicos);
    boolean existeCodigoMedicamento(String idExamenes) throws IOException;
    String generarCodigoUnico() throws IOException;
    List<ExamenesMedicos> buscarExamenes(String criterio) throws IOException;
}
