package unice.plfgd.common.data;

import unice.plfgd.common.forme.Point;
import unice.plfgd.common.net.Packet;

import java.util.ArrayList;
import java.util.List;

public class Draw extends Packet {
    private List<List<Point>> points;
	private int width;
	private int height;

    public Draw(List<List<Point>> points, int width, int height) {
        this.points = points;
		this.width = width;
		this.height = height;
	}

    public Draw(int width, int height) {
        this(new ArrayList<List<Point>>(), width, height);
    }

	public Draw() {
	}

    public List<List<Point>> getPoints() {
        return points;
	}

	public int getWidth() {
		return width;
	}

    public void setPoints(List<List<Point>> points) {
        this.points = points;
    }

    public int getHeight() {
        return height;
    }

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

    public void addLine() {
        if (points != null) {
            points.add(new ArrayList<Point>());
        }
    }

    public void addPoint(Point point) {
        if (points != null && points.size() != 0) {
            points.get(points.size() - 1).add(point);
        }
    }

    public Draw convertRefactor(int newWidth, int newHeight) {
        Draw draw = new Draw(newWidth, newHeight);

        List<List<Point>> coords = getPoints();

        boolean nWLEToW = newWidth <= getWidth();
        boolean nHLEToH = newHeight <= getHeight();

        double coef = 1;
        boolean divide = false;
        boolean moveHeight = false;

        if (nWLEToW && nHLEToH) {
            double coefWidth = (double) newWidth / (double) getWidth();
            double coefHeight = (double) newHeight / (double) getHeight();

            coef = Math.min(coefWidth, coefHeight);
            moveHeight = coefWidth == coef;
        } else if (!nWLEToW && !nHLEToH) {
            double coefWidth = (double) getWidth() / (double) newWidth;
            double coefHeight = (double) getHeight() / (double) newHeight;

            divide = true;
            coef = Math.max(coefWidth, coefHeight);
            moveHeight = coefWidth == coef;
        } else if (nWLEToW) {
            moveHeight = true;
            coef = (double) newWidth / (double) getWidth();
        } else {
            coef = (double) newHeight / (double) getHeight();
        }


        for (List<Point> pts : coords) {
            draw.addLine();
            for (int i = 0; i < pts.size(); i++) {

                double x = divide ? pts.get(i).getX() / coef : pts.get(i).getX() * coef;
                double y = divide ? pts.get(i).getY() / coef : pts.get(i).getY() * coef;

                if (divide && moveHeight) {
                    y += (newHeight - (getHeight() / coef)) / 2;
                } else if (divide) {
                    x += (newWidth - (getWidth() / coef)) / 2;
                } else if (moveHeight) {
                    y += (newHeight - (getHeight() * coef)) / 2;
                } else {
                    x += (newWidth - (getWidth() * coef)) / 2;
                }

                draw.addPoint(new Point(x, y));
            }
        }

        return draw;
    }
}
