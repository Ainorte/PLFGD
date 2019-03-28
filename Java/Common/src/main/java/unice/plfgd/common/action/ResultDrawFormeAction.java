package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.data.packet.FormeRequest;
import unice.plfgd.common.data.packet.ResultDrawForme;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.forme.FormeFactory;
import unice.plfgd.common.forme.Point;
import unice.plfgd.common.forme.RecogForme;

import java.util.ArrayList;
import java.util.List;

public class ResultDrawFormeAction extends Action<Draw, ResultDrawForme> {
	private Handler resultHandler;

	public ResultDrawFormeAction(Handler resultHandler) {
		this.resultHandler = resultHandler;
	}

	public Handler getResultHandler() {
		return resultHandler;
	}

	@Override
	public ResultDrawForme run(UserStore store, Draw payload) {

		Forme expected = store.getData("forme", FormeRequest.class).getForme();

		List<List<Point>> ptGroups = payload.getPoints();
		List<Point> pts = new ArrayList<>();
		for (List<Point> p : ptGroups) {
			pts.addAll(p);
		}

		final List<Object> results = RecogForme.process(pts);

		ResultDrawForme resultDrawForme = new ResultDrawForme(
				new Draw(new ArrayList<List<Point>>() {{
					add(FormeFactory.make(results));
				}}, payload.getWidth(), payload.getHeight()),
				(Forme) results.get(0),
				expected
		);

		return resultDrawForme;
	}
}
