package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.method.RecogForme;

public class DevinerCheckDrawAction extends Action<Draw, DevinerFormeResult> {
	private Handler handler;

	public DevinerCheckDrawAction(Handler handler) {
		super(handler);
		this.handler = handler;
	}

	@Override
	public DevinerFormeResult run(UserStore store, Draw payload) {
		DevinerFormeResult formes = store.getData("formes", DevinerFormeResult.class);

		var toGuess = formes.getFormes();
		if (!toGuess.isEmpty()) {
			Forme toCheck = toGuess.remove(0);
			// WTF is this hell? Types, modafucka...
			Forme found = (Forme) RecogForme.process(payload.getPoints().get(0)).get(0);

			if (toCheck.equals(found)) {
				formes.incrementScore();
			}
		}

		formes.setHasWon(formes.getScoreToReach() == formes.getScore());

		store.addOrReplaceData("formes", formes);
		return formes;
	}

	@Override
	public Handler getResultHandler() {
		return handler;
	}
}