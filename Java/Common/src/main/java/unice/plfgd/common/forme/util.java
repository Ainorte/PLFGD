package unice.plfgd.common.forme;

public class util {

	static private double sqr(double x){
		return x * x;
	}

	static private double dist2(Point v, Point w) {
		return sqr(v.getX() - w.getX()) + sqr(v.getY() - w.getY());
	}

	static public double distToSegmentSquared(Point pt, Point v, Point w) {
		int l2 = (int)dist2(v, w);
		if (l2 == 0){
			return dist2(pt, v);
		}
		double t = ((pt.getX() - v.getY()) * (w.getX() - v.getY()) + (pt.getY() - v.getY()) * (w.getY() - v.getY())) / l2;
		t = Math.max(0, Math.min(1, t));

		Point nearest = new Point((v.getX() + t * (w.getX() - v.getX())),(v.getY() + t * (w.getY() - v.getY())));
		return dist2(pt, nearest);
	}

	static public double distToSegment(Point pt, Segment l) {
		return Math.sqrt(distToSegmentSquared(pt, l.getStart(), l.getEnd()));
	}
}
