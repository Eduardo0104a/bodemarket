package dao;

import dto.Producto;
import dto.Categoria;

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
public class ProductoDAO {

    private DBConnection dbConnection;

    public ProductoDAO() {
        dbConnection = new DBConnection();
    }

    public int insertarProducto(Producto producto) {
        String sql = "{CALL insertar_producto(?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, producto.getNombre());
            cs.setDouble(2, producto.getPrecio());
            cs.setInt(3, producto.getStock());
            cs.setInt(4, producto.getCategoria().getId());

            return cs.executeUpdate(); // retorna número de filas afectadas

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // error
        }
    }

    public int actualizarProducto(Producto producto) {
        String sql = "{CALL actualizar_producto(?, ?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, producto.getId());
            cs.setString(2, producto.getNombre());
            cs.setDouble(3, producto.getPrecio());
            cs.setInt(4, producto.getStock());
            cs.setInt(5, producto.getCategoria().getId());

            return cs.executeUpdate(); // retorna número de filas afectadas

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // error
        }
    }

    public int eliminarProducto(int id) {
        String sql = "{CALL eliminar_producto(?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate(); // retorna número de filas afectadas

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // error
        }
    }

    public Producto buscarProductoPorId(int id) {
        String sql = "{CALL buscar_producto_por_id(?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria(
                            rs.getInt("id_categoria"),
                            rs.getString("nombre_categoria")
                    );
                    return new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            categoria
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "{CALL listar_productos()}";

        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria")
                );
                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        categoria
                );
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
