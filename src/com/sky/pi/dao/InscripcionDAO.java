/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sky.pi.dao;

import static com.sky.pi.dao.Conexion.getConnection;

import com.sky.pi.model.Inscripcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SkylakeFrost
 */
public class InscripcionDAO extends Conexion {

    private final String SQL_INSERT = "INSERT INTO inscripcion (insc_nombre, insc_fecha, insc_car_cod) VALUES (?,?,?)";
    private final String SQL_SELECT = "SELECT * FROM inscripcion";
    private final String SQL_DELETE = "DELETE FROM inscripcion WHERE insc_cod=?";
    private final String SQL_UPDATE = "UPDATE inscripcion SET insc_nombre=?,insc_fecha=?, insc_car_cod WHERE insc_cod=?";

    public boolean create(Inscripcion inscripcion) {
        PreparedStatement ps = null;
        /*La interfaz PrepareStatement hereda de la interfaz Statement y puede almacenar
        de manera precompilada una sentencia SQL, por lo que es una opción más eficiente
        si es que necesitamos ejecutar una sentencia SQL en repetidas ocasiones, a su vez
        también podemos especificar parámetros a ejecutar en nuestro query.*/
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setString(1, inscripcion.getNombre());
            ps.setDate(2, inscripcion.getFecha());
            ps.setString(3, inscripcion.getCodigoCarrera());

            ps.executeUpdate();
            /*El método executeUpdate se utiliza para ejecutar sentencias DML (Data
            Manipulation Language) como son las sentencias insert, update y delete. También
            nos va a permitir ejecutar sentencias de tipo DDL (Data Definition Language) como
            son las sentencias create table, truncate table, entre otras. La función
            executeUpdate regresa un entero, indicando el número de registros afectados como
            resultado de ejecutar el query deseado.
             */

            System.out.println("Agregado Con Exito");

            return true;
        } catch (SQLException e) {
            System.out.println("Error al Crear : " + e);
            return false;

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
        }
    }

    public List<Inscripcion> read() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Inscripcion inscripcion;
        List<Inscripcion> listaInscripciones = new ArrayList<>();

        try {
            conn = Conexion.getConnection();//Me conecto a la base de datos
            ps = getConnection().prepareStatement(SQL_SELECT);//ejecuto la query
            rs = ps.executeQuery();//igualo la respuesta de la query a un resultSet


            /*El método de executeQuery se utiliza para ejecutar sentencias de tipo select y por
            ello el método regresa un objeto ResultSet, el cual almacena el resultado en forma
            de una matriz de dos dimensiones, es decir, en renglones y columnas, así podemos
            procesar el resultado del query sin ningún problema. */
            while (rs.next()) {
                inscripcion = new Inscripcion();

                inscripcion.setCodigoInscripcion(rs.getInt(1));
                inscripcion.setNombre(rs.getString(2));
                inscripcion.setFecha(rs.getDate(3));
                inscripcion.setCodigoCarrera(rs.getString(4));

                listaInscripciones.add(inscripcion);

            }

        } catch (SQLException e) {

            System.out.println(e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
        return listaInscripciones;
    }

    public boolean update(Inscripcion inscripcion) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setString(1, inscripcion.getNombre());
            ps.setDate(2, inscripcion.getFecha());
            ps.setString(3, inscripcion.getCodigoCarrera());

            ps.setInt(4, inscripcion.getCodigoInscripcion());
            ps.executeUpdate();
            System.out.println("Actualizado Con Exito");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al Actualizar : " + e);
            return false;

        } finally {

            Conexion.close(conn);
            Conexion.close(ps);

        }
    }

    public boolean delete(int codInscripcion) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, codInscripcion);
            ps.executeUpdate();
            System.out.println("Eliminado con Exito");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al Eliminar : " + e);
            return false;

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);

        }
    }
}
