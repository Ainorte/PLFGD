package unice.plfgd.common.forme;

import unice.plfgd.common.data.Draw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Identifier {

	static public List<Point> sanitize(List<Point> ptList, int tolerance) {
		if (ptList.size() < 3) {
			return ptList;
		}
		Segment l = new Segment(ptList.get(0), ptList.get(ptList.size() - 1));
		// Find the point furthest away from the line segment
		double maxDistance = -1;
		int indexOfFurthestPoint = -1;

		for (int i = 2; i <= ptList.size() - 1; ++i) {
			Point pi = ptList.get(i);
			if (util.distToSegment(pi, l) > maxDistance) {
				maxDistance = util.distToSegment(pi, l);
				indexOfFurthestPoint = i;
			}
		}
		if (maxDistance <= tolerance) {
			return Arrays.asList(ptList.get(0), ptList.get(ptList.size() - 1));
		} else {// Recursively handle the parts

			ArrayList<Point> left = new ArrayList<>(sanitize(ptList.subList(0, indexOfFurthestPoint), tolerance));
			ArrayList<Point> right = new ArrayList<>(sanitize(ptList.subList(indexOfFurthestPoint, ptList.size()), tolerance));

			if (right.size() > 1) {
				right.remove(0);
			}

			left.addAll(right);

			return left;
		}
	}

	static public String identify(Draw draw, int tolerance) {
		List<Point> ptList = sanitize(draw.getPts(), tolerance);

		//TODO Identify the shape;

		return "Server was unable to identify drawing";
	}

	;


}
