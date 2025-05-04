
package model;

import java.util.*;

public class TelekomStudent extends Student {
    public TelekomStudent(int id, String meno, String priezvisko, Date datum) {
        super(id, meno, priezvisko, datum);
    }

    private static final Map<Character,String> M = Map.ofEntries(
            Map.entry('A',".-"),Map.entry('B',"-..."),Map.entry('C',"-.-."),
            Map.entry('D',"-.."),Map.entry('E',"."),Map.entry('F',"..-."),
            Map.entry('G',"--."),Map.entry('H',"...."),Map.entry('I',".."),
            Map.entry('J',".---"),Map.entry('K',"-.-"),Map.entry('L',".-.."),
            Map.entry('M',"--"),Map.entry('N',"-."),Map.entry('O',"---"),
            Map.entry('P',".--."),Map.entry('Q',"--.-"),Map.entry('R',".-."),
            Map.entry('S',"..."),Map.entry('T',"-"),Map.entry('U',"..-"),
            Map.entry('V',"...-"),Map.entry('W',".--"),Map.entry('X',"-..-"),
            Map.entry('Y',"-.--"),Map.entry('Z',"--.."),Map.entry(' ',"/")
    );

    public String getTransformovaneMeno() {
        StringBuilder sb = new StringBuilder();
        String s = (meno + " " + priezvisko).toUpperCase();
        for (int i=0;i<s.length();i++) {
            sb.append(M.getOrDefault(s.charAt(i), "")).append(" ");
        }
        return sb.toString().trim();
    }
}
