package unice.plfgd.common.forme;

import java.util.List;

public class Inconnu {

    List<Point> forme;
    public Inconnu(List<Point> pts){
        this.forme = pts;
    }

    List<Point> make(){
        return forme;
    }
}
