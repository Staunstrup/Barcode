package staunstrups.dk.bicyclestands;

/**
 * Created by jst on 3/24/16.
 */
public class Position {
    public double getLa() { return la; }
    public void setLa(double la) { this.la = la; }
    public double getLo() { return lo; }
    public void setLo(double lo) { this.lo = lo; }
    private double la, lo;
    public Position(double la, double lo) {setLa(la); setLo(lo);}

    @Override
    public String toString() {return "la= "+la+", lo= "+lo; }
}