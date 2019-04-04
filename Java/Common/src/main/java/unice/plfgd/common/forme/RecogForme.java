package unice.plfgd.common.forme;


import java.util.ArrayList;
import java.util.List;

public class RecogForme {


	public static List<Object> process(List<Point> pts) {
		List<Point> sanitized = TraitementPoints.sanitize(pts, 0.02);

		List<Point >convexHull = TraitementPoints.refineEndPoints(sanitized, 2);
		convexHull = TraitementPoints.closeStroke(convexHull);
		convexHull = ConvexHull.getConvexHull(convexHull);
        if(convexHull == null){
            List<Object> obj = new ArrayList<>();
            obj.add(Forme.SEGMENT);
            obj.add(new Segment(pts.get(0), pts.get(pts.size()-1)));
            return obj;
        }
		List<Point> encTriangle = TraitementPoints.maximumAreaEnclosedTriangle(convexHull);
		List<Point> encRectangle = TraitementPoints.minimumAreaEnclosingRectangle(convexHull);
		return recog(sanitized, convexHull, encTriangle, encRectangle);
	}

	public static List<Object> recog(List<Point> pts , List<Point> convexHull, List<Point> encTriangle, List<Point> encRectangle) {

		List<Object> res = new ArrayList<>();

		List<Object> angle = TraitementPoints.findVertices(convexHull);
		int angleSize = (int) angle.get(2);
		//System.out.println("ANGLE NUMBER: "  + angleSize);

		Triangle triangle = new Triangle(new Point(0, 0), encTriangle.get(0), encTriangle.get(1), encTriangle.get(2), 0);
		double triangleArea = triangle.getAire();
		Quadrilatere rectangle = new Quadrilatere(encRectangle.get(0), encRectangle.get(1), encRectangle.get(2), encRectangle.get(3));
		double rectangleArea = rectangle.getAire();
		double rectanglePerim = rectangle.getPerim();

		double convexHullArea = 0;
		double convexHullPerim = 0;
		for (int i = 0; i < convexHull.size() - 2; i++) {
			double x0 = convexHull.get(i).getX();
			double y0 = convexHull.get(i).getY();
			double x1 = convexHull.get(i + 1).getX();
			double y1 = convexHull.get(i + 1).getY();
			convexHullArea += x0 * y1 - x1 * y0;
			convexHullPerim += MethodesForme.norme(convexHull.get(i), convexHull.get(i + 1));
		}
		convexHullArea = 0.5 * Math.abs(convexHullArea);
		double thinnessRatio = convexHullPerim * convexHullPerim / convexHullArea;
		double convexTriangleRatio = triangleArea / convexHullArea;
		double convexRectangleRatio = convexHullPerim / rectanglePerim;


		//Print ratio:
		System.out.println("Thinness Ratio " + thinnessRatio);
		System.out.println("Convex Triangle Ratio " + convexTriangleRatio);
		System.out.println("Convex Rectangle Ratio " + convexRectangleRatio);



		if (thinnessRatio < 2) {
			res.add(Forme.SEGMENT);
			res.add(new Segment(convexHull.get(0), convexHull.get(convexHull.size() - 1)));
		} else if (convexTriangleRatio > 0.9 && convexTriangleRatio < 2.4
					&& convexRectangleRatio < 0.90) {
			res.add(Forme.TRIANGLE);
			res.add(triangle);
		} else if (convexRectangleRatio > 0.80 && convexRectangleRatio < 1.25
				&& convexTriangleRatio < 1.3) {
			if (convexRectangleRatio > 0.95 && convexRectangleRatio < 1.05
				&& convexTriangleRatio < 1) {
				Point G = MethodesForme.barycentre(convexHull);
				Carre carre = new Carre(G, rectanglePerim / 4, 0);
				res.add(Forme.SQUARE);
				res.add(carre);
			} else {
				res.add(Forme.RECTANGLE);
				res.add(rectangle);
			}
        } else if (thinnessRatio > 5
				&& convexRectangleRatio > 0.5 && convexRectangleRatio < 1.25
				&& convexTriangleRatio > 0.5 && convexTriangleRatio < 1.1
				&& angleSize < 5) {
            res.add(Forme.CIRCLE);
            res.add(new Cercle(MethodesForme.barycentre(convexHull), rectanglePerim / (2 * Math.PI), 0));
		} else {
			res.add(Forme.UNKNOWN);
			res.add(new Inconnu(convexHull));
		}
		return res;
	}
}
