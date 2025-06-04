
package dto;

import java.util.Date;
/**
 *
 * @author Eduardo
 */
public class Vendedor {
    private int idVendedor;
    private int idUsuario;
    private Date fechaInicio;
    private Date fechaFin;
    private String correoElectronico;
    private String razonSocial;

    public Vendedor(int idVendedor, int idUsuario, Date fechaInicio, Date fechaFin, String razonSocial) {
        this.idVendedor = idVendedor;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.razonSocial = razonSocial;
    }

    public Vendedor(int idVendedor, int idUsuario, Date fechaInicio, Date fechaFin, String correoElectronico, String razonSocial) {
        this.idVendedor = idVendedor;
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.correoElectronico = correoElectronico;
        this.razonSocial = razonSocial;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    @Override
    public String toString() {
        return razonSocial;
    }
}

