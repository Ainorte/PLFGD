package unice.plfgd.common.forme;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

	protected double x;
	protected double y;
	protected double xV;
	protected double yV;
	protected Forme type = Forme.POINT;

	public Point(double x, double y) {
		this(x,y,0,0);
	}

	public Point(double x, double y, double xV, double yV) {
		this.x = x;
		this.y = y;
		this.xV = xV;
		this.yV = yV;
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


	public Forme getType(){
		return type;
	}

	public double getxV() {
		return xV;
	}

	public void setxV(double xV) {
		this.xV = xV;
	}

	public double getyV() {
		return yV;
	}

	public void setyV(double yV) {
		this.yV = yV;
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

	public Point pointSym(Point pt) {
		double xS = 2 * this.x - pt.getX();
		double yS = 2 * this.y - pt.getY();
		return new Point(xS, yS);
	}
}
