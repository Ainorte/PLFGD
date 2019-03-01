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

    static public List<Point> filterPointsByInterval(List<Point> pts, int itv){
        List<Point> new_pts = new ArrayList<>();
        int listSize = pts.size();
        int npts = listSize / itv;
        for(int i = 0; i < listSize; i += itv){
            if(listSize - i < itv) itv = listSize % itv;
            new_pts.add(pts.get(i));
        }
        return new_pts;    
    } 

    static public List<Point> filterPointsByDistance(List<Point> pts, float dist){
        List<Point> new_pts = new ArrayList<>();
        int i = 0;
        new_pts.add(pts.get(0));
        for(int j = i + 1; j < pts.size(); j++){
            if(Math.abs(MethodesForme.norme(pts.get(i), pts.get(j))) >= dist){
                new_pts.add(pts.get(j));
                i = j;
            }
        }
        return new_pts;
    }

    // Transforme une liste de points en liste de segments
    static public List<Segment> pointsToSegments(List<Point> pts, int itv){
        List<Segment> new_segs = new ArrayList<>();
        int listSize = pts.size();
        int npts = listSize / itv;
        for(int i = 0; i < listSize; i += itv){
            if(listSize - i < itv) itv = listSize % itv;
            Segment seg = new Segment(pts.get(i), pts.get(i+itv-1));
            Point barycentre = MethodesForme.barycentre(pts.subList(i, i+itv));
            if(seg.isPointInAxis(barycentre, 1500)) new_segs.add(seg);  
        }
        return new_segs;
    }

    static public List<Segment> segmentsToSegments(List<Segment> segs){
        List<Segment> new_segs = new ArrayList<>();
        int i = 0;
        Point firstPoint = segs.get(i).getP1();
        Point lastPoint = segs.get(i).getP2();
        for(int j = i + 1; j < segs.size(); j++){
            if(segs.get(i).isSegmentInAxis(segs.get(j), 0.5)){
                lastPoint = segs.get(j).getP2();   
            } else {
                new_segs.add(new Segment(firstPoint, lastPoint));
                firstPoint = segs.get(j).getP1();
                lastPoint = segs.get(j).getP2();
                i = j;
            }
        }
        new_segs.add(new Segment(firstPoint, lastPoint));
        return new_segs;
    }



    public static void main(String[] args) {
        List<Point> pts1 = Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7));
        List<Point> pts2 = filterPointsByInterval(pts1, 2);
        for(Point pt : pts2) System.out.println(pt.getX());
        List<Point> pts3 = filterPointsByDistance(pts1, 2);
        for(Point pt : pts3) System.out.println(pt.getX());
        List<Segment> segs1 = pointsToSegments(pts1, 2);
        System.out.println(segs1);
        System.out.println(segmentsToSegments(segs1));
    }
}