package Utilidades;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import javax.swing.JOptionPane;

public class EnviadorCredenciales {
    private static EnviadorCredenciales instancia;
    private final String username = "haider32108@gmail.com";
    private final String password = "ulbfggqhnouyrwmc";
    private final Properties props;
    
    private EnviadorCredenciales() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    }
    
    public static synchronized EnviadorCredenciales getInstancia() {
        if (instancia == null) {
            instancia = new EnviadorCredenciales();
        }
        return instancia;
    }
    
    public boolean enviarCredenciales(String destinatario, String nombre, String contrasena) {
        try {
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Credenciales de acceso al Sistema Médico");
            
            String cuerpoHtml = construirCuerpoCorreo(nombre, destinatario, contrasena);
            message.setContent(cuerpoHtml, "text/html; charset=utf-8");
            
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null,
                "Error al enviar correo: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private String construirCuerpoCorreo(String nombre, String destinatario, String contrasena) {
        // Escapar caracteres especiales para HTML
        String nombreEscapado = escapeHtml(nombre);
        String correoEscapado = escapeHtml(destinatario);
        String contrasenaEscapada = escapeHtml(contrasena);
        
        return "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<meta charset='UTF-8'>"
            + "<style>"
            + "  body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: 0 auto; padding: 20px; }"
            + "  .header { background-color: #2c3e50; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }"
            + "  .header h1 { color: #fff; margin: 0; }"
            + "  .content { padding: 20px; background-color: #f9f9f9; border-radius: 0 0 5px 5px; }"
            + "  .credentials { background-color: #fff; border: 1px solid #ddd; border-radius: 5px; padding: 15px; margin: 20px 0; }"
            + "  .credential-row { display: flex; margin-bottom: 10px; }"
            + "  .credential-label { font-weight: bold; width: 120px; }"
            + "  .password { font-family: 'Courier New', monospace; font-size: 16px; color: #e74c3c; }"
            + "  .warning { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 5px; margin: 20px 0; }"
            + "  .footer { text-align: center; margin-top: 20px; font-size: 12px; color: #7f8c8d; }"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<div class='header'>"
            + "  <h1>Sistema Médico</h1>"
            + "</div>"
            + "<div class='content'>"
            + "  <h2 style='color: #2c3e50; margin-top: 0;'>¡Bienvenido, " + nombreEscapado + "!</h2>"
            + "  <p>Sus credenciales de acceso al sistema son:</p>"
            + "  <div class='credentials'>"
            + "    <div class='credential-row'>"
            + "      <div class='credential-label'>Correo:</div>"
            + "      <div>" + correoEscapado + "</div>"
            + "    </div>"
            + "    <div class='credential-row'>"
            + "      <div class='credential-label'>Contraseña:</div>"
            + "      <div class='password'>" + contrasenaEscapada + "</div>"
            + "    </div>"
            + "  </div>"
            + "  <div class='warning'>"
            + "    <strong>Importante:</strong> Por seguridad, cambie esta contraseña después de su primer acceso al sistema."
            + "    <p>Este es un mensaje automático, por favor no responda a este correo.</p>"
            + "    <p>&copy; 2025 Sistema Médico. Todos los derechos reservados.</p>"
            + "  </div>"
            + "</div>"
            + "</body>"
            + "</html>";
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}