package unice.plfgd.common.forme;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends AbstractForme {

	protected Point A;
	protected Point B;
	protected Point C;
	protected double rot;
	protected Forme type = Forme.TRIANGLE;

	public Triangle(Point G, Point A, Point B, Point C, double rot) {
		super(G);
		this.A = A;
		this.B = B;
		this.C = C;
		this.rot = rot;
	}

	public Triangle(List<Point> pointList) {
		super(MethodesForme.barycentre(pointList));
		this.A = pointList.get(0);
		this.B = pointList.get(1);
		this.C = pointList.get(2);
		this.rot = 0;
	}

	public static void main(String[] args) {
		Triangle triangle1 = new Triangle(new Point(1, 1), new Point(0, 0), new Point(2, 2), new Point(4, 0), 0.0);
		//System.out.println(triangle1.getPerim());
		//System.out.println(triangle1.getAire());
	}

	public double getAire() {
		double distAB = MethodesForme.norme(A, B);
		double distBC = MethodesForme.norme(B, C);
		double distAC = MethodesForme.norme(A, C);
		double s = (distAB + distBC + distAC) / 2;
		double h = 2 * Math.sqrt(s * (s - distAB) * (s - distBC) * (s - distAC)) / distAC;
		return distAC * h;
	}

	public double getPerim() {
		double distAB = MethodesForme.norme(A, B);
		double distBC = MethodesForme.norme(B, C);
		double distAC = MethodesForme.norme(A, C);
		return distAB + distBC + distAC;
	}

	public Forme getType() {
		return type;
	}

	public List<Point> make() {
		Segment[] segmentsTri = new Segment[]{new Segment(A, B), new Segment(B, C), new Segment(C, A)};
		for (Segment seg : segmentsTri) seg.rotation(this.getG(), rot);
		List<Point> listPts = new ArrayList<>();
		for (Segment seg : segmentsTri) listPts.addAll(GenerationPoints.generatePtsFromSeg(seg, 10));
		return listPts;
	}
}
