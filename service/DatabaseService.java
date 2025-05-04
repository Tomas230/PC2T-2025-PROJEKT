
package service;

import model.Student;
import java.sql.*;
import java.util.*;

public class DatabaseService {
    private static final String URL = "jdbc:sqlite:studenti.db";

    public DatabaseService() {
        try (Connection c = DriverManager.getConnection(URL);
             Statement st = c.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS studenti (" +
                       "id INTEGER PRIMARY KEY, meno TEXT, priezvisko TEXT," +
                       "datumNarodenia TEXT, znamky TEXT, typ TEXT)");
        } catch (Exception e) { }
    }

    public void ulozCelyZoznam(List<Student> zoznam) {
        try (Connection c = DriverManager.getConnection(URL);
             Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM studenti");
            for (Student s: zoznam) ulozStudenta(s);
        } catch(Exception e){}
    }

    public void ulozStudenta(Student s) {
        StringBuilder sb = new StringBuilder();
        List<Integer> z = s.getZnamky();
        for (int i=0;i<z.size();i++){ sb.append(z.get(i)); if(i<z.size()-1) sb.append(","); }
        String znamky = sb.toString();

        String sql = "INSERT INTO studenti (id,meno,priezvisko,datumNarodenia,znamky,typ) VALUES (?,?,?,?,?,?)";
        try (Connection c = DriverManager.getConnection(URL);
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, s.getId());
            p.setString(2, s.getMeno());
            p.setString(3, s.getPriezvisko());
            p.setString(4, s.getDatumNarodenia().toString());
            p.setString(5, znamky);
            p.setString(6, s instanceof model.TelekomStudent ? "T":"K");
            p.executeUpdate();
        } catch(Exception e){}
    }

    public List<Student> nacitajStudentov() {
        List<Student> list = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(URL);
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM studenti")) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String meno = rs.getString("meno");
                String priezvisko = rs.getString("priezvisko");
                java.sql.Date dat = java.sql.Date.valueOf(rs.getString("datumNarodenia"));
                String typ = rs.getString("typ");
                Student s = typ.equals("T") ?
                    new model.TelekomStudent(id, meno, priezvisko, dat) :
                    new model.KyberStudent(id, meno, priezvisko, dat);
                String znamky = rs.getString("znamky");
                if (znamky!=null && !znamky.isEmpty()) {
                    for (String zz: znamky.split(",")) s.pridajZnamku(Integer.parseInt(zz));
                }
                list.add(s);
            }
        } catch(Exception e){}
        return list;
    }
}
