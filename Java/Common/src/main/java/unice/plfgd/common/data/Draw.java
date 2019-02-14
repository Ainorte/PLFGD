package unice.plfgd.common.data;

import unice.plfgd.common.forme.Point;
import unice.plfgd.common.net.Packet;

import java.io.Serializable;

public class Draw implements Packet, Serializable {
    private final Point[] points;

    public Draw(Point[] points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }
}
