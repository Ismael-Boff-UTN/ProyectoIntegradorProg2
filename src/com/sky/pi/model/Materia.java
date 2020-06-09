package com.sky.pi.model;

/**
 *
 * @author SkylakeFrost
 */
public class Materia {

    private int idMateria;
    private String nombreMateria;
    private int dniProfesor;
    private String nombreProfesor;

    public Materia(String nombreMateria, int dniProfesor, String nombreProfesor) {
        this.nombreMateria = nombreMateria;
        this.dniProfesor = dniProfesor;
        this.nombreProfesor = nombreProfesor;
    }

    public Materia() {
    }

    
    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getDniProfesor() {
        return dniProfesor;
    }

    public void setDniProfesor(int dniProfesor) {
        this.dniProfesor = dniProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

}
