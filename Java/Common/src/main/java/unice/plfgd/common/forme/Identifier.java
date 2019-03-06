package unice.plfgd.common.forme;

import unice.plfgd.common.data.Draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Identifier {

	static public List<Point> sanitize(List<Point> pointList, int tolerance){
		if(pointList.size() <3){
			return  pointList;
		}
		Segment l = new Segment(pointList.get(0),pointList.get(pointList.size()-1));
		// Find the point furthest away from the line segment
		double maxDistance = -1;
		Point pz = null;
		int indexOfFurthestPoint = -1;
		for(int i = 2; i <= pointList.size()-1; ++i){
			Point pi = pointList.get(i);
			if (util.distToSegment(pi,l) > maxDistance) {
				maxDistance = util.distToSegment(pi, l);
				pz = pi;
				indexOfFurthestPoint = i;
			}
		}
		if(maxDistance <= tolerance){
			List<Point> result = Arrays.asList(pointList.get(0),pointList.get(pointList.size()-1));
			return result;
			}
		else {// Recursively handle the parts
			ArrayList<Point> left = new ArrayList<Point>(sanitize(pointList.subList(0, indexOfFurthestPoint), tolerance));
			ArrayList<Point> right = new ArrayList<Point>(sanitize(pointList.subList(indexOfFurthestPoint, pointList.size()), tolerance));
			if(right.size() != 0) {
				right.remove(0);
			}

			left.addAll(right);
			return left;
		}
	}

	static public String identify(Draw draw, int tolerance){
		List<Point> pointList = sanitize(draw.getPoints(),tolerance);

		//TODO Identify the shape;

		return "Server was unable to identify drawing";
	};


}
