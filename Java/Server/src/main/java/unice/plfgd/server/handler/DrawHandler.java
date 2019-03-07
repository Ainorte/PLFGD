package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Point;
import unice.plfgd.server.Log;

import java.util.ArrayList;
import java.util.List;

import static unice.plfgd.common.forme.TraitementPoints.sanitize;

public class DrawHandler extends Handler<Draw> {

	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) {
		if (data != null) {
			//TODO FIND TOLERANCE IN PERCENTAGE OF DRAW SIZE
			Log.log(Log.State.GREEN, data.getPoints().toString());
			List<List<Point>> points = data.getPoints();
			List<List<Point>> results = new ArrayList<>();
			for (List<Point> point:points) {
				results.add(sanitize(point,20));

			}
			//Log.log(Log.State.BLUE,"Reduced input from "+data.getPoints().size()+" to "+ sanitize(data.getPoints(),20)/.size());
			client.sendEvent("draw",new Draw(results,data.getWidth(), data.getHeight()));
			//client.sendEvent("draw", data);
		}
	}
}
