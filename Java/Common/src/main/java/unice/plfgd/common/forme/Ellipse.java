package unice.plfgd.common.forme;

import java.util.*;

public class Ellipse extends AbstractForme{

    protected Point G;
    protected double a;
    protected double b;
    protected double rot;
    protected Forme type = Forme.ELLIPSE;

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

    public Forme getType(){
        return type;
    }

    @Override
    public String toString(){
        String G = "La position d'ancrage est à : " + this.getG().getX() + " " + this.getG().getY();
        String aire = ", l'aire est de : " + this.getAire();
        String perim = " et le périmètre est de : " + this.getPerim();
        return G + aire + perim;
    }

    // A faire
    public List<Point> make(){
        List<Point> listPts = new ArrayList<>();
        return listPts;
    }
}