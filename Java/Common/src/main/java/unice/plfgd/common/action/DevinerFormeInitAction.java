package unice.plfgd.common.action;

import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.generation.GenerationFormes;
import unice.plfgd.common.net.Packet;

import java.util.ArrayList;
import java.util.List;

public class DevinerFormeInitAction extends Action<Packet, DevinerFormeResult> {
	private static final int GUESS_COUNT = 5;
	private Handler handler;

	public DevinerFormeInitAction(Handler handler) {
		super(handler);
		this.handler = handler;
	}

	@Override
	public DevinerFormeResult run(UserStore store, Packet payload) {
		store.setCurrentGame(Game.DEVINER);
		DevinerFormeResult formes = new DevinerFormeResult();

		List<Forme> toGuess = new ArrayList<>();
		formes.setScoreToReach(GUESS_COUNT);
		for (int i = 0; i < formes.getScoreToReach(); ++i) {
			toGuess.add(GenerationFormes.randEnumForme());
		}
		formes.setFormes(toGuess);

		store.addOrReplaceData("formes", formes);
		return formes;
	}

	@Override
	public Handler getResultHandler() {
		return handler;
	}
}
