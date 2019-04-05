package unice.plfgd.common.forme;

import java.util.*;


public class TraitementPoints {


    public static List<Point> mergeList(List<List<Point>> pointList) {
        List<Point> base = pointList.get(0);
        for (int i = 1; i < pointList.size(); i++) {
            List<Point> merge = pointList.get(i);

            Point baseStart = base.get(0);
            Point baseEnd = base.get(base.size() - 1);

            Point mergeStart = merge.get(0);
            Point mergeEnd = merge.get(merge.size() - 1);

            double distanceStartToStart = MethodesForme.norme(baseStart, mergeStart);
            double distanceEndToStart = MethodesForme.norme(baseEnd, mergeStart);
            double distanceEndToEnd = MethodesForme.norme(baseEnd, mergeEnd);

            double minDistance = Math.min(distanceStartToStart, Math.min(distanceEndToStart, distanceEndToEnd));

            if (minDistance == distanceEndToStart) {
                base.addAll(merge);
            } else {
                Collections.reverse(merge);
                if (minDistance == distanceStartToStart) {
                    Collections.reverse(base);
                }
                base.addAll(merge);
            }
        }

        return base;
    }

    public static List<Point> sanitize(List<Point> pointList, double tolerance) {
        if (pointList.size() < 3) {
            return pointList;
        }
        Segment l = new Segment(pointList.get(0), pointList.get(pointList.size() - 1));
        // Find the point furthest away from the line segment
        double maxDistance = -1;
        int indexOfFurthestPoint = -1;

        for (int i = 2; i < pointList.size(); ++i) {
            Point pi = pointList.get(i);
            if (l.distPtToSeg(pi) > maxDistance) {
                maxDistance = l.distPtToSeg(pi);
                indexOfFurthestPoint = i;
            }
        }
        if (maxDistance <= tolerance) {
            return Arrays.asList(pointList.get(0), pointList.get(pointList.size() - 1));
        } else {// Recursively handle the parts

            ArrayList<Point> left = new ArrayList<>(sanitize(pointList.subList(0, indexOfFurthestPoint), tolerance));
            ArrayList<Point> right = new ArrayList<>(sanitize(pointList.subList(indexOfFurthestPoint, pointList.size()), tolerance));

            if (right.size() > 1) {
                right.remove(0);
            }

            left.addAll(right);

            return left;
        }
    }

    public static List<Point> refineEndPoints(List<Point> pts, int binSize) {
        int maxBinPts = pts.size() - 1;

        List<Point> binStart = new ArrayList<>();
        int i = 0;
        for (; i < maxBinPts; i++) {
            if (binStart.size() <= binSize) binStart.add(pts.get(i));
            else break;
        }
        Point start = MethodesForme.barycentre(binStart);
        start = closestDiscretePoint(start);

        List<Point> binEnd = new ArrayList<>();
        int j = 0;
        for (; j < maxBinPts; j++) {
            if (binEnd.size() <= binSize) binEnd.add(pts.get(maxBinPts - j));
            else break;
        }
        Point end = MethodesForme.barycentre(binEnd);
        end = closestDiscretePoint(end);

        List<Point> result = new ArrayList<>();
        result.add(start);
        for (int k = i; k < maxBinPts - j; k++) result.add(pts.get(k));
        result.add(end);
        return result;
    }

    public static Point closestDiscretePoint(Point pt) {
        double x = pt.getX();
        double y = pt.getY();
        x = (x - Math.floor(x)) < (Math.ceil(x) - x) ? Math.floor(x) : Math.ceil(x);
        y = (y - Math.floor(y)) < (Math.ceil(y) - y) ? Math.floor(y) : Math.ceil(y);
        return new Point(x, y);
    }

    public static List<Point> closeStroke(List<Point> pts) {
        List<Point> intersections = intersections(pointsToSegments(pts));
        if (intersections.isEmpty()) {
            return handleNoIntersections(pts);
        }
        //System.out.println(intersections);
        return handleIntersections(pts, intersections);
    }


