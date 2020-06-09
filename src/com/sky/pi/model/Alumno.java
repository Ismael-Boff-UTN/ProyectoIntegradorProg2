package com.sky.pi.model;

import java.sql.Date;

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

    public Alumno(int dni, String nombre, String apellido, Date fechaNacimiento, String domicilio, int telefono, String codigoInscripcion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.domicilio = domicilio;
        this.telefono = telefono;
        this.codigoInscripcion = codigoInscripcion;
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

}
