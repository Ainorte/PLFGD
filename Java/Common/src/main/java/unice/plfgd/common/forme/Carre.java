package unice.plfgd.common.forme;

import java.util.ArrayList;
import java.util.List;

public class Carre extends Rectangle {

	protected Forme type = Forme.SQUARE;

	public Carre(Point G, double l, double rot) {
		super(G, l, l, rot);
	}

	public double getAire() {
		return l * l;
	}

	public double getPerim() {
		return 4 * l;
	}

	public Forme getType() {
		return type;
	}

	public String toString() {
		return super.toString();
	}


}
