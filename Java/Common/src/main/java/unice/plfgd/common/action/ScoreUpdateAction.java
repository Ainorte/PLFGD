package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.Score;
import unice.plfgd.common.net.Packet;

public class ScoreUpdateAction extends Action<Packet, Score> {

	public ScoreUpdateAction(Handler resultHandler) {
		super(resultHandler);
	}

	@Override
	public Score run(UserStore store, Packet payload) {
		return new Score(store.getScore());
	}
}
