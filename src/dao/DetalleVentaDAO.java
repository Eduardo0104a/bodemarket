
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

    public DetalleVentaDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarDetalleVenta(DetalleVenta detalle) throws SQLException {
        String sql = "{CALL sp_insert_detalleventa(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, detalle.getVenta().getId());
            cs.setInt(2, detalle.getProducto().getId());
            cs.setInt(3, detalle.getCantidad());
            cs.setDouble(4, detalle.getPrecioUnitario());
            cs.setDouble(5, detalle.getSubtotal());
            return cs.executeUpdate() > 0;
        }
    }

    public List<DetalleVenta> listarDetallesPorVenta(int idVenta) throws SQLException {
        List<DetalleVenta> lista = new ArrayList<>();
        String sql = "{CALL sp_get_detalleventa_by_venta(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idVenta);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setId(rs.getInt("id_detalle"));
                    
                    Venta venta = new Venta();
                    venta.setId(rs.getInt("id_venta"));
                    detalle.setVenta(venta);
                    
                    Producto producto = new Producto();
                    producto.setId(rs.getInt("id_producto"));
                    detalle.setProducto(producto);
                    
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setSubTotal(rs.getDouble("subtotal"));
                    
                    lista.add(detalle);
                }
            }
        }
        return lista;
    }
}

