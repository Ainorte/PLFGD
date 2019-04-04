package unice.plfgd.common.forme;

/*
 * Copyright (c) 2010, Bart Kiers
 * Modified to fit our needs on the projects
 */



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;


public final class RotatingCalipers2 {

    protected enum Corner { UPPER_RIGHT, UPPER_LEFT, LOWER_LEFT, LOWER_RIGHT }

    public static double getArea(Point[] rectangle) {

        double deltaXAB = rectangle[0].getX() - rectangle[1].getX();
        double deltaYAB = rectangle[0].getY() - rectangle[1].getY();

        double deltaXBC = rectangle[1].getX() - rectangle[2].getX();
        double deltaYBC = rectangle[1].getY() - rectangle[2].getY();

        double lengthAB = Math.sqrt((deltaXAB * deltaXAB) + (deltaYAB * deltaYAB));
        double lengthBC = Math.sqrt((deltaXBC * deltaXBC) + (deltaYBC * deltaYBC));

        return lengthAB * lengthBC;
    }

    public static List<Point[]> getAllBoundingRectangles(int[] xs, int[] ys) throws IllegalArgumentException {

        if(xs.length != ys.length) {
            throw new IllegalArgumentException("xs and ys don't have the same size");
        }

        List<Point> points = new ArrayList<Point>();

        for(int i = 0; i < xs.length; i++) {
            points.add(new Point(xs[i], ys[i]));
        }

        return getAllBoundingRectangles(points);
    }

    public static List<Point[]> getAllBoundingRectangles(List<Point> points) throws IllegalArgumentException {

        List<Point[]> rectangles = new ArrayList<Point[]>();

        List<Point> convexHull = GrahamScan.getConvexHull(points);

        Caliper I = new Caliper(convexHull, getIndex(convexHull, Corner.UPPER_RIGHT), 90);
        Caliper J = new Caliper(convexHull, getIndex(convexHull, Corner.UPPER_LEFT), 180);
        Caliper K = new Caliper(convexHull, getIndex(convexHull, Corner.LOWER_LEFT), 270);
        Caliper L = new Caliper(convexHull, getIndex(convexHull, Corner.LOWER_RIGHT), 0);

        while(L.currentAngle < 90.0) {

            rectangles.add(new Point[]{
                    L.getIntersection(I),
                    I.getIntersection(J),
                    J.getIntersection(K),
                    K.getIntersection(L)
            });

            double smallestTheta = getSmallestTheta(I, J, K, L);

            I.rotateBy(smallestTheta);
            J.rotateBy(smallestTheta);
            K.rotateBy(smallestTheta);
            L.rotateBy(smallestTheta);
        }

        return rectangles;
    }


    public static List<Point> getMinimumBoundingRectangle(List<Point> points) throws IllegalArgumentException {

        List<Point[]> rectangles = getAllBoundingRectangles(points);

        Point[] minimum = null;
        double area = Long.MAX_VALUE;

        for (Point[] rectangle : rectangles) {

            double tempArea = getArea(rectangle);

            if (minimum == null || tempArea < area) {
                minimum = rectangle;
                area = tempArea;
            }
        }
        List<Point> res = Arrays.asList(minimum);
        return res;
    }

    private static double getSmallestTheta(Caliper I, Caliper J, Caliper K, Caliper L) {

        double thetaI = I.getDeltaAngleNextPoint();
        double thetaJ = J.getDeltaAngleNextPoint();
        double thetaK = K.getDeltaAngleNextPoint();
        double thetaL = L.getDeltaAngleNextPoint();

        if(thetaI <= thetaJ && thetaI <= thetaK && thetaI <= thetaL) {
            return thetaI;
        }
        else if(thetaJ <= thetaK && thetaJ <= thetaL) {
            return thetaJ;
        }
        else if(thetaK <= thetaL) {
            return thetaK;
        }
        else {
            return thetaL;
        }
    }

    protected static int getIndex(List<Point> convexHull, Corner corner) {

        int index = 0;
        Point point = convexHull.get(index);

        for(int i = 1; i < convexHull.size() - 1; i++) {

            Point temp = convexHull.get(i);
            boolean change = false;

            switch(corner) {
                case UPPER_RIGHT:
                    change = (temp.getX() > point.getX() || (temp.getX() == point.getX() && temp.getY() > point.getY()));
                    break;
                case UPPER_LEFT:
                    change = (temp.getY() > point.getY() || (temp.getY() == point.getY() && temp.getX() < point.getX()));
                    break;
                case LOWER_LEFT:
                    change = (temp.getX() < point.getX() || (temp.getX() == point.getX() && temp.getY() < point.getY()));
                    break;
                case LOWER_RIGHT:
                    change = (temp.getY() < point.getY() || (temp.getY() == point.getY() && temp.getX() > point.getX()));
                    break;
            }

            if(change) {
                index = i;
                point = temp;
            }
        }

        return index;
    }

    protected static class Caliper {

        final static double SIGMA = 0.00000000001;

        final List<Point> convexHull;
        int pointIndex;
        double currentAngle;

        Caliper(List<Point> convexHull, int pointIndex, double currentAngle) {
            this.convexHull = convexHull;
            this.pointIndex = pointIndex;
            this.currentAngle = currentAngle;
        }

        double getAngleNextPoint() {

            Point p1 = convexHull.get(pointIndex);
            Point p2 = convexHull.get((pointIndex + 1) % convexHull.size());

            double deltaX = p2.getX() - p1.getX();
            double deltaY = p2.getY() - p1.getY();

            double angle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;

            return angle < 0 ? 360 + angle : angle;
        }

