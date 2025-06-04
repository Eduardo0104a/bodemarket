package dao;

import db.DBConnection;
import java.sql.*;
import java.util.List;
import java.sql.SQLException;

public abstract class BaseDAO<T> implements iCrudDAO<T> {

    protected Connection conn = null;
    protected CallableStatement stmt = null;
    protected ResultSet rs = null;

    protected Connection obtenerConexion() {
        DBConnection conexionSQL = new DBConnection();
        return conexionSQL.getConnection();
    }

    protected void cerrarRecursos() {
        DBConnection.close(conn, stmt, rs);
    }

    public abstract List<T> obtenerProductoInventario();
    
    public abstract T obtenerProductoInventarioPorId(int id);
    
    public abstract int insertarProductoInventario(T entidad);
    
    public abstract int modificarProductoInventario(T entidad);
    
    public abstract int eliminarProductoInventario(int id);
}

