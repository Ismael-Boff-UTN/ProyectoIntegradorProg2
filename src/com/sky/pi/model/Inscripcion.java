package com.sky.pi.model;

import java.sql.Date;

/**
 *
 * @author SkylakeFrost
 */
public class Inscripcion {

    private int codigoInscripcion; //ID
    private String nombre;
    private Date fecha;
    private String codigoCarrera;

    public Inscripcion() {
    }

    public Inscripcion(String nombre, Date fecha, String codigoCarrera) {
        
        this.nombre = nombre;
        this.fecha = fecha;
        this.codigoCarrera = codigoCarrera;
    }

    public int getCodigoInscripcion() {
        return codigoInscripcion;
    }

    public void setCodigoInscripcion(int codigoInscripcion) {
        this.codigoInscripcion = codigoInscripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(String codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

}
