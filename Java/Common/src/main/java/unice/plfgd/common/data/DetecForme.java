package unice.plfgd.common.data;

import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.net.Packet;

public class DetecForme extends Packet {

	private Draw draw;
	private Forme forme;

	public DetecForme(Draw draw, Forme forme) {
		this.draw = draw;
		this.forme = forme;
	}

	public DetecForme() {
	}

	public Draw getDraw() {
		return draw;
	}

	public void setDraw(Draw draw) {
		this.draw = draw;
	}

	public Forme getForme() {
		return forme;
	}

	public void setForme(Forme forme) {
		this.forme = forme;
	}
}
