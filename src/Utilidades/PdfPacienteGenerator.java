package Utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.OrdenMedica;

public class PdfPacienteGenerator {

    // ----- FUENTES -----
    private static final Font TITULO_PRINCIPAL =
            new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.WHITE);

    private static final Font TITULO_CARD =
            new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

    private static final Font TEXTO =
            new Font(Font.FontFamily.HELVETICA, 12);

    public static void generarOrdenMedica(OrdenMedica orden, String rutaDestino) throws Exception {

        try {

            // --- Selección de archivo ---
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Guardar Orden Médica");
            chooser.setSelectedFile(new File("OrdenMedica_" + orden.getIdCita() + ".pdf"));

            if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "Se canceló la descarga del PDF.");
                return;
            }

            String ruta = chooser.getSelectedFile().getAbsolutePath();
            if (!ruta.endsWith(".pdf")) ruta += ".pdf";

            // --- Documento ---
            Document doc = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            // --- ENCABEZADO ---
            PdfPTable encabezado = new PdfPTable(1);
            encabezado.setWidthPercentage(100);

            PdfPCell titulo = new PdfPCell(new Phrase("ORDEN MÉDICA", TITULO_PRINCIPAL));
            titulo.setBackgroundColor(new BaseColor(30, 136, 229));
            titulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            titulo.setPadding(15);
            titulo.setBorder(Rectangle.NO_BORDER);
            encabezado.addCell(titulo);

            doc.add(encabezado);
            doc.add(new Paragraph("\n"));

            // --------------------------------------------------------
            //                  SECCIONES TIPO "CARD"
            // --------------------------------------------------------

            doc.add(crearCard("DATOS DEL PACIENTE"));
            doc.add(crearPar("Nombre: " + orden.getNombre()));
            doc.add(crearPar("Apellidos: " + orden.getApellido()));
            doc.add(crearPar("Email: " + orden.getEmail()));
            doc.add(crearPar("Celular: " + orden.getCelular()));
            doc.add(crearPar("Fecha de nacimiento: " + orden.getFechaNacimiento()));
            doc.add(crearPar("Sexo: " + orden.getSexo()));
            doc.add(crearPar("EPS: " + orden.getEps()));
            doc.add(crearPar("Tipo de sangre: " + orden.getTipoSangre()));
            doc.add(crearPar("Altura: " + orden.getAltura()));
            doc.add(crearPar("Peso: " + orden.getPeso()));
            doc.add(new Paragraph("\n"));

            doc.add(crearCard("INFORMACIÓN DE LA CITA"));
            doc.add(crearPar("Fecha: " + orden.getFecha()));
            doc.add(crearPar("Hora: " + orden.getHora()));
            doc.add(crearPar("Motivo: " + orden.getSede()));
            doc.add(crearPar("Estado: " + orden.getEstado()));
            doc.add(crearPar("Sede: " + orden.getMotivo()));
            doc.add(crearPar("ID Cita: " + orden.getIdCita()));
            doc.add(new Paragraph("\n"));

            doc.add(crearCard("DATOS DEL MÉDICO"));
            doc.add(crearPar("Nombre: " + orden.getNombreMedico()));
            doc.add(crearPar("Apellido: " + orden.getApellidoMedico()));
            doc.add(crearPar("Especialidad: " + orden.getEspecialidadMedico()));
            doc.add(new Paragraph("\n"));

            doc.add(crearCard("DIAGNÓSTICO"));
            doc.add(crearPar(orden.getDiagnostico()));
            doc.add(new Paragraph("\n"));

            doc.add(crearCard("RECETA"));
            doc.add(crearPar(orden.getReceta()));
            doc.add(new Paragraph("\n"));

            doc.add(crearCard("MEDICAMENTOS"));

            if (orden.getAreamedicamentos() != null && !orden.getAreamedicamentos().isEmpty()) {
                for (String m : orden.getAreamedicamentos()) {
                    doc.add(crearPar("• " + m));
                }
            } else {
                doc.add(crearPar("No hay medicamentos formulados."));
            }

            doc.close();

            JOptionPane.showMessageDialog(null, "PDF generado correctamente:\n" + ruta);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
    }

    // ----- CREA UNA TARJETA (CARD) -----
    private static PdfPTable crearCard(String tituloCard) {
        PdfPTable t = new PdfPTable(1);
        t.setWidthPercentage(100);

        PdfPCell c = new PdfPCell(new Phrase(tituloCard, TITULO_CARD));
        c.setBackgroundColor(new BaseColor(230, 230, 230));
        c.setPadding(10);
        c.setBorder(Rectangle.NO_BORDER);

        t.addCell(c);
        return t;
    }

    // ----- CREA PÁRRAFOS -----
    private static Paragraph crearPar(String texto) {
        Paragraph p = new Paragraph(texto, TEXTO);
        p.setSpacingBefore(3);
        return p;
    }

}
