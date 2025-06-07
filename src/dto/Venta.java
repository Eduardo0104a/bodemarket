package dto;

import java.time.LocalDateTime;
import java.util.Date;

public class Venta {

    private int id;
    private int idUsuario;
    private Date fecha;
    private String usuarioNombre;
    private double total;

    public Venta(int id, int idUsuario, Date fecha, double total) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.total = total;
    }
    
    public Venta() {}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}