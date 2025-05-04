
package model;

import java.util.*;

public abstract class Student {
    protected int id;
    protected String meno;
    protected String priezvisko;
    protected Date datumNarodenia;
    protected List<Integer> znamky = new ArrayList<>();

    public Student(int id, String meno, String priezvisko, Date datumNarodenia) {
        this.id = id;
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.datumNarodenia = datumNarodenia;
    }

    public void pridajZnamku(int z) {
        if (z >= 1 && z <= 5) znamky.add(z);
    }

    public double getPriemerZnamok() {
        if (znamky.isEmpty()) return 0;
        int s = 0;
        for (int z : znamky) s += z;
        return (double)s / znamky.size();
    }

    public int getId() { return id; }
    public String getMeno() { return meno; }
    public String getPriezvisko() { return priezvisko; }
    public Date getDatumNarodenia() { return datumNarodenia; }
    public List<Integer> getZnamky() { return znamky; }

    public abstract String getTransformovaneMeno();
}
