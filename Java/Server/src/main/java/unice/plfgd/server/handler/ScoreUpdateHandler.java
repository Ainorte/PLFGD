package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.ScoreUpdateAction;
import unice.plfgd.common.net.Packet;

public class ScoreUpdateHandler extends Handler<Packet> {
	@Override
	public void onData(SocketIOClient client, Packet data, AckRequest ackRequest) throws Exception {
		client.sendEvent("setScore", new ScoreUpdateAction(null).run(getStore(client),data));
	}
}
