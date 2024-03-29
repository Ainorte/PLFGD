import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;

public class Carre extends Rectangle {

    public Carre(Point G, double l, double rot){
        super(G, l, l, rot);
    }

    public double getAire(){ 
        return l * l;
    }

    public double getPerim(){
        return 4 * l;
    }

    public String toString(){
        return super.toString();
    }


    public List<Point> make(){
        //Point[] make() à l'avenir quand on aura défini l'écart entre chaque point/pixel
        Point A = new Point(G.getX() - l/2, G.getY() + l/2);
        Point B = new Point(G.getX() + l/2, G.getY() + l/2);
        Point C = new Point(G.getX() + l/2, G.getY() - l/2);
        Point D = new Point(G.getX() - l/2, G.getY() - l/2);
        Segment[] segmentsCarre = new Segment[]{new Segment(A,B), new Segment(B,C), new Segment(C,D), new Segment(D,A)};
        for(Segment seg : segmentsCarre) seg.rotation(this.getG(), rot);
        List<Point> listPts = new ArrayList<>();
        for(Segment seg : segmentsCarre) listPts.addAll(GenerationPoints.generatePtsFromSeg(seg, 10));
        return listPts;
    }

    public static void main(String[] args) {
        Carre carre1 = new Carre(new Point(5,5), 420, 180);
        System.out.println(carre1.getG().getX());
        System.out.println(carre1.getAire());
        System.out.println(carre1.getPerim());
        System.out.println(carre1.toString());
        carre1.make();
        System.out.println(carre1.make());
    }
}