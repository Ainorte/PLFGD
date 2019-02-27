
public class Segment {

    protected Point p1;
    protected Point p2;
    protected double coefDir;
    protected double constReelle;

    public Segment(Point p1, Point p2){
        this.p1 = p1;
        this.p2 = p2;
        this.coefDir = p1.getX() - p2.getX() != 0 ? (p1.getY() - p2.getY()) / (p1.getX() - p2.getX()) : p1.getY() - p2.getY();
        this.constReelle = p1.getY() - coefDir * p1.getX(); 
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

    //Rotation du segment autour d'un point [x0, y0] avec un angle donné
    public void rotation(double x0, double y0, double rot){
        rot = MethodesForme.degToRad(rot);
        double new_x1 = (p1.getX() - x0) * Math.cos(rot) - (p1.getY() - y0) * Math.sin(rot) + x0;
        double new_y1 = (p1.getX() - x0) * Math.sin(rot) - (p1.getY() - y0) * Math.cos(rot) + y0;
        double new_x2 = (p2.getX() - x0) * Math.cos(rot) - (p2.getY() - y0) * Math.sin(rot) + x0;
        double new_y2 = (p2.getX() - x0) * Math.sin(rot) - (p2.getY() - y0) * Math.cos(rot) + y0;
        p1.setX(new_x1);
        p1.setY(new_y1);
        p2.setX(new_x2);
        p2.setY(new_y2);
    }

    //Savoir si un point appartient à l'axe du segment
    public boolean isPointInAxis(Point pt, double tolerance){
        return Math.abs(pt.getY() - pt.getX() * coefDir + constReelle) < tolerance;
    }

    //Savoir si un autre segment appartient à l'axe du segment (avec degré de tolérance)
    public boolean isSegmentInAxis(Segment seg, double tolerance){
        return Math.abs(coefDir - seg.getCoef() + constReelle - seg.getConst()) < tolerance;
    }

    //Point de croisement de deux segments prolongés sur leurs axes
    public Point crossPoint(Segment seg){
        double x = coefDir - seg.getCoef() != 0 ? (seg.getConst() - constReelle) / (coefDir - seg.getCoef()) : seg.getConst() - constReelle;
        double y = coefDir * x + constReelle;
        return new Point(x,y);
    }

    //Connaitre l'angle d'un axe partant de l'origine p1 du segment vers un point quelconque 
    public double findAngleToPoint(Point pt){
        double OA = MethodesForme.norme(p1, p2);
        double OB = MethodesForme.norme(p1, pt);
        double AB = MethodesForme.norme(p2, pt);
        return Math.acos((OA * OA + OB * OB - AB * AB) / (2 * OA * OB));
    }

    public Point pointSym(Point pt){
        double coefDirPerp = coefDir != 0 ? -1/coefDir : -1;
        double constReellePerp = constReelle - coefDirPerp * coefDir;
        double x0 = coefDir - coefDirPerp != 0 ? (constReellePerp - constReelle) / (coefDir - coefDirPerp) : constReellePerp - constReelle;
        double y0 = coefDir * x0 + constReelle;
        System.out.println(x0 + "," + y0);
        Point pointSym = new Point(pt.getX() - 2 * (pt.getX() - x0), pt.getY() - 2 * (pt.getY() - y0));
        //Point pointSym = MethodesForme.rotation(pt, x0, y0, Math.PI/2);
        return pointSym; 
    }


    public static void main(String[] args) {
        Segment seg1 = new Segment(new Point(1, 2), new Point(4, 5));
        Segment seg2 = new Segment(new Point(2, 0), new Point(5, 0));
        Point c = new Point(1,0);
        //System.out.println(seg1.isPointInAxis(c));
        //System.out.println(seg1.findAngleToPoint(c) * 180 / Math.PI);
        System.out.println(seg1.pointSym(c).getX());
        System.out.println(seg1.pointSym(c).getY());
        //System.out.println(seg1.getCoef());
        //System.out.println(seg1.getConst());
        //System.out.println(seg1.crossPoint(seg2));
    }
}