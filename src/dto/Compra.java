package dto;

import java.util.Date;

/**
 *
 * @author EduardoPC
 */
public class Compra {
    private int idCompra;
    private int idProveedor;
    private Date fecha;
    private double total; 

    public Compra() {}

    public Compra(int idProveedor, Date fecha, double total) {
        this.idProveedor = idProveedor;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

