package dao;

import dto.MovimientoInventario;
import dto.Producto;

import db.DBConnection;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.util.Date;
/**
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

            String query = "{call sp_obtener_movimientos_inventario()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idMovimiento = rs.getInt("id_movimiento");
                int idInventario = rs.getInt("id_inventario");
                int idProducto = rs.getInt("id_producto");
                String tipo = rs.getString("tipo");
                int cantidad = rs.getInt("cantidad");
                Date fecha = rs.getDate("fecha");
                String nombreProducto = rs.getString("nombreProducto");
                String descripcion = rs.getString("descripcion");

                MovimientoInventario movimiento = new MovimientoInventario(idMovimiento, idInventario, idProducto, tipo, cantidad, fecha, descripcion);
                movimiento.setNombreProducto(nombreProducto);
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
