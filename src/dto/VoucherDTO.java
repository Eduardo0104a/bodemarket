
package dto;

import java.util.Date;
/**
 *
 * @author Eduardo
 */
public class VoucherDTO {
    private int idVoucher;
    private int idVenta;
    private String codigo;
    private double descuento;
    private Date fecha;

    public VoucherDTO(int idVoucher, int idVenta, String codigo, double descuento, Date fecha) {
        this.idVoucher = idVoucher;
        this.idVenta = idVenta;
        this.codigo = codigo;
        this.descuento = descuento;
        this.fecha = fecha;
    }

    public VoucherDTO() {
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}

