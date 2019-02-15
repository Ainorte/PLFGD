package unice.plfgd.common.data;

import unice.plfgd.common.forme.Point;
import unice.plfgd.common.net.Packet;

import java.io.Serializable;
import java.util.List;

public class Draw implements Packet, Serializable {
    private final List<Point> points;

	public Draw(List<Point> points) {
		this.points = points;
	}

	public List<Point> getPoints() {
		return points;
	}
}
