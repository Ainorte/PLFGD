package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.DevinerFormeInitAction;
import unice.plfgd.common.net.Packet;

public class DevinerFormeInitHandler extends Handler<Packet> {
	@Override
	public void onData(SocketIOClient client, Packet packet, AckRequest ackRequest) {
		client.sendEvent("startDevinerForme", new DevinerFormeInitAction(null)
				.run(this.getStore(client), packet));
	}
}