    public static List<Point> intersections(List<Segment> segs) {
        Set<Point> listInter = new HashSet<>();
        for (int i = 0; i < segs.size() - 3; i++) {
            for (int j = i + 2; j < segs.size() - 1; j++) {
                if (segs.get(i).isIntersection(segs.get(j)))
                    listInter.add(segs.get(i).crossPoint(segs.get(j)));
            }
        }
        return new ArrayList<>(listInter);
    }


    public static List<Point> handleNoIntersections(List<Point> pts) {
        int listSize = pts.size();
        if (listSize > 3) {
            Segment headExt = new Segment(pts.get(0), pts.get(1));
            Segment tailExt = new Segment(pts.get(listSize - 2), pts.get(listSize - 1));
            Point inter = headExt.crossPoint(tailExt);
            if (MethodesForme.norme(inter, pts.get(0)) < MethodesForme.norme(inter, pts.get(1))
                    & MethodesForme.norme(inter, pts.get(listSize - 1)) < MethodesForme.norme(inter, pts.get(listSize - 2))) {
                pts.remove(0);
                pts.add(0, inter);
                pts.add(inter);
            } else {
                int MCPL = 20;//maximum cutted path length
                int MDE = 20;//maximum distance from endpoint
                List<Segment> listSeg = pointsToSegments(pts);
                for (int i = 0; i < listSeg.size() - 1; i++) {

                    Point headCross = listSeg.get(i).crossPoint(headExt);
                    Point tailCross = listSeg.get(i).crossPoint(tailExt);
                    Double distHeadCross = MethodesForme.norme(headExt.getP1(), headCross);
                    if (distHeadCross < MethodesForme.norme(headExt.getP2(), headCross)) {
                        if (distHeadCross <= MDE) {
                            pts = pts.subList(0, i + 1);
                            pts.add(0, headCross);
                            pts.add(headCross);
                            break;
                        }
                    }
                    Double distTailCross = MethodesForme.norme(tailExt.getP2(), tailCross);
                    if (distTailCross < MethodesForme.norme(tailExt.getP1(), tailCross)) {
                        if (distTailCross <= MCPL) {
                            pts = pts.subList(i + 1, pts.size());
                            pts.add(0, tailCross);
                            pts.add(tailCross);
                            break;
                        }
                    }
                }
            }
        }
        return pts;
    }

    public static List<Point> handleIntersections(List<Point> pts, List<Point> inters) {
        Point startPoint = pts.get(0);
        Point endPoint = pts.get(pts.size() - 1);
        Point closestInter = new Point(0, 0);
        double minDistance = Double.POSITIVE_INFINITY;
        for (Point inter : inters) {
            double distStart = MethodesForme.norme(inter, startPoint);
            double distEnd = MethodesForme.norme(inter, endPoint);
            if (distStart + distEnd <= minDistance) closestInter = inter;
        }
        int MCPL = 100;//maximum cutted path length
        int MDE = 100;//maximum distance from endpoint
        if (minDistance <= MCPL && minDistance <= MDE) {
            for (int i = pts.size() - 1; i >= 0; i--) {
                if (pts.get(i).equals(closestInter)) {
                    pts = pts.subList(0, i);
                    pts.add(0, closestInter);
                    pts.add(closestInter);
                }
            }
        }
        return pts;
    }

