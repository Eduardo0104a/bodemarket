package dao;

import db.DBConnection;
import dto.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;


/*
 *
 * @author Eduardo
 */
public class CategoriaDAO {

    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{call sp_listar_categorias()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idCategoria = rs.getInt("id_categoria");
                String descripcion = rs.getString("descripcion_categoria");

                Categoria categoria = new Categoria(idCategoria, descripcion);
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return categorias;
    }

    public int insertar(Categoria categoria) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_insertar_categoria(?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, categoria.getDescripcionCat());
            stmt.registerOutParameter(2, java.sql.Types.INTEGER);
            stmt.registerOutParameter(3, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(3);

            if (errorCode == 0) {
                int idGenerado = stmt.getInt(2);
                categoria.setIdCat(idGenerado);
            } else {
                System.out.println("Error: La categoría ya existe.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return errorCode;
    }

    public int modificar(Categoria categoria) {
        Connection conn = null;
        CallableStatement stmt = null;
        int resultado = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_modificar_categoria(?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, categoria.getIdCat());
            stmt.setString(2, categoria.getDescripcionCat());
            stmt.registerOutParameter(3, java.sql.Types.INTEGER);

            stmt.execute();
            resultado = stmt.getInt(3);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return resultado;
    }

    public int eliminar(int idCategoria) {
        Connection conn = null;
        CallableStatement stmt = null;
        int result = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminar_categoria(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idCategoria);

            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }

    public Categoria obtenerCategoriaPorId(int idCategoria) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Categoria categoria = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtener_categoria_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idCategoria);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String descripcion = rs.getString("descripcion_categoria");
                categoria = new Categoria(idCategoria, descripcion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return categoria;
    }

}
