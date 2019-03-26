package unice.plfgd.common.forme;

import java.util.ArrayList;
import java.util.List;

public class Cercle extends Ellipse {

	protected Point G;
	protected Forme type = Forme.CIRCLE;

	Cercle(Point G, double a, double rot) {
		super(G, a, a, rot);
		this.G = G;
	}

	public double getAire() {
		return 2 * Math.PI * a;
	}

	public double getPerim() {
		return Math.PI * Math.sqrt(4 * a * a);
	}

	public String toString() {
		return super.toString();
	}

	public Forme getType() {
		return type;
	}

	public List<Point> make() {
		double nbPoints = this.getPerim() / 10;
		double angleParPoints = nbPoints != 0 ? Math.toRadians(360 / nbPoints) : 0;
		List<Point> listPts = new ArrayList<>();
		for (int i = 0; i < nbPoints + 1; i++) {
			double x = this.G.getX() + this.a * Math.cos(i * angleParPoints);
			double y = this.G.getY() + this.a * Math.sin(i * angleParPoints);
			listPts.add(new Point(x, y));
		}
		return listPts;
	}
}
