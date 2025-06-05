package dao;

import db.DBConnection;
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
/**
 *
 * @author EduardoPC
 */
public class VentaDAO {

    private final DBConnection dbConnection = new DBConnection();

    // Insertar venta
    public boolean insertar(Venta venta) {
        String sql = "{CALL insertarVenta(?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            cs.setInt(2, venta.getUsuario().getIdUsuario());
            cs.setDouble(3, venta.getTotal());

            return cs.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Venta venta) {
        String sql = "{CALL actualizarVenta(?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, venta.getId());
            cs.setTimestamp(2, Timestamp.valueOf(venta.getFecha()));
            cs.setInt(3, venta.getUsuario().getIdUsuario());
            cs.setDouble(4, venta.getTotal());

            return cs.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "{CALL eliminarVenta(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();
        String sql = "{CALL listarVentas()}";

        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id_venta"));
                venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                venta.setUsuario(usuario); 

                venta.setTotal(rs.getDouble("total"));
                lista.add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Venta obtenerPorId(int id) {
        Venta venta = null;
        String sql = "{CALL obtenerVentaPorId(?)}";

        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    venta = new Venta();
                    venta.setId(rs.getInt("id_venta"));
                    venta.setFecha(rs.getTimestamp("fecha").toLocalDateTime());

                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    venta.setUsuario(usuario);

                    venta.setTotal(rs.getDouble("total"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return venta;
    }
}