    public static List<Point> maximumAreaEnclosedTriangle(List<Point> pts) {

        List<Point> sommetsTriangle = new ArrayList<>();
        int listSize = pts.size() - 1;
        if (pts.size() < 3) return pts;

        Point A = pts.get(0);
        Point B = pts.get(1);
        Point C = pts.get(2);
        int a = 0;
        int b = 1;
        int c = 2;
        do {
            while (true) {
                while (new Triangle(new Point(0, 0), pts.get(a), pts.get(b), pts.get(c + 1), 0).getAire() >= new Triangle(new Point(0, 0), pts.get(a), pts.get(b), pts.get(c), 0).getAire()) {
                    c = (c + 1) % listSize;
                }
                if (new Triangle(new Point(0, 0), pts.get(a), pts.get(b + 1), pts.get(c), 0).getAire() >= new Triangle(new Point(0, 0), pts.get(a), pts.get(b), pts.get(c), 0).getAire()) {
                    b = (b + 1) % listSize;
                } else break;
            }
            if (new Triangle(new Point(0, 0), pts.get(a), pts.get(b), pts.get(c), 0).getAire() >= new Triangle(new Point(0, 0), A, B, C, 0).getAire()) {
                A = pts.get(a);
                B = pts.get(b);
                C = pts.get(c);
            }
            a = (a + 1) % listSize;
            b = (a == b) ? (b + 1) % listSize : b;
            c = (b == c) ? (c + 1) % listSize : c;
        } while (a != 0);

        sommetsTriangle.add(A);
        sommetsTriangle.add(B);
        sommetsTriangle.add(C);

        return sommetsTriangle;
    }

    public static List<Point> minimumAreaEnclosingRectangle(List<Point> convexHull) {
        int size = convexHull.size();
        int[] xs = new int[size];
        int[] ys = new int[size];

        for (int i = 0; i < size; i++) {
            Point pts = convexHull.get(i);
            xs[i] = (int) pts.getX();
            ys[i] = (int) pts.getY();
        }

        Point2D.Double[] temp = RotatingCalipers.getMinimumBoundingRectangle(xs, ys);
        List<Point> res = new ArrayList<>();

        for (Point2D.Double pts : temp) {
            Point newpts = new Point(pts.x, pts.y);
            res.add(newpts);
        }

        return res;
    }


