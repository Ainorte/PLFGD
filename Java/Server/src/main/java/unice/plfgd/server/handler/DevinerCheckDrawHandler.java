package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.DevinerCheckDrawAction;
import unice.plfgd.common.data.packet.Draw;

public class DevinerCheckDrawHandler extends Handler<Draw> {
	@Override
	public void onData(SocketIOClient client, Draw draw, AckRequest ackRequest) {
		client.sendEvent("devinerCheckDraw", new DevinerCheckDrawAction(null)
				.run(getStore(client), draw));
	}
}
