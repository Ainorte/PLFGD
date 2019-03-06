package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.data.Draw;
import unice.plfgd.server.Log;

import static unice.plfgd.common.forme.TraitementPoints.sanitize;

public class DrawHandler extends Handler<Draw> {

	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) {
		if(data != null) {

			Log.log(Log.State.GREEN, sanitize(data.getPts(),100).toString());
			Log.log(Log.State.BLUE,"Reduced input from "+data.getPts().size()+" to "+ sanitize(data.getPts(),100).size());
			client.sendEvent("draw",new Draw(sanitize(data.getPts(),100),data.getWidth(), data.getHeight()));

		}
	}
}
