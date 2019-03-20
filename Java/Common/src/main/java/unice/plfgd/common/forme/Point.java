package unice.plfgd.common.forme;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

	protected double x;
	protected double y;
	protected double xG;
	protected double yG;
	protected Forme type = Forme.POINT;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.xG = x;
		this.yG = y;
	}

	public Point(double x, double y, double xG, double yG) {
		this.x = x;
		this.y = y;
		this.xG = xG;
		this.yG = yG;
	}

	public Point() {
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getxG() {
		return xG;
	}

	public void setxG(double xG) {
		this.xG = xG;
	}

	public double getyG() {
		return yG;
	}

	public void setyG(double yG) {
		this.yG = yG;
	}

	public Forme getType(){
		return type;
	}

	@Override
	public String toString() {
		return "[" + x + ";" + y + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Point point = (Point) o;
		return Double.compare(point.getX(), x) == 0 &&
				Double.compare(point.getY(), y) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	public void rotation(Point pt0, double rot) {
		rot = Math.toRadians(rot);
		double x0 = pt0.getX();
		double y0 = pt0.getY();
		double new_x = (x - x0) * Math.cos(rot) - (y - y0) * Math.sin(rot) + x0;
		double new_y = (x - x0) * Math.sin(rot) + (y - y0) * Math.cos(rot) + y0;
		this.setX(new_x);
		this.setY(new_y);
	}

	public double getDirection(Point pt){
	    return Math.atan((pt.getY() - y) / (pt.getX() - x));
    }

	public double calculeDistanceG() {
		return Math.sqrt(Math.pow(x - xG, 2)
				+ Math.pow(y - yG, 2));
	}

	public Point pointSym(Point pt) {
		double xS = 2 * this.x - pt.getX();
		double yS = 2 * this.y - pt.getY();
		return new Point(xS, yS);
	}
}
