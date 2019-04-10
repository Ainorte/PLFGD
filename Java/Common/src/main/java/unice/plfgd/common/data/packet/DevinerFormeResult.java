package unice.plfgd.common.data.packet;

import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.net.Packet;

import java.util.List;

public class DevinerFormeResult extends Packet {
	public List<Forme> getFormes() {
		return formes;
	}

	public void setFormes(List<Forme> formes) {
		this.formes = formes;
		this.scoreToReach = formes.size();
	}

	private List<Forme> formes;

	private int scoreToReach;
	private int score;
	private Boolean hasWon;

	public DevinerFormeResult() {
	}

	public DevinerFormeResult(List<Forme> formes) {
		this.formes = formes;
		this.scoreToReach = formes.size();
	}

	public int getScore() {
		return score;
	}

	public Boolean getHasWon() {
		return hasWon;
	}

	public void setHasWon(Boolean hasWon) {
		this.hasWon = hasWon;
	}

	public void setScore(int score) {
		this.score = score;
	}
	public void incrementScore() {
		this.score++;
	}

	public int getScoreToReach() {
		return scoreToReach;
	}

	public void setScoreToReach(int scoreToReach) {
		this.scoreToReach = scoreToReach;
	}
}
