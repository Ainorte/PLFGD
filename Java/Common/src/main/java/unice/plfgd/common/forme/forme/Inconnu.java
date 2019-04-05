package unice.plfgd.common.forme.forme;

import java.util.List;

public class Inconnu {

	protected List<Point> forme;
	protected Forme type = Forme.UNKNOWN;


	public Inconnu(List<Point> pts) {
		this.forme = pts;
	}

	public Forme getType() {
		return type;
	}

	public List<Point> make() {
		return forme;
	}
}
