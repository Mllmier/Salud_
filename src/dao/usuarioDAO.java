package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class usuarioDAO {

    private static final String JSON_BASE_PATH = System.getProperty("user.dir") + "/src/resources/data/";
    private static final String ADMINISTRADORES_JSON = JSON_BASE_PATH + "usuarios.json";
    private static final String MEDICOS_JSON = JSON_BASE_PATH + "medico.json";
    private static final String RECEPCIONISTAS_JSON = JSON_BASE_PATH + "recepcionista.json";
    private static final String PACIENTES_JSON = JSON_BASE_PATH + "pacientes.json";
    

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     public static Medico medicoActual; 
     public static Recepcionista recepcionistaActual;
    /**
     * Valida las credenciales de un usuario según su rol
     * @param email Correo electrónico del usuario
     * @param contraseña Contraseña del usuario
     * @param rol Rol del usuario (Administrador, Médico, Recepcionista, Paciente)
     * @return Objeto del tipo de usuario correspondiente o null si no se encuentra
     */
    public Object validarCredenciales(String email, String contraseña, String rol) {
        try {
            switch (rol) {
                case "Administrador":
                    return buscarAdministrador(email, contraseña);

                case "Doctor":
                    Medico medico = buscarMedico(email, contraseña);
                    if (medico != null) {
                    usuarioDAO.medicoActual = medico; 
                     }
                    return medico;

                case "Recepcionista":
                    Recepcionista recepcionista = buscarRecepcionista(email, contraseña);
                    if (recepcionista != null) {
                        usuarioDAO.recepcionistaActual = recepcionista;
                    }
                    
                    return recepcionista;

                case "Paciente":
                    return buscarPaciente(email, contraseña);

                default:
                    System.err.println("Rol no reconocido: " + rol);
                    return null;
            }
        } catch (Exception e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Administrador buscarAdministrador(String email, String contraseña) throws Exception {
        System.out.println("Buscando administrador en: " + ADMINISTRADORES_JSON);
        try (FileReader reader = new FileReader(ADMINISTRADORES_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            System.out.println("Número de administradores en archivo: " + array.size());

            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                System.out.println("Comparando con: " + json.get("email").getAsString());

                if (json.get("email").getAsString().equalsIgnoreCase(email) &&
                    json.get("contraseña").getAsString().equals(contraseña)) {

                    System.out.println("Administrador encontrado!");
                    return new Administrador(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        json.get("codigoEmpleado").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("contraseña").getAsString()
                    );
                }
            }
        }
        System.out.println("Administrador no encontrado con esas credenciales");
        return null;
    }

   private Medico buscarMedico(String email, String contraseña) throws Exception {
    System.out.println("Buscando médico en: " + MEDICOS_JSON);
    try (FileReader reader = new FileReader(MEDICOS_JSON)) {
        JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
        System.out.println("Número de médicos en archivo: " + array.size());

        for (int i = 0; i < array.size(); i++) {
            JsonObject json = array.get(i).getAsJsonObject();
            System.out.println("Comparando con: " + json.get("email").getAsString());

            if (json.get("email").getAsString().equalsIgnoreCase(email) &&
                json.get("contraseña").getAsString().equals(contraseña)) {

                System.out.println("Médico encontrado!");


                String estado = json.has("estado") && !json.get("estado").isJsonNull()
                        ? json.get("estado").getAsString()
                        : "Activo";

                String sede = json.has("sede") && !json.get("sede").isJsonNull()
                        ? json.get("sede").getAsString()
                        : "Sin asignar";
                
                 String sala = json.has("sala") && !json.get("sala").isJsonNull()
                        ? json.get("sala").getAsString()
                        : "Sin asignar";

                return new Medico(
                    json.get("numeroDocumento").getAsString(),
                    json.get("nombres").getAsString(),
                    json.get("apellidos").getAsString(),
                    LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                    json.get("sexo").getAsString(),
                    json.get("email").getAsString(),
                    json.get("celular").getAsString(),
                    json.get("contraseña").getAsString(),
                    json.get("especialidad").getAsString(),
                    LocalDate.parse(json.get("fechaContratacion").getAsString(), DATE_FORMATTER),
                    json.get("horario").getAsString(),
                    estado,
                    sede,
                    sala
                );
            }
        }
    }
    System.out.println("Médico no encontrado con esas credenciales");
    return null;
}


    private Recepcionista buscarRecepcionista(String email, String contraseña) throws Exception {
        // Obtener la ruta absoluta del archivo
        String filePath = new File(RECEPCIONISTAS_JSON).getAbsolutePath();
        System.out.println("Buscando recepcionista en: " + filePath);

        // Verificar si el archivo existe
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("ERROR: El archivo no existe en: " + filePath);
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            System.out.println("Número de recepcionistas: " + array.size());

            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                String jsonEmail = json.get("email").getAsString().toLowerCase();

                if (jsonEmail.equals(email.toLowerCase())) {
                    System.out.println("Usuario encontrado, verificando contraseña...");
                    String jsonPassword = json.get("contraseña").getAsString();

                    // Comparación segura de contraseñas (en tu caso son hashes)
                    if (jsonPassword.equals(contraseña)) {
                        System.out.println("Autenticación exitosa!");
                        return new Recepcionista(
                            json.get("numeroDocumento").getAsString(),
                            json.get("nombres").getAsString(),
                            json.get("apellidos").getAsString(),
                            LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                            json.get("sexo").getAsString(),
                            json.get("eps").getAsString(),
                            json.get("email").getAsString(),
                            json.get("celular").getAsString(),
                            json.get("contraseña").getAsString(),
                            json.get("codigoEmpleado").getAsString(),
                            LocalDate.parse(json.get("fechaContratacion").getAsString(), DATE_FORMATTER),
                            json.get("horario").getAsString()
                        );
                    } else {
                        System.out.println("Contraseña incorrecta para: " + email);
                    }
                }
            }
        }
        System.out.println("Usuario no encontrado: " + email);
        return null;
    }

    private Paciente buscarPaciente(String email, String contraseña) throws Exception {
        System.out.println("Buscando paciente en: " + PACIENTES_JSON);
        try (FileReader reader = new FileReader(PACIENTES_JSON)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            System.out.println("Número de pacientes en archivo: " + array.size());

            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                System.out.println("Comparando con: " + json.get("email").getAsString());

                if (json.get("email").getAsString().equalsIgnoreCase(email) &&
                    json.get("contraseña").getAsString().equals(contraseña)) {

                    System.out.println("Paciente encontrado!");
                    int peso = json.has("Peso") && !json.get("Peso").isJsonNull() ? json.get("Peso").getAsInt() : 0;
                    double altura = json.has("Altura") && !json.get("Altura").isJsonNull() ? json.get("Altura").getAsDouble() : 0.0;

                    return new Paciente(
                        json.get("numeroDocumento").getAsString(),
                        json.get("nombres").getAsString(),
                        json.get("apellidos").getAsString(),
                        LocalDate.parse(json.get("fechaNacimiento").getAsString(), DATE_FORMATTER),
                        json.get("sexo").getAsString(),
                        json.get("eps").getAsString(),
                        json.get("email").getAsString(),
                        json.get("celular").getAsString(),
                        json.get("contraseña").getAsString(),
                        json.get("tipoDocumento").getAsString(),
                        json.get("tipoSangre").getAsString(),
                        json.get("antecedentes").getAsString(),
                        peso,
                        altura
                    );
                }
            }
        }
        System.out.println("Paciente no encontrado con esas credenciales");
        return null;
    }

    // Métodos para verificar roles
    public boolean esAdministrador(Object usuario) {
        return usuario instanceof Administrador;
    }

    public boolean esMedico(Object usuario) {
        return usuario instanceof Medico;
    }

    public boolean esRecepcionista(Object usuario) {
        return usuario instanceof Recepcionista;
    }

    public boolean esPaciente(Object usuario) {
        return usuario instanceof Paciente;
    }

    /**
     * Método para verificar si un email ya existe en algún tipo de usuario
     * @param email Email a verificar
     * @return true si el email ya existe, false si no
     */
    public boolean existeEmail(String email) {
        try {
            return buscarEmailEnArchivo(email, ADMINISTRADORES_JSON) ||
                   buscarEmailEnArchivo(email, MEDICOS_JSON) ||
                   buscarEmailEnArchivo(email, RECEPCIONISTAS_JSON) ||
                   buscarEmailEnArchivo(email, PACIENTES_JSON);
        } catch (Exception e) {
            System.err.println("Error al verificar email: " + e.getMessage());
            return false;
        }
    }

    private boolean buscarEmailEnArchivo(String email, String archivo) throws Exception {
        try (FileReader reader = new FileReader(archivo)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject json = array.get(i).getAsJsonObject();
                if (json.get("email").getAsString().equalsIgnoreCase(email)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
   public List<Paciente> cargarPacientes() {
    try {
        File file = new File(PACIENTES_JSON); // <- usa la misma ruta del login

        if (!file.exists()) {
            System.err.println("ERROR: No existe el archivo pacientes.json en: " + file.getAbsolutePath());
            return new ArrayList<>();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader reader = new FileReader(file);

        List<Paciente> pacientes = gson.fromJson(reader, new TypeToken<List<Paciente>>() {}.getType());
        reader.close();

        return pacientes != null ? pacientes : new ArrayList<>();

    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}


    public Paciente validarPacientePorEmailIdentificacion(String email, String identificacion) {
    List<Paciente> pacientes = cargarPacientes(); // tu método existente

    for (Paciente p : pacientes) {
        if (p.getEmail().equalsIgnoreCase(email)
                && p.getNumeroDocumento().equals(identificacion)) {
            return p;
        }
    }
    return null;
}

}
