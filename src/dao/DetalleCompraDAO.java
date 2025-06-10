package dao;

import dto.DetalleCompra;
import db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
/**
 *
 * @author EduardoPC
 */
public class DetalleCompraDAO {

    public int insertar(DetalleCompra detalle) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_registrar_detalle_compra(?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, detalle.getIdCompra());
            stmt.setInt(2, detalle.getIdProducto());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setDouble(4, detalle.getPrecioCompra());
            stmt.registerOutParameter(5, java.sql.Types.INTEGER);

            stmt.executeUpdate();
            errorCode = stmt.getInt(5);

            if (errorCode != 0) {
                System.out.println("Error al registrar el detalle de compra.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }
}
