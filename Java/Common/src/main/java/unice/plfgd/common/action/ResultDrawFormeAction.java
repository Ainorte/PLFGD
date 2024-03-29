package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.data.packet.FormeRequest;
import unice.plfgd.common.data.packet.ResultDrawForme;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.forme.Point;
import unice.plfgd.common.forme.generation.FormeFactory;
import unice.plfgd.common.forme.method.RecogForme;

import java.util.ArrayList;
import java.util.List;

public class ResultDrawFormeAction extends Action<Draw, ResultDrawForme> {
	private Handler resultHandler;

	public ResultDrawFormeAction(Handler resultHandler) {
		super(resultHandler);
	}

	@Override
	public ResultDrawForme run(UserStore store, Draw payload) {

		FormeRequest ex = store.getData("forme", FormeRequest.class);
		Forme expected = Forme.UNKNOWN;
		if (ex != null) {
			expected = ex.getForme();
		}

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

		if (resultDrawForme.isValidate()) {
			store.incrementScore();
		}

		store.resetGame();

		return resultDrawForme;
	}

	// Return the payload with no action made on the draw;
	public ResultDrawForme nullRun(Draw payload) {
		return new ResultDrawForme(payload, Forme.UNKNOWN, false, Forme.UNKNOWN);
	}

}
