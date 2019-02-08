package unice.plfgd.common.data;

import unice.plfgd.common.net.Packet;

import java.io.Serializable;

public class User implements Packet, Serializable {
	private String name;
	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
