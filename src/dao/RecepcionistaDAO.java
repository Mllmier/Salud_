package dao;

import java.util.List;
import model.Recepcionista;

public interface RecepcionistaDAO {

    List<Recepcionista> cargarTodos();

    boolean guardarRecepcionista(Recepcionista recepcionista);

    void guardarTodos(List<Recepcionista> recepcionistas);

    List<Recepcionista> obtenerTodosRecepcionistas();

    Recepcionista buscarPorDocumento(String documento);

    boolean actualizarRecepcionista(String documentoOriginal, Recepcionista recepcionistaActualizado);

    boolean eliminarRecepcionista(String numeroDocumento);

    boolean existeRecepcionista(String numeroDocumento);
    boolean existeEmail(String email);

    boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña);

    boolean actualizarEstado(String numeroDocumento, String nuevoEstado);
}
