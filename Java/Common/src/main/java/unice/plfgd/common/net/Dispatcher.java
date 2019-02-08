package unice.plfgd.common.net;

import unice.plfgd.common.data.User;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
	private final Map<String, Handler> actions = new HashMap<>();
	public final Object newConnectionLock = new Object();

	// Data (Custom data class?)
	private User remoteClient;
	private String toFind;

	public Dispatcher set(String action, Handler handler) {
		this.actions.put(action, handler);

		return this;
	}

	public Handler getHandlerFor(String action) {
		return this.actions.get(action);
	}

	public User getRemoteClient() {
		return remoteClient;
	}

	public void setRemoteClient(User remoteClient) {
		this.remoteClient = remoteClient;
	}

	public String getToFind() {
		return toFind;
	}

	public void setToFind(String toFind) {
		this.toFind = toFind;
	}
}
