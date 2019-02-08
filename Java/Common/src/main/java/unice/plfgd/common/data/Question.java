package unice.plfgd.common.data;

import unice.plfgd.common.net.Packet;

import java.io.Serializable;

public class Question implements Packet, Serializable {
	private final String question;

	public Question(String question) {
		this.question = question;
	}

	public String getQuestion() { return question; }
}
