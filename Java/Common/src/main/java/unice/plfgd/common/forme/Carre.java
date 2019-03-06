package unice.plfgd.common.forme;

import java.util.ArrayList;
import java.util.List;

public class Carre extends Rectangle {

	public Carre(Point G, double l, double rot) {
		super(G, l, l, rot);
	}

	public double getAire() {
		return l * l;
	}

	public double getPerim() {
		return 4 * l;
	}

	public String toString() {
		return super.toString();
	}


	public List<Point> make() {
		Point A = new Point(G.getX() - l / 2, G.getY() + l / 2);
		Point B = new Point(G.getX() + l / 2, G.getY() + l / 2);
		Point C = new Point(G.getX() + l / 2, G.getY() - l / 2);
		Point D = new Point(G.getX() - l / 2, G.getY() - l / 2);
		Segment[] segmentsCarre = new Segment[]{new Segment(A, B), new Segment(B, C), new Segment(C, D), new Segment(D, A)};
		for (Segment seg : segmentsCarre) seg.rotation(this.getG(), rot);
		List<Point> listPts = new ArrayList<>();
		for (Segment seg : segmentsCarre) listPts.addAll(GenerationPoints.generatePtsFromSeg(seg, 10));
		return listPts;
	}
}