        double getConstant() {

            Point p = convexHull.get(pointIndex);

            return p.getY() - (getSlope() * p.getX());
        }

        double getDeltaAngleNextPoint() {

            double angle = getAngleNextPoint();

            angle = angle < 0 ? 360 + angle - currentAngle : angle - currentAngle;

            return angle < 0 ? 360 : angle;
        }

        Point getIntersection(Caliper that) {

            // the x-intercept of 'this' and 'that': x = ((c2 - c1) / (m1 - m2))
            double x;
            // the y-intercept of 'this' and 'that', given 'x': (m*x) + c
            double y;

            if(this.isVertical()) {
                x = convexHull.get(pointIndex).getX();
            }
            else if(this.isHorizontal()) {
                x = that.convexHull.get(that.pointIndex).getX();
            }
            else {
                x = (that.getConstant() -  this.getConstant()) / (this.getSlope() - that.getSlope());
            }

            if(this.isVertical()) {
                y = that.getConstant();
            }
            else if(this.isHorizontal()) {
                y = this.getConstant();
            }
            else {
                y = (this.getSlope() * x) + this.getConstant();
            }

            return new Point(x, y);
        }

        double getSlope() {
            return Math.tan(Math.toRadians(currentAngle));
        }

        boolean isHorizontal() {
            return (Math.abs(currentAngle) < SIGMA) || (Math.abs(currentAngle - 180.0) < SIGMA);
        }

        boolean isVertical() {
            return (Math.abs(currentAngle - 90.0) < SIGMA) || (Math.abs(currentAngle - 270.0) < SIGMA);
        }

        void rotateBy(double angle) {

            if(this.getDeltaAngleNextPoint() == angle) {
                pointIndex++;
            }

            this.currentAngle = (this.currentAngle + angle) % 360;
        }
    }

    /**
     * For a documented (and unit tested version) of the class below, see:
     * <a href="https://github.com/bkiers/GrahamScan">github.com/bkiers/GrahamScan</a>
     */
    private static class GrahamScan {

        protected static enum Turn { CLOCKWISE, COUNTER_CLOCKWISE, COLLINEAR }

        protected static boolean areAllCollinear(List<Point> points) {

            if(points.size() < 2) {
                return true;
            }

            final Point a = points.get(0);
            final Point b = points.get(1);

            for(int i = 2; i < points.size(); i++) {

                Point c = points.get(i);

                if(getTurn(a, b, c) != Turn.COLLINEAR) {
                    return false;
                }
            }

            return true;
        }

        public static List<Point> getConvexHull(List<Point> points) throws IllegalArgumentException {

            List<Point> sorted = new ArrayList<Point>(getSortedPointSet(points));

            if(sorted.size() < 3) {
                throw new IllegalArgumentException("can only create a convex hull of 3 or more unique points");
            }

            if(areAllCollinear(sorted)) {
                throw new IllegalArgumentException("cannot create a convex hull from collinear points");
            }

            Stack<Point> stack = new Stack<Point>();
            stack.push(sorted.get(0));
            stack.push(sorted.get(1));

            for (int i = 2; i < sorted.size(); i++) {

                Point head = sorted.get(i);
                Point middle = stack.pop();
                Point tail = stack.peek();

                Turn turn = getTurn(tail, middle, head);

                switch(turn) {
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

            stack.push(sorted.get(0));

            return new ArrayList<Point>(stack);
        }

        protected static Point getLowestPoint(List<Point> points) {

            Point lowest = points.get(0);

            for(int i = 1; i < points.size(); i++) {

                Point temp = points.get(i);

                if(temp.getY() < lowest.getY() || (temp.getY() == lowest.getY() && temp.getX() < lowest.getX())) {
                    lowest = temp;
                }
            }

            return lowest;
        }

        protected static Set<Point> getSortedPointSet(List<Point> points) {

            final Point lowest = getLowestPoint(points);

            TreeSet<Point> set = new TreeSet<Point>(new Comparator<Point>() {
                @Override
                public int compare(Point a, Point b) {

                    if(a == b || a.equals(b)) {
                        return 0;
                    }

                    double thetaA = Math.atan2((long)a.getY() - lowest.getY(), (long)a.getX() - lowest.getX());
                    double thetaB = Math.atan2((long)b.getY() - lowest.getY(), (long)b.getX() - lowest.getX());

                    if(thetaA < thetaB) {
                        return -1;
                    }
                    else if(thetaA > thetaB) {
                        return 1;
                    }
                    else {
                        double distanceA = Math.sqrt((((long)lowest.getX() - a.getX()) * ((long)lowest.getX() - a.getX())) +
                                (((long)lowest.getY() - a.getY()) * ((long)lowest.getY() - a.getY())));
                        double distanceB = Math.sqrt((((long)lowest.getX() - b.getX()) * ((long)lowest.getX() - b.getX())) +
                                (((long)lowest.getY() - b.getY()) * ((long)lowest.getY() - b.getY())));

                        if(distanceA < distanceB) {
                            return -1;
                        }
                        else {
                            return 1;
                        }
                    }
                }
            });

            set.addAll(points);

            return set;
        }

        protected static Turn getTurn(Point a, Point b, Point c) {

            double crossProduct = (((long)b.getX() - a.getX()) * ((long)c.getY() - a.getY())) -
                    (((long)b.getY() - a.getY()) * ((long)c.getX() - a.getX()));

            if(crossProduct > 0) {
                return Turn.COUNTER_CLOCKWISE;
            }
            else if(crossProduct < 0) {
                return Turn.CLOCKWISE;
            }
            else {
                return Turn.COLLINEAR;
            }
        }
    }
}

