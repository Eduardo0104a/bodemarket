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
/**
/**
 *
 * @author Eduardo
 */
public class MovimientoInventarioDAO {
    private Connection conn;

    public MovimientoInventarioDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insertarMovimiento(MovimientoInventario movimiento) throws SQLException {
        String sql = "{CALL sp_insert_movimiento_inventario(?, ?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, movimiento.getProducto().getId());
            cs.setString(2, String.valueOf(movimiento.getTipo()));
            cs.setInt(3, movimiento.getCantidad());
            cs.setTimestamp(4, Timestamp.valueOf(movimiento.getFecha()));
            cs.setString(5, movimiento.getDescripcion());
            return cs.executeUpdate() > 0;
        }
    }

    public List<MovimientoInventario> listarMovimientosPorProducto(int idProducto) throws SQLException {
        List<MovimientoInventario> lista = new ArrayList<>();
        String sql = "{CALL sp_get_movimientos_by_producto(?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, idProducto);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    MovimientoInventario mov = new MovimientoInventario();
                    mov.setId(rs.getInt("id_movimiento"));

                    Producto producto = new Producto();
                    producto.setId(rs.getInt("id_producto"));
                    mov.setProducto(producto);

                    mov.setTipo(rs.getString("tipo").charAt(0));
                    mov.setCantidad(rs.getInt("cantidad"));
                    mov.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    mov.setDescripcion(rs.getString("descripcion"));

                    lista.add(mov);
                }
            }
        }
        return lista;
    }
}
