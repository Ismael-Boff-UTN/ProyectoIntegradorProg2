package com.sky.pi.model;

import com.sky.pi.dao.MateriaDAO;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class Materia {

    private int codMateria;
    private String nombreMateria;
    private int dniProfesor;
    private MateriaDAO materiaDAO = new MateriaDAO();

    public Materia() {
    }

    public int getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(int codMateria) {
        this.codMateria = codMateria;
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

    public MateriaDAO getMateriaDAO() {
        return materiaDAO;
    }

    public void setMateriaDAO(MateriaDAO materiaDAO) {
        this.materiaDAO = materiaDAO;
    }

//CRUD
    //CREATE
    public boolean createMateria(Materia materia) {
        return materiaDAO.create(materia);
    }

    //READ
    public List<Materia> readMaterias() {
        return materiaDAO.read();
    }

    //UPDATE
    public boolean updateMateria(Materia materia) {
        return materiaDAO.update(materia);
    }

    //DELETE
    public boolean deleteMateria(int idMateria) {
        return materiaDAO.delete(idMateria);
    }

    public boolean materiaExist(int codMate) {
        return materiaDAO.exist(codMate);
    }
}
