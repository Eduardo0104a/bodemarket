
package dao;

import dto.DetalleVenta;
import dto.Producto;
import dto.Venta;

import db.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 *
 * @author Eduardo
 */
public class DetalleVentaDAO {
    private Connection conn;

    public int insertarDetalleVenta(DetalleVenta detalle, Connection con) throws SQLException {
        String sql = "{CALL sp_insert_detalleventa(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, detalle.getIdVenta());
            cs.setInt(2, detalle.getIdProducto());  
            cs.setString(3, detalle.getProducto());
            cs.setInt(3, detalle.getCantidad());
            cs.setDouble(4, detalle.getPrecioUnitario());
            cs.setDouble(5, detalle.getSubTotal());
            return cs.executeUpdate();
        }
    }

    public List<DetalleVenta> obtenerDetallesVentaPorId(int idVenta) {
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_get_detalleventa_by_venta}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idVenta);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idDetalle = rs.getInt("id_detalle");
                int idVentas = rs.getInt("id_venta");
                int idProducto = rs.getInt("id_producto");
                String Producto = rs.getString("ProductoNombre");
                int cantidad = rs.getInt("cantidad");
                double precioUnitario = rs.getDouble("precio_unitario");
                double subTotal = rs.getDouble("subtotal");

                DetalleVenta detalle = new DetalleVenta(idDetalle, idVenta, idProducto, cantidad, precioUnitario, subTotal, Producto);
                detalle.setProducto(Producto);
                detallesVenta.add(detalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return detallesVenta;
    }
}

