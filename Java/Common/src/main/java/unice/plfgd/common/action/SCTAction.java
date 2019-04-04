package unice.plfgd.common.action;

import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.FormeRequest;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.net.Packet;

import java.util.Random;

public class SCTAction extends Action<Packet,Packet> {

	public SCTAction(Handler handler) {
		super(handler);
	}

	@Override
	public Packet run(UserStore store, Packet payload) {
		store.setCurrentGame(Game.SCT);
		Forme[] formes = new Forme[]{Forme.RECTANGLE, Forme.CIRCLE, Forme.TRIANGLE};
		FormeRequest forme = new FormeRequest(formes[new Random().nextInt(formes.length)]);
		store.addOrReplaceData("forme", forme);
		return null;
	}
}
