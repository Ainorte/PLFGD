public class Cercle extends Ellipse {

    Cercle(Point G, double a, double rot){
        super(G, a, a, rot);
    }

    public double getAire(){
        return 2 * Math.PI * a;
    }

    public double getPerim(){
        return Math.PI * Math.sqrt(4 * a * a);
    }

    public String toString(){
        return super.toString();
    }

    public static void main(String[] args) {
        Cercle test1 = new Cercle(new Point(6, 0), 5, 4);
        System.out.println(test1.toString());
    }
}