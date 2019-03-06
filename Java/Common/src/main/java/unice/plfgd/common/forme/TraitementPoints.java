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
}
