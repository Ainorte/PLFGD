package unice.plfgd.common.forme;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

	protected double x;
    protected double y;
    protected double xG;
    protected double yG;
	protected boolean start;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
        this.xG = x;
        this.yG = y;
        this.start = false;
    }

	public Point(double x, double y, boolean b){
    	this(x,y);
    	this.start = b;
	}

    public Point(double x, double y, double xG, double yG, boolean start) {
        this.x = x;
        this.y = y;
        this.xG = xG;
        this.yG = yG;
        this.start = start;
    }

    public Point(){}

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
		Point pt = (Point) o;
		return Double.compare(pt.x, x) == 0 &&
				Double.compare(pt.y, y) == 0 &&
				start == pt.start;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, start);
	}

	public double calculeDistanceG(){
        return Math.sqrt(Math.pow(x - xG, 2)
                + Math.pow(y - yG, 2));
    }

    public boolean compareXY(Point pt){
        return (this.x == pt.getX() && this.y == pt.getY());
    }

    public boolean compareXY(double x, double y){
        return (this.x == x && this.y == y);
    }

    public Point pointSym(Point pt){
        double xS = 2 * this.x - pt.getX();
        double yS = 2 * this.y - pt.getY();
        return new Point(xS, yS);
    }
}
