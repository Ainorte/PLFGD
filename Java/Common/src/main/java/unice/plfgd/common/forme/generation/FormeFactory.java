package unice.plfgd.common.forme.generation;

import unice.plfgd.common.forme.forme.*;

import java.util.ArrayList;
import java.util.List;

public class FormeFactory {

	static public List<Point> make(List<Object> forme) {

		List<Point> listForme = new ArrayList<>();
		Forme formeType = (Forme) forme.get(0);

		if (forme.size() > 2) {
			switch (formeType) {
				case SEGMENT:
					listForme = new Segment((Point) forme.get(1), (Point) forme.get(2)).make();
					break;
				case RECTANGLE:
					listForme = new Rectangle((Point) forme.get(1), (double) forme.get(2), (double) forme.get(3), (double) forme.get(4)).make();
					break;
				case SQUARE:
					listForme = new Carre((Point) forme.get(1), (double) forme.get(2), (double) forme.get(3)).make();
					break;
				case TRIANGLE:
					listForme = new Triangle((Point) forme.get(1), (Point) forme.get(2), (Point) forme.get(3), (Point) forme.get(4), (double) forme.get(5)).make();
					break;
				case ELLIPSE:
					//listForme = new Ellipse().make();
				case CIRCLE:
					listForme = new Cercle((Point) forme.get(1), (double) forme.get(2), (double) forme.get(3)).make();
					break;
				default:
					//System.out.println("error not such form");
			}
		} else {
			switch (formeType) {
				case SEGMENT:
					listForme = ((Segment) forme.get(1)).make();
					break;
				case RECTANGLE:
					listForme = ((Quadrilatere) forme.get(1)).make();
					break;
				case SQUARE:
					listForme = ((Carre) forme.get(1)).make();
					break;
				case TRIANGLE:
					listForme = ((Triangle) forme.get(1)).make();
					break;
				case ELLIPSE:
					//listForme = ((Ellipse)forme.get(1)).make();
				case CIRCLE:
					listForme = ((Cercle) forme.get(1)).make();
					break;
				case UNKNOWN:
					listForme = ((Inconnu) forme.get(1)).make();
				default:
					//System.out.println("error not such form");
			}
		}
		return listForme;
	}
}
