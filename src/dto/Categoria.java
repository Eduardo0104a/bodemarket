package dto;

/**
 *
 * @author EduardoPC
 */
public class Categoria {
    private int idCat;
    private String descripcionCat;

    public Categoria(int idCat, String codigoCat, String descripcionCat) {
        this.idCat = idCat;
        this.descripcionCat = descripcionCat;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getDescripcionCat() {
        return descripcionCat;
    }

    public void setDescripcionCat(String descripcionCat) {
        this.descripcionCat = descripcionCat;
    }
}