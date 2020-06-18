package com.sky.pi.model;

import com.sky.pi.dao.AlumnoDAO;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class Alumno {

    private int dni;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String domicilio;
    private String telefono;
    private int codigoInscripcion;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();

    public Alumno() {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCodigoInscripcion() {
        return codigoInscripcion;
    }

    public void setCodigoInscripcion(int codigoInscripcion) {
        this.codigoInscripcion = codigoInscripcion;
    }

    //CRUD
    //CREATE
    public boolean createAlumno(Alumno alumno) {
        return alumnoDAO.create(alumno);
    }

    //READ
    public List<Alumno> readlumnos() {
        return alumnoDAO.read();
    }

    //UPDATE
    public boolean updateAlumno(Alumno alumno) {
        return alumnoDAO.update(alumno);
    }

    //DELETE
    public boolean deleteAlumno(int idAlumno) {
        return alumnoDAO.delete(idAlumno);
    }

    public Alumno findAlumno(int dniAlumno) {
        return alumnoDAO.find(dniAlumno);
    }

    public boolean updateCarreraAlumno(Alumno alumno) {
        return alumnoDAO.updateCarrera(alumno);
    }

    public boolean alumnoExist(int dniAlumno) {
        return alumnoDAO.exist(dniAlumno);
    }

}
