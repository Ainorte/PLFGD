package unice.plfgd.common.forme;

import java.util.*;

public class Rectangle extends AbstractForme {

    protected double h;
    protected double l;
    protected double rot;


    public Rectangle(Point G, double h, double l, double rot) {
        super(G);
        this.h = h;
        this.l = l;
        this.rot = rot;
    }

    public double getAire() {
        return this.h * this.l;
    }

    public double getPerim() {
        return (this.h + this.l) * 2;
    }

    public String toString() {
        String bary = super.toString();
        String aire = ", l'aire est de : " + this.getAire();
        String perim = " et le périmètre est de : " + this.getPerim();
        return bary + aire + perim;
    }
    
    public List<Point> make() {
        Point A = new Point(G.getX() - l / 2, G.getY() + h / 2);
        Point B = new Point(G.getX() + l / 2, G.getY() + h / 2);
        Point C = new Point(G.getX() + l / 2, G.getY() - h / 2);
        Point D = new Point(G.getX() - l / 2, G.getY() - h / 2);
        Segment[] segmentsRect = new Segment[]{new Segment(A, B), new Segment(B, C), new Segment(C, D), new Segment(D, A)};
        for (Segment seg : segmentsRect) seg.rotation(this.getG(), rot);
        List<Point> listPts = new ArrayList<>();
        for (Segment seg : segmentsRect)
            listPts.addAll(GenerationPoints.generatePtsFromSeg(seg, 10));
        return listPts;
    }
}