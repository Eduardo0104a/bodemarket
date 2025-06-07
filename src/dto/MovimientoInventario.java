package dto;

import java.time.LocalDateTime;
import java.util.Date;

public class MovimientoInventario {

    private int idMovimiento;
    private int idInventario;
    private int idProducto;
    private String nombreProducto;
    private String tipo;
    private int cantidad;
    private Date fecha;
    private String descripcion;

    public MovimientoInventario(int idMovimiento, int idInventario, int idProducto, String tipo, int cantidad, Date fecha, String descripcion) {
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

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
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