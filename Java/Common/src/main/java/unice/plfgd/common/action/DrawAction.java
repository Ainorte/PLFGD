package unice.plfgd.common.action;

import unice.plfgd.common.data.DetecForme;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.forme.FormeFactory;
import unice.plfgd.common.forme.Point;
import unice.plfgd.common.forme.RecogForme;

import java.util.ArrayList;
import java.util.List;

public class DrawAction extends Action<Draw, DetecForme> {
	private Handler resultHandler;

	public Handler getResultHandler() {
		return resultHandler;
	}

	public DrawAction(Handler resultHandler) {
		this.resultHandler = resultHandler;
	}

	@Override
	public DetecForme run(Draw payload) {
		List<List<Point>> ptGroups = payload.getPoints();
		List<Point> pts = new ArrayList<>();
		for (List<Point> p : ptGroups) {
			pts.addAll(p);
		}

		final List<Object> results = RecogForme.process(pts);

		return new DetecForme(
				new Draw(new ArrayList<List<Point>>() {{
					add(FormeFactory.make(results));
				}}, payload.getWidth(), payload.getHeight()),
				(Forme) results.get(0)
		);
	}
}
