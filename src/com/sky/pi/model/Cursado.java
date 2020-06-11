package com.sky.pi.model;

import com.sky.pi.dao.CursadoDAO;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class Cursado {

    private int alumnoDni;
    private int codigoMateria;
    private double nota;
    private CursadoDAO cursadoDAO = new CursadoDAO();

    public Cursado() {
    }

    public Cursado(int alumnoDni, int codigoMateria, double nota) {
        this.alumnoDni = alumnoDni;
        this.codigoMateria = codigoMateria;
        this.nota = nota;
    }

    public int getAlumnoDni() {
        return alumnoDni;
    }

    public void setAlumnoDni(int alumnoDni) {
        this.alumnoDni = alumnoDni;
    }

    public int getCodigoMateria() {
        return codigoMateria;
    }

    public void setCodigoMateria(int codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    //CRUD
    //CREATE
    public boolean createCursado(Cursado cursado) {
        return cursadoDAO.create(cursado);
    }

    //READ
    public List<Cursado> readCursado() {
        return cursadoDAO.read();
    }

    //UPDATE
    public boolean updateCursado(Cursado cursado) {
        return cursadoDAO.update(cursado);
    }

    //DELETE
    public boolean deleteCursado(int idCursado) {
        return cursadoDAO.delete(idCursado);
    }

    //BUSCAR
    public Cursado findCursado(int idCursado) {
        return cursadoDAO.find(idCursado);
    }

}
