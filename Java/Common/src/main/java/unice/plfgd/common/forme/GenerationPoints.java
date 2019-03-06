package unice.plfgd.common.forme;

import java.util.ArrayList;
import java.util.List;

public class GenerationPoints {

	static public List<Point> generatePtsFromSeg(Segment seg, int pas) {
		List<Point> newListPts = new ArrayList<>();
		List<Double> listX = new ArrayList<>();
		List<Double> listY = new ArrayList<>();
		double x1 = seg.getP1().getX();
		double y1 = seg.getP1().getY();
		double x2 = seg.getP2().getX();
		double y2 = seg.getP2().getY();
		double nbPasX = (x1 - x2) != 0 ? Math.abs(x1 - x2) / pas : 0;
		double nbPasY = (y1 - y2) != 0 ? Math.abs(y1 - y2) / pas : 0;
		double maxNbPas = Math.max(nbPasX, nbPasY);
		double ratio = nbPasX < nbPasY ? nbPasY / nbPasX : nbPasX / nbPasY;
		double petitPas = pas / ratio;
		listX.add(x1);
		if (x1 < x2) {
			double x = x1;
			for (int i = 0; i < maxNbPas - 1; i++) {
				if (nbPasX >= nbPasY) {
					x += pas;
					listX.add(x);
				} else {
					x += petitPas;
					listX.add(x);
				}
			}
		} else {
			double x = x1;
			for (int i = 0; i < maxNbPas - 1; i++) {
				if (nbPasX >= nbPasY) {
					x -= pas;
					listX.add(x);
				} else {
					x -= petitPas;
					listX.add(x);
				}
			}
		}
		listY.add(y1);
		if (y1 < y2) {
			double y = y1;
			for (int i = 0; i < maxNbPas - 1; i++) {
				if (nbPasY >= nbPasX) {
					y += pas;
					listY.add(y);
				} else {
					y += petitPas;
					listY.add(y);
				}
			}
		} else {
			double y = y1;
			for (int i = 0; i < maxNbPas - 1; i++) {
				if (nbPasY >= nbPasX) {
					y -= pas;
					listY.add(y);
				} else {
					y -= petitPas;
					listY.add(y);
				}
			}
		}
		listX.add(x2);
		listY.add(y2);
		for (int i = 0; i < listX.size(); i++) newListPts.add(new Point(listX.get(i), listY.get(i)));
		return newListPts;
	}
}
