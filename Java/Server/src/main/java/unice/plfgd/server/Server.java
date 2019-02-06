package unice.plfgd.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import unice.plfgd.common.net.Exchange;
import unice.plfgd.server.error.UnknownActionError;

public class Server {
	private SocketIOServer server;
	private Dispatcher requestDispatcher;

	public Server(Configuration config, Dispatcher requestDispatcher) {
		this.server = new SocketIOServer(config);
		this.requestDispatcher = requestDispatcher;

		this.server.addConnectListener(this::handleNewUser);
		this.server.addEventListener("message", Exchange.class, this::handleIncomingMessage);
	}

	public void start() {
		Log.log("Starting WebSocket server...");
		this.server.start();
		Log.log("Awaiting connection");

		synchronized (this.requestDispatcher.newConnectionLock) {
			try {
				this.requestDispatcher.newConnectionLock.wait();
			} catch (InterruptedException e) {
				Log.log(Log.State.RED, "Error during listen");
				Log.log(Log.State.RED, e.toString());
			}
		}

		Log.log("Shutting down WebSocket server");
		this.server.stop();
	}

	private void handleNewUser(SocketIOClient socketIOClient) {
		Log.log(Log.State.BLUE, "New user connection from address " + socketIOClient.getRemoteAddress());
	}

	private void handleIncomingMessage(SocketIOClient client, Exchange packet, AckRequest ackRequest) {
		// Did the client send us an error? If yes > we don't answer back and stop processing
		var error = packet.getError();
		if (error != null) {
			Log.log(Log.State.RED, error.toString());
			return;
		}

		// Can we find a handler?
		final var handler = this.requestDispatcher.getHandlerFor(packet.getAction());
		Exchange response;
		if (handler != null) {
			response = handler.invoke(this.requestDispatcher, packet.getPayload());
		} else {
			error = new UnknownActionError("No handler implemented for action " + packet.getAction());
			Log.log(Log.State.RED, error.toString());
			response = Exchange.with("message").error(error);
		}

		// If a dispatcher sent null, we assume we're closing the server.
		if (response == null) {
			synchronized (this.requestDispatcher.newConnectionLock) {
				this.requestDispatcher.newConnectionLock.notify();
			}
		}

		client.sendEvent("message", response);
	}
}
