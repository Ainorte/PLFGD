package unice.plfgd.common.data.packet;

import unice.plfgd.common.net.Packet;

public class Score extends Packet {
	private int score;

	public Score(int score) {
		this.score = score;
	}

	public Score() {
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
