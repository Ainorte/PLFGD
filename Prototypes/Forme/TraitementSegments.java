import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.ArrayList;

public class TraitementSegments{

    static public List<Point> trouverSommets(List<Segment> segs){
        List<Point> sommets = new ArrayList<>();
        if(segs.size() > 1){
            for(int i = 0; i < segs.size()-1; i++){
                for(int j = i + 1; j < segs.size(); j++){
                    if(!segs.get(i).isSegmentParallel(segs.get(j))){
                        Point crossPoint = segs.get(i).crossPoint(segs.get(j));
                        System.out.println(crossPoint);
                        if(segs.get(i).isPointInSeg(crossPoint, 0.5) & segs.get(j).isPointInSeg(crossPoint, 0.5)){
                            sommets.add(crossPoint);
                        }
                    }
                }
            }
        } else {
            sommets.add(segs.get(0).getP1());
            sommets.add(segs.get(0).getP2());
        }
        return sommets;
    }

    public static void main(String[] args) {
        List<Segment> triangle = Arrays.asList(new Segment(new Point(0,0), new Point(1,1)), new Segment(new Point(1,1), new Point(2,0)), new Segment(new Point(2,0), new Point(0,0)));
        List<Segment> carre = Arrays.asList(new Segment(new Point(0,5), new Point(5,5)),new Segment(new Point(5,5), new Point(5,0)), new Segment(new Point(5,0), new Point(0,0)), new Segment(new Point(0,0), new Point(0,5)));
        System.out.println(trouverSommets(triangle));
        System.out.println(trouverSommets(carre));
        Segment testSeg = new Segment(new Point(0,0), new Point(2,0));
        System.out.println(testSeg.isPointInSeg(new Point(2,0), 0.5));
    }
}