package unice.plfgd.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.listener.DataListener;
import unice.plfgd.server.handler.DrawHandler;
import unice.plfgd.server.handler.Handler;
import unice.plfgd.server.handler.IdentHandler;

import java.util.HashMap;


/**
 * Awaiting a connection, to whom we send a question, followed by a wait for answer.
 * This is done until the client sends the right answer.
 */
public class Main {
	public static void main(String[] args) {
		Log.init();
		Log.log("Starting...");

		Configuration config = new Configuration();
		config.setHostname("192.168.43.28");
		config.setPort(10101);

		HashMap<String, Handler<?>> handlers = new HashMap<>();
		handlers.put("ident", new IdentHandler());
		handlers.put("draw", new DrawHandler());

		var server = new Server(config, handlers);
		server.start();
	}
}
