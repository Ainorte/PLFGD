package unice.plfgd.server;

import unice.plfgd.common.Move;
import unice.plfgd.common.net.Identifier;
import unice.plfgd.server.handlers.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dispatcher {
	private final Map<String, Handler> actions = new HashMap<>();
	public final Object newConnectionLock = new Object();

	// Data (Custom data class?)
	private Identifier remoteClient;
	private int toFind;
	private final List<Move> lastMoves = new ArrayList<>();

	public Dispatcher set(String action, Handler handler) {
		this.actions.put(action, handler);

		return this;
	}

	public Handler getHandlerFor(String action) {
		return this.actions.get(action);
	}

	public Identifier getRemoteClient() {
		return remoteClient;
	}

	public void setRemoteClient(Identifier remoteClient) {
		this.remoteClient = remoteClient;
	}

	public int getToFind() {
		return toFind;
	}

	public void setToFind(int toFind) {
		this.toFind = toFind;
	}

	public List<Move> getLastMoves() {
		return lastMoves;
	}
}
