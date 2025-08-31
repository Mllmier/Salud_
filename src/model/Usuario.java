package model;

public class Usuario {
    private String email;
    private String password;
    private Rol role;
    private Persona persona; 

    public Usuario(String email, String password, Rol role, Persona persona) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.persona = persona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    

}