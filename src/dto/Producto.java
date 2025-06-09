package dto;

/**
 *
 * @author EduardoPC
 */
public class Producto {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private double precioCompra;
    private int idInventario;
    private int stock;
    private int idProveedor;
    private int idMedida;
    private int idCategoria;
    private String nomCategoria;
    private String nombreProveedor;
    private String nombreMedida;
    private String nombreCortoMedida;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String descripcion, double precio, double precioCompra,
            int idInventario, int stock, int idProveedor, int idMedida, int idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precioCompra = precioCompra;
        this.idInventario = idInventario;
        this.stock = stock;
        this.idProveedor = idProveedor;
        this.idMedida = idMedida;
        this.idCategoria = idCategoria;
    }
    
    public Producto(int idProducto, String nombre, String descripcion, double precio, int idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idCategoria = idCategoria;
    }

    // Getters y setters de todos los campos
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(int idMedida) {
        this.idMedida = idMedida;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreMedida() {
        return nombreMedida;
    }

    public void setNombreMedida(String nombreMedida) {
        this.nombreMedida = nombreMedida;
    }

    public String getNombreCortoMedida() {
        return nombreCortoMedida;
    }

    public void setNombreCortoMedida(String nombreCortoMedida) {
        this.nombreCortoMedida = nombreCortoMedida;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
