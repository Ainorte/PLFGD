package unice.plfgd.common.data;

import unice.plfgd.common.net.Packet;

public class User extends Packet {
	private String name;

	public User(String name) {
		this.name = name;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
