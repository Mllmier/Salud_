package Utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.OrdenMedica;

public class PdfPacienteGenerator {

    private static final Font TITULO_PRINCIPAL =
            new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, BaseColor.WHITE);

    private static final Font TITULO_CARD =
            new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

    private static final Font TEXTO =
            new Font(Font.FontFamily.HELVETICA, 12);

    private static final BaseColor COLOR_CARD = new BaseColor(230, 230, 230);
    private static final BaseColor COLOR_HEADER = new BaseColor(33, 150, 243);

    public static void generarOrdenMedica(OrdenMedica orden, String rutaDestino) throws Exception {

        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Guardar Orden Médica");
            chooser.setSelectedFile(new File("OrdenMedica_" + orden.getIdCita() + ".pdf"));

            if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "Se canceló la descarga del PDF.");
                return;
            }

            String ruta = chooser.getSelectedFile().getAbsolutePath();
            if (!ruta.endsWith(".pdf")) ruta += ".pdf";

            Document doc = new Document(PageSize.A4, 40, 40, 50, 50);
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            PdfPTable encabezado = new PdfPTable(1);
            encabezado.setWidthPercentage(100);

            PdfPCell titulo = new PdfPCell(new Phrase("ORDEN MÉDICA", TITULO_PRINCIPAL));
            titulo.setBackgroundColor(COLOR_HEADER);
            titulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            titulo.setPadding(18);
            titulo.setBorder(Rectangle.NO_BORDER);

            encabezado.addCell(titulo);
            doc.add(encabezado);
            doc.add(new Paragraph("\n"));

           
            doc.add(crearCard("DATOS DEL PACIENTE"));
            doc.add(crearPar("Nombre: " + orden.getNombre()));
            doc.add(crearPar("Apellido: " + orden.getApellido()));
            doc.add(crearPar("Email: " + orden.getEmail()));
            doc.add(crearPar("Celular: " + orden.getCelular()));
            doc.add(crearPar("Fecha de nacimiento: " + orden.getFechaNacimiento()));
            doc.add(crearPar("Sexo: " + orden.getSexo()));
            doc.add(crearPar("EPS: " + orden.getEps()));
            doc.add(crearPar("Tipo de sangre: " + orden.getTipoSangre()));
            doc.add(crearPar("Altura: " + orden.getAltura() + " m"));
            doc.add(crearPar("Peso: " + orden.getPeso() + " kg"));
            agregarSeparador(doc);

           
            doc.add(crearCard("INFORMACIÓN DE LA CITA"));
            doc.add(crearPar("ID Cita: " + orden.getIdCita()));
            doc.add(crearPar("Fecha: " + orden.getFecha()));
            doc.add(crearPar("Hora: " + orden.getHora()));
            doc.add(crearPar("Sede: " + orden.getMotivo()));
            doc.add(crearPar("Motivo: " + orden.getSede()));
            doc.add(crearPar("Estado: " + orden.getEstado()));
            agregarSeparador(doc);

            
            doc.add(crearCard("DATOS DEL MÉDICO"));
            doc.add(crearPar("Nombre: " + orden.getNombreMedico()));
            doc.add(crearPar("Apellido: " + orden.getApellidoMedico()));
            doc.add(crearPar("Especialidad: " + orden.getEspecialidadMedico()));
            agregarSeparador(doc);

            doc.add(crearCard("DIAGNÓSTICO"));
            doc.add(crearPar(orden.getDiagnostico()));
            agregarSeparador(doc);

          
            doc.add(crearCard("EXAMEN SOLICITADO"));
            doc.add(crearPar("Examen ordenado: " + orden.getExamen()));
            agregarSeparador(doc);

         
            doc.add(crearCard("RECETA"));
            doc.add(crearPar(orden.getReceta()));
            agregarSeparador(doc);

           
            doc.add(crearCard("MEDICAMENTOS FORMULADOS"));

            if (orden.getAreamedicamentos() != null && !orden.getAreamedicamentos().isEmpty()) {
                for (String m : orden.getAreamedicamentos()) {
                    doc.add(crearPar("• " + m));
                }
            } else {
                doc.add(crearPar("No se registraron medicamentos."));
            }

            doc.close();
            JOptionPane.showMessageDialog(null, "PDF generado correctamente:\n" + ruta);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR: " + e.getMessage());
        }
    }

    private static PdfPTable crearCard(String tituloCard) {
        PdfPTable t = new PdfPTable(1);
        t.setWidthPercentage(100);

        PdfPCell c = new PdfPCell(new Phrase(tituloCard, TITULO_CARD));
        c.setBackgroundColor(COLOR_CARD);
        c.setPadding(10);
        c.setBorder(Rectangle.NO_BORDER);

        t.addCell(c);
        return t;
    }

    private static Paragraph crearPar(String texto) {
        Paragraph p = new Paragraph(texto, TEXTO);
        p.setSpacingBefore(4);
        return p;
    }

    private static void agregarSeparador(Document doc) throws DocumentException {
        LineSeparator sep = new LineSeparator();
        sep.setPercentage(100);
        sep.setLineWidth(1f);
        sep.setLineColor(new BaseColor(200, 200, 200));

        doc.add(new Chunk(sep));
        doc.add(new Paragraph("\n"));
    }
}