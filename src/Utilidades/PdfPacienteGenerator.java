package Utilidades;

import Controller.ControllerOrdenMedica;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Paciente;
import model.HistoriaMedica;
import model.OrdenMedica;

public class PdfPacienteGenerator {

public static void generarOrdenMedica(OrdenMedica orden, String rutaDestino) throws Exception {

    try {

        // ✨ 1. Seleccionar ubicación donde guardar
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar Orden Médica");
        chooser.setSelectedFile(new File("OrdenMedica_" + orden.getIdCita() + ".pdf"));

        int opcion = chooser.showSaveDialog(null);
        if (opcion != JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(null,
                    "Se canceló la descarga del PDF.",
                    "Cancelado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ruta = chooser.getSelectedFile().getAbsolutePath();

        // Asegura extensión .pdf
        if (!ruta.toLowerCase().endsWith(".pdf")) {
            ruta += ".pdf";
        }

        // ✨ 2. Generación del PDF (TU DISEÑO ORIGINAL SIN CAMBIOS)
        Document documento = new Document(PageSize.A4, 40, 40, 50, 50);
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));

        documento.open();

        // ----------- ENCABEZADO ELEGANTE -----------
        PdfPTable encabezado = new PdfPTable(1);
        encabezado.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell(new Phrase("ORDEN MÉDICA",
                new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE)));
        cell.setBackgroundColor(new BaseColor(30, 136, 229)); // azul material
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(12);
        encabezado.addCell(cell);
        documento.add(encabezado);

        documento.add(new Paragraph("\n"));

        // ----------- DATOS DEL PACIENTE -----------
        PdfPTable tablaDatos = new PdfPTable(2);
        tablaDatos.setWidthPercentage(100);
        tablaDatos.setSpacingBefore(10);

        agregarDato(tablaDatos, "Nombre:", orden.getNombre());
        agregarDato(tablaDatos, "Apellidos:", orden.getApellido());
        agregarDato(tablaDatos, "Email:", orden.getEmail());
        agregarDato(tablaDatos, "Celular:", orden.getCelular());
        agregarDato(tablaDatos, "Fecha de nacimiento:", orden.getFechaNacimiento());
        agregarDato(tablaDatos, "Sexo:", orden.getSexo());
        agregarDato(tablaDatos, "EPS:", orden.getEps());
        agregarDato(tablaDatos, "Tipo de Sangre:", orden.getTipoSangre());
        agregarDato(tablaDatos, "Altura:", orden.getAltura());
        agregarDato(tablaDatos, "Peso:", orden.getPeso());

        documento.add(tablaDatos);
        documento.add(new Paragraph("\n"));

        // ----------- LÍNEA DIVISORIA -----------
        Paragraph linea = new Paragraph("___________________________________________________________");
        linea.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea);

        documento.add(new Paragraph("\n"));

        // ----------- DATOS DE LA CITA -----------
        PdfPTable tablaCita = new PdfPTable(2);
        tablaCita.setWidthPercentage(100);

        agregarDato(tablaCita, "Fecha:", orden.getFecha());
        agregarDato(tablaCita, "Hora:", orden.getHora());
        agregarDato(tablaCita, "Motivo:", orden.getMotivo());
        agregarDato(tablaCita, "Estado:", orden.getEstado());
        agregarDato(tablaCita, "Sede:", orden.getSede());
        agregarDato(tablaCita, "ID Cita:", orden.getIdCita());

        documento.add(tablaCita);
        documento.add(new Paragraph("\n"));

        // ----------- MÉDICO -----------
        PdfPTable tablaMedico = new PdfPTable(2);
        tablaMedico.setWidthPercentage(100);

        agregarDato(tablaMedico, "Médico:", orden.getNombreMedico());
        agregarDato(tablaMedico, "Apellido Médico:", orden.getApellidoMedico());
        agregarDato(tablaMedico, "Especialidad:", orden.getEspecialidadMedico());

        documento.add(tablaMedico);
        documento.add(new Paragraph("\n"));

        // ----------- SECCIÓN DIAGNÓSTICO -----------

        Paragraph tituloDiag = new Paragraph(
                "DIAGNÓSTICO",
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)
        );
        documento.add(tituloDiag);
        documento.add(new Paragraph(orden.getDiagnostico()));
        documento.add(new Paragraph("\n"));

        // ----------- RECETA -----------

        Paragraph tituloReceta = new Paragraph(
                "RECETA",
                new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)
        );
        documento.add(tituloReceta);
        documento.add(new Paragraph(orden.getReceta()));
        documento.add(new Paragraph("\n"));

// ----------- MEDICAMENTOS -----------
Paragraph tituloMed = new Paragraph(
        "MEDICAMENTOS",
        new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD)
);
documento.add(tituloMed);

// Revisar si hay medicamentos
if (orden.getAreamedicamentos() == null || orden.getAreamedicamentos().isEmpty()) {
    documento.add(new Paragraph("No hay medicamentos formulados.",
            new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC)));
} else {
    for (String med : orden.getAreamedicamentos()) {
        documento.add(new Paragraph("• " + med,
                new Font(Font.FontFamily.HELVETICA, 12)));
    }
}


        documento.close();

        // ✨ Mensaje final
        JOptionPane.showMessageDialog(null,
                "PDF generado correctamente en:\n" + ruta,
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
                "Error al generar PDF: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}


    private static void agregarDato(PdfPTable tabla, String etiqueta, String valor) {
        PdfPCell c1 = new PdfPCell(new Phrase(etiqueta));
        c1.setBackgroundColor(new BaseColor(224, 224, 224));
        c1.setPadding(8);
        tabla.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase(valor == null ? "No disponible" : valor));
        c2.setPadding(8);
        tabla.addCell(c2);
    }



}
