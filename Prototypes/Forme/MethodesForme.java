import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.*;

public class MethodesForme {

    static double toleranceAngle = 0.5;
    static double toleranceMoment = 0.5;

    static Point centreMasse(Point M1, Point M2){
       double xG = (M1.getX() + M2.getX())/2;
       double yG = (M1.getY() + M2.getY())/2;
       return new Point(xG, yG);
    }

    static Point centreMasse(double x1, double y1, double x2, double y2){
        double xG = (x1 + x2)/2;
        double yG = (y1 + y2)/2;
        return new Point(xG, yG);
    }

    // permet de "recentrer" l'image dessinée.
    // éviter de dire que deux images translatées l'une de l'autre sont différentes.
    /*static Point barycentre(List<Point> pts){
        double xB = pts.parallelStream().map(x -> x.getX()).reduce(0.0, (x1, x2) -> x1 + x2)/pts.size();
        double yB = pts.parallelStream().map(y -> y.getY()).reduce(0.0, (y1, y2) -> y1 + y2)/pts.size();
        return new Point(xB, yB);
    }*/

    /*static Point barycentre(List<Double> xn, List<Double> yn){
        double xB = xn.parallelStream().reduce(0.0, (x1, x2) -> x1 + x2)/xn.size();
        double yB = yn.parallelStream().reduce(0.0, (y1, y2) -> y1 + y2)/yn.size();
        return new Point(xB, yB);
    }*/

    static Point isobarycentre(List<Point> pts){
        double xG = calculeMoment(pts, 1, 0) / calculeMoment(pts, 0, 0);
        double yG = calculeMoment(pts, 0, 1) / calculeMoment(pts, 0, 0);
        return new Point(xG, yG);
    }

    static double findAngle(Point ptO, Point ptA, Point ptB){
        double OA = norme(ptO, ptA);
        double OB = norme(ptO, ptB);
        double AB = norme(ptA, ptB);
        return radToDeg(Math.acos((OA * OA + OB * OB - AB * AB) / (2 * OA * OB)));
    }

    static double norme(Point ptA, Point ptB){
        return Math.sqrt(Math.pow(ptA.getX()-ptB.getX(), 2) + Math.pow(ptA.getY()-ptB.getY(), 2));
    }

    static double norme(double xA, double yA, double xB, double yB){
        return Math.sqrt(Math.pow(xA-xB, 2) + Math.pow(yA - yB, 2));
    }

    static void translation(List<Point> pts, double x, double y){
        for (Point point : pts) {
            point.setX(point.getX() + x);
            point.setY(point.getY() + y);
        }
    }

    static void translation(List<Double> xn, List<Double> yn, double x, double y){
        for(int i = 0; i < xn.size(); i++){
            xn.set(i, xn.get(i) + x);
            yn.set(i, yn.get(i) + y);
        }
    }

    static double radToDeg(double rad){
        return rad * 180 / Math.PI;
    }

    static double degToRad(double deg){
        return deg * Math.PI / 180;
    }

    static Point rotation(Point pt, double x0, double y0, double rot){
        double new_x = (pt.getX() - x0) * Math.cos(rot) - (pt.getY() - y0) * Math.sin(rot) + x0;
        double new_y = (pt.getX() - x0) * Math.sin(rot) - (pt.getY() - y0) * Math.cos(rot) + y0;
        return new Point(new_x, new_y);
    }

    static double[] rotation(double x, double y, double x0, double y0, double rot){
        double new_x = (x - x0) * Math.cos(rot) - (y - y0) * Math.sin(rot) + x0;
        double new_y = (x - x0) * Math.sin(rot) - (y - y0) * Math.cos(rot) + y0;
        return new double[]{new_x, new_y};
    }

    static Point rotation_inverse(Point pt, double x0, double y0, double rot){
        double[] rotation = rotation(pt.getX(), pt.getY(), x0, y0, rot);
        double x_inv = rotation[0];
        double y_inv = rotation[1];
        double new_x = (x_inv - x0) * Math.cos(rot) + (y_inv - y0) * Math.sin(rot) + x0;
        double new_y = -(x_inv - x0) * Math.sin(rot) + (y_inv - y0) * Math.cos(rot) + y0;
        return new Point(new_x, new_y);
    }

    static Point rotation_inverse(double x, double y, double x0, double y0, double rot){
        double[] rotation = rotation(x, y, x0, y0, rot);
        double x_inv = rotation[0];
        double y_inv = rotation[1];
        double new_x = (x_inv - x0) * Math.cos(rot) + (y_inv - y0) * Math.sin(rot) + x0;
        double new_y = -(x_inv - x0) * Math.sin(rot) + (y_inv - y0) * Math.cos(rot) + y0;
        return new Point(new_x, new_y);
    }

    static List<Segment> axesSym(List<Point> pts, double tolerance){
        List<Segment> listAxesSym = new ArrayList<>();
        Point G = barycentre(pts);
        for(int i = 0; i < pts.size()-1; i++){
            for(int j = i+1; j < pts.size(); j++){
                Point A = pts.get(i);
                Point B = pts.get(j);
                Point M = centreMasse(A, B);
                if(!G.compareXY(M)){
                    if(Math.abs(findAngle(M, G, A) - 90) < tolerance) listAxesSym.add(new Segment(M, G));
                }
            }
        }
        return listAxesSym;
    }

    

