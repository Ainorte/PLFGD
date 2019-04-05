package unice.plfgd.common.data.packet;

import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.net.Packet;

public class FormeRequest extends Packet {
	private Forme forme;

	public FormeRequest(Forme forme) {
		this.forme = forme;
	}

	public FormeRequest(){
		forme = Forme.UNKNOWN;
	}

	public Forme getForme() {
		return forme;
	}

	public void setForme(Forme forme) {
		this.forme = forme;
	}
}
