package unice.plfgd.common.forme;

import java.util.*;

public class FormeFactory {

    static public List<Point> make(List<Object> forme) {

        List<Point> listForme = new ArrayList<>();
        String formeType = (String)forme.get(0);

        if(forme.size() > 2){
            switch(formeType) {
                case "segment":
                    listForme = new Segment((Point)forme.get(1), (Point)forme.get(2)).make();
                    break;
                case "rectangle":
                    listForme = new Rectangle((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3),(double)forme.get(4)).make();
                    break;
                case "carre":
                    listForme = new Carre((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3)).make();
                    break;
                case "triangle":
                    listForme = new Triangle((Point)forme.get(1),(Point)forme.get(2),(Point)forme.get(3),(Point)forme.get(4),(double)forme.get(5)).make();
                    break;
                case "ellipse":
                    //listForme = new Ellipse().make();
                case "cercle":
                    listForme = new Cercle((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3)).make();
                    break;
                default:
                    System.out.println("error not such form");
            }
        } else {
            switch(formeType) {
                case "segment":
                    listForme = ((Segment)forme.get(1)).make();
                    break;
                case "rectangle":
                    listForme = ((Quadrilatere)forme.get(1)).make();
                    break;
                case "carre":
                    listForme = ((Carre)forme.get(1)).make();
                    break;
                case "triangle":
                    listForme = ((Triangle)forme.get(1)).make();
                    break;
                case "ellipse":
                    //listForme = ((Ellipse)forme.get(1)).make();
                case "cercle":
                    listForme = ((Cercle)forme.get(1)).make();
                    break;
                case "inconnu":
                    listForme = ((Inconnu)forme.get(1)).make();
                default:
                    System.out.println("error not such form");
            }
        }
        return listForme;
    }
}
