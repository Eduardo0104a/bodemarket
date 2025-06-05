package dao;

import db.DBConnection;
import dto.DetalleVenta;
import dto.Venta;
import dto.Usuario;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author EduardoPC
 */
public class VentaDAO {

    private final DBConnection dbConnection = new DBConnection();

    // Insertar venta
    public int insertar(Venta venta, List<DetalleVenta> detallesVenta) {
        String sql = "{CALL insertarVenta(?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            cs.setInt(2, venta.getIdUsuario());
            cs.setDouble(3, venta.getTotal());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int actualizar(Venta venta) {
        String sql = "{CALL actualizarVenta(?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, venta.getId());
            cs.setTimestamp(2, Timestamp.valueOf(venta.getFecha()));
            cs.setInt(3, venta.getIdUsuario());
            cs.setDouble(4, venta.getTotal());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int eliminar(int id) {
        String sql = "{CALL eliminarVenta(?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();
        String sql = "{CALL listarVentas()}";

        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();
                double total = rs.getDouble("Total");
                int idUsuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombreUsuario");
                Venta venta = new Venta(idVenta, idUsuario, fecha, nombreUsuario, total);
                venta.setUsuarioNombre(nombreUsuario);
                lista.add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Venta obtenerPorId(int id) {
        Venta venta = null;
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call obtenerVentaPorId}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            {
                if (rs.next()) {
                    int IdUsuario = rs.getInt("id_usuario");
                    LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();
                    double total = rs.getDouble("total");

                    venta = new Venta(id, IdUsuario, fecha, total);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return venta;
    }

}
