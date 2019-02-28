package unice.plfgd.common.data;

import unice.plfgd.common.forme.Point;
import unice.plfgd.common.net.Packet;

import java.io.Serializable;
import java.util.List;

public class Draw implements Packet, Serializable {
    private final List<Point> points;
    private final int lar;
    private final int haut;

	public Draw(List<Point> points, int l, int h) {
		this.points = points;
		this.lar = l;
		this.haut = h;
	}

	public List<Point> getPoints() {
		return points;
	}

	public int getLar() {
		return lar;
	}

	public int getHaut() {
		return haut;
	}
}
