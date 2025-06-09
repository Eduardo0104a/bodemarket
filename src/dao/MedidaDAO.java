package dao;

import db.DBConnection;
import dto.Medida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;

/**
 *
 * @author Eduardo
 */
public class MedidaDAO {

    public List<Medida> listar() {
        List<Medida> medidas = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_listar_medidas()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idMedida = rs.getInt("id_medida");
                String nombre = rs.getString("nombre");
                String nombreCorto = rs.getString("nombre_corto");

                Medida medida = new Medida(idMedida, nombre, nombreCorto);
                medidas.add(medida);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return medidas;
    }

    public int insertar(Medida medida) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_insertar_medida(?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);

            stmt.setString(1, medida.getNombre());
            stmt.setString(2, medida.getNombreCorto());

            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.registerOutParameter(4, java.sql.Types.INTEGER);

            stmt.executeUpdate();

            errorCode = stmt.getInt(4);

            if (errorCode == 0) {
                int idGenerado = stmt.getInt(3);
                medida.setIdMedida(idGenerado);
            } else {
                System.out.println("Error: La medida ya existe.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }

    public int modificar(Medida medida) {
        Connection conn = null;
        CallableStatement stmt = null;
        int resultado = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_modificar_medida(?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, medida.getIdMedida());
            stmt.setString(2, medida.getNombre());
            stmt.setString(3, medida.getNombreCorto());
            stmt.registerOutParameter(4, java.sql.Types.INTEGER);

            stmt.execute();
            resultado = stmt.getInt(4);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return resultado;
    }

    public int eliminar(int idMedida) {
        Connection conn = null;
        CallableStatement stmt = null;
        int result = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminar_medida(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idMedida);

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }

    public Medida obtenerMedidaPorId(int idMedida) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Medida medida = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtener_medida_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idMedida);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String nombreCorto = rs.getString("nombre_corto");

                medida = new Medida(idMedida, nombre, nombreCorto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return medida;
    }

}