    // A revoir
    static double distanceImages(List<Point> pts1, List<Point> pts2) {
        double dist = 0;
        for(Point pt1 : pts1){
            for(Point pt2 : pts2){
                dist += pt1.getX() - pt2.getX() + pt1.getY() - pt1.getY();
            }
        }
        return Math.abs(dist);
    }

    // A revoir
    static double distanceImagesP(List<Point> pts1, List<Point> pts2) {
        double p = 2;
        double dist = 0;
        for(Point pt1 : pts1){
            for(Point pt2 : pts2){
                dist += Math.pow(Math.pow(Math.abs(pt1.getX() - pt2.getX()), p) + Math.pow(Math.abs(pt1.getY() - pt1.getY()), p), 1/p);
            }
        }
        return Math.abs(dist);
    }

    static double calculeMoment(List<Point> pts, int p, int q){
        double m = 0;
        for(Point ptx : pts){
            for(Point pty : pts){
                m += Math.pow(ptx.getX(), p) * Math.pow(pty.getY(), q);
            }
        }
        return m;
    }

    static double calculeMomentCentre(List<Point> pts, int p, int q){
        double mc = 0;
        Point isobar = isobarycentre(pts);
        double xG = isobar.getX();
        double yG = isobar.getY();
        for(Point ptx : pts){
            for(Point pty : pts){
                mc += Math.pow(ptx.getX() - xG, p) * Math.pow(pty.getY() - yG, q);
            }
        }
        return mc;
    }

    static double[] momentsInvariants(List<Point> pts){
        double mc20 = calculeMomentCentre(pts, 2, 0);
        double mc02 = calculeMomentCentre(pts, 0, 2);
        double mc03 = calculeMomentCentre(pts, 0, 3);
        double mc11 = calculeMomentCentre(pts, 1, 1);
        double mc12 = calculeMomentCentre(pts, 1, 2);
        double mc21 = calculeMomentCentre(pts, 2, 1);
        double mc30 = calculeMomentCentre(pts, 3, 0);
        double mcA = mc20 + mc02;
        double mcB = Math.pow(mc20 - mc02, 2) + 4 * Math.pow(mc11, 2);
        double mcC = Math.pow(mc30 - 3 * mc12, 2) + Math.pow(3 * mc21 - mc03, 2);
        double mcD = Math.pow(mc30 + mc12, 2) + Math.pow(mc21 + mc03, 2);
        double mcE = (mc30 - 3 * mc12) * (mc30 + mc12) * (Math.pow(mc30 + mc12, 2) - 3 * Math.pow(mc21 + mc03, 2))
                    + (3 * mc21 - mc03) * (mc21 + mc03) * (3 * Math.pow(mc30 + mc12, 2) - Math.pow(mc21 + mc03, 2));
        double mcF = (mc20 - mc02) * (Math.pow(mc30 + mc12, 2) - Math.pow(mc21 + mc03, 2)) + 4 * mc11 * (mc30 + mc12) * (mc21 + mc03);
        double[] invariants = new double[]{mcA, mcB, mcC, mcD, mcE, mcF};
        return invariants;
    }

    static boolean compareImages(List<Point> pts1, List<Point> pts2, double tolerance){
        double[] invariants1 = momentsInvariants(pts1);
        double[] invariants2 = momentsInvariants(pts2);
        for(int i = 0; i < 6; i++){
            if(Math.abs(invariants1[i] - invariants2[i]) > tolerance) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Point a1 = new Point(2.5, 3.3);
        Point a2 = new Point(4.3, 5.4);
        Point O = new Point(0, 0);
        Point A = new Point(0, 5);
        Point B = new Point(5, 0);
        Point C = new Point(5, 5);
        Point D = new Point(3, 3);
        Point E = new Point(10, 5);
        Point F = new Point(5, 10);
        Point G = new Point(10, 10); 
        List<Point> pts1 = Arrays.asList(a1, a2);
        List<Point> triangleRect = Arrays.asList(O, A, B);
        List<Point> carre = Arrays.asList(O, A, B, C);
        List<Point> carre2 = Arrays.asList(C, E, F, G);
        List<Point> triangle = Arrays.asList(O, A, D);
        List<Double> xn = Arrays.asList(2.5, 4.3);
        List<Double> yn = Arrays.asList(3.3, 5.4);
        translation(pts1, 2, 2);
        translation(xn, yn, 2, 2);
        rotation(a1, 0, 0, 90);

        System.out.println(barycentre(pts1).getX());
        System.out.println(barycentre(xn, yn).getX());
        System.out.println("x, y après rotation de 90 : " + a1.getX() + ", " + a1.getY());
        //System.out.println(findAngle(O, A, B));
        System.out.println(axesSym(triangleRect, 0.5).size());
        System.out.println(axesSym(carre, 0.5).size());
        System.out.println(axesSym(triangle, 0.5).size());
        //System.out.println(axesSym(triangleRect).get(0).getP2().getX());
        //System.out.println(axesSym(triangleRect).get(0).getP2().getY());
        System.out.println(distanceImagesP(carre, triangleRect));
        System.out.println(calculeMomentCentre(triangle, 0, 1));
        System.out.println(momentsInvariants(carre)[0]);
        System.out.println(compareImages(carre, carre2, 0.5));
    }
}
