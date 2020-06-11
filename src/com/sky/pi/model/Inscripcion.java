package com.sky.pi.model;

import com.sky.pi.dao.InscripcionDAO;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class Inscripcion {

    private int codigoInscripcion; //ID
    private String nombre;
    private Date fecha;
    private int codigoCarrera;
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();

    public Inscripcion() {
    }

    public Inscripcion(int codigoInscripcion, String nombre, Date fecha, int codigoCarrera) {
        this.codigoInscripcion = codigoInscripcion;
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

    public int getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(int codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    //CRUD
    //CREATE
    public boolean createInscripcion(Inscripcion inscripcion) {
        return inscripcionDAO.create(inscripcion);
    }

    //READ
    public List<Inscripcion> readInscripciones() {
        return inscripcionDAO.read();
    }

    //UPDATE
    public boolean updateInscripcion(Inscripcion inscripcion) {
        return inscripcionDAO.update(inscripcion);
    }

    //DELETE
    public boolean deleteInscripcion(int idInscripcion) {
        return inscripcionDAO.delete(idInscripcion);
    }

}
