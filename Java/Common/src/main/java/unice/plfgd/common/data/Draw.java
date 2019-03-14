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

		final int currentWidth = getWidth();
		final int currentHeight = getHeight();
		boolean nWLEToW = newWidth <= currentWidth;
		boolean nHLEToH = newHeight <= currentHeight;

		double coef;
		boolean divide = false;
		boolean moveHeight = false;

		if (nWLEToW && nHLEToH) {
			double coefWidth = (double) newWidth / (double) currentWidth;
			double coefHeight = (double) newHeight / (double) currentHeight;

			coef = Math.min(coefWidth, coefHeight);
			moveHeight = coefWidth == coef;
		} else if (!nWLEToW && !nHLEToH) {
			double coefWidth = (double) currentWidth / (double) newWidth;
			double coefHeight = (double) currentHeight / (double) newHeight;

			divide = true;
			coef = Math.max(coefWidth, coefHeight);
			moveHeight = coefWidth == coef;
		} else if (nWLEToW) {
			moveHeight = true;
			coef = (double) newWidth / (double) currentWidth;
		} else {
			coef = (double) newHeight / (double) currentHeight;
		}


		for (List<Point> pts : coords) {
			draw.addLine();
			for (Point pt : pts) {

				double x = divide ? pt.getX() / coef : pt.getX() * coef;
				double y = divide ? pt.getY() / coef : pt.getY() * coef;

				if (divide && moveHeight) {
					y += (newHeight - (currentHeight / coef)) / 2;
				} else if (divide) {
					x += (newWidth - (currentWidth / coef)) / 2;
				} else if (moveHeight) {
					y += (newHeight - (currentHeight * coef)) / 2;
				} else {
					x += (newWidth - (currentWidth * coef)) / 2;
				}

				draw.addPoint(new Point(x, y));
			}
		}

		return draw;
	}
}
