package dto;

/**
 *
 * @author EduardoPC
 */
public class Categoria {
    private int idCat;
    private String codigoCat;
    private String descripcionCat;

    public Categoria(int idCat, String codigoCat, String descripcionCat) {
        this.idCat = idCat;
        this.codigoCat = codigoCat;
        this.descripcionCat = descripcionCat;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getCodigoCat() {
        return codigoCat;
    }

    public void setCodigoCat(String codigoCat) {
        this.codigoCat = codigoCat;
    }

    public String getDescripcionCat() {
        return descripcionCat;
    }

    public void setDescripcionCat(String descripcionCat) {
        this.descripcionCat = descripcionCat;
    }
}