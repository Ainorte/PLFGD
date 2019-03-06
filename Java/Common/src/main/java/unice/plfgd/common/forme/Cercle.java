package unice.plfgd.common.forme;

import java.util.List;
import java.util.ArrayList;

public class Cercle extends Ellipse {

    protected Point G;

    Cercle(Point G, double a, double rot){
        super(G, a, a, rot);
        this.G = G;
    }

    public double getAire(){
        return 2 * Math.PI * a;
    }

    public double getPerim(){
        return Math.PI * Math.sqrt(4 * a * a);
    }

    public String toString(){
        return super.toString();
    }

    public List<Point> make(){
        double nbPoints = this.getPerim()/10;
        double angleParPoints = nbPoints != 0 ? Math.toRadians(360 / nbPoints) : 0;
        List<Point> listPts = new ArrayList<>();
        for(int i = 0; i < nbPoints+1; i++){
            double x = this.G.getX() + this.a * Math.cos(i * angleParPoints);
            double y = this.G.getY() + this.a * Math.sin(i * angleParPoints);
            listPts.add(new Point(x, y));
        }

        return listPts;

    }

    public static void main(String[] args) {
        Cercle test1 = new Cercle(new Point(6, 0), 300, 0);
        System.out.println(test1.toString());
        System.out.println(test1.make());
    }
}
