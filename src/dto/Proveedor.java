package dto;

/**
 *
 * @author EduardoPC
 */
public class Proveedor {

    private int idProveedor;
    private String nombre;
    private String correo;
    private String telefono;
    private String ruc;

    public Proveedor() {
    }

    public Proveedor(int idProveedor, String nombre, String correo, String telefono, String ruc) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.ruc = ruc;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @Override
    public String toString() {
        return nombre; 
    }

}
