package unice.plfgd.common.forme.generation;

import java.util.ArrayList;
import java.util.List;

import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.forme.Point;
import unice.plfgd.common.forme.forme.Triangle;
import unice.plfgd.common.forme.method.utils;

public class GenerationFormes {


	public static List<Point> generateStandRandForme(double l, double h) {
		List<Point> randForme = new ArrayList<>();
		int random = (int) (Math.random() * 4 + 1);
		Point G = new Point(l / 2, h / 2);
		switch (random) {
			case 1:
				List<Object> objCarre = new ArrayList<>();
				objCarre.add(Forme.SQUARE);
				objCarre.add(G);
				objCarre.add(l / 2);
				objCarre.add(0.0);
				randForme = FormeFactory.make(objCarre);
				break;
			case 2:
				List<Object> objRect = new ArrayList<>();
				objRect.add(Forme.RECTANGLE);
				objRect.add(G);
				objRect.add(l / 2);
				objRect.add(h / 2);
				objRect.add(0.0);
				randForme = FormeFactory.make(objRect);
				break;
			case 3:
				List<Object> objCercle = new ArrayList<>();
				objCercle.add(Forme.CIRCLE);
				objCercle.add(G);
				objCercle.add(l / 4);
				objCercle.add(0.0);
				randForme = FormeFactory.make(objCercle);
				break;
			case 4:
				List<Point> objTriangle = new ArrayList<>();
				Point A = new Point(l / 4, 3 * h / 4);
				Point B = new Point(l / 2, 3 * h / 4);
				Point C = new Point(3 * l / 4, 3 * h / 4);
				objTriangle.add(A);
				objTriangle.add(B);
				objTriangle.add(C);
				List<Object> objTriangle2 = new ArrayList<>();
				objTriangle2.add(Forme.TRIANGLE);
				objTriangle2.add(G);
				objTriangle2.addAll(objTriangle);
				objTriangle2.add(0.0);
				randForme = FormeFactory.make(objTriangle2);
				break;
			default:
				System.out.println("error");
		}
		return randForme;
	}

	public static List<Point> generateEnumForme(Forme forme, double l, double h){
        List<Point> randForme = new ArrayList<>();
        Point G = new Point(l / 2, h / 2);
        switch (forme) {
            case SQUARE:
                List<Object> objCarre = new ArrayList<>();
                objCarre.add(forme);
                objCarre.add(G);
                objCarre.add(l / 2);
                objCarre.add(0.0);
                randForme = FormeFactory.make(objCarre);
                break;
            case RECTANGLE:
                List<Object> objRect = new ArrayList<>();
                objRect.add(forme);
                objRect.add(G);
                objRect.add(l / 2);
                objRect.add(h / 2);
                objRect.add(0.0);
                randForme = FormeFactory.make(objRect);
                break;
            case CIRCLE:
                List<Object> objCercle = new ArrayList<>();
                objCercle.add(forme);
                objCercle.add(G);
                objCercle.add(l / 4);
                objCercle.add(0.0);
                randForme = FormeFactory.make(objCercle);
                break;
            case TRIANGLE:
                List<Point> objTriangle = new ArrayList<>();
                Point A = new Point(l / 4, 3 * h / 4);
                Point B = new Point(l / 2, h / 4);
                Point C = new Point(3 * l / 4, 3 * h / 4);
                objTriangle.add(A);
                objTriangle.add(B);
                objTriangle.add(C);
                Triangle triangle = new Triangle(objTriangle);
                List<Object> objTriangle2 = new ArrayList<>();
                objTriangle2.add(forme);
                objTriangle2.add(triangle);
                randForme = FormeFactory.make(objTriangle2);
                break;
            default:
                System.out.println("error");
        }
        return randForme;
    }


	public static Forme randEnumForme(){
		Forme[] arr = new Forme[]{Forme.CIRCLE, Forme.RECTANGLE, Forme.TRIANGLE, Forme.SQUARE};
		int random = (int) (Math.random() * 4);
		return arr[random];
	}

	public static List<Point> generateRandomForme(double l, double h, boolean isCentered, boolean isRotated) {
		List<Point> randForme = new ArrayList<>();
		double randRotation = 0;
		if (isRotated) randRotation = randRotation();
		int random = (int) (Math.random() * 4 + 1);
		switch (random) {
			case 1:
				List<Object> objCarre = new ArrayList<>();
				objCarre.add(Forme.SQUARE);
				Point randG = isCentered ? new Point(l / 2, h / 2) : randG(l, h);
				double randL = randL(randG, l, h);
				objCarre.add(randG);
				objCarre.add(randL);
				objCarre.add(randRotation);
				randForme = FormeFactory.make(objCarre);
				break;
			case 2:
				List<Object> objRect = new ArrayList<>();
				objRect.add(Forme.RECTANGLE);
				Point randG2 = isCentered ? new Point(l / 2, h / 2) : randG(l, h);
				double randL2 = randL(randG2, l, h);
				double randH2 = randL(randG2, l, h);
				objRect.add(randG2);
				objRect.add(randL2);
				objRect.add(randH2);
				objRect.add(randRotation);
				randForme = FormeFactory.make(objRect);
				break;
			case 3:
				List<Object> objCercle = new ArrayList<>();
				objCercle.add(Forme.CIRCLE);
				Point randG3 = isCentered ? new Point(l / 2, h / 2) : randG(l, h);
				double randL3 = randL(randG3, l / 2, h / 2);
				objCercle.add(randG3);
				objCercle.add(randL3);
				objCercle.add(0.0);
				randForme = FormeFactory.make(objCercle);
				break;
			case 4:
				List<Point> objTriangle = new ArrayList<>();
				Point A = randPoint(l, h);
				Point B = randPoint(l, h);
				Point C = randPoint(l, h);
				objTriangle.add(A);
				objTriangle.add(B);
				objTriangle.add(C);
				Point G = utils.barycentre(objTriangle);
				List<Object> objTriangle2 = new ArrayList<>();
				objTriangle2.add(Forme.TRIANGLE);
				objTriangle2.add(G);
				objTriangle2.addAll(objTriangle);
				objTriangle2.add(randRotation);
				randForme = FormeFactory.make(objTriangle2);
				break;
			default:
				System.out.println("error");
		}
		return randForme;
	}

	public static Point randPoint(double l, double h) {
		double randX = Math.random() * (2 * l / 3) + l / 3;
		double randY = Math.random() * (2 * h / 3) + h / 3;
		return new Point(randX, randY);
	}

	public static Point randG(double l, double h) {
		double maxLarg = l - l / 3;
		double minLarg = l / 3;
		double maxHaut = h - h / 3;
		double minHaut = h / 3;
		double randL = Math.random() * (maxLarg - minLarg) + minLarg;
		double randH = Math.random() * (maxHaut - minHaut) + minHaut;
		return new Point(randL, randH);
	}

	public static double randL(Point G, double l, double h) {
		double maxSize = Math.min(l, h);
		double minDistToEdge = Math.min(l - G.getX(), h - G.getY());
		double randL = Math.random() * (maxSize - minDistToEdge) + minDistToEdge;
		return randL;
	}

	public static double randRotation() {
		return Math.random() * 360 + 1;
	}
}
