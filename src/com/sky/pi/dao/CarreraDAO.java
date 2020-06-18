package com.sky.pi.dao;

import static com.sky.pi.dao.Conexion.getConnection;
import com.sky.pi.model.Carrera;
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
public class CarreraDAO extends Conexion {

    private final String SQL_INSERT = "INSERT INTO carrera (car_cod, car_nombre,car_duracion) VALUES (?,?,?)";
    private final String SQL_SELECT = "SELECT * FROM carrera";
    private final String SQL_DELETE = "DELETE FROM carrera WHERE car_cod=?";
    private final String SQL_UPDATE = "UPDATE carrera SET car_nombre=?,car_duracion=? WHERE car_cod=?";
    private final String SQL_FIND = "SELECT * FROM carrera WHERE car_cod=?";

    public boolean create(Carrera carrera) {
        PreparedStatement ps = null;
        /*La interfaz PrepareStatement hereda de la interfaz Statement y puede almacenar
        de manera precompilada una sentencia SQL, por lo que es una opción más eficiente
        si es que necesitamos ejecutar una sentencia SQL en repetidas ocasiones, a su vez
        también podemos especificar parámetros a ejecutar en nuestro query.*/
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setInt(1, carrera.getCodigoCarrera());
            ps.setString(2, carrera.getNombre());
            ps.setString(3, carrera.getDuracion());

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

    public List<Carrera> read() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Carrera carrera;
        List<Carrera> listaCarreras = new ArrayList<>();

        try {
            conn = Conexion.getConnection();//Me conecto a la base de datos
            ps = getConnection().prepareStatement(SQL_SELECT);//ejecuto la query
            rs = ps.executeQuery();//igualo la respuesta de la query a un resultSet


            /*El método de executeQuery se utiliza para ejecutar sentencias de tipo select y por
            ello el método regresa un objeto ResultSet, el cual almacena el resultado en forma
            de una matriz de dos dimensiones, es decir, en renglones y columnas, así podemos
            procesar el resultado del query sin ningún problema. */
            while (rs.next()) {
                carrera = new Carrera();

                carrera.setCodigoCarrera(rs.getInt(1));
                carrera.setNombre(rs.getString(2));
                carrera.setDuracion(rs.getString(3));

                listaCarreras.add(carrera);

            }

        } catch (SQLException e) {

            System.out.println(e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
        return listaCarreras;
    }

    public boolean update(Carrera carrera) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setString(1, carrera.getNombre());
            ps.setString(2, carrera.getDuracion());

            ps.setInt(3, carrera.getCodigoCarrera());
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

    public boolean delete(int codCarrera) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, codCarrera);
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

    public Carrera find(int codCarrera) {

        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;
        Carrera carrera = new Carrera();
        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_FIND);
            ps.setInt(1, codCarrera);
            rs = ps.executeQuery();

            while (rs.next()) {
                carrera.setCodigoCarrera(rs.getInt(1));
                carrera.setNombre(rs.getString(2));
                carrera.setDuracion(rs.getString(3));
            }

        } catch (SQLException e) {
            System.out.println("Error al Buscar : " + e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);

        }

        return carrera;
    }

    public boolean exist(int codCarrera) {

        PreparedStatement ps = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_FIND);
            ps.setInt(1, codCarrera);
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
