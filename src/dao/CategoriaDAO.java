package dao;


import db.DBConnection;
import dto.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author EduardoPC
 */
public class CategoriaDAO {
    private DBConnection dbConnection;

    public CategoriaDAO() {
        dbConnection = new DBConnection();
    }

    public int insertar(Categoria categoria) {
        String sql = "{CALL insertar_categoria(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, categoria.getNombre());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }
    }

    public int actualizar(Categoria categoria) {
        String sql = "{CALL actualizar_categoria(?, ?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, categoria.getId());
            cs.setString(2, categoria.getNombre());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }
    }

    public int eliminar(int idCategoria) {
        String sql = "{CALL eliminar_categoria(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCategoria);

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }
    }

    public Categoria buscar(int idCategoria) {
        String sql = "{CALL buscar_categoria(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idCategoria);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "{CALL listar_categorias()}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre")
                );
                lista.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
