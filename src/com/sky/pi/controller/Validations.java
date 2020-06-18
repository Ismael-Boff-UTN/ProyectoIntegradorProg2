package com.sky.pi.controller;

/**
 *
 * @author SkylakeFrost
 */
public class Validations {

    //Validaciones Que Se Usan Para Varios Modelos, Alumno, Profesor, etc
    public boolean verificarDni(String dni) {
        //DNI Debe Contener 8 Caracteres, Ni Mas Ni Menos!
        if (dni.length() > 8 || dni.length() < 8) {
            return false;
        } else {
            return true;
        }
    }

    public boolean dniContieneLetras(String dni) {

        try {
            Integer.parseInt(dni);
            return true;//Si El DNI No Contiene Letras Retorna True
        } catch (NumberFormatException e) {
            return false;//Lo Contrario
        }
    }

    public boolean verificarTelefono(String tel) {
        //El Telefono No Puede Tener Mas De 12 Caracteres Alfanumericos
        //Por Ej. +2617684576
        if (tel.length() > 12) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validarNota(double nota) {
        if (nota >= 1.0 && nota <= 10.0) {

            return true;
        } else {
            return false;
        }

    }

    public boolean notaContieneLetras(String nota) {
        try {
            System.out.println("nota "+nota);
            Double.parseDouble(nota);
            return true;//Si La Nota No Contiene Letras Retorna True
        } catch (NumberFormatException e) {
            return false;//Lo Contrario
        }
    }
}
