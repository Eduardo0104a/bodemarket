
package dao;

import db.DBConnection;
import dto.DetalleVentaDTO;
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
    
    public int insertarDetalleVenta(DetalleVentaDTO detalleVenta, Connection conn) {
        int result = 0;
        CallableStatement stmt = null;

        try {
            String query = "{call sp_insertar_detalle_venta(?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, detalleVenta.getIdVenta());
            stmt.setInt(2, detalleVenta.getIdProducto());
            stmt.setInt(3, detalleVenta.getCantidad());
            stmt.setDouble(4, detalleVenta.getPrecioUnitario());
            stmt.setDouble(5, detalleVenta.getSubTotal());

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(null, stmt, null);
        }

        return result;
    }
    
    public List<DetalleVentaDTO> obtenerDetallesVentaPorId(int idVenta) {
        List<DetalleVentaDTO> detallesVenta = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_obtener_detalles_venta_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idVenta);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idDetalle = rs.getInt("ID_Detalle");
                int idProducto = rs.getInt("ID_PRODUCTO");
                String Producto = rs.getString("ProductoNombre");
                int cantidad = rs.getInt("CANTIDAD");
                double precioUnitario = rs.getDouble("PRECIO_UNITARIO");
                double subTotal = rs.getDouble("SUBTOTAL");

                DetalleVentaDTO detalle = new DetalleVentaDTO(idDetalle, idVenta, idProducto, cantidad, precioUnitario, subTotal);
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

