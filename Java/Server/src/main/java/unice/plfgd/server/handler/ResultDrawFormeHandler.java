package unice.plfgd.server.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import unice.plfgd.common.action.ResultDrawFormeAction;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.*;
import unice.plfgd.server.Log;

import java.util.ArrayList;
import java.util.List;

public class ResultDrawFormeHandler extends Handler<Draw> {

	@Override
	public void onData(SocketIOClient client, Draw data, AckRequest ackSender) {
		if (data != null) {
			//TODO FIND TOLERANCE IN PERCENTAGE OF DRAW SIZE
			//TODO (possible) SOLUTION : Now that draw max value is 100 tolerance is a percentage.
			// It is in fact not, it's still pixel based, we still need to find a good solution
			Log.log(Log.State.GREEN, data.getPoints().toString());


			//Uncomment this block to display convexHull with the draw without recognition, debug purpose.
			List<List<Point>> pts = data.getPoints();
			List<Point> merge = TraitementPoints.mergeList(data.getPoints());
			List<Point> sanitezed = TraitementPoints.sanitize(merge,2);
			List<Point> refined = TraitementPoints.refineEndPoints(sanitezed,2);
			List<Point> closed = TraitementPoints.closeStroke(refined);
			List<Point> convex = ConvexHull.getConvexHull(closed);

			List<Point> encRec = TraitementPoints.minimumAreaEnclosingRectangle(convex);
			encRec = new Quadrilatere(encRec).make();


			List<Point> maxTri = TraitementPoints.maximumAreaEnclosedTriangle(convex);
			Log.log(Log.State.BLUE, maxTri.toString());
			maxTri = new Triangle(maxTri).make();

			List<List<Point>> res = new ArrayList<>();
			res.add(closed);
			res.add(encRec);
			res.add(maxTri);

			Draw futureDraw = new Draw(res,data.getWidth(),data.getHeight());
			var detecForme = new ResultDrawFormeAction(null).nullRun(futureDraw);



			//var detecForme = new ResultDrawFormeAction(null).run(getStore(client),data);

			Log.log(detecForme.getDraw().toString());
			Log.log(detecForme.getForme().toString());

			client.sendEvent("resultDrawForme", detecForme);
		}
	}
}
