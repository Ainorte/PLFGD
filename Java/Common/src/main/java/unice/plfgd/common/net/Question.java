package unice.plfgd.common.net;

public class Question implements Packet {
	private final boolean bigger;

	public Question(boolean bigger) {
		this.bigger = bigger;
	}

	public boolean isBigger() {
		return bigger;
	}
}
