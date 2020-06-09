
package com.sky.pi.model;

/**
 *
 * @author SkylakeFrost
 */
public class Cursado {
    private int alumnoDni;
    private int codigoMateria;
    private double nota;

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
    
    
}
