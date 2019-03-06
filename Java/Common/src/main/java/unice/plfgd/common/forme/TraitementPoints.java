package unice.plfgd.common.forme;

import java.util.*;

public class TraitementPoints {

    public static List<Point> sanitize(List<Point> pointList, double tolerance){
        if(pointList.size() < 3){
            return  pointList;
        }
        Segment l = new Segment(pointList.get(0),pointList.get(pointList.size()-1));
        // Find the point furthest away from the line segment
        double maxDistance = -1;
        int indexOfFurthestPoint = -1;


        for(int i = 2; i < pointList.size(); ++i){
            Point pi = pointList.get(i);
            if (l.distPtToSeg(pi) > maxDistance) {
                maxDistance = l.distPtToSeg(pi);
                indexOfFurthestPoint = i;
            }
        }
        if(maxDistance <= tolerance){
            return Arrays.asList(pointList.get(0),pointList.get(pointList.size()-1));
        }
        else {// Recursively handle the parts

			ArrayList<Point> left = new ArrayList<>(sanitize(pointList.subList(0, indexOfFurthestPoint), tolerance));
			ArrayList<Point> right = new ArrayList<>(sanitize(pointList.subList(indexOfFurthestPoint, pointList.size()), tolerance));

			if (right.size() > 1) {
			right.remove(0);
			}

			left.addAll(right);

			return left;

        }
    }

    public static List<Point> refineEndPoints(List<Point> pts, int binSize){
        int maxBinPts = pts.size()-1;

        List<Point> binStart = new ArrayList<>();
        int i = 0;
        for(; i < maxBinPts; i++){
            if(binStart.size() <= binSize) binStart.add(pts.get(i));
            else break;
        }
        Point start = MethodesForme.barycentre(binStart);
        start = closestDiscretePoint(start);

        List<Point> binEnd = new ArrayList<>();
        int j = 0;
        for(; j < maxBinPts; j++){
            if(binEnd.size() <= binSize) binEnd.add(pts.get(maxBinPts-j));
            else break;
        }
        Point end = MethodesForme.barycentre(binEnd);
        end = closestDiscretePoint(end);

        List<Point> result = new ArrayList<>();
        result.add(start);
        for(int k = i; k < maxBinPts-j; k++) result.add(pts.get(k));
        result.add(end);
        return result;
    }

    public static Point closestDiscretePoint(Point pt){
        double x = pt.getX();
        double y = pt.getY();
        x = (x - Math.floor(x)) < (Math.ceil(x) - x) ? Math.floor(x) : Math.ceil(x);
        y = (y - Math.floor(y)) < (Math.ceil(y) - y) ? Math.floor(y) : Math.ceil(y);
        return new Point(x, y);
    }
}
