
package model;

import java.security.*;

public class KyberStudent extends Student {
    public KyberStudent(int id, String meno, String priezvisko, java.util.Date datum) {
        super(id, meno, priezvisko, datum);
    }

    public String getTransformovaneMeno() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] h = md.digest((meno+priezvisko).getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b: h) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch(Exception e){ return meno + " " + priezvisko;}
    }
}
