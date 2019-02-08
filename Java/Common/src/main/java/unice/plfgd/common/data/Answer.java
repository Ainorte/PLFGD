package unice.plfgd.common.data;

import unice.plfgd.common.net.Packet;

import java.io.Serializable;

public class Answer implements Packet, Serializable {
	private final String value;

	public Answer(String value) {
		this.value = value;
	}

	public String getValue() { return value; }
}
