package unice.plfgd.common.action;

import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.forme.GenerationFormes;
import unice.plfgd.common.net.Packet;

import java.util.List;

public class DevinerFormeInitAction extends Action<Packet, DevinerFormeResult> {
	private Handler handler;

	public DevinerFormeInitAction(Handler handler) {
		this.handler = handler;
	}

	@Override
	public DevinerFormeResult run(UserStore store, Packet payload) {
		store.setCurrentGame(Game.DEVINER);
		DevinerFormeResult formes = new DevinerFormeResult(List.of(
				GenerationFormes.randEnumForme(),
				GenerationFormes.randEnumForme(),
				GenerationFormes.randEnumForme(),
				GenerationFormes.randEnumForme(),
				GenerationFormes.randEnumForme()
		));
		store.addOrReplaceData("formes", formes);
		return formes;
	}

	@Override
	public Handler getResultHandler() {
		return handler;
	}
}
