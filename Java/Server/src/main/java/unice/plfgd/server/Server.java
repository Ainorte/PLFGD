package unice.plfgd.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.net.Packet;
import unice.plfgd.server.handler.Handler;

import java.util.HashMap;
import java.util.Map;

public class Server {
	private SocketIOServer server;
	private Map<SocketIOClient, UserStore> store = new HashMap<>();

	Server(Configuration config, HashMap<String, Handler<? extends Packet>> handlers) {
		this.server = new SocketIOServer(config);

		this.server.addConnectListener(this::handleNewUser);
		this.server.addDisconnectListener(this::handleDisconnectedUser);
		for (var handler : handlers.entrySet()) {
			var classType = handler.getValue().getGenericTypeClass();
			var handlerInst = handler.getValue();
			handlerInst.setServer(this);
			server.addEventListener(handler.getKey(), (Class<Packet>) classType, (DataListener<Packet>) handlerInst);
		}
	}

	void start() {
		Log.log("Starting WebSocket server...");
		this.server.start();
		Log.log("Awaiting connection");
	}

	private void handleNewUser(SocketIOClient client) {
		store.put(client, new UserStore());
		Log.log(Log.State.BLUE, "New user connection from address " + client.getRemoteAddress());
	}

	private void handleDisconnectedUser(SocketIOClient client) {
		store.remove(client);
		Log.log(Log.State.BLUE, "User disconnected");
	}

	public UserStore getStoreForUser(SocketIOClient client) {
		return store.get(client);
	}
}
