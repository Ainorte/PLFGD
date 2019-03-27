package unice.plfgd.common.forme;


import java.util.ArrayList;
import java.util.List;

public class RecogForme {


	public static List<Object> process(List<Point> pts) {
		List<Point> sanitized = TraitementPoints.sanitize(pts, 0.02);

		List<Point >convexHull = TraitementPoints.refineEndPoints(sanitized, 2);
		convexHull = TraitementPoints.closeStroke(convexHull);
		convexHull = ConvexHull.getConvexHull(convexHull);
		if (convexHull == null) {
			List<Object> obj = new ArrayList<>();
			obj.add(Forme.UNKNOWN);
			obj.add(new Inconnu(pts));
			return obj;
		}
		List<Point> encTriangle = TraitementPoints.maximumAreaEnclosedTriangle(convexHull);
		List<Point> encRectangle = RotatingCalipers.getMinimumBoundingRectangle(convexHull);
		return recog(sanitized, convexHull, encTriangle, encRectangle);
	}

	public static List<Object> recog(List<Point> pts , List<Point> convexHull, List<Point> encTriangle, List<Point> encRectangle) {

		List<Object> res = new ArrayList<>();

		List<Double> angle = getBigAngle(convexHull);
		int angleSize = angle.size();
		System.out.println("ANGLE NUMBER: "  + angleSize);

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

		if (thinnessRatio > 50) {
			res.add(Forme.SEGMENT);
			res.add(new Segment(convexHull.get(0), convexHull.get(convexHull.size() - 1)));
		} else if (thinnessRatio < 12.5 && angleSize < 1) {
			res.add(Forme.CIRCLE);
			res.add(new Cercle(MethodesForme.barycentre(convexHull), rectanglePerim / (2 * Math.PI), 0));
		} else if (convexTriangleRatio > 0.7 & convexTriangleRatio < 1 && angleSize < 4) {
			res.add(Forme.TRIANGLE);
			res.add(triangle);
		} else if (convexRectangleRatio > 0.85 & convexRectangleRatio < 1.25 && angleSize < 5) {
			if (convexRectangleRatio > 0.9 & convexRectangleRatio < 1.1) {
				Point G = MethodesForme.barycentre(convexHull);
				Carre carre = new Carre(G, rectanglePerim / 4, 0);
				res.add(Forme.SQUARE);
				res.add(carre);
			} else {
				res.add(Forme.RECTANGLE);
				res.add(rectangle);
			}
		} else {
			res.add(Forme.UNKNOWN);
			res.add(new Inconnu(convexHull));
		}
		return res;
	}



	public static List<Double> getBigAngle(List<Point> pointList){

	List<Double> angleList = new ArrayList<>();
		for (int i = 1; i < pointList.size()-1; i++) {
			Point P1 = pointList.get(i-1);
			Point P2 = pointList.get(i);
			Point P3 = pointList.get(i+1);

			if(Math.abs(P1.getxV()) > Math.abs(P2.getxV()) && Math.abs(P2.getxV()) < Math.abs(P3.getxV()) &&
					Math.abs(P1.getyV()) > Math.abs(P2.getyV()) && Math.abs(P2.getyV()) < Math.abs(P3.getyV())	)
			{
				double angle = Math.atan2(P3.getY() - P1.getY(), P3.getX() - P1.getX()) - Math.atan2(P2.getY() - P1.getY(), P2.getX() - P1.getX());


				if (Math.abs(angle) >= Math.PI / 6) {
					angleList.add(angle);
				}
			}
		}

		return angleList;


	}
}
