package unice.plfgd.common.forme;

import java.util.List;

public interface FormeGeometrique  {
    public Point getG();
    public void setG(double x, double y);
    public double getAire();
    public double getPerim();
    public List<Point> make();
    public String toString();
}

