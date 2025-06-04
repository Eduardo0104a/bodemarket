package dao;

import db.DBConnection;
import dto.ProductoInventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoInventarioDAO extends BaseDAO<ProductoInventario> {

    @Override
    public List<ProductoInventario> obtenerProductoInventario() {
        List<ProductoInventario> productosInventario = new ArrayList<>();

        try {
            conn = obtenerConexion();
            String query = "{CALL sp_obtener_productos_inventario()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("ID_Producto");
                String nombre = rs.getString("Nombre");
                String descripcion = rs.getString("Descripcion");
                double precio = rs.getDouble("Precio");
                int idInventario = rs.getInt("ID_Inventario");
                int stock = rs.getInt("stock");
                String categoria = rs.getString("Categoria");

                ProductoInventario productoInventario = new ProductoInventario(idProducto, nombre, descripcion, precio, idInventario, stock, categoria);
                productosInventario.add(productoInventario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }

        return productosInventario;
    }

    @Override
    public ProductoInventario obtenerProductoInventarioPorId(int idProducto) {
        ProductoInventario productoInventario = null;

        try {
            conn = obtenerConexion();
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

                productoInventario = new ProductoInventario(idProducto, nombre, descripcion, precio, idInventario, stock, categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }

        return productoInventario;
    }

    @Override
    public int insertarProductoInventario(ProductoInventario productoInventario) {
        int errorCode = 0;

        try {
            conn = obtenerConexion();
            String query = "{CALL sp_insertar_producto_inventario(?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, productoInventario.getNombre());
            stmt.setString(2, productoInventario.getDescripcion());
            stmt.setDouble(3, productoInventario.getPrecio());
            stmt.setInt(4, productoInventario.getStock());
            stmt.setString(5, productoInventario.getCategoria());
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(6);

            if (errorCode != 0) {
                System.out.println("Error: No se pudo insertar el producto en inventario.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }

        return errorCode;
    }

    @Override
    public int modificarProductoInventario(ProductoInventario productoInventario) {
        int errorCode = 0;

        try {
            conn = obtenerConexion();
            String query = "{CALL sp_modificar_producto_inventario(?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, productoInventario.getIdProducto());
            stmt.setString(2, productoInventario.getNombre());
            stmt.setString(3, productoInventario.getDescripcion());
            stmt.setDouble(4, productoInventario.getPrecio());
            stmt.setInt(5, productoInventario.getStock());
            stmt.setString(6, productoInventario.getCategoria());
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(7);

            if (errorCode != 0) {
                System.out.println("Error: No se pudo modificar el producto en inventario.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }

        return errorCode;
    }

    @Override
    public int eliminarProductoInventario(int idProducto) {
        int result = -1;

        try {
            conn = obtenerConexion();
            String query = "{CALL sp_eliminar_producto_inventario(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idProducto);
            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }

        return result;
    }
}
