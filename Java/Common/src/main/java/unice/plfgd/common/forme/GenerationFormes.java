package unice.plfgd.common.forme;

import java.util.*;

public class GenerationFormes {

    public static List<Point> generateRandomForme(double l, double h, boolean isCentered, boolean isRotated){
        List<Point> randForme = new ArrayList<>();
        double randRotation = 0;
        if(isRotated) randRotation = randRotation();
        int random = (int)(Math.random() * 4 + 1);
        switch(random){
            case 1:
                List<Object> objCarre = new ArrayList<>();
                objCarre.add("carre");
                Point randG = isCentered ? new Point(l/2, h/2) : randG(l,h);
                double randL = randL(randG,l,h);
                objCarre.add(randG);
                objCarre.add(randL);
                objCarre.add(randRotation);
                randForme = FormeFactory.make(objCarre);
                break;
            case 2:
                List<Object> objRect = new ArrayList<>();
                objRect.add("rect");
                Point randG2 = isCentered ? new Point(l/2, h/2) : randG(l,h);
                double randL2 = randL(randG2,l,h);
                double randH2 = randL(randG2,l,h);
                objRect.add(randG2);
                objRect.add(randL2);
                objRect.add(randH2);
                objRect.add(randRotation);
                randForme = FormeFactory.make(objRect);
                break;
            case 3:
                List<Object> objCercle = new ArrayList<>();
                objCercle.add("cercle");
                Point randG3 = isCentered ? new Point(l/2, h/2) : randG(l,h);
                double randL3 = randL(randG3,l/2,h/2);
                objCercle.add(randG3);
                objCercle.add(randL3);
                objCercle.add(0.0);
                randForme = FormeFactory.make(objCercle);
                break;
            case 4:
                List<Point> objTriangle = new ArrayList<>();
                Point A = randPoint(l,h);
                Point B = randPoint(l,h);
                Point C = randPoint(l,h);
                objTriangle.add(A);
                objTriangle.add(B);
                objTriangle.add(C);
                Point G = MethodesForme.barycentre(objTriangle);
                List<Object> objTriangle2 = new ArrayList<>();
                objTriangle2.add("triangle");
                objTriangle2.add(G);
                objTriangle2.addAll(objTriangle);
                objTriangle2.add(randRotation);
                randForme = FormeFactory.make(objTriangle2);
                break;
            default :
                System.out.println("error");
        }
        return randForme;
    }

    public static Point randPoint(double l, double h){
        double randX = Math.random() * (2*l/3) + l/3;
        double randY = Math.random() * (2*h/3) + h/3;
        return new Point(randX, randY);
    }

    public static Point randG(double l, double h){
        double maxLarg = l - l/3;
        double minLarg = l/3;
        double maxHaut = h - h/3;
        double minHaut = h/3;
        double randL = Math.random() * (maxLarg - minLarg) + minLarg;
        double randH = Math.random() * (maxHaut - minHaut) + minHaut;
        return new Point(randL, randH);
    }

    public static double randL(Point G, double l, double h){
        double maxSize = Math.min(l, h);
        double minDistToEdge = Math.min(l - G.getX(), h - G.getY());
        double randL = Math.random() * (maxSize - minDistToEdge) + minDistToEdge;
        return randL;
    }

    public static double randRotation(){
        return Math.random() * 360 + 1;
    }

    public static void main(String[] args) {

    }
}