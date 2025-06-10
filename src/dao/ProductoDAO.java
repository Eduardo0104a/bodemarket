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
                productoInv.setNombre(rs.getString("nombre_producto"));  // ojo al alias en SP
                productoInv.setDescripcion(rs.getString("descripcion"));
                productoInv.setPrecio(rs.getDouble("precio"));
                productoInv.setPrecioCompra(rs.getDouble("precio_compra"));
                productoInv.setIdInventario(rs.getInt("id_inventario"));
                productoInv.setStock(rs.getInt("stock"));
                productoInv.setIdCategoria(rs.getInt("id_categoria"));
                productoInv.setNomCategoria(rs.getString("nom_categoria"));
                productoInv.setNombreProveedor(rs.getString("nombre_proveedor"));
                productoInv.setNombreMedida(rs.getString("nombre_medida"));
                productoInv.setNombreCortoMedida(rs.getString("nombre_corto_medida"));

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

            String query = "{CALL sp_insertar_producto(?, ?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setDouble(4, producto.getPrecioCompra());
            stmt.setInt(5, producto.getIdProveedor());
            stmt.setInt(6, producto.getIdMedida());
            stmt.setInt(7, producto.getIdCategoria());
            stmt.registerOutParameter(8, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(8);

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

    public int actualizarEntradaStock(int idInventario, int cantidad) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_actualizar_entrada_stock(?, ?, ?)}";
            stmt = conn.prepareCall(query);

            stmt.setInt(1, idInventario);   
            stmt.setInt(2, cantidad);       
            stmt.registerOutParameter(3, java.sql.Types.INTEGER);  

            stmt.executeUpdate();

            errorCode = stmt.getInt(3);

            if (errorCode != 0) {
                System.out.println("Error: No se pudo actualizar el stock.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            errorCode = -1;  
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

            String query = "{CALL sp_modificar_producto(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);

            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setDouble(5, producto.getPrecioCompra());  
            stmt.setInt(6, producto.getStock());           
            stmt.setInt(7, producto.getIdProveedor());
            stmt.setInt(8, producto.getIdMedida());
            stmt.setInt(9, producto.getIdCategoria());
            stmt.registerOutParameter(10, java.sql.Types.INTEGER); 

            stmt.executeUpdate();

            resultado = stmt.getInt(10);

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

    public int modificarBasico(Producto producto) {
        Connection conn = null;
        CallableStatement stmt = null;
        int resultado = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_actualizar_producto_basico(?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);

            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getIdCategoria());
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);

            stmt.executeUpdate();

            resultado = stmt.getInt(6);

            if (resultado != 0) {
                System.out.println("Error: No se pudo modificar el producto básico. Código: " + resultado);
            }

        } catch (SQLException e) {
            System.err.println("SQLException en modificarBasico:");
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return resultado;
    }

    public int eliminar(int idProducto) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminar_producto_inventario(?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idProducto);
            stmt.registerOutParameter(2, java.sql.Types.INTEGER);

            stmt.executeUpdate();

            errorCode = stmt.getInt(2);

            if (errorCode != 0) {
                System.out.println("Error: No se pudo eliminar el producto.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }

    public Producto obtenerProductoPorId(int idProducto) {
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
                producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setPrecioCompra(rs.getDouble("precio_compra"));
                producto.setIdInventario(rs.getInt("id_inventario"));
                producto.setStock(rs.getInt("stock"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return producto;
    }

}
