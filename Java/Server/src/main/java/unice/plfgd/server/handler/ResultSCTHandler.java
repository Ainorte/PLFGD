package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.ResultSCTAction;
import unice.plfgd.common.data.packet.Draw;

public class ResultSCTHandler extends Handler<Draw> {
	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) throws Exception {
		client.sendEvent("resultSCT", new ResultSCTAction(null).run(getStore(client),data));
	}
}
