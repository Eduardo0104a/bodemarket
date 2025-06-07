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
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("Precio");
                int idInventario = rs.getInt("id_inventario");
                int stock = rs.getInt("stock");
                String nombreCat = rs.getString("categoria");

                Producto productoInv = new Producto(idProducto, nombre, descripcion, precio, idInventario, stock, nombreCat);
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

            String query = "{CALL sp_insertar_producto(?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getCategoria());
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(6);

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

            String query = "{CALL sp_modificar_producto(?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getStock());
            stmt.setString(6, producto.getCategoria());
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);
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
                String nombre = rs.getString("Nombre");
                String descripcion = rs.getString("Descripcion");
                double precio = rs.getDouble("Precio");
                int idInventario = rs.getInt("ID_Inventario");
                int stock = rs.getInt("stock");
                String categoria = rs.getString("Categoria");

                producto = new Producto(idProducto, nombre, descripcion, precio, idInventario, stock, categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return producto;
    }

}
