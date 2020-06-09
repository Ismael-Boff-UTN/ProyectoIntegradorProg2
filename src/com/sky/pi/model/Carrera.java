package com.sky.pi.model;

/**
 *
 * @author SkylakeFrost
 */
public class Carrera {

    private int codigoCarrera;
    private String nombre;
    private String duracion;

    public Carrera() {
    }

    public Carrera(int codigoCarrera, String nombre, String duracion) {
        this.codigoCarrera = codigoCarrera;
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public int getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(int codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

}
