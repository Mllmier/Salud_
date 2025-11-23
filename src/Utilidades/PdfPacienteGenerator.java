package Utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JOptionPane;
import model.Paciente;
import model.HistoriaMedica;

public class PdfPacienteGenerator {

    public static void generarFichaPaciente(Paciente paciente, List<HistoriaMedica> historial) {

        try {
            Document documento = new Document(PageSize.A4, 40, 40, 50, 50);
            String ruta = "Ficha_Paciente_" + paciente.getNumeroDocumento() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));

            documento.open();

            // ----------- ENCABEZADO ELEGANTE -----------
            PdfPTable encabezado = new PdfPTable(1);
            encabezado.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("FICHA CLÍNICA DEL PACIENTE",
                    new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE)));
            cell.setBackgroundColor(new BaseColor(30, 136, 229)); // azul material
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(12);
            encabezado.addCell(cell);
            documento.add(encabezado);

            documento.add(new Paragraph("\n"));

            // ----------- DATOS BÁSICOS -----------
            PdfPTable tablaDatos = new PdfPTable(2);
            tablaDatos.setWidthPercentage(100);
            tablaDatos.setSpacingBefore(10);

            agregarDato(tablaDatos, "Nombre:", paciente.getNombres());
            agregarDato(tablaDatos, "Apellidos:", paciente.getApellidos());
            agregarDato(tablaDatos, "Identificación:", paciente.getNumeroDocumento());
            agregarDato(tablaDatos, "Email:", paciente.getEmail());
            agregarDato(tablaDatos, "Celular:", paciente.getCelular());
            agregarDato(tablaDatos, "Fecha de nacimiento:", paciente.getFechaNacimiento().toString());
            agregarDato(tablaDatos, "Sexo:", paciente.getSexo());
            agregarDato(tablaDatos, "EPS:", paciente.getEps());
            agregarDato(tablaDatos, "Tipo de Sangre:", paciente.getTipoSangre());
            agregarDato(tablaDatos, "Altura:", paciente.getAltura() + " m");
            agregarDato(tablaDatos, "Peso:", paciente.getPeso() + " kg");

            documento.add(tablaDatos);

            documento.add(new Paragraph("\n"));

            // ----------- LÍNEA DIVISORIA -----------
            Paragraph linea = new Paragraph("___________________________________________________________");
            linea.setAlignment(Element.ALIGN_CENTER);
            documento.add(linea);

            documento.add(new Paragraph("\n"));

            // ----------- HISTORIAL MÉDICO -----------
            Paragraph tituloHist = new Paragraph(
                    "HISTORIAL MÉDICO",
                    new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK)
            );
            tituloHist.setSpacingAfter(10);
            documento.add(tituloHist);

            if (historial == null || historial.isEmpty()) {
                documento.add(new Paragraph("No hay registros médicos disponibles.",
                        new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC)));
            } else {
                for (HistoriaMedica h : historial) {
                    Paragraph item = new Paragraph(
                            "• Fecha: " + h.getFechaCreacion()+
                            "\n  Diagnóstico: " + h.getDiagnostico() +
                            "\n  Tratamiento: " + h.getTratamiento() +
                            "\n",
                            new Font(Font.FontFamily.HELVETICA, 12)
                    );
                    documento.add(item);
                }
            }

            documento.close();

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
