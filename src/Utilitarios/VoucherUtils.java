package Utilitarios;

/**
 *
 * @author EduardoPC
 */
public class VoucherUtils {

    public static String generarCodigoVoucher() {
        String fecha = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        int randomNum = new java.util.Random().nextInt(9000) + 1000; 
        return "VCH" + fecha + randomNum;
    }
}

