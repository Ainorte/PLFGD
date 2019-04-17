package unice.plfgd.common.forme.method;


import java.util.ArrayList;
import java.util.List;

import unice.plfgd.common.forme.forme.Carre;
import unice.plfgd.common.forme.forme.Cercle;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.forme.Inconnu;
import unice.plfgd.common.forme.forme.Point;
import unice.plfgd.common.forme.forme.Quadrilatere;
import unice.plfgd.common.forme.forme.Segment;
import unice.plfgd.common.forme.forme.Triangle;
import unice.plfgd.common.forme.forme.Ellipse;

public class RecogForme {


	public static List<Object> process(List<Point> pts) {
	    if(pts.isEmpty()) {
            List<Object> objNull = new ArrayList<>();
            objNull.add(Forme.UNKNOWN);
            objNull.add(new Inconnu(pts));
            return objNull;
        }
		List<Point> sanitized = TraitementPoints.sanitize(pts, 0.05);

		List<Point> convexHull = TraitementPoints.refineEndPoints(sanitized, 2);
		convexHull = TraitementPoints.closeStroke(convexHull);
		convexHull = ConvexHull.getConvexHull(convexHull);
		if (convexHull == null) {
			List<Object> obj = new ArrayList<>();
			obj.add(Forme.SEGMENT);
			obj.add(new Segment(pts.get(0), pts.get(pts.size() - 1)));
			return obj;
		}
		List<Point> encTriangle = TraitementPoints.maximumAreaEnclosedTriangle(convexHull);
		List<Point> encRectangle = TraitementPoints.minimumAreaEnclosingRectangle(convexHull);
		return recog(sanitized, convexHull, encTriangle, encRectangle);
	}

	public static List<Object> recog(List<Point> pts, List<Point> convexHull, List<Point> encTriangle, List<Point> encRectangle) {

		List<Object> res = new ArrayList<>();
        double distStartToEnd = utils.norme(pts.get(0),pts.get(pts.size()-1));

		Triangle triangle = new Triangle(new Point(0, 0), encTriangle.get(0), encTriangle.get(1), encTriangle.get(2), 0);
		double triangleArea = triangle.getAire();
		Quadrilatere rectangle = new Quadrilatere(encRectangle.get(0), encRectangle.get(1), encRectangle.get(2), encRectangle.get(3));
		double rectangleArea = rectangle.getAire();
		double rectanglePerim = rectangle.getPerim();
		Ellipse ellipse = new Ellipse(new Point(0,0), utils.norme(encRectangle.get(0), encRectangle.get(1)),utils.norme(encRectangle.get(1),encRectangle.get(2)), 0);
		double ellipseArea = ellipse.getAire();

		double convexHullArea = 0;
		double convexHullPerim = 0;
		for (int i = 0; i < convexHull.size() - 2; i++) {
			double x0 = convexHull.get(i).getX();
			double y0 = convexHull.get(i).getY();
			double x1 = convexHull.get(i + 1).getX();
			double y1 = convexHull.get(i + 1).getY();
			convexHullArea += x0 * y1 - x1 * y0;
			convexHullPerim += utils.norme(convexHull.get(i), convexHull.get(i + 1));
		}
		convexHullArea = 0.5 * Math.abs(convexHullArea);
		double convexHullArea2 = utils.polygonArea(convexHull);
		double thinnessRatio = convexHullPerim * convexHullPerim / convexHullArea2;
		double convexTriangleRatio = triangleArea / convexHullArea2;
		double convexRectangleRatio = convexHullPerim / rectanglePerim;
		//double ratioCHellipse = convexHullArea2/ellipseArea;
		double ratioCHrectangle = convexHullArea2/rectangleArea;



		if (thinnessRatio > 50 || distStartToEnd > 150) {
			res.add(Forme.SEGMENT);
			res.add(new Segment(pts.get(0), pts.get(pts.size() - 1)));
		} else if (convexTriangleRatio > 1.6 && convexTriangleRatio < 2.4
			&& convexRectangleRatio < 0.89) {
			res.add(Forme.TRIANGLE);
			res.add(triangle);
		} else if (convexRectangleRatio > 0.57 && convexRectangleRatio < 1.25 && ratioCHrectangle > 0.83) {
			if (convexRectangleRatio > 0.95 && convexRectangleRatio < 1.05) {
				Point G = utils.barycentre(convexHull);
				Carre carre = new Carre(G, rectanglePerim / 4, 0);
				res.add(Forme.SQUARE);
				res.add(carre);
			} else {
				res.add(Forme.RECTANGLE);
				res.add(rectangle);
			}
		} else if (thinnessRatio > 1
			&& convexRectangleRatio > 0.45 && convexRectangleRatio < 1.25
			&& convexTriangleRatio > 0.43 && convexTriangleRatio < 1.5) {
			res.add(Forme.CIRCLE);
			res.add(new Cercle(utils.barycentre(convexHull), Math.sqrt(convexHullArea / Math.PI), 0));
		} else {
			res.add(Forme.UNKNOWN);
			res.add(new Inconnu(convexHull));
		}
		return res;
	}
}
