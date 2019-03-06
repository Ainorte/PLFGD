package unice.plfgd.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import unice.plfgd.BuildConfig;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.common.data.User;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.handler.error.ConnectHandler;
import unice.plfgd.tool.handler.error.DisconnectHandler;
import unice.plfgd.tool.handler.DrawHandler;
import unice.plfgd.tool.handler.error.TimeoutHandler;

import java.net.URISyntaxException;

public class Connexion {

	public static final String SERVER_DOMAIN_PORT = BuildConfig.SERVER_DOMAIN + ":" + BuildConfig.SERVER_PORT;
	private static final String PROTOCOL = "http://";
	private static Connexion INSTANCE;
	private Socket socket;
	private String serverURL;
	private BasePresenter presenter;
	private User user;

	private Connexion() {
	}

	public static String getServerURL(String domain) {
		return PROTOCOL + domain;
	}

	public static Connexion getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Connexion();
			INSTANCE.setServerURL(Connexion.getServerURL(SERVER_DOMAIN_PORT));
		}
		return INSTANCE;
	}

	private boolean isConnected() {
		return socket != null;
	}

	public <T extends BasePresenter> T getPresenter(Class<T> obj) {
		return (obj.isInstance(presenter)) ? obj.cast(presenter) : null;
	}

	public void setPresenter(BasePresenter presenter) {
		this.presenter = presenter;
	}

	public void openSocket(User user) {
		this.user = user;

		try {
			socket = IO.socket(getServerURL());

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

	private String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public enum ResetSocketMessage {
		CONNEXION_LOST,
		TIMEOUT
	}

	private void defineHandlers() {
		socket.on(Socket.EVENT_CONNECT_TIMEOUT, new TimeoutHandler(this));
		socket.on(Socket.EVENT_CONNECT_ERROR, new TimeoutHandler(this));
		socket.on(Socket.EVENT_CONNECT, new ConnectHandler(this));
		socket.on(Socket.EVENT_DISCONNECT, new DisconnectHandler(this));

		socket.on("draw", new DrawHandler(this));
	}
}
