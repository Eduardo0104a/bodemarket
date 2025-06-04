
package dto;

import java.util.Date;
/**
 *
 * @author Eduardo
 */
public class MovimientoInventario {
  private int idMovimiento;
    private int idInventario;
    private String tipo;
    private Double cantidad;
    private Date fecha;
    private String descripcion;

    public MovimientoInventario(int idMovimiento, int idInventario, String tipo, Double cantidad, Date fecha, String descripcion) {
        this.idMovimiento = idMovimiento;
        this.idInventario = idInventario;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public MovimientoInventario() {
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
