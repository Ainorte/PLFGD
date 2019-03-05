public class Ellipse extends AbstractForme{

    protected Point G;
    protected double a;
    protected double b;
    protected double rot;

    public Ellipse(Point G, double a, double b, double rot){
        super(G);
        this.a = a;
        this.b = b;
        this.rot = rot;
    }

    public double getAire(){
        return Math.PI * this.a * this.b;
    }

    public double getPerim(){
        return Math.PI * Math.sqrt(2 * (this.a * this.a + this.b * this.b));
    }

    public String toString(){
        String G = "La position d'ancrage est à : " + this.getG().getX() + " " + this.getG().getY();
        String aire = ", l'aire est de : " + this.getAire();
        String perim = " et le périmètre est de : " + this.getPerim();
        return G + aire + perim;
    }

    public static void main(String[] args) {
        Ellipse test1 = new Ellipse(new Point(5, 0), 3, 5, 0);
        System.out.println(test1.toString());
    }

}