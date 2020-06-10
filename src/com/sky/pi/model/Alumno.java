package com.sky.pi.model;

import com.sky.pi.dao.AlumnoDAO;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class Alumno {

    private int id;
    private int dni;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String domicilio;
    private int telefono;
    private String codigoInscripcion;
    private AlumnoDAO alumnoDAO = new AlumnoDAO();

    public Alumno(int dni, String nombre, String apellido, Date fechaNacimiento, String domicilio, int telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
       
    }

    public Alumno(String codigoInscripcion, int id) {
        this.codigoInscripcion = codigoInscripcion;
        this.id = id;
    }

    public Alumno() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCodigoInscripcion() {
        return codigoInscripcion;
    }

    public void setCodigoInscripcion(String codigoInscripcion) {
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

}
