package com.sky.pi.dao;

import static com.sky.pi.dao.Conexion.getConnection;

import com.sky.pi.model.Profesor;
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
public class ProfesorDAO extends Conexion {

    private final String SQL_INSERT = "INSERT INTO profesor (prof_dni, prof_nombre,prof_apellido,prof_fec_nac,prof_domicilio, prof_telefono) VALUES (?,?,?,?,?,?)";
    private final String SQL_SELECT = "SELECT * FROM profesor";
    private final String SQL_DELETE = "DELETE FROM profesor WHERE prof_id=?";
    private final String SQL_UPDATE = "UPDATE profesor SET prof_dni = ?, prof_nombre=?,prof_apellido=?,prof_fec_nac=?,prof_domicilio=?, prof_telefono=? WHERE prof_id=?";

    public boolean create(Profesor profesor) {
        PreparedStatement ps = null;
        /*La interfaz PrepareStatement hereda de la interfaz Statement y puede almacenar
        de manera precompilada una sentencia SQL, por lo que es una opción más eficiente
        si es que necesitamos ejecutar una sentencia SQL en repetidas ocasiones, a su vez
        también podemos especificar parámetros a ejecutar en nuestro query.*/
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setInt(1, profesor.getDni());
            ps.setString(2, profesor.getNombre());
            ps.setString(3, profesor.getApellido());
            ps.setDate(4, profesor.getFechaNacimiento());
            ps.setString(5, profesor.getDomicilio());
            ps.setInt(6, profesor.getTelefono());

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

    public List<Profesor> read() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Profesor profesor;
        List<Profesor> listaProfesores = new ArrayList<>();

        try {
            conn = Conexion.getConnection();//Me conecto a la base de datos
            ps = getConnection().prepareStatement(SQL_SELECT);//ejecuto la query
            rs = ps.executeQuery();//igualo la respuesta de la query a un resultSet


            /*El método de executeQuery se utiliza para ejecutar sentencias de tipo select y por
            ello el método regresa un objeto ResultSet, el cual almacena el resultado en forma
            de una matriz de dos dimensiones, es decir, en renglones y columnas, así podemos
            procesar el resultado del query sin ningún problema. */
            while (rs.next()) {
                profesor = new Profesor();
                profesor.setId(rs.getInt(1));
                profesor.setDni(rs.getInt(2));
                profesor.setNombre(rs.getString(3));
                profesor.setApellido(rs.getString(4));
                profesor.setFechaNacimiento(rs.getDate(5));
                profesor.setDomicilio(rs.getString(6));
                profesor.setTelefono(rs.getInt(7));

                listaProfesores.add(profesor);

            }

        } catch (SQLException e) {

            System.out.println(e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
        return listaProfesores;
    }

    public boolean update(Profesor profesor) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setInt(1, profesor.getDni());
            ps.setString(2, profesor.getNombre());
            ps.setString(3, profesor.getApellido());
            ps.setDate(4, profesor.getFechaNacimiento());
            ps.setString(5, profesor.getDomicilio());
            ps.setInt(6, profesor.getTelefono());

            ps.setInt(7, profesor.getId());
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

    public boolean delete(int idProf) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idProf);
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
