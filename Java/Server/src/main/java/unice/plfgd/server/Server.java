package unice.plfgd.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import unice.plfgd.common.net.Packet;
import unice.plfgd.server.handler.Handler;

import java.util.HashMap;

public class Server {
	private SocketIOServer server;

	public Server(Configuration config, HashMap<String, Handler<? extends Packet>> handlers) {
		this.server = new SocketIOServer(config);

		this.server.addConnectListener(this::handleNewUser);
		for (var handler : handlers.entrySet()) {
			var classType = handler.getValue().getGenericTypeClass();
			server.addEventListener(handler.getKey(), (Class<Packet>) classType, (DataListener<Packet>) handler.getValue());
		}
	}

	public void start() {
		Log.log("Starting WebSocket server...");
		this.server.start();
		Log.log("Awaiting connection");
	}

	private void handleNewUser(SocketIOClient socketIOClient) {
		Log.log(Log.State.BLUE, "New user connection from address " + socketIOClient.getRemoteAddress());
	}
}
