package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.data.packet.User;
import unice.plfgd.server.Log;

public class IdentHandler extends Handler<User> {

	@Override
	public void onData(SocketIOClient client, User data, AckRequest ackSender) {
		if (data != null) {
			final var name = data.getName();
			Log.log("The remote user is " + name);
			getStore(client).setName(name);
		}
	}
}
