package com.sky.pi.dao;

import static com.sky.pi.dao.Conexion.getConnection;
import com.sky.pi.model.Carrera;
import com.sky.pi.model.Materia;
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
public class MateriaDAO extends Conexion {

    private final String SQL_INSERT = "INSERT INTO materia (mat_nombre,mat_prof_dni, mat_prof_nombre) VALUES (?,?,?)";
    private final String SQL_SELECT = "SELECT * FROM materia";
    private final String SQL_DELETE = "DELETE FROM materia WHERE mate_cod=?";
    private final String SQL_UPDATE = "UPDATE materia SET mat_nombre =?,mat_prof_dni=?, mat_prof_nombre=? WHERE mate_cod=?";

    public boolean create(Materia materia) {
        PreparedStatement ps = null;
        /*La interfaz PrepareStatement hereda de la interfaz Statement y puede almacenar
        de manera precompilada una sentencia SQL, por lo que es una opción más eficiente
        si es que necesitamos ejecutar una sentencia SQL en repetidas ocasiones, a su vez
        también podemos especificar parámetros a ejecutar en nuestro query.*/
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setString(1, materia.getNombreMateria());
            ps.setInt(2, materia.getDniProfesor());
            ps.setString(3, materia.getNombreProfesor());

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

    public List<Materia> read() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Materia materia;
        List<Materia> listaMaterias = new ArrayList<>();

        try {
            conn = Conexion.getConnection();//Me conecto a la base de datos
            ps = getConnection().prepareStatement(SQL_SELECT);//ejecuto la query
            rs = ps.executeQuery();//igualo la respuesta de la query a un resultSet


            /*El método de executeQuery se utiliza para ejecutar sentencias de tipo select y por
            ello el método regresa un objeto ResultSet, el cual almacena el resultado en forma
            de una matriz de dos dimensiones, es decir, en renglones y columnas, así podemos
            procesar el resultado del query sin ningún problema. */
            while (rs.next()) {
                materia = new Materia();

                materia.setIdMateria(rs.getInt(1));
                materia.setNombreMateria(rs.getString(1));
                materia.setDniProfesor(rs.getInt(2));
                materia.setNombreProfesor(rs.getString(3));

                listaMaterias.add(materia);

            }

        } catch (SQLException e) {

            System.out.println(e);

        } finally {
            Conexion.close(conn);
            Conexion.close(ps);
            Conexion.close(rs);
        }
        return listaMaterias;
    }

    public boolean update(Materia materia) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_UPDATE);

            ps.setString(1, materia.getNombreMateria());
            ps.setInt(2, materia.getDniProfesor());
            ps.setString(3, materia.getNombreProfesor());

            ps.setInt(4, materia.getIdMateria());
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

    public boolean delete(int idMateria) {
        PreparedStatement ps = null;
        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            ps = conn.prepareStatement(SQL_DELETE);
            ps.setInt(1, idMateria);
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
