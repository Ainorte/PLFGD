package unice.plfgd.common.forme;

import java.util.*;

public class RecogForme {


    public static List<Object> process(List<Point> pts){
        pts = TraitementPoints.sanitize(pts,0.02);
        pts = TraitementPoints.refineEndPoints(pts,2);
        List<Point> convexHull = ConvexHull.getConvexHull(pts);
        if(convexHull == null){
            List<Object> obj = new ArrayList<>();
            obj.add("Segment");
            obj.add(new Segment(pts.get(0),pts.get(pts.size()-1)));
            return obj;
        }
        List<Point> encTriangle = TraitementPoints.maximumAreaEnclosedTriangle(convexHull);
        List<Point> encRectangle = TraitementPoints.minimumAreaEnclosingRectangle(convexHull);
        return recog(convexHull, encTriangle, encRectangle);
    }

    public static List<Object> recog(List<Point> convexHull, List<Point> encTriangle, List<Point> encRectangle){

        List<Object> res = new ArrayList<>();

        Triangle triangle = new Triangle(new Point(0,0),encTriangle.get(0),encTriangle.get(1),encTriangle.get(2),0);
        double triangleArea = triangle.getAire();
        Quadrilatere rectangle = new Quadrilatere(encRectangle.get(0),encRectangle.get(1),encRectangle.get(2),encRectangle.get(3));
        double rectangleArea = rectangle.getAire();
        double rectanglePerim = rectangle.getPerim();

        double convexHullArea = 0;
        double convexHullPerim = 0;
        for(int i = 0; i < convexHull.size()-2; i++){
            double x0 = convexHull.get(i).getX();
            double y0 = convexHull.get(i).getY();
            double x1 = convexHull.get(i+1).getX();
            double y1 = convexHull.get(i+1).getY();
            convexHullArea += x0 * y1 - x1 * y0;
            convexHullPerim += MethodesForme.norme(convexHull.get(i), convexHull.get(i+1));
        }
        convexHullArea = 0.5 * Math.abs(convexHullArea);
        System.out.println("convexHullArea = " + convexHullArea);
        double thinnessRatio = convexHullPerim * convexHullPerim / convexHullArea;
        System.out.println("thinness ratio = " + thinnessRatio);
        double convexTriangleRatio = triangleArea / convexHullArea;
        System.out.println("convexTriangleRatio = " + convexTriangleRatio);
        double convexRectangleRatio = convexHullPerim / rectanglePerim;
        System.out.println("convexRectangleRatio = " + convexRectangleRatio);

        if(thinnessRatio > 50) {
            res.add("segment");
            res.add(new Segment(convexHull.get(0), convexHull.get(convexHull.size() - 1)));
        } else if(convexTriangleRatio > 0.7 & convexTriangleRatio < 1) {
            res.add("triangle");
            res.add(triangle);
        } else if(convexRectangleRatio > 0.85 & convexRectangleRatio < 1.25){
            if(convexRectangleRatio > 0.9 & convexRectangleRatio < 1.1){
                Point G = MethodesForme.barycentre(convexHull);
                Carre carre = new Carre(G, rectanglePerim/4, 0);
                res.add("carre");
                res.add(carre);
            } else {
                res.add("rectangle");
                res.add(rectangle);
            }
        } else if(thinnessRatio < 12.5){
            res.add("cercle");
            res.add(new Cercle(MethodesForme.barycentre(convexHull),MethodesForme.norme(rectangle.getA(),rectangle.getB())/2,0));
        }
        return res;
    }
}