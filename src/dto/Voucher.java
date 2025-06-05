package dto;

public class Voucher {

    private int id;
    private Venta venta;
    private String tipo;
    private String numero;

    public Voucher() {
    }

    public Voucher(int id, Venta venta, String tipo, String numero) {
        this.id = id;
        this.venta = venta;
        this.tipo = tipo;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
