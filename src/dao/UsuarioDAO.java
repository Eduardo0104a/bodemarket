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

    private DBConnection dbConnection;

    public UsuarioDAO() {
        dbConnection = new DBConnection();
    }

    public Usuario validarUsuario(String nombreUsuario, String clave) {
        String sql = "{CALL validar_usuario(?, ?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, nombreUsuario);
            cs.setString(2, clave);

            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("correo"),
                            rs.getString("telefono"),
                            rs.getString("nombre_usuario"),
                            rs.getString("clave"),
                            rs.getString("rol")                           
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) {
        Usuario usuario = null;
        String sql = "{CALL sp_obtener_usuario_por_id(?)}";

        try (Connection conn = new DBConnection().getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setUsuario(rs.getString("nombre_usuario"));
                    usuario.setPassword(rs.getString("clave"));
                    usuario.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    public int insertar(Usuario usuario) {
        String sql = "{CALL insertar_usuario(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, usuario.getNombre());
            cs.setString(2, usuario.getApellido());
            cs.setString(3, usuario.getCorreo());
            cs.setString(4, usuario.getTelefono());
            cs.setString(5, usuario.getUsuario());
            cs.setString(6, usuario.getPassword());
            cs.setString(7, usuario.getRol());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;  // o cualquier valor para indicar error
        }
    }

    public int actualizar(Usuario usuario) {
        String sql = "{CALL actualizar_usuario(?, ?, ?, ?, ?, ?, ?, ?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, usuario.getIdUsuario());
            cs.setString(2, usuario.getNombre());
            cs.setString(3, usuario.getApellido());
            cs.setString(4, usuario.getCorreo());
            cs.setString(5, usuario.getTelefono());
            cs.setString(6, usuario.getUsuario());
            cs.setString(7, usuario.getPassword());
            cs.setString(8, usuario.getRol());

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int eliminar(int idUsuario) {
        String sql = "{CALL eliminar_usuario(?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);

            return cs.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Usuario buscar(int idUsuario) {
        String sql = "{CALL buscar_usuario(?)}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("correo"),
                            rs.getString("telefono"),
                            rs.getString("nombre_usuario"),
                            rs.getString("clave"),
                            rs.getString("rol"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "{CALL listar_usuarios()}";
        try (Connection conn = dbConnection.getConnection(); CallableStatement cs = conn.prepareCall(sql); ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("correo"),
                            rs.getString("telefono"),
                            rs.getString("nombre_usuario"),
                            rs.getString("clave"),
                            rs.getString("rol") );
                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
