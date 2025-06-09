package dao;

import dto.Producto;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author EduardoPC
 */
public class ProductoDAO {

    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();
            String query = "{CALL sp_listar_productos()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Producto productoInv = new Producto();
                productoInv.setIdProducto(rs.getInt("id_producto"));
                productoInv.setNombre(rs.getString("nombre"));
                productoInv.setDescripcion(rs.getString("descripcion"));
                productoInv.setStock(rs.getInt("stock"));
                productos.add(productoInv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return productos;
    }

    public int insertar(Producto producto) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_insertar_producto(?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getIdProveedor());
            stmt.setInt(5, producto.getIdMedida());
            stmt.setInt(6, producto.getIdCategoria());
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(7);

            if (errorCode != 0) {
                System.out.println("Error: No se pudo insertar el producto en inventario.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }

    public int modificar(Producto producto) {
        Connection conn = null;
        CallableStatement stmt = null;
        int resultado = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_modificar_producto(?, ?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getIdProveedor());
            stmt.setInt(6, producto.getIdMedida());
            stmt.setInt(7, producto.getIdCategoria());
            stmt.registerOutParameter(8, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            stmt.execute();
            resultado = stmt.getInt(7);

            if (resultado != 0) {
                System.out.println("Error: No se pudo modificar el producto en inventario.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return resultado;
    }

    public int eliminar(int idProducto) {
        Connection conn = null;
        CallableStatement stmt = null;
        int result = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminar_producto_inventario(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idProducto);
            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }

    public Producto obtenerProductoInventarioPorId(int idProducto) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Producto producto = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtener_producto_inventario_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idProducto);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Producto pro = new Producto();
                pro.setNombre(rs.getString("nombre"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPrecio(rs.getDouble("Precio"));
                pro.setIdInventario(rs.getInt("id_inventario"));
                pro.setStock(rs.getInt("stock"));
                pro.setIdProveedor(rs.getInt("id_proveedor"));
                pro.setIdMedida(rs.getInt("id_medida"));
                pro.setIdCategoria(rs.getInt("id_categoria"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return producto;
    }

}
