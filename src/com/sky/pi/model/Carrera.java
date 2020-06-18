package com.sky.pi.model;

import com.sky.pi.dao.CarreraDAO;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class Carrera {

    private int codigoCarrera;
    private String nombre;
    private String duracion;
    private CarreraDAO carreraDAO = new CarreraDAO();

    public Carrera() {
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

    //CRUD
    //CREATE
    public boolean createCarrera(Carrera carrera) {
        return carreraDAO.create(carrera);
    }

    //READ
    public List<Carrera> readCarrera() {
        return carreraDAO.read();
    }

    //UPDATE
    public boolean updateCarrera(Carrera carrera) {
        return carreraDAO.update(carrera);
    }

    //DELETE
    public boolean deleteCarrera(int idCarrera) {
        return carreraDAO.delete(idCarrera);
    }

    public Carrera findCarrera(int idCarrera) {
        return carreraDAO.find(idCarrera);
    }

    public boolean carreraExist(int codCarrera) {
        return carreraDAO.exist(codCarrera);
    }
}
