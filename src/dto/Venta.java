package dto;

import java.time.LocalDateTime;

public class Venta {

    private int id;
    private int idUsuario;
    private LocalDateTime fecha;
    private String usuarioNombre;
    private double total;

    public Venta(int id, int idUsuario, LocalDateTime fecha, String usuarioNombre, double total) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.usuarioNombre = usuarioNombre;
        this.total = total;
    }

    public Venta() {
    }

    public Venta(int id, int IdUsuario, LocalDateTime fecha, double total) {
    }


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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
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