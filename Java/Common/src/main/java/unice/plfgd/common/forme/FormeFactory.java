package unice.plfgd.common.forme;

import java.util.*;

public class FormeFactory {

    static public List<Point> make(List<Object> forme) {
        List<Point> listForme = new ArrayList<>();
        String formeType = (String)forme.get(0);
        switch(formeType) {
            case "segment":
                listForme = new Segment((Point)forme.get(1), (Point)forme.get(2)).make();
                break;
            case "rect":
                listForme = new Rectangle((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3),(double)forme.get(4)).make();
                break;
            case "carre":
                listForme = new Carre((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3)).make();
                break;
            case "triangle":
                //listForme = new Triangle().make();
            case "ellipse":
                //listForme = new Ellipse().make();
            case "cercle":
                listForme = new Cercle((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3)).make();
                break;
            default:
                System.out.println("error not such form");
        }
        return listForme;
    }
}
