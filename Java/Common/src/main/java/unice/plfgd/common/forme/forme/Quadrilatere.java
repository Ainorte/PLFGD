package unice.plfgd.common.forme.forme;

import unice.plfgd.common.forme.generation.GenerationPoints;
import unice.plfgd.common.forme.method.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quadrilatere {

	protected Point A;
	protected Point B;
	protected Point C;
	protected Point D;

	public Quadrilatere(Point A, Point B, Point C, Point D) {
		this.A = A;
		this.B = B;
		this.C = C;
		this.D = D;
	}

	public Quadrilatere(List<Point> pointList){
		this.A = pointList.get(0);
		this.B = pointList.get(1);
		this.C = pointList.get(2);
		this.D = pointList.get(3);
	}


	public Point getA() {
		return A;
	}

	public Point getB() {
		return B;
	}

	public Point getC() {
		return C;
	}

	public Point getD() {
		return D;
	}

	public Point getG() {
		Point[] ptsArray = new Point[]{A, B, C, D};
		List<Point> pts = Arrays.asList(ptsArray);
		return utils.barycentre(pts);
	}

	public double getAire() {
		double distAB = utils.norme(A, B);
		double distBC = utils.norme(B, C);
		double distCD = utils.norme(C, D);
		double distAD = utils.norme(A, D);
		double angleA = Math.toRadians(utils.findAngle(A, B, D));
		double angleD = Math.toRadians(utils.findAngle(D, A, C));
		return 0.5 * distAB * distAD * Math.sin(angleA) + 0.5 * distBC * distCD * Math.sin(angleD);

	}

	public double getPerim() {
		double distAB = utils.norme(A, B);
		double distBC = utils.norme(B, C);
		double distCD = utils.norme(C, D);
		double distAD = utils.norme(A, D);
		return distAB + distBC + distCD + distAD;
	}

	@Override
	public String toString() {
		return "Quadrilatere :\n" + "Centre : " + this.getG() + "\n Aire : " + this.getAire() + "\n Perim :" + this.getPerim();
	}

	public List<Point> make() {
		Segment[] segs = new Segment[]{new Segment(A, B), new Segment(B, C), new Segment(C, D), new Segment(D, A)};
		List<Point> listPts = new ArrayList<>();
		for (Segment seg : segs) listPts.addAll(GenerationPoints.generatePtsFromSeg(seg, 10));
		return listPts;
	}
}
