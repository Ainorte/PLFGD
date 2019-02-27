import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;

public class TraitementPoints {

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
            if(seg.isPointInAxis(barycentre, 0.5)) new_segs.add(seg);  
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

    static List<Point> generatePtsFromSeg(Segment seg, int pas){
        List<Point> newListPts = new ArrayList<>();
        List<Double> listX = new ArrayList<>();
        List<Double> listY = new ArrayList<>();
        double x1 = seg.getP1().getX();
        double y1 = seg.getP1().getY();
        double x2 = seg.getP2().getX();
        double y2 = seg.getP2().getY();
        double nbPasX = (x1 - x2) != 0 ? Math.abs(x1 - x2)/pas : 0;
        double nbPasY = (y1 - y2) != 0 ? Math.abs(y1 - y2)/pas : 0;
        double maxNbPas = Math.max(nbPasX, nbPasY);
        double ratio = nbPasX < nbPasY ? nbPasY / nbPasX : nbPasX / nbPasY;
        double petitPas = pas / ratio;
        listX.add(x1);
        if(x1 < x2){
            double x = x1;
            for(int i = 0; i < maxNbPas; i++){
                if(nbPasX >= nbPasY){
                    x += pas;
                    listX.add(x);
                } else {
                    x += petitPas;
                    listX.add(x);
                }
            }
        } else {
            double x = x1;
            for(int i = 0; i < maxNbPas; i++){
                if(nbPasX >= nbPasY){
                    x -= pas;
                    listX.add(x);
                } else {
                    x -= petitPas;
                    listX.add(x);
                }
            }
        }
        listY.add(y1);
        if(y1 < y2){
            double y = y1;
            for(int i = 0; i < maxNbPas; i++){
                if(nbPasY >= nbPasX){
                    y += pas;
                    listY.add(y);
                } else {
                    y += petitPas;
                    listY.add(y);
                }
            }
        } else {
            double y = y1;
            for(int i = 0; i < maxNbPas; i++){
                if(nbPasY >= nbPasX){
                    y -= pas;
                    listY.add(y);
                } else {
                    y -= petitPas;
                    listY.add(y);
                }
            }
        }
        listX.add(x2);
        listY.add(y2);
        for(int i = 0; i < listX.size(); i++) newListPts.add(new Point(listX.get(i), listY.get(i)));
        return newListPts;
    }


    public static void main(String[] args) {
        List<Point> pts1 = Arrays.asList(new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(5, 5), new Point(6, 6), new Point(7, 7));
        List<Point> pts2 = filterPointsByInterval(pts1, 2);
        for(Point pt : pts2) System.out.println(pt.getX());
        List<Point> pts3 = filterPointsByDistance(pts1, 2);
        for(Point pt : pts3) System.out.println(pt.getX());
        List<Segment> segs1 = pointsToSegments(pts1, 2);
        Segment testSeg = new Segment(new Point(10, 20), new Point(470, 2));
        System.out.println(segs1);
        System.out.println(segmentsToSegments(segs1));
        System.out.println(generatePtsFromSeg(testSeg, 10));
    }
}