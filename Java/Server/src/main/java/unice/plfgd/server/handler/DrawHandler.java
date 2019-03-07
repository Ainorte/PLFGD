package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.data.Draw;
import unice.plfgd.server.Log;

public class DrawHandler extends Handler<Draw> {

	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) {
		if (data != null) {
			//TODO FIND TOLERANCE IN PERCENTAGE OF DRAW SIZE
			Log.log(Log.State.GREEN, data.getPoints().toString());
			//Log.log(Log.State.BLUE,"Reduced input from "+data.getPoints().size()+" to "+ sanitize(data.getPoints(),20).size());
			//client.sendEvent("draw",new Draw(sanitize(data.getPoints(),20),data.getWidth(), data.getHeight()));
			client.sendEvent("draw", data);
		}
	}
}
