package unice.plfgd.server;

import com.corundumstudio.socketio.Configuration;
import unice.plfgd.common.net.Dispatcher;
import unice.plfgd.server.handlers.DrawHandler;
import unice.plfgd.server.handlers.IdentHandler;


/**
 * Awaiting a connection, to whom we send a question, followed by a wait for answer.
 * This is done until the client sends the right answer.
 */
public class Main {
	public static void main(String[] args) {
		Log.init();
		Log.log("Starting...");

		Configuration config = new Configuration();
		config.setHostname("127.0.0.1");
		config.setPort(10101);

		var server = new Server(
				config,
				new Dispatcher()
						.set("ident", new IdentHandler())
						.set("draw", new DrawHandler())
		);
		server.start();

		Log.log("Exiting...");
	}
}
