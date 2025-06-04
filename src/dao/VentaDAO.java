package dao;

import db.DBConnection;
import dto.VentaDTO;
import dto.DetalleVentaDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author Eduardo
 */
public class VentaDAO {
    public int insertarVenta(VentaDTO venta, List<DetalleVentaDTO> detallesVenta) {
        int result = 0;
        Connection conn = null;
        CallableStatement stmt = null;
        int idVentaGenerado = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();
            conn.setAutoCommit(false);

            String queryVenta = "{call sp_insertar_venta(?, ?, ?, ?)}";
            stmt = conn.prepareCall(queryVenta);
            stmt.setInt(1, venta.getIdVendedor());
            stmt.setDate(2, new java.sql.Date(venta.getFecha().getTime()));
            stmt.setDouble(3, venta.getTotal());
            stmt.registerOutParameter(4, java.sql.Types.INTEGER);

            result = stmt.executeUpdate();
            idVentaGenerado = stmt.getInt(4);

            if (idVentaGenerado > 0) {
                DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
                for (DetalleVentaDTO detalle : detallesVenta) {
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
    
    public int modificarVenta(VentaDTO venta, List<DetalleVentaDTO> detallesVenta) {
        int result = 0;
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();
            conn.setAutoCommit(false);

            String queryModificarVenta = "{call sp_modificar_venta(?, ?, ?, ?)}";
            stmt = conn.prepareCall(queryModificarVenta);
            stmt.setInt(1, venta.getIdVenta());
            stmt.setInt(2, venta.getIdVendedor());
            stmt.setDate(3, new java.sql.Date(venta.getFecha().getTime()));
            stmt.setDouble(4, venta.getTotal());

            result = stmt.executeUpdate();

            String queryBorrarDetalles = "{call sp_borrar_detalles_venta(?)}";
            stmt = conn.prepareCall(queryBorrarDetalles);
            stmt.setInt(1, venta.getIdVenta());
            stmt.executeUpdate();

            DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
            for (DetalleVentaDTO detalle : detallesVenta) {
                detalle.setIdVenta(venta.getIdVenta());
                detalleVentaDAO.insertarDetalleVenta(detalle, conn);
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
    
    public int eliminarVenta(int idVenta) {
        int result = 0;
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();
            conn.setAutoCommit(false);

            String queryEliminarVenta = "{call sp_eliminar_venta(?)}";
            stmt = conn.prepareCall(queryEliminarVenta);
            stmt.setInt(1, idVenta);

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
    
    public List<VentaDTO> obtenerTodasVentas() {
        List<VentaDTO> ventas = new ArrayList<>();
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
                int idVenta = rs.getInt("ID_Venta");
                int idVendedor = rs.getInt("ID_Vendedor");
                String vendedor = rs.getString("VendedorRazonSocial");
                Date fecha = rs.getDate("Fecha");
                double total = rs.getDouble("Total");

                VentaDTO venta = new VentaDTO(idVenta, idVendedor, fecha, total);
                venta.setVendedor(vendedor);
                ventas.add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return ventas;
    }
    
    public VentaDTO obtenerVentaPorId(int idVenta) {
        VentaDTO venta = null;
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_obtener_venta_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idVenta);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idVendedor = rs.getInt("ID_Vendedor");
                Date fecha = rs.getDate("Fecha");
                double total = rs.getDouble("Total");

                venta = new VentaDTO(idVenta, idVendedor, fecha, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return venta;
    }
}
