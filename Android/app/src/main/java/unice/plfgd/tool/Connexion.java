package unice.plfgd.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.User;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.handler.AbstractHandler;
import unice.plfgd.tool.handler.status.ConnectHandler;
import unice.plfgd.tool.handler.status.DisconnectHandler;
import unice.plfgd.tool.handler.DrawHandler;
import unice.plfgd.tool.handler.status.TimeoutHandler;
import unice.plfgd.tool.service.APIService;

import java.net.URISyntaxException;

public class Connexion {

	private static Connexion INSTANCE;
	private Socket socket;
	private User user;

	private Connexion() {
	}

	public static Connexion getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Connexion();
		}
		return INSTANCE;
	}

	public boolean isConnected() {
		return socket != null;
	}

	public void openSocket(Configuration conf) {
		this.user = new User(conf.getOrNull("username"));

		try {
			socket = IO.socket("http://" + conf.getOrNull("serverDomain"));

			defineHandlers();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		socket.connect();
	}

	public void close() {
		socket.close();
	}

	public void Identify() {
		sendMessage("ident", user);
	}

	public void sendMessage(String event, Packet payload) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JsonOrgModule());
		JSONObject object = mapper.convertValue(payload, JSONObject.class);

		socket.emit(event, object);
	}

	public void reset() {
		if (isConnected()) {
			socket.close();
		}
	}

	public enum ResetSocketMessage {
		CONNEXION_LOST,
		TIMEOUT
	}

	private void defineHandlers() {
		APIService svc = new APIService();

		final AbstractHandler th = new TimeoutHandler(svc).setConnexion(this);
		socket.on(Socket.EVENT_CONNECT_TIMEOUT, th);
		socket.on(Socket.EVENT_CONNECT_ERROR, th);
		socket.on(Socket.EVENT_CONNECT, new ConnectHandler(svc).setConnexion(this));
		socket.on(Socket.EVENT_DISCONNECT, new DisconnectHandler(svc).setConnexion(this));

		socket.on("draw", new DrawHandler(svc));
	}
}
