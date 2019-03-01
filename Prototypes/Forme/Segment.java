
public class Segment {

    protected Point p1;
    protected Point p2;
    protected double coefDir;
    protected double constReelle;

    public Segment(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
        this.coefDir = p1.getX() - p2.getX() != 0 ? (p1.getY() - p2.getY()) / (p1.getX() - p2.getX()) : Double.POSITIVE_INFINITY;
        this.constReelle = coefDir != Double.POSITIVE_INFINITY ? p1.getY() - coefDir * p1.getX() : Double.NEGATIVE_INFINITY; 
    }

    public Point getP1(){
        return p1;
    }

    public Point getP2(){
        return p2;
    }

    public double getCoef(){
        return coefDir;
    }

    public double getConst(){
        return constReelle;
    }

    public String toString(){
        return "Segment([" + p1.getX() + ";" + p1.getY() + "]" + "[" + p2.getX() + ";" + p2.getY() + "])";
    }

    //Rotation du segment autour d'un point [x0, y0] avec un angle donné
    public void rotation(Point pt0, double rot){
        p1.rotation(pt0, rot);
        p2.rotation(pt0, rot);
    }
    public Point barycentre(){
        return new Point((p1.getX()+p2.getX())/2,(p1.getY()+p2.getY())/2);
    }

    //Savoir si un point appartient à l'axe du segment
    public boolean isPointInAxis(Point pt, double tolerance){
        if(coefDir == Double.POSITIVE_INFINITY) return pt.getX() == p1.getX();
        else return Math.abs(pt.getY() - pt.getX() * coefDir + constReelle) < tolerance;
    }

    public boolean isPointInSeg(Point pt, double tolerance){
        //if(pt.equals(p1)||pt.equals(p2)) return true;
        return (Math.min(p1.getX(),p2.getX()) - tolerance <= pt.getX() && pt.getX() <= Math.max(p1.getX(),p2.getX()) + tolerance) 
            &  (Math.min(p1.getY(),p2.getY()) - tolerance <= pt.getY() && pt.getY() <= Math.max(p1.getY(),p2.getY()) + tolerance);
    }

    public boolean isSegmentParallel(Segment seg){
        return seg.getCoef() == coefDir;
    }

    //Savoir si un autre segment appartient à l'axe du segment (avec degré de tolérance)
    public boolean isSegmentInAxis(Segment seg, double tolerance){
        if(coefDir != Double.POSITIVE_INFINITY && seg.getCoef() != Double.POSITIVE_INFINITY){
            return Math.abs(coefDir - seg.getCoef() + constReelle - seg.getConst()) < tolerance;
        }
        return Math.abs(seg.getP1().getX() - p1.getX()) < tolerance;
    }

    //Point de croisement de deux segments prolongés sur leurs axes
    public Point crossPoint(Segment seg){
        if(coefDir != Double.POSITIVE_INFINITY && seg.getCoef() != Double.POSITIVE_INFINITY){
            double x = coefDir - seg.getCoef() != 0 ? (seg.getConst() - constReelle) / (coefDir - seg.getCoef()) : Double.POSITIVE_INFINITY;
            double y = coefDir * x + constReelle;
            return new Point(x,y);
        } else {
            if(coefDir != seg.getCoef()) return coefDir == Double.POSITIVE_INFINITY ? new Point(p1.getX(), seg.getCoef() * p1.getX() + seg.getConst()) : new Point(seg.getP1().getX(), coefDir * seg.getP1().getX() + constReelle);
            return new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);  
        }
    }

    //Connaitre l'angle d'un axe partant de l'origine p1 du segment vers un point quelconque 
    public double findAngleToPoint(Point pt){
        double OA = MethodesForme.norme(p1, p2);
        double OB = MethodesForme.norme(p1, pt);
        double AB = MethodesForme.norme(p2, pt);
        return Math.acos((OA * OA + OB * OB - AB * AB) / (2 * OA * OB));
    }

    public double distPtToSeg(Point pt){
        return Math.max(MethodesForme.norme(pt, p1), MethodesForme.norme(pt, p2));
    }
    //A corriger
    public Point pointSym(Point pt){
        double coefDirPerp = coefDir != 0 ? -1/coefDir : Double.NEGATIVE_INFINITY;
        double constReellePerp = constReelle - coefDirPerp * coefDir;
        double x0 = coefDir - coefDirPerp != 0 ? (constReellePerp - constReelle) / (coefDir - coefDirPerp) : Double.POSITIVE_INFINITY;
        double y0 = coefDir * x0 + constReelle;
        //System.out.println(x0 + "," + y0);
        Point pointSym = new Point(pt.getX() - 2 * (pt.getX() - x0), pt.getY() - 2 * (pt.getY() - y0));
        //Point pointSym = MethodesForme.rotation(pt, x0, y0, Math.PI/2);
        return pointSym; 
    }


    public static void main(String[] args) {
        Segment seg1 = new Segment(new Point(1, 1), new Point(3, 3));
        Segment seg2 = new Segment(new Point(0, 0), new Point(5, 0));
        Segment seg3 = new Segment(new Point(5, 5), new Point(0, 5));
        Segment seg4 = new Segment(new Point(5, 0), new Point(5, 5));
        Segment seg5 = new Segment(new Point(1, 1), new Point(2, 0));
        Point c = new Point(1,0);
        //System.out.println(seg1.isPointInAxis(c));
        //System.out.println(seg1.findAngleToPoint(c) * 180 / Math.PI);
        System.out.println(seg1.pointSym(c).getX());
        System.out.println(seg1.pointSym(c).getY());
        //System.out.println(seg1.getCoef());
        //System.out.println(seg1.getConst());
        //System.out.println(seg1.crossPoint(seg2));
        System.out.println(seg4.isPointInSeg(new Point(2,3), 0));
        System.out.println(seg5.isPointInSeg(new Point(1,1), 0));
        System.out.println(seg2.isSegmentParallel(seg3));
        System.out.println(new Point(2,2).equals(new Point(2,2)));
        System.out.println(seg3.crossPoint(seg4));
        seg3.rotation(new Point(2.5, 2.5), 90);
        System.out.println(seg3.toString());
        seg3.toString();
    }
}