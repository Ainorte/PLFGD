package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Point;
import unice.plfgd.server.Log;

import static unice.plfgd.common.forme.Identifier.sanitize;

public class DrawHandler extends Handler<Draw> {

	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) {
		if(data != null) {


			System.out.println((data.getPts().get(data.getPts().size()-1)));

			Log.log(Log.State.GREEN, sanitize(data.getPts(),100).toString());
			System.out.println("Reduced input from "+data.getPts().size()+" to "+ sanitize(data.getPts(),100).size());
			client.sendEvent("draw",new Draw(sanitize(data.getPts(),100),data.getWidth(), data.getHeight()));

		}
	}
}
