package unice.plfgd.server;

import com.corundumstudio.socketio.Configuration;
import unice.plfgd.server.handler.DrawHandler;
import unice.plfgd.server.handler.Handler;
import unice.plfgd.server.handler.IdentHandler;


/**
 * Awaiting a connection, to whom we send a question, followed by a wait for answer.
 * This is done until the client sends the right answer.
 */
public class Main {
	public static void main(String[] args) {
		Log.init();
		Log.log("Starting...");

		Configuration config = new Configuration();
		// TODO put that as cmdline args
		config.setHostname("127.0.0.1");
		config.setPort(10101);

		var handlers = Handler.buildHandlersMap();
		handlers.put("ident", new IdentHandler());
		handlers.put("draw", new DrawHandler());

		var server = new Server(config, handlers);
		server.start();
	}
}
