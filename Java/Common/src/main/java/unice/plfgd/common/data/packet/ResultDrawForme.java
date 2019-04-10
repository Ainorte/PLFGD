package unice.plfgd.common.data.packet;

import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.net.Packet;

public class ResultDrawForme extends Packet {

	private Draw draw;
	private Forme forme;
	private boolean validate;
	private Forme expected;

	public ResultDrawForme(Draw draw, Forme forme, boolean validate, Forme expected) {
		this.draw = draw;
		this.forme = forme;
		this.validate = validate;
		this.expected = expected;
	}

	public ResultDrawForme(Draw draw, Forme forme, Forme expected) {
		this.draw = draw;
		this.forme = forme;
		this.expected = expected;
		this.validate = expected == Forme.RECTANGLE ? forme == Forme.SQUARE || forme == Forme.RECTANGLE : forme == expected;
	}

	public ResultDrawForme() {
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

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public Forme getExpected() {
		return expected;
	}

	public void setExpected(Forme expected) {
		this.expected = expected;
	}
}
