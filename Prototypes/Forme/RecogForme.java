public class RecogForme {

    public List<Object> recogForme(List<Point> pts){
        List<Segment> axesSym = MethodesForme.axesSym(pts);
        double[] invariants =  momentsInvariants(pts);

    }

}