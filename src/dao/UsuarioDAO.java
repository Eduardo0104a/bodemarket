package dao;

import db.DBConnection;
import dto.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
/**
 *
 * @author Eduardo
 */
public class UsuarioDAO {
    public Usuario validarUsuario(String correoElectronico, String contraseña) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_validarUsuario(?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, correoElectronico);
            stmt.setString(2, contraseña);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("ID_Usuario");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String rol = rs.getString("Rol");

                usuario = new Usuario(idUsuario, nombre, apellido, correoElectronico, contraseña, rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs); 
        }

        return usuario;
    }

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection(); 
            conn = conexionSQL.getConnection(); 

            String query = "{call sp_obtener_usuarios()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idUsuario = rs.getInt("ID_Usuario");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String correoElectronico = rs.getString("correo_electronico");
                String contraseña = rs.getString("contraseña");
                String rol = rs.getString("Rol");

                Usuario usuario = new Usuario(idUsuario, nombre, apellido, correoElectronico, contraseña, rol);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs); 
        }

        return usuarios;
    }
    
    public int insertarUsuario(Usuario usuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_insertarUsuario(?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getCorreoElectronico());
            stmt.setString(4, usuario.getContraseña());
            stmt.setString(5, usuario.getRol());
            stmt.registerOutParameter(6, java.sql.Types.INTEGER);
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(7);

            if (errorCode == 0) {
                int idGenerado = stmt.getInt(6);
                usuario.setIdUsuario(idGenerado);
            } else {
                System.out.println("Error: El correo electrónico ya está registrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null); 
        }

        return errorCode;
    }
    
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtenerUsuarioPorId(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String correoElectronico = rs.getString("Correo_Electronico");
                String contraseña = rs.getString("Contraseña");
                String rol = rs.getString("Rol");

                usuario = new Usuario(idUsuario, nombre, apellido, correoElectronico, contraseña, rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return usuario;
    }
    
        public int modificarUsuario(Usuario usuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        int resultado = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_modificarUsuario(?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getCorreoElectronico());
            stmt.setString(5, usuario.getContraseña());
            stmt.setString(6, usuario.getRol());
            stmt.registerOutParameter(7, java.sql.Types.INTEGER);

            stmt.execute();
            resultado = stmt.getInt(7); // Obtener el valor del parámetro de salida

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null); 
        }

        return resultado;
    }
        
        public int eliminarUsuario(int idUsuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        int result = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminarUsuario(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idUsuario);
            result = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null);
        }

        return result;
    }
}

