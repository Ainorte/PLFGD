package unice.plfgd.tool.service;

import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONObject;
import unice.plfgd.common.data.packet.User;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.Configuration;
import unice.plfgd.tool.responsehandler.AbstractHandler;
import unice.plfgd.tool.responsehandler.DrawFormeHandler;
import unice.plfgd.tool.responsehandler.DrawHandler;
import unice.plfgd.tool.responsehandler.RecogHandler;
import unice.plfgd.tool.responsehandler.status.ConnectHandler;
import unice.plfgd.tool.responsehandler.status.DisconnectHandler;
import unice.plfgd.tool.responsehandler.status.TimeoutHandler;

import java.net.URISyntaxException;

public class RemoteAPIImpl implements API {
	private static final String TAG = "LOCAL_API_IMPLEMENTATION";

	private static RemoteAPIImpl INSTANCE;
	private Socket socket;
	private User user;

	private RemoteAPIImpl() {
	}

	public static RemoteAPIImpl getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RemoteAPIImpl();
		}
		return INSTANCE;
	}

	public boolean isConnected() {
		return socket != null;
	}

	public void openSocket(Configuration conf) {
		this.user = new User(conf.getOrNull("username"));

		try {
			socket = IO.socket("http://" + conf.getOrNull("serverURL"));

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
		Log.i(TAG, "Identifying ourselves against remote server, as " + user.getName());
		sendMessage("ident", user);
	}

	public void sendMessage(String event, Packet payload) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JsonOrgModule());
		JSONObject object = mapper.convertValue(payload, JSONObject.class);

		Log.i(TAG, "Sending message for event " + event);

		socket.emit(event, object);
	}

	public void reset() {
		if (isConnected()) {
			Log.i(TAG, "Closing socket");
			socket.close();
		}
	}

	private void defineHandlers() {
		APIService svc = APIService.getInstance();

		final AbstractHandler th = new TimeoutHandler(svc).setConnexion(this);
		socket.on(Socket.EVENT_CONNECT_TIMEOUT, th);
		socket.on(Socket.EVENT_CONNECT_ERROR, th);
		socket.on(Socket.EVENT_CONNECT, new ConnectHandler(svc).setConnexion(this));
		socket.on(Socket.EVENT_DISCONNECT, new DisconnectHandler(svc).setConnexion(this));
		socket.on("draw", new DrawHandler(svc));
		socket.on("recog", new RecogHandler(svc));
		socket.on("drawForme", new DrawFormeHandler(svc));
	}

	public enum ResetSocketMessage {
		CONNEXION_LOST,
		TIMEOUT
	}
}
