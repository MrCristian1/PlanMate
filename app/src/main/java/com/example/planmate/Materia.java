package com.example.planmate;

public class Materia {

    private String nombreMateria;
    private String nombreProfesor;
    private String correoProfesor;
    private String nrcMateria;

    public Materia() {
        // Constructor vacío requerido por Firebase
    }

    public Materia(String nombreMateria, String nombreProfesor, String correoProfesor, String nrcMateria) {
        this.nombreMateria = nombreMateria;
        this.nombreProfesor = nombreProfesor;
        this.correoProfesor = correoProfesor;
        this.nrcMateria = nrcMateria;
    }

    // Getters y setters (opcional) para acceder a los campos
    // Puedes generarlos automáticamente en Android Studio utilizando Alt + Insert

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getCorreoProfesor() {
        return correoProfesor;
    }

    public void setCorreoProfesor(String correoProfesor) {
        this.correoProfesor = correoProfesor;
    }

    public String getNrcMateria() {
        return nrcMateria;
    }

    public void setNrcMateria(String nrcMateria) {
        this.nrcMateria = nrcMateria;
    }
}


