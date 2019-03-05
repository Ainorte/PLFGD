package unice.plfgd.common.forme;

import java.util.*;

public class TraitementPoints {

    static public List<Point> sanitize(List<Point> pointList, double tolerance){
        if(pointList.size() < 3){
            return  pointList;
        }
        Segment l = new Segment(pointList.get(0),pointList.get(pointList.size()-1));
        // Find the point furthest away from the line segment
        double maxDistance = -1;
        Point pz = null;
        int indexOfFurthestPoint = -1;
        for(int i = 2; i < pointList.size()-1; ++i){
            Point pi = pointList.get(i);
            if (l.distPtToSeg(pi) > maxDistance) {
                maxDistance = l.distPtToSeg(pi);
                pz = pi;
                indexOfFurthestPoint = i;
            }
        }
        if(maxDistance <= tolerance){
            List<Point> result = Arrays.asList(pointList.get(0),pointList.get(pointList.size()-1));
            return result;
        }
        else {// Recursively handle the parts
            List<Point> left = sanitize(pointList.subList(0, indexOfFurthestPoint), tolerance);
            List<Point> right = sanitize(pointList.subList(indexOfFurthestPoint, pointList.size() - 1), tolerance);
            List<Point> result = new ArrayList<>();
            result.addAll(left);
            result.addAll(right);
            Set<Point> set = new LinkedHashSet<>();
            set.addAll(result);
            result.clear();
            result.addAll(set);
            return result;
        }
    }
}
