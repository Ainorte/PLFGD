package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.Forme;

public class DevinerCheckDrawAction extends Action<Draw, DevinerFormeResult> {
	private Handler handler;

	public DevinerCheckDrawAction(Handler handler) {
		this.handler = handler;
	}

	@Override
	public DevinerFormeResult run(UserStore store, Draw payload) {
		DevinerFormeResult formes = store.getData("formes", DevinerFormeResult.class);

		Forme toCheck = formes.getFormes().remove(0);
		// TODO compare toCheck with the point list in payload

		store.addOrReplaceData("formes", formes);
		return formes;
	}

	@Override
	public Handler getResultHandler() {
		return handler;
	}
}
