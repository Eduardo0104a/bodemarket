
package dto;

import java.util.Date;
/**
 *
 * @author Eduardo
 */
public class VentaDTO {
    private int idVenta;
    private int idVendedor;
    private Date fecha;
    private double total;
    private String vendedor;

    public VentaDTO() {}
    
    public VentaDTO(int idVenta, int idVendedor, Date fecha, double total) {
        this.idVenta = idVenta;
        this.idVendedor = idVendedor;
        this.fecha = fecha;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
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
    
    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }
}

