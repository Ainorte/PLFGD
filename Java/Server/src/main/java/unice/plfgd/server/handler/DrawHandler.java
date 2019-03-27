package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.DrawAction;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.server.Log;

public class DrawHandler extends Handler<Draw> {

	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) {
		if (data != null) {
			//TODO FIND TOLERANCE IN PERCENTAGE OF DRAW SIZE
			//TODO (possible) SOLUTION : Now that draw max value is 100 tolerance is a percentage.
			// It is in fact not, it's still pixel based, we still need to find a good solution
			Log.log(Log.State.GREEN, data.getPoints().toString());

			var detecForme = new DrawAction(null).run(getStore(client), data);

			Log.log(detecForme.getDraw().toString());
			Log.log(detecForme.getForme().toString());

			client.sendEvent("recog", detecForme);
		}
	}
}
