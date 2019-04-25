package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.data.packet.ResultDrawForme;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.method.RecogForme;

import java.util.List;

public class DevinerCheckDrawAction extends Action<Draw, DevinerFormeResult> {
	private Handler handler;

	public DevinerCheckDrawAction(Handler handler) {
		super(handler);
		this.handler = handler;
	}

	@Override
	public DevinerFormeResult run(UserStore store, Draw payload) {
		DevinerFormeResult formes = store.getData("formes", DevinerFormeResult.class);

		if (formes != null) {
			List<Forme> toGuess = formes.getFormes();
			if (!toGuess.isEmpty()) {
				Forme toCheck = toGuess.remove(0);

				ResultDrawForme recog = new ResultDrawFormeAction(null).run(new UserStore(),payload);
				Forme found = recog.getForme();

				if (toCheck.equals(found) || (toCheck.equals(Forme.SQUARE) && found.equals(Forme.RECTANGLE))) {
					formes.incrementScore();
					store.incrementScore();
					formes.setHasGuessedRight(true);
				} else {
					formes.setHasGuessedRight(false);
				}
			}

			formes.setHasWon(formes.getScoreToReach() == formes.getScore());
		} else {
			System.err.println("Formes is null, wat?");
		}

		store.addOrReplaceData("formes", formes);
		if(formes.getFormes().size() == 0 || !formes.getHasGuessedRight())
		{
			store.resetGame();
		}
		return formes;
	}

	@Override
	public Handler getResultHandler() {
		return handler;
	}
}
