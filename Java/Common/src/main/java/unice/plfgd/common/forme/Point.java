package unice.plfgd.common.forme;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

	protected double x;
	protected double y;
	protected double xG;
	protected double yG;
	protected boolean start;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.xG = x;
		this.yG = y;
		this.start = false;
	}

	public Point(double x, double y, boolean start) {
		this(x, y);
		this.start = start;
	}

	public Point(double x, double y, double xG, double yG, boolean start) {
		this.x = x;
		this.y = y;
		this.xG = xG;
		this.yG = yG;
		this.start = start;
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

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
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

	public double calculeDistanceG() {
		return Math.sqrt(Math.pow(x - xG, 2)
				+ Math.pow(y - yG, 2));
	}

	public boolean compareXY(Point pt) {
		return (this.x == pt.getX() && this.y == pt.getY());
	}

	public boolean compareXY(double x, double y) {
		return (this.x == x && this.y == y);
	}

	public Point pointSym(Point pt) {
		double xS = 2 * this.x - pt.getX();
		double yS = 2 * this.y - pt.getY();
		return new Point(xS, yS);
	}
}
