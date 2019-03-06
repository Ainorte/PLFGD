package unice.plfgd.common.forme;

import java.util.*;
import java.util.List;

public class ConvexHull {
	
	protected enum Turn {
		CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR
	}

	/**
	 * Returns true if all points in points are collinear.
	 *
	 * @param points the list of points.
	 * @return true if all points in points are collinear.
	 */
	protected static boolean areAllCollinear(List<Point> points) {

		if (points.size() < 2) {
			return true;
		}

		final Point a = points.get(0);
		final Point b = points.get(1);

		for (int i = 2; i < points.size(); i++) {

			Point c = points.get(i);

			if (getTurn(a, b, c) != Turn.COLLINEAR) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the convex hull of the points created from xs
	 * and ys Note that the first and last point in the returned
	 * list are the same point.
	 *
	 * @param xs the x coordinates.
	 * @param ys the y coordinates.
	 * @return the convex hull of the points created from xs and ys.
	 * @throws IllegalArgumentException if xs and ys don't have the same size
	 */
	public static List<Point> getConvexHull(int[] xs, int[] ys) throws IllegalArgumentException{

		if (xs.length != ys.length) {
			throw new IllegalArgumentException("xs and ys don't have the same size");
		}

		List<Point> points = new ArrayList<Point>();

		for (int i = 0; i < xs.length; i++) {
			points.add(new Point(xs[i], ys[i]));
		}

		return getConvexHull(points);
	}

	/**
	 * Returns the convex hull of the points created from the list
	 * points. Note that the first and last point in the
	 * returned List are the same point.
	 *
	 * @param points the list of points.
	 * @return the convex hull of the points created from the list
	 *         points.
	 */
	public static List<Point> getConvexHull(List<Point> points) {

		Set<Point> unsorted = getSortedPointSet(points);

		if (unsorted == null) {
			return null;
		}

		List<Point> sorted = new ArrayList<>(unsorted);

		if (sorted.size() < 3) {
			return null;
		}

		if (areAllCollinear(sorted)) {
			return null;
		}

		Stack<Point> stack = new Stack<>();
		stack.push(sorted.get(0));
		stack.push(sorted.get(1));

		for (int i = 2; i < sorted.size(); i++) {

			Point head = sorted.get(i);
			Point middle = stack.pop();
			Point tail = stack.peek();

			Turn turn = getTurn(tail, middle, head);

			switch (turn) {
				case COUNTER_CLOCKWISE:
					stack.push(middle);
					stack.push(head);
					break;
				case CLOCKWISE:
					i--;
					break;
				case COLLINEAR:
					stack.push(head);
					break;
			}
		}

		// close the hull
		stack.push(sorted.get(0));

		return new ArrayList<>(stack);
	}

	/**
	 * Returns the points with the lowest y coordinate. In case more than 1 such
	 * point exists, the one with the lowest x coordinate is returned.
	 *
	 * @param points the list of points to return the lowest point from.
	 * @return the points with the lowest y coordinate. In case more than
	 *         1 such point exists, the one with the lowest x coordinate
	 *         is returned.
	 */
	protected static Point getLowestPoint(List<Point> points) {

		if (points.size() < 1) {
			return null;
		}

		Point lowest = points.get(0);

		for (Point temp : points) {

			if (temp.y < lowest.y || (temp.y == lowest.y && temp.x < lowest.x)) {
				lowest = temp;
			}
		}

		return lowest;
	}

	/**
	 * Returns a sorted set of points from the list points. The
	 * set of points are sorted in increasing order of the angle they and the
	 * lowest point P< make with the x-axis. If tow (or more) points
	 * form the same angle towards P, the one closest to P
	 * comes first.
	 *
	 * @param points the list of points to sort.
	 * @return a sorted set of points from the list points.
	 */

	protected static Set<Point> getSortedPointSet(List<Point> points) {

		final Point lowest = getLowestPoint(points);

		if (lowest == null) {
			return null;
		}

		TreeSet<Point> set = new TreeSet<Point>(new Comparator<Point>() {
			@Override
			public int compare(Point a, Point b) {

				if (a == b || a.equals(b)) {
					return 0;
				}

				// use longs to guard against int-underflow
				double thetaA = Math.atan2((long) a.y - lowest.y, (long) a.x - lowest.x);
				double thetaB = Math.atan2((long) b.y - lowest.y, (long) b.x - lowest.x);

				if (thetaA < thetaB) {
					return -1;
				} else if (thetaA > thetaB) {
					return 1;
				} else {
					// collinear with the 'lowest' point, let the point closest to it come first

					// use longs to guard against int-over/underflow
					double distanceA = Math.sqrt((((long) lowest.x - a.x) * ((long) lowest.x - a.x)) +
							(((long) lowest.y - a.y) * ((long) lowest.y - a.y)));
					double distanceB = Math.sqrt((((long) lowest.x - b.x) * ((long) lowest.x - b.x)) +
							(((long) lowest.y - b.y) * ((long) lowest.y - b.y)));

					if (distanceA < distanceB) {
						return -1;
					} else {
						return 1;
					}
				}
			}
		});

		set.addAll(points);
		return set;
	}

	protected static Turn getTurn(Point a, Point b, Point c) {

		// use longs to guard against int-over/underflow
		double crossProduct = (((long) b.x - a.x) * ((long) c.y - a.y)) -
				(((long) b.y - a.y) * ((long) c.x - a.x));

		if (crossProduct > 0) {
			return Turn.COUNTER_CLOCKWISE;
		} else if (crossProduct < 0) {
			return Turn.CLOCKWISE;
		} else {
			return Turn.COLLINEAR;
		}
	}

}
