package unice.plfgd.common.forme.method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import unice.plfgd.common.forme.forme.Point;
import unice.plfgd.common.forme.forme.Quadrilatere;
import unice.plfgd.common.forme.forme.Segment;
import unice.plfgd.common.forme.forme.Triangle;


public class TraitementPoints {


    public static List<Point> mergeList(List<List<Point>> pointList) {
        List<Point> base = pointList.get(0);
        for (int i = 1; i < pointList.size(); i++) {
            List<Point> merge = pointList.get(i);

            Point baseStart = base.get(0);
            Point baseEnd = base.get(base.size() - 1);

            Point mergeStart = merge.get(0);
            Point mergeEnd = merge.get(merge.size() - 1);

            double distanceStartToStart = utils.norme(baseStart, mergeStart);
            double distanceEndToStart = utils.norme(baseEnd, mergeStart);
            double distanceEndToEnd = utils.norme(baseEnd, mergeEnd);

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
        Point start = utils.barycentre(binStart);
        start = closestDiscretePoint(start);

        List<Point> binEnd = new ArrayList<>();
        int j = 0;
        for (; j < maxBinPts; j++) {
            if (binEnd.size() <= binSize) binEnd.add(pts.get(maxBinPts - j));
            else break;
        }
        Point end = utils.barycentre(binEnd);
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
            if (utils.norme(inter, pts.get(0)) < utils.norme(inter, pts.get(1))
                    & utils.norme(inter, pts.get(listSize - 1)) < utils.norme(inter, pts.get(listSize - 2))) {
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
                    Double distHeadCross = utils.norme(headExt.getP1(), headCross);
                    if (distHeadCross < utils.norme(headExt.getP2(), headCross)) {
                        if (distHeadCross <= MDE) {
                            pts = pts.subList(0, i + 1);
                            pts.add(0, headCross);
                            pts.add(headCross);
                            break;
                        }
                    }
                    Double distTailCross = utils.norme(tailExt.getP2(), tailCross);
                    if (distTailCross < utils.norme(tailExt.getP1(), tailCross)) {
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
            double distStart = utils.norme(inter, startPoint);
            double distEnd = utils.norme(inter, endPoint);
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


    public static List<Point> minimumAreaEnclosingRectangle2(List<Point> pts) {
        return RotatingCalipers.getMinimumBoundingRectangle(pts);
    }

    public static List<Point> minimumAreaEnclosingRectangle(List<Point> pts){

        int listSize = pts.size();
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = 0;
        double maxY = 0;
        int iMinX = 0; //indice du point avec le plus petit x
        int iMinY= 0;
        int iMaxX = 0;
        int iMaxY = 0;
        Point ptMinX = pts.get(iMinX%listSize);
        Point ptMinY = pts.get(iMinY%listSize);
        Point ptMaxX = pts.get(iMaxX%listSize);
        Point ptMaxY = pts.get(iMaxY%listSize);

        for(int i = 0; i < pts.size()-1; i++){
            double x = pts.get(i).getX();
            double y = pts.get(i).getY();
            if(x < minX || (x == minX && y < pts.get(iMinX).getY())) {
                minX = x;
                iMinX = i;
            }
            if(x > maxX || (x == maxX && y > pts.get(iMaxX).getY())){
                maxX = x;
                iMaxX = i;
            }
            if(y < minY || (y == minY && x < pts.get(iMinY).getX())) {
                minY = y;
                iMinY = i;
            }
            if(y > maxY || (y == maxY && x > pts.get(iMaxY).getX())) {
                maxY = y;
                iMaxY = i;
            }
        }
        Point r1 = new Point(minX, minY); //bounding box left-lower corner
        Point r2 = new Point(maxX, minY); //bounding box right-lower corner
        Point r3 = new Point(maxX, maxY); //bounding box right-upper corner
        Point r4 = new Point(minX, maxY); //bounding box left-upper corner

        Quadrilatere rect = new Quadrilatere(r1,r2,r3,r4);
        Point[] minRectArray = new Point[]{r1,r2,r3,r4};

        double minAire = rect.getAire();

        Segment caliperX1 = new Segment(r1, r2);
        Segment caliperX2 = new Segment(r4, r3);
        Segment caliperY1 = new Segment(r1, r4);
        Segment caliperY2 = new Segment(r2, r3);


        Segment seg1 = new Segment(pts.get(iMinX%listSize), pts.get((iMinX+1)%listSize));
        Segment seg2 = new Segment(pts.get(iMaxX%listSize), pts.get((iMaxX+1)%listSize));
        Segment seg3 = new Segment(pts.get(iMinY%listSize), pts.get((iMinY+1)%listSize));
        Segment seg4 = new Segment(pts.get(iMaxY%listSize), pts.get((iMaxY+1)%listSize));


        double halfPI = Math.PI/2;
        double j = 0;
        while(j <= halfPI){

            double teta1 = seg1.findAngleToPoint(caliperY1.getP2());
            teta1 = teta1 > halfPI ? teta1 - halfPI : teta1;
            double teta2 = seg2.findAngleToPoint(caliperY2.getP1());
            teta2 = teta2 > halfPI ? teta2 - halfPI : teta2;
            double teta3 = seg3.findAngleToPoint(caliperX1.getP1());
            teta3 = teta3 > halfPI ? teta3 - halfPI : teta3;
            double teta4 = seg4.findAngleToPoint(caliperX2.getP2());
            teta4 = teta4 > halfPI ? teta4 - halfPI : teta4;

            double minAngle = Math.min(teta1,teta2);
            double minAngle2 = Math.min(teta3,teta4);
            minAngle = Math.min(minAngle,minAngle2);

            caliperX1.rotation(ptMinX,minAngle);
            caliperX2.rotation(ptMaxX,minAngle);
            caliperY1.rotation(ptMinY,minAngle);
            caliperY2.rotation(ptMaxY,minAngle);
            if(teta1 == minAngle){
                iMinX = (iMinX+1)%listSize;
                ptMinX = pts.get(iMinX%listSize);
                seg1 = new Segment(pts.get(iMinX%listSize), pts.get((iMinX+1)%listSize));
            }
            else if(teta2 == minAngle){
                iMaxX = (iMaxX+1)%listSize;
                ptMaxX = pts.get(iMaxX%listSize);
                seg2 = new Segment(pts.get(iMaxX%listSize), pts.get((iMaxX+1)%listSize));
            }
            else if(teta3 == minAngle){
                iMinY = (iMinY+1)%listSize;
                ptMinY = pts.get(iMinY%listSize);
                seg3 = new Segment(pts.get(iMinY%listSize), pts.get((iMinY+1)%listSize));
            }
            else if(teta4 == minAngle){
                iMaxY = (iMaxY+1)%listSize;
                ptMaxY = pts.get(iMaxY%listSize);
                seg4 = new Segment(pts.get(iMaxY%listSize), pts.get((iMaxY+1)%listSize));
            }

            r1 = caliperX1.crossPoint(caliperY1);
            r2 = caliperX1.crossPoint(caliperY2);
            r3 = caliperX2.crossPoint(caliperY2);
            r4 = caliperX2.crossPoint(caliperY1);

            double newAire = new Quadrilatere(r1,r2,r3,r4).getAire();

            if(newAire <= minAire){
                minAire = newAire;
                minRectArray = new Point[]{r1,r2,r3,r4};
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

    public static List<Object> findVertex(List<Point> pts) {
        int cpt = 0;
        List<Object> finalList = new ArrayList<>();
        List<Point> vertices = new ArrayList<>();
        List<Integer> verticesIndices = new ArrayList<>();
        int listSize = pts.size();
        if (listSize < 3) {
            finalList.add(verticesIndices);
            finalList.add(vertices);
            finalList.add(0);
            return finalList;
        }
        for (int i = 1; i < pts.size(); i++) {
            double halfPI = Math.PI/2;
            double courbure = utils.courbure(pts.get(i - 1), pts.get(i % listSize), pts.get((i + 1) % listSize));
            courbure = courbure > halfPI ?  courbure - halfPI : courbure;

            //System.out.println(Math.toDegrees(courbure));
            if (Math.abs(Math.toDegrees(courbure)) > 25) {
                cpt += 1;
                verticesIndices.add(i % listSize);
                vertices.add(pts.get(i % listSize));
            }
        }
        finalList.add(verticesIndices);
        finalList.add(vertices);
        finalList.add(cpt);
        return finalList;
    }
}
