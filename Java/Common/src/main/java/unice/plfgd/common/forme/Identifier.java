package unice.plfgd.common.forme;

import unice.plfgd.common.data.Draw;

import java.util.Arrays;
import java.util.List;

public class Identifier {

	static public List<Point> sanitize(List<Point> ptList, int tolerance){
		if(ptList.size() <3){
			return ptList;
		}
		Segment l = new Segment(ptList.get(0), ptList.get(ptList.size()-1));
		// Find the point furthest away from the line segment
		double maxDistance = -1;
		Point pz = null;
		int indexOfFurthestPoint = -1;
		for(int i = 2; i <= ptList.size()-1; ++i){
			Point pi = ptList.get(i);
			if (util.distToSegment(pi,l) > maxDistance) {
				maxDistance = util.distToSegment(pi, l);
				pz = pi;
				indexOfFurthestPoint = i;
			}
		}
		if(maxDistance <= tolerance){
			List<Point> result = Arrays.asList(ptList.get(0), ptList.get(ptList.size()-1));
			return result;
			}
		else {// Recursively handle the parts
			List<Point> left = sanitize(ptList.subList(0, indexOfFurthestPoint), tolerance);
			List<Point> right = sanitize(ptList.subList(indexOfFurthestPoint, ptList.size() - 1), tolerance);

			right.remove(0);
			left.addAll(right);
			return left;
		}
	}

	static public String identify(Draw draw, int tolerance){
		List<Point> ptList = sanitize(draw.getPts(),tolerance);

		//TODO Identify the shape;
		return "Server was unable to identify drawing";
	};


}
