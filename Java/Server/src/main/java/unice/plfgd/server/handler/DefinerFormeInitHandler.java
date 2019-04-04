package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.net.Packet;

public class DefinerFormeInitHandler extends Handler<Packet> {
	@Override
	public void onData(SocketIOClient client, Packet packet, AckRequest ackRequest) {
		// TODO
		var store = this.getStore(client);
		store.getData()
		client.sendEvent("startDevinerForme");
	}
}
