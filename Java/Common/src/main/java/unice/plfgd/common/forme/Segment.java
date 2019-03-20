package unice.plfgd.common.forme;

import java.io.Serializable;
import java.util.List;

public class Segment implements Serializable {

	protected Point p1;
	protected Point p2;
	protected double coefDir;
	protected double constReelle;
	protected Forme type = Forme.SEGMENT;

	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.coefDir = p1.getX() - p2.getX() != 0 ? (p1.getY() - p2.getY()) / (p1.getX() - p2.getX()) : Double.POSITIVE_INFINITY;
		this.constReelle = coefDir != Double.POSITIVE_INFINITY ? p1.getY() - coefDir * p1.getX() : Double.NEGATIVE_INFINITY;
	}

	static public double distToSegmentSquared(Point p, Point v, Point w) {
		double l2 = Math.pow(MethodesForme.norme(v, w), 2);
		if (l2 == 0) {
			return Math.pow(MethodesForme.norme(p, v), 2);
		}
		double t = ((p.getX() - v.getY()) * (w.getX() - v.getY()) + (p.getY() - v.getY()) * (w.getY() - v.getY())) / l2;
		t = Math.max(0, Math.min(1, t));

		Point nearest = new Point((v.getX() + t * (w.getX() - v.getX())), (v.getY() + t * (w.getY() - v.getY())));
		return Math.pow(MethodesForme.norme(p, nearest), 2);
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public double getCoef() {
		return coefDir;
	}

	public double getConst() {
		return constReelle;
	}

	public Forme getType(){
	    return type;
    }

	public String toString() {
		return "Segment([" + p1.getX() + ";" + p1.getY() + "]" + "[" + p2.getX() + ";" + p2.getY() + "])";
	}

	//Rotation du segment autour d'un point [x0, y0] avec un angle donné en degré
	public void rotation(Point pt0, double rot) {
		p1.rotation(pt0, rot);
		p2.rotation(pt0, rot);
	}

	public List<Point> make() {
		return GenerationPoints.generatePtsFromSeg(this, 10);
	}

	public Point barycentre() {
		return new Point((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
	}

	//Savoir si un point appartient à l'axe du segment
	public boolean isPointInAxis(Point pt, double tolerance) {
		if (coefDir == Double.POSITIVE_INFINITY) return pt.getX() == p1.getX();
		else return Math.abs(pt.getY() - pt.getX() * coefDir + constReelle) < tolerance;
	}

	public boolean isPointInSeg(Point pt, double tolerance) {
		//if(pt.equals(p1)||pt.equals(p2)) return true;
		return (Math.min(p1.getX(), p2.getX()) - tolerance <= pt.getX() && pt.getX() <= Math.max(p1.getX(), p2.getX()) + tolerance)
				& (Math.min(p1.getY(), p2.getY()) - tolerance <= pt.getY() && pt.getY() <= Math.max(p1.getY(), p2.getY()) + tolerance);
	}

	//Retourne true si il y a une intersection entre deux segment
	public boolean isIntersection(Segment seg){
		Point crossPoint = this.crossPoint(seg);
		return seg.isPointInSeg(crossPoint,0) & this.isPointInSeg(crossPoint,0);
	}

	//Savoir si un autre segment est parallèle
	public boolean isSegmentParallel(Segment seg) {
		return seg.getCoef() == coefDir;
	}

	//Savoir si un autre segment appartient à l'axe du segment (avec degré de tolérance)
	public boolean isSegmentInAxis(Segment seg, double tolerance) {
		if (coefDir != Double.POSITIVE_INFINITY && seg.getCoef() != Double.POSITIVE_INFINITY) {
			return Math.abs(coefDir - seg.getCoef() + constReelle - seg.getConst()) < tolerance;
		}
		return Math.abs(seg.getP1().getX() - p1.getX()) < tolerance;
	}

	//Point de croisement de deux segments prolongés sur leurs axes
	public Point crossPoint(Segment seg) {
		if (coefDir != Double.POSITIVE_INFINITY && seg.getCoef() != Double.POSITIVE_INFINITY) {
			double x = coefDir - seg.getCoef() != 0 ? (seg.getConst() - constReelle) / (coefDir - seg.getCoef()) : Double.POSITIVE_INFINITY;
			double y = coefDir * x + constReelle;
			return new Point(x, y);
		} else {
			if (coefDir != seg.getCoef())
				return coefDir == Double.POSITIVE_INFINITY ? new Point(p1.getX(), seg.getCoef() * p1.getX() + seg.getConst()) : new Point(seg.getP1().getX(), coefDir * seg.getP1().getX() + constReelle);
			return new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		}
	}

	//Connaitre l'angle d'un axe partant de l'origine p1 du segment vers un point quelconque
	public double findAngleToPoint(Point pt) {
		double OA = MethodesForme.norme(p1, p2);
		double OB = MethodesForme.norme(p1, pt);
		double AB = MethodesForme.norme(p2, pt);
		return OA != 0 && OB != 0 ? Math.acos((OA * OA + OB * OB - AB * AB) / (2 * OA * OB)) : 0;
	}

	public double distPtToSeg(Point p) {
		return Math.sqrt(distToSegmentSquared(p, p1, p2));
	}

	//A corriger
	public Point pointSym(Point pt) {
		double coefDirPerp = coefDir != 0 ? -1 / coefDir : Double.NEGATIVE_INFINITY;
		double constReellePerp = constReelle - coefDirPerp * coefDir;
		double x0 = coefDir - coefDirPerp != 0 ? (constReellePerp - constReelle) / (coefDir - coefDirPerp) : Double.POSITIVE_INFINITY;
		double y0 = coefDir * x0 + constReelle;
		//System.out.println(x0 + "," + y0);
		Point pointSym = new Point(pt.getX() - 2 * (pt.getX() - x0), pt.getY() - 2 * (pt.getY() - y0));
		//Point pointSym = MethodesForme.rotation(pt, x0, y0, Math.PI/2);
		return pointSym;
	}
}
