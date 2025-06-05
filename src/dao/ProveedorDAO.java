/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    private final DBConnection dbConnection = new DBConnection();

    public int insertar(Proveedor proveedor) {
        String sql = "{CALL insertarProveedor(?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, proveedor.getNombre());
            cs.setString(2, proveedor.getCorreo());
            cs.setString(3, proveedor.getTelefono());
            cs.setString(4, proveedor.getRuc());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }
    }

    public int actualizar(Proveedor proveedor) {
        String sql = "{CALL actualizarProveedor(?, ?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, proveedor.getId());
            cs.setString(2, proveedor.getNombre());
            cs.setString(3, proveedor.getCorreo());
            cs.setString(4, proveedor.getTelefono());
            cs.setString(5, proveedor.getRuc());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }
    }

    public int eliminar(int id) {
        String sql = "{CALL eliminarProveedor(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  
        }
    }

    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "{CALL listarProveedores()}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setCorreo(rs.getString("correo"));
                proveedor.setTelefono(rs.getString("telefono"));
                proveedor.setRuc(rs.getString("ruc"));
                lista.add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Proveedor obtenerPorId(int id) {
        Proveedor proveedor = null;
        String sql = "{CALL obtenerProveedorPorId(?)}";
        try (Connection conn = dbConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    proveedor = new Proveedor();
                    proveedor.setId(rs.getInt("id_proveedor"));
                    proveedor.setNombre(rs.getString("nombre"));
                    proveedor.setCorreo(rs.getString("correo"));
                    proveedor.setTelefono(rs.getString("telefono"));
                    proveedor.setRuc(rs.getString("ruc"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedor;
    }
}
