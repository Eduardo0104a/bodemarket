package dao;

import db.DBConnection;
import dto.MovimientoInventario;

import java.util.Date;
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
public class MovimientoInventarioDAO {
    
    public List<MovimientoInventario> obtenerMovimientosInventario() {
        List<MovimientoInventario> movimientos = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection(); 
            conn = conexionSQL.getConnection(); 

            String query = "{call ObtenerMovimientosInventario()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idMovimiento = rs.getInt("ID_Movimiento");
                int idInventario = rs.getInt("ID_Inventario");
                String tipo = rs.getString("Tipo");
                double cantidad = rs.getDouble("Cantidad");
                Date fecha = rs.getDate("Fecha");
                String descripcion = rs.getString("Descripcion");

                MovimientoInventario movimiento = new MovimientoInventario(idMovimiento, idInventario, tipo, cantidad, fecha, descripcion);
                movimientos.add(movimiento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs); 
        }

        return movimientos;
    }
}

