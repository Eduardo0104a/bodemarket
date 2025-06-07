package dao;

import db.DBConnection;
import dto.VoucherDTO;
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
public class VoucherDAO {

    private DBConnection db;

    public VoucherDAO() {
        db = new DBConnection();
    }

    public int insertarVoucher(VoucherDTO voucher) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = db.getConnection();
            cs = conn.prepareCall("{CALL insertVoucher(?,?,?,?)}");
            cs.setInt(1, voucher.getIdVenta());
            cs.setString(2, voucher.getCodigo());
            cs.setDouble(3, voucher.getDescuento());
            cs.setDate(4, new java.sql.Date(voucher.getFecha().getTime()));

            return cs.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        } finally {
            DBConnection.close(conn, cs);
        }
    }

    public VoucherDTO obtenerPorVentaId(int idVenta) {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        VoucherDTO voucher = null;
        try {
            conn = db.getConnection();
            cs = conn.prepareCall("{CALL getVoucherByVentaId(?)}");
            cs.setInt(1, idVenta);
            rs = cs.executeQuery();
            if (rs.next()) {
                voucher = new VoucherDTO();
                voucher.setIdVoucher(rs.getInt("id_voucher"));
                voucher.setIdVenta(rs.getInt("id_venta"));
                voucher.setCodigo(rs.getString("codigo"));
                voucher.setDescuento(rs.getDouble("descuento"));
                voucher.setFecha(rs.getDate("fecha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, cs, rs);
        }
        return voucher;
    }

    public VoucherDTO obtenerPorCodigo(String codigo) {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        VoucherDTO voucher = null;
        try {
            conn = db.getConnection();
            cs = conn.prepareCall("{CALL getVoucherByCodigo(?)}");
            cs.setString(1, codigo);
            rs = cs.executeQuery();
            if (rs.next()) {
                voucher = new VoucherDTO();
                voucher.setIdVoucher(rs.getInt("id_voucher"));
                voucher.setIdVenta(rs.getInt("id_venta"));
                voucher.setCodigo(rs.getString("codigo"));
                voucher.setDescuento(rs.getDouble("descuento"));
                voucher.setFecha(rs.getDate("fecha"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, cs, rs);
        }
        return voucher;
    }

    public List<VoucherDTO> listarTodos() {
        Connection conn = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        List<VoucherDTO> lista = new ArrayList<>();
        try {
            conn = db.getConnection();
            cs = conn.prepareCall("{CALL getAllVouchers()}");
            rs = cs.executeQuery();
            while (rs.next()) {
                VoucherDTO voucher = new VoucherDTO();
                voucher.setIdVoucher(rs.getInt("id_voucher"));
                voucher.setIdVenta(rs.getInt("id_venta"));
                voucher.setCodigo(rs.getString("codigo"));
                voucher.setDescuento(rs.getDouble("descuento"));
                voucher.setFecha(rs.getDate("fecha"));
                lista.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(conn, cs, rs);
        }
        return lista;
    }
}
