package dao;

import db.DBConnection;
import dto.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
/**
 *
 * @author Eduardo
 */
public class VendedorDAO {
    public List<Vendedor> obtenerVendedores() {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        List<Vendedor> vendedores = new ArrayList<>();

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtener_vendedores()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idVendedor = rs.getInt("ID_Vendedor");
                int idUsuario = rs.getInt("ID_Usuario");
                Date fechaInicio = rs.getDate("Fecha_Inicio");
                Date fechaFin = rs.getDate("Fecha_Fin");
                String correoElectronico = rs.getString("Correo_Electronico");
                String razonSocial = rs.getString("Razon_Social");
                Vendedor vendedor = new Vendedor(idVendedor, idUsuario, fechaInicio, fechaFin, correoElectronico, razonSocial);
                vendedores.add(vendedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return vendedores;
    }

    public int insertarVendedor(Vendedor vendedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_insertar_vendedor(?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, vendedor.getIdUsuario());
            stmt.setDate(2, new java.sql.Date(vendedor.getFechaInicio().getTime()));
            stmt.setDate(3, new java.sql.Date(vendedor.getFechaFin().getTime()));
            stmt.setString(4, vendedor.getRazonSocial());
            stmt.registerOutParameter(5, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(5);

            if (errorCode != 0) {
                System.out.println("Error: No se pudo insertar el vendedor.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }

    public Vendedor obtenerVendedorPorId(int idVendedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Vendedor vendedor = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtener_vendedor_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idVendedor);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("ID_Usuario");
                Date fechaInicio = rs.getDate("Fecha_Inicio");
                Date fechaFin = rs.getDate("Fecha_Fin");
                String razonSocial = rs.getString("Razon_Social");
                vendedor = new Vendedor(idVendedor, idUsuario, fechaInicio, fechaFin, razonSocial);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return vendedor;
    }
    
    public int modificarVendedor(Vendedor vendedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_modificar_vendedor(?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, vendedor.getIdVendedor());
            stmt.setInt(2, vendedor.getIdUsuario());
            stmt.setDate(3, new java.sql.Date(vendedor.getFechaInicio().getTime()));
            stmt.setDate(4, new java.sql.Date(vendedor.getFechaFin().getTime()));
            stmt.setString(5, vendedor.getRazonSocial());
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(6);

            if (errorCode != 0) {
                System.out.println("Error: No se pudo modificar el vendedor.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }

    public int eliminarVendedor(int idVendedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        int result = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminar_vendedor(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idVendedor);
            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }
}
