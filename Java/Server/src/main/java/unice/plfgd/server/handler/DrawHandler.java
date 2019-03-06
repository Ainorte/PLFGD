package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.data.Draw;
import unice.plfgd.server.Log;

public class DrawHandler extends Handler<Draw> {

	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) {
		if(data != null) {
			Log.log(Log.State.GREEN, data.getPts().toString());
			client.sendEvent("draw",data);
		}
	}
}
