package unice.plfgd.common.net;

/**
 * This class *must* be used when exchanging data over WebSocket.
 */
public class Exchange {
	private String action;
	private Packet payload;
	private Exception error;

	private Exchange(String action) {
		this.action = action;
	}

	public Exchange payload(Packet payload) {
		this.payload = payload;
		return this;
	}

	public Exchange error(Exception error) {
		this.error = error;
		return this;
	}

	public static Exchange with(String action) {
		return new Exchange(action);
	}

	public String getAction() {
		return action;
	}

	public Packet getPayload() {
		return payload;
	}

	public Exception getError() {
		return error;
	}
}
