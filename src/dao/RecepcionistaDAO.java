package dao;

import java.util.List;
import model.Recepcionista;

public interface RecepcionistaDAO {

    // 🔹 Cargar todos los recepcionistas desde el archivo JSON
    List<Recepcionista> cargarTodos();

    // 🔹 Guardar un nuevo recepcionista
    boolean guardarRecepcionista(Recepcionista recepcionista);

    // 🔹 Guardar toda la lista (sobrescribe el JSON completo)
    void guardarTodos(List<Recepcionista> recepcionistas);

    // 🔹 Obtener todos los recepcionistas
    List<Recepcionista> obtenerTodosRecepcionistas();

    // 🔹 Buscar por documento
    Recepcionista buscarPorDocumento(String documento);

    // 🔹 Actualizar información completa de un recepcionista
    boolean actualizarRecepcionista(String documentoOriginal, Recepcionista recepcionistaActualizado);

    // 🔹 Eliminar un recepcionista (si decides borrarlo completamente)
    boolean eliminarRecepcionista(String numeroDocumento);

    // 🔹 Verificar si ya existe un recepcionista con esa cédula
    boolean existeRecepcionista(String numeroDocumento);
    // 🔹 Verificar si existe un email registrado
    boolean existeEmail(String email);

    // 🔹 Actualizar credenciales (correo o contraseña)
    boolean actualizarCredenciales(String emailActual, String nuevoEmail, String nuevaContraseña);

    // 🔹 NUEVO: Actualizar el estado ("Activo" / "Deshabilitado")
    boolean actualizarEstado(String numeroDocumento, String nuevoEstado);
}
