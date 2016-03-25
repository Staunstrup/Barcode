package staunstrups.dk.bicyclestands;

/**
 * Created by jst on 3/24/16.
 */
public class Position {
    private double la, lo;
    public Position(double la, double lo, String where) {setLa(la); setLo(lo); setWhere(where);}
    public String getWhere() {return where; }
    public void setWhere(String where) { this.where = where;  }
    private String where;
    public double getLa() { return la; }
    public void setLa(double la) { this.la = la; }
    public double getLo() { return lo; }
    public void setLo(double lo) { this.lo = lo; }

    @Override
    public String toString() {return where+" la= "+la+", lo= "+lo; }
}