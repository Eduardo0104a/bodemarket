package dto;

/**
 *
 * @author Eduardo
 */
public class Medida {
    private int idMedida;
    private String nombre;
    private String nombreCorto;

    public Medida(int idMedida, String nombre, String nombreCorto) {
        this.idMedida = idMedida;
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
    }

    public int getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(int idMedida) {
        this.idMedida = idMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }
    
    @Override
    public String toString() {
        return  nombreCorto;
    }
}
