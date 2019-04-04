package unice.plfgd.common.data.packet;

import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.net.Packet;

import java.util.List;

public class DevinerFormeResult extends Packet {
	public List<Forme> getFormes() {
		return formes;
	}

	public void setFormes(List<Forme> formes) {
		this.formes = formes;
	}

	private List<Forme> formes;

	public DevinerFormeResult(int score) {
		this.score = score;
	}

	public DevinerFormeResult(List<Forme> formes, int score) {
		this.formes = formes;
		this.score = score;
	}

	private int score;

	public DevinerFormeResult() {
	}

	public DevinerFormeResult(List<Forme> formes) {
		this.formes = formes;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
