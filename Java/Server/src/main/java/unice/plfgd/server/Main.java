package unice.plfgd.server;

import com.corundumstudio.socketio.Configuration;
import unice.plfgd.server.handler.DrawHandler;
import unice.plfgd.server.handler.Handler;
import unice.plfgd.server.handler.IdentHandler;

import java.util.Map;

/**
 * Awaiting a connection, to whom we send a question, followed by a wait for answer.
 * This is done until the client sends the right answer.
 */
public class Main {
	private static int uglyStringToInt(String val, int def) {
		try {
			def = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			// This is so ugly it makes me cringe
		}
		return def;
	}

	public static void main(String[] args) {
		Log.init();
		Log.log("Starting...");

		final Map<String, String> env = System.getenv();
		Configuration config = new Configuration();
		config.setHostname(env.getOrDefault("HOST", "127.0.0.1"));
		config.setPort(uglyStringToInt(env.getOrDefault("PORT", "10101"), 10101));
		var handlers = Handler.buildHandlersMap();
		handlers.put("ident", new IdentHandler());
		handlers.put("draw", new DrawHandler());

		var server = new Server(config, handlers);
		server.start();
	}
}
