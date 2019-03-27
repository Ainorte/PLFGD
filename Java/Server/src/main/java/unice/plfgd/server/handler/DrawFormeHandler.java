package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.DrawFormeAction;
import unice.plfgd.common.net.Packet;

public class DrawFormeHandler  extends Handler<Packet>{
	@Override
	public void onData(SocketIOClient client, Packet data, AckRequest ackSender) throws Exception {
		client.sendEvent("drawForme", new DrawFormeAction(null).run(getStore(client),null));
	}
}
