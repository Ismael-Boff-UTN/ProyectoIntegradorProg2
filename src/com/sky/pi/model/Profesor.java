package com.sky.pi.model;

import com.sky.pi.dao.ProfesorDAO;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class Profesor {

    private int dni;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String domicilio;
    private int telefono;
    private ProfesorDAO profesorDAO = new ProfesorDAO();

    public Profesor() {
    }

    public Profesor(int dni, String nombre, String apellido, Date fechaNacimiento, String domicilio, int telefono) {

        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public Profesor(String nombre, String apellido, Date fechaNacimiento, String domicilio, int telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    //CRUD
    //CREATE
    public boolean createProfesor(Profesor profesor) {
        return profesorDAO.create(profesor);
    }

    //READ
    public List<Profesor> readProfesores() {
        return profesorDAO.read();
    }

    //UPDATE
    public boolean updateProfesores(Profesor profesor) {
        return profesorDAO.update(profesor);
    }

    //DELETE
    public boolean deleteProfesor(int idProfesor) {
        return profesorDAO.delete(idProfesor);
    }
}
