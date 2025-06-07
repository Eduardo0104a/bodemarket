
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

        public Usuario validarUsuario(String nombreUsuario, String clave) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_validar_usuario(?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, clave);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String rol = rs.getString("Rol");

                usuario = new Usuario(idUsuario, nombre, apellido, correo, telefono, nombreUsuario, clave, rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs); 
        }

        return usuario;
    }
        
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            DBConnection conexionSQL = new DBConnection(); 
            conn = conexionSQL.getConnection(); 

            String query = "{call sp_listar_usuarios()}";
            stmt = conn.prepareCall(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String nombreUsuario = rs.getString("nombre_usuario");
                String clave = rs.getString("clave");
                String rol = rs.getString("rol");

                Usuario usuario = new Usuario(idUsuario, nombre, apellido, correo, telefono, nombreUsuario, clave, rol);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs); 
        }

        return usuarios;
    }

    public int insertar(Usuario usuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        int errorCode = 0;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_insertar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getUsuario());
            stmt.setString(6, usuario.getPassword());
            stmt.setString(7, usuario.getRol());
            stmt.registerOutParameter(8, java.sql.Types.INTEGER);
            stmt.registerOutParameter(9, java.sql.Types.INTEGER);
            stmt.executeUpdate();

            errorCode = stmt.getInt(9);

            if (errorCode == 0) {
                int idGenerado = stmt.getInt(8);
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

            String query = "{CALL sp_obtener_usuario_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String nombreUsuario = rs.getString("nombre_usuario");
                String clave = rs.getString("clave");
                String rol = rs.getString("rol");

                usuario = new Usuario(idUsuario, nombre, apellido, correo, telefono, nombreUsuario, clave, rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return usuario;
    }

    public int modificar(Usuario usuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        int resultado = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_modificar_usuario(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getCorreo());
            stmt.setString(5, usuario.getTelefono());
            stmt.setString(6, usuario.getUsuario());
            stmt.setString(7, usuario.getPassword());
            stmt.setString(8, usuario.getRol());
            stmt.registerOutParameter(9, java.sql.Types.INTEGER);

            stmt.execute();
            resultado = stmt.getInt(9); 

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, null); 
        }

        return resultado;
    }
    
    public int eliminar(int idUsuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        int result = -1;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_eliminar_usuario(?)}";
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
    
    public Usuario buscar(int idUsuario) {
        Connection conn = null;
        CallableStatement stmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            DBConnection conexionSQL = new DBConnection();
            conn = conexionSQL.getConnection();

            String query = "{CALL sp_obtener_usuario_por_id(?)}";
            stmt = conn.prepareCall(query);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String nombreUsuario = rs.getString("nombre_usuario");
                String clave = rs.getString("clave");
                String rol = rs.getString("rol");

                usuario = new Usuario(idUsuario, nombre, apellido, correo, telefono, nombreUsuario, clave, rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, stmt, rs);
        }

        return usuario;
    }
}
