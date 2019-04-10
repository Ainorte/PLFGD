package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.SCTAction;
import unice.plfgd.common.net.Packet;

public class SCTHandler extends Handler<Packet>{

	@Override
	public void onData(SocketIOClient client, Packet data, AckRequest ackSender) throws Exception {
		client.sendEvent("sct", new SCTAction(null).run(getStore(client),null));
	}
}
