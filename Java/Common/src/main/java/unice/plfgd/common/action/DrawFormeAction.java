package unice.plfgd.common.action;

import unice.plfgd.common.data.FormeRequest;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.net.Packet;

import java.util.Random;

public class DrawFormeAction extends Action<Packet, FormeRequest> {

	private Handler handler;

	public DrawFormeAction(Handler handler) {
		this.handler = handler;
	}

	@Override
	public FormeRequest run(Packet payload) {
		Forme[] formes = new Forme[]{Forme.SQUARE, Forme.CIRCLE, Forme.TRIANGLE};
		return new FormeRequest(formes[new Random().nextInt(formes.length)]);
	}

	@Override
	public Handler getResultHandler() {
		return handler;
	}
}
