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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author EduardoPC
 */
public class VentaDAO {

    public int insertarVenta(Venta venta, List<DetalleVenta> detallesVenta) {
        int result = 0;
        Connection conn = null;
        CallableStatement stmt = null;
        int idVentaGenerado = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();
            conn.setAutoCommit(false);

            String queryVenta = "{call sp_insertar_Venta(?, ?, ?, ?)}";
            stmt = conn.prepareCall(queryVenta);
            stmt.setInt(1, venta.getIdUsuario());
            stmt.setDate(2, new java.sql.Date(venta.getFecha().getTime()));
            stmt.setDouble(3, venta.getTotal());
            stmt.registerOutParameter(4, java.sql.Types.INTEGER);

            result = stmt.executeUpdate();
            idVentaGenerado = stmt.getInt(4);

            if (idVentaGenerado > 0) {
                DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
                for (DetalleVenta detalle : detallesVenta) {
                    detalle.setIdVenta(idVentaGenerado);
                    detalleVentaDAO.insertarDetalleVenta(detalle, conn);
                }
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }

    public int eliminarVenta(int id) {
        int result = 0;
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();
            conn.setAutoCommit(false);

            String queryEliminarVenta = "{call sp_eliminar_venta(?)}";
            stmt = conn.prepareCall(queryEliminarVenta);
            stmt.setInt(1, id);

            result = stmt.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }

    public List<Venta> obtenerTodasVentas() {
        List<Venta> ventas = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_obtener_todas_ventas()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idVenta = rs.getInt("id_venta");
                int idUsuario = rs.getInt("id_usuario");
                String vendedor = rs.getString("nombreUsuario");
                Date fecha = rs.getDate("fecha");
                double total = rs.getDouble("total");

                Venta venta = new Venta(idVenta, idUsuario, fecha, total);
                venta.setUsuarioNombre(vendedor);
                ventas.add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return ventas;
    }

    public Venta obtenerVentaPorId(int id) {
        Venta venta = null;
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_obtener_venta_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                Date fecha = rs.getDate("fecha");
                double total = rs.getDouble("total");

                venta = new Venta(id, idUsuario, fecha, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return venta;
    }

    public Map<String, Double> obtenerVentasPorCategoria() {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Map<String, Double> ventasPorCategoria = new HashMap<>();

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_ventas_por_categoria()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String categoria = rs.getString("categoria");
                double total = rs.getDouble("total_ventas");
                ventasPorCategoria.put(categoria, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return ventasPorCategoria;
    }

    public Map<String, Double> obtenerVentasPorMes() {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Map<String, Double> datos = new LinkedHashMap<>();

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_ventas_por_mes()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String mes = rs.getString("mes");
                double total = rs.getDouble("total_ventas");
                datos.put(mes, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return datos;
    }

    public Map<String, Double> obtenerVentasPorDia() {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Map<String, Double> datos = new LinkedHashMap<>();

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_ventas_por_dia()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String dia = rs.getString("dia");
                double total = rs.getDouble("total_ventas");
                datos.put(dia, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return datos;
    }

    public Map<String, Integer> obtenerProductosMasVendidos() {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Map<String, Integer> datos = new LinkedHashMap<>();

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_productos_mas_vendidos()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nombreProducto = rs.getString("producto");
                int totalVendido = rs.getInt("total_vendido");
                datos.put(nombreProducto, totalVendido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return datos;
    }

}
