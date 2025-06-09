package dao;

import dto.Proveedor;

import db.DBConnection;
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
public class ProveedorDAO {

    public List<Proveedor> listar() {
        List<Proveedor> proveedores = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_listar_proveedores()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idProveedor = rs.getInt("id_proveedor");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String ruc = rs.getString("ruc");

                Proveedor proveedor = new Proveedor(idProveedor, nombre, correo, telefono, ruc);
                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return proveedores;
    }

    public int insertar(Proveedor proveedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_insertar_proveedor(?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getCorreo());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getRuc());
            stmt.registerOutParameter(5, java.sql.Types.INTEGER);
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(6);

            if (errorCode == 0) {
                int idGenerado = stmt.getInt(5);
                proveedor.setIdProveedor(idGenerado);
            } else {
                System.out.println("Error: El RUC ya está registrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }

    public int modificar(Proveedor proveedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        int resultado = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_modificar_proveedor(?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, proveedor.getIdProveedor());
            stmt.setString(2, proveedor.getNombre());
            stmt.setString(3, proveedor.getCorreo());
            stmt.setString(4, proveedor.getTelefono());
            stmt.setString(5, proveedor.getRuc());
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);

            stmt.execute();
            resultado = stmt.getInt(6);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return resultado;
    }

    public int eliminar(int idProveedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        int result = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminar_proveedor(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idProveedor);

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }

    public Proveedor obtenerProveedorPorId(int idProveedor) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Proveedor proveedor = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtener_proveedor_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idProveedor);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String ruc = rs.getString("ruc");

                proveedor = new Proveedor(idProveedor, nombre, correo, telefono, ruc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return proveedor;
    }

}
