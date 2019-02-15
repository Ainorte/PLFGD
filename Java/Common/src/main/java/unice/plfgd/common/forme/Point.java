package unice.plfgd.common.forme;

import java.io.Serializable;
import java.util.Objects;

public class Point implements Serializable {

	protected double x;
    protected double y;
    protected double xG;
    protected double yG;
	protected boolean start;

	public boolean isStart() {
		return start;
	}


    public Point(double x, double y){
        this.x = x;
        this.y = y;
        this.xG = x;
        this.yG = y;
        this.start = false;
    }

	public Point(double x, double y,boolean b){
    	this(x,y);
    	this.start = b;
	}

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getXG(){
        return xG;
    }

    public double getYG(){
        return yG;
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
		return Double.compare(point.x, x) == 0 &&
				Double.compare(point.y, y) == 0 &&
				start == point.start;
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