    public static List<Point> minimumAreaEnclosingRectangle2(List<Point> pts) {

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = 0;
        double maxY = 0;

        int ptMinX = 0; //indice du point avec le plus petit x
        int ptMinY = 0;
        int ptMaxX = 0;
        int ptMaxY = 0;

        for (int i = 0; i < pts.size() - 1; i++) {
            double x = pts.get(i).getX();
            double y = pts.get(i).getY();
            if (x < minX || (x == minX && y < pts.get(ptMinX).getY())) {
                minX = x;
                ptMinX = i;
            }
            if (x > maxX || (x == maxX && y > pts.get(ptMaxX).getY())) {
                maxX = x;
                ptMaxX = i;
            }
            if (y < minY || (y == minY && x < pts.get(ptMinY).getX())) {
                minY = y;
                ptMinY = i;
            }
            if (y > maxY || (y == maxY && x > pts.get(ptMaxY).getX())) {
                maxY = y;
                ptMaxY = i;
            }
        }
        Point r1 = new Point(minX, minY); //bounding box left-lower corner
        Point r2 = new Point(maxX, minY); //bounding box right-lower corner
        Point r3 = new Point(maxX, maxY); //bounding box right-upper corner
        Point r4 = new Point(minX, maxY); //bounding box left-upper corner

        Quadrilatere rect = new Quadrilatere(r1, r2, r3, r4);
        Point[] minRectArray = new Point[]{r1, r2, r3, r4};
        double minAire = rect.getAire();

        Segment caliperX1 = new Segment(r1, r2);
        Segment caliperX2 = new Segment(r4, r3);
        Segment caliperY1 = new Segment(r1, r4);
        Segment caliperY2 = new Segment(r2, r3);

        int listSize = pts.size();
        Segment seg1 = new Segment(pts.get(ptMinX % listSize), pts.get((ptMinX + 1) % listSize));
        Segment seg2 = new Segment(pts.get(ptMaxX % listSize), pts.get((ptMaxX + 1) % listSize));
        Segment seg3 = new Segment(pts.get(ptMinY % listSize), pts.get((ptMinY + 1) % listSize));
        Segment seg4 = new Segment(pts.get(ptMaxY % listSize), pts.get((ptMaxY + 1) % listSize));


        double halfPI = Math.PI / 2;
        double j = 0;
        while (j <= halfPI) {

            double teta1 = seg1.findAngleToPoint(caliperX1.getP2());
            teta1 = teta1 > halfPI ? teta1 - halfPI : teta1;
            double teta2 = seg2.findAngleToPoint(caliperX2.getP2());
            teta2 = teta2 > halfPI ? teta2 - halfPI : teta2;
            double teta3 = seg3.findAngleToPoint(caliperY1.getP2());
            teta3 = teta3 > halfPI ? teta3 - halfPI : teta3;
            double teta4 = seg4.findAngleToPoint(caliperY2.getP2());
            teta4 = teta4 > halfPI ? teta4 - halfPI : teta4;

            double minAngle = Math.min(teta1, teta2);
            double minAngle2 = Math.min(teta3, teta4);
            minAngle = Math.min(minAngle, minAngle2);
            if (minAngle == 0) minAngle = Math.max(minAngle, minAngle2);

            int ptRotation = 0;
            if (teta1 == minAngle) {
                ptRotation = ptMinX;
                ptMinX = (ptMinX + 1) % listSize;
                seg1 = new Segment(pts.get(ptMinX % listSize), pts.get((ptMinX + 1) % listSize));
            } else if (teta2 == minAngle) {
                ptRotation = ptMaxX;
                ptMaxX = (ptMaxX + 1) % listSize;
                seg2 = new Segment(pts.get(ptMaxX % listSize), pts.get((ptMaxX + 1) % listSize));
            } else if (teta3 == minAngle) {
                ptRotation = ptMinY;
                ptMinY = (ptMinY + 1) % listSize;
                seg3 = new Segment(pts.get(ptMinY % listSize), pts.get((ptMinY + 1) % listSize));
            } else if (teta4 == minAngle) {
                ptRotation = ptMaxY;
                ptMaxY = (ptMaxY + 1) % listSize;
                seg4 = new Segment(pts.get(ptMaxY % listSize), pts.get((ptMaxY + 1) % listSize));
            }

            Point ptR = pts.get(ptRotation);

            caliperX1.rotation(r1, minAngle);
            caliperX2.rotation(r3, minAngle);
            caliperY1.rotation(r4, minAngle);
            caliperY2.rotation(r2, minAngle);
            r1 = caliperX1.crossPoint(caliperY1);
            r2 = caliperX1.crossPoint(caliperY2);
            r3 = caliperX2.crossPoint(caliperY2);
            r4 = caliperX2.crossPoint(caliperY1);

            double newAire = new Quadrilatere(r1, r2, r3, r4).getAire();

            if (newAire <= minAire) {
                minAire = newAire;
                minRectArray = new Point[]{r1, r2, r3, r4};
            }
            j += minAngle;
        }
        return Arrays.asList(minRectArray);
    }


	static public List<Segment> pointsToSegments(List<Point> pts) {
		List<Segment> listSeg = new ArrayList<>();
		for (int i = 0; i < pts.size() - 2; i++) {
			listSeg.add(new Segment(pts.get(i), pts.get(i + 1)));
		}
		return listSeg;
	}

	public static List<Object> findVertices(List<Point> pts){
		int cpt = 0;
		List<Object> finalList = new ArrayList<>();
		List<Point> vertices = new ArrayList<>();
		List<Integer> verticesIndices = new ArrayList<>();
		int listSize = pts.size();
		if(listSize < 3) {
			finalList.add(verticesIndices);
			finalList.add(vertices);
			finalList.add(0);
			return finalList;
		}
		for(int i = 1; i < pts.size(); i++){
			double courbure = MethodesForme.courbure(pts.get(i-1),pts.get(i%listSize),pts.get((i+1)%listSize));
			//System.out.println(Math.toDegrees(courbure));
			if(Math.abs(Math.toDegrees(courbure)) > 30){
				cpt += 1;
				verticesIndices.add(i%listSize);
				vertices.add(pts.get(i%listSize));
			}
		}
		finalList.add(verticesIndices);
		finalList.add(vertices);
		finalList.add(cpt);
		return finalList;
	}
}
