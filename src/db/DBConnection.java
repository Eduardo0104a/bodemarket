package db;

import java.sql.Connection;
import java.sql.DriverManager;    
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author Eduardo
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bodemarket?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root1";
    private static final String PASSWORD = "1234";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private Connection connection;
    
    public DBConnection(){
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn, PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(conn);
    }

    public static void close(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(conn, stmt);
    }
}
