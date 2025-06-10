package dao;

import dto.Compra;

import db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;

/**
 *
 * @author EduardoPC
 */
public class CompraDAO {

    public int insertar(Compra compra) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_registrar_compra(?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, compra.getIdProveedor());
            stmt.setDate(2, new java.sql.Date(compra.getFecha().getTime()));
            stmt.setDouble(3, compra.getTotal());
            stmt.registerOutParameter(4, java.sql.Types.INTEGER);

            stmt.executeUpdate();

            int idGenerado = stmt.getInt(4); // ✅ Corrección aquí
            compra.setIdCompra(idGenerado);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }
}
