package com.sky.pi.dao;

import com.sky.pi.model.Alumno;
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
public class AlumnoDAO extends Conexion {

    private final String SQL_INSERT = "INSERT INTO alumno (alu_dni, alu_nombre,alu_apellido,alu_fec_nac,alu_domicilio, alu_telefono) VALUES (?,?,?,?,?,?)";
    private final String SQL_SELECT = "SELECT * FROM alumno";
    private final String SQL_DELETE = "DELETE FROM alumno WHERE alu_dni=?";
    private final String SQL_UPDATE = "UPDATE alumno SET alu_nombre=?,alu_apellido=?,alu_fec_nac=?, alu_domicilio=?,alu_telefono=? WHERE alu_dni=?";
    private final String SQL_UPDATE_CARRERA = "UPDATE alumno SET alu_insc_cod=? WHERE alu_dni=?";
    private final String SQL_FIND = "SELECT * FROM alumno WHERE alu_dni=?";

    public boolean create(Alumno alumno) {
        PreparedStatement ps = null;
        /*La interfaz PrepareStatement hereda de la interfaz Statement y puede almacenar
        de manera precompilada una sentencia SQL, por lo que es una opción más eficiente
        si es que necesitamos ejecutar una sentencia SQL en repetidas ocasiones, a su vez
        también podemos especificar parámetros a ejecutar en nuestro query.*/
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getNombre());
            ps.setString(3, alumno.getApellido());
            ps.setDate(4, alumno.getFechaNacimiento());
            ps.setString(5, alumno.getDomicilio());
            ps.setString(6, alumno.getTelefono());

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

    public List<Alumno> read() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Alumno alumno;
        List<Alumno> listaAlumnos = new ArrayList<>();

        try {
            conn = Conexion.getConnection();//Me conecto a la base de datos
            ps = getConnection().prepareStatement(SQL_SELECT);//ejecuto la query
            rs = ps.executeQuery();//igualo la respuesta de la query a un resultSet


            /*El método de executeQuery se utiliza para ejecutar sentencias de tipo select y por
            ello el método regresa un objeto ResultSet, el cual almacena el resultado en forma
            de una matriz de dos dimensiones, es decir, en renglones y columnas, así podemos
            procesar el resultado del query sin ningún problema. */
            while (rs.next()) {
                alumno = new Alumno();

                alumno.setDni(rs.getInt(1));
                alumno.setNombre(rs.getString(2));
                alumno.setApellido(rs.getString(3));
                alumno.setFechaNacimiento(rs.getDate(4));
                alumno.setDomicilio(rs.getString(5));
                alumno.setTelefono(rs.getString(6));
                alumno.setCodigoInscripcion(rs.getInt(7));

                listaAlumnos.add(alumno);

            }

        } catch (SQLException e) {

            System.out.println(e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
        return listaAlumnos;
    }

    public boolean update(Alumno alumno) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setDate(3, alumno.getFechaNacimiento());
            ps.setString(4, alumno.getDomicilio());
            ps.setString(5, alumno.getTelefono());

            ps.setInt(6, alumno.getDni());
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

    public boolean updateCarrera(Alumno alumno) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE_CARRERA);

//          ps.setInt(1, alumno.getDni());
//          ps.setString(2, alumno.getNombre());
//          ps.setString(3, alumno.getApellido());
//          ps.setDate(4, alumno.getFechaNacimiento());
//          ps.setString(5, alumno.getDomicilio());
//          ps.setInt(6, alumno.getTelefono());
            ps.setInt(1, alumno.getCodigoInscripcion());

            ps.setInt(2, alumno.getDni());
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

    public boolean delete(int idAlumno) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idAlumno);
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

    public Alumno find(int dniAlumno) {

        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        Alumno alumno = new Alumno();
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_FIND);
            ps.setInt(1, dniAlumno);
            rs = ps.executeQuery();

            while (rs.next()) {
                alumno.setDni(rs.getInt(1));
                alumno.setNombre(rs.getString(2));
                alumno.setApellido(rs.getString(3));
                alumno.setFechaNacimiento(rs.getDate(4));
                alumno.setDomicilio(rs.getString(5));
                alumno.setTelefono(rs.getString(6));
                alumno.setCodigoInscripcion(rs.getInt(7));
            }

        } catch (SQLException e) {
            System.out.println("Error al Buscar : " + e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);

        }

        return alumno;
    }

    public boolean exist(int dniAlumno) {

        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_FIND);
            ps.setInt(1, dniAlumno);
            rs = ps.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al Buscar : " + e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);

        }

        return false;
    }

}
